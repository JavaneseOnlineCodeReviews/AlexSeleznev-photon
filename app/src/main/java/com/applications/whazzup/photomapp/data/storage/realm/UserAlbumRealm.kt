package com.applications.whazzup.photomapp.data.storage.realm

import com.applications.whazzup.photomapp.data.network.res.user.UserAlbumRes
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class UserAlbumRealm() : RealmObject() {
    @PrimaryKey
    var albumId : String = ""
    var owner : String = ""
    var title : String = ""
    var preview : String = ""
    var description : String = ""
    var views : Int = 0
    var active = false
    var favorites : Int = 0
    var photoCards : RealmList<PhotocardRealm> = RealmList()

    constructor(album : UserAlbumRes) : this() {
        albumId = album.id
        owner = album.owner
        title = album.title
        for(item in album.photocards){
            if(item.active){
                preview = item.photo
                break
            }
        }
        active = album.active
        description = album.description
        views = album.views
        favorites = album.favorits
        for(item in album.photocards){
            photoCards.add(PhotocardRealm(item))
        }


    }
}