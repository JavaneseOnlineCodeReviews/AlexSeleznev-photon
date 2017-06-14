package com.applications.whazzup.photomapp.ui.screens.user_profile_auth


import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import com.applications.whazzup.photomapp.R
import com.applications.whazzup.photomapp.data.network.req.AddAlbumReq
import com.applications.whazzup.photomapp.data.network.res.user.UserRes
import com.applications.whazzup.photomapp.data.storage.dto.ActivityResultDto
import com.applications.whazzup.photomapp.di.DaggerScope
import com.applications.whazzup.photomapp.di.DaggerService
import com.applications.whazzup.photomapp.flow.AbstractScreen
import com.applications.whazzup.photomapp.flow.Screen
import com.applications.whazzup.photomapp.mvp.models.UserProfileModel
import com.applications.whazzup.photomapp.mvp.presenters.MenuItemHolder
import com.applications.whazzup.photomapp.mvp.presenters.SubscribePresenter
import com.applications.whazzup.photomapp.mvp.views.IRootView
import com.applications.whazzup.photomapp.ui.activities.RootActivity
import com.applications.whazzup.photomapp.ui.screens.user_profile_idle.UserProfileIdleScreen
import com.applications.whazzup.photomapp.util.ConstantManager
import dagger.Provides
import flow.Direction
import flow.Flow
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import mortar.MortarScope
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription


@Screen(R.layout.screen_user_profile)
class UserProfileAuthScreen : AbstractScreen<RootActivity.RootComponent>() {


    override fun createScreenComponent(parentComponent: RootActivity.RootComponent): Any {
        return DaggerUserProfileAuthScreen_Component.builder().rootComponent(parentComponent).userProfileModule(UserProfileModule()).build()
    }


    //region===============================Presenter==========================

    inner class UserProfilePresenter : SubscribePresenter<UserProfileAuthView, UserProfileModel>() {

        lateinit var mActivityresultSub : Disposable


        override fun getRootView(): IRootView? {
            return mRootPresenter.rootView
        }

        override fun initDagger(scope: MortarScope?) {
            (scope!!.getService<Any>(DaggerService.SERVICE_NAME) as DaggerUserProfileAuthScreen_Component).inject(this)
        }

        override fun onEnterScope(scope: MortarScope?) {
            super.onEnterScope(scope)
            var res: UserRes? = null
            mModel.getUserById().subscribeOn(Schedulers.io())
                    .doOnNext { res = it }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeBy(onComplete = {
                        view.initView(res)
                    })
        }

        override fun initToolbar() {
            mRootPresenter.newActionBarBuilder()
                    .setVisible(true)
                    .setTitle("Профиль")
                    .addAction(MenuItemHolder("Добавить альбом", R.layout.add_album_menu_item, listener = {
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
            val count: Int = res?.albums!!.sumBy { it.photocards.size }
            return count.toString()
        }

        fun addAlbum(addAlbumReq: AddAlbumReq) {

            mModel.createAlbum(addAlbumReq).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnNext { view.userAdapter.addAlbum(it) }
                    .subscribeBy(onComplete = {
                        mRootPresenter.rootView?.showMessage("Новый альбом успешно создан.")
                        view.incrementAlbumCount()
                        view.hideDialog()
                    }, onError = {
                        it.printStackTrace()
                        mRootPresenter.rootView?.showMessage("Такой альбом уже существует.")
                    })
        }


        fun deleteUser() {
            mModel.deleteUser().subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeBy(onComplete = {
                        Flow.get(view).replaceTop(UserProfileIdleScreen(), Direction.REPLACE)
                    })
            mModel.logOut()
        }

        fun chooseGallery() {
            if (rootView != null) {
                val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                if (mRootPresenter.checkPermissionAndRequestIfNotGranted(permissions, ConstantManager.REQUEST_PERMISSION_READ_EXTERNAL_STORAGE)) {
                    takePhotoFromGallery()
                }
            }
        }


        private fun takePhotoFromGallery() {
            var intent = Intent()
            intent.type = "image/*"
            intent.setAction(Intent.ACTION_OPEN_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            (mRootPresenter.rootView as RootActivity).startActivityForResult(intent, ConstantManager.REQUEST_PROFILE_PHOTO)

        }

        fun subscribeOnActivityResult(){
            val activityResultObs = mRootPresenter.mActivityResultObs.filter({ activityResultDto -> activityResultDto.resultCode === Activity.RESULT_OK })
            mActivityresultSub = subscribe(activityResultObs, object : ViewSubscriber<ActivityResultDto>() {
                override fun onNext(activityResultDto: ActivityResultDto) {
                    handleActivityResult(activityResultDto)
                }
            })

        }

        private fun handleActivityResult(activityResultDto: ActivityResultDto) {
            when(activityResultDto.requestCode){
                ConstantManager.REQUEST_PROFILE_PHOTO_CAMERA->{
                    if(activityResultDto.data!=null){
                       var  photoUrl = activityResultDto.data.data.toString()
                        view.updateAvatarPhoto(Uri.parse(photoUrl))
                    }
                }
            }
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

