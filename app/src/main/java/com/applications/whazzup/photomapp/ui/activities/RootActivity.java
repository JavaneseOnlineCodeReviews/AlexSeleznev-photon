package com.applications.whazzup.photomapp.ui.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.applications.whazzup.photomapp.R;
import com.applications.whazzup.photomapp.di.DaggerService;
import com.applications.whazzup.photomapp.di.components.AppComponent;
import com.applications.whazzup.photomapp.di.modules.PicassoCacheModule;
import com.applications.whazzup.photomapp.di.modules.RootModule;
import com.applications.whazzup.photomapp.di.scopes.RootScope;
import com.applications.whazzup.photomapp.flow.TreeKeyDispatcher;
import com.applications.whazzup.photomapp.mvp.presenters.RootPresenter;
import com.applications.whazzup.photomapp.mvp.views.IRootView;
import com.applications.whazzup.photomapp.mvp.views.IView;
import com.applications.whazzup.photomapp.ui.screens.photo_card_list.PhotoCardListScreen;
import com.applications.whazzup.photomapp.ui.screens.splash.SplashScreen;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import flow.Flow;
import mortar.MortarScope;
import mortar.bundler.BundleServiceRunner;

public class RootActivity extends AppCompatActivity implements IRootView {

    @BindView(R.id.root_frame)
    FrameLayout mRootFrame;
    @BindView(R.id.navigation)
    BottomNavigationView mNavigation;

    @Inject
    RootPresenter mRootPresenter;

    ProgressDialog mProgressDialog;


    @Override
    protected void attachBaseContext(Context newBase) {
        newBase = Flow.configure(newBase, this)
                .defaultKey(new SplashScreen())
                .dispatcher(new TreeKeyDispatcher(this))
                .install();
        super.attachBaseContext(newBase);
    }

    @Override
    public Object getSystemService(@NonNull String name){
        MortarScope rootActivityScope = MortarScope.findChild(getApplicationContext(), RootActivity.class.getName());
        return rootActivityScope.hasService(name)? rootActivityScope.getService(name) : super.getSystemService(name);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        BundleServiceRunner.getBundleServiceRunner(this).onSaveInstanceState(outState);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        return true;
                    case R.id.navigation_dashboard:
                        return true;
                    case R.id.navigation_notifications:
                        return true;
                }
                return false;
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root);
        ButterKnife.bind(this);
        mNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BundleServiceRunner.getBundleServiceRunner(this).onCreate(savedInstanceState);
        ((RootComponent) DaggerService.getDaggerComponent(this)).inject(this);
        mRootPresenter.takeView(this);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRootPresenter.dropView(this);
    }

    // region================IRootView==============
    @Override
    public boolean viewOnBackPressed() {
        return false;
    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void showError(Throwable e) {

    }

    @Override
    public void showLoad() {
        if (mProgressDialog == null) {
            mProgressDialog=new ProgressDialog(this);
            mProgressDialog.setCancelable(false);
            mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            mProgressDialog.show();
            mProgressDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            mProgressDialog.setContentView(R.layout.progress_dialog);
        } else {
            mProgressDialog.show();
            mProgressDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            mProgressDialog.setContentView(R.layout.progress_dialog);
        }
    }

    @Override
    public void hideLoad() {
        if (mProgressDialog!=null) {
            if (mProgressDialog.isShowing()) {
                mProgressDialog.hide();
            }
        }
    }

    @Override
    public void hideBottomNavigation(boolean isVisible) {
        if(isVisible) mNavigation.setVisibility(View.VISIBLE);
        else mNavigation.setVisibility(View.GONE);

    }

    @Nullable
    @Override
    public IView getCurrentScreen() {
        return (IView) mRootFrame.getChildAt(0);
    }
    // endregion

    // region================DI==============

    @dagger.Component(dependencies = AppComponent.class, modules = {RootModule.class, PicassoCacheModule.class })
    @RootScope
    public interface RootComponent{
        void inject(RootActivity rootActivity);
        void inject(RootPresenter rootPresenter);
        RootPresenter getRootPresenter();
        Picasso getPicasso();
    }

    // endregion

}
