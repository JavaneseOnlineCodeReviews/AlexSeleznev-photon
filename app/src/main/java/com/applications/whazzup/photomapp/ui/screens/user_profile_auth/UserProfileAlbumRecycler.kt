package com.applications.whazzup.photomapp.ui.screens.user_profile_auth

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.applications.whazzup.photomapp.R
import com.applications.whazzup.photomapp.data.network.res.user.UserAlbumRes
import com.applications.whazzup.photomapp.di.DaggerService
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_album.view.*
import javax.inject.Inject


class UserProfileAlbumRecycler(albums : List<UserAlbumRes>?) : RecyclerView.Adapter< UserProfileAlbumRecycler.ViewHolder>() {

    @Inject
    lateinit var mPicasso : Picasso


    var albums : MutableList<UserAlbumRes> = albums as MutableList<UserAlbumRes>


    override fun getItemCount(): Int {
        return albums.size
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView?) {
        super.onAttachedToRecyclerView(recyclerView)
        DaggerService.getDaggerComponent<DaggerUserProfileAuthScreen_Component>(recyclerView!!.context).inject(this)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
            var album = albums[position]
        if(!album.photocards.isEmpty()) {
            var albumPreview = album?.photocards[0].photo

            if (albumPreview != null) {
                mPicasso.load(albumPreview).into(holder?.albumPreview)
            }
        }
            holder?.albumTitle?.text = album.title
            holder?.albumCardCount?.text = album.photocards.size.toString()
            holder?.viewCount?.text = album.views.toString()
            holder?.favoriteCount?.text = album.favorits.toString()
        }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        var inflater = LayoutInflater.from(parent?.context)
        return ViewHolder(inflater.inflate(R.layout.item_album, parent, false))
    }

    fun addAlbum(album : UserAlbumRes){
        albums.add(album)
        notifyDataSetChanged()
    }


    class ViewHolder(item : View) : RecyclerView.ViewHolder(item) {
        var albumTitle = item.album_title!!
        var albumCardCount = item.album_card_count
        var viewCount = item.views_count_txt
        var favoriteCount = item.favorite_count_txt
        var albumPreview = item.album_image
    }
}