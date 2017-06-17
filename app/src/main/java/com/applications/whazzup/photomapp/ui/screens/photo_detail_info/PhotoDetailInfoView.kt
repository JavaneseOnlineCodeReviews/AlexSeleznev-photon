package com.applications.whazzup.photomapp.ui.screens.photo_detail_info

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import com.applications.whazzup.photomapp.R
import com.applications.whazzup.photomapp.data.storage.dto.PhotoCardDto
import com.applications.whazzup.photomapp.di.DaggerService
import com.applications.whazzup.photomapp.mvp.views.AbstractView
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.screen_photo_detail_info.view.*
import javax.inject.Inject


class PhotoDetailInfoView(context: Context, attrs: AttributeSet) : AbstractView<PhotoDetailInfoScreen.PhotoDetailInfoPresenter>(context, attrs) {

    @Inject
    lateinit var picasso : Picasso

    @BindView(R.id.photo_detail_img) lateinit var image: ImageView
    @BindView(R.id.photo_detail_name) lateinit var photoName: TextView

    fun showImageInfo(photoCard: PhotoCardDto) {
        picasso.load(photoCard.photo).fit().centerCrop().into(image)
        photoName.text = photoCard.title

        with(tag_recycler){
            isNestedScrollingEnabled = false
            val llm = FlexboxLayoutManager(context)
            llm.flexDirection = FlexDirection.ROW
            llm.justifyContent = JustifyContent.FLEX_START
            layoutManager = llm
            adapter = DetailInfoTagAdapter(photoCard.tags)
        }
    }

    //region ================= AbstractView =================

    override fun viewOnBackPressed(): Boolean {
        return false
    }

    override fun initDagger(context: Context) {
        DaggerService.getDaggerComponent<DaggerPhotoDetailInfoScreen_Component>(context).inject(this)
    }

    //endregion
}

