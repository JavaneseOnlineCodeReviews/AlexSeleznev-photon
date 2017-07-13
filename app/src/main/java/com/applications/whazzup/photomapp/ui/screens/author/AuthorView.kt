package com.applications.whazzup.photomapp.ui.screens.author

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import com.applications.whazzup.photomapp.R
import com.applications.whazzup.photomapp.data.network.res.user.UserRes
import com.applications.whazzup.photomapp.di.DaggerService
import com.applications.whazzup.photomapp.mvp.views.AbstractView
import com.applications.whazzup.photomapp.ui.screens.author.author_album_info.AuthorAlbumInfoScreen
import com.squareup.picasso.Picasso
import flow.Flow

class AuthorView(context : Context, attrs : AttributeSet) : AbstractView<AuthorScreen.AuthorPresenter>(context, attrs) {

    @BindView(R.id.author_info_album_recycler) lateinit var recycler : RecyclerView
    @BindView(R.id.author_avatar_img) lateinit var authorAvatar : ImageView
    @BindView(R.id.author_name_txt) lateinit var authorName : TextView
    @BindView(R.id.author_album_count_txt) lateinit var authorAlbumCount : TextView
    @BindView(R.id.author_card_count_txt) lateinit var authorCardCount : TextView

    lateinit var authorAlbumAdapter : AuthorAlbumAdapter





    override fun initDagger(context: Context?) {
        DaggerService.getDaggerComponent<DaggerAuthorScreen_Component>(context!!).inject(this)
    }

    override fun viewOnBackPressed(): Boolean {
        return false
    }

    fun initView(user: UserRes){

        Picasso.with(context).load(user.avatar).into(authorAvatar)
        authorName.text = user.name+ " / " + user.login
        authorAlbumCount.text = user.albumCount.toString()
        authorCardCount.text = user.photocardCount.toString()
        authorAlbumAdapter = AuthorAlbumAdapter()
        for(album in user.albums){
            if(album.active) {
                authorAlbumAdapter.addItem(album)
            }
        }
        with(recycler){
            layoutManager = GridLayoutManager(context, 2)
            adapter = authorAlbumAdapter
            (adapter as AuthorAlbumAdapter).addListener{ Flow.get(context).set(AuthorAlbumInfoScreen((adapter as AuthorAlbumAdapter).getItemByPosition(it))) }
        }
    }
}