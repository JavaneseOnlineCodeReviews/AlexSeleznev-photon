package com.applications.whazzup.photomapp.ui.screens.filtered_photo_card_list

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import butterknife.BindView
import com.applications.whazzup.photomapp.R
import com.applications.whazzup.photomapp.di.DaggerScope
import com.applications.whazzup.photomapp.di.DaggerService
import com.applications.whazzup.photomapp.mvp.views.AbstractView

class FilteredPhotoCardListView(context : Context, attrs : AttributeSet): AbstractView<FilteredPhotoCardListScreen.FilteredPhotoCardListPresenter>(context, attrs) {

    @BindView(R.id.filtered_recycler)
    lateinit var recycler : RecyclerView

    var filteredAdapter = FilteredPhotoCardListAdapter()

    override fun viewOnBackPressed(): Boolean {
        return false
    }

    override fun initDagger(context: Context?) {
        DaggerService.getDaggerComponent<FilteredPhotoCardListScreen.FilteredPhotoCardListComponent>(context!!).inject(this)
    }

    fun initView() {
        with(recycler){
            layoutManager = GridLayoutManager(context, 2)
            adapter = filteredAdapter
        }
    }
}