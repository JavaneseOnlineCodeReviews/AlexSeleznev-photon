package com.applications.whazzup.photomapp.ui.screens.upload_photo_screen.card_filters

import android.os.Bundle
import com.applications.whazzup.photomapp.R
import com.applications.whazzup.photomapp.di.DaggerScope
import com.applications.whazzup.photomapp.di.DaggerService
import com.applications.whazzup.photomapp.flow.AbstractScreen
import com.applications.whazzup.photomapp.flow.Screen
import com.applications.whazzup.photomapp.mvp.models.CardFiltersModel
import com.applications.whazzup.photomapp.mvp.presenters.AbstractPresenter
import com.applications.whazzup.photomapp.ui.screens.upload_photo_screen.UploadCardInfoScreen
import dagger.Provides
import mortar.MortarScope
import javax.inject.Inject


@Screen(R.layout.content_card_parametrs)
class CardFiltersScreen : AbstractScreen<UploadCardInfoScreen.UploadCardInfoComponent>() {


    override fun createScreenComponent(parentComponent: UploadCardInfoScreen.UploadCardInfoComponent): Any {
        return DaggerCardFiltersScreen_CardFiltersComponent.builder().uploadCardInfoComponent(parentComponent).cardFiltersModule(CardFiltersModule()).build()
    }

    // region================Presenter==============

    inner class CardFilterPresenter : AbstractPresenter<CardFiltersView, CardFiltersModel>(){

        @Inject
        lateinit var uploadCardInfoPresneter : UploadCardInfoScreen.UploaCardInfoPresenter
        override fun initToolbar() {

        }

        override fun onEnterScope(scope: MortarScope?) {
            super.onEnterScope(scope)

        }

        override fun onLoad(savedInstanceState: Bundle?) {
            super.onLoad(savedInstanceState)
            view.initView()
        }

        override fun initDagger(scope: MortarScope?) {

            (scope?.getService<Any>(DaggerService.SERVICE_NAME) as DaggerCardFiltersScreen_CardFiltersComponent).inject(this)
        }

        fun saveNuances(s: String) {
            uploadCardInfoPresneter.addNuances(s)
        }

        fun deleteNuances(s: String) {
            uploadCardInfoPresneter.deleteNuances(s)
        }

        fun addDish(s: String) {
            uploadCardInfoPresneter.cardDish = s
        }

        fun addDecor(s: String) {
            uploadCardInfoPresneter.cardDecor = s
        }

        fun addTemperature(s: String) {
            uploadCardInfoPresneter.cardTemperature = s
        }

        fun addLight(s: String) {
            uploadCardInfoPresneter.cardLight = s
        }

        fun addLightDirection(s: String) {
            uploadCardInfoPresneter.cardLightDirection = s
        }

        fun addLightCount(s: String) {
            uploadCardInfoPresneter.cardLightCount = s
        }

    }

    // endregion

    // region================DI==============

    @dagger.Module
    inner class CardFiltersModule{
        @Provides
        @DaggerScope(CardFiltersScreen :: class)
        internal fun providePresenter() : CardFilterPresenter{
            return CardFilterPresenter()
        }

        @Provides
        @DaggerScope(CardFiltersScreen :: class)
        internal fun provideModel() : CardFiltersModel{
            return CardFiltersModel()
        }
    }


    @dagger.Component(dependencies = arrayOf(UploadCardInfoScreen.UploadCardInfoComponent :: class), modules = arrayOf(CardFiltersModule :: class))
    @DaggerScope(CardFiltersScreen :: class)
    interface CardFiltersComponent{

        fun inject(view : CardFiltersView)
        fun inject(presenter : CardFilterPresenter)
    }
    // endregion
}
