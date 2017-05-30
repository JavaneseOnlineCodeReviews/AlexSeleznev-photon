package com.applications.whazzup.photomapp.mvp.views;

import android.support.annotation.Nullable;

public interface IRootView extends IView {
    void showMessage (String message);
    void showError (Throwable e);

    void showLoad();
    void hideLoad();

    void hideBottomNavigation(boolean isVisible);

    @Nullable
    IView getCurrentScreen();
}
