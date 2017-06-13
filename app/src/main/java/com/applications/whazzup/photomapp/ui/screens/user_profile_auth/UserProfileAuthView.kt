package com.applications.whazzup.photomapp.ui.screens.user_profile_auth

import android.app.AlertDialog
import android.content.Context
import android.support.v7.view.menu.MenuBuilder
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.Button

import butterknife.BindView

import com.afollestad.materialdialogs.GravityEnum
import com.afollestad.materialdialogs.MaterialDialog
import com.applications.whazzup.photomapp.R
import com.applications.whazzup.photomapp.di.DaggerService
import com.applications.whazzup.photomapp.mvp.views.AbstractView

import android.support.v7.view.menu.MenuPopupHelper
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.PopupMenu
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.TextView
import com.applications.whazzup.photomapp.data.network.req.AddAlbumReq
import com.applications.whazzup.photomapp.data.network.res.user.UserRes
import kotlinx.android.synthetic.main.screen_user_profile.view.*


class UserProfileAuthView(context: Context, attrs: AttributeSet) : AbstractView<UserProfileAuthScreen.UserProfilePresenter>(context, attrs) {

    @BindView(R.id.user_name_txt) lateinit  var mUserNameTxt : TextView
    @BindView(R.id.album_count_txt) lateinit  var mAlbumCount : TextView
    @BindView(R.id.card_count_txt) lateinit  var mCardCount:TextView

    lateinit  var builder : AlertDialog


    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
    }



    override fun viewOnBackPressed(): Boolean {
       return false
    }

    override fun initDagger(context: Context?) {
       DaggerService.getDaggerComponent<DaggerUserProfileAuthScreen_Component>(context!!).inject(this)
    }

    fun addAlbum(view: View) {
        builder = AlertDialog.Builder(context).create()

        val v: View= LayoutInflater.from(context).inflate(R.layout.dialog_customview_login, null)
        val mAlbumName = v.findViewById(R.id.album_name_et) as EditText
        val mAlbumDesc = v.findViewById(R.id.album_desc_et) as EditText
        val mAddAlbumBtn = v.findViewById(R.id.add_album_btn) as Button

        builder.setTitle("Новый альбом!")
        mAddAlbumBtn.setOnClickListener {
            mPresenter.addAlbum(AddAlbumReq(mAlbumName.text.toString(), mAlbumDesc.text.toString()))
        }
        builder?.setView(v)
        builder?.show()
    }

    fun showPopupMenu(view : View){
        var menu = PopupMenu(view.context, view)
        menu.inflate(R.menu.profile_select)
        menu.setOnMenuItemClickListener({
            when(it.itemId) {
                R.id.change_user ->{

                }
                R.id.delete_user ->{
//                    mPresenter.mRootPresenter.rootView?.createLoginDialog()
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
        mAlbumCount.text = res?.albums?.size.toString()
        mCardCount.text = mPresenter.getCardCount(res)
        with(user_info_album_recycler){
            layoutManager = GridLayoutManager(context, 2)
            adapter = UserProfileAlbumRecycler(res?.albums)
        }
    }

    fun hideDialog() {
        builder.dismiss()
     }

}
