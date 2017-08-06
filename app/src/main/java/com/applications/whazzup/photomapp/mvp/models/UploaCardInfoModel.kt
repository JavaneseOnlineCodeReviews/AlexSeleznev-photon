package com.applications.whazzup.photomapp.mvp.models

import com.applications.whazzup.photomapp.data.network.req.CardInfoReq
import com.applications.whazzup.photomapp.data.network.res.UploadPhotoRes
import com.applications.whazzup.photomapp.jobs.UploadCardJob
import io.reactivex.Observable

class UploaCardInfoModel : AbstractModel(){

    fun createPhotoCard(cardInfo : CardInfoReq) : Observable<UploadPhotoRes>{
        return mDataManager.createPhotoCard(cardInfo)
    }

    fun uploaCardToServer(cardInfo: CardInfoReq){
        mJobManger.addJobInBackground(UploadCardJob(cardInfo))
    }

    fun getCardUrl(): String {
        return mDataManager.mPreferencesManager.getPhotoUrl()
    }
}