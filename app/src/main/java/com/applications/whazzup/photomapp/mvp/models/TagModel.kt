package com.applications.whazzup.photomapp.mvp.models

import com.applications.whazzup.photomapp.data.storage.realm.TagRealm
import io.reactivex.Observable
import io.realm.RealmResults


class TagModel : AbstractModel() {

    fun getTagList() : Observable<List<String>> {
        return mDataManager.getTagsObs()
    }

    fun saveTagRealm(tagSearch: String) {
        mDataManager.mRealmManager.saveTagSearchToRealm(tagSearch)
    }

    fun getRecentlyTagList(): Observable<RealmResults<TagRealm>> {
        return mDataManager.mRealmManager.getRecentlyTagList()
    }
}