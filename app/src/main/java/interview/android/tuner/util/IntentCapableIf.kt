package interview.android.tuner.util

/* @desc inheritors capable of intents will contract this class to access it's member elements */
interface IntentCapableIf {

    /* instance method */
    val mTunerUtil: TunerUtil
        get() = TunerUtil()
}