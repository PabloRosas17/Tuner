package interview.android.tuner.util

/* @desc inheritors capable of presenting will contract this class to access it's member elements */
interface PresenterListenerBindingIf {

    /* initiate the presentation */
    fun fireOnPresenter()
}