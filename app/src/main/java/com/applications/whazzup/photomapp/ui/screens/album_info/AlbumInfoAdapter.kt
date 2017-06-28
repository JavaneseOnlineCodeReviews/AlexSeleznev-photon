package com.applications.whazzup.photomapp.ui.screens.album_info


import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.applications.whazzup.photomapp.R
import com.applications.whazzup.photomapp.data.network.res.PhotocardRes
import com.applications.whazzup.photomapp.di.DaggerService
import com.applications.whazzup.photomapp.ui.screens.album_info.AlbumInfoAdapter.ViewHolder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.album_info_card_item.view.*
import javax.inject.Inject


class AlbumInfoAdapter() : RecyclerView.Adapter<ViewHolder>() {

    @Inject
    lateinit var mPicasso : Picasso

    var editListener : ((Int)->Unit)? = null
    var deleteListener : ((Int)->Unit)? = null
    var selectedPosition : Int = -1
    var adapterCardList = emptyList<PhotocardRes>()

    constructor(cardList : MutableList<PhotocardRes>) : this(){
        adapterCardList = cardList
    }

    override fun getItemCount(): Int {
       return adapterCardList.size
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        var item = adapterCardList[position]
        mPicasso.load(item.photo).into(holder?.cardImage)
        if(selectedPosition != position){
            holder?.menu?.visibility = View.GONE
        }
    }

    fun addEditListener(onItemClick : ((Int)->Unit)){
        editListener = onItemClick
    }

    fun addDeleteListener(onItemClick : ((Int)->Unit)){
        deleteListener = onItemClick
    }

    fun getItem(position : Int) : PhotocardRes{
        return adapterCardList[position]
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView?) {
        super.onAttachedToRecyclerView(recyclerView)
        DaggerService.getDaggerComponent<DaggerAlbumInfoScreen_AlbumInfoComponent>(recyclerView!!.context).inject(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        var inflater = LayoutInflater.from(parent?.context)
        return ViewHolder(inflater.inflate(R.layout.album_info_card_item,parent, false))
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnLongClickListener, View.OnClickListener, View.OnTouchListener{
        var cardImage = itemView.album_info_card_img
        var menu = itemView.card_menu
        var edit = itemView.edit
        var delete = itemView.delete
        init {
            cardImage.setOnLongClickListener(this)
            edit.setOnClickListener(this)
            delete.setOnClickListener(this)
            cardImage.setOnClickListener(this)
        }

        override fun onLongClick(v: View?): Boolean {
            if(menu.visibility==View.VISIBLE){
                selectedPosition = adapterPosition
                menu.visibility=View.GONE}
            else menu.visibility=View.VISIBLE
            return true
        }

        override fun onTouch(v: View?, event: MotionEvent?): Boolean {
            if (event?.action == MotionEvent.ACTION_DOWN)   {
                menu.visibility = View.GONE
            }
            return false
        }

        override fun onClick(v: View?) {
            selectedPosition = adapterPosition
            notifyDataSetChanged()
            if(menu.visibility==View.VISIBLE){
                menu.visibility = View.GONE
            }
            when(v?.id){
                R.id.edit -> {editListener?.invoke(selectedPosition)}
                R.id.delete ->{deleteListener?.invoke(selectedPosition)}
            }
        }
    }

    fun deleteItem(position: Int) {
        (adapterCardList as MutableList).removeAt(position)
        notifyDataSetChanged()
    }

    fun addItem(photoCard : PhotocardRes){
        if(photoCard.active){
            (adapterCardList as MutableList).add(photoCard)
            notifyDataSetChanged()
        }
    }

}