package com.applications.whazzup.photomapp.ui.screens.photo_card_list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.applications.whazzup.photomapp.R
import kotlinx.android.synthetic.main.photo_card_item.view.*


class PhotoCardListAdapter : RecyclerView.Adapter<PhotoCardListAdapter.ViewHolder>() {

    private var list : List<String>

    init {
        list = ArrayList()
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        return ViewHolder(inflater.inflate(R.layout.photo_card_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        //val item = list[position]
        //holder?.picture?.setImageBitmap(null)
        holder?.favoriteCount?.setText("1")
        holder?.viewCount?.setText("22")
    }

    override fun getItemCount(): Int {
        return 5
    }


    class ViewHolder(item : View) : RecyclerView.ViewHolder(item) {
        val picture = item.card_image
            val favoriteCount = item.favorite_count_txt
        val viewCount = item.views_count_txt

    }
}