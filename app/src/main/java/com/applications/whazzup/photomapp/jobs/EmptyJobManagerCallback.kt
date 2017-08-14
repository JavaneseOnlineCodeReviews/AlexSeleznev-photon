package com.applications.whazzup.photomapp.jobs

import com.birbit.android.jobqueue.Job
import com.birbit.android.jobqueue.callback.JobManagerCallback


abstract class EmptyJobManagerCallback : JobManagerCallback {
    override fun onJobRun(job: Job, resultCode: Int){

    }

    abstract override fun onDone(job: Job)

     override fun onAfterJobRun(job: Job, resultCode: Int) {

    }

     override fun onJobCancelled(job: Job, byCancelRequest: Boolean, throwable: Throwable?) {

    }

     override fun onJobAdded(job: Job) {

    }
}