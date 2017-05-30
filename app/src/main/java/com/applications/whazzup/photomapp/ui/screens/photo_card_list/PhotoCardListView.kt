package com.applications.whazzup.photomapp.ui.screens.photo_card_list

import android.content.Context
import android.util.AttributeSet

import com.applications.whazzup.photomapp.di.DaggerService
import com.applications.whazzup.photomapp.mvp.views.AbstractView


class PhotoCardListView(context: Context, attrs: AttributeSet) : AbstractView<PhotoCardListScreen.PhotoCardListPresenter>(context, attrs) {

    override fun viewOnBackPressed(): Boolean {
        return false
    }

    override fun initDagger(context: Context) {
        DaggerService.getDaggerComponent<DaggerPhotoCardListScreen_Component>(context).inject(this)
    }
}
