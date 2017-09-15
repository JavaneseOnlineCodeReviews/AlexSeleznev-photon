package com.applications.whazzup.photomapp.ui.screens.filtered_photo_card_list

import com.applications.whazzup.photomapp.R
import com.applications.whazzup.photomapp.di.DaggerScope
import com.applications.whazzup.photomapp.di.DaggerService
import com.applications.whazzup.photomapp.flow.AbstractScreen
import com.applications.whazzup.photomapp.flow.Screen
import com.applications.whazzup.photomapp.mvp.models.FilteredPhotoCardListModel
import com.applications.whazzup.photomapp.mvp.presenters.AbstractPresenter
import com.applications.whazzup.photomapp.ui.activities.RootActivity
import com.applications.whazzup.photomapp.ui.screens.search.SearchScreen
import dagger.Provides
import flow.TreeKey
import io.reactivex.rxkotlin.subscribeBy
import mortar.MortarScope

@Screen(R.layout.screen_filtered_photo_card_list)
class FilteredPhotoCardListScreen() : AbstractScreen<RootActivity.RootComponent>() {


    var mode : Int = -1
    var searchData : Object = Object()

    constructor(searchMode : Int, data : Object) : this(){
        this.mode = searchMode
        this.searchData = data
    }

    override fun createScreenComponent(parentComponent: RootActivity.RootComponent): Any {
            return DaggerFilteredPhotoCardListScreen_FilteredPhotoCardListComponent.builder().rootComponent(parentComponent).filteredPhotoCardListModule(FilteredPhotoCardListModule()).build()
    }

    //region===============================Presenter==========================

    inner class FilteredPhotoCardListPresenter : AbstractPresenter<FilteredPhotoCardListView, FilteredPhotoCardListModel>(){
        override fun initDagger(scope: MortarScope?) {
            scope?.getService<FilteredPhotoCardListComponent>(DaggerService.SERVICE_NAME)?.inject(this)
        }

        override fun onEnterScope(scope: MortarScope?) {
            super.onEnterScope(scope)
            if(mode == 0){
                mModel.mRealmManager.getSearchCardFromRealm(mutableListOf("Рыба","Салат", "Мороженое"))?.subscribeBy(onNext = {
                    view.filteredAdapter.addItem(it)
                }, onComplete = {
                    view.initView()
                })
            }
        }

        override fun initToolbar() {
            if(mode==1) {
                mRootPresenter.newActionBarBuilder().setTitle("Фильтрация").setBackArrow(true).build()
            }
            if(mode==0){
                mRootPresenter.newActionBarBuilder().setTitle("Поиск").setBackArrow(true).build()
            }

        }

    }
    //endregion

    //region===============================DI==========================

    @dagger.Module
    inner class FilteredPhotoCardListModule {
        @Provides
        @DaggerScope(FilteredPhotoCardListScreen :: class)
        internal fun provideModel(): FilteredPhotoCardListModel {
            return FilteredPhotoCardListModel()
        }

        @Provides
        @DaggerScope(FilteredPhotoCardListScreen :: class)
        internal fun providePresenter(): FilteredPhotoCardListPresenter {
            return FilteredPhotoCardListPresenter()
        }
    }

    @dagger.Component(dependencies = arrayOf(RootActivity.RootComponent :: class), modules = arrayOf(FilteredPhotoCardListModule :: class))
    @DaggerScope(FilteredPhotoCardListScreen :: class)
    interface FilteredPhotoCardListComponent{
        fun inject(view : FilteredPhotoCardListView)
        fun inject(presenter : FilteredPhotoCardListPresenter)
        fun inject(adapter : FilteredPhotoCardListAdapter)

    }
    //endregion


}