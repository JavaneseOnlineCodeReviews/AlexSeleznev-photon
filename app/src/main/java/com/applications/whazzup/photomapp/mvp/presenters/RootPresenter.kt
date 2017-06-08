package com.applications.whazzup.photomapp.mvp.presenters


import android.support.v4.view.ViewPager

import com.applications.whazzup.photomapp.App
import com.applications.whazzup.photomapp.data.network.req.UserSigInReq
import com.applications.whazzup.photomapp.mvp.models.RootModel
import com.applications.whazzup.photomapp.mvp.views.IRootView
import com.applications.whazzup.photomapp.ui.activities.RootActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

import mortar.Presenter
import mortar.bundler.BundleService
import javax.inject.Inject

class RootPresenter private constructor() : Presenter<IRootView>() {

    @Inject
    lateinit var mRootModel: RootModel

    val DEFAULT_MODE = 0
    val TAB_MODE = 1

    companion object {
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

    fun signUpUser(user: UserSigInReq) {
        mRootModel.signUpUser(user)
                .doOnNext { }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(onComplete = {
                    view.showMessage("Регистрациия прошла успешно")
                    view.hideAlertDialog()
                }, onError = {
                    view.showMessage("Такой пользователь уже существует")
                })
    }

    fun newActionBarBuilder(): ActionBarBuilder {
        return ActionBarBuilder()
    }

    inner class ActionBarBuilder {
        var isGoBack: Boolean = false
        var isVisible: Boolean = true
        var title: CharSequence? = null
        var item: List<MenuItemHolder> = ArrayList()
        var viewPager: ViewPager? = null
        var toolBarMode = DEFAULT_MODE

        fun setBackArrow(enabled: Boolean): ActionBarBuilder {
            this.isGoBack = enabled
            return this
        }

        fun setVisible(visible: Boolean): ActionBarBuilder {
            this.isVisible = visible
            return this
        }

        fun addAction(itemMenu: MenuItemHolder): ActionBarBuilder {
            (item as ArrayList).add(itemMenu)
            return this
        }

        fun setTab(pager: ViewPager?): ActionBarBuilder {
            this.toolBarMode = TAB_MODE
            this.viewPager = pager
            return this
        }

        fun setTitle(title: CharSequence?): ActionBarBuilder {
            this.title = title
            return this
        }

        fun build() {
            if (view != null) {
                var activity = view as RootActivity
                activity.setBackArrow(isGoBack)
                activity.setActionBarTitle(title)
                activity.setActionBarVisible(isVisible)
                activity.setMenuItem(item)
                if (toolBarMode == TAB_MODE) {
                    activity.setTabLayout(viewPager)
                } else {
                    activity.removeTabLayout()
                }

            }
        }
    }
}

