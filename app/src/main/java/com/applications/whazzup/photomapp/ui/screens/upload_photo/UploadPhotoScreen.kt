package com.applications.whazzup.photomapp.ui.screens.upload_photo

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import com.applications.whazzup.photomapp.R
import com.applications.whazzup.photomapp.data.storage.dto.ActivityResultDto
import com.applications.whazzup.photomapp.di.DaggerScope
import com.applications.whazzup.photomapp.di.DaggerService
import com.applications.whazzup.photomapp.flow.AbstractScreen
import com.applications.whazzup.photomapp.flow.Screen
import com.applications.whazzup.photomapp.mvp.models.UploadPhotoModel
import com.applications.whazzup.photomapp.mvp.presenters.SubscribePresenter
import com.applications.whazzup.photomapp.mvp.views.IRootView
import com.applications.whazzup.photomapp.ui.activities.RootActivity
import com.applications.whazzup.photomapp.util.ConstantManager
import com.applications.whazzup.photomapp.util.UriGetter
import dagger.Provides
import io.reactivex.disposables.Disposable
import mortar.MortarScope

@Screen(R.layout.screen_upload_photo)
class UploadPhotoScreen : AbstractScreen<RootActivity.RootComponent>() {
    override fun createScreenComponent(parentComponent: RootActivity.RootComponent): Any {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return DaggerUploadPhotoScreen_Component.builder().rootComponent(parentComponent).uploadPhotoModule(UploadPhotoModule()).build()
//        return null!!
    }



    //region===============================Presenter==========================

    inner class UploadPhotoPresenter : SubscribePresenter<UploadPhotoView, UploadPhotoModel>() {
        lateinit var mActivityresultSub: Disposable

        override fun getRootView(): IRootView? {
            return mRootPresenter.rootView
        }

        override fun initDagger(scope: MortarScope?) {
            (scope!!.getService<Any>(DaggerService.SERVICE_NAME) as DaggerUploadPhotoScreen_Component).inject(this)
        }

        override fun initToolbar() {
            mRootPresenter.newActionBarBuilder().setVisible(false).setTitle("Добавление фотографии").build()
        }

        override fun onEnterScope(scope: MortarScope?) {
            super.onEnterScope(scope)
            subscribeOnActivityResult()
        }


        fun uploadPhoto() {
            chooseGallery();
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


        fun handleActivityResult(activityResultDto: ActivityResultDto) {
            when (activityResultDto.requestCode) {
                ConstantManager.REQUEST_PROFILE_PHOTO -> {
                    if (activityResultDto.data != null) {
                        var photoUrl = UriGetter.getPath(view.context, activityResultDto.data.data)
                        mModel.uploadAvatarOnServer(Uri.parse(photoUrl).toString());
                    }
                }
            }
        }

    }

    //endregion



    //region===============================DI==========================

    @dagger.Module
    inner class UploadPhotoModule {
        @Provides
        @DaggerScope(UploadPhotoScreen :: class)
        internal fun providePresenter(): UploadPhotoPresenter {
            return UploadPhotoPresenter()
        }

        @Provides
        @DaggerScope(UploadPhotoScreen :: class)
        internal  fun provideModel(): UploadPhotoModel {
            return  UploadPhotoModel()
        }
    }

    @dagger.Component(dependencies = arrayOf(RootActivity.RootComponent::class), modules = arrayOf(UploadPhotoModule::class))
    @DaggerScope(UploadPhotoScreen::class)
    interface Component {
        fun inject(view : UploadPhotoView)
        fun inject(presenter : UploadPhotoPresenter)
    }

    //endregion
}