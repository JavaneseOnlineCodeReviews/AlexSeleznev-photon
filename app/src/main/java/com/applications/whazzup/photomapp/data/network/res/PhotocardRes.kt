package com.applications.whazzup.photomapp.data.network.res

data class PhotocardRes(val id: String, val owner: String, val title: String, val photo:String,
                        val views: Int, val favorits: Int, val tags: List<String>, val filters: Map<String, String>)