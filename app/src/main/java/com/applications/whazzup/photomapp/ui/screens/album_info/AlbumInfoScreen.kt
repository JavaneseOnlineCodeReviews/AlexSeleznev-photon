package com.applications.whazzup.photomapp.ui.screens.album_info

import android.os.Bundle
import com.applications.whazzup.photomapp.R
import com.applications.whazzup.photomapp.data.network.res.user.UserAlbumRes
import com.applications.whazzup.photomapp.di.DaggerScope
import com.applications.whazzup.photomapp.di.DaggerService
import com.applications.whazzup.photomapp.flow.AbstractScreen
import com.applications.whazzup.photomapp.flow.Screen
import com.applications.whazzup.photomapp.mvp.models.AlbumInfoModel
import com.applications.whazzup.photomapp.mvp.presenters.AbstractPresenter
import com.applications.whazzup.photomapp.mvp.presenters.MenuItemHolder
import com.applications.whazzup.photomapp.ui.activities.RootActivity
import com.applications.whazzup.photomapp.ui.screens.user_profile_auth.UserProfileAuthScreen
import dagger.Provides
import flow.TreeKey
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import mortar.MortarScope

@Screen(R.layout.screen_album_info)
class AlbumInfoScreen(var item: UserAlbumRes) : AbstractScreen<RootActivity.RootComponent>(), TreeKey {

    override fun getParentKey(): Any {
        return UserProfileAuthScreen()
    }

    override fun createScreenComponent(parentComponent: RootActivity.RootComponent): Any {
       return DaggerAlbumInfoScreen_AlbumInfoComponent.builder().rootComponent(parentComponent).albumInfoModule(AlbumInfoModule()).build()
    }

    // region================Presenter==============

    inner class AlbumInfoPresenter : AbstractPresenter<AlbumInfoView, AlbumInfoModel>(){

        override fun initToolbar() {
            mRootPresenter.newActionBarBuilder().setTitle("Альбом").setBackArrow(true).addAction(MenuItemHolder("Пункты меню", R.layout.dots_menu_item, listener = {
                //view.showPopupMenu(it)
                true
            })).build()
        }

        override fun initDagger(scope: MortarScope?) {
            (scope!!.getService<Any>(DaggerService.SERVICE_NAME) as DaggerAlbumInfoScreen_AlbumInfoComponent).inject(this)
        }

        override fun onLoad(savedInstanceState: Bundle?) {
            var res : UserAlbumRes? = null
            super.onLoad(savedInstanceState)
            mModel.getAlbumById(item.id).subscribeOn(Schedulers.io())
                    .doOnNext {res = it}
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeBy(onComplete = {
                        view.initView(res!!)})
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
        fun inject (adapter : AlbumInfoAdapter)
    }

    // endregion
}