package interview.android.tuner.controller.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import interview.android.tuner.R
import interview.android.tuner.controller.*
import interview.android.tuner.view.*
import java.lang.RuntimeException

/* @desc inherit and implement item count and renderable view based on the types associated with the call
 * @param mActivity is the activity type calling this, mViewModel is the viewmodel type calling this
 * @return attached card view adapter knowing its item count and renderable view */
class CardViewFeedAdapter(private val mActivity: AppCompatActivity, private val mViewModel: ViewModel) : RecyclerViewAdapter(mViewModel) {

    /* determines the amount of items a card view will generate based on the viewmodel model*/
    override fun getItemCount(): Int {
        when(mViewModel) {
            is ViewModelComingSoon -> { return mViewModel.mModelComingSoon.mArtists.size }
            is ViewModelHotTracks -> { return mViewModel.mModelHotTracks.mArtists.size }
            is ViewModelNewReleases -> { return mViewModel.mModelNewReleases.mArtists.size }
            is ViewModelTopAlbums -> { return mViewModel.mModelTopAlbums.mArtists.size }
            is ViewModelTopSongs -> { return mViewModel.mModelTopSongs.mArtists.size }
            else -> { throw RuntimeException("RecyclerViewAdapter.kt, getItemCount()")}
        }
    }

    /* determines how the view will render once it's been type detected and bounded to it's respective card item */
    override fun onBindViewHolder(holder: CardItemHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        when(mViewModel) {
            is ViewModelComingSoon -> {
                (mActivity as ViewComingSoon).mTunerUtil.fidgetOnCardView(mActivity
                    , holder, mViewModel.mModelComingSoon.mArtUrl, mViewModel.mModelComingSoon.mArtists
                        , position, R.id.material_tv_card_item_artwork, R.id.material_tv_card_item_artist_name)
            }
            is ViewModelHotTracks -> {
                (mActivity as ViewHotTracks).mTunerUtil.fidgetOnCardView(mActivity
                    , holder, mViewModel.mModelHotTracks.mArtUrl, mViewModel.mModelHotTracks.mArtists
                    , position, R.id.material_tv_card_item_artwork, R.id.material_tv_card_item_artist_name)
            }
            is ViewModelNewReleases -> {
                (mActivity as ViewNewReleases).mTunerUtil.fidgetOnCardView(mActivity
                    , holder, mViewModel.mModelNewReleases.mArtUrl, mViewModel.mModelNewReleases.mArtists
                    , position, R.id.material_tv_card_item_artwork, R.id.material_tv_card_item_artist_name)
            }
            is ViewModelTopAlbums -> {
                (mActivity as ViewTopAlbums).mTunerUtil.fidgetOnCardView(mActivity
                    , holder, mViewModel.mModelTopAlbums.mArtUrl, mViewModel.mModelTopAlbums.mArtists
                    , position, R.id.material_tv_card_item_artwork, R.id.material_tv_card_item_artist_name)
            }
            is ViewModelTopSongs -> {
                (mActivity as ViewTopSongs).mTunerUtil.fidgetOnCardView(mActivity
                    , holder, mViewModel.mModelTopSongs.mArtUrl, mViewModel.mModelTopSongs.mArtists
                    , position, R.id.material_tv_card_item_artwork, R.id.material_tv_card_item_artist_name)
            }
            else -> { throw RuntimeException("RecyclerViewAdapter.kt, getItemCount()")
            }
        }
    }
}