package com.applications.whazzup.photomapp.ui.screens.upload_photo_screen.card_name

import android.os.Bundle
import com.applications.whazzup.photomapp.R
import com.applications.whazzup.photomapp.di.DaggerScope
import com.applications.whazzup.photomapp.di.DaggerService
import com.applications.whazzup.photomapp.flow.AbstractScreen
import com.applications.whazzup.photomapp.flow.Screen
import com.applications.whazzup.photomapp.mvp.models.CardNameModel
import com.applications.whazzup.photomapp.mvp.presenters.AbstractPresenter
import com.applications.whazzup.photomapp.ui.screens.upload_photo_screen.UploadCardInfoAdapter
import com.applications.whazzup.photomapp.ui.screens.upload_photo_screen.UploadCardInfoScreen
import dagger.Provides
import mortar.MortarScope
import javax.inject.Inject

@Screen(R.layout.content_card_name)
class CardNameScreen() : AbstractScreen<UploadCardInfoScreen.UploadCardInfoComponent>() {

    var aName : String = ""

    constructor(albumName : String) : this() {
        this.aName = albumName
    }

    override fun createScreenComponent(parentComponent: UploadCardInfoScreen.UploadCardInfoComponent): Any {
      return DaggerCardNameScreen_cardNameComponent.builder().uploadCardInfoComponent(parentComponent)
               .cardNameModule(CardNameModule()).build()
    }

    // region================Presenter==============

    inner class CardNamePresenter : AbstractPresenter<CardNameView, CardNameModel>(){

        @Inject
        lateinit var uploadCardInfoPresneter : UploadCardInfoScreen.UploaCardInfoPresenter

        override fun initDagger(scope: MortarScope?) {
            (scope?.getService<Any>(DaggerService.SERVICE_NAME) as DaggerCardNameScreen_cardNameComponent).inject(this)
        }

        override fun onEnterScope(scope: MortarScope?) {
            super.onEnterScope(scope)

        }

        override fun onLoad(savedInstanceState: Bundle?) {
            super.onLoad(savedInstanceState)
            view.initView(aName)
        }

        override fun initToolbar() {
        }

        fun getTagsFromDb() : List<String> {
            val list = mModel.getTagsFromDb().map { it.name!! }
            return list
        }

        fun saveCardName(toString: String) {
            uploadCardInfoPresneter.cardName = toString
        }


    }
    // endregion

    // region================DI==============

    @dagger.Module
    inner class CardNameModule{
        @Provides
        @DaggerScope(CardNameScreen :: class)
        internal fun providePresenter() : CardNamePresenter{
            return CardNamePresenter()
        }

        @Provides
        @DaggerScope(CardNameScreen :: class)
        internal fun provideModel() : CardNameModel{
            return CardNameModel()
        }
    }

    @dagger.Component(dependencies = arrayOf(UploadCardInfoScreen.UploadCardInfoComponent :: class), modules = arrayOf(CardNameModule :: class))
    @DaggerScope(CardNameScreen :: class)
    interface cardNameComponent{
        fun inject(presenter : CardNamePresenter)
        fun inject(view : CardNameView)
    }

    // endregion
}