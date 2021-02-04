package interview.android.tuner.view

import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.textview.MaterialTextView
import com.squareup.picasso.Picasso
import interview.android.tuner.R
import interview.android.tuner.controller.ViewModelFocused
import interview.android.tuner.databinding.ViewFocusedLayoutBinding
import interview.android.tuner.view.presenter.PresenterTappedSingle
import interview.android.tuner.util.BtmNavIf
import interview.android.tuner.util.ViewBinderIf
import org.koin.androidx.viewmodel.ext.android.viewModel

/* @desc activity that will show titles on focused */
class ViewFocused : AppCompatActivity(), ViewBinderIf<ViewFocusedLayoutBinding>, BtmNavIf {

    /* view model by koin and dependency injection by constructor */
    val mVmFocused : ViewModelFocused by viewModel()

    /* layout binding type */
    override lateinit var mBinding: ViewFocusedLayoutBinding

    /* bottom navigation view */
    override lateinit var mBtmNavView: BottomNavigationView

    /* create lifecycle method */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val artist = intent.extras?.getString("mArtist")!!
        val caller: String = intent.extras?.getString("mCaller")!!
        mVmFocused.fire(this, caller, artist)
        this.fireUiBindings()
        this.registerUi()
        this.listeningObservers()
    }

    /* ui will bind itself to layouts, for consistency */
    override fun fireUiBindings(){
        /* binding is generated through BR class, this will set the view through binding */
        this.mBinding = DataBindingUtil.setContentView(this, R.layout.view_focused_layout)

        /* execute pending bindings */
        mBinding.executePendingBindings()
    }

    /* let the ui know about it's adapters */
    fun subscribeUi() {
        Picasso.with(this).load(mVmFocused.mModelFocused.mArtUrl[0]).into(this.findViewById<ImageView>(R.id.material_tv_focused_art_work))
        this.findViewById<MaterialTextView>(R.id.material_tv_focused_music_details).text = mVmFocused.mModelFocused.mInfo[0]
        this.findViewById<MaterialTextView>(R.id.material_tv_focused_copyright).text = mVmFocused.mModelFocused.mCopyright[0]
    }

    /* associate elements with listeners */
    private fun registerUi(){
        this.mBtmNavView = mBinding.btmNavViewFocused
        mBtmNavView.setOnNavigationItemSelectedListener(this)

        mBinding.materialBtnOnline.setOnClickListener {
            val temp =
                PresenterTappedSingle(this@ViewFocused)
            temp.fireOnPresenter()
        }
    }

    /* observer listening to for Observables (view model's model) */
    /* TODO: dedicate a ui element to call this observer */
    private fun listeningObservers(){
        val mTitle = Observer<String> { title ->
            mBinding.materialFocusedTitleTv.text = title
        }
        this.mVmFocused.mTitle.observe(this,mTitle)
    }

    /* handle navigation selection */
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_coming_soon -> { mTunerUtil.fireIntent(this,ViewComingSoon::class.java) }
            R.id.action_hot_tracks -> { mTunerUtil.fireIntent(this,ViewHotTracks::class.java) }
            R.id.action_new_releases -> { mTunerUtil.fireIntent(this,ViewNewReleases::class.java) }
            R.id.action_top_albums -> { mTunerUtil.fireIntent(this,ViewTopAlbums::class.java) }
            R.id.action_top_songs -> { mTunerUtil.fireIntent(this,ViewTopSongs::class.java) }
        }
        return true
    }

    /* remove back press option */
    override fun onBackPressed() {
        /* no operation causes to disable the back button, doing this for ux consistency */
    }
}