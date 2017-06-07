package com.applications.whazzup.photomapp.mvp.views

import android.support.v4.view.ViewPager
import com.applications.whazzup.photomapp.mvp.presenters.MenuItemHolder

interface IActionBarView {

     fun setActionBarTitle(title: CharSequence?)
     fun setActionBarVisible(visible: Boolean)
     fun setBackArrow(enable: Boolean)
     fun setMenuItem(items: List<MenuItemHolder>)

     fun setTabLayout(viewPager: ViewPager?)

     fun removeTabLayout()
}