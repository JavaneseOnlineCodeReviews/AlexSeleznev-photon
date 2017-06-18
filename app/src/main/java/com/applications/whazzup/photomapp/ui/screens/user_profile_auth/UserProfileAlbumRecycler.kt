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


class UserProfileAlbumRecycler() : RecyclerView.Adapter< UserProfileAlbumRecycler.ViewHolder>() {

    @Inject
    lateinit var mPicasso : Picasso

    var userAlbums : MutableList<UserAlbumRes>
    var listener : ((Int)->Unit)?=null

    constructor(albums : List<UserAlbumRes>?) : this() {
    userAlbums = albums as MutableList<UserAlbumRes>
}

    fun addListener(onItemClick : (Int)->Unit){
        this.listener = onItemClick
    }

    init {
        userAlbums = mutableListOf()
    }



    override fun getItemCount(): Int {
        return userAlbums.size
    }

    fun getItem(position: Int): UserAlbumRes{
        return userAlbums[position]
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView?) {
        super.onAttachedToRecyclerView(recyclerView)
        DaggerService.getDaggerComponent<DaggerUserProfileAuthScreen_Component>(recyclerView!!.context).inject(this)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
            var album = userAlbums[position]
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
        return ViewHolder(inflater.inflate(R.layout.item_album, parent, false), listener)
    }

    fun addAlbum(album : UserAlbumRes){
        userAlbums.add(album)
        notifyDataSetChanged()
    }


    class ViewHolder(item : View, onItemClick : ((Int)->Unit)?) : RecyclerView.ViewHolder(item), View.OnClickListener {
        override fun onClick(v: View?) {
        listener?.invoke(adapterPosition)
        }

        var albumTitle = item.album_title!!
        var albumCardCount = item.album_card_count
        var viewCount = item.views_count_txt
        var favoriteCount = item.favorite_count_txt
        var albumPreview = item.album_image
        var listener = onItemClick
        init {
            albumPreview.setOnClickListener (this)
        }


    }
}