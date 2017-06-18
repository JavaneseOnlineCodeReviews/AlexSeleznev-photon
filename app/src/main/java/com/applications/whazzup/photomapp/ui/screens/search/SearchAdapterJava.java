package com.applications.whazzup.photomapp.ui.screens.search;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.applications.whazzup.photomapp.di.DaggerService;
import com.applications.whazzup.photomapp.flow.AbstractScreen;
import com.applications.whazzup.photomapp.ui.screens.search.filter.FilterScreen;

import mortar.MortarScope;

public class SearchAdapterJava extends PagerAdapter {

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        AbstractScreen screen = null;

        switch (position) {
            case 0:
                screen = new FilterScreen();
            case 1:
                screen = new FilterScreen();
                break;
        }

        MortarScope screenScope = createScreenScopeFromContext(container.getContext(), screen);
        Context screenContext = screenScope.createContext(container.getContext());

        View newView = LayoutInflater
                .from(screenContext)
                .inflate(screen.getLayoutResId(), container, false);
        container.addView(newView);
        return newView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(((View) object));
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position) {
            case 0:
                title = "Tag";
                break;
            case 1:
                title = "Filter";
                break;
        }
        return title;
    }

    private MortarScope createScreenScopeFromContext(Context context, AbstractScreen screen) {
        MortarScope parentScope = MortarScope.getScope(context);
        MortarScope childScope = parentScope.findChild(screen.getScopeName());

        if (childScope == null) {
            Object screenComponent = screen.createScreenComponent(parentScope.getService(DaggerService.Companion.getSERVICE_NAME()));

            if (screenComponent == null) {
                throw new IllegalStateException(" don`t create screen component for " + screen.getScopeName());
            }

            childScope = parentScope.buildChild()
                    .withService(DaggerService.Companion.getSERVICE_NAME(), screenComponent)
                    .build(screen.getScopeName());
        }

        return childScope;
    }

}
