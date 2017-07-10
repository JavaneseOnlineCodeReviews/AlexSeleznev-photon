package com.applications.whazzup.photomapp.ui.screens.author

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


class AuthorAlbumAdapter : RecyclerView.Adapter<AuthorAlbumAdapter.ViewHolder>() {

    @Inject
    lateinit var mPicasso : Picasso

    var authorAlbumList = mutableListOf<UserAlbumRes>()

    lateinit var listener: ((Int) -> Unit)

    fun addListener(onItemClick: (Int) -> Unit) {
        this.listener = onItemClick

    }

    fun addItem(album: UserAlbumRes) {
        authorAlbumList.add(album)
        notifyDataSetChanged()
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView?) {
        super.onAttachedToRecyclerView(recyclerView)
        DaggerService.getDaggerComponent<DaggerAuthorScreen_Component>(recyclerView!!.context).inject(this)
    }


    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        var album = authorAlbumList[position]
        if(!album.photocards.isEmpty()) {
            var albumPreview = album?.photocards[0].photo

            if (albumPreview != null) {
                mPicasso.load(albumPreview).into(holder?.albumPreview)
            }
        }
        holder?.title?.text = album.title
        holder?.cardCount?.text = album.photocards.size.toString()
        holder?.viewsCount?.text = album.views.toString()
        holder?.favoriteCount?.text = album.favorits.toString()

    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        return ViewHolder(inflater.inflate(R.layout.item_album, parent, false), listener)
    }

    override fun getItemCount(): Int {
        return authorAlbumList.size
    }


    inner class ViewHolder(itemView: View, onItemClick: ((Int) -> Unit)) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var listener = onItemClick
        var title = itemView.album_title
        var cardCount = itemView.album_card_count
        var favoriteCount = itemView.favorite_count_txt
        var viewsCount = itemView.views_count_txt
        var albumPreview = itemView.album_image


        override fun onClick(v: View?) {
            listener.invoke(adapterPosition)
        }

    }


}