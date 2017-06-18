package com.applications.whazzup.photomapp.ui.screens.search.tag

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.widget.EditText
import android.widget.ImageView
import butterknife.BindView
import com.applications.whazzup.photomapp.R
import com.applications.whazzup.photomapp.di.DaggerService
import com.applications.whazzup.photomapp.mvp.views.AbstractView
import com.applications.whazzup.photomapp.ui.adapters.RecentlyTagsAdapter
import com.applications.whazzup.photomapp.ui.adapters.ServerTagsAdapter
import kotlinx.android.synthetic.main.content_tag.view.*

class TagView(context: Context, attrs: AttributeSet) : AbstractView<TagScreen.TagPresenter>(context, attrs) {

    @BindView(R.id.tag_search_et)
    lateinit var mSearchEt: EditText
    @BindView(R.id.perform_tag_search_iv)
    lateinit var mPerformSearch: ImageView
    @BindView(R.id.close_tag_search_iv)
    lateinit var mCloseSearch: ImageView

    //region ================= AbstractView =================

    override fun viewOnBackPressed(): Boolean {
        return false
    }

    override fun initDagger(context: Context) {
        DaggerService.getDaggerComponent<TagScreen.TagPresenterComponent>(context).inject(this)
    }


    fun updateRecentlyTagList(recentlyTagList: MutableList<String>?) {
        with(recently_tag_list) {
            val llm = LinearLayoutManager(context)
            layoutManager = llm
            adapter = RecentlyTagsAdapter(recentlyTagList!!)
        }
    }

    fun initServerAdapter(tagList: List<String>?) {
        setupSearchView()
        with(tag_server_list) {
            isNestedScrollingEnabled = false
            val llm = com.google.android.flexbox.FlexboxLayoutManager(context)
            llm.flexDirection = com.google.android.flexbox.FlexDirection.ROW
            llm.justifyContent = com.google.android.flexbox.JustifyContent.FLEX_START
            layoutManager = llm
            adapter = ServerTagsAdapter(tagList!!)
        }
    }

    //endregion

    //region ================= Search Edit Text =================

    private fun setupSearchView() {
        mSearchEt.addTextChangedListener(mSearchWatcher)
        mPerformSearch.setOnClickListener { performSearch()}
        mCloseSearch.setOnClickListener { closeSearch() }
    }

    private fun performSearch() {
        mPresenter.performSearch()
    }

    private fun closeSearch() {
        mPresenter.closeSearch()
    }

    fun clearSearchTag() {
        mSearchEt.setText("")
    }

    //endregion

    //region ================= Search Watcher =================

    private val mSearchWatcher = object : TextWatcher {
        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

        }

        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

        }

        override fun afterTextChanged(editable: Editable) {
            mPresenter.setSearchTag(editable.toString())
        }
    }

    fun showEmptyTagSearchError() {

    }

    //endregion

}