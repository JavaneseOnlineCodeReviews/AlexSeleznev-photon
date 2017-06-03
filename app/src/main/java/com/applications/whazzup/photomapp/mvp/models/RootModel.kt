package com.applications.whazzup.photomapp.mvp.models

import com.applications.whazzup.photomapp.data.storage.dto.PhotoCardDto


class RootModel : AbstractModel() {

    var cardList : List<PhotoCardDto> = ArrayList()

    fun addToCardList(cardDto:PhotoCardDto){
        (cardList as ArrayList).add(cardDto)
    }

}
