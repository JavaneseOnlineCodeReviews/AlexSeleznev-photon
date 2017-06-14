package com.applications.whazzup.photomapp.ui.screens.user_profile_auth


import com.applications.whazzup.photomapp.R
import com.applications.whazzup.photomapp.data.network.req.AddAlbumReq
import com.applications.whazzup.photomapp.data.network.res.user.UserRes
import com.applications.whazzup.photomapp.di.DaggerScope
import com.applications.whazzup.photomapp.di.DaggerService
import com.applications.whazzup.photomapp.flow.AbstractScreen
import com.applications.whazzup.photomapp.flow.Screen
import com.applications.whazzup.photomapp.mvp.models.UserProfileModel
import com.applications.whazzup.photomapp.mvp.presenters.AbstractPresenter
import com.applications.whazzup.photomapp.mvp.presenters.MenuItemHolder
import com.applications.whazzup.photomapp.ui.activities.RootActivity
import com.applications.whazzup.photomapp.ui.screens.user_profile_idle.UserProfileIdleScreen
import dagger.Provides
import flow.Direction
import flow.Flow
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import mortar.MortarScope



@Screen(R.layout.screen_user_profile)
class UserProfileAuthScreen : AbstractScreen<RootActivity.RootComponent>() {


    override fun createScreenComponent(parentComponent: RootActivity.RootComponent): Any {
        return DaggerUserProfileAuthScreen_Component.builder().rootComponent(parentComponent).userProfileModule(UserProfileModule()).build()
    }


    //region===============================Presenter==========================

    inner class UserProfilePresenter : AbstractPresenter<UserProfileAuthView, UserProfileModel>(){

        override fun initDagger(scope: MortarScope?) {
            (scope!!.getService<Any>(DaggerService.SERVICE_NAME) as DaggerUserProfileAuthScreen_Component).inject(this)
        }

        override fun onEnterScope(scope: MortarScope?) {
            super.onEnterScope(scope)
             var res : UserRes? = null
            mModel.getUserById() .subscribeOn(Schedulers.io())
                    .doOnNext { res = it }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeBy (onComplete = {
                        view.initView(res)
                    })
        }

        override fun initToolbar() {
            mRootPresenter.newActionBarBuilder()
                    .setVisible(true)
                    .setTitle("Профиль")
                    .addAction(MenuItemHolder("Добавить альбом", R.layout.add_album_menu_item,listener =  {
                        view.addAlbum(it)
                        true
                    }))
                    .addAction(MenuItemHolder("Пункты меню", R.layout.dots_menu_item, listener = {
                        view.showPopupMenu(it)
                        true
                    }))
                    .build()
        }

        fun isUserAuth(): Boolean {
            return mModel.isUserAuth()
        }

        fun getCardCount(res: UserRes?): CharSequence? {
            var count : Int = 0
            for(item in res?.albums!!){
                count+=item.photocards.size
            }
            return count.toString()
        }

        fun addAlbum(addAlbumReq: AddAlbumReq) {
            mModel.createAlbum(addAlbumReq).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnNext { view.userAdapter.addAlbum(it) }
                    .subscribeBy (onComplete = {
                        mRootPresenter.rootView?.showMessage("Новый альбом успешно создан.")
                        view.hideDialog()
                    }, onError = {
                        it.printStackTrace()
                        mRootPresenter.rootView?.showMessage("Такой альбом уже существует.")
                    })
        }

        fun deleteUser() {
            mModel.deleteUser().subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeBy (onComplete = {
                        Flow.get(view).replaceTop(UserProfileIdleScreen(), Direction.REPLACE)
                    })
            mModel.logOut()
        }

    }

    //endregion

    //region===============================DI==========================

    @dagger.Module
    inner class UserProfileModule{
        @Provides
        @DaggerScope(UserProfileAuthScreen:: class)
        internal fun providePresenter(): UserProfilePresenter{
            return UserProfilePresenter()
        }

        @Provides
        @DaggerScope(UserProfileAuthScreen:: class)
        internal  fun provideModel(): UserProfileModel{
            return  UserProfileModel()
        }
    }

    @dagger.Component(dependencies = arrayOf(RootActivity.RootComponent::class), modules = arrayOf(UserProfileModule::class))
    @DaggerScope(UserProfileAuthScreen::class)
    interface Component{
        fun inject(view : UserProfileAuthView)
        fun inject(presenter : UserProfilePresenter)
        fun inject(adapter: UserProfileAlbumRecycler)
    }

    //endregion
}

