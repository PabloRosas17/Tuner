package interview.android.tuner.view

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import interview.android.tuner.R
import interview.android.tuner.controller.adapter.CardViewFeedAdapter
import interview.android.tuner.controller.ViewModelTopAlbums
import interview.android.tuner.databinding.ViewTopAlbumsLayoutBinding
import interview.android.tuner.util.BtmNavIf
import interview.android.tuner.util.ViewBinderIf
import org.koin.androidx.viewmodel.ext.android.viewModel

/* @desc activity that will show titles top albums */
class ViewTopAlbums : AppCompatActivity(), ViewBinderIf<ViewTopAlbumsLayoutBinding>, BtmNavIf {

    /* view model by koin and dependency injection by constructor */
    private val mVmTopAlbums : ViewModelTopAlbums by viewModel()

    /* layout binding type */
    override lateinit var mBinding: ViewTopAlbumsLayoutBinding

    /* bottom navigation view */
    override lateinit var mBtmNavView: BottomNavigationView

    /* create lifecycle method */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.fireUiBindings()
    }

    /* ui will bind itself to layouts, for consistency */
    override fun fireUiBindings(){
        /* binding is generated through BR class, this will set the view through binding */
        this.mBinding = DataBindingUtil.setContentView(this, R.layout.view_top_albums_layout)

        /* execute pending bindings */
        mBinding.executePendingBindings()

        this.registerUi()
        this.subscribeUi()
        this.listeningObservers()
    }

    /* let the ui know about it's adapters */
    private fun subscribeUi() {
        this.mBinding.materialTopAlbumsRecyclerView.layoutManager = LinearLayoutManager(this)
        mBinding.materialTopAlbumsRecyclerView.adapter =
            CardViewFeedAdapter(
                this,
                mVmTopAlbums
            )
    }

    /* associate elements with listeners */
    private fun registerUi(){
        this.mBtmNavView = mBinding.btmNavViewTopAlbums
        mBtmNavView.setOnNavigationItemSelectedListener(this)
    }

    /* observer listening to for Observables (view model's model) */
    private fun listeningObservers(){
        val mData = Observer<String> { data ->
            mBinding.materialTopAlbumsRecyclerView.adapter?.notifyDataSetChanged()
        }
        this.mVmTopAlbums.mData.observe(this,mData)
    }

    /* handle navigation selection */
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_coming_soon -> { mTunerUtil.fireIntent(this,ViewComingSoon::class.java) }
            R.id.action_hot_tracks -> { mTunerUtil.fireIntent(this,ViewHotTracks::class.java) }
            R.id.action_new_releases -> { mTunerUtil.fireIntent(this,ViewNewReleases::class.java) }
            R.id.action_top_albums -> { }
            R.id.action_top_songs -> { mTunerUtil.fireIntent(this,ViewTopSongs::class.java) }
        }
        return true
    }

    /* remove back press option */
    override fun onBackPressed() {
        /* no operation causes to disable the back button, doing this for ux consistency */
    }
}