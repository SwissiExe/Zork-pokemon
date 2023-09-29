package com.bbw.pokemon.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PokemonModel {
    private int id;
    private String name;
    private int hp;
    private int attack;
    private int defense;
    private int speed;
    private AttackModel[] attacks;


    public PokemonModel(int i, String name, int hp, int attack, int defense, int speed, AttackModel[] attacks) {
        setId(i);
        setName(name);
        setHp(hp);
        setAttack(attack);
        setDefense(defense);
        setSpeed(speed);
        setAttacks(attacks);
    }
}
