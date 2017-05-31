package com.applications.whazzup.photomapp.mvp.presenters


import com.applications.whazzup.photomapp.App
import com.applications.whazzup.photomapp.mvp.views.IRootView
import com.applications.whazzup.photomapp.ui.activities.RootActivity

import mortar.Presenter
import mortar.bundler.BundleService

object RootPresenter : Presenter<IRootView>() {

    init {
        App.rootComponent!!.inject(this)
    }

    override fun extractBundleService(view: IRootView): BundleService {
        return BundleService.getBundleService(view as RootActivity)
    }

    val rootView: IRootView?
        get() = view
}
