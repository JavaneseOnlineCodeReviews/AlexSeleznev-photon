package com.applications.whazzup.photomapp.ui.screens.splash

import android.os.Handler
import com.applications.whazzup.photomapp.R
import com.applications.whazzup.photomapp.di.DaggerService
import com.applications.whazzup.photomapp.di.scopes.SplashScope
import com.applications.whazzup.photomapp.flow.AbstractScreen
import com.applications.whazzup.photomapp.flow.Screen
import com.applications.whazzup.photomapp.mvp.models.SplashModel
import com.applications.whazzup.photomapp.mvp.presenters.AbstractPresenter
import com.applications.whazzup.photomapp.ui.activities.RootActivity
import com.applications.whazzup.photomapp.ui.screens.photo_card_list.PhotoCardListScreen
import dagger.Provides
import flow.Flow
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
        override fun onEnterScope(scope: MortarScope?) {
            super.onEnterScope(scope)
            rootView!!.hideBottomNavigation(false)
            rootView!!.showLoad()
            val handler = Handler()
            handler.postDelayed({
                rootView!!.hideLoad()
                Flow.get(view.context).set(PhotoCardListScreen())
            }, 3000)
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
        @SplashScope
        internal fun providePresenter(): SplashPresenter {
            return SplashPresenter()
        }

        @Provides
        @SplashScope
        internal fun provideModel(): SplashModel {
            return SplashModel()
        }
    }

    @dagger.Component(dependencies = arrayOf(RootActivity.RootComponent::class), modules = arrayOf(SplashModule::class))
    @SplashScope
    interface SplashComponent {
        fun inject(presenter: SplashPresenter)
        fun inject(view: SplashView)
    }

    // endregion
}
