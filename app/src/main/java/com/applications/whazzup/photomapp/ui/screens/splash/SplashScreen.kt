package com.applications.whazzup.photomapp.ui.screens.splash

import android.util.Log
import com.applications.whazzup.photomapp.R
import com.applications.whazzup.photomapp.data.storage.dto.PhotoCardDto
import com.applications.whazzup.photomapp.di.DaggerScope
import com.applications.whazzup.photomapp.di.DaggerService
import com.applications.whazzup.photomapp.flow.AbstractScreen
import com.applications.whazzup.photomapp.flow.Screen
import com.applications.whazzup.photomapp.mvp.models.SplashModel
import com.applications.whazzup.photomapp.mvp.presenters.AbstractPresenter
import com.applications.whazzup.photomapp.ui.activities.RootActivity
import com.applications.whazzup.photomapp.ui.screens.photo_card_list.PhotoCardListScreen
import dagger.Provides
import flow.Flow
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import mortar.MortarScope

@Screen(R.layout.screen_splash)
class SplashScreen : AbstractScreen<RootActivity.RootComponent>() {

    override fun createScreenComponent(parentComponent: RootActivity.RootComponent): Any {
        return DaggerSplashScreen_SplashComponent.builder()
                .rootComponent(parentComponent)
                .splashModule(SplashModule())
                .build()
    }

    // region================Presenter==============
    inner class SplashPresenter : AbstractPresenter<SplashView, SplashModel>() {
        override fun initToolbar() {
            mRootPresenter.newActionBarBuilder().setVisible(false).build()
        }

        override fun onEnterScope(scope: MortarScope?) {
            super.onEnterScope(scope)
            rootView?.hideBottomNavigation(false)
            rootView?.showLoad()
            if(mModel.mDataManager.isUserAuth()){
            mModel.getUserById() .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeBy(onNext = {
                        for(item in it.albums){
                            mModel.saveAlbumToRealm(item)
                        }
                    })
            }
            mModel.getPhotoCard(60, 0)
                    .flatMap { Observable.fromIterable(it) }
                    .sorted { o1, o2 -> compareValues(o1, o2)  }
                    .filter { it.active }
                    .doOnNext {
                        mModel.savePhotoCardToRealm(it)
                        mRootPresenter.mRootModel.addToCardList(PhotoCardDto(it))
                    }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeBy(
                            onComplete = {
                                rootView?.hideLoad()
                                rootView?.hideBottomNavigation(true)
                                Flow.get(view.context).set(PhotoCardListScreen())
                            },
                            onError = {
                                rootView!!.hideLoad()
                                Log.d("TAG", it.message)
                            }
                    )

        }

        override fun initDagger(scope: MortarScope) {
            (scope.getService<Any>(DaggerService.SERVICE_NAME) as SplashComponent).inject(this)
        }
    }
    // endregion

    // region================DI==============
    @dagger.Module
    inner class SplashModule {
        @Provides
        @DaggerScope(SplashScreen::class)
        internal fun providePresenter(): SplashPresenter {
            return SplashPresenter()
        }

        @Provides
        @DaggerScope(SplashScreen::class)
        internal fun provideModel(): SplashModel {
            return SplashModel()
        }
    }

    @dagger.Component(dependencies = arrayOf(RootActivity.RootComponent::class), modules = arrayOf(SplashModule::class))
    @DaggerScope(SplashScreen::class)
    interface SplashComponent {
        fun inject(presenter: SplashPresenter)
        fun inject(view: SplashView)
    }
    // endregion
}
