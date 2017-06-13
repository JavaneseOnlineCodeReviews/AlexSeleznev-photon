package com.applications.whazzup.photomapp.data.network.res.user

class UserRes(val id : String, val name: String, val login: String, var albums: List<UserAlbumRes>, val token: String) {
}