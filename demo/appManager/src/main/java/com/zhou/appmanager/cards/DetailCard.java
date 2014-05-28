package com.zhou.appmanager.cards;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.zhou.appmanager.R;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;

/**
 * Created by zhou on 14-5-27.
 */
public class DetailCard extends Card {

    public DetailCard(Context context) {
        this(context, R.layout.item_app_detail);
    }

    public DetailCard(Context context, int innerLayout) {
        super(context, innerLayout);
        init();
    }

    private void init() {
        CardHeader header = new CardHeader(getContext());
        header.setButtonExpandVisible(true);
        header.setTitle("Stocks today"); //should use R.string.
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
