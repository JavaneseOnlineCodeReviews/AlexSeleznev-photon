package com.applications.whazzup.photomapp.ui.screens.photo_card_list;


import com.applications.whazzup.photomapp.R;
import com.applications.whazzup.photomapp.di.DaggerService;
import com.applications.whazzup.photomapp.di.scopes.PhotoCardListScope;
import com.applications.whazzup.photomapp.flow.AbstractScreen;
import com.applications.whazzup.photomapp.flow.Screen;
import com.applications.whazzup.photomapp.mvp.models.PhotoCardListModel;
import com.applications.whazzup.photomapp.mvp.presenters.AbstractPresenter;
import com.applications.whazzup.photomapp.ui.activities.RootActivity;

import dagger.Provides;
import mortar.MortarScope;

@Screen(R.layout.screen_photo_card_list)
public class PhotoCardListScreen extends AbstractScreen<RootActivity.RootComponent> {
    @Override
    public Object createScreenComponent(RootActivity.RootComponent parentComponent) {
        return DaggerPhotoCardListScreen_Component.builder().rootComponent(parentComponent).module(new Module()).build();
    }

    // region================Presenter==============
    public class PhotoCardListPresenter extends AbstractPresenter<PhotoCardListView, PhotoCardListModel>{

        @Override
        protected void initDagger(MortarScope scope) {
            ((DaggerPhotoCardListScreen_Component) scope.getService(DaggerService.SERVICE_NAME)).inject(this);
        }
    }


    // endregion


    // region================DI==============

    @dagger.Module
    public class Module{
        @Provides
        @PhotoCardListScope
        PhotoCardListPresenter providePresenter(){
            return new PhotoCardListPresenter();
        }

        @Provides
        @PhotoCardListScope
        PhotoCardListModel provideModel(){
            return new PhotoCardListModel();
        }
    }

    @dagger.Component(dependencies = RootActivity.RootComponent.class, modules = PhotoCardListScreen.Module.class)
    @PhotoCardListScope
    public interface Component{
        void inject(PhotoCardListPresenter presenter);
        void inject(PhotoCardListView view);
    }

    // endregion
}
