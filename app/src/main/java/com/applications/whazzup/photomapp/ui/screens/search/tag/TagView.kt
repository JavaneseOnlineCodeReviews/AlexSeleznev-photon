package com.applications.whazzup.photomapp.ui.screens.search.tag

import android.content.Context
import android.util.AttributeSet
import com.applications.whazzup.photomapp.di.DaggerService
import com.applications.whazzup.photomapp.mvp.views.AbstractView


class TagView(context : Context, attrs : AttributeSet) : AbstractView<TagScreen.TagPresenter>(context, attrs) {


    //region ================= AbstractView =================

    override fun viewOnBackPressed(): Boolean {
        return false
    }

    override fun initDagger(context: Context) {
        DaggerService.getDaggerComponent<TagScreen.TagPresenterComponent>(context).inject(this)
    }

    fun initView(tagList: List<String>?) {
        var locslTagList: List<String> = tagList!!
        emptyFun()
    }

    private fun emptyFun() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    //endregion

}