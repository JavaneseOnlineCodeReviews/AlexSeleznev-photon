package com.applications.whazzup.photomapp.ui.screens.user_profile_auth

import android.app.AlertDialog
import android.content.Context
import android.net.Uri
import android.support.v7.view.menu.MenuBuilder
import android.util.AttributeSet
import android.view.View

import butterknife.BindView

import com.applications.whazzup.photomapp.R
import com.applications.whazzup.photomapp.di.DaggerService
import com.applications.whazzup.photomapp.mvp.views.AbstractView

import android.support.v7.view.menu.MenuPopupHelper
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.PopupMenu
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.*
import butterknife.OnClick
import com.applications.whazzup.photomapp.data.network.req.AddAlbumReq
import com.applications.whazzup.photomapp.data.network.res.user.UserRes
import com.applications.whazzup.photomapp.ui.screens.album_info.AlbumInfoScreen
import com.squareup.picasso.Picasso
import flow.Flow
import kotlinx.android.synthetic.main.screen_user_profile.view.*
import java.io.IOException


class UserProfileAuthView(context: Context, attrs: AttributeSet) : AbstractView<UserProfileAuthScreen.UserProfilePresenter>(context, attrs) {

    @BindView(R.id.user_name_txt) lateinit  var mUserNameTxt : TextView
    @BindView(R.id.album_count_txt) lateinit  var mAlbumCount : TextView
    @BindView(R.id.card_count_txt) lateinit  var mCardCount:TextView
    @BindView(R.id.user_info_album_recycler) lateinit var mUserAlbumRecycler : RecyclerView
    @BindView(R.id.album_card_wrapper) lateinit  var mAlbumCardWrapper: RelativeLayout
    @BindView(R.id.user_avatar_img) lateinit  var mUserAvatar: ImageView

    lateinit  var builder : AlertDialog
    lateinit  var userAdapter : UserProfileAlbumRecycler


    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
    }



    override fun viewOnBackPressed(): Boolean {
       return false
    }

    override fun initDagger(context: Context?) {
       DaggerService.getDaggerComponent<DaggerUserProfileAuthScreen_Component>(context!!).inject(this)
    }

    fun addAlbum() {
        builder = AlertDialog.Builder(context).create()
        val v: View= LayoutInflater.from(context).inflate(R.layout.dialog_customview_login, null)
        val mAlbumName = v.findViewById(R.id.album_name_et) as EditText
        val mAlbumDesc = v.findViewById(R.id.album_desc_et) as EditText
        val mAddAlbumBtn = v.findViewById(R.id.add_album_btn) as Button

        builder.setTitle("Новый альбом!")
        mAddAlbumBtn.setOnClickListener {
            if(mUserAlbumRecycler.visibility == View.VISIBLE) {
                mPresenter.addAlbum(AddAlbumReq(mAlbumName.text.toString(), mAlbumDesc.text.toString()))
            }else{
                mAlbumCardWrapper.visibility = View.VISIBLE
                mUserAlbumRecycler.visibility = View.VISIBLE
                userAdapter = UserProfileAlbumRecycler()
                with(user_info_album_recycler){
                    layoutManager = GridLayoutManager(context, 2)
                    adapter = userAdapter
            }
                mPresenter.addAlbum(AddAlbumReq(mAlbumName.text.toString(), mAlbumDesc.text.toString()))
        }

    }
        builder?.setView(v)
        builder?.show()
    }

    fun incrementAlbumCount(){
        var  i = Integer.parseInt(mAlbumCount.text.toString())
        i++
        mAlbumCount.text=i.toString()
    }

    fun showPopupMenu(view : View){
        var menu = PopupMenu(view.context, view)
        menu.inflate(R.menu.profile_select)
        menu.setOnMenuItemClickListener({
            when(it.itemId) {
                R.id.change_user ->{
                    mPresenter.mRootPresenter.rootView?.createChangeUserInfoDialog()
                }
                R.id.logout ->{
                    mPresenter.logOut()
                }
                R.id.add_album ->{
                    addAlbum()
                }
                R.id.change_user_avatar->{
                    showSourceDialog()
                }
            }
            false
        })
        val menuHelper = MenuPopupHelper(context, menu.menu as MenuBuilder, view)
        menuHelper.setForceShowIcon(true)
        menuHelper.show()
    }

    fun initView(res: UserRes?) {
        mUserNameTxt.setText(res?.name + "/" + res?.login)
        mAlbumCount.text = res?.albumCount.toString()
        mCardCount.text = res?.photocardCount.toString()
        Picasso.with(context).load(res?.avatar).into(mUserAvatar)

        if(res?.albums!!.isEmpty()){
            mUserAlbumRecycler.visibility = View.GONE
            mAlbumCardWrapper.visibility = View.GONE
        }else{
            mAlbumCardWrapper.visibility = View.VISIBLE
            mUserAlbumRecycler.visibility = View.VISIBLE
            userAdapter = UserProfileAlbumRecycler(res?.albums)
            with(user_info_album_recycler){
                layoutManager = GridLayoutManager(context, 2)
                adapter = userAdapter
                (adapter as UserProfileAlbumRecycler).addListener { Flow.get(context).set(AlbumInfoScreen()) }
        }
        }
    }

    fun hideDialog() {
        builder.dismiss()
     }

    @OnClick(R.id.user_avatar_img)
    fun clickToUserAvatar(){
       showSourceDialog()
    }

    private fun showSourceDialog() {
        val source = arrayOf("Загрузить из галереи", "Сделать снимок", "Отмена")
        val alertDialog = android.support.v7.app.AlertDialog.Builder(context)
        alertDialog.setTitle("Установить фото")
        alertDialog.setItems(source) { dialogInterface, i ->
            when (i) {
                0 -> mPresenter.chooseGallery()
                1 -> try {
                    mPresenter.chooseCamera()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

                2 -> dialogInterface.cancel()
            }
        }
        alertDialog.show()
    }

    fun updateAvatarPhoto(parse: Uri?) {
        Picasso.with(context).load(parse).into(mUserAvatar)
    }
}
