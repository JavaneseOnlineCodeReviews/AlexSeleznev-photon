package com.applications.whazzup.photomapp.ui.screens.album_info

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.applications.whazzup.photomapp.R
import com.applications.whazzup.photomapp.data.network.res.PhotocardRes
import com.applications.whazzup.photomapp.di.DaggerService
import com.applications.whazzup.photomapp.ui.screens.album_info.AlbumInfoAdapter.ViewHolder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.album_info_card_item.view.*
import javax.inject.Inject


class AlbumInfoAdapter(var cardList : MutableList<PhotocardRes>) : RecyclerView.Adapter<ViewHolder>() {

    @Inject
    lateinit var mPicasso : Picasso

    override fun getItemCount(): Int {
       return cardList.size
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        var item = cardList[position]
        mPicasso.load(item.photo).into(holder?.cardImage)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView?) {
        super.onAttachedToRecyclerView(recyclerView)
        DaggerService.getDaggerComponent<DaggerAlbumInfoScreen_AlbumInfoComponent>(recyclerView!!.context).inject(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        var inflater = LayoutInflater.from(parent?.context)
        return ViewHolder(inflater.inflate(R.layout.album_info_card_item,parent, false))
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var cardImage = itemView.album_info_card_img
    }
}