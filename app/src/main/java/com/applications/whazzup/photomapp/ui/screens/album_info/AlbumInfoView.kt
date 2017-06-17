package com.applications.whazzup.photomapp.ui.screens.album_info

import android.content.Context
import android.util.AttributeSet
import com.applications.whazzup.photomapp.di.DaggerService
import com.applications.whazzup.photomapp.mvp.views.AbstractView


class AlbumInfoView(context : Context, attrs : AttributeSet) : AbstractView<AlbumInfoScreen.AlbumInfoPresenter>(context, attrs) {

    override fun viewOnBackPressed(): Boolean {
        return false
    }

    override fun initDagger(context: Context?) {
        DaggerService.getDaggerComponent<DaggerAlbumInfoScreen_AlbumInfoComponent>(context!!).inject(this)
    }
}