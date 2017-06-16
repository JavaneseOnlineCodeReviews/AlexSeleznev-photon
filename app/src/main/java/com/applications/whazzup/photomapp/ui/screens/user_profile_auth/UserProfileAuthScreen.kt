package com.applications.whazzup.photomapp.ui.screens.user_profile_auth


import android.Manifest
import android.Manifest.permission.CAMERA
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.content.FileProvider
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
import com.applications.whazzup.photomapp.util.UriGetter
import dagger.Provides
import flow.Direction
import flow.Flow
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import mortar.MortarScope

import java.io.File
import java.io.IOException
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


@Screen(R.layout.screen_user_profile)
class UserProfileAuthScreen : AbstractScreen<RootActivity.RootComponent>() {

    lateinit var mPhotoFile: File


    override fun createScreenComponent(parentComponent: RootActivity.RootComponent): Any {
        return DaggerUserProfileAuthScreen_Component.builder().rootComponent(parentComponent).userProfileModule(UserProfileModule()).build()
    }


    //region===============================Presenter==========================

    inner class UserProfilePresenter : SubscribePresenter<UserProfileAuthView, UserProfileModel>() {

        lateinit var mActivityresultSub: Disposable


        override fun getRootView(): IRootView? {
            return mRootPresenter.rootView
        }

        override fun initDagger(scope: MortarScope?) {
            (scope!!.getService<Any>(DaggerService.SERVICE_NAME) as DaggerUserProfileAuthScreen_Component).inject(this)
        }

        override fun onEnterScope(scope: MortarScope?) {
            super.onEnterScope(scope)
            subscribeOnActivityResult()
        }

        override fun onLoad(savedInstanceState: Bundle?) {
            super.onLoad(savedInstanceState)
            var res: UserRes? = null
            mModel.getUserById().subscribeOn(Schedulers.io())
                    .doOnNext { res = it }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeBy(onComplete = {
                        view.initView(res)
                    })
        }

        override fun onExitScope() {
            super.onExitScope()
            mActivityresultSub.dispose()
        }

        override fun initToolbar() {
            mRootPresenter.newActionBarBuilder()
                    .setVisible(true)
                    .setTitle("Профиль")
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


        fun logOut() {
            mModel.logOut()
            Flow.get(view).replaceTop(UserProfileIdleScreen(), Direction.REPLACE)
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
            intent.action = Intent.ACTION_OPEN_DOCUMENT
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            (mRootPresenter.rootView as RootActivity).startActivityForResult(intent, ConstantManager.REQUEST_PROFILE_PHOTO)

        }

        fun subscribeOnActivityResult() {
            val activityResultObs = mRootPresenter.mActivityResultObs.filter({ activityResultDto -> activityResultDto.resultCode === Activity.RESULT_OK })
            mActivityresultSub = subscribe(activityResultObs, object : ViewSubscriber<ActivityResultDto>() {
                override fun onNext(activityResultDto: ActivityResultDto) {
                    handleActivityResult(activityResultDto)
                }
            })
        }

        fun getUserAvatar(): String {
            return mModel.getUserAvatar()
        }

        fun handleActivityResult(activityResultDto: ActivityResultDto) {
            when (activityResultDto.requestCode) {
                ConstantManager.REQUEST_PROFILE_PHOTO -> {
                    if (activityResultDto.data != null) {
                        var photoUrl = UriGetter.getPath(view.context, activityResultDto.data.data)
                        mModel.uploadPhoto(Uri.parse(photoUrl)).subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .doOnNext { mModel.saveAvatarUrl(it.image) }
                                .subscribeBy(onComplete = {
                                    view.updateAvatarPhoto(Uri.parse(mModel.getUserAvatar()))
                                })
                    }
                }
                ConstantManager.REQUEST_PROFILE_PHOTO_CAMERA -> {
                    view.updateAvatarPhoto(Uri.fromFile(mPhotoFile))
                    mModel.uploadPhoto(Uri.fromFile(mPhotoFile)).subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .doOnNext { mModel.saveAvatarUrl(it.image) }
                            .subscribeBy(onComplete = {
                                view.updateAvatarPhoto(Uri.parse(mModel.getUserAvatar()))
                            })
                }
            }
        }

        fun chooseCamera() {
            if (rootView != null) {
                val permissions = arrayOf(WRITE_EXTERNAL_STORAGE, CAMERA)
                if (mRootPresenter.checkPermissionAndRequestIfNotGranted(permissions, ConstantManager.REQUEST_PERMISSON_CAMERA)) {
                    mPhotoFile = createImageFile()
                    if (mPhotoFile == null) {
                        rootView?.showMessage("Файл не может быть создан")
                    }
                    takePhotoFromCamera()
                }
            }
        }

        private fun takePhotoFromCamera() {
            var uriForFile = FileProvider.getUriForFile((rootView as RootActivity), ConstantManager.FILE_PROVIDER_AUTHORITY, mPhotoFile)
            var intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uriForFile)
            (mRootPresenter.rootView as RootActivity).startActivityForResult(intent, ConstantManager.REQUEST_PROFILE_PHOTO_CAMERA)
        }

        @Throws(IOException::class)
        private fun createImageFile(): File {
            val dateTimeInstance = SimpleDateFormat.getTimeInstance(DateFormat.MEDIUM)
            val timeStamp = dateTimeInstance.format(Date())
            val imageFileName = "IMG_" + timeStamp
            val storageDir = view.context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            val imageFile = File.createTempFile(imageFileName, ".jpg", storageDir)
            return imageFile
        }
    }


    //endregion

    //region===============================DI==========================

    @dagger.Module
    inner class UserProfileModule {
        @Provides
        @DaggerScope(UserProfileAuthScreen::class)
        internal fun providePresenter(): UserProfilePresenter {
            return UserProfilePresenter()
        }

        @Provides
        @DaggerScope(UserProfileAuthScreen::class)
        internal fun provideModel(): UserProfileModel {
            return UserProfileModel()
        }
    }

    @dagger.Component(dependencies = arrayOf(RootActivity.RootComponent::class), modules = arrayOf(UserProfileModule::class))
    @DaggerScope(UserProfileAuthScreen::class)
    interface Component {
        fun inject(view: UserProfileAuthView)
        fun inject(presenter: UserProfilePresenter)
        fun inject(adapter: UserProfileAlbumRecycler)
    }

    //endregion
}

