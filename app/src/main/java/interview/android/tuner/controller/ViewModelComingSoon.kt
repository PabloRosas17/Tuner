package interview.android.tuner.controller

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import interview.android.tuner.model.AppleApiRequest
import interview.android.tuner.model.ModelComingSoon
import interview.android.tuner.services.TunesService
import interview.android.tuner.util.TunerUtil
import interview.android.tuner.view.ViewComingSoon
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/* @desc model typed for ComingSoon and module definition for di, expose the model to the view
 * @param injected TunerService
 * @return view model of type coming soon */
class ViewModelComingSoon(private val mTunesService: TunesService): ViewModel() {

    /* model definition for coming soon */
    var mModelComingSoon: ModelComingSoon = ModelComingSoon()

    /* observable acting on behalf as live data */
    val mData: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    /* initialization */
    init {
        fire()
    }

    /* perform network call and parse the response */
    private fun fire(){
        viewModelScope.launch {
            val call = mTunesService.getComingSoon()
            call.enqueue(object : Callback<AppleApiRequest> {
                override fun onResponse(call: Call<AppleApiRequest>, response: Response<AppleApiRequest>) {
                    val mAppleApiRequest = response.body()!!
                    TunerUtil().filterAppleApiResults(
                        "ViewComingSoon",
                        this@ViewModelComingSoon,
                        mAppleApiRequest,
                        ""
                    )
                    mData.postValue("Done")
                }

                override fun onFailure(call: Call<AppleApiRequest>, t: Throwable) {
                    println(t.message)
                }
            })
        }
    }
}