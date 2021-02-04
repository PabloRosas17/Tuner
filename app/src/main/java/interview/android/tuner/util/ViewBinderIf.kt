package interview.android.tuner.util

/* @desc contract for views capable of binding, all views in this case since mvvm architecture
* @param T type of the calling class */
interface ViewBinderIf<T> {

    /* T type of the calling class, associate the binding element */
    var mBinding: T

    /* method used to enforce bindings happen */
    fun fireUiBindings()
}