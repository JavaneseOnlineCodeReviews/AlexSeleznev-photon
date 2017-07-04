package com.applications.whazzup.photomapp.data.storage.realm

import com.applications.whazzup.photomapp.data.network.res.PhotocardRes
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by VZ on 02.06.2017.
 */

open class PhotocardRealm() : RealmObject() {
    @PrimaryKey
    var id: String = ""
    var owner: String = ""
    var title: String = ""
    var photo:String = ""
    var views: Int = 0
    var favorits: Int = 0
    var active : Boolean = false
    var tags: RealmList<TagRealm> = RealmList()

    constructor(photoCard: PhotocardRes) : this() {
        id = photoCard.id
        owner = photoCard.owner
        title = photoCard.title
        photo = photoCard.photo
        views = photoCard.views
        active = photoCard.active
        favorits = photoCard.favorits
        for (tag in photoCard.tags) {
            tags.add(TagRealm(tag))
        }
    }
}