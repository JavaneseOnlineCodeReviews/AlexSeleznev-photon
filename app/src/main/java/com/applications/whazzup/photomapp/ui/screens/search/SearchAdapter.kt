package com.applications.whazzup.photomapp.ui.screens.search

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.applications.whazzup.photomapp.di.DaggerService
import com.applications.whazzup.photomapp.flow.AbstractScreen
import com.applications.whazzup.photomapp.ui.screens.search.filter.FilterScreen
import mortar.MortarScope

class SearchAdapter : PagerAdapter() {

    lateinit var screen: AbstractScreen<*>


    override fun isViewFromObject(view: View, `object`: Any?): Boolean {
        return view.equals(`object`)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        when (position) {
            0 -> screen = FilterScreen()
//            1 -> screen = FilterScreen()
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
        return 1
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun getPageTitle(position: Int): CharSequence {
        when (position) {
            0 -> return "Поиск"
            1 -> return "Фильтры"
        }
        return ""
    }

    private fun createScreenScopeFromContext(context: Context, screen: AbstractScreen<*>): MortarScope? {
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