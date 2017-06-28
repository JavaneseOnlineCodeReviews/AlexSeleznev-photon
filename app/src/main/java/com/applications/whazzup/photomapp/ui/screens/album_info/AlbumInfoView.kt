package com.applications.whazzup.photomapp.ui.screens.album_info

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.TextView
import android.widget.Toast
import butterknife.BindView
import com.applications.whazzup.photomapp.R
import com.applications.whazzup.photomapp.data.network.res.PhotocardRes
import com.applications.whazzup.photomapp.data.network.res.user.UserAlbumRes
import com.applications.whazzup.photomapp.di.DaggerService
import com.applications.whazzup.photomapp.mvp.views.AbstractView
import com.applications.whazzup.photomapp.ui.screens.photo_card_list.PhotoCardListAdapter
import kotlinx.android.synthetic.main.screen_album_info.view.*


class AlbumInfoView(context: Context, attrs: AttributeSet) : AbstractView<AlbumInfoScreen.AlbumInfoPresenter>(context, attrs) {

    @BindView(R.id.album_title)
    lateinit var mAlbumTitle: TextView

    @BindView(R.id.card_count)
    lateinit var mAlbumsCardCount: TextView

    @BindView(R.id.album_description)
    lateinit var mAlbumDesc: TextView

    lateinit var albumInfoAdapter : AlbumInfoAdapter

    override fun viewOnBackPressed(): Boolean {
        return false
    }

    override fun initDagger(context: Context?) {
        DaggerService.getDaggerComponent<DaggerAlbumInfoScreen_AlbumInfoComponent>(context!!).inject(this)
    }

    fun initView(res: UserAlbumRes) {
        mAlbumTitle.text = res.title
        mAlbumsCardCount.text = res.photocards.size.toString()
        mAlbumDesc.text = res.description
        albumInfoAdapter = AlbumInfoAdapter()
        for(item in res.photocards){
            albumInfoAdapter.addItem(item)
        }
        with(album_info_recycler) {
            layoutManager = GridLayoutManager(context, 3)
            adapter = albumInfoAdapter
            (adapter as AlbumInfoAdapter).addEditListener {
                mPresenter.deletePhotoCard((adapter as AlbumInfoAdapter).getItem(it).id, it) }
            (adapter as AlbumInfoAdapter).addDeleteListener { Toast.makeText(context, "Delete push", Toast.LENGTH_LONG).show() }
        }
    }
}