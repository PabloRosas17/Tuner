package interview.android.tuner.util

import android.content.Intent
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.google.android.material.textview.MaterialTextView
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import interview.android.tuner.controller.adapter.RecyclerViewAdapter
import interview.android.tuner.controller.*
import interview.android.tuner.model.*
import java.lang.RuntimeException

/* @desc utility servicing generalized methods */
class TunerUtil {

    /* @desc generalized methods to fire intents
    *  @param activity is the calling activity , type T is the class type of the caller */
    fun <T : AppCompatActivity> fireIntent(activity: AppCompatActivity, type: Class<T>) {
        val mIntent = Intent(activity, type)
        activity.startActivity(mIntent)
        activity.finish()
    }

    /* @desc generalized methods to fire on apple api results
    *  @param caller, mViewModel, results, key for filtering the results */
    fun filterAppleApiResults(caller: String, mViewModel: ViewModel, results: AppleApiRequest, key: String) {
        when(mViewModel) {
            is ViewModelComingSoon -> { helpFilter(mViewModel,mViewModel.mModelComingSoon.mArtists,mViewModel.mModelComingSoon.mArtUrl,results) }
            is ViewModelHotTracks -> { helpFilter(mViewModel,mViewModel.mModelHotTracks.mArtists,mViewModel.mModelHotTracks.mArtUrl,results) }
            is ViewModelNewReleases -> { helpFilter(mViewModel,mViewModel.mModelNewReleases.mArtists,mViewModel.mModelNewReleases.mArtUrl,results) }
            is ViewModelTopAlbums -> { helpFilter(mViewModel,mViewModel.mModelTopAlbums.mArtists,mViewModel.mModelTopAlbums.mArtUrl,results) }
            is ViewModelTopSongs -> { helpFilter(mViewModel,mViewModel.mModelTopSongs.mArtists,mViewModel.mModelTopSongs.mArtUrl,results) }
            is ViewModelFocused -> { helpFocusedFilter(
                mViewModel,mViewModel.mModelFocused.mInfo,mViewModel.mModelFocused.mArtUrl,mViewModel.mModelFocused.mCopyright,mViewModel.mModelFocused.mWebUrl
                ,results,key,caller) }
            else -> { throw RuntimeException("RecyclerViewAdapter.kt, getItemCount()")
            }
        }
    }

    /* @desc auxilary method to help filter results
    *  @param vm, and collection of lists that'll add data */
    private fun helpFilter(vm: ViewModel, artistlist: ArrayList<String>, artworklist: ArrayList<String>, results: AppleApiRequest) {
        val rsp = Gson().fromJson<AppleApiModel>(results.mFeed,AppleApiModel::class.java)
        /* apple api has a different approach for HotTracks, therefore detect the type ViewModelHotTracks
        *  else continue execution on the generalized type
        *  when data is found add it to its respective list */
        if(vm is ViewModelHotTracks) {
            /* perform iterations, 0(n) operations, due to list iteration */
            for(i in 0 until rsp.results.size()){
                val tempdata = Gson().fromJson<AppleApiModelHotTrackResults>(rsp.results.get(i),AppleApiModelHotTrackResults::class.java)
                artistlist.add(tempdata.name)
                artworklist.add(tempdata.artworkUrl100)
            }
        } else {
            /* perform iterations, 0(n) operations, due to list iteration */
            for (i in 0 until rsp.results.size()) {
                val tempdata = Gson().fromJson<AppleApiModelResults>(
                    rsp.results.get(i),
                    AppleApiModelResults::class.java
                )
                artistlist.add(tempdata.artistName)
                artworklist.add(tempdata.artworkUrl100)
            }
        }
    }

    /* @desc auxilary method to help filter results
    *  @param vm, and collection of lists that'll add data */
    private fun helpFocusedFilter(vm: ViewModel,
        infolist: ArrayList<String>, artworklist: ArrayList<String>, copyrightlist: ArrayList<String>, weblist: ArrayList<String>
        , results: AppleApiRequest, key: String, caller: String){
        val rsp = Gson().fromJson<AppleApiModel>(results.mFeed,AppleApiModel::class.java)
        /* apple api has a different approach for HotTracks, therefore detect the type ViewModelHotTracks
        *  else continue execution on the generalized type
        *  when data is found add it to its respective list */
        if(caller == "ViewHotTracks"){
            /* perform iterations, 0(n) operations, due to list iteration */
            for(i in 0 until rsp.results.size()){
                val rspResponse = Gson().fromJson<AppleApiModelHotTrackResults>(
                    rsp.results.get(i), AppleApiModelHotTrackResults::class.java)
                if(rspResponse.name == key) {
                    infolist.add(rspResponse.name)
                    artworklist.add(rspResponse.artworkUrl100)
                    copyrightlist.add(rsp.copyright)
                    weblist.add(rspResponse.url)
                }
            }
        } else {
            /* perform nested iterations, 0(n^2) operations, due to nested list iteration */
            for(i in 0 until rsp.results.size()){
                val rspResponse = Gson().fromJson<AppleApiModelResults>(
                    rsp.results.get(i), AppleApiModelResults::class.java)
                for(j in 0 until rspResponse.genres.size()){
                    val rspResponseGenre = Gson().fromJson<AppleApiModeResultsGenres>(
                        rspResponse.genres.get(j), AppleApiModeResultsGenres::class.java)
                    if(rspResponse.artistName == key){
                        val tempstr =
                            "Artist: ${rspResponse.artistName} , Genre: ${rspResponseGenre.name} , Release: ${rspResponse.releaseDate}"
                        infolist.add(tempstr)
                        artworklist.add(rspResponse.artworkUrl100)
                        copyrightlist.add(rspResponse.copyright)
                        weblist.add(rspResponse.url)
                    }
                }
            }
        }
    }

    /* @desc fidget (move cautiously), auxilary method to pull image data and update its text
    *  @param activity as the caller, holder as the current view item
    *  ,  collection of lists for data, position as the current rv item, name as an identifier */
    fun fidgetOnCardView(activity: AppCompatActivity, holder: RecyclerViewAdapter.CardItemHolder
                                 , artlist: ArrayList<String>, namelist: ArrayList<String>, position: Int, imgid: Int, nameid: Int){
        Picasso.with(activity).load(artlist[position])
            .into(holder.itemView.findViewById<ImageView>(imgid))
        holder.itemView.findViewById<MaterialTextView>(nameid).text = namelist[position]
    }
}