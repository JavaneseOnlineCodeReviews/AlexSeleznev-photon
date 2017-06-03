package com.applications.whazzup.photomapp.mvp.presenters


import com.applications.whazzup.photomapp.App
import com.applications.whazzup.photomapp.mvp.models.RootModel
import com.applications.whazzup.photomapp.mvp.views.IRootView
import com.applications.whazzup.photomapp.ui.activities.RootActivity

import mortar.Presenter
import mortar.bundler.BundleService
import javax.inject.Inject

class RootPresenter private constructor(): Presenter<IRootView>() {

    @Inject
    lateinit var mRootModel : RootModel

    companion object{
        val INSTANCE = RootPresenter()
    }

    init {
        App.rootComponent!!.inject(this)
    }

    override fun extractBundleService(view: IRootView): BundleService {
        return BundleService.getBundleService(view as RootActivity)
    }

    val rootView: IRootView?
        get() = view
}
