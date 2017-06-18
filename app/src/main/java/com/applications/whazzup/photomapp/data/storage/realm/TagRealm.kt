package com.applications.whazzup.photomapp.data.storage.realm

import io.realm.RealmObject

open class TagRealm() : RealmObject() {

    var name: String? = null

    constructor(tag: String) : this() {
        name = tag
    }
}