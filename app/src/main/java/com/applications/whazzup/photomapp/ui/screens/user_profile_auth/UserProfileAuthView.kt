package com.applications.whazzup.photomapp.ui.screens.user_profile_auth

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
import android.widget.TextView
import com.applications.whazzup.photomapp.data.network.res.user.UserRes
import kotlinx.android.synthetic.main.screen_user_profile.view.*


class UserProfileAuthView(context: Context, attrs: AttributeSet) : AbstractView<UserProfileAuthScreen.UserProfilePresenter>(context, attrs) {

    @BindView(R.id.user_name_txt) lateinit  var mUserNameTxt : TextView
    @BindView(R.id.album_count_txt) lateinit  var mAlbumCount : TextView
    @BindView(R.id.card_count_txt) lateinit  var mCardCount:TextView


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
        val dialog = MaterialDialog.Builder(context)
                .title(R.string.new_album)
                .titleGravity(GravityEnum.CENTER)
                .customView(R.layout.dialog_customview_login, true)
//                .positiveColor(Color.WHITE)
//                .positiveText(R.string.okay)
//                .negativeColor(Color.WHITE)
//                .negativeText(android.R.string.cancel)
//                .onPositive(
//                        {
//                            dialog1, which -> dialog1.dismiss()
//                        })
                .build()


                var cancel: Button = dialog.getCustomView()!!.findViewById(R.id.cancel) as Button
                cancel.setOnClickListener({
                    v: View? -> Log.e("lul", "cancel");
                })
//                positiveAction = dialog.getActionButton(DialogAction.POSITIVE)
//
//                val login: EditText = dialog.getCustomView()!!.findViewById(R.id.login) as EditText
//                login.addTextChangedListener(
//                        object : TextWatcher {
//                            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
//
//                            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
//                                (positiveAction as View).setEnabled(s.toString().trim { it <= ' ' }.length > 0)
//                            }
//
//                            override fun afterTextChanged(s: Editable) {}
//                        })
//
//                // Toggling the show password CheckBox will mask or unmask the password input EditText
//                val password: EditText = dialog.getCustomView()!!.findViewById(R.id.password) as EditText
//                password.addTextChangedListener(object : TextWatcher {
//                    override fun afterTextChanged(s: Editable?) {
//                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//                    }
//
//                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//                    }
//
//                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//                    }
//
//                })

//                val widgetColor = ThemeSingleton.get().widgetColor
//                MDTintHelper.setTint(
//                        checkbox, if (widgetColor == 0) ContextCompat.getColor(view.context, R.color.accent) else widgetColor)
//
//                MDTintHelper.setTint(
//                        passwordInput,
//                        if (widgetColor == 0) ContextCompat.getColor(view.context, R.color.accent) else widgetColor)

            dialog.show()
//                (positiveAction as View).setEnabled(false) // disabled by default
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

}
