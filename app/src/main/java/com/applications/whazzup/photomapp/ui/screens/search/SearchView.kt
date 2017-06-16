package com.applications.whazzup.photomapp.ui.screens.search

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.util.AttributeSet
import com.applications.whazzup.photomapp.di.DaggerService
import com.applications.whazzup.photomapp.mvp.views.AbstractView
import kotlinx.android.synthetic.main.screen_search.view.*

class SearchView(context : Context, attrs : AttributeSet) : AbstractView<SearchScreen.SearchPresenter>(context, attrs) {
    override fun viewOnBackPressed(): Boolean {
        return false
    }

    override fun initDagger(context: Context) {
        DaggerService.getDaggerComponent<DaggerSearchScreen_SearchPresenterComponent>(context).inject(this)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        search_pager.adapter = SearchAdapter()
    }
}
