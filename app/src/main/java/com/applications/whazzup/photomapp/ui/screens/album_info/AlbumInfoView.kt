package com.applications.whazzup.photomapp.ui.screens.album_info

import android.app.AlertDialog
import android.content.Context
import android.support.v7.view.menu.MenuBuilder
import android.support.v7.view.menu.MenuPopupHelper
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.PopupMenu
import android.text.Editable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import butterknife.BindView
import com.applications.whazzup.photomapp.R
import com.applications.whazzup.photomapp.data.network.req.AlbumChangeInfoReq
import com.applications.whazzup.photomapp.data.network.res.user.UserAlbumRes
import com.applications.whazzup.photomapp.di.DaggerService
import com.applications.whazzup.photomapp.mvp.views.AbstractView
import com.applications.whazzup.photomapp.ui.screens.upload_photo_screen.UploadCardInfoScreen
import flow.Flow
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.screen_album_info.view.*


class AlbumInfoView(context: Context, attrs: AttributeSet) : AbstractView<AlbumInfoScreen.AlbumInfoPresenter>(context, attrs) {

    @BindView(R.id.album_title)
    lateinit var mAlbumTitle: TextView

    @BindView(R.id.card_count)
    lateinit var mAlbumsCardCount: TextView

    @BindView(R.id.album_description)
    lateinit var mAlbumDescription: TextView

    lateinit var albumInfoAdapter : AlbumInfoAdapter

    lateinit  var builder : AlertDialog

    override fun viewOnBackPressed(): Boolean {
        return false
    }

    override fun initDagger(context: Context?) {
        DaggerService.getDaggerComponent<DaggerAlbumInfoScreen_AlbumInfoComponent>(context!!).inject(this)
    }

    fun initView(res: UserAlbumRes) {
        mAlbumTitle.text = res.title

        mAlbumDescription.text = res.description
        albumInfoAdapter = AlbumInfoAdapter()
        for(item in res.photocards){
            albumInfoAdapter.addItem(item)
        }
        with(album_info_recycler) {
            layoutManager = GridLayoutManager(context, 3)
            adapter = albumInfoAdapter
            (adapter as AlbumInfoAdapter).addEditListener {
                Flow.get(this).set(UploadCardInfoScreen(1))
            }

            (adapter as AlbumInfoAdapter).addDeleteListener {
                mPresenter.deletePhotoCard((adapter as AlbumInfoAdapter).getItem(it).id, it).subscribeBy(onComplete = {
                    (adapter as AlbumInfoAdapter).deleteItem(it)
                    mPresenter.mRootPresenter.rootView?.showMessage("Карточка успешно удалена")
                })
            }

        }
        mAlbumsCardCount.text = albumInfoAdapter.adapterCardList.size.toString()
    }

    fun showPopupMenu(it: View) {
        var menu = PopupMenu(context, it)
        menu.inflate(R.menu.album_info_popup_menu)
        menu.setOnMenuItemClickListener({
            when(it.itemId) {
                R.id.change_album ->{
                   changeAlbum()
                }
                R.id.delete_album -> {
                    mPresenter.deleteAlbum()
                }
            }
            false
        })
        val menuHelper = MenuPopupHelper(context, menu.menu as MenuBuilder, it)
        menuHelper.setForceShowIcon(true)
        menuHelper.show()
    }

    fun changeAlbum() {
        builder = AlertDialog.Builder(context).create()
        val v: View= LayoutInflater.from(context).inflate(R.layout.dialog_customview_login, null)
        val mAlbumName = v.findViewById(R.id.album_name_et) as EditText
        mAlbumName.setText(mAlbumTitle.text)
        val mAlbumDesc = v.findViewById(R.id.album_desc_et) as EditText
        mAlbumDesc.setText(mAlbumDescription.text)
        val mAddAlbumBtn = v.findViewById(R.id.add_album_btn) as Button
        mAddAlbumBtn.setText("Изменить")

        builder.setTitle("Новый альбом!")
        mAddAlbumBtn.setOnClickListener {
            mPresenter.changeAlbumInfo(AlbumChangeInfoReq(mAlbumName.text.toString(), mAlbumDesc.text.toString()))
        }
        builder?.setView(v)
        builder?.show()
    }

    fun hideDialog() {
        builder.dismiss()
    }
}