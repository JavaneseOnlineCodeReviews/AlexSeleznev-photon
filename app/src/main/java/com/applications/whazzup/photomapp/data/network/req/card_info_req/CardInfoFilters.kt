package com.applications.whazzup.photomapp.data.network.req.card_info_req

import java.io.Serializable

data class CardInfoFilters(val dish : String, val nuances : List<String>,
                           val decor : String, val temperature : String, val light : String,
                           val lightDirection : String, val lightSource : String) : Serializable
