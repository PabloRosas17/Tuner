package interview.android.tuner.controller

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import interview.android.tuner.model.AppleApiRequest
import interview.android.tuner.model.ModelFocused
import interview.android.tuner.services.TunesService
import interview.android.tuner.util.TunerUtil
import interview.android.tuner.view.*
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/* @desc model typed for Focused and module definition for di, expose the model to the view
 * @param injected TunerService
 * @return view model of type focused */
class ViewModelFocused(private val mTunesService: TunesService) : ViewModel() {

    /* model definition for focused */
    var mModelFocused: ModelFocused = ModelFocused()

    /* observable acting on behalf as live data */
    val mTitle: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    /* perform network call and parse the response */
    fun fire(view: ViewFocused, caller: String, artist: String) {
        viewModelScope.launch {
            var call: Call<AppleApiRequest>? = null
            when (caller) {
                ViewComingSoon::class.java.simpleName -> {
                    call = mTunesService.getComingSoon()
                }
                ViewHotTracks::class.java.simpleName -> {
                    call = mTunesService.getHotTracks()
                }
                ViewNewReleases::class.java.simpleName -> {
                    call = mTunesService.getNewReleases()
                }
                ViewTopAlbums::class.java.simpleName -> {
                    call = mTunesService.getTopAlbums()
                }
                ViewTopSongs::class.java.simpleName -> {
                    call = mTunesService.getTopSongs()
                }
            }

            call?.enqueue(object : Callback<AppleApiRequest> {
                override fun onResponse(
                    call: Call<AppleApiRequest>,
                    response: Response<AppleApiRequest>
                ) {
                    val mAppleApiRequest = response.body()!!
                    TunerUtil().filterAppleApiResults(
                        caller,
                        this@ViewModelFocused,
                        mAppleApiRequest,
                        artist
                    )
                    view.subscribeUi()
                }

                override fun onFailure(call: Call<AppleApiRequest>, t: Throwable) {
                    println(t.message)
                }
            })
        }
    }
}