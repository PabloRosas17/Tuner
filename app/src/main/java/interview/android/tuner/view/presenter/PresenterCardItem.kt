package interview.android.tuner.view.presenter

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textview.MaterialTextView
import interview.android.tuner.R
import interview.android.tuner.view.*

/* @desc bounded action class for card views who decide to present their duties through mvp
* @param mView who calls this class, position of item clicked
* @return focused activity */
class PresenterCardItem(private val mView: View, private val position: Int): PresenterTuner() {

    /* action to start focused activity, requires an artist name as a key and a caller type as a detector */
    override fun fireOnPresenter() {
        val mIntent = Intent(this.mView.context, ViewFocused::class.java)
        this.fireCheckSetActivityType(this.mView.context,mIntent)
        mIntent.putExtra("mArtist",this.mView.findViewById<MaterialTextView>(R.id.material_tv_card_item_artist_name).text.toString())
        this.mView.context.startActivity(mIntent)
        (this.mView.context as AppCompatActivity).finish()
    }

    /* auxilary function to determines the type of the calling intent */
    private fun fireCheckSetActivityType(ctx: Context, mIntent: Intent){
        when(ctx) {
            is ViewComingSoon -> { mIntent.putExtra("mCaller", ViewComingSoon::class.java.simpleName) }
            is ViewHotTracks -> { mIntent.putExtra("mCaller", ViewHotTracks::class.java.simpleName) }
            is ViewNewReleases -> { mIntent.putExtra("mCaller", ViewNewReleases::class.java.simpleName) }
            is ViewTopAlbums -> { mIntent.putExtra("mCaller", ViewTopAlbums::class.java.simpleName) }
            is ViewTopSongs -> { mIntent.putExtra("mCaller", ViewTopSongs::class.java.simpleName) }
            else -> { mIntent.putExtra("mCaller","null")}
        }
    }
}