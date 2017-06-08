package com.applications.whazzup.photomapp.ui.screens.photo_card_list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.applications.whazzup.photomapp.R
import com.applications.whazzup.photomapp.data.storage.dto.PhotoCardDto
import com.applications.whazzup.photomapp.di.DaggerService
import com.applications.whazzup.photomapp.util.RecyclerClickListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_photo_card.view.*
import javax.inject.Inject


class PhotoCardListAdapter(cardList : List<PhotoCardDto>) : RecyclerView.Adapter<PhotoCardListAdapter.ViewHolder>() {

    @Inject
    lateinit var mPicasso : Picasso

    var list : List<PhotoCardDto> = cardList
    var listener:RecyclerClickListener ?= null

    public fun addListener(listener: RecyclerClickListener) {
        this.listener = listener
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView?) {
        super.onAttachedToRecyclerView(recyclerView)
        DaggerService.getDaggerComponent<DaggerPhotoCardListScreen_Component>(recyclerView!!.context).inject(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        return ViewHolder(inflater.inflate(R.layout.item_photo_card, parent, false), listener)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val item = list[position]
        mPicasso.load(item.photo).fit().centerCrop().into(holder?.picture)
        holder?.favoriteCount?.text = item.favorites.toString()
        holder?.viewCount?.text = item.views.toString()
    }

    override fun getItemCount(): Int {
        return list.size
    }


    class ViewHolder(item : View, listener: RecyclerClickListener?) : RecyclerView.ViewHolder(item), View.OnClickListener {

        var picture: ImageView ?= null
        var favoriteCount: TextView ?= null
        var viewCount: TextView ?= null
        var listener: RecyclerClickListener ?= null

        init {
            this.listener = listener
            picture = item.card_image
            favoriteCount = item.favorite_count_txt
            viewCount = item.views_count_txt

            picture?.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            listener?.onItemClick(adapterPosition)
        }

    }
}