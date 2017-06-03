package com.applications.whazzup.photomapp.mvp.presenters;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.applications.whazzup.photomapp.mvp.models.AbstractModel;
import com.applications.whazzup.photomapp.mvp.views.AbstractView;
import com.applications.whazzup.photomapp.mvp.views.IRootView;

import javax.inject.Inject;

import mortar.MortarScope;
import mortar.ViewPresenter;


public abstract class AbstractPresenter<V extends AbstractView, M extends AbstractModel> extends ViewPresenter<V> {
    private final String TAG = this.getClass().getSimpleName();

    @Inject
    public M mModel;

    @Inject
    public RootPresenter mRootPresenter;

    @Override
    protected void onEnterScope(MortarScope scope) {
        super.onEnterScope(scope);
        initDagger(scope);
    }

    @Override
    protected void onLoad(Bundle savedInstanceState) {
        super.onLoad(savedInstanceState);
    }

    @Nullable
    protected IRootView getRootView() {
        return mRootPresenter.getRootView();
    }

    protected abstract void initDagger(MortarScope scope);
}
