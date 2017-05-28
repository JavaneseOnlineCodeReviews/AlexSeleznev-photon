package com.applications.whazzup.photomapp;


import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.applications.whazzup.photomapp.di.DaggerService;
import com.applications.whazzup.photomapp.di.components.AppComponent;
import com.applications.whazzup.photomapp.di.components.DaggerAppComponent;
import com.applications.whazzup.photomapp.di.modules.AppModule;
import com.applications.whazzup.photomapp.di.modules.PicassoCacheModule;
import com.applications.whazzup.photomapp.di.modules.RootModule;
import com.applications.whazzup.photomapp.mortar.ScreenScoper;
import com.applications.whazzup.photomapp.ui.activities.DaggerRootActivity_RootComponent;
import com.applications.whazzup.photomapp.ui.activities.RootActivity;

import io.realm.Realm;
import mortar.MortarScope;
import mortar.bundler.BundleServiceRunner;

public class App extends Application {

    private static AppComponent sAppComponent;
    private static RootActivity.RootComponent sRootComponent;
    public static SharedPreferences sSharedPreferences;
    private MortarScope mRootScope;
    private MortarScope mRootActivityScope;


    @Override
    public Object getSystemService(String name) {
        return (mRootScope != null && mRootScope.hasService(name)) ? mRootScope.getService(name) : super.getSystemService(name);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);

        sSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        createDaggerAppComponent();
        createDaggerRootComponent();

        mRootScope = MortarScope.buildRootScope()
                .withService(DaggerService.SERVICE_NAME, sAppComponent)
                .build("Root");
        mRootActivityScope = mRootScope.buildChild()
                .withService(DaggerService.SERVICE_NAME, sRootComponent)
                .withService(BundleServiceRunner.SERVICE_NAME, new BundleServiceRunner())
                .build(RootActivity.class.getName());

        ScreenScoper.registerScope(mRootScope);
        ScreenScoper.registerScope(mRootActivityScope);
    }


    public static SharedPreferences getSharedPreferences() {
        return sSharedPreferences;
    }

    private void createDaggerRootComponent() {
        sRootComponent = DaggerRootActivity_RootComponent.builder()
                .appComponent(sAppComponent)
                .rootModule(new RootModule())
                .picassoCacheModule(new PicassoCacheModule())
                .build();

    }

    private void createDaggerAppComponent() {
        sAppComponent = DaggerAppComponent.builder().appModule(new AppModule(getApplicationContext())).build();
    }

    public static AppComponent getAppComponent() {
        return sAppComponent;
    }
    public static RootActivity.RootComponent getRootComponent() {
        return sRootComponent;
    }

}
