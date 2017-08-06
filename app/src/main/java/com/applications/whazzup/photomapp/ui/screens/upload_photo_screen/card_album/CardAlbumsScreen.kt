package com.applications.whazzup.photomapp.ui.screens.upload_photo_screen.card_album

import android.os.Bundle
import com.applications.whazzup.photomapp.R
import com.applications.whazzup.photomapp.di.DaggerScope
import com.applications.whazzup.photomapp.di.DaggerService
import com.applications.whazzup.photomapp.flow.AbstractScreen
import com.applications.whazzup.photomapp.flow.Screen
import com.applications.whazzup.photomapp.mvp.models.CardAlbumsModel
import com.applications.whazzup.photomapp.mvp.presenters.AbstractPresenter
import com.applications.whazzup.photomapp.ui.screens.upload_photo_screen.UploadCardInfoScreen
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import mortar.MortarScope
import javax.inject.Inject

@Screen(R.layout.content_card_albums)
class CardAlbumsScreen : AbstractScreen<UploadCardInfoScreen.UploadCardInfoComponent>() {



    override fun createScreenComponent(parentComponent: UploadCardInfoScreen.UploadCardInfoComponent): Any {
        return DaggerCardAlbumsScreen_CardAlbumsComponent.builder().uploadCardInfoComponent(parentComponent).cardAlbumsModule(CardAlbumsModule()).build()
    }

    // region================Presenter==============

    inner class CardAlbumsPresenter : AbstractPresenter<CardAlbumsView, CardAlbumsModel>(){
        @Inject
        lateinit var uploadCardInfoPresneter : UploadCardInfoScreen.UploaCardInfoPresenter
        override fun initDagger(scope: MortarScope?) {
            (scope?.getService<Any>(DaggerService.SERVICE_NAME) as DaggerCardAlbumsScreen_CardAlbumsComponent).inject(this)
        }

        override fun initToolbar() {

        }

        override fun onEnterScope(scope: MortarScope?) {
            super.onEnterScope(scope)
            getAllAlbumFromRealm()
        }

        override fun onLoad(savedInstanceState: Bundle?) {
            super.onLoad(savedInstanceState)
            mModel.getAllAlbumsFromRealm().filter { it.active }
                    .doOnNext { view.mCardAlbumsAdapter.addAlbum(it) }
                    .subscribeBy(onComplete = {view.initView()})
        }

            fun getAllAlbumFromRealm(){

        }

        fun chooseAlbum(albumId: String) {
            uploadCardInfoPresneter.cardAlbumId = albumId
            mRootPresenter.rootView?.showMessage("Вы выбрали альбом "+ albumId)
        }

    }
    // endregion
    // region================DI==============

    @dagger.Module
    inner class CardAlbumsModule{
        @Provides
        @DaggerScope(CardAlbumsScreen :: class)
        internal fun providePresenter() : CardAlbumsPresenter{
            return  CardAlbumsPresenter()
        }

        @Provides
        @DaggerScope(CardAlbumsScreen :: class)
        internal fun provideModel() : CardAlbumsModel{
            return CardAlbumsModel()
        }
    }

    @dagger.Component(dependencies = arrayOf(UploadCardInfoScreen.UploadCardInfoComponent :: class), modules =  arrayOf(CardAlbumsModule :: class))
    @DaggerScope(CardAlbumsScreen :: class)
    interface CardAlbumsComponent{
        fun inject(presenter : CardAlbumsPresenter)
        fun inject(view : CardAlbumsView)
        fun inject(adapter : CardAlbumAdapter)
    }

    // endregion
}