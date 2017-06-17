package com.applications.whazzup.photomapp.ui.screens.album_info

import com.applications.whazzup.photomapp.R
import com.applications.whazzup.photomapp.di.DaggerScope
import com.applications.whazzup.photomapp.di.DaggerService
import com.applications.whazzup.photomapp.flow.AbstractScreen
import com.applications.whazzup.photomapp.flow.Screen
import com.applications.whazzup.photomapp.mvp.models.AlbumInfoModel
import com.applications.whazzup.photomapp.mvp.presenters.AbstractPresenter
import com.applications.whazzup.photomapp.ui.activities.RootActivity
import dagger.Provides
import mortar.MortarScope

@Screen(R.layout.screen_album_info)
class AlbumInfoScreen : AbstractScreen<RootActivity.RootComponent>() {

    override fun createScreenComponent(parentComponent: RootActivity.RootComponent): Any {
       return DaggerAlbumInfoScreen_AlbumInfoComponent.builder().rootComponent(parentComponent).albumInfoModule(AlbumInfoModule()).build()
    }

    // region================Presenter==============

    inner class AlbumInfoPresenter : AbstractPresenter<AlbumInfoView, AlbumInfoModel>(){

        override fun initToolbar() {
            mRootPresenter.newActionBarBuilder().setTitle("Альбом").setBackArrow(true).build()
        }

        override fun initDagger(scope: MortarScope?) {
            (scope!!.getService<Any>(DaggerService.SERVICE_NAME) as DaggerAlbumInfoScreen_AlbumInfoComponent).inject(this)
        }

    }
    // endregion

    // region================DI==============

    @dagger.Module
    inner class AlbumInfoModule{
        @Provides
        @DaggerScope(AlbumInfoScreen :: class)
        internal fun providePresenter(): AlbumInfoPresenter{
            return AlbumInfoPresenter()
        }

        @Provides
        @DaggerScope(AlbumInfoScreen :: class)
        internal fun provideModel(): AlbumInfoModel{
            return AlbumInfoModel()
        }
    }

    @dagger.Component(dependencies = arrayOf(RootActivity.RootComponent::class), modules = arrayOf(AlbumInfoModule :: class))
    @DaggerScope(AlbumInfoScreen :: class)
    interface AlbumInfoComponent{
        fun inject (view : AlbumInfoView)
        fun inject (presetner: AlbumInfoPresenter)
    }

    // endregion
}