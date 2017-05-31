package com.applications.whazzup.photomapp.ui.screens.photo_card_list


import com.applications.whazzup.photomapp.R
import com.applications.whazzup.photomapp.di.DaggerService
import com.applications.whazzup.photomapp.di.scopes.PhotoCardListScope
import com.applications.whazzup.photomapp.flow.AbstractScreen
import com.applications.whazzup.photomapp.flow.Screen
import com.applications.whazzup.photomapp.mvp.models.PhotoCardListModel
import com.applications.whazzup.photomapp.mvp.presenters.AbstractPresenter
import com.applications.whazzup.photomapp.ui.activities.RootActivity

import dagger.Provides
import mortar.MortarScope

@Screen(R.layout.screen_photo_card_list)
class PhotoCardListScreen : AbstractScreen<RootActivity.RootComponent>() {
    override fun createScreenComponent(parentComponent: RootActivity.RootComponent): Any {
        return DaggerPhotoCardListScreen_Component.builder().rootComponent(parentComponent).module(Module()).build()
    }

    // region================Presenter==============
    inner class PhotoCardListPresenter : AbstractPresenter<PhotoCardListView, PhotoCardListModel>() {

        override fun initDagger(scope: MortarScope) {
            (scope.getService<Any>(DaggerService.SERVICE_NAME) as DaggerPhotoCardListScreen_Component).inject(this)
        }
    }


    // endregion


    // region================DI==============

    @dagger.Module
    inner class Module {
        @Provides
        @PhotoCardListScope
        internal fun providePresenter(): PhotoCardListPresenter {
            return PhotoCardListPresenter()
        }

        @Provides
        @PhotoCardListScope
        internal fun provideModel(): PhotoCardListModel {
            return PhotoCardListModel()
        }
    }

    @dagger.Component(dependencies = arrayOf(RootActivity.RootComponent::class), modules = arrayOf(PhotoCardListScreen.Module::class))
    @PhotoCardListScope
    interface Component {
        fun inject(presenter: PhotoCardListPresenter)
        fun inject(view: PhotoCardListView)
    }

    // endregion
}
