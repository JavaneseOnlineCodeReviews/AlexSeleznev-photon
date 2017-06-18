package com.applications.whazzup.photomapp.ui.screens.search

import com.applications.whazzup.photomapp.R
import com.applications.whazzup.photomapp.di.DaggerScope
import com.applications.whazzup.photomapp.di.DaggerService
import com.applications.whazzup.photomapp.flow.AbstractScreen
import com.applications.whazzup.photomapp.flow.Screen
import com.applications.whazzup.photomapp.mvp.models.SearchModel
import com.applications.whazzup.photomapp.mvp.presenters.AbstractPresenter
import com.applications.whazzup.photomapp.mvp.presenters.RootPresenter
import com.applications.whazzup.photomapp.ui.activities.RootActivity
import com.applications.whazzup.photomapp.ui.screens.photo_card_list.PhotoCardListScreen
import dagger.Component
import dagger.Module
import dagger.Provides
import flow.TreeKey
import mortar.MortarScope

@Screen(R.layout.screen_search)
class SearchScreen : AbstractScreen<RootActivity.RootComponent>(), TreeKey {

    override fun createScreenComponent(parentComponent: RootActivity.RootComponent): Any {
        return DaggerSearchScreen_SearchPresenterComponent.builder()
                .rootComponent(parentComponent)
                .searchPresenterModule(SearchPresenterModule())
                .build()
    }

    override fun getParentKey(): Any = PhotoCardListScreen()

    inner class SearchPresenter : AbstractPresenter<SearchView, SearchModel>() {
        override fun initDagger(scope: MortarScope) {
            (scope.getService<Any>(DaggerService.SERVICE_NAME) as DaggerSearchScreen_SearchPresenterComponent).inject(this)
        }

        override fun initToolbar() {
            mRootPresenter
                    .newActionBarBuilder()
                    .setVisible(false)
                    .setTab(view.mViewPager)
                    .build()
        }
    }

    @Module
    inner class SearchPresenterModule {

        @Provides
        @DaggerScope(SearchScreen::class)
        internal fun providePresenter(): SearchPresenter {
            return SearchPresenter()
        }

        @Provides
        @DaggerScope(SearchScreen::class)
        internal fun provideModel(): SearchModel {
            return SearchModel()
        }
    }

    @Component(dependencies = arrayOf(RootActivity.RootComponent::class), modules = arrayOf(SearchPresenterModule::class))
    @DaggerScope(SearchScreen::class)
    interface SearchPresenterComponent {
        fun inject(presenter: SearchPresenter)
        fun inject(view: SearchView)
        fun inject(adapter: SearchAdapter)

        val rootPresenter: RootPresenter
    }


}