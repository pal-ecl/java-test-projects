package com.lescure.dragoncardgame.model;

import java.io.Serializable;

public class CardBean implements Serializable {
    private static final long serialVersionUID = -3754517063424839031L;
    private long id;
    private String name;
    private int textColor;
    private String location;
    private int power;
    private int hp;
    private String status;

    public CardBean(String name, int textColor, int power, int hp) {
        this.name = name;
        this.textColor = textColor;
        this.power = power;
        this.hp = hp;
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

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
