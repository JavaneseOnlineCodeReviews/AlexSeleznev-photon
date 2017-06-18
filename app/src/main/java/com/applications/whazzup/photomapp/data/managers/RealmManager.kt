package com.applications.whazzup.photomapp.data.managers

import com.applications.whazzup.photomapp.data.network.res.PhotocardRes
import com.applications.whazzup.photomapp.data.storage.realm.PhotoCardRealm
import com.applications.whazzup.photomapp.data.storage.realm.TagRealm
import io.reactivex.Observable
import io.realm.Realm
import io.realm.RealmResults



class RealmManager {

    fun savePhotoCardResponseToRealm(photoCard: PhotocardRes) {
        val realm = Realm.getDefaultInstance()

        val photoCardRealm = PhotoCardRealm(photoCard)

        realm.executeTransaction { realm1 -> realm1.insertOrUpdate(photoCardRealm) }
        realm.close()
    }

    fun saveTagSearchToRealm(tagSearch: String) {
        val realm = Realm.getDefaultInstance()

        val tagSearchRealm = TagRealm(tagSearch)

        realm.executeTransaction { realm1 -> realm1.insertOrUpdate(tagSearchRealm) }
        realm.close()
    }

    fun getRecentlyTagList(): Observable<RealmResults<TagRealm>> {
        val realm = Realm.getDefaultInstance()

        var tagList: RealmResults<TagRealm> = realm.where(TagRealm::class.java).findAll()
        return Observable.just(tagList)
    }
}
