package com.applications.whazzup.photomapp.data.network.res.user

import com.applications.whazzup.photomapp.data.network.res.PhotocardRes

class UserAlbumRes(val owner: String, val title: String, val description: String, val photocards: List<PhotocardRes>, val id: String, val views: Int, val favorits: Int) {
}