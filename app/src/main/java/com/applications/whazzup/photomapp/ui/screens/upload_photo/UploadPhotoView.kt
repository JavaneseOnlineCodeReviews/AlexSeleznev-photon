package com.applications.whazzup.photomapp.ui.screens.upload_photo

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

class UploadPhotoView(context: Context, attrs: AttributeSet) : AbstractView<UploadPhotoScreen.UploadPhotoPresenter>(context, attrs) {
    @BindView(R.id.upload_photo)
    lateinit var button : Button
    @BindView(R.id.upload_card_user_not_auth_wrapper)
    lateinit var wrapper : LinearLayout

    override fun viewOnBackPressed(): Boolean {
        return false
    }

    override fun initDagger(context: Context?) {
        DaggerService.getDaggerComponent<DaggerUploadPhotoScreen_Component>(context!!).inject(this)
    }


    @OnClick(R.id.upload_photo)
    fun uploadPhoto() {
        mPresenter.uploadPhoto()
    }

    @OnClick(R.id.upload_card_login_btn)
    fun clickOnLoginBtn(){
        mPresenter.mRootPresenter.rootView?.createLoginDialog()
    }

    @OnClick(R.id.upload_card_signIn_btn)
        fun clickOnSignInBtn(){
        mPresenter.mRootPresenter.rootView?.createSignInAlertDialog()
        }




    fun initView(){
        if(!mPresenter.isUserAuth()){
            wrapper.visibility = View.VISIBLE
        }
    }

    fun  changeScreen() {
        wrapper.visibility = View.GONE
    }
}