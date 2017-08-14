package com.applications.whazzup.photomapp.ui.screens.upload_photo

import android.content.Context
import android.util.AttributeSet
import android.widget.Button
import butterknife.BindView
import butterknife.OnClick
import com.applications.whazzup.photomapp.R
import com.applications.whazzup.photomapp.di.DaggerService
import com.applications.whazzup.photomapp.mvp.views.AbstractView

class UploadPhotoView(context: Context, attrs: AttributeSet) : AbstractView<UploadPhotoScreen.UploadPhotoPresenter>(context, attrs) {
    @BindView(R.id.upload_photo)
    lateinit var button : Button

    override fun viewOnBackPressed(): Boolean {
        return false
    }

    override fun initDagger(context: Context?) {
        DaggerService.getDaggerComponent<DaggerUploadPhotoScreen_Component>(context!!).inject(this)
    }


    @OnClick(R.id.upload_photo)
    fun uploadPhoto() {
        if(isEnabled){
        mPresenter.uploadPhoto()
        }else{
            mPresenter.mRootPresenter.rootView?.showMessage("Для добавления фото необходиа авторизация")
        }
    }

    fun initView(){
        if(!mPresenter.isUserAuth()){
            button.isEnabled = false
        }
    }
}