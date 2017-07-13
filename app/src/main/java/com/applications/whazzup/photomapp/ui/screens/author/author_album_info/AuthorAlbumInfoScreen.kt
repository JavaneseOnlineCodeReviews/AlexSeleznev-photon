package com.applications.whazzup.photomapp.ui.screens.author.author_album_info

import android.os.Bundle
import com.applications.whazzup.photomapp.R
import com.applications.whazzup.photomapp.data.network.res.user.UserAlbumRes
import com.applications.whazzup.photomapp.di.DaggerScope
import com.applications.whazzup.photomapp.di.DaggerService
import com.applications.whazzup.photomapp.flow.AbstractScreen
import com.applications.whazzup.photomapp.flow.Screen
import com.applications.whazzup.photomapp.mvp.models.AuthorAlbumInfoModel
import com.applications.whazzup.photomapp.mvp.presenters.AbstractPresenter
import com.applications.whazzup.photomapp.ui.screens.author.AuthorScreen
import dagger.Provides
import flow.TreeKey
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import mortar.MortarScope

@Screen(R.layout.screen_author_album_info)
class AuthorAlbumInfoScreen() : AbstractScreen<AuthorScreen.Component>(), TreeKey {

   lateinit  var albumId : String
   lateinit  var albumRes : UserAlbumRes

    constructor(id : String) : this() {
        this.albumId = id
    }

    constructor(res : UserAlbumRes) : this(){
        this.albumId = res.id
        albumRes = res
    }


    override fun createScreenComponent(parentComponent: AuthorScreen.Component): Any {
        return DaggerAuthorAlbumInfoScreen_Component.builder().component(parentComponent).authorAlbumInfoModule(AuthorAlbumInfoModule()).build()
    }

    override fun getParentKey(): Any {
        return AuthorScreen()
    }

    //region===============================Presenter==========================

    inner class AuthorAlbumInfoPresenter : AbstractPresenter<AuthorAlbumInfoView, AuthorAlbumInfoModel>(){


        override fun onLoad(savedInstanceState: Bundle?) {
            super.onLoad(savedInstanceState)
            var res : UserAlbumRes? = null
            mModel.getAlbumById(albumId).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribeBy(onNext = {
                res = it
                it.photocards
                        .filter { it.active }
                        .forEach { view.authorAdapter.additem(it) }
            }, onComplete = {
                view.initView(res!!)
            })
        }


        override fun onEnterScope(scope: MortarScope?) {
            super.onEnterScope(scope)

        }

        override fun initDagger(scope: MortarScope?) {
            (scope?.getService<DaggerAuthorAlbumInfoScreen_Component>(DaggerService.SERVICE_NAME))?.inject(this)
        }

        override fun initToolbar() {
            mRootPresenter.newActionBarBuilder().setTitle("Альбом").setBackArrow(true).build()
        }
    }
    //endregion

    //region===============================DI==========================

    @dagger.Module
    inner class AuthorAlbumInfoModule{
        @Provides
        @DaggerScope(AuthorAlbumInfoScreen :: class)
        internal fun providePresenter() : AuthorAlbumInfoPresenter{
            return AuthorAlbumInfoPresenter()
        }

        @Provides
        @DaggerScope(AuthorAlbumInfoScreen :: class)
        internal fun provideModel() : AuthorAlbumInfoModel{
            return AuthorAlbumInfoModel()
        }
    }

    @dagger.Component(dependencies = arrayOf(AuthorScreen.Component :: class), modules = arrayOf(AuthorAlbumInfoModule :: class))
    @DaggerScope(AuthorAlbumInfoScreen :: class)
    interface Component{
        fun inject(presenter : AuthorAlbumInfoPresenter)
        fun inject(view : AuthorAlbumInfoView)
        fun inject(adapter : AuthorAlbumInfoAdapter)
    }
    //endregion
}