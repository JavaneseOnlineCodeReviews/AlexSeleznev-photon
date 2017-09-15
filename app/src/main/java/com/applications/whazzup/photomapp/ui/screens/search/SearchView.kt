package com.applications.whazzup.photomapp.ui.screens.search

import android.content.Context
import android.support.v4.view.ViewPager

import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.Button
import butterknife.BindView
import butterknife.OnClick
import com.applications.whazzup.photomapp.R
import com.applications.whazzup.photomapp.data.storage.dto.FiltersDto
import com.applications.whazzup.photomapp.di.DaggerService
import com.applications.whazzup.photomapp.mvp.views.AbstractView
import com.applications.whazzup.photomapp.ui.screens.filtered_photo_card_list.FilteredPhotoCardListScreen
import com.applications.whazzup.photomapp.ui.screens.search.filter.FilterScreen
import com.applications.whazzup.photomapp.ui.screens.search.filter.FilterView
import com.applications.whazzup.photomapp.ui.screens.search.tag.TagScreen
import com.applications.whazzup.photomapp.ui.screens.search.tag.TagView
import flow.Flow
import kotlinx.android.synthetic.main.screen_search.view.*

class SearchView(context : Context, attrs : AttributeSet) : AbstractView<SearchScreen.SearchPresenter>(context, attrs), ViewPager.OnPageChangeListener {


    @BindView(R.id.apply_filter_btn)
    lateinit var button : Button

    var pagerAdapterPosition = 0


    //region===============================OnPageChangeListener==========================

    override fun onPageScrollStateChanged(state: Int) {

    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    override fun onPageSelected(position: Int) {

    }

    //endregion



    @BindView(R.id.search_pager)
    lateinit var mViewPager: ViewPager

    var searchAdapter : SearchAdapter = SearchAdapter()


    override fun viewOnBackPressed(): Boolean {
        return false
    }

    override fun initDagger(context: Context) {
        DaggerService.getDaggerComponent<DaggerSearchScreen_SearchPresenterComponent>(context).inject(this)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        mViewPager.adapter = searchAdapter
        mViewPager.addOnPageChangeListener(this)

    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
    }




    @OnClick(R.id.apply_filter_btn)
    fun applyClick(){

        Log.e("M_VIEW_PAGER", "Cuurent Item is "+ mViewPager.currentItem + " " + mViewPager.getChildAt(mViewPager.currentItem)::class.toString())
        if (searchAdapter.getScreenByPosition(mViewPager.currentItem)is FilterScreen){
            Flow.get(this).set(FilteredPhotoCardListScreen(1, Object()))
        }
        if (searchAdapter.getScreenByPosition(mViewPager.currentItem) is TagScreen){
            Flow.get(this).set(FilteredPhotoCardListScreen(0, Object()))
        }
    }
}
