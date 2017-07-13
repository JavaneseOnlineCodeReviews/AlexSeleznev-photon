package com.applications.whazzup.photomapp.ui.screens.author

import android.os.Bundle
import com.applications.whazzup.photomapp.R
import com.applications.whazzup.photomapp.data.network.res.user.UserRes
import com.applications.whazzup.photomapp.data.storage.dto.PhotoCardDto
import com.applications.whazzup.photomapp.di.DaggerScope
import com.applications.whazzup.photomapp.di.DaggerService
import com.applications.whazzup.photomapp.flow.AbstractScreen
import com.applications.whazzup.photomapp.flow.Screen
import com.applications.whazzup.photomapp.mvp.models.AuthorModel
import com.applications.whazzup.photomapp.mvp.presenters.AbstractPresenter
import com.applications.whazzup.photomapp.mvp.presenters.RootPresenter
import com.applications.whazzup.photomapp.ui.activities.RootActivity
import com.applications.whazzup.photomapp.ui.screens.photo_detail_info.PhotoDetailInfoScreen
import com.squareup.picasso.Picasso
import dagger.Provides
import flow.TreeKey
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import mortar.MortarScope
import rx.Scheduler

@Screen(R.layout.screen_author)
class AuthorScreen() : AbstractScreen<RootActivity.RootComponent>(){

    lateinit var userId : String

    constructor(id : String) : this(){
       userId = id
    }

    override fun createScreenComponent(parentComponent: RootActivity.RootComponent): Any {
        return DaggerAuthorScreen_Component.builder().rootComponent(parentComponent).authorModule(AuthorModule()).build()
    }

    //region===============================Presenter==========================

    inner class AuthorPresenter : AbstractPresenter<AuthorView, AuthorModel>(){

        override fun onLoad(savedInstanceState: Bundle?) {
            super.onLoad(savedInstanceState)
            var res : UserRes? = null
            mModel.getUserInfoById(userId).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribeBy(onNext = {
                res = it
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

    @dagger.Component(dependencies = arrayOf(RootActivity.RootComponent :: class), modules = arrayOf(AuthorModule :: class))
    @DaggerScope(AuthorScreen :: class)
    interface Component{
        fun inject(view : AuthorView)
        fun inject(presenter : AuthorPresenter)
        fun inject(adapter : AuthorAlbumAdapter)
        val rootPresenter: RootPresenter
        val picasso : Picasso
    }

    //endregion


}