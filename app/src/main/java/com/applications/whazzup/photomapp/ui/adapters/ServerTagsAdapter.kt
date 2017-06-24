package com.applications.whazzup.photomapp.ui.adapters

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.applications.whazzup.photomapp.App
import com.applications.whazzup.photomapp.R
import kotlinx.android.synthetic.main.item_tag.view.*


class ServerTagsAdapter(tagList: List<String>) : RecyclerView.Adapter<ServerTagsAdapter.ViewHolder>() {

    var list: List<String> = tagList
    var context: Context? = null

    override fun getItemViewType(position: Int): Int {
        return position % 2
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        context = parent?.context
        return ViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_tag, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.tag?.text = context?.getString(R.string.hash_tag, list[position])
        var colorBackGround: Int? = null
        var drawableBackGround: Int? = null
//        var drawable: Drawable = ContextCompat.getDrawable(App.applicationContext(), R.drawable.shape_tag_dark)
        when (position % 2) {
            0 -> {
                colorBackGround = R.color.colorPrimary
                drawableBackGround = R.drawable.shape_tag_primary
            }
            1 -> {
                colorBackGround = R.color.colorPrimaryDark
                drawableBackGround = R.drawable.shape_tag_dark
            }
        }
        holder?.tag?.setTextColor(ContextCompat.getColor(App.applicationContext(), colorBackGround!!))
        holder?.tag?.setBackgroundResource(drawableBackGround!!)
    }

    override fun getItemCount(): Int = list.size


    class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        var tag: TextView = item.tag_txt
    }
}