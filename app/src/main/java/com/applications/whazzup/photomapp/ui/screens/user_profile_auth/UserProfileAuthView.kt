package com.applications.whazzup.photomapp.ui.screens.user_profile_auth

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.PopupMenu
import butterknife.BindView
import butterknife.OnClick
import com.afollestad.materialdialogs.GravityEnum
import com.afollestad.materialdialogs.MaterialDialog
import com.applications.whazzup.photomapp.R
import com.applications.whazzup.photomapp.di.DaggerService
import com.applications.whazzup.photomapp.mvp.views.AbstractView


class UserProfileAuthView(context: Context, attrs: AttributeSet) : AbstractView<UserProfileAuthScreen.UserProfilePresenter>(context, attrs) {

    @BindView(R.id.user_not_auth_wrapper) lateinit var any : LinearLayout
    @BindView(R.id.signIn_btn) lateinit var mSignInBtn : Button
    @BindView(R.id.login_btn) lateinit var mLoginBtn : Button
    val LOGIN_STATE = 0
    val IDLE_STATE = 1


    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if(mPresenter.isUserAuth()){
            any.visibility = View.GONE
        }else{
            any.visibility = View.VISIBLE
        }
    }



    @OnClick(R.id.signIn_btn)
    fun onClick(){
        mPresenter.mRootPresenter.rootView?.createSignInAlertDialog()
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
                R.id.add_album ->{
                    addAlbum(view)
                }
                R.id.edit_info_user ->{
//                    mPresenter.mRootPresenter.rootView?.createLoginDialog()
                }
                R.id.change_avatar ->{
//                    mPresenter.mRootPresenter.logOut()
                }
                R.id.log_out -> {
                    mPresenter.mRootPresenter.logOut()
                }
            }
            false
        })
        menu.show()
    }
}
