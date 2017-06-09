package com.applications.whazzup.photomapp.ui.screens.user_profile

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import butterknife.BindView
import butterknife.OnClick
import com.applications.whazzup.photomapp.R
import com.applications.whazzup.photomapp.di.DaggerService
import com.applications.whazzup.photomapp.mvp.views.AbstractView


class UserProfileView(context: Context, attrs: AttributeSet) : AbstractView<UserProfileScreen.UserProfilePresenter>(context, attrs) {

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
       DaggerService.getDaggerComponent<DaggerUserProfileScreen_Component>(context!!).inject(this)
    }
}