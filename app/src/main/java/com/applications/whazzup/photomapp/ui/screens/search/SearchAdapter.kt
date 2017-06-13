package com.applications.whazzup.photomapp.ui.screens.search

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.applications.whazzup.photomapp.di.DaggerService
import com.applications.whazzup.photomapp.flow.AbstractScreen
import com.applications.whazzup.photomapp.ui.activities.RootActivity
import com.applications.whazzup.photomapp.ui.screens.search.filter.FilterScreen
import mortar.MortarScope

class SearchAdapter : PagerAdapter() {

    override fun isViewFromObject(view: View?, `object`: Any?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun instantiateItem(container: ViewGroup?, position: Int): Any {
        var screen: AbstractScreen<RootActivity.RootComponent>? = null

        when (position) {
            0 -> screen = FilterScreen()
            1 -> screen = FilterScreen()
        }

        val screenScope = createScreenScopeFromContext(container.getContext(), screen)
        val screenContext = screenScope.createContext(container.getContext())

        val newView = LayoutInflater
                .from(screenContext)
                .inflate(screen!!.getLayoutResId(), container, false)
        container.addView(newView)
        return newView
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence {
        when(position) {
            0 -> return "Поиск"
            1 -> return "Фильтры"
        }
        return ""
    }

    private fun createScreenScopeFromContext(context: Context, screen: AbstractScreen<RootActivity.RootComponent>?): MortarScope {
        val parentScope = MortarScope.getScope(context)
        var childScope: MortarScope = parentScope.findChild(screen.getScopeName())

        if (childScope == null) {
            val screenComponent = screen.createScreenComponent(parentScope.getService(DaggerService.SERVICE_NAME)) ?: throw IllegalStateException(" don`t create screen component for " + screen.getScopeName())

            childScope = parentScope.buildChild()
                    .withService(DaggerService.SERVICE_NAME, screenComponent)
                    .build(screen.getScopeName())
        }

        return childScope
    }
}