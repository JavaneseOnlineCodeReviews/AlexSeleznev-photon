package com.applications.whazzup.photomapp.ui.screens.filtered_photo_card_list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.applications.whazzup.photomapp.R
import com.applications.whazzup.photomapp.data.storage.realm.PhotocardRealm
import com.applications.whazzup.photomapp.di.DaggerService
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_photo_card.view.*
import javax.inject.Inject


class FilteredPhotoCardListAdapter : RecyclerView.Adapter<FilteredPhotoCardListAdapter.ItViewHolder>() {

    @Inject
    lateinit var mPicasso : Picasso

    var cardList = mutableListOf<PhotocardRealm>()

    fun addItem(card: PhotocardRealm){
        cardList.add(card)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ItViewHolder?, position: Int) {
        val item = cardList[position]
        mPicasso.load(item.photo).fit().centerCrop().into(holder?.picture)
        holder?.favoriteCount?.text = item.favorits.toString()
        holder?.viewCount?.text = item.views.toString()
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView?) {
        super.onAttachedToRecyclerView(recyclerView)
        DaggerService.getDaggerComponent<DaggerFilteredPhotoCardListScreen_FilteredPhotoCardListComponent>(recyclerView!!.context).inject(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ItViewHolder {
        return ItViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_photo_card, parent, false))
    }

    override fun getItemCount(): Int {
        return cardList.size
    }

    inner class ItViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        var picture: ImageView?= null
        var favoriteCount: TextView?= null
        var viewCount: TextView?= null


        init {
            picture = itemView?.card_image
            favoriteCount = itemView?.favorite_count_txt
            viewCount = itemView?.views_count_txt

        }
    }
}