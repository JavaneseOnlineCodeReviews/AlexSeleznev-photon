package com.applications.whazzup.photomapp.mvp.models

import com.applications.whazzup.photomapp.data.network.req.UserSigInReq
import com.applications.whazzup.photomapp.data.network.res.user.UserRes
import com.applications.whazzup.photomapp.data.storage.dto.PhotoCardDto
import io.reactivex.Observable


class RootModel : AbstractModel() {

    var cardList : List<PhotoCardDto> = ArrayList()

    fun addToCardList(cardDto:PhotoCardDto){
        (cardList as ArrayList).add(cardDto)
    }

    fun signUpUser(user : UserSigInReq) : Observable<UserRes>{
        return mDataManager.signUpUser(user);
    }
}
