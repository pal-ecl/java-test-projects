package com.lescure.dragoncardgame;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lescure.dragoncardgame.adapters.CardAdapter;
import com.lescure.dragoncardgame.model.CardBean;
import com.lescure.dragoncardgame.model.DeckBean;

import java.util.ArrayList;
import java.util.Random;

public class FightActivity extends AppCompatActivity implements CardAdapter.CardListener {

    public final static int MENU_TURN = 1;
    public final static String PLAYERS_FINAL_DECK = "playersFinalDeck";
    public final static int NBR_CARDS_START_HAND = 3;
    public final static String IN_HAND = "inHand";
    public final static String IN_ARENA = "inArena";
    public final static String STATUS_SLEEP = "sleep";
    public final static String STATUS_TIRED = "tired";
    public final static String STATUS_READY = "ready";
    public final static int AVATARS_HP = 20;


    private DeckBean playersDeck, dlsDeck;
    private ArrayList<CardBean> playersHand, dlsHand, playersPlayedCards, dlsPlayedCards;
    private RecyclerView rvPlayersHand, rvDlsHand, rvPlayersArena, rvDlsArena;
    private CardAdapter playersHandAdapter, dlsHandAdapter, playersArenaAdapter, dlsArenaAdapter;
    private TextView tvDlsHP;
    private int turn;
    private Random ran;
    private CardBean attacker;
    private boolean hasPlay;
    private int dlsHP;
    private int playersHP;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fight);

        dlsDeck = new DeckBean("Dragon Lord's deck", new ArrayList<CardBean>());
        dlsDeck.getCards().add(new CardBean("Poisonous Dragon", Color.CYAN, 7, 11));
        dlsDeck.getCards().add(new CardBean("Dead Dragon", Color.GRAY, 5, 13));
        dlsDeck.getCards().add(new CardBean("Bad Dragon", Color.BLACK, 8, 10));
        dlsDeck.getCards().add(new CardBean("Angry Dragon", Color.RED, 9, 9));
        dlsDeck.getCards().add(new CardBean("Shadow Dragon", Color.GRAY, 4, 14));


        playersDeck = (DeckBean) getIntent().getSerializableExtra(PLAYERS_FINAL_DECK);
        Log.w("TAG_DECKS", "get the player's final deck");

        rvPlayersHand = findViewById(R.id.rvPlayersHand);
        playersHand = new ArrayList<>();
        playersHandAdapter = new CardAdapter(playersHand, this);
        rvPlayersHand.setAdapter(playersHandAdapter);
        rvPlayersHand.setLayoutManager(new GridLayoutManager(this, 5));

        rvDlsHand = findViewById(R.id.rvDlsHand);
        dlsHand = new ArrayList<>();
        dlsHandAdapter = new CardAdapter(dlsHand, this);
        rvDlsHand.setAdapter(dlsHandAdapter);
        rvDlsHand.setLayoutManager(new GridLayoutManager(this, 5));
        tvDlsHP = findViewById(R.id.tvDlsHP);

        rvPlayersArena = findViewById(R.id.rvPlayersArena);
        playersPlayedCards = new ArrayList<>();
        playersArenaAdapter = new CardAdapter(playersPlayedCards, this);
        rvPlayersArena.setAdapter(playersArenaAdapter);
        rvPlayersArena.setLayoutManager(new GridLayoutManager(this, 4));

        rvDlsArena = findViewById(R.id.rvDlsArena);
        dlsPlayedCards = new ArrayList<>();
        dlsArenaAdapter = new CardAdapter(dlsPlayedCards, this);
        rvDlsArena.setAdapter(dlsArenaAdapter);
        rvDlsArena.setLayoutManager(new GridLayoutManager(this, 4));

        turn = 0;
        ran = new Random();
        dlsHP = AVATARS_HP;
        tvDlsHP.setText("Dragon Lord : "+dlsHP+"HP");
        playersHP = AVATARS_HP;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, MENU_TURN, 1, "Start")
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                onTurnClick(item);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCardClick(CardBean cardBean) {
        switch (cardBean.getLocation()) {
            case IN_HAND:
                if (attacker != null) {
                    Toast.makeText(this, "A Dragon is waiting to attack...",
                            Toast.LENGTH_SHORT).show();
                } else {
                    if (!hasPlay) {
                        playersHand.remove(cardBean);
                        playersHandAdapter.notifyDataSetChanged();
                        cardBean.setStatus(STATUS_SLEEP);
                        playersPlayedCards.add(cardBean);
                        playersArenaAdapter.notifyDataSetChanged();
                        cardBean.setLocation(IN_ARENA);
                        Log.w("TAG_PLAY", "play card : " + cardBean.getName());
                        hasPlay = true;
                    } else {
                        Toast.makeText(this, "You have already played this turn !",
                                Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case IN_ARENA:
                if (attacker != null) {
                    if (attacker.equals(cardBean)) {
                        Log.w("TAG_ATTACK", attacker.getName()+" is not attacking yet.");
                        attacker = null;
                    } else if (playersPlayedCards.contains(cardBean)) {
                        Toast.makeText(this,
                                "Dragons are too proud to attack their allies !",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        attack(attacker, cardBean);
                        attackResult(attacker, playersArenaAdapter, playersPlayedCards);
                        attackResult(cardBean, dlsArenaAdapter, dlsPlayedCards);
                        attacker = null;
                    }
                } else {
                    if (cardBean.getStatus().equals(STATUS_READY)) {
                        attacker = cardBean;
                        Log.w("TAG_ATTACK", "attacker prepared :  " + cardBean.getName());
                    } else {
                        Toast.makeText(this, "This dragon is still sleeping...",
                                Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            default:
                Toast.makeText(this, "default on card click case...?",
                        Toast.LENGTH_SHORT).show();
        }
    }

    public void onTurnClick(MenuItem item) {
        CardBean ranDlsPlayedCard;

        attacker = null;
        hasPlay = false;

        switch (turn) {
            case 0:
                Log.w("TAG_TURN", "Click on start button");
                item.setTitle("Next turn");
                for (int i = 0; i < NBR_CARDS_START_HAND; i++) {
                    drawRandomCard(playersDeck, playersHand);
                    drawRandomCard(dlsDeck, dlsHand);
                }
                break;
            default:
                Log.w("TAG_TURN", "Click on end turn button");

                wakeUp(playersArenaAdapter, playersPlayedCards);
                wakeUp(dlsArenaAdapter, dlsPlayedCards);

                if (playersDeck.getCards().size() == 0) {
                    Toast.makeText(this, "No more cards in deck",
                            Toast.LENGTH_SHORT).show();
                } else {
                    drawRandomCard(playersDeck, playersHand);
                }
                if (dlsDeck.getCards().size() != 0) {
                    drawRandomCard(dlsDeck, dlsHand);
                }
                if (dlsHand.size() > 0) {
                    ranDlsPlayedCard = dlsHand.get(ran.nextInt(dlsHand.size()));
                    dlsHand.remove(ranDlsPlayedCard);
                    dlsHandAdapter.notifyDataSetChanged();
                    dlsPlayedCards.add(ranDlsPlayedCard);
                    ranDlsPlayedCard.setStatus(STATUS_SLEEP);
                    dlsArenaAdapter.notifyDataSetChanged();
                    ranDlsPlayedCard.setLocation(IN_ARENA);
                }
                if (dlsPlayedCards.size() > 0) {
                    for (int i = 0; i < dlsPlayedCards.size(); i++){
                        if (playersPlayedCards.size() > 0) {
                            dlsAttack();
                        }
                    }
                }
        }
        playersHandAdapter.notifyDataSetChanged();
        dlsHandAdapter.notifyDataSetChanged();
        turn += 1;
    }

    private void drawRandomCard(DeckBean deck, ArrayList<CardBean> hand) {
        CardBean randomCard;
        randomCard = deck.getCards().get(ran.nextInt(deck.getCards().size()));
        hand.add(randomCard);
        randomCard.setLocation(IN_HAND);
        deck.getCards().remove(randomCard);
        Log.w("TAG_TURN", randomCard.getName() + " picked");
    }

    private void attack(CardBean attacker, CardBean defender) {
        defender.setHp(defender.getHp() - attacker.getPower());
        attacker.setHp(attacker.getHp() - defender.getPower());
        Log.w("TAG_ATTACK", attacker.getName()+" attacks "+defender.getName());
        attacker.setStatus(STATUS_SLEEP);
    }

    private void attackResult(CardBean card, CardAdapter adapter, ArrayList<CardBean> playedCards) {
        if (card.getHp() < 1) {
            playedCards.remove(card);
            Log.w("TAG_ATTACK", card.getName()+" died");
        }
        adapter.notifyDataSetChanged();
    }

    private void wakeUp(CardAdapter adapter, ArrayList<CardBean> playedCards) {
        for (CardBean card : playedCards) {
            if (card.getStatus().equals(STATUS_SLEEP)) {
                card.setStatus(STATUS_READY);
                Log.w("TAG_TURN", "waking up : " + card.getName());
            }
        }
        adapter.notifyDataSetChanged();
    }

    private void dlsAttack() {
        CardBean ranAttacker;
        CardBean ranDefender;
        ArrayList<CardBean> attackGroup;

        attackGroup = new ArrayList<>();

        for (int i = 0; i < dlsPlayedCards.size(); i++) {
            if(dlsPlayedCards.get(i).getStatus().equals(STATUS_READY)){
                attackGroup.add(dlsPlayedCards.get(i));
            }
        }
        while (attackGroup.size() > 0) {
            ranAttacker = attackGroup.get(ran.nextInt(attackGroup.size()));
                if (playersPlayedCards.size() > 0) {
                    ranDefender = playersPlayedCards.get(ran.nextInt(playersPlayedCards.size()));
                    attack(ranAttacker, ranDefender);
                    attackResult(ranDefender, playersArenaAdapter, playersPlayedCards);
                    attackResult(ranAttacker, dlsArenaAdapter, dlsPlayedCards);
                    playersArenaAdapter.notifyDataSetChanged();
                    dlsArenaAdapter.notifyDataSetChanged();
                }
            attackGroup.remove(ranAttacker);
        }
    }

    public void onDLsClick(View view) {
        if (attacker != null && dlsPlayedCards.size() == 0){
            Log.w("TAG_ATTACK", attacker.getName()+" attacks DL");
            dlsHP -= dlsHP - attacker.getPower();
            tvDlsHP.setText("Dragon Lord : "+dlsHP+"HP");
            attacker.setStatus(STATUS_SLEEP);
            attacker = null;
        } else {
            Toast.makeText(this, "YOU REALLY THINK YOU CAN REACH ME ?!", Toast.LENGTH_SHORT).show();
        }
    }
}
