package com.applications.whazzup.photomapp.ui.screens.search.tag

import android.os.Bundle
import android.text.TextUtils
import com.applications.whazzup.photomapp.R
import com.applications.whazzup.photomapp.di.DaggerScope
import com.applications.whazzup.photomapp.di.DaggerService
import com.applications.whazzup.photomapp.flow.AbstractScreen
import com.applications.whazzup.photomapp.flow.Screen
import com.applications.whazzup.photomapp.mvp.models.TagModel
import com.applications.whazzup.photomapp.mvp.presenters.AbstractPresenter
import com.applications.whazzup.photomapp.ui.screens.search.SearchScreen
import dagger.Component
import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import mortar.MortarScope

@Screen(R.layout.content_tag)
class TagScreen : AbstractScreen<SearchScreen.SearchPresenterComponent>() {

    override fun createScreenComponent(parentComponent: SearchScreen.SearchPresenterComponent): Any {
        return DaggerTagScreen_TagPresenterComponent.builder()
                .searchPresenterComponent(parentComponent)
                .tagPresenterModule(TagPresenterModule())
                .build()
    }

    //region ================= Presenter =================

    inner class TagPresenter : AbstractPresenter<TagView, TagModel>() {

        private var mSearchTag: String? = null

        //region ================= AbstractPresenter =================

        override fun initDagger(scope: MortarScope) {
            (scope.getService<Any>(DaggerService.SERVICE_NAME) as DaggerTagScreen_TagPresenterComponent).inject(this)
        }

        override fun initToolbar() {
            mRootPresenter.newActionBarBuilder()
                    .setVisible(false)
                    .build()
        }

        override fun onLoad(savedInstanceState: Bundle?) {
            super.onLoad(savedInstanceState)
            var tagList: List<String>? = null
            mModel.getTagList().subscribeOn(Schedulers.io())
                    .doOnNext { tagList = it }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeBy(onComplete = {
                        view.initView(tagList)
                    })
        }

        fun closeSearch() {
            view.clearSearchTag()
        }

        fun performSearch() {
            if (TextUtils.isEmpty(mSearchTag)) {
                view.showEmptyTagSearchError()
            } else {
                var tagList: MutableList<String>? = null
                mModel.saveTagRealm(mSearchTag!!)
                mModel.getRecentlyTagList()
                        .doOnNext {
                            for (i in 1..it.size) {
                                tagList!!.add(it[i].name!!)
                            }
                        }
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeBy(onComplete = {
                            view.updateRecentlyTagList(tagList)
                        })
            }
        }

        fun setSearchTag(searchTag: String) {
            mSearchTag = searchTag
        }

        //endregion
    }

    //endregion

    //region ================= DI =================

    @Module
    inner class TagPresenterModule {
        @Provides
        @DaggerScope(TagScreen::class)
        internal fun provideTagPresenter(): TagPresenter {
            return TagPresenter()
        }

        @Provides
        @DaggerScope(TagScreen::class)
        internal fun provideTagModel(): TagModel {
            return TagModel()
        }
    }

    @Component(dependencies = arrayOf(SearchScreen.SearchPresenterComponent::class), modules = arrayOf(TagPresenterModule::class))
    @DaggerScope(TagScreen::class)
    interface TagPresenterComponent {
        fun inject(presenter: TagPresenter)
        fun inject(view: TagView)
    }

    //endregion


}