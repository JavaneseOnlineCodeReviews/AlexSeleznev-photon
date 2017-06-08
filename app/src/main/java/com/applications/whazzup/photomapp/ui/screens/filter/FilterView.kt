package com.applications.whazzup.photomapp.ui.screens.filter

import android.content.Context
import android.util.AttributeSet
import com.applications.whazzup.photomapp.di.DaggerService
import com.applications.whazzup.photomapp.mvp.views.AbstractView

class FilterView(context: Context, attrs: AttributeSet) : AbstractView<FilterScreen.FilterPresenter>(context, attrs) {

    //region ================= AbstractView =================

    override fun viewOnBackPressed(): Boolean {
        return false
    }

    override fun initDagger(context: Context) {
        DaggerService.getDaggerComponent<DaggerFilterScreen_FilterPresenterComponent>(context).inject(this)
    }

    //endregion
}
