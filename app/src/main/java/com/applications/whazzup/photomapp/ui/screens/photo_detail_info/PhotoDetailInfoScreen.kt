package com.applications.whazzup.photomapp.ui.screens.photo_detail_info

import android.os.Bundle
import com.applications.whazzup.photomapp.R
import com.applications.whazzup.photomapp.data.storage.dto.PhotoCardDto
import com.applications.whazzup.photomapp.di.DaggerScope
import com.applications.whazzup.photomapp.di.DaggerService
import com.applications.whazzup.photomapp.flow.AbstractScreen
import com.applications.whazzup.photomapp.flow.Screen
import com.applications.whazzup.photomapp.mvp.models.PhotoDetailInfoModel
import com.applications.whazzup.photomapp.mvp.presenters.AbstractPresenter
import com.applications.whazzup.photomapp.ui.activities.RootActivity
import com.applications.whazzup.photomapp.ui.screens.photo_card_list.PhotoCardListScreen
import dagger.Provides
import flow.TreeKey
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import mortar.MortarScope

@Screen(R.layout.screen_photo_detail_info)
class PhotoDetailInfoScreen(photoCard: PhotoCardDto) : AbstractScreen<RootActivity.RootComponent>(), TreeKey {

    var photoCard: PhotoCardDto = photoCard

    override fun createScreenComponent(parentComponent: RootActivity.RootComponent): Any {
        return DaggerPhotoDetailInfoScreen_Component.builder()
                .rootComponent(parentComponent)
                .module(Module())
                .build()
    }

    override fun getParentKey(): Any = PhotoCardListScreen()

    //region ================= Presenter =================
    inner class PhotoDetailInfoPresenter : AbstractPresenter<PhotoDetailInfoView, PhotoDetailInfoModel>() {

        override fun onLoad(savedInstanceState: Bundle?) {
            super.onLoad(savedInstanceState)
            view.showImageInfo(photoCard)

            mModel.getUserById(photoCard.owner)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeBy(onNext = {view.showOwnerInfo(it)})
        }

        override fun initToolbar() {
            mRootPresenter.newActionBarBuilder()
                    .setVisible(true)
                    .setTitle("Фотокарточка")
                    .setBackArrow(true)
                    .build()
        }

        override fun initDagger(scope: MortarScope) {
            (scope.getService<Any>(DaggerService.SERVICE_NAME) as DaggerPhotoDetailInfoScreen_Component).inject(this)
        }

    }
    //endregion

    //region ================= DI =================
    @dagger.Module
    inner class Module {

        @Provides
        @DaggerScope(PhotoDetailInfoView::class)
        internal fun providePresenter() : PhotoDetailInfoPresenter {
            return PhotoDetailInfoPresenter()
        }

        @Provides
        @DaggerScope(PhotoDetailInfoView::class)
        internal fun provideModel() : PhotoDetailInfoModel {
            return PhotoDetailInfoModel()
        }
    }

    @dagger.Component(dependencies = arrayOf(RootActivity.RootComponent::class), modules = arrayOf(Module::class))
    @DaggerScope(PhotoDetailInfoView::class)
    interface Component {
        fun inject(presenter: PhotoDetailInfoPresenter)
        fun inject(view: PhotoDetailInfoView)
    }
    //endregion
}
