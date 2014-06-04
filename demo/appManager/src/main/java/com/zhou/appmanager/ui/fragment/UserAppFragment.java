package com.zhou.appmanager.ui.fragment;

import android.app.Fragment;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import com.nhaarman.listviewanimations.swinginadapters.AnimationAdapter;
import com.nhaarman.listviewanimations.swinginadapters.prepared.ScaleInAnimationAdapter;
import com.zhou.appmanager.R;
import com.zhou.appmanager.cards.DetailCard;
import com.zhou.appmanager.controller.AppChecker;
import com.zhou.appmanager.helper.AsyncImageLoader;
import com.zhou.appmanager.model.AppInfo;
import com.zhou.appmanager.service.ServiceManger;

import java.util.ArrayList;
import java.util.List;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.internal.CardGridArrayAdapter;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.base.BaseCard;
import it.gmariotti.cardslib.library.internal.base.BaseCardArrayAdapter;
import it.gmariotti.cardslib.library.view.CardGridView;
import it.gmariotti.cardslib.library.view.CardListView;

/**
 * Created by zhou on 14-5-30.
 */
public class UserAppFragment extends BaseFragment implements CardHeader.OnClickCardHeaderPopupMenuListener {

    private AbsListView mListView;
    private BaseCardArrayAdapter mAdapter;
    private List<AppInfo> mAppData;
    private AsyncImageLoader loader;

    List<Card> cards;

    int screenOrientation = -1;
    private AppChecker checker;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        screenOrientation = getResources().getConfiguration().orientation;
        findView(view);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        screenOrientation = newConfig.orientation;
        mListView.setVisibility(View.GONE);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loader = new AsyncImageLoader();
        if (cards == null)
            initCard();
    }

    private void findView(View view) {
        if (screenOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            mListView = (AbsListView) view.findViewById(R.id.grid_view);
        } else if (screenOrientation == Configuration.ORIENTATION_PORTRAIT) {
            mListView = (AbsListView) view.findViewById(R.id.list_view);
        }
    }

    private void initCard() {
        checker = (AppChecker) getService(ServiceManger.AppCheckerService);
        mAppData = checker.getAppInfos();
        cards = new ArrayList<Card>(mAppData.size());
        for (AppInfo appInfo : mAppData) {
            DetailCard card = new DetailCard(getActivity());
            card.setAppInfo(appInfo);
            card.setLoader(loader);
            card.setIconUrl(appInfo.getPackageName());
            card.setAppName(appInfo.getName());
            card.setVersionName(appInfo.getVersionName());
            card.setListener(this);
//            card.set
            card.setOnSwipeListener(new Card.OnSwipeListener() {
                @Override
                public void onSwipe(Card card) {
                    cards.remove(card);
                }
            });
            card.init();
            cards.add(card);
        }
        setUpArrayList();
    }

    private void setUpArrayList() {
        if (screenOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            mAdapter = new CardGridArrayAdapter(getActivity(), cards);
        } else if (screenOrientation == Configuration.ORIENTATION_PORTRAIT) {
            mAdapter = new CardArrayAdapter(getActivity(), cards);
        }
        setScaleAdapter();
    }

    /**
     * Scale animation
     */
    private void setScaleAdapter() {
        AnimationAdapter animCardArrayAdapter = new ScaleInAnimationAdapter(mAdapter);
        animCardArrayAdapter.setAbsListView(mListView);
        if (mListView != null) {
            if (CardListView.class.isInstance(mListView) && CardArrayAdapter.class.isInstance(mAdapter))
                ((CardListView) mListView).setExternalAdapter(animCardArrayAdapter, (CardArrayAdapter) mAdapter);
            else if (CardGridView.class.isInstance(mListView) && CardGridArrayAdapter.class.isInstance(mAdapter))
                ((CardGridView) mListView).setExternalAdapter(animCardArrayAdapter, (CardGridArrayAdapter) mAdapter);
        }
    }

    @Override
    public void onMenuItemClick(BaseCard card, MenuItem item) {
        DetailCard detailCard = (DetailCard) card;
        switch (item.getItemId()) {
            case R.id.menu_backup:
                AppInfo app = detailCard.getAppInfo();
                getActivity().setProgressBarIndeterminateVisibility(true);
                checker.backUpApp(app);
                getActivity().setProgressBarIndeterminateVisibility(false);
                break;
        }
    }
}
