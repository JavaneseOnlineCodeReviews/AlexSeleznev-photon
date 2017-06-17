package com.applications.whazzup.photomapp.ui.screens.user_profile_idle

import android.content.Context
import android.util.AttributeSet
import butterknife.OnClick
import com.applications.whazzup.photomapp.R
import com.applications.whazzup.photomapp.di.DaggerService
import com.applications.whazzup.photomapp.mvp.views.AbstractView
import com.applications.whazzup.photomapp.ui.screens.user_profile_auth.UserProfileAuthScreen
import flow.Direction
import flow.Flow

class UserProfileIdleView(context: Context, attrs: AttributeSet): AbstractView<UserProfileIdleScreen.UserProfileIdlePresenter>(context, attrs) {
    val LOGIN_STATE = 0
    val IDLE_STATE = 1


    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
//        if(mPresenter.isUserAuth()){
//            any.visibility = View.GONE
//        }else{
//            any.visibility = View.VISIBLE
//        }
    }



//   @OnClick(R.id.signIn_btn)
//    fun onClick(){
////        mPresenter.mRootPresenter.rootView?.createSignInAlertDialog()
//    }

    @OnClick(R.id.login_btn)
    fun clickToLoginBtn(){
        mPresenter.mRootPresenter.rootView?.createLoginDialog()
    }

    @OnClick(R.id.signIn_btn)
    fun clickToSignBtn(){
        mPresenter.mRootPresenter.rootView?.createSignInAlertDialog()
    }

    fun changeScreen(){
        Flow.get(this).replaceTop(UserProfileAuthScreen(), Direction.REPLACE)
    }

    override fun viewOnBackPressed(): Boolean {
        return false
    }

    override fun initDagger(context: Context?) {
        DaggerService.getDaggerComponent<DaggerUserProfileIdleScreen_Component>(context!!).inject(this)
    }
}