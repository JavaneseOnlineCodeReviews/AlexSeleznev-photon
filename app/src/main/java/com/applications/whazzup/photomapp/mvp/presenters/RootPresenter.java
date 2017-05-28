package com.applications.whazzup.photomapp.mvp.presenters;


import android.support.annotation.Nullable;

import com.applications.whazzup.photomapp.App;
import com.applications.whazzup.photomapp.mvp.views.IRootView;
import com.applications.whazzup.photomapp.ui.activities.RootActivity;

import mortar.Presenter;
import mortar.bundler.BundleService;

public class RootPresenter extends Presenter<IRootView> {

    private static RootPresenter ourInstance=null;

    private RootPresenter() {
        App.getRootComponent().inject(this);
    }

    @Override
    protected BundleService extractBundleService(IRootView view) {
        return BundleService.getBundleService(((RootActivity) view));
    }

    public static RootPresenter getInstance(){
        if(ourInstance==null){
            ourInstance=new RootPresenter();
        }
        return ourInstance;
    }

    @Nullable
    public IRootView getRootView() {
        return getView();
    }
}
