package com.applications.whazzup.photomapp.ui.screens.author.author_album_info

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.applications.whazzup.photomapp.R
import com.applications.whazzup.photomapp.data.network.res.PhotocardRes
import com.applications.whazzup.photomapp.data.storage.dto.PhotoCardDto
import com.applications.whazzup.photomapp.di.DaggerService
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.album_info_card_item.view.*
import javax.inject.Inject

class AuthorAlbumInfoAdapter : RecyclerView.Adapter<AuthorAlbumInfoAdapter.ViewHolder>() {

    @Inject
    lateinit var mPicasso : Picasso

    var cardList = mutableListOf<PhotocardRes>()

    lateinit var listener: ((Int) -> Unit)

    fun addListener(onItemClick: (Int) -> Unit) {
        this.listener = onItemClick

    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView?) {
        super.onAttachedToRecyclerView(recyclerView)
        DaggerService.getDaggerComponent<DaggerAuthorAlbumInfoScreen_Component>(recyclerView!!.context).inject(this)
    }

    fun getCardbyPosition(position : Int) : PhotoCardDto{
        var card = PhotoCardDto(cardList[position])
        return card
    }

    fun additem(card : PhotocardRes){
        cardList.add(card)
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int {
        return cardList.size
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        var card = cardList[position]
        mPicasso.load(card.photo).into(holder?.cardImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        var inflater = LayoutInflater.from(parent?.context)
        return ViewHolder(inflater.inflate(R.layout.album_info_card_item,parent, false), listener)
    }


    inner class ViewHolder(itemView : View, onItemClick: ((Int) -> Unit)) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        override fun onClick(v: View?) {
            listener.invoke(adapterPosition)
        }

        var listener = onItemClick
        var cardImage = itemView.album_info_card_img

        init {
            cardImage.setOnClickListener(this)
        }
    }
}