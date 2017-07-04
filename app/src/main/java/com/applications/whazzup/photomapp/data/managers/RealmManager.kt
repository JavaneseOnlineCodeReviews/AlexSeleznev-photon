package com.applications.whazzup.photomapp.data.managers

import com.applications.whazzup.photomapp.data.network.res.PhotocardRes
import com.applications.whazzup.photomapp.data.network.res.user.UserAlbumRes
import com.applications.whazzup.photomapp.data.storage.realm.PhotocardRealm
import com.applications.whazzup.photomapp.data.storage.realm.UserAlbumRealm
import com.milkmachine.rxjava2interop.toV2Observable
import io.reactivex.Observable
import io.realm.Realm
import io.realm.RealmObject
import io.realm.RealmResults



class RealmManager {

    lateinit var  mRealmInstance : Realm

    fun savePhotocardResponseToRealm(photoСard: PhotocardRes) {
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

    fun saveAlbumToRealm(it: UserAlbumRes) {
        val realm = Realm.getDefaultInstance()

        val albumRealm = UserAlbumRealm(it)

        realm.executeTransaction { realm -> realm.insertOrUpdate(albumRealm) }
        realm.close()
    }

    fun getAllAlbumFromRealm() : Observable<UserAlbumRealm>{
        var managedAlbum = getQueryRealmInstance().where(UserAlbumRealm :: class.java).findAllAsync()
        return  managedAlbum.asObservable().toV2Observable().filter { it.isLoaded }.flatMap {Observable.fromIterable(it)}
    }

    private fun getQueryRealmInstance(): Realm {
        if (mRealmInstance == null || mRealmInstance.isClosed()) {
            mRealmInstance = Realm.getDefaultInstance()
        }
        return mRealmInstance
    }

    internal fun deleteFromRealm(entityRealmClass: Class<out RealmObject>, id: String) {
        val realm = Realm.getDefaultInstance()
        val entity = realm.where(entityRealmClass).equalTo("id", id).findFirst()  //находим запись по идентификатору
        if (entity != null) {
            realm.executeTransaction { realm1 -> entity.deleteFromRealm() }  //Удаляем сущность из БД
            realm.close()
        }
    }
}
