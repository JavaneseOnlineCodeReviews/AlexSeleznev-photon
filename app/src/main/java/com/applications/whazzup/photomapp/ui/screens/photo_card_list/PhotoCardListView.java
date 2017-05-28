package com.applications.whazzup.photomapp.ui.screens.photo_card_list;

import android.content.Context;
import android.util.AttributeSet;

import com.applications.whazzup.photomapp.di.DaggerService;
import com.applications.whazzup.photomapp.mvp.views.AbstractView;



public class PhotoCardListView extends AbstractView<PhotoCardListScreen.PhotoCardListPresenter> {

    public PhotoCardListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean viewOnBackPressed() {
        return false;
    }

    @Override
    protected void initDagger(Context context) {
        DaggerService.<DaggerPhotoCardListScreen_Component>getDaggerComponent(context).inject(this);
    }
}
