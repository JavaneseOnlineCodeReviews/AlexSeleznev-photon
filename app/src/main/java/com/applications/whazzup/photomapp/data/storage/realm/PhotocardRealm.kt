package com.applications.whazzup.photomapp.data.storage.realm

import com.applications.whazzup.photomapp.data.network.res.photocard.PhotocardRes
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey


open class PhotocardRealm() : RealmObject(), Comparable<PhotocardRealm> {


    @PrimaryKey
    var id: String = ""
    var owner: String = ""
    var title: String = ""
    var searchTitle = ""
    var photo:String = ""
    var views: Int = 0
    var favorits: Int = 0
    var active : Boolean = false
    var tags: RealmList<TagRealm> = RealmList()
    var dish : String =""
    var nuances : String = ""
    var temperature : String = ""

    constructor(photoCard: PhotocardRes) : this() {
        id = photoCard.id
        owner = photoCard.owner
        title = photoCard.title
        searchTitle = photoCard.title.toUpperCase()
        photo = photoCard.photo
        views = photoCard.views
        active = photoCard.active
        favorits = photoCard.favorits
        for (tag in photoCard.tags) {
            tags.add(TagRealm(tag))
        }
        dish = photoCard.filters.dish
        nuances = photoCard.filters.nuances
        temperature = photoCard.filters.temperature
    }
    override fun compareTo(other: PhotocardRealm): Int {
        if(this.views < other.views){
            return 1
        }else if(this.views == other.views){
            return 0
        }else{
            return -1
        }
    }
}