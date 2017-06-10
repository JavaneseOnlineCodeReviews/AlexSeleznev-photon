package com.applications.whazzup.photomapp.ui.screens.user_profile_auth

import android.view.View
import com.applications.whazzup.photomapp.R
import com.applications.whazzup.photomapp.di.DaggerScope
import com.applications.whazzup.photomapp.di.DaggerService
import com.applications.whazzup.photomapp.flow.AbstractScreen
import com.applications.whazzup.photomapp.flow.Screen
import com.applications.whazzup.photomapp.mvp.models.UserProfileModel
import com.applications.whazzup.photomapp.mvp.presenters.AbstractPresenter
import com.applications.whazzup.photomapp.mvp.presenters.MenuItemHolder
import com.applications.whazzup.photomapp.ui.activities.RootActivity
import dagger.Provides
import mortar.MortarScope



@Screen(R.layout.screen_user_profile)
class UserProfileAuthScreen : AbstractScreen<RootActivity.RootComponent>() {

    var mCustomState = 1
    private var positiveAction: View? = null

    override fun createScreenComponent(parentComponent: RootActivity.RootComponent): Any {
        return DaggerUserProfileAuthScreen_Component.builder().rootComponent(parentComponent).userProfileModule(UserProfileModule()).build()
    }


    //region===============================Presenter==========================

    inner class UserProfilePresenter : AbstractPresenter<UserProfileAuthView, UserProfileModel>(){

        override fun initDagger(scope: MortarScope?) {
            (scope!!.getService<Any>(DaggerService.SERVICE_NAME) as DaggerUserProfileAuthScreen_Component).inject(this)
        }

        override fun initToolbar() {
            mRootPresenter.newActionBarBuilder()
                    .setVisible(true)
                    .setTitle("Фотон")
                    .addAction(MenuItemHolder("Добавить альбом", R.layout.search_menu_item,listener =  {
                        view.showPopupMenu(it)
                        true
                    }))
                    .build()
        }

        fun isUserAuth(): Boolean {
            return mModel.isUserAuth()
        }
    }

    //endregion

    //region===============================DI==========================

    @dagger.Module
    inner class UserProfileModule{
        @Provides
        @DaggerScope(UserProfileAuthScreen:: class)
        internal fun providePresenter(): UserProfilePresenter{
            return UserProfilePresenter()
        }

        @Provides
        @DaggerScope(UserProfileAuthScreen:: class)
        internal  fun provideModel(): UserProfileModel{
            return  UserProfileModel()
        }
    }

    @dagger.Component(dependencies = arrayOf(RootActivity.RootComponent::class), modules = arrayOf(UserProfileModule::class))
    @DaggerScope(UserProfileAuthScreen::class)
    interface Component{
        fun inject(view : UserProfileAuthView)
        fun inject(presenter : UserProfilePresenter)
    }

    //endregion
}

