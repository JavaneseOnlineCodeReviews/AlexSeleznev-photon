package com.applications.whazzup.photomapp.data.storage.dto

import com.applications.whazzup.photomapp.data.network.res.photocard.PhotocardRes
import com.applications.whazzup.photomapp.data.storage.realm.PhotocardRealm
import com.applications.whazzup.photomapp.data.storage.realm.TagRealm


class PhotoCardDto(){

    var id :String=""
    var owner : String = ""
    var title :String = ""
    var photo : String = ""
    var views = 0
    var favorites = 0
    var tags = mutableListOf<String>()
    //var filters = cardRes.filters

    constructor(cardRealm : PhotocardRealm) : this() {
        this.id = cardRealm.id
        this.owner = cardRealm.owner
        this.title = cardRealm.title
        this.photo = cardRealm.photo
        this.views = cardRealm.views
        this.favorites = cardRealm.favorits
        for(tag in cardRealm.tags){
            this.tags.add(tag.name!!)
        }

    }

    constructor(cardRes : PhotocardRes) : this(){
        this.id = cardRes.id
        this.owner=cardRes.owner
        this.title = cardRes.owner
        this.photo = cardRes.photo
        this.views = cardRes.views
        this.favorites = cardRes.favorits
        this.tags = cardRes.tags as MutableList<String>
    }

}