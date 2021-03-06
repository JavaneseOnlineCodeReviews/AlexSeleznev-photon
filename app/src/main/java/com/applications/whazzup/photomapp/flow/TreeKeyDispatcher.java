package com.applications.whazzup.photomapp.flow;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.applications.whazzup.photomapp.R;
import com.applications.whazzup.photomapp.mortar.ScreenScoper;
import com.applications.whazzup.photomapp.ui.screens.author.AuthorScreen;
import com.applications.whazzup.photomapp.ui.screens.author.author_album_info.AuthorAlbumInfoScreen;
import com.applications.whazzup.photomapp.ui.screens.filtered_photo_card_list.FilteredPhotoCardListScreen;
import com.applications.whazzup.photomapp.ui.screens.photo_detail_info.PhotoDetailInfoScreen;
import com.applications.whazzup.photomapp.ui.screens.search.SearchScreen;

import java.util.Collections;
import java.util.Map;

import flow.Direction;
import flow.Dispatcher;
import flow.KeyChanger;
import flow.State;
import flow.Traversal;
import flow.TraversalCallback;
import flow.TreeKey;


public class TreeKeyDispatcher extends KeyChanger implements Dispatcher {
    private Activity mActivity;

    private Object inKey;

    @Nullable
    private Object outKey;

    private FrameLayout mRootFrame;

    public TreeKeyDispatcher(android.app.Activity mActivity) {
        this.mActivity = mActivity;
    }

    @Override
    public void dispatch(Traversal traversal, TraversalCallback callback) {
        Map<Object, Context> mContexts;
        State inState = traversal.getState(traversal.destination.top());
        inKey = inState.getKey();
        State outState = traversal.origin == null ? null : traversal.getState(traversal.origin.top());
        outKey = outState == null ? null : outState.getKey();

        mRootFrame = (FrameLayout) mActivity.findViewById(R.id.root_frame);

        if (inKey.equals(outKey)) {
            callback.onTraversalCompleted();
            return;
        }
        if((outKey instanceof FilteredPhotoCardListScreen)){
            ((FilteredPhotoCardListScreen) outKey).unregisterScope();
        }

       /* if((outKey instanceof AuthorAlbumInfoScreen) && (inKey instanceof PhotoDetailInfoScreen)){
            ScreenScoper.destroyScreenScope(PhotoDetailInfoScreen.class.getName());
        }*/

        // TODO: 27.11.2016 create mortar context for screen
        Context flowContext = traversal.createContext(inKey, mActivity);
        Context mortarContext = ScreenScoper.getScreenScope((AbstractScreen) inKey).createContext(flowContext);
        mContexts = Collections.singletonMap(inKey, mortarContext);
        changeKey(outState, inState, traversal.direction, mContexts, callback);
    }

    @Override
    public void changeKey(@Nullable State outgoingState, State incomingState, final Direction direction, Map<Object, Context> incomingContexts, final TraversalCallback callback) {
        Context context = incomingContexts.get(inKey);
        // Сохраняем состояние экрана
        if (outgoingState != null) {
            outgoingState.save(mRootFrame.getChildAt(0));
        }

        // Создаем новый экран
        Screen screen;
        screen = inKey.getClass().getAnnotation(Screen.class);
        if (screen == null) {
            throw new IllegalStateException("@Screen annotation is missing in screen " + ((AbstractScreen) inKey).getClass());
        } else {
            int layout = screen.value();
            LayoutInflater inflater = LayoutInflater.from(context);
            final View newView = inflater.inflate(layout, mRootFrame, false);
            final View oldView = mRootFrame.getChildAt(0);

            //restore state new view
            incomingState.restore(newView);
            // TODO: 27.11.2016 Unregister screen scope

            //delete old view
            if (outKey != null && !(inKey instanceof TreeKey)) {
                ((AbstractScreen) outKey).unregisterScope();
            }

            if (mRootFrame.getChildAt(0) != null) {
                mRootFrame.removeView(mRootFrame.getChildAt(0));
            }
            mRootFrame.addView(newView);
            callback.onTraversalCompleted();
        }
    }

}
