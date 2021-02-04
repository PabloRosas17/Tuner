package interview.android.tuner.view.presenter

import interview.android.tuner.util.PresenterListenerBindingIf

/* @desc generalized presenter class to disable inheritance from ambiguous classes,
    each presenter should at the very least implement a fireOnPresenter action
* @return type PresenterTuner */
abstract class PresenterTuner : PresenterListenerBindingIf