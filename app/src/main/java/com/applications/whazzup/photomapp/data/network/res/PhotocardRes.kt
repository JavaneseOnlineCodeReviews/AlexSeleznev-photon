package com.applications.whazzup.photomapp.data.network.res


data class PhotocardRes(val id: String, val owner: String, val active: Boolean, val title: String, val photo:String,
                        val views: Int, val favorits: Int, val tags: List<String>, val filters: Map<String, String>) : Comparable<PhotocardRes>{


    override fun compareTo(other: PhotocardRes): Int {
        if(this.views < other.views){
            return 1
        }else if(this.views == other.views){
            return 0
        }else{
            return -1
        }
    }

}
