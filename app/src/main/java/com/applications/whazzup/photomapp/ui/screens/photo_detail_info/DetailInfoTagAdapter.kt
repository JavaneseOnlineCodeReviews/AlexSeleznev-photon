package com.applications.whazzup.photomapp.ui.screens.photo_detail_info

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.applications.whazzup.photomapp.R
import kotlinx.android.synthetic.main.item_tag.view.*

class DetailInfoTagAdapter(tagList: List<String>) : RecyclerView.Adapter<DetailInfoTagAdapter.ViewHolder>() {

    var list: List<String> = tagList
    var context: Context ?= null

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        context = parent?.context
        return ViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_tag, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.tag?.text = context?.getString(R.string.hashtag, list[position])
    }

    override fun getItemCount(): Int =  list.size


    class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        var tag: TextView = item.tag_txt
    }
}