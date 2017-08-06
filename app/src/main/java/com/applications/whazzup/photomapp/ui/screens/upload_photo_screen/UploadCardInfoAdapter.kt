package com.applications.whazzup.photomapp.ui.screens.upload_photo_screen

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.applications.whazzup.photomapp.App
import com.applications.whazzup.photomapp.R
import com.applications.whazzup.photomapp.di.DaggerService
import com.applications.whazzup.photomapp.flow.AbstractScreen
import com.applications.whazzup.photomapp.ui.screens.search.SearchScreen
import com.applications.whazzup.photomapp.ui.screens.search.filter.FilterScreen
import com.applications.whazzup.photomapp.ui.screens.search.tag.TagScreen
import com.applications.whazzup.photomapp.ui.screens.upload_photo_screen.card_album.CardAlbumsScreen
import com.applications.whazzup.photomapp.ui.screens.upload_photo_screen.card_filters.CardFiltersScreen
import com.applications.whazzup.photomapp.ui.screens.upload_photo_screen.card_name.CardNameScreen
import mortar.MortarScope


class UploadCardInfoAdapter : PagerAdapter(){

    lateinit var screen: AbstractScreen<UploadCardInfoScreen.UploadCardInfoComponent>

    override fun isViewFromObject(view: View, `object`: Any?): Boolean {
        return view.equals(`object`)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        when (position) {
            0 -> screen = CardNameScreen()
            1 -> screen = CardFiltersScreen()
            2 -> screen = CardAlbumsScreen()
        }

        val screenScope = createScreenScopeFromContext(container.context, screen)
        val screenContext = screenScope?.createContext(container.context)

        val newView = LayoutInflater
                .from(screenContext)
                .inflate(screen!!.layoutResId, container, false)
        container.addView(newView)
        return newView
    }

    override fun getCount(): Int {
        return 3
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun getPageTitle(position: Int): CharSequence {
        when (position) {
            0 -> return App.applicationContext().getString(R.string.search)
            1 -> return App.applicationContext().getString(R.string.filters)
            else -> return "some test"
        }
    }

    private fun createScreenScopeFromContext(context: Context, screen: AbstractScreen<UploadCardInfoScreen.UploadCardInfoComponent>): MortarScope? {
        val parentScope = MortarScope.getScope(context)
        var childScope: MortarScope? = parentScope.findChild(screen.scopeName)

        if (childScope == null) {
            val screenComponent = screen?.createScreenComponent(parentScope.getService(DaggerService.SERVICE_NAME)) ?: throw IllegalStateException(" don`t create screen component for " + screen.scopeName)

            childScope = parentScope.buildChild()
                    .withService(DaggerService.SERVICE_NAME, screenComponent)
                    .build(screen.scopeName)
        }

        return childScope
    }
}
