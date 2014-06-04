package com.zhou.appmanager.cards;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.zhou.appmanager.R;
import com.zhou.appmanager.helper.AsyncImageLoader;
import com.zhou.appmanager.helper.ViewFinder;
import com.zhou.appmanager.model.AppInfo;

import java.lang.ref.WeakReference;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.base.BaseCard;

/**
 * Created by zhou on 14-5-27.
 */
public class DetailCard extends Card {

    private AppInfo appInfo;
    private String versionName;
    private String size;
    private ViewFinder viewFinder;
    private AsyncImageLoader loader;
    private String iconUrl;
    private WeakReference<Drawable> icon;
    private CardHeader.OnClickCardHeaderPopupMenuListener listener;

    public DetailCard(Context context) {
        this(context, R.layout.item_app_detail);
    }

    public DetailCard(Context context, int innerLayout) {
        super(context, innerLayout);
        CardHeader header = new CardHeader(getContext());
        header.setButtonExpandVisible(true);
        addCardHeader(header);
    }

    public void setAppName(String appName) {
        getCardHeader().setTitle(appName);
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

    public void setIcon(Drawable icon) {
        this.icon = new WeakReference<Drawable>(icon);
    }

    public CardHeader.OnClickCardHeaderPopupMenuListener getListener() {
        return listener;
    }

    public void setListener(CardHeader.OnClickCardHeaderPopupMenuListener listener) {
        this.listener = listener;
    }

    public AppInfo getAppInfo() {
        return appInfo;
    }

    public void setAppInfo(AppInfo appInfo) {
        this.appInfo = appInfo;
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
        super.setupInnerViewElements(parent, view);
        viewFinder = new ViewFinder(view);
        versionName = appInfo.getVersionName();

        viewFinder.setText(R.id.version_name, versionName);
        ImageView image = viewFinder.imageView(R.id.app_icon);
        image.setTag(iconUrl);
        if (icon != null && icon.get() != null) {
            image.setImageDrawable(icon.get());
        } else {
            loader.loadDrawable(iconUrl, image, AsyncImageLoader.DrawableLoaderFactory.LoaderType.AppIcon);
        }
    }

    public void init() {

        getCardHeader().setPopupMenu(R.menu.popu_installed_app, listener);
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
