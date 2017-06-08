package com.applications.whazzup.photomapp.ui.screens.photo_card_list



import android.view.View
import android.widget.PopupMenu
import com.applications.whazzup.photomapp.R
import com.applications.whazzup.photomapp.R.id.*
import com.applications.whazzup.photomapp.di.DaggerScope
import com.applications.whazzup.photomapp.di.DaggerService
import com.applications.whazzup.photomapp.flow.AbstractScreen
import com.applications.whazzup.photomapp.flow.Screen
import com.applications.whazzup.photomapp.mvp.models.PhotoCardListModel
import com.applications.whazzup.photomapp.mvp.presenters.AbstractPresenter
import com.applications.whazzup.photomapp.mvp.presenters.MenuItemHolder
import com.applications.whazzup.photomapp.ui.activities.RootActivity
import com.applications.whazzup.photomapp.ui.screens.filter.FilterScreen
import dagger.Provides
import flow.Flow
import mortar.MortarScope

@Screen(R.layout.screen_photo_card_list)
class PhotoCardListScreen : AbstractScreen<RootActivity.RootComponent>() {

    override fun createScreenComponent(parentComponent: RootActivity.RootComponent): Any {
        return DaggerPhotoCardListScreen_Component.builder().rootComponent(parentComponent).module(Module()).build()
    }

    // region================Presenter==============
    inner class PhotoCardListPresenter : AbstractPresenter<PhotoCardListView, PhotoCardListModel>() {


        override fun initToolbar() {
            mRootPresenter.newActionBarBuilder()
                    .setVisible(true)
                    .setTitle("Фотон")
                    .addAction(MenuItemHolder("Поиск", R.layout.search_menu_item,listener =  {
                        showFilterScreen()
                        true
                    }))
                    .addAction(MenuItemHolder("Настройки", R.layout.settings_menu_item,listener =  {
                        showPopUpMenu(it)
                        true
                    }))
                    .build()
        }

        fun showFilterScreen() {
            Flow.get(view.context).set(FilterScreen())
        }

        fun showPopUpMenu(view : View){
            var menu = PopupMenu(getView().context, view)
            if(mRootPresenter.isUserAuth()){
                menu.inflate(R.menu.popup_exit)
            }else {
                menu.inflate(R.menu.popup)
            }
            menu.setOnMenuItemClickListener({
                when(it.itemId){
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
