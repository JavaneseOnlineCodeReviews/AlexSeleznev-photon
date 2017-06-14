package com.applications.whazzup.photomapp.mvp.presenters;


import android.support.annotation.Nullable;
import android.util.Log;

import com.applications.whazzup.photomapp.mvp.models.AbstractModel;
import com.applications.whazzup.photomapp.mvp.views.AbstractView;
import com.applications.whazzup.photomapp.mvp.views.IRootView;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.ResourceSubscriber;

public abstract class SubscribePresenter<V extends AbstractView, M extends AbstractModel> extends AbstractPresenter<V,M> {
    private final String TAG = this.getClass().getSimpleName();

    @Nullable
    protected abstract IRootView getRootView();

    protected abstract class ViewSubscriber<T> extends DisposableObserver<T>{

        @Override
        public void onComplete() {

        }

        @Override
        public void onError(Throwable e) {
            if(getRootView() !=null){
                getRootView().showError(e);
            }
        }

        @Override
        public abstract void onNext(T t);
    }


    protected <T>Disposable subscribe (Observable<T> observable, ViewSubscriber<T> subscriber){
        return  observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(subscriber);
    }
}
