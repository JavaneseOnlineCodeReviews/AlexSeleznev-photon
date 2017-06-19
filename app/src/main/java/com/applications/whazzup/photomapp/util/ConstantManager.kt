package com.applications.whazzup.photomapp.util

object ConstantManager {
    val MAX_CONNECTION_TIMEOUT = 5000
    val MAX_READ_TIMEOUT = 5000
    val MAX_WRITE_TIMEOUT = 5000

    val USER_NAME_KEY = "USER_NAME_KEY"
    val USER_LOGIN_KEY = "USER_LOGIN_KEY"
    val USER_TOKEN_KEY = "USER_TOKEN_KEY"
    val USER_ID_KEY = "USER_ID_KEY"
    val USER_AVATAR_KEY = "USER_AVATAR_KEY"

    val REQUEST_PERMISSION_READ_EXTERNAL_STORAGE = 3001
    val REQUEST_PERMISSON_CAMERA = 3000


    val REQUEST_PROFILE_PHOTO = 1001
    val REQUEST_PROFILE_PHOTO_CAMERA = 1002
    
    val FILE_PROVIDER_AUTHORITY = "com.applications.whazzup.photomapp"
    val INITIAL_BACK_OFF_IN_MS: Long = 1000
    val MIN_CONSUMER_COUNT = 1
    val MAX_CONSUMER_COUNT = 3
    val LOAD_FACTOR = 3
    val KEEP_ALIVE = 120
}
