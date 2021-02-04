package interview.android.tuner.view.presenter

import android.content.Intent
import android.net.Uri
import interview.android.tuner.view.ViewFocused

/* @desc bounded action class for focused view that toggles listeners like buttons, mvp
* @param mView who calls this class
* @return web activity */
class PresenterTappedSingle(private val mActivity: ViewFocused): PresenterTuner() {

    /* action to start web activity */
    override fun fireOnPresenter() {
        val mWebIntent = Intent(Intent.ACTION_VIEW, Uri.parse(mActivity.mVmFocused.mModelFocused.mWebUrl[0]))
        mActivity.startActivity(mWebIntent)
    }
}