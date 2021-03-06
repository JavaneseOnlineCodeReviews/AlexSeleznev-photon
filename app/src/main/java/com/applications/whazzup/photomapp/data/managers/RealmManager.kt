package com.applications.whazzup.photomapp.data.managers

import com.applications.whazzup.photomapp.data.network.res.photocard.PhotocardRes
import com.applications.whazzup.photomapp.data.network.res.user.UserAlbumRes
import com.applications.whazzup.photomapp.data.storage.realm.PhotocardRealm
import com.applications.whazzup.photomapp.data.storage.realm.TagRealm
import com.applications.whazzup.photomapp.data.storage.realm.UserAlbumRealm
import com.milkmachine.rxjava2interop.toV2Observable
import io.reactivex.Observable
import io.realm.Case
import io.realm.Realm
import io.realm.RealmObject
import io.realm.RealmResults



class RealmManager {

    lateinit var  mRealmInstance : Realm

    fun savePhotocardResponseToRealm(photoСard: PhotocardRes) {
        val realm = Realm.getDefaultInstance()

        val photoCardRealm = PhotocardRealm(photoСard)

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
        val realm = Realm.getDefaultInstance()
        var managedAlbum = realm.where(UserAlbumRealm :: class.java).findAllAsync()
        return  managedAlbum.asObservable().toV2Observable().filter { it.isLoaded }.firstElement().flatMapObservable{Observable.fromIterable(it)}
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

    fun clearUserAlbum() {
        val realm = Realm.getDefaultInstance()
        realm.executeTransaction { realm -> realm.where(UserAlbumRealm :: class.java).findAll().deleteAllFromRealm() }
        realm.close()
    }

    fun getAllCardFromRealm(): Observable<PhotocardRealm>{
        val realm = Realm.getDefaultInstance()
        var managedCard = realm.where(PhotocardRealm::class.java).findAllAsync()
        return managedCard.asObservable().toV2Observable().filter { it.isLoaded }.firstElement().flatMapObservable { Observable.fromIterable(it) }.sorted { o1, o2 -> compareValues(o1,o2) }
    }

    fun filter(dishList: MutableList<String>, colorList: MutableList<String>, decorList: MutableList<String>, temperatureList: MutableList<String>, lighrCountList: MutableList<String>, lightDirectionList: MutableList<String>, lightList: MutableList<String>) {
        val realm = Realm.getDefaultInstance()
        println(dishList + ", " + temperatureList)
        val managedCard = realm.where(PhotocardRealm :: class.java).`in`("dish", dishList.toTypedArray()).`in`("temperature", temperatureList.toTypedArray()).findAll()
        for(filter in colorList){
            var mo = managedCard.where().contains("nuances", filter).findAll()
            managedCard.addAll(mo)
        }
        println(managedCard)
    }

    fun getSearchCardFromRealm(nameList : List<String>): Observable<PhotocardRealm>? {
        val realm = Realm.getDefaultInstance()
        var photos = realm.where(PhotocardRealm :: class.java)

        /*var list = mutableListOf<PhotocardRealm>()

        for(name in nameList){
            var mo = realm.where(PhotocardRealm :: class.java).contains("searchTitle", name.toUpperCase()).findAll()
            list.addAll(mo)
        }*/

       /* var managedCard = realm.where(PhotocardRealm :: class.java).`in`("title", nameList.toTypedArray()).findAll()
        var mo = realm.where(PhotocardRealm :: class.java).contains("searchTitle", "ФРУКТОВЫЙ САЛАТ").findAll()
        list.addAll(mo)
        list.addAll(managedCard)*/
        return null //Observable.fromIterable(list) //managedCard.asObservable().toV2Observable().filter { it.isLoaded }.firstElement().flatMapObservable { Observable.fromIterable(it) }
    }
}
