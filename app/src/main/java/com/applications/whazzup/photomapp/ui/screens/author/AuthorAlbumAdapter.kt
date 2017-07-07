package com.applications.whazzup.photomapp.ui.screens.author

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.applications.whazzup.photomapp.R
import com.applications.whazzup.photomapp.data.storage.realm.UserAlbumRealm


class AuthorAlbumAdapter : RecyclerView.Adapter<AuthorAlbumAdapter.ViewHolder>() {

    var authorAlbumList = mutableListOf<UserAlbumRealm>()

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView?) {
        super.onAttachedToRecyclerView(recyclerView)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        return ViewHolder(inflater.inflate(R.layout.item_album,parent, false))
    }

    override fun getItemCount(): Int {
       return authorAlbumList.size
    }


    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

    }
}