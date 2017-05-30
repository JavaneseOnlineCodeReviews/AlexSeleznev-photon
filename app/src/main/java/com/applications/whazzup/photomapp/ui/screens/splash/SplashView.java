package com.applications.whazzup.photomapp.ui.screens.splash;

import android.content.Context;
import android.util.AttributeSet;

import com.applications.whazzup.photomapp.di.DaggerService;
import com.applications.whazzup.photomapp.mvp.views.AbstractView;
import com.applications.whazzup.photomapp.mvp.views.ISplashView;


public class SplashView extends AbstractView<SplashScreen.SplashPresenter> implements ISplashView {


    public SplashView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean viewOnBackPressed() {
        return false;
    }

    @Override
    protected void initDagger(Context context) {
        DaggerService.<SplashScreen.SplashComponent>getDaggerComponent(context).inject(this);
    }

    @Override
    public void showLoad() {

    }

    @Override
    public void hideLoad() {

    }
}
