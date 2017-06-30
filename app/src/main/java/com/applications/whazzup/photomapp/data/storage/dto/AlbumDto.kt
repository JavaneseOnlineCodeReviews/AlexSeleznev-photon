package com.applications.whazzup.photomapp.data.storage.dto

import com.applications.whazzup.photomapp.data.network.res.user.UserAlbumRes

class AlbumDto() {
    var owner : String = ""
    var title : String = ""
    var active : Boolean = false
    var description : String = ""
    var photoCards : MutableList<PhotoCardDto> = mutableListOf()
    var id : String = ""
    var views : Int = 0
    var favorites : Int = 0
    var isFavorite : Boolean = false

    constructor(album : UserAlbumRes) : this() {
        var owner= album.owner
        var title= album.title
        var active= album.active
        var description= album.description
        for(item in album.photocards){
            photoCards.add(PhotoCardDto(item))
        }
        var id= album.id
        var views = album.views
        var favorites= album.favorits
        var isFavorite= album.isFavorite
    }
}