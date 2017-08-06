package com.applications.whazzup.photomapp.ui.screens.upload_photo_screen.card_name

import android.content.Context
import android.util.AttributeSet
import android.widget.*
import butterknife.BindView
import butterknife.OnClick
import com.applications.whazzup.photomapp.R
import com.applications.whazzup.photomapp.di.DaggerService
import com.applications.whazzup.photomapp.mvp.views.AbstractView

class CardNameView(context : Context, attrs : AttributeSet) : AbstractView<CardNameScreen.CardNamePresenter>(context, attrs) {

    @BindView(R.id.card_tags_et)
    lateinit var mTagsEditText : AutoCompleteTextView
    @BindView(R.id.tags_list_view)
    lateinit var mTagsListView : ListView
    @BindView(R.id.card_name_et)
    lateinit var mCardNameEt : EditText

    var tagsList = mutableListOf<String>()
    lateinit var listAdapter : ArrayAdapter<String>

    override fun viewOnBackPressed(): Boolean {
        return false
    }

    override fun initDagger(context: Context?) {
       DaggerService.getDaggerComponent<DaggerCardNameScreen_cardNameComponent>(context!!).inject(this)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mPresenter.saveCardName(mCardNameEt.text.toString())
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        mCardNameEt.setText( mPresenter.uploadCardInfoPresneter.cardName)
        var adapter = ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, mPresenter.getTagsFromDb())
        mTagsEditText.setAdapter(adapter)
        mTagsEditText.threshold = 1

        listAdapter = ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, tagsList)
        mTagsListView.adapter = listAdapter


    }
    // region================Events==============

    @OnClick(R.id.perform_tags_iv)
    fun onClick(){
        //mPresenter.mRootPresenter.rootView?.showMessage("Нажали")
        tagsList.add(0, mTagsEditText.text.toString())
        listAdapter.notifyDataSetChanged()
        mPresenter.uploadCardInfoPresneter.cardTags.add( mTagsEditText.text.toString())
        mTagsEditText.setText("")
    }

    @OnClick(R.id.close_tags_iv)
    fun onCloseClick(){
        mTagsEditText.setText("")
    }

    // endregion
}