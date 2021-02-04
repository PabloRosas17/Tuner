package interview.android.tuner.controller

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import interview.android.tuner.model.AppleApiRequest
import interview.android.tuner.model.ModelTopSongs
import interview.android.tuner.services.TunesService
import interview.android.tuner.util.TunerUtil
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/* @desc model typed for TopSongs and module definition for di, expose the model to the view
 * @param injected TunerService
 * @return view model of type top songs */
class ViewModelTopSongs(val mTunesService: TunesService): ViewModel() {

    /* model definition for top songs */
    var mModelTopSongs: ModelTopSongs = ModelTopSongs()

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
            val call = mTunesService.getTopSongs()
            call.enqueue(object : Callback<AppleApiRequest> {
                override fun onResponse(
                    call: Call<AppleApiRequest>,
                    response: Response<AppleApiRequest>
                ) {
                    val mAppleApiRequest = response.body()!!
                    TunerUtil().filterAppleApiResults(
                        "ViewTopSongs",
                        this@ViewModelTopSongs,
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