package com.applications.whazzup.photomapp.data.storage.realm

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class TagRealm() : RealmObject() {
@PrimaryKey
    var name: String? = null

    constructor(tag: String) : this() {
        name = tag
    }
}