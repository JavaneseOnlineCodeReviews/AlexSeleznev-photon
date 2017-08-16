package com.applications.whazzup.photomapp.ui.screens.upload_photo_screen

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.widget.CompoundButton
import android.widget.TextView
import butterknife.BindView
import butterknife.OnClick
import com.applications.whazzup.photomapp.R
import com.applications.whazzup.photomapp.di.DaggerService
import com.applications.whazzup.photomapp.mvp.views.AbstractView
import kotlinx.android.synthetic.main.screen_upload_card_info.*

class UploadCardInfoView(context : Context, attrs: AttributeSet) : AbstractView<UploadCardInfoScreen.UploaCardInfoPresenter>(context, attrs), ViewPager.OnPageChangeListener{


    @BindView(R.id.step_tv)
   lateinit var mStepText : TextView


    override fun onPageScrollStateChanged(state: Int) {

    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    override fun onPageSelected(position: Int) {
        when(position){
            0 -> mStepText.text = "Шаг 1 из 3"
            1 -> mStepText.text = "Шаг 2 из 3"
            2 -> mStepText.text = "Шаг 3 из 3"
        }
    }

    @BindView(R.id.upload_card_info_pager)
    lateinit var mViewPager : ViewPager

    override fun viewOnBackPressed(): Boolean {
        return false
    }

    override fun initDagger(context: Context?) {
        DaggerService.getDaggerComponent<DaggerUploadCardInfoScreen_UploadCardInfoComponent>(context!!).inject(this)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        mViewPager.adapter = UploadCardInfoAdapter()
        mViewPager.addOnPageChangeListener(this)
    }

    // region================Events==============

    @OnClick(R.id.forward_btn)
    fun onForwardClick(){
        if(mViewPager.currentItem<3){
        mViewPager.currentItem = mViewPager.currentItem+1}
    }

    @OnClick(R.id.backward)
    fun onBackwardClick(){
        if(mViewPager.currentItem!=0){
            mViewPager.currentItem = mViewPager.currentItem-1}
    }

    @OnClick(R.id.save_card_btn)
    fun onSaveCardClack(){

        mPresenter.saveCard()
        /*if(mPresenter.cardDish!="" && mPresenter.cardDecor!="" && mPresenter.cardTemperature !="")
        {mPresenter.mRootPresenter.rootView?.showMessage("nuances: " + mPresenter.cardNuances.toString()+"," +
                " tags: " + mPresenter.cardTags.toString() +
                ", dish: " + mPresenter.cardDish +
                ", deror: " + mPresenter.cardDecor +
                ", temperature: " + mPresenter.cardTemperature + " cardName: " + mPresenter.cardName)}
        else{
            mPresenter.mRootPresenter.rootView?.showMessage("Выбирите параметры карточки")
        }*/
    }

    fun initView() {

    }

    // endregion
}