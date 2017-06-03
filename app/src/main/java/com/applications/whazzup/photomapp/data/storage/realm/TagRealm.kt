package com.applications.whazzup.photomapp.data.storage.realm

import io.realm.RealmObject

/**
 * Created by VZ on 02.06.2017.
 */

open class TagRealm() : RealmObject() {

    var name: String? = null

    constructor(tag: String) : this() {
        name = tag
    }
}