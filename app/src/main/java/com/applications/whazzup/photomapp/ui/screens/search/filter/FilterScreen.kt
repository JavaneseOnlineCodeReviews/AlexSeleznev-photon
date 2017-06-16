package com.applications.whazzup.photomapp.ui.screens.search.filter

import com.applications.whazzup.photomapp.R
import com.applications.whazzup.photomapp.di.DaggerScope
import com.applications.whazzup.photomapp.di.DaggerService
import com.applications.whazzup.photomapp.flow.AbstractScreen
import com.applications.whazzup.photomapp.flow.Screen
import com.applications.whazzup.photomapp.mvp.models.FilterModel
import com.applications.whazzup.photomapp.mvp.presenters.AbstractPresenter
import com.applications.whazzup.photomapp.ui.activities.RootActivity
import com.applications.whazzup.photomapp.ui.screens.photo_card_list.PhotoCardListScreen
import dagger.Component
import dagger.Module
import dagger.Provides
import flow.TreeKey
import mortar.MortarScope

@Screen(R.layout.context_filter)
class FilterScreen : AbstractScreen<RootActivity.RootComponent>(), TreeKey {

    override fun createScreenComponent(parentComponent: RootActivity.RootComponent): Any {
        return DaggerFilterScreen_FilterPresenterComponent.builder()
                .rootComponent(parentComponent)
                .filterPresenterModule(FilterPresenterModule())
                .build()
    }

    override fun getParentKey(): Any = PhotoCardListScreen()

    //region ================= Presenter =================

    inner class FilterPresenter : AbstractPresenter<FilterView, FilterModel>() {

        override fun initToolbar() {
            mRootPresenter.newActionBarBuilder().setVisible(false).build()
        }

        override fun initDagger(scope: MortarScope) {
            (scope.getService<Any>(DaggerService.SERVICE_NAME) as DaggerFilterScreen_FilterPresenterComponent).inject(this)
        }

    }

    //endregion

    // region================ DI ==============

    @Module
    inner class FilterPresenterModule {

        @Provides
        @DaggerScope(FilterScreen::class)
        internal fun providePresenter() : FilterPresenter {
            return FilterPresenter()
        }

        @Provides
        @DaggerScope(FilterScreen::class)
        internal fun provideModel() : FilterModel {
            return FilterModel()
        }
    }

    @Component(dependencies = arrayOf(RootActivity.RootComponent::class), modules = arrayOf(FilterPresenterModule::class))
    @DaggerScope(FilterScreen::class)
    interface FilterPresenterComponent {
        fun inject(presenter: FilterPresenter)
        fun inject(view: FilterView)
    }

    // endregion
}
