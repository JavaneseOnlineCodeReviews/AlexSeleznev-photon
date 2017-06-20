package com.applications.whazzup.photomapp.data.storage.realm

import com.applications.whazzup.photomapp.data.network.res.PhotocardRes
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class PhotocardRealm() : RealmObject() {
    @PrimaryKey
    var id: String = ""
    var owner: String = ""
    var title: String = ""
    var photo:String = ""
    var views: Int = 0
    var favorits: Int = 0
    var tags: RealmList<TagRealm> = RealmList()

    constructor(photocard: PhotocardRes) : this() {
        id = photocard.id
        owner = photocard.owner
        title = photocard.title
        photo = photocard.photo
        views = photocard.views
        favorits = photocard.favorits
        for (tag in photocard.tags) {
            tags.add(TagRealm(tag))
        }
    }
}