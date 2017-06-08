package com.applications.whazzup.photomapp.data.network.res.user

class UserRes(val id : String, val name: String, val login: String, val albums: List<UserAlbumRes>, val token: String) {
}