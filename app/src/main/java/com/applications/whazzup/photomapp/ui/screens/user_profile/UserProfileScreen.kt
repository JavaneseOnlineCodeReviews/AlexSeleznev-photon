package com.applications.whazzup.photomapp.ui.screens.user_profile

import com.applications.whazzup.photomapp.R
import com.applications.whazzup.photomapp.di.DaggerScope
import com.applications.whazzup.photomapp.di.DaggerService
import com.applications.whazzup.photomapp.flow.AbstractScreen
import com.applications.whazzup.photomapp.flow.Screen
import com.applications.whazzup.photomapp.mvp.models.UserProfileModel
import com.applications.whazzup.photomapp.mvp.presenters.AbstractPresenter
import com.applications.whazzup.photomapp.ui.activities.RootActivity
import com.applications.whazzup.photomapp.ui.screens.photo_card_list.DaggerPhotoCardListScreen_Component
import dagger.Provides
import mortar.MortarScope

@Screen(R.layout.screen_user_profile)
class UserProfileScreen : AbstractScreen<RootActivity.RootComponent>() {

     var mCustomState = 1

    override fun createScreenComponent(parentComponent: RootActivity.RootComponent): Any {
        return DaggerUserProfileScreen_Component.builder().rootComponent(parentComponent).userProfileModule(UserProfileModule()).build()
    }


    //region===============================Presenter==========================

    inner class UserProfilePresenter : AbstractPresenter<UserProfileView, UserProfileModel>(){

        override fun initDagger(scope: MortarScope?) {
            (scope!!.getService<Any>(DaggerService.SERVICE_NAME) as DaggerUserProfileScreen_Component).inject(this)
        }

        override fun initToolbar() {
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
        @DaggerScope(UserProfileScreen :: class)
        internal fun providePresenter(): UserProfilePresenter{
            return UserProfilePresenter()
        }

        @Provides
        @DaggerScope(UserProfileScreen :: class)
        internal  fun provideModel(): UserProfileModel{
            return  UserProfileModel()
        }
    }

    @dagger.Component(dependencies = arrayOf(RootActivity.RootComponent::class), modules = arrayOf(UserProfileModule::class))
    @DaggerScope(UserProfileScreen::class)
    interface Component{
        fun inject(view : UserProfileView)
        fun inject(presenter : UserProfilePresenter)
    }

    //endregion
}

