package com.applications.whazzup.photomapp.ui.screens.upload_photo_screen.card_album

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.applications.whazzup.photomapp.R
import com.applications.whazzup.photomapp.data.storage.realm.UserAlbumRealm
import com.applications.whazzup.photomapp.di.DaggerService
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_card_albums.view.*
import javax.inject.Inject


class CardAlbumAdapter(var context : Context) : RecyclerView.Adapter<CardAlbumAdapter.ViewHolder>(){

    @Inject
    lateinit var mPicasso : Picasso

    var i =-1  //переменная для сохранения позиции по который был клик

    var listener : ((Int)->Unit)?=null

    var albumList : MutableList<UserAlbumRealm> = mutableListOf()

    fun addAlbum(album : UserAlbumRealm){
        albumList.add(album)
        notifyDataSetChanged()
    }

    fun addListener(onItemClick : (Int)->Unit){
        this.listener = onItemClick
    }

    fun getItemByPosition(position : Int): UserAlbumRealm{
        return albumList[position]
    }
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView?) {
        super.onAttachedToRecyclerView(recyclerView)
        DaggerService.getDaggerComponent<DaggerCardAlbumsScreen_CardAlbumsComponent>(recyclerView?.context!!).inject(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        return ViewHolder(inflater.inflate(R.layout.item_card_albums, parent, false), listener)
    }

    override fun getItemCount(): Int {
        return albumList.size
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {

        var albums = albumList[position]
        var preview = albums.preview
        if(!(preview.isEmpty())){
        mPicasso.load(albums.preview).into(holder?.picture)
        }
        if(position == i){ //Сравниваем текущую позицию адаптера с позицией по которой был клик
            holder?.cardAlpha?.visibility = View.VISIBLE
            holder?.check?.visibility = View.VISIBLE
        }else{
            holder?.cardAlpha?.visibility = View.GONE
            holder?.check?.visibility = View.GONE
        }
        holder?.cardCount?.text = albums.photoCards.size.toString()
        holder?.title?.text = albums.title

    }

    inner class ViewHolder(itemView : View, onItemClick : ((Int)->Unit)?) : RecyclerView.ViewHolder(itemView), View.OnClickListener {


        override fun onClick(v: View?) {

            i = adapterPosition
            Log.e("CardAlpha", cardAlpha.background.toString())
            Log.e("CardAlpha", v?.context?.resources?.getDrawable(R.drawable.album_gradient).toString())
            //cardAlpha.background.equals(v?.context?.resources?.getDrawable(R.drawable.album_gradient))

            if(cardAlpha.visibility == View.GONE){
            cardAlpha.visibility = View.VISIBLE
                check.visibility = View.VISIBLE
                listener?.invoke(adapterPosition)
                notifyDataSetChanged()
            }else{
                cardAlpha.visibility = View.GONE
                check.visibility = View.GONE
            }
        }
        var listener = onItemClick
        var picture : ImageView = itemView.card_album_image
        var title : TextView = itemView.card_album_title
        var cardCount : TextView = itemView.card_album_card_count
        var cardAlpha : ImageView = itemView.card_album_alpha
        var check : ImageView = itemView.check
        init {
            picture.setOnClickListener(this)

        }
    }
}