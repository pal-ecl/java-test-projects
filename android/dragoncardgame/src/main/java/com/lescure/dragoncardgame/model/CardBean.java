package com.lescure.dragoncardgame.model;

import android.util.Log;

import com.lescure.dragoncardgame.FightActivity;

import java.io.Serializable;

public class CardBean implements Serializable, Cloneable {
    private static final long serialVersionUID = -3754517063424839031L;
    private long id;
    private String name;
    private int dragonColor;
    private String location;
    private int power;
    private int hp;
    protected String effect;
    protected int statusAwake;
    protected int statusHealth;
    protected boolean attacker;

    public CardBean(String name, int dragonColor, int power, int hp) {
        this.name = name;
        this.dragonColor = dragonColor;
        this.power = power;
        this.hp = hp;
        this.statusAwake = FightActivity.STATUS_AWAKENING_TIRED;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDragonColor() {
        return dragonColor;
    }

    public void setDragonColor(int textColor) {
        this.dragonColor = dragonColor;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getStatusAwake() {
        return statusAwake;
    }

    public void setStatusAwake(int statusAwake) {
        this.statusAwake = statusAwake;
    }

    public int getStatusHealth() {
        return statusHealth;
    }

    public void setStatusHealth(int statusHealth) {
        this.statusHealth = statusHealth;
    }

    public boolean isAttacker() {
        return attacker;
    }

    public void setAttacker(boolean attacker) {
        this.attacker = attacker;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public void attacks(CardBean defender){
        Log.w("TAG_ATTACK", this.getName()+" attacks "+defender.getName());
        defender.defends(this);
        this.defends(defender);
        this.setStatusAwake(FightActivity.STATUS_AWAKENING_TIRED);
        this.setAttacker(false);
    }

    public void defends(CardBean attacker){
        this.setHp(this.getHp() - attacker.getPower());
        Log.w("TAG_ATTACK", this.getName()+" defends against "+attacker.getName());
    }

    public Object clone() {
        Object o = null;
        try {
            o = super.clone();
        } catch(CloneNotSupportedException cnse) {
            cnse.printStackTrace(System.err);
        }
        return o;
    }
}
