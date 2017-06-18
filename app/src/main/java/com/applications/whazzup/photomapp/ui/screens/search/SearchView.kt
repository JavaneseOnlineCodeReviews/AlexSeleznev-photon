package com.applications.whazzup.photomapp.ui.screens.search

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import butterknife.BindView
import com.applications.whazzup.photomapp.R
import com.applications.whazzup.photomapp.di.DaggerService
import com.applications.whazzup.photomapp.mvp.views.AbstractView
import kotlinx.android.synthetic.main.screen_search.view.*

class SearchView(context : Context, attrs : AttributeSet) : AbstractView<SearchScreen.SearchPresenter>(context, attrs) {

    @BindView(R.id.search_pager)
    lateinit var mViewPager: ViewPager

    override fun viewOnBackPressed(): Boolean {
        return false
    }

    override fun initDagger(context: Context) {
        DaggerService.getDaggerComponent<DaggerSearchScreen_SearchPresenterComponent>(context).inject(this)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        search_pager.adapter = SearchAdapterJava()
    }

//    override fun onAttachedToWindow() {
//        super.onAttachedToWindow()
//    }
}
