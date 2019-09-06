package com.lescure.dragoncardgame.model.AvailableCards;

import android.util.Log;

import com.lescure.dragoncardgame.FightActivity;
import com.lescure.dragoncardgame.model.CardBean;

public class GreenDragonBean extends CardBean {
    public GreenDragonBean(String name, int textColor, int power, int hp) {
        super(name, textColor, power, hp);
        this.statusAwake = FightActivity.STATUS_AWAKENING_WAKE;
        this.effect = FightActivity.EFFECT_POISON;
    }

    @Override
    public void attacks(CardBean defender) {
        defender.setStatusHealth(FightActivity.STATUS_HEALTH_POISONED);
        Log.w("TAG_ATTACK", this.getName()+" poisoned "+defender.getName());
        super.attacks(defender);
    }
}
