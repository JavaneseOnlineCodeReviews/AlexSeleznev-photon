package com.applications.whazzup.photomapp.data.network.req

import java.io.Serializable


class CardInfoReq(var title : String, var album: String, var photo : String, var tags : MutableList<String>, var filters : MutableMap<String, String>) : Serializable{

}

