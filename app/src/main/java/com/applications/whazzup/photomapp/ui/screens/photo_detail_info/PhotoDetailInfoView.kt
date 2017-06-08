package com.applications.whazzup.photomapp.ui.screens.photo_detail_info

import android.content.Context
import android.util.AttributeSet
import com.applications.whazzup.photomapp.di.DaggerService
import com.applications.whazzup.photomapp.mvp.views.AbstractView

class PhotoDetailInfoView(context: Context, attrs: AttributeSet) : AbstractView<PhotoDetailInfoScreen.PhotoDetailInfoPresenter>(context, attrs) {

    //region ================= AbstractView =================

    override fun viewOnBackPressed(): Boolean {
        return false
    }

    override fun initDagger(context: Context) {
        DaggerService.getDaggerComponent<DaggerPhotoDetailInfoScreen_Component>(context).inject(this)
    }

    //endregion
}

