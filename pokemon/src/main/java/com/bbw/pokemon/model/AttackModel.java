package com.bbw.pokemon.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttackModel {
    private String name;
    private int attackBuff;
    private int attackCount;

    public AttackModel(String name, int attackBuff, int attackCount) {
        setName(name);
        setAttackBuff(attackBuff);
        setAttackCount(attackCount);
    }
}
