package com.applications.whazzup.photomapp.mvp.presenters

import android.view.MenuItem
import android.view.View

class MenuItemHolder(var itemTitle : CharSequence, var iconRes : Int, var listener : View.OnClickListener) {

    constructor(itemTitle: CharSequence,
                iconId: Int,
                listener: (View) -> Boolean) :
            this(itemTitle,
                    iconId,
                    View.OnClickListener { listener(it) })
}