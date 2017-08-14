package com.applications.whazzup.photomapp.jobs

import com.applications.whazzup.photomapp.data.managers.DataManager
import com.applications.whazzup.photomapp.data.network.req.card_info_req.CardInfoReq
import com.applications.whazzup.photomapp.util.ConstantManager
import com.birbit.android.jobqueue.Job
import com.birbit.android.jobqueue.Params
import com.birbit.android.jobqueue.RetryConstraint
import io.reactivex.rxkotlin.subscribeBy


class UploadCardJob(var userInfo : CardInfoReq) : Job(Params(JobPriority.HIGH).requireNetwork().groupBy("uploadPhoto").persist()) {


    override fun onRun() {
        DataManager.INSTANCE.createPhotoCard(userInfo).subscribeBy(onError = {})
    }

    override fun shouldReRunOnThrowable(throwable: Throwable, runCount: Int, maxRunCount: Int): RetryConstraint {
        return RetryConstraint.createExponentialBackoff(runCount, ConstantManager
                .INITIAL_BACK_OFF_IN_MS)
    }

    override fun onAdded() {

    }

    override fun onCancel(cancelReason: Int, throwable: Throwable?) {

    }
}