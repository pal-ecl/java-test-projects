package com.lescure.dragoncardgame.model.AvailableCards;

import android.util.Log;

import com.lescure.dragoncardgame.model.CardBean;
import com.lescure.dragoncardgame.model.GameRules;

public class GreenDragonBean extends CardBean {
    public GreenDragonBean(String name, int dragonColor, int power, int hp) {
        super(name, dragonColor, power, hp);
        this.baseStatusAwake = GameRules.STATUS_AWAKENING_WAKE;
        this.effect = GameRules.EFFECT_POISON;
    }

    @Override
    public void attacks(CardBean defender) {
        defender.setStatusHealth(GameRules.STATUS_HEALTH_POISONED);
        Log.w("TAG_ATTACK", this.getName()+" poisoned "+defender.getName());
        super.attacks(defender);
    }
}
