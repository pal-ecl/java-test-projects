package com.lescure.dragoncardgame;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ScrollView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lescure.dragoncardgame.adapters.CardAdapter;
import com.lescure.dragoncardgame.model.CardBean;
import com.lescure.dragoncardgame.model.DeckBean;

public class PlayersDeckActivity extends AppCompatActivity implements CardAdapter.CardListener {

    private static final int MENU_OK = 1;
    public final static String CARDS_IN_DECK = "cardsInDeck";
    public final static String PLAYERS_CARDS = "playersCards";
    private DeckBean deck;
    private ScrollView svPlayersDeck;
    private RecyclerView rvPlayersDeck;
    private CardAdapter cardAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players_deck);

        svPlayersDeck = findViewById(R.id.svPlayersDeck);
        rvPlayersDeck = findViewById(R.id.rvPlayersDeck);

        deck = (DeckBean) getIntent().getSerializableExtra(CARDS_IN_DECK);
        cardAdapter = new CardAdapter(deck.getCards(), this);
        rvPlayersDeck.setAdapter(cardAdapter);
        rvPlayersDeck.setLayoutManager(new GridLayoutManager(this, 3));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, MENU_OK, 1, "ok").setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == MENU_OK) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(PLAYERS_CARDS, deck);
            Log.w("TAG_MENU", "return on main activity from player's deck");
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCardClick(CardBean cardBean) {
        deck.getCards().remove(cardBean);
        cardAdapter.notifyDataSetChanged();
        Log.w("TAG_PLAYER_DECK", "remove from deck : " + cardBean.getName());
    }
}
