package interview.android.tuner.util

import kotlinx.coroutines.Job

/* @desc inheritors capable of coroutine will contract this class to follow proper execution cycles*/
interface CoroutineWorkerIf{

    /* job instance to represent the coroutine */
    var mWorker: Job

    /* lifecycle method to start the coroutine */
    fun fireCoroutineWorker()

    /* coroutine method that will perform asynchronous preemptive multitasking */
    suspend fun popFrozenTime()

    /* lifecycle method to end the coroutine */
    fun onDestroy()
}
