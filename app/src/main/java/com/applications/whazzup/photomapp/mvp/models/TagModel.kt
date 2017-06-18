package com.applications.whazzup.photomapp.mvp.models

import io.reactivex.Observable


class TagModel : AbstractModel() {

    fun getTagList() : Observable<List<String>> {
        return mDataManager.getTagsObs();
    }
}