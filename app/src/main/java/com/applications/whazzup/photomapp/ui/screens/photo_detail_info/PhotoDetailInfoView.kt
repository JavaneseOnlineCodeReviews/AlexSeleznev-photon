package com.applications.whazzup.photomapp.ui.screens.photo_detail_info

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Environment
import android.support.v7.view.menu.MenuBuilder
import android.support.v7.view.menu.MenuPopupHelper
import android.support.v7.widget.PopupMenu
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import com.applications.whazzup.photomapp.R
import com.applications.whazzup.photomapp.data.network.res.user.UserRes
import com.applications.whazzup.photomapp.data.storage.dto.PhotoCardDto
import com.applications.whazzup.photomapp.di.DaggerService
import com.applications.whazzup.photomapp.mvp.views.AbstractView
import com.applications.whazzup.photomapp.ui.screens.author.AuthorScreen
import com.applications.whazzup.photomapp.ui.screens.user_profile.UserProfileScreen
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import de.hdodenhof.circleimageview.CircleImageView
import flow.Flow
import kotlinx.android.synthetic.main.screen_photo_detail_info.view.*
import java.io.File
import java.io.FileOutputStream
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class PhotoDetailInfoView(context: Context, attrs: AttributeSet) : AbstractView<PhotoDetailInfoScreen.PhotoDetailInfoPresenter>(context, attrs) {

    @Inject
    lateinit var picasso : Picasso

    @BindView(R.id.photo_detail_img) lateinit var image: ImageView
    @BindView(R.id.photo_detail_name) lateinit var photoName: TextView
    @BindView(R.id.owner_name) lateinit var name: TextView
    @BindView(R.id.album_count) lateinit var albumCount: TextView
    @BindView(R.id.photocard_count) lateinit var photocardCount: TextView
    @BindView(R.id.owner_img) lateinit var ownerImg: CircleImageView

    private var photocard: PhotoCardDto?= null

    fun showImageInfo(photoCard: PhotoCardDto) {
        this.photocard = photoCard

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

    fun showOwnerInfo(user: UserRes) {
        picasso.load(user.avatar).fit().centerCrop().into(ownerImg)
        ownerImg.setOnClickListener { Flow.get(context).set(AuthorScreen(user.id)) }

        name.text = user.name
        albumCount.text = user.albumCount.toString()
        photocardCount.text = user.photocardCount.toString()

    }

    fun showPopupMenu(view : View){
        var menu = PopupMenu(view.context, view)
        menu.inflate(R.menu.detail_info_menu)
        menu.setOnMenuItemClickListener({
            when(it.itemId) {
                R.id.to_favorite ->{
                }
                R.id.share ->{

                }
                R.id.download ->{
                    mPresenter.downloadPermissionAccess()
                }
            }
            false
        })
        val menuHelper = MenuPopupHelper(context, menu.menu as MenuBuilder, view)
        menuHelper.setForceShowIcon(true)
        menuHelper.show()
    }

    fun downloadImage() {
        picasso.load(photocard?.photo).into(target)
    }

    private val target = object : Target {
        override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
            val dateTimeInstance = SimpleDateFormat.getTimeInstance(DateFormat.MEDIUM)
            val timeStamp = dateTimeInstance.format(Date())
            val imageFileName = "IMG_" + timeStamp

            val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).absolutePath, "$imageFileName.jpg")
            try {
                file.createNewFile()
                val ostream = FileOutputStream(file)
                bitmap?.compress(Bitmap.CompressFormat.JPEG, 80, ostream)
                ostream.flush()
                println(file.absolutePath)
                ostream.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        override fun onBitmapFailed(errorDrawable: Drawable?) { return }

        override fun onPrepareLoad(placeHolderDrawable: Drawable?) { return }
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

