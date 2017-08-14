package com.applications.whazzup.photomapp.mvp.models

import com.applications.whazzup.photomapp.jobs.UploadPhotoJob

class UploadPhotoModel: AbstractModel() {
    fun uploadAvatarOnServer(imageUri: String) {
        mJobManger.addJobInBackground(UploadPhotoJob(imageUri))
    }

    fun isUserAuth(): Boolean {
        return mDataManager.isUserAuth()
    }
}