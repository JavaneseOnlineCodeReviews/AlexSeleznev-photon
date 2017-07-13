package com.applications.whazzup.photomapp.ui.screens.photo_detail_info

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.os.Bundle
import com.applications.whazzup.photomapp.R
import com.applications.whazzup.photomapp.data.storage.dto.PhotoCardDto
import com.applications.whazzup.photomapp.di.DaggerScope
import com.applications.whazzup.photomapp.di.DaggerService
import com.applications.whazzup.photomapp.flow.AbstractScreen
import com.applications.whazzup.photomapp.flow.Screen
import com.applications.whazzup.photomapp.mvp.models.PhotoDetailInfoModel
import com.applications.whazzup.photomapp.mvp.presenters.AbstractPresenter
import com.applications.whazzup.photomapp.mvp.presenters.MenuItemHolder
import com.applications.whazzup.photomapp.mvp.presenters.RootPresenter
import com.applications.whazzup.photomapp.ui.activities.RootActivity
import com.applications.whazzup.photomapp.ui.screens.photo_card_list.PhotoCardListScreen
import com.applications.whazzup.photomapp.util.ConstantManager
import com.squareup.picasso.Picasso
import dagger.Provides
import flow.TreeKey
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import mortar.MortarScope

@Screen(R.layout.screen_photo_detail_info)
class PhotoDetailInfoScreen() : AbstractScreen<RootActivity.RootComponent>() {

    lateinit var photoCard: PhotoCardDto

    constructor( photoCar: PhotoCardDto) : this() {
        photoCard = photoCar
    }

    override fun createScreenComponent(parentComponent: RootActivity.RootComponent): Any {
        return DaggerPhotoDetailInfoScreen_Component.builder()
                .rootComponent(parentComponent)
                .module(Module())
                .build()
    }


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false
        if (!super.equals(other)) return false

        other as PhotoDetailInfoScreen

        if (photoCard != other.photoCard) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + photoCard.hashCode()
        return result
    }


    //region ================= Presenter =================
    inner class PhotoDetailInfoPresenter : AbstractPresenter<PhotoDetailInfoView, PhotoDetailInfoModel>() {

        override fun onLoad(savedInstanceState: Bundle?) {
            super.onLoad(savedInstanceState)
            view.showImageInfo(photoCard)

            mModel.addView(photoCard.id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeBy(onError = {it.printStackTrace()
                        mRootPresenter.rootView?.showMessage(it.toString())})

            mModel.getUserById(photoCard.owner)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeBy(onNext = {view.showOwnerInfo(it)}, onError = {it.printStackTrace()
                        mRootPresenter.rootView?.showMessage(it.toString())})
        }

        override fun initToolbar() {
            mRootPresenter.newActionBarBuilder()
                    .setVisible(true)
                    .setTitle("Фотокарточка")
                    .setBackArrow(true)
                    .addAction(MenuItemHolder("Пункты меню", R.layout.dots_menu_item, listener = {
                        view.showPopupMenu(it)
                        true
                    }))
                    .build()
        }

        override fun initDagger(scope: MortarScope) {
            (scope.getService<Any>(DaggerService.SERVICE_NAME) as DaggerPhotoDetailInfoScreen_Component).inject(this)
        }

        fun downloadPermissionAccess() {
            if (mRootPresenter.checkPermissionAndRequestIfNotGranted(arrayOf(WRITE_EXTERNAL_STORAGE), ConstantManager.REQUEST_DOWNLOAD_IMAGE)) {
                view.downloadImage()
            }
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
        val rootPresenter: RootPresenter
        val picasso : Picasso
    }
    //endregion
}
