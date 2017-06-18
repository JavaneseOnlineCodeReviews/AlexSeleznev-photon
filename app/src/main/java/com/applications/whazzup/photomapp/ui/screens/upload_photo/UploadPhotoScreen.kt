package com.applications.whazzup.photomapp.ui.screens.upload_photo

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

@Screen(R.layout.screen_upload_photo)
class UploadPhotoScreen : AbstractScreen<RootActivity.RootComponent>() {
    override fun createScreenComponent(parentComponent: RootActivity.RootComponent): Any {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return DaggerUploadPhotoScreen_Component.builder().rootComponent(parentComponent).uploadPhotoModule(UploadPhotoModule()).build()
//        return null!!
    }



    //region===============================Presenter==========================

    inner class UploadPhotoPresenter : AbstractPresenter<UploadPhotoView, UserProfileModel>() {

        override fun initDagger(scope: MortarScope?) {
            (scope!!.getService<Any>(DaggerService.SERVICE_NAME) as DaggerUploadPhotoScreen_Component).inject(this)
        }

        override fun initToolbar() {
            mRootPresenter.newActionBarBuilder().setVisible(false).setTitle("Добавление фотографии").build()
        }

    }

    //endregion



    //region===============================DI==========================

    @dagger.Module
    inner class UploadPhotoModule {
        @Provides
        @DaggerScope(UploadPhotoScreen :: class)
        internal fun providePresenter(): UploadPhotoPresenter {
            return UploadPhotoPresenter()
        }

        @Provides
        @DaggerScope(UploadPhotoScreen :: class)
        internal  fun provideModel(): UserProfileModel {
            return  UserProfileModel()
        }
    }

    @dagger.Component(dependencies = arrayOf(RootActivity.RootComponent::class), modules = arrayOf(UploadPhotoModule::class))
    @DaggerScope(UploadPhotoScreen::class)
    interface Component{
        fun inject(view : UploadPhotoView)
        fun inject(presenter : UploadPhotoPresenter)
    }

    //endregion
}