package com.applications.whazzup.photomapp.ui.screens.photo_card_list

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.util.AttributeSet


import com.applications.whazzup.photomapp.di.DaggerService
import com.applications.whazzup.photomapp.mvp.views.AbstractView
import com.applications.whazzup.photomapp.ui.screens.photo_detail_info.PhotoDetailInfoScreen
import com.applications.whazzup.photomapp.util.RecyclerClickListener
import flow.Flow

import kotlinx.android.synthetic.main.screen_photo_card_list.view.*


class PhotoCardListView(context: Context, attrs: AttributeSet) : AbstractView<PhotoCardListScreen.PhotoCardListPresenter>(context, attrs) {


    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        with(card_lit_recycler){
            layoutManager = GridLayoutManager(context, 2)
            adapter = PhotoCardListAdapter(mPresenter.mRootPresenter.mRootModel.cardList)
            (adapter as PhotoCardListAdapter).addListener(object: RecyclerClickListener {
                override fun onItemClick(position: Int) {
                    Flow.get(context).set(PhotoDetailInfoScreen())
                }
            })
        }
    }

    override fun viewOnBackPressed(): Boolean {
        return false
    }

    override fun initDagger(context: Context) {
        DaggerService.getDaggerComponent<DaggerPhotoCardListScreen_Component>(context).inject(this)
    }
}
