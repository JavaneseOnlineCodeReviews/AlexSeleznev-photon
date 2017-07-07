package com.applications.whazzup.photomapp.ui.screens.author

import android.content.Context
import android.util.AttributeSet
import com.applications.whazzup.photomapp.di.DaggerService
import com.applications.whazzup.photomapp.mvp.views.AbstractView

class AuthorView(context : Context, attrs : AttributeSet) : AbstractView<AuthorScreen.AuthorPresenter>(context, attrs) {


    override fun initDagger(context: Context?) {
        DaggerService.getDaggerComponent<DaggerAuthorScreen_Component>(context!!).inject(this)
    }

    override fun viewOnBackPressed(): Boolean {
        return false
    }
}