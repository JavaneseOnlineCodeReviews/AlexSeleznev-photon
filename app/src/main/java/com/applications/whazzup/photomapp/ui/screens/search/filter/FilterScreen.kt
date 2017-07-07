package com.applications.whazzup.photomapp.ui.screens.search.filter

import com.applications.whazzup.photomapp.R
import com.applications.whazzup.photomapp.di.DaggerScope
import com.applications.whazzup.photomapp.di.DaggerService
import com.applications.whazzup.photomapp.flow.AbstractScreen
import com.applications.whazzup.photomapp.flow.Screen
import com.applications.whazzup.photomapp.mvp.models.FilterModel
import com.applications.whazzup.photomapp.mvp.presenters.AbstractPresenter
import com.applications.whazzup.photomapp.ui.screens.search.SearchScreen
import dagger.Component
import dagger.Module
import dagger.Provides
import mortar.MortarScope

@Screen(R.layout.context_filter)
class FilterScreen : AbstractScreen<SearchScreen.SearchPresenterComponent>() {

    override fun createScreenComponent(parentComponent: SearchScreen.SearchPresenterComponent): Any {
        return DaggerFilterScreen_FilterPresenterComponent.builder()
                .searchPresenterComponent(parentComponent)
                .filterPresenterModule(FilterPresenterModule())
                .build()
    }

    //region ================= Presenter =================

    inner class FilterPresenter : AbstractPresenter<FilterView, FilterModel>() {

        override fun initToolbar() {
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
        internal fun providePresenter(): FilterPresenter {
            return FilterPresenter()
        }

        @Provides
        @DaggerScope(FilterScreen::class)
        internal fun provideModel(): FilterModel {
            return FilterModel()
        }
    }

    @Component(dependencies = arrayOf(SearchScreen.SearchPresenterComponent::class), modules = arrayOf(FilterPresenterModule::class))
    @DaggerScope(FilterScreen::class)
    interface FilterPresenterComponent {
        fun inject(presenter: FilterPresenter)
        fun inject(view: FilterView)
    }

    // endregion
}
