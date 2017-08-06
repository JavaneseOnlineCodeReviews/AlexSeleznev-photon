package com.applications.whazzup.photomapp.ui.screens.upload_photo_screen.card_album

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import butterknife.BindView
import com.applications.whazzup.photomapp.R
import com.applications.whazzup.photomapp.di.DaggerService
import com.applications.whazzup.photomapp.mvp.views.AbstractView


class CardAlbumsView(context : Context, attrs : AttributeSet) : AbstractView<CardAlbumsScreen.CardAlbumsPresenter>(context, attrs) {

    @BindView(R.id.card_albums_recycler)
    lateinit var recycler : RecyclerView

    var mCardAlbumsAdapter = CardAlbumAdapter(context)

    override fun viewOnBackPressed(): Boolean {
        return false
    }

    override fun initDagger(context: Context?) {
        DaggerService.getDaggerComponent<DaggerCardAlbumsScreen_CardAlbumsComponent>(context!!).inject(this)
    }

    fun initView() {
        with(recycler){
            layoutManager = GridLayoutManager(context, 2)
            adapter = mCardAlbumsAdapter
            (adapter as CardAlbumAdapter).addListener { mPresenter.chooseAlbum((adapter as CardAlbumAdapter).getItemByPosition(it).albumId)}
        }
    }
}