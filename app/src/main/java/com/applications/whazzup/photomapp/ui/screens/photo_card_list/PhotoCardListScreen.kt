package com.applications.whazzup.photomapp.ui.screens.photo_card_list



import android.view.View
import android.widget.PopupMenu
import com.applications.whazzup.photomapp.R
import com.applications.whazzup.photomapp.R.id.*
import com.applications.whazzup.photomapp.data.storage.dto.PhotoCardDto
import com.applications.whazzup.photomapp.di.DaggerScope
import com.applications.whazzup.photomapp.di.DaggerService
import com.applications.whazzup.photomapp.flow.AbstractScreen
import com.applications.whazzup.photomapp.flow.Screen
import com.applications.whazzup.photomapp.mvp.models.PhotoCardListModel
import com.applications.whazzup.photomapp.mvp.presenters.AbstractPresenter
import com.applications.whazzup.photomapp.mvp.presenters.MenuItemHolder
import com.applications.whazzup.photomapp.ui.activities.RootActivity
import com.applications.whazzup.photomapp.ui.screens.search.SearchScreen
import dagger.Provides
import flow.Flow
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import mortar.MortarScope

@Screen(R.layout.screen_photo_card_list)
class PhotoCardListScreen : AbstractScreen<RootActivity.RootComponent>() {

    override fun createScreenComponent(parentComponent: RootActivity.RootComponent): Any {
        return DaggerPhotoCardListScreen_Component.builder().rootComponent(parentComponent).module(Module()).build()
    }

    // region================Presenter==============
    inner class PhotoCardListPresenter : AbstractPresenter<PhotoCardListView, PhotoCardListModel>() {


        override fun onEnterScope(scope: MortarScope?) {
            super.onEnterScope(scope)

            mModel.getCardObs()
                    .filter { it.active }
                    .doOnNext {view.cardAdapter.addItem(PhotoCardDto(it))}
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeBy(onComplete = {view.initView()})
            /*mModel.mDataManager.getPhotoCard(1000, 0)
                    .flatMap { Observable.fromIterable(it) }
                    .sorted { o1, o2 -> compareValues(o1, o2)  }
                    .filter { it.active }
                    .doOnNext {
                        mModel.mRealmManager.savePhotocardResponseToRealm(it)
                        mRootPresenter.mRootModel.addToCardList(PhotoCardDto(it))
                    }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeBy()*/
        }

        override fun initToolbar() {
            mRootPresenter.newActionBarBuilder()
                    .setVisible(true)
                    .setTitle("Фотон")
                    .addAction(MenuItemHolder("Поиск", R.layout.search_menu_item,listener =  {
                        showSearchScreen()
                        true
                    }))
                    .addAction(MenuItemHolder("Настройки", R.layout.settings_menu_item,listener =  {
                        showPopUpMenu(it)
                        true
                    }))
                    .build()
        }


        fun showSearchScreen() {
            Flow.get(view).set(SearchScreen())
        }

        fun showPopUpMenu(view : View){
            var menu = PopupMenu(view.context, view)
            if(mRootPresenter.isUserAuth()){
                menu.inflate(R.menu.popup_exit)
            }else {
                menu.inflate(R.menu.popup)
            }
            menu.setOnMenuItemClickListener({
                when(it.itemId) {
                   sign_in_item->{
                       mRootPresenter.rootView?.createSignInAlertDialog()
                   }
                    log_in_item->{
                        mRootPresenter.rootView?.createLoginDialog()
                    }
                    log_out_item->{
                        mRootPresenter.logOut()
                    }
                }
                false
            })
            menu.show()
        }

        override fun initDagger(scope: MortarScope) {
            (scope.getService<Any>(DaggerService.SERVICE_NAME) as DaggerPhotoCardListScreen_Component).inject(this)
        }
    }

    // endregion

    // region================DI==============

    @dagger.Module
    inner class Module {
        @Provides
        @DaggerScope(PhotoCardListScreen::class)
        internal fun providePresenter(): PhotoCardListPresenter {
            return PhotoCardListPresenter()
        }

        @Provides
        @DaggerScope(PhotoCardListScreen::class)
        internal fun provideModel(): PhotoCardListModel {
            return PhotoCardListModel()
        }
    }

    @dagger.Component(dependencies = arrayOf(RootActivity.RootComponent::class), modules = arrayOf(PhotoCardListScreen.Module::class))
    @DaggerScope(PhotoCardListScreen::class)
    interface Component {
        fun inject(presenter: PhotoCardListPresenter)
        fun inject(view: PhotoCardListView)
        fun inject(cardAdapter: PhotoCardListAdapter)
    }

    // endregion
}
