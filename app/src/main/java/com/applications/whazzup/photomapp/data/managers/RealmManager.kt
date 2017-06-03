package com.applications.whazzup.photomapp.data.managers

import com.applications.whazzup.photomapp.data.network.res.PhotocardRes
import com.applications.whazzup.photomapp.data.storage.realm.PhotocardRealm
import io.realm.Realm

class RealmManager {
    fun savePhotocardResponseToRealm(photocard : PhotocardRes) {
        val realm = Realm.getDefaultInstance()

        val photocardRealm = PhotocardRealm(photocard)

        realm.executeTransaction { realm1 -> realm1.insertOrUpdate(photocardRealm) }
        realm.close()
    }
}
