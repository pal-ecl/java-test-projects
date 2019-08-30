package com.lescure.dragoncardgame.model;

import java.io.Serializable;
import java.util.ArrayList;

public class DeckBean implements Serializable {

    private static final long serialVersionUID = -777316564545175706L;
    private long id;
    private String name;
    private ArrayList<CardBean> cards;

    public DeckBean(String name, ArrayList<CardBean> cards) {
        this.name = name;
        this.cards = cards;
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

    public ArrayList<CardBean> getCards() {
        return cards;
    }
}
