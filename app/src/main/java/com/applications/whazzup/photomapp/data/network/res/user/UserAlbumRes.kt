package com.applications.whazzup.photomapp.data.network.res.user

import com.applications.whazzup.photomapp.data.network.res.photocard.PhotocardRes

class UserAlbumRes(val owner: String, val title: String, val active: Boolean, val isFavorite : Boolean, val description: String, val photocards: List<PhotocardRes>, val id: String, val views: Int, val favorits: Int)