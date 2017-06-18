package com.applications.whazzup.photomapp.ui.screens.upload_photo

import android.content.Context
import android.util.AttributeSet
import com.applications.whazzup.photomapp.di.DaggerService
import com.applications.whazzup.photomapp.mvp.views.AbstractView

class UploadPhotoView(context: Context, attrs: AttributeSet) : AbstractView<UploadPhotoScreen.UploadPhotoPresenter>(context, attrs) {
    override fun viewOnBackPressed(): Boolean {
        return false
    }

    override fun initDagger(context: Context?) {
        DaggerService.getDaggerComponent<DaggerUploadPhotoScreen_Component>(context!!).inject(this)
    }

}