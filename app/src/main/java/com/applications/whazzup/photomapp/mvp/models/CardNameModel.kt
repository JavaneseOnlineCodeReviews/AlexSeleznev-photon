package com.applications.whazzup.photomapp.mvp.models

import com.applications.whazzup.photomapp.data.storage.realm.TagRealm
import io.reactivex.rxkotlin.subscribeBy



class CardNameModel : AbstractModel() {

    lateinit var list : MutableList<TagRealm>
    fun getTagsFromDb() : List<TagRealm> {

        mRealmManager.getRecentlyTagList().subscribeBy(onNext = {list = it})
        return list
    }
}