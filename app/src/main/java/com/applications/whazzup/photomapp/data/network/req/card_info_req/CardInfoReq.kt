package com.applications.whazzup.photomapp.data.network.req.card_info_req

import java.io.Serializable


class CardInfoReq(var title : String, var album: String, var photo : String, var tags : MutableList<String>, var filters : CardInfoFilters) : Serializable{

}

