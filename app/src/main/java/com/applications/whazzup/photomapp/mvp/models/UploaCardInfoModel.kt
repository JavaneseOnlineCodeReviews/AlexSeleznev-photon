package com.applications.whazzup.photomapp.mvp.models

import com.applications.whazzup.photomapp.data.network.req.card_info_req.CardInfoReq
import com.applications.whazzup.photomapp.data.network.res.UploadPhotoRes
import com.applications.whazzup.photomapp.data.network.res.photocard.PhotocardRes
import com.applications.whazzup.photomapp.jobs.EmptyJobManagerCallback
import com.applications.whazzup.photomapp.jobs.UploadCardJob
import com.birbit.android.jobqueue.Job
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.subjects.PublishSubject

class UploaCardInfoModel : AbstractModel(){

    var subj : PublishSubject<String> = PublishSubject.create()

    fun createPhotoCard(cardInfo : CardInfoReq) : Observable<UploadPhotoRes>{
        return mDataManager.createPhotoCard(cardInfo)
    }

    fun uploaCardToServer(cardInfo: CardInfoReq){
        mJobManger.addJobInBackground(UploadCardJob(cardInfo))
        mJobManger.addCallback(object : EmptyJobManagerCallback(){
            override fun onDone(job: Job) {
             subj.onNext("Фотокарточка успешно создана")
            }
        })

    }

    fun updateCardToServer(cardId : String, cardInfo: CardInfoReq) : Observable<UploadPhotoRes>{
        return mDataManager.updatePhotoCard(cardId, cardInfo)
    }

    fun getCardUrl(): String {
        return mDataManager.mPreferencesManager.getPhotoUrl()
    }
}