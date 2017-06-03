package com.applications.whazzup.photomapp.ui.screens.splash

import android.content.Context
import android.util.AttributeSet

import com.applications.whazzup.photomapp.di.DaggerService
import com.applications.whazzup.photomapp.mvp.views.AbstractView
import com.applications.whazzup.photomapp.mvp.views.ISplashView


class SplashView(context: Context, attrs: AttributeSet) : AbstractView<SplashScreen.SplashPresenter>(context, attrs), ISplashView {

    override fun viewOnBackPressed(): Boolean {
        return false

    }

    override fun initDagger(context: Context) {
        DaggerService.getDaggerComponent<SplashScreen.SplashComponent>(context).inject(this)
    }

    override fun showLoad() {

    }

    override fun hideLoad() {

    }
}
