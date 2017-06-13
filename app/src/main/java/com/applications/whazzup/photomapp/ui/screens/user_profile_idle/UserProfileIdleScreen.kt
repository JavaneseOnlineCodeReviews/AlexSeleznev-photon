package com.applications.whazzup.photomapp.ui.screens.user_profile_idle

import com.applications.whazzup.photomapp.R
import com.applications.whazzup.photomapp.di.DaggerScope
import com.applications.whazzup.photomapp.di.DaggerService
import com.applications.whazzup.photomapp.flow.AbstractScreen
import com.applications.whazzup.photomapp.flow.Screen
import com.applications.whazzup.photomapp.mvp.models.UserProfileModel
import com.applications.whazzup.photomapp.mvp.presenters.AbstractPresenter
import com.applications.whazzup.photomapp.ui.activities.RootActivity
import dagger.Provides
import mortar.MortarScope

@Screen(R.layout.screen_user_profile_idle)
class UserProfileIdleScreen: AbstractScreen<RootActivity.RootComponent>() {

    var mCustomState = 1

    override fun createScreenComponent(parentComponent: RootActivity.RootComponent): Any {
        return DaggerUserProfileIdleScreen_Component.builder().rootComponent(parentComponent).userProfileIdleModule(UserProfileIdleModule()).build()
    }


    //region===============================Presenter==========================

    inner class UserProfileIdlePresenter : AbstractPresenter<UserProfileIdleView, UserProfileModel>(){

        override fun initDagger(scope: MortarScope?) {
            (scope!!.getService<Any>(DaggerService.SERVICE_NAME) as DaggerUserProfileIdleScreen_Component).inject(this)
        }

        override fun initToolbar() {
            mRootPresenter.newActionBarBuilder().setTitle("Профиль").build()
        }

        fun isUserAuth(): Boolean {
            return mModel.isUserAuth()
        }
    }

    //endregion

    //region===============================DI==========================

    @dagger.Module
    inner class UserProfileIdleModule{
        @Provides
        @DaggerScope(UserProfileIdleScreen:: class)
        internal fun providePresenter(): UserProfileIdlePresenter{
            return UserProfileIdlePresenter()
        }

        @Provides
        @DaggerScope(UserProfileIdleScreen:: class)
        internal fun provideModel(): UserProfileModel {
            return  UserProfileModel()
        }
    }

    @dagger.Component(dependencies = arrayOf(RootActivity.RootComponent::class), modules = arrayOf(UserProfileIdleModule::class))
    @DaggerScope(UserProfileIdleScreen::class)
    interface Component{
        fun inject(view : UserProfileIdleView)
        fun inject(presenter : UserProfileIdlePresenter)
    }

    //endregion
}