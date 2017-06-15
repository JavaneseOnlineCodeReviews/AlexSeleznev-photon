package com.applications.whazzup.photomapp.mvp.presenters


import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager

import com.applications.whazzup.photomapp.App
import com.applications.whazzup.photomapp.data.network.req.UserLogInReq
import com.applications.whazzup.photomapp.data.network.req.UserSigInReq
import com.applications.whazzup.photomapp.data.storage.dto.ActivityResultDto
import com.applications.whazzup.photomapp.mvp.models.RootModel
import com.applications.whazzup.photomapp.mvp.views.IRootView
import com.applications.whazzup.photomapp.ui.activities.RootActivity
import com.applications.whazzup.photomapp.ui.screens.user_profile_auth.UserProfileAuthView
import com.applications.whazzup.photomapp.ui.screens.user_profile_idle.UserProfileIdleView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

import mortar.Presenter
import mortar.bundler.BundleService
import javax.inject.Inject

class RootPresenter private constructor() : Presenter<IRootView>() {

    @Inject
    lateinit var mRootModel: RootModel

    val DEFAULT_MODE = 0
    val TAB_MODE = 1

   val mActivityResultObs : BehaviorSubject<ActivityResultDto> = BehaviorSubject.create()

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
                .doOnNext { mRootModel.saveUserInfo(it)  }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(onComplete = {
                    view.showMessage("Регистрациия прошла успешно")
                    view.hideAlertDialog()
                    if(view.currentScreen is UserProfileIdleView) run {
                        (view.currentScreen as UserProfileIdleView).changeScreen()
                    }
                }, onError = {
                    view.showMessage("Такой пользователь уже существует")
                })
    }

    fun logInUser(user: UserLogInReq){
        mRootModel.logInUser(user)
                .doOnNext { mRootModel.saveUserInfo(it) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(onComplete = {
                    view.showMessage("Добрро пожаловать")
                    view.hideAlertDialog()
                    if(view.currentScreen is UserProfileIdleView) run {
                        (view.currentScreen as UserProfileIdleView).changeScreen()
                    }
                }, onError = {
                    view.showMessage("Такого пользователя не существует")
                })
    }

    fun isUserAuth():Boolean{
        return mRootModel.isUserAuth()
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

    fun logOut() {
        mRootModel.logOut()
    }

    fun checkPermissionAndRequestIfNotGranted(permissions: Array<String>, requestCode: Int): Boolean {
        var allGranted = true
        for (permission in permissions) {
            val selfPermission = ContextCompat.checkSelfPermission(view as RootActivity, permission)
            if (selfPermission != PackageManager.PERMISSION_GRANTED) {
                allGranted = false
                break
            }
        }
        if (!allGranted) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                (view as RootActivity).requestPermissions(permissions, requestCode)
            }
            return false
        }
        return allGranted
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        mActivityResultObs.onNext(ActivityResultDto(requestCode, resultCode, intent))
    }

    fun onRequestPermissionResult(requestCode: Int, permissions: Array<String>, grantResult: IntArray) {
        //Implements me
    }
}

