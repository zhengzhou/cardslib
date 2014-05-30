package com.zhou.appmanager.cards;

import android.content.Context;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.zhou.appmanager.R;
import com.zhou.appmanager.helper.AsyncImageLoader;
import com.zhou.appmanager.helper.ViewFinder;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.base.BaseCard;

/**
 * Created by zhou on 14-5-27.
 */
public class DetailCard extends Card {

    private String appName;
    private String versionName;
    private String size;
    private ViewFinder viewFinder;
    private AsyncImageLoader loader;
    private String iconUrl;

    public DetailCard(Context context) {
        this(context, R.layout.item_app_detail);
    }

    public DetailCard(Context context, int innerLayout) {
        super(context, innerLayout);
        init();
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public void setLoader(AsyncImageLoader loader) {
        this.loader = loader;
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
        super.setupInnerViewElements(parent, view);
        viewFinder = new ViewFinder(view);
        viewFinder.setText(R.id.version_name, versionName);
        getCardHeader().setTitle(appName);
        ImageView image = viewFinder.imageView(R.id.app_icon);
        loader.loadDrawable(iconUrl, image, AsyncImageLoader.DrawableLoaderFactory.LoaderType.AppIcon);
    }

    private void init() {
        CardHeader header = new CardHeader(getContext());
        header.setButtonExpandVisible(true);

        header.setPopupMenu(R.menu.popu_installed_app, new CardHeader.OnClickCardHeaderPopupMenuListener() {
            @Override
            public void onMenuItemClick(BaseCard card, MenuItem item) {
                Toast.makeText(getContext(), "Click on " + item.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });

        addCardHeader(header);

        setOnClickListener(new OnCardClickListener() {
            @Override
            public void onClick(Card card, View view) {
                Toast.makeText(getContext(), "Card click", Toast.LENGTH_SHORT).show();
            }
        });
        setOnSwipeListener(new OnSwipeListener() {
            @Override
            public void onSwipe(Card card) {
                Toast.makeText(getContext(), "Card removed", Toast.LENGTH_LONG).show();
            }
        });

        setOnUndoSwipeListListener(new OnUndoSwipeListListener() {
            @Override
            public void onUndoSwipe(Card card) {
                Toast.makeText(getContext(), "Card undo", Toast.LENGTH_LONG).show();
            }
        });
    }
}
