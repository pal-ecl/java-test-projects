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
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lescure.dragoncardgame.adapters.CardAdapter;
import com.lescure.dragoncardgame.adapters.DlsHandAdapter;
import com.lescure.dragoncardgame.adapters.HandAdapter;
import com.lescure.dragoncardgame.model.AvailableCards.GreenDragonBean;
import com.lescure.dragoncardgame.model.CardBean;
import com.lescure.dragoncardgame.model.DeckBean;
import com.lescure.dragoncardgame.model.GameRules;

import java.util.ArrayList;
import java.util.Random;

public class FightActivity extends AppCompatActivity implements CardAdapter.CardListener,
        HandAdapter.CardListener {

    private DeckBean playersDeck, dlsDeck;
    private ArrayList<CardBean> playersHand, dlsHand, playersPlayedCards, dlsPlayedCards,
            playersCemetery, dlsCemetery;
    private RecyclerView rvPlayersHand, rvDlsHand, rvPlayersArena, rvDlsArena;
    private CardAdapter playersArenaAdapter, dlsArenaAdapter;
    private HandAdapter playersHandAdapter;
    private DlsHandAdapter dlsHandAdapter;
    private TextView tvDlsHP, tvPlayersHP, tvPlayersDeck, tvDlsDeck;
    private int turn, dlsHP, playersHP;
    private Random ran;
    private CardBean attacker;
    private View attackerView;
    private boolean hasPlay;
    private CardView cvDlsAvatar, cvPlayersAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fight);

        //creation de cartes temporaire pour test
        dlsDeck = new DeckBean("Dragon Lord's deck", new ArrayList<CardBean>());
        dlsDeck.getCards().add(new GreenDragonBean
                ("Poisonous Dragon", Color.CYAN, 7, 11));
        dlsDeck.getCards().add(new CardBean
                ("Dead Dragon", Color.GRAY, 5, 13));
        dlsDeck.getCards().add(new CardBean
                ("Bad Dragon", Color.BLACK, 8, 10));
        dlsDeck.getCards().add(new CardBean
                ("Angry Dragon", Color.RED, 9, 9));
        dlsDeck.getCards().add(new CardBean
                ("Shadow Dragon", Color.GRAY, 4, 14));
        //fin de creation de cartes temporaire pour test

        playersDeck = (DeckBean) getIntent().getSerializableExtra(GameRules.PLAYERS_FINAL_DECK);
        Log.w("TAG_DECKS", "get the player's final deck");

        rvPlayersHand = findViewById(R.id.rvPlayersHand);
        playersHand = new ArrayList<>();
        playersCemetery = new ArrayList<>();
        playersHandAdapter = new HandAdapter(playersHand, this);
        rvPlayersHand.setAdapter(playersHandAdapter);
        rvPlayersHand.setLayoutManager(new GridLayoutManager(this, 4));
        tvPlayersHP = findViewById(R.id.tvPlayersHP);
        tvPlayersDeck = findViewById(R.id.tvPlayersDeck);
        cvPlayersAvatar = findViewById(R.id.cvPlayersAvatar);

        rvDlsHand = findViewById(R.id.rvDlsHand);
        dlsHand = new ArrayList<>();
        dlsCemetery = new ArrayList<>();
        dlsHandAdapter = new DlsHandAdapter(dlsHand);
        rvDlsHand.setAdapter(dlsHandAdapter);
        rvDlsHand.setLayoutManager(new GridLayoutManager(this, 4));
        tvDlsHP = findViewById(R.id.tvDlsHP);
        tvDlsDeck = findViewById(R.id.tvDlsDeck);
        cvDlsAvatar = findViewById(R.id.cvDlsAvatar);

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
        dlsHP = GameRules.AVATARS_HP;
        tvDlsHP.setText(Integer.toString(dlsHP));
        playersHP = GameRules.AVATARS_HP;
        tvPlayersHP.setText(Integer.toString(playersHP));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, GameRules.MENU_TURN, 1, "Start")
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.add(1, GameRules.MENU_NEW_GAME, 2, "New game")
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 2:
                onTurnClick(item);
                break;
            case 1:
                Log.w("TAG_BUTTON", "click new game");
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCardClick(CardBean cardBean, View view) {
        switch (cardBean.getLocation()) {
            case GameRules.IN_HAND:
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
                        cardBean.setStatusAwake(cardBean.getBaseStatusAwake());
                        playersArenaAdapter.notifyDataSetChanged();
                        cardBean.setLocation(GameRules.IN_ARENA);
                        Log.w("TAG_PLAY", "play card : " + cardBean.getName());
                        hasPlay = true;
                    } else {
                        Toast.makeText(this, "You have already played this turn !",
                                Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case GameRules.IN_ARENA:
                if (attacker != null) {
                    if (attacker.equals(cardBean)) {
                        Log.w("TAG_ATTACK", attacker.getName()+" is not attacking yet.");
                        attacker.setAttacker(false);
                        attacker = null;
                        attackerView = null;
                        playersArenaAdapter.notifyDataSetChanged();
                    } else if (playersPlayedCards.contains(cardBean)) {
                        Toast.makeText(this,
                                "Dragons are too proud to attack their allies !",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        // testing animation
                        //attackAnimation(cvPlayersAvatar, GameRules.ANIM_PLAYERS_ATTACK);
                        //defendAnimation(cvDlsAvatar);
                        //attackAnimation(attackerView, GameRules.ANIM_PLAYERS_ATTACK);
                        //defendAnimation(view);
                        attacker.setAnimate(GameRules.ANIMATE_ATTACK);
                        cardBean.setAnimate(GameRules.ANIMATE_DEFEND);
                        //end testing animation
                        attacker.attacks(cardBean);
                        attackResult(attacker, playersArenaAdapter, playersPlayedCards);
                        attackResult(cardBean, dlsArenaAdapter, dlsPlayedCards);
                        attacker = null;
                        attackerView = null;
                    }
                } else {
                    if (cardBean.getStatusAwake() == GameRules.STATUS_AWAKENING_READY) {
                        attacker = cardBean;
                        attackerView = view;
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
                for (int i = 0; i < GameRules.NBR_CARDS_START_HAND; i++) {
                    drawRandomCard(playersDeck, playersHand);
                    drawRandomCard(dlsDeck, dlsHand);
                }
                break;
            default:
                Log.w("TAG_TURN", "Click on next turn button");

                attacker = null;
                attackerView = null;
                nextTurnConditions(playersArenaAdapter, playersPlayedCards);
                nextTurnConditions(dlsArenaAdapter, dlsPlayedCards);

                if (playersDeck.getCards().size() == 0) {
                    Toast.makeText(this, "No more cards in deck",
                            Toast.LENGTH_SHORT).show();
                } else if (playersHand.size() == GameRules.HANDS_SIZE - 1) {
                    CardBean drawnCard = drawRandomCard(playersDeck, playersCemetery);
                    Toast.makeText(this, "Your hand is too full ! "
                                    +drawnCard.getName()+" is lost...",
                            Toast.LENGTH_SHORT).show();
                    Log.w("TAG_TURN", drawnCard.getName() + " sent to the cemetary");
                }else {
                    drawRandomCard(playersDeck, playersHand);
                }
                if (dlsHand.size() == GameRules.HANDS_SIZE){
                    drawRandomCard(dlsDeck, dlsCemetery);
                }else if (dlsDeck.getCards().size() != 0) {
                    drawRandomCard(dlsDeck, dlsHand);
                }
                if (dlsHand.size() > 0) {
                    ranDlsPlayedCard = dlsHand.get(ran.nextInt(dlsHand.size()));
                    dlsHand.remove(ranDlsPlayedCard);
                    dlsHandAdapter.notifyDataSetChanged();
                    dlsPlayedCards.add(ranDlsPlayedCard);
                    ranDlsPlayedCard.setStatusAwake(ranDlsPlayedCard.getBaseStatusAwake());
                    dlsArenaAdapter.notifyDataSetChanged();
                    ranDlsPlayedCard.setLocation(GameRules.IN_ARENA);
                }
                if (dlsPlayedCards.size() > 0) {
                            dlsAttack();
                }
        }
        playersHandAdapter.notifyDataSetChanged();
        dlsHandAdapter.notifyDataSetChanged();
        tvPlayersDeck.setText(Integer.toString(playersDeck.getCards().size()));
        tvDlsDeck.setText(Integer.toString(dlsDeck.getCards().size()));
        turn += 1;
    }

    private CardBean drawRandomCard(DeckBean deck, ArrayList<CardBean> hand) {
        CardBean randomCard;
        randomCard = deck.getCards().get(ran.nextInt(deck.getCards().size()));
        hand.add(randomCard);
        randomCard.setLocation(GameRules.IN_HAND);
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
            if (card.getStatusAwake() != GameRules.STATUS_AWAKENING_READY) {
                card.setStatusAwake(card.getStatusAwake() - 1);
                Log.w("TAG_WAKING", card.getName()+" awakening status is "
                        +card.getStatusAwake());
            }
            switch (card.getStatusHealth()){
                case GameRules.STATUS_HEALTH_POISONED:
                    card.setHp(card.getHp() - GameRules.POISON_POWER);
                    card.setStatusHealth(GameRules.STATUS_HEALTH_HEALTHY);
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
            if(dlsPlayedCards.get(i).getStatusAwake() == GameRules.STATUS_AWAKENING_READY){
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
                    tvPlayersHP.setText(Integer.toString(playersHP));
                    Log.w("TAG_DLS_ATTACK", attacker.getName()+" attacks player");
                    victoryConditions();
                }
            attackGroup.remove(attacker);
            attacker = null;
            attackerView = null;
        }
    }

    public void onDLsClick(View view) {
        if (attacker != null && dlsPlayedCards.size() == 0){
            Log.w("TAG_ATTACK", attacker.getName()+" attacks DL");
            dlsHP += - attacker.getPower();
            tvDlsHP.setText(Integer.toString(dlsHP));
            attacker.setStatusAwake(GameRules.STATUS_AWAKENING_TIRED);
            attacker.setAttacker(false);
            attacker = null;
            attackerView = null;
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
            alertDialogBuilder.setMessage("Congratulations, The Dragon Lord is dead !");
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

    public void attackAnimation(View view, int toYDelta){
        Log.w("TAG_ANIMATION", "animation attack : "+getResources()
                .getResourceEntryName(view.getId()));
        RotateAnimation rotateAnimation = new RotateAnimation(0.0f, 25.0f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(100);

        TranslateAnimation translateAnimation = new TranslateAnimation(
                0, 0, 0, toYDelta);
        translateAnimation.setStartOffset(200);
        translateAnimation.setDuration(100);

        AnimationSet set = new AnimationSet(true);
        set.addAnimation(rotateAnimation);
        set.addAnimation(translateAnimation);
        view.startAnimation(set);
    }

     public void defendAnimation (View view){
        Log.w("TAG_ANIMATION", "animation defend : "+getResources()
                .getResourceEntryName(view.getId()));
        TranslateAnimation translateAnimation = new TranslateAnimation(
                -3, 3, 0, 0);
        translateAnimation.setDuration(80);
        translateAnimation.setRepeatCount(5);

        AnimationSet set = new AnimationSet(true);
        set.addAnimation(translateAnimation);
        view.startAnimation(set);
    }
}
