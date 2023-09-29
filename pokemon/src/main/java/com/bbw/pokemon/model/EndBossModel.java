package com.bbw.pokemon.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EndBossModel {
    private String name;
    private int hp;
    private int attack;
    private int defense;
    private int speed;
    private AttackModel[] attacks;

    public EndBossModel(String name, int hp, int attack, int defense, int speed, AttackModel[] attacks) {
        setName(name);
        setHp(hp);
        setAttack(attack);
        setDefense(defense);
        setSpeed(speed);
        setAttacks(attacks);
    }
}
