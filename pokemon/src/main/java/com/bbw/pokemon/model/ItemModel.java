package com.bbw.pokemon.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ItemModel {
    private String name;
    private int buff;
    private int id;
    private String kategorie;

    public ItemModel (String name, int buff, int id, String kategorie) {
        this.buff =buff;
        this.name = name;
        this.id = id;
        this.kategorie = kategorie;

    }
}
