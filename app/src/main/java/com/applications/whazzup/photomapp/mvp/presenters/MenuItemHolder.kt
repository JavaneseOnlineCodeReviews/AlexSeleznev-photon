package com.applications.whazzup.photomapp.mvp.presenters

import android.view.MenuItem

class MenuItemHolder(var itemTitle : CharSequence, var iconRes : Int, var listener : MenuItem.OnMenuItemClickListener) {

    constructor(itemTitle: CharSequence,
                iconId: Int,
                listener: (MenuItem) -> Boolean) :
            this(itemTitle,
                    iconId,
                    MenuItem.OnMenuItemClickListener { listener(it) })
}