package com.lescure.dragoncardgame.model;

public class GameRules {

    //menu
    public static final int MENU_NEW_GAME = 1;
    public final static int MENU_TURN = 2;

    //extras
    public final static String PLAYERS_FINAL_DECK = "playersFinalDeck";


    //game config
    public static final int DECK_CAPACITY = 10;
    public final static int NBR_CARDS_START_HAND = 3;
    public final static int HANDS_SIZE = 5;
    public final static int AVATARS_HP = 20;
    public final static int POISON_POWER = 15;

    //animations
    public final static int ANIM_PLAYERS_ATTACK = -50;
    public final static int ANIM_DLS_ATTACK = 50;


    //cards effects
    public static final String EFFECT_POISON = "Poison";

    //cards position
    public final static String IN_HAND = "inHand";
    public final static String IN_ARENA = "inArena";

    //awakening status
    public final static int STATUS_AWAKENING_READY = 0;
    public final static int STATUS_AWAKENING_TIRED = 1;
    public final static int STATUS_AWAKENING_WAKE = 2;
    public final static int STATUS_AWAKENING_SLEEP = 3;
    public final static int STATUS_AWAKENING_DEEP_SLEEP = 4;

    //health status
    public final static int STATUS_HEALTH_HEALTHY = 0;
    public final static int STATUS_HEALTH_POISONED = 1;
    public final static int STATUS_HEALTH_WEAKENED = 2;

    //animations
    public final static int ANIMATE_NONE = 0;
    public final static int ANIMATE_ATTACK = 1;
    public final static int ANIMATE_DEFEND = 2;


}

