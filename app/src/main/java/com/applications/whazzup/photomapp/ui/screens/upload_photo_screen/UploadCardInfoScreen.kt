package com.applications.whazzup.photomapp.ui.screens.upload_photo_screen

import com.applications.whazzup.photomapp.R
import com.applications.whazzup.photomapp.data.network.req.card_info_req.CardInfoFilters
import com.applications.whazzup.photomapp.data.network.req.card_info_req.CardInfoReq
import com.applications.whazzup.photomapp.data.network.res.photocard.PhotocardRes
import com.applications.whazzup.photomapp.di.DaggerScope
import com.applications.whazzup.photomapp.di.DaggerService
import com.applications.whazzup.photomapp.flow.AbstractScreen
import com.applications.whazzup.photomapp.flow.Screen
import com.applications.whazzup.photomapp.mvp.models.UploaCardInfoModel
import com.applications.whazzup.photomapp.mvp.presenters.AbstractPresenter
import com.applications.whazzup.photomapp.mvp.presenters.RootPresenter
import com.applications.whazzup.photomapp.ui.activities.RootActivity
import com.applications.whazzup.photomapp.ui.screens.upload_photo_screen.card_name.CardNameView
import com.squareup.picasso.Picasso
import dagger.Provides
import flow.Flow
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import mortar.MortarScope



@Screen(R.layout.screen_upload_card_info)
class UploadCardInfoScreen(var uploadMode : Int) : AbstractScreen<RootActivity.RootComponent>(){

    lateinit var card : PhotocardRes

    constructor(uploadMode : Int, photoCard : PhotocardRes) : this(uploadMode) {
            this.uploadMode = uploadMode
            card = photoCard
    }

    override fun createScreenComponent(parentComponent: RootActivity.RootComponent): Any {
       return DaggerUploadCardInfoScreen_UploadCardInfoComponent.builder()
               .rootComponent(parentComponent).uploadCardInfoModule(UploadCardInfoModule())
               .build()
    }

    // region================Presenter==============

    inner class UploaCardInfoPresenter : AbstractPresenter<UploadCardInfoView, UploaCardInfoModel>(){

        var cardName : String
        var cardTags : MutableList<String>
        var cardNuances : MutableList<String>
        var someNuances : MutableList<String>
        var cardDish : String
        var cardDecor : String
        var cardTemperature : String
        var cardAlbumId : String
        var cardLight : String
        var cardLightDirection : String
        var cardLightCount : String

init{
    if(uploadMode ==1){
        this.cardName = card.title
        this.cardTags  = card.tags as MutableList<String>
        this.cardNuances = mutableListOf()
        this.someNuances = card.filters.nuances.split(",") as MutableList<String>
        this.cardDish = card.filters.dish
        this.cardDecor= card.filters.decor
        this.cardTemperature=card.filters.temperature
        this.cardAlbumId = card.id
        this.cardLight = card.filters.light
        this.cardLightDirection = card.filters.lightDirection
        this.cardLightCount = card.filters. lightSource
    }else{
        this.cardName = ""
        this.cardTags  = mutableListOf<String>()
        this.cardNuances = mutableListOf<String>()
        this.someNuances = mutableListOf()
        this.cardDish = ""
        this.cardDecor=""
        this.cardTemperature=""
        this.cardAlbumId = ""
        this.cardLight = ""
        this.cardLightDirection = ""
        this.cardLightCount = ""
    }
}


        override fun onEnterScope(scope: MortarScope?) {
            super.onEnterScope(scope)
            if(uploadMode==1){
                //view.initView()
            }
        }

        override fun onExitScope() {
            super.onExitScope()
            someNuances = mutableListOf()
        }

        override fun initDagger(scope: MortarScope?) {
            (scope?.getService<Any>(DaggerService.SERVICE_NAME) as DaggerUploadCardInfoScreen_UploadCardInfoComponent).inject(this)
        }

        override fun initToolbar() {
            mRootPresenter.ActionBarBuilder().setVisible(true).setTitle("Фотон").build()
        }

        fun addNuances(s: String) {
            cardNuances.add(s)

        }

        fun deleteNuances(s: String) {
            cardNuances.remove(s)

        }



        fun saveCard() {
            val filters = CardInfoFilters(cardDish, cardNuances, cardDecor, cardTemperature, cardLight, cardLightDirection, cardLightCount)
            if(uploadMode==0) {
                if (!(cardName.equals("")) && !(cardDish.equals("")) && !(cardDecor.equals("")) && !(cardTemperature.equals("")) && !(cardAlbumId.equals(""))
                        && !(cardLight.equals("")) && !(cardLightDirection.equals("")) && !(cardLightCount.equals(""))) {
                    mModel.uploaCardToServer(CardInfoReq(cardName, cardAlbumId, mModel.getCardUrl(), cardTags, filters))
                    mModel.subj.subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeBy(onNext = {
                                mRootPresenter.rootView?.showMessage(it)
                            })
                    Flow.get(view).goBack()
                } else {
                    mRootPresenter.rootView?.showMessage("Введите название фотокарточки, а так же выбирите фильтры и укажите альбом")
                }
            }
            else{
                mModel.updateCardToServer(card.id, CardInfoReq(cardName, cardAlbumId, card.photo, cardTags, filters)).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeBy(
                        onNext = {
                            mRootPresenter.rootView?.showMessage(it.id)
                        }, onComplete = {
                            Flow.get(view).goBack()

                }, onError = {it.printStackTrace()}
                )
            }
        }

    }

    // endregion

    // region================DI==============

    @dagger.Module
    inner class UploadCardInfoModule{
        @Provides
        @DaggerScope(UploadCardInfoScreen :: class)
        internal fun providePresenter() : UploaCardInfoPresenter{
            return UploaCardInfoPresenter()
        }

        @Provides
        @DaggerScope(UploadCardInfoScreen:: class)
        internal fun provideModel() : UploaCardInfoModel{
            return UploaCardInfoModel()
        }
    }

    @dagger.Component(dependencies = arrayOf(RootActivity.RootComponent :: class), modules = arrayOf(UploadCardInfoModule :: class))
    @DaggerScope(UploadCardInfoScreen :: class)
    interface UploadCardInfoComponent{
        fun inject(view : UploadCardInfoView)
        fun inject(presenter : UploaCardInfoPresenter)
        val rootPresenter: RootPresenter
        val presenter : UploaCardInfoPresenter
        val picasso: Picasso
    }

    // endregion
}