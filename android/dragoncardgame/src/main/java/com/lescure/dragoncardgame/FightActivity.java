package com.lescure.dragoncardgame;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lescure.dragoncardgame.adapters.CardAdapter;
import com.lescure.dragoncardgame.adapters.HandAdapter;
import com.lescure.dragoncardgame.model.AvailableCards.GreenDragonBean;
import com.lescure.dragoncardgame.model.CardBean;
import com.lescure.dragoncardgame.model.DeckBean;

import java.util.ArrayList;
import java.util.Random;

public class FightActivity extends AppCompatActivity implements CardAdapter.CardListener, HandAdapter.CardListener {

    public final static int MENU_TURN = 1;
    public final static int MENU_NEW_GAME = 2;
    public final static String PLAYERS_FINAL_DECK = "playersFinalDeck";
    public final static int NBR_CARDS_START_HAND = 3;
    public final static String IN_HAND = "inHand";
    public final static String IN_ARENA = "inArena";
    public final static int AVATARS_HP = 20;
    public final static int POISON_POWER = 15;

    public static final String EFFECT_POISON = "Poison";

    public final static int STATUS_AWAKENING_READY = 0;
    public final static int STATUS_AWAKENING_TIRED = 1;
    public final static int STATUS_AWAKENING_WAKE = 2;
    public final static int STATUS_AWAKENING_SLEEP = 3;
    public final static int STATUS_AWAKENING_DEEP_SLEEP = 4;

    public final static int STATUS_HEALTH_HEALTHY = 0;
    public final static int STATUS_HEALTH_POISONED = 1;
    public final static int STATUS_HEALTH_WEAKENED = 2;


    private DeckBean playersDeck, dlsDeck;
    private ArrayList<CardBean> playersHand, dlsHand, playersPlayedCards, dlsPlayedCards, playersCemetery, dlsCemetery;
    private RecyclerView rvPlayersHand, rvDlsHand, rvPlayersArena, rvDlsArena;
    private CardAdapter dlsHandAdapter, playersArenaAdapter, dlsArenaAdapter;
    private HandAdapter playersHandAdapter;
    private TextView tvDlsHP, tvPlayersHP;
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
        dlsDeck.getCards().add(new GreenDragonBean("Poisonous Dragon", Color.CYAN, 7, 11));
        dlsDeck.getCards().add(new GreenDragonBean("Dead Dragon", Color.GRAY, 5, 13));
        dlsDeck.getCards().add(new GreenDragonBean("Bad Dragon", Color.BLACK, 8, 10));
        dlsDeck.getCards().add(new GreenDragonBean("Angry Dragon", Color.RED, 9, 9));
        dlsDeck.getCards().add(new GreenDragonBean("Shadow Dragon", Color.GRAY, 4, 14));


        playersDeck = (DeckBean) getIntent().getSerializableExtra(PLAYERS_FINAL_DECK);
        Log.w("TAG_DECKS", "get the player's final deck");

        rvPlayersHand = findViewById(R.id.rvPlayersHand);
        playersHand = new ArrayList<>();
        playersCemetery = new ArrayList<>();
        playersHandAdapter = new HandAdapter(playersHand, this);
        rvPlayersHand.setAdapter(playersHandAdapter);
        rvPlayersHand.setLayoutManager(new GridLayoutManager(this, 4));
        tvPlayersHP = findViewById(R.id.tvPlayersHP);

        rvDlsHand = findViewById(R.id.rvDlsHand);
        dlsHand = new ArrayList<>();
        dlsCemetery = new ArrayList<>();
        dlsHandAdapter = new CardAdapter(dlsHand, this);
        rvDlsHand.setAdapter(dlsHandAdapter);
        rvDlsHand.setLayoutManager(new GridLayoutManager(this, 4));
        tvDlsHP = findViewById(R.id.tvDlsHP);

        rvPlayersArena = findViewById(R.id.rvPlayersArena);
        playersPlayedCards = new ArrayList<>();
        playersArenaAdapter = new CardAdapter(playersPlayedCards, this);
        rvPlayersArena.setAdapter(playersArenaAdapter);
        rvPlayersArena.setLayoutManager(new GridLayoutManager(this, 5));

        rvDlsArena = findViewById(R.id.rvDlsArena);
        dlsPlayedCards = new ArrayList<>();
        dlsArenaAdapter = new CardAdapter(dlsPlayedCards, this);
        rvDlsArena.setAdapter(dlsArenaAdapter);
        rvDlsArena.setLayoutManager(new GridLayoutManager(this, 5));

        turn = 0;
        ran = new Random();
        dlsHP = AVATARS_HP;
        tvDlsHP.setText("Dragon Lord : "+dlsHP+"HP");
        playersHP = AVATARS_HP;
        tvPlayersHP.setText("Challenger : "+playersHP+"HP");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, MENU_TURN, 1, "Start")
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.add(1, MENU_NEW_GAME, 2, "New game").setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                onTurnClick(item);
                break;
            case 2:
                Log.w("TAG_BUTTON", "click new game");
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
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
                    if (playersPlayedCards.size() >= 5){
                        Toast.makeText(this, "No more place on the field...",
                                Toast.LENGTH_SHORT).show();
                    }else if (!hasPlay) {
                        playersHand.remove(cardBean);
                        playersHandAdapter.notifyDataSetChanged();
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
                        attacker.setAttacker(false);
                        attacker = null;
                        playersArenaAdapter.notifyDataSetChanged();
                    } else if (playersPlayedCards.contains(cardBean)) {
                        Toast.makeText(this,
                                "Dragons are too proud to attack their allies !",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        attacker.attacks(cardBean);
                        attackResult(attacker, playersArenaAdapter, playersPlayedCards);
                        attackResult(cardBean, dlsArenaAdapter, dlsPlayedCards);
                        attacker = null;
                    }
                } else {
                    if (cardBean.getStatusAwake() == STATUS_AWAKENING_READY) {
                        attacker = cardBean;
                        attacker.setAttacker(true);
                        playersArenaAdapter.notifyDataSetChanged();

                        Log.w("TAG_ATTACK", "attacker prepared :  " + cardBean.getName());
                    } else {
                        switch (cardBean.getStatusAwake()){
                            case 1:
                                Toast.makeText(this, "This dragon is tired...",
                                        Toast.LENGTH_SHORT).show();
                                break;
                            case 2:
                                Toast.makeText(this, "This dragon is not totally wake up...",
                                        Toast.LENGTH_SHORT).show();
                                break;
                            case 3:
                                Toast.makeText(this, "This dragon is still sleeping...",
                                        Toast.LENGTH_SHORT).show();
                                break;
                            case 4:
                                Toast.makeText(this, "This dragon is in a deep sleep...",
                                        Toast.LENGTH_SHORT).show();
                                break;
                        }
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
                Log.w("TAG_TURN", "Click on next turn button");

                attacker = null;
                nextTurnConditions(playersArenaAdapter, playersPlayedCards);
                nextTurnConditions(dlsArenaAdapter, dlsPlayedCards);

                if (playersDeck.getCards().size() == 0) {
                    Toast.makeText(this, "No more cards in deck",
                            Toast.LENGTH_SHORT).show();
                } else if (playersHand.size() == 4) {
                    CardBean drawnCard = drawRandomCard(playersDeck, playersCemetery);
                    Toast.makeText(this, "Your hand is too full ! "
                                    +drawnCard.getName()+" is lost...",
                            Toast.LENGTH_SHORT).show();
                    Log.w("TAG_TURN", drawnCard.getName() + " sent to the cemetary");
                }else {
                    drawRandomCard(playersDeck, playersHand);
                }
                if (dlsHand.size() == 5){
                    drawRandomCard(dlsDeck, dlsCemetery);
                }else if (dlsDeck.getCards().size() != 0) {
                    drawRandomCard(dlsDeck, dlsHand);
                }
                if (dlsHand.size() > 0) {
                    ranDlsPlayedCard = dlsHand.get(ran.nextInt(dlsHand.size()));
                    dlsHand.remove(ranDlsPlayedCard);
                    dlsHandAdapter.notifyDataSetChanged();
                    dlsPlayedCards.add(ranDlsPlayedCard);
                    dlsArenaAdapter.notifyDataSetChanged();
                    ranDlsPlayedCard.setLocation(IN_ARENA);
                }
                if (dlsPlayedCards.size() > 0) {
                            dlsAttack();
                }
        }
        playersHandAdapter.notifyDataSetChanged();
        dlsHandAdapter.notifyDataSetChanged();
        turn += 1;
    }

    private CardBean drawRandomCard(DeckBean deck, ArrayList<CardBean> hand) {
        CardBean randomCard;
        randomCard = deck.getCards().get(ran.nextInt(deck.getCards().size()));
        hand.add(randomCard);
        randomCard.setLocation(IN_HAND);
        deck.getCards().remove(randomCard);
        Log.w("TAG_TURN", randomCard.getName() + " picked");
        return randomCard;
    }

    private void attackResult(CardBean card, CardAdapter adapter, ArrayList<CardBean> playedCards) {
        if (card.getHp() < 1) {
            playedCards.remove(card);
            Log.w("TAG_ATTACK", card.getName()+" died");
        }
        adapter.notifyDataSetChanged();
    }

    private void nextTurnConditions(CardAdapter adapter, ArrayList<CardBean> playedCards) {
        ArrayList<CardBean> toRemoveCards = new ArrayList<>();
        for (CardBean card : playedCards) {
            if (card.getStatusAwake() != STATUS_AWAKENING_READY) {
                card.setStatusAwake(card.getStatusAwake() - 1);
                Log.w("TAG_WAKING", card.getName()+" awakening status is "
                        +card.getStatusAwake());
            }
            switch (card.getStatusHealth()){
                case STATUS_HEALTH_POISONED:
                    card.setHp(card.getHp() - POISON_POWER);
                    card.setStatusHealth(STATUS_HEALTH_HEALTHY);
                    if (card.getHp() < 1){
                        toRemoveCards.add(card);
                        Log.w("TAG_ATTACK", card.getName()+" succumbs to his wounds");
                    }
                    break;
                default:
            }
        }
        playedCards.removeAll(toRemoveCards);
        adapter.notifyDataSetChanged();
    }

    private void dlsAttack() {
        CardBean ranDefender;
        ArrayList<CardBean> attackGroup;

        attackGroup = new ArrayList<>();

        for (int i = 0; i < dlsPlayedCards.size(); i++) {
            if(dlsPlayedCards.get(i).getStatusAwake() == STATUS_AWAKENING_READY){
                attackGroup.add(dlsPlayedCards.get(i));
                Log.w("TAG_ATTACK", "Add in DLS attack group : "
                        +dlsPlayedCards.get(i).getName());
            }
        }
        while (attackGroup.size() > 0) {
            Log.w("TAG_TEMP", "Size of the attack group : "+attackGroup.size());
            attacker = attackGroup.get(ran.nextInt(attackGroup.size()));
                if (playersPlayedCards.size() > 0) {
                    ranDefender = playersPlayedCards.get(ran.nextInt(playersPlayedCards.size()));
                    attacker.attacks(ranDefender);
                    attackResult(ranDefender, playersArenaAdapter, playersPlayedCards);
                    attackResult(attacker, dlsArenaAdapter, dlsPlayedCards);
                } else {
                    playersHP += - attacker.getPower();
                    tvPlayersHP.setText("Challenger : "+playersHP+"HP");
                    Log.w("TAG_DLS_ATTACK", attacker.getName()+" attacks player");
                    victoryConditions();
                }
            attackGroup.remove(attacker);
            attacker = null;
        }
    }

    public void onDLsClick(View view) {
        if (attacker != null && dlsPlayedCards.size() == 0){
            Log.w("TAG_ATTACK", attacker.getName()+" attacks DL");
            dlsHP += - attacker.getPower();
            tvDlsHP.setText("Dragon Lord : "+dlsHP+"HP");
            attacker.setStatusAwake(STATUS_AWAKENING_TIRED);
            attacker.setAttacker(false);
            attacker = null;
            victoryConditions();
            playersArenaAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "YOU REALLY THINK YOU CAN REACH ME ?!",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void victoryConditions(){
        if (dlsHP <= 0){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("Congratulations, The Dragon's Lord is dead !");
            alertDialogBuilder.setTitle("Victory !");
            alertDialogBuilder.setPositiveButton("ok",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(FightActivity.this,
                                    MainActivity.class);
                            startActivity(intent);
                        }
                    });
            alertDialogBuilder.show();
        }
        if (playersHP <=0){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("Sadly, you died...");
            alertDialogBuilder.setTitle("Defeat...");
            alertDialogBuilder.setPositiveButton("ok",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(FightActivity.this,
                                    MainActivity.class);
                            startActivity(intent);
                        }
                    });
            alertDialogBuilder.show();
        }
    }
}
