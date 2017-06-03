package com.applications.whazzup.photomapp.data.storage.dto

import com.applications.whazzup.photomapp.data.network.res.PhotocardRes


class PhotoCardDto(cardRes: PhotocardRes) {
    var id :String=cardRes.id
    var owner : String = cardRes.owner
    var title :String = cardRes.title
    var photo = cardRes.photo
    var views =cardRes.views
    var favorites = cardRes.favorits
    var tags = cardRes.tags
    var filters = cardRes.filters

}