package com.applications.whazzup.photomapp.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.applications.whazzup.photomapp.R
import kotlinx.android.synthetic.main.item_recent_tag.view.*


class RecentlyTagsAdapter(tagList: MutableList<String>) : RecyclerView.Adapter<RecentlyTagsAdapter.ViewHolder>() {

    var mTagList: MutableList<String> = tagList

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.tag?.text = mTagList[position]
    }

    override fun getItemCount(): Int {
        return mTagList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_recent_tag, parent, false))
    }

    class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        var tag: TextView = item.tag_txt
    }
}