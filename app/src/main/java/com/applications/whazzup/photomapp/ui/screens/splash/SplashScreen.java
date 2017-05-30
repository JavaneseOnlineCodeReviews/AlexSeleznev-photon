package com.applications.whazzup.photomapp.ui.screens.splash;

import android.os.Handler;

import com.applications.whazzup.photomapp.R;
import com.applications.whazzup.photomapp.di.DaggerService;
import com.applications.whazzup.photomapp.di.scopes.SplashScope;
import com.applications.whazzup.photomapp.flow.AbstractScreen;
import com.applications.whazzup.photomapp.flow.Screen;
import com.applications.whazzup.photomapp.mvp.models.SplashModel;
import com.applications.whazzup.photomapp.mvp.presenters.AbstractPresenter;
import com.applications.whazzup.photomapp.ui.activities.RootActivity;
import com.applications.whazzup.photomapp.ui.screens.photo_card_list.PhotoCardListScreen;

import dagger.Provides;
import flow.Flow;
import mortar.MortarScope;

@Screen(R.layout.screen_splash)
public class SplashScreen extends AbstractScreen<RootActivity.RootComponent> {
    @Override
    public Object createScreenComponent(RootActivity.RootComponent parentComponent) {
        return DaggerSplashScreen_SplashComponent.builder()
                .rootComponent(parentComponent)
                .splashModule(new SplashModule())
                .build();
    }

    // region================Presenter==============

    public class SplashPresenter extends AbstractPresenter<SplashView, SplashModel>{
        @Override
        protected void onEnterScope(MortarScope scope) {
            super.onEnterScope(scope);
            getRootView().hideBottomNavigation(false);
            getRootView().showLoad();
            Handler handler = new Handler();
            handler.postDelayed(() -> {
                getRootView().hideLoad();
                Flow.get(getView().getContext()).set(new PhotoCardListScreen());
            }, 3000);
        }

        @Override
        protected void initDagger(MortarScope scope) {
            ((SplashComponent) scope.getService(DaggerService.SERVICE_NAME)).inject(this);
        }
    }

    // endregion

    // region================DI==============

    @dagger.Module
    public class SplashModule{
        @Provides
        @SplashScope
        SplashPresenter providePresenter(){
            return new SplashPresenter();
        }

        @Provides
        @SplashScope
        SplashModel provideModel(){
            return new SplashModel();
        }
    }

    @dagger.Component(dependencies = RootActivity.RootComponent.class, modules = SplashModule.class)
    @SplashScope
    public interface SplashComponent{
        void inject (SplashPresenter presenter);
        void inject (SplashView view);
    }

    // endregion
}
