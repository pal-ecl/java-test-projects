package com.lescure.dragoncardgame;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lescure.dragoncardgame.adapters.CardAdapter;
import com.lescure.dragoncardgame.model.CardBean;
import com.lescure.dragoncardgame.model.DeckBean;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CardAdapter.CardListener {

    private static final int MENU_NEW_GAME = 1;
    private ImageView imageView;
    private ScrollView svMView;
    private RecyclerView rvMain;
    private ArrayList<CardBean> cards;
    private CardAdapter cardAdapter;
    private DeckBean playersDeck;
    private Button btPlayersDeck, btFight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.ivDcgLogo);
        svMView = findViewById(R.id.svMView);
        rvMain = findViewById(R.id.rvMain);

        cards = new ArrayList<>();

        //création des cartes temporaire pour test
        cards.add(new CardBean("Blue Dragon", Color.BLUE, 5, 15));
        cards.add(new CardBean("White Dragon", Color.WHITE, 6, 14));
        cards.add(new CardBean("Red Dragon", Color.RED, 7, 13));
        cards.add(new CardBean("Green Dragon", Color.GREEN, 8, 12));
        cards.add(new CardBean("Black Dragon", Color.BLACK, 9, 11));
        cards.add(new CardBean("Gold Dragon", Color.YELLOW, 10, 10));

        cardAdapter = new CardAdapter(cards, this);
        rvMain.setAdapter(cardAdapter);
        rvMain.setLayoutManager(new GridLayoutManager(this, 3));
        btPlayersDeck = findViewById(R.id.btPlayersDeck);
        btFight = findViewById(R.id.btFight);

        if (getIntent().getSerializableExtra(PlayersDeckActivity.PLAYERS_CARDS) != null) {
            playersDeck = (DeckBean) getIntent().getSerializableExtra(PlayersDeckActivity.PLAYERS_CARDS);
            Log.w("TAG_CARDS", "get the cards from the player's deck");
            imageView.setVisibility(View.GONE);
            svMView.setVisibility(View.VISIBLE);
            btPlayersDeck.setVisibility(View.VISIBLE);
            btPlayersDeck.setText("Your deck : " + playersDeck.getCards().size() + "/5 cards");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, MENU_NEW_GAME, 1, "New game")
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == MENU_NEW_GAME) {
            imageView.setVisibility(View.GONE);
            svMView.setVisibility(View.VISIBLE);
            btPlayersDeck.setVisibility(View.VISIBLE);
            btPlayersDeck.setText("Your deck");
            playersDeck = new DeckBean("Your deck", new ArrayList<CardBean>());
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCardClick(CardBean cardBean) {
        if (playersDeck.getCards().size() < 5) {
            Log.w("TAG_CARD", "Add card in player's deck : ");
            if (playersDeck.getCards().contains(cardBean)) {
                playersDeck.getCards().add(new CardBean(cardBean.getName(), cardBean.getTextColor(),
                        cardBean.getPower(), cardBean.getHp()));
            } else {
                playersDeck.getCards().add(cardBean);
            }
            btPlayersDeck.setText("Your deck : " + playersDeck.getCards().size() + "/5 cards");
            Log.w("TAG_CARD", "Add card in player's deck : " + cardBean.getName());

            if (playersDeck.getCards().size() == 5) {
                Log.w("TAG_CARD", "deck's complete");
                Toast.makeText(this, "Congratulations, your deck is complete !",
                        Toast.LENGTH_SHORT).show();
                btFight.setVisibility(View.VISIBLE);
            }
        }
    }

    public void onPlayersDeckClick(View view) {
        Log.w("TAG_BUTTON", "click to player's deck");
        Intent intent = new Intent(this, PlayersDeckActivity.class);
        intent.putExtra(PlayersDeckActivity.CARDS_IN_DECK, playersDeck);
        startActivity(intent);
    }

    public void onFightClick(View view) {
        Log.w("TAG_BUTTON", "To fight");
        Intent intent = new Intent(this, FightActivity.class);
        intent.putExtra(FightActivity.PLAYERS_FINAL_DECK, playersDeck);
        startActivity(intent);
    }
}
