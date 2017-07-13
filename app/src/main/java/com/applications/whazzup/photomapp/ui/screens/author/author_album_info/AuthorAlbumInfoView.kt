package com.applications.whazzup.photomapp.ui.screens.author.author_album_info

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.widget.TextView
import butterknife.BindView
import com.applications.whazzup.photomapp.R
import com.applications.whazzup.photomapp.data.network.res.user.UserAlbumRes
import com.applications.whazzup.photomapp.di.DaggerService
import com.applications.whazzup.photomapp.mvp.views.AbstractView
import com.applications.whazzup.photomapp.ui.screens.photo_detail_info.PhotoDetailInfoScreen
import flow.Direction
import flow.Flow

class AuthorAlbumInfoView(context : Context, attrs : AttributeSet):AbstractView<AuthorAlbumInfoScreen.AuthorAlbumInfoPresenter>(context, attrs) {

    @BindView(R.id.author_album_info_recycler) lateinit var recycler : RecyclerView
    @BindView(R.id.author_album_title) lateinit var mAlbumtitle : TextView
    @BindView(R.id.author_card_count) lateinit var mAuthorCardCount : TextView
    @BindView(R.id.author_album_description) lateinit var  mAuthorAlbumDesc : TextView

    var authorAdapter = AuthorAlbumInfoAdapter()

    override fun viewOnBackPressed(): Boolean {
       return false
    }

    override fun initDagger(context: Context?) {
        DaggerService.getDaggerComponent<DaggerAuthorAlbumInfoScreen_Component>(context!!).inject(this)
    }

    fun initView(res: UserAlbumRes) {
        mAlbumtitle.text = res.title
        mAuthorCardCount.text = authorAdapter.cardList.size.toString()
        mAuthorAlbumDesc.text = res.description
        with(recycler){
            layoutManager = GridLayoutManager(context, 3)
            adapter = authorAdapter
            (adapter as AuthorAlbumInfoAdapter).addListener { Flow.get(context).set(PhotoDetailInfoScreen((adapter as AuthorAlbumInfoAdapter).getCardbyPosition(it))) }
        }
    }
}