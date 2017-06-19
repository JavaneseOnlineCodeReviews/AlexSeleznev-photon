package com.applications.whazzup.photomapp.data.network.res


data class PhotocardRes(val id: String, val owner: String, val title: String, val photo:String,
                        val views: Int, val favorits: Int, val tags: List<String>, val active : Boolean, val filters: Map<String, String>){



    operator fun compareTo(o2: PhotocardRes?): Int {
       if(o2!=null){
           if(this.views>o2.views){
               return 1
           }else{
               return -1
           }
       }
        return -2
    }
}
