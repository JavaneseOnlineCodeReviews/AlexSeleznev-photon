package com.applications.whazzup.photomapp.ui.screens.author

import android.os.Bundle
import com.applications.whazzup.photomapp.R
import com.applications.whazzup.photomapp.data.network.res.user.UserRes
import com.applications.whazzup.photomapp.di.DaggerScope
import com.applications.whazzup.photomapp.di.DaggerService
import com.applications.whazzup.photomapp.flow.AbstractScreen
import com.applications.whazzup.photomapp.flow.Screen
import com.applications.whazzup.photomapp.mvp.models.AuthorModel
import com.applications.whazzup.photomapp.mvp.presenters.AbstractPresenter
import com.applications.whazzup.photomapp.ui.screens.photo_detail_info.PhotoDetailInfoScreen
import dagger.Provides
import flow.TreeKey
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import mortar.MortarScope
import rx.Scheduler

@Screen(R.layout.screen_author)
class AuthorScreen(val userId : String) : AbstractScreen<PhotoDetailInfoScreen.Component>(), TreeKey {
    override fun getParentKey(): Any {
        return PhotoDetailInfoScreen()
    }

    override fun createScreenComponent(parentComponent: PhotoDetailInfoScreen.Component): Any {
        return DaggerAuthorScreen_Component.builder().component(parentComponent).authorModule(AuthorModule()).build()
    }

    //region===============================Presenter==========================

    inner class AuthorPresenter : AbstractPresenter<AuthorView, AuthorModel>(){

        override fun onLoad(savedInstanceState: Bundle?) {
            super.onLoad(savedInstanceState)
            var res : UserRes? = null
            mModel.getUserInfoById(userId).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribeBy(onNext = {
                res = it
                        for(album in it.albums){
                            if(album.active) {
                                view.authorAlbumAdapter.addItem(album)
                            }
                        }
            }, onComplete = {
                view.initView(res!!)
            }, onError = {
                print(it.toString())})
        }

        override fun initDagger(scope: MortarScope?) {
            (scope?.getService<Any>(DaggerService.SERVICE_NAME) as DaggerAuthorScreen_Component).inject(this)
        }

        override fun initToolbar() {
            mRootPresenter.newActionBarBuilder().setTitle("Автор").setBackArrow(true).build()
        }

    }

    //endregion

    //region===============================DI==========================

@dagger.Module
    inner class AuthorModule{

    @Provides
    @DaggerScope(AuthorScreen :: class)
    internal fun providePresneter() : AuthorPresenter{
        return AuthorPresenter()
    }

    @Provides
    @DaggerScope(AuthorScreen :: class)
    internal fun provideModel() : AuthorModel{
        return AuthorModel()
    }

}

    @dagger.Component(dependencies = arrayOf(PhotoDetailInfoScreen.Component :: class), modules = arrayOf(AuthorModule :: class))
    @DaggerScope(AuthorScreen :: class)
    interface Component{
        fun inject(view : AuthorView)
        fun inject(presenter : AuthorPresenter)
        fun inject(adapter : AuthorAlbumAdapter)
    }

    //endregion


}