package com.company.objects.creatures;

import com.company.objects.items.Equipment;
import com.company.objects.items.Item;
import com.company.objects.items.Weapon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

public class Mob extends Creature {
    public final HashMap<Equipment.EquipmentType, Equipment> equipmentItems = new HashMap();
    public Weapon weapon;

    public Mob(String name, int maxHitPoints, int attackPower, int defencePoints) {
        super(name, maxHitPoints, attackPower, defencePoints);
        model = name.charAt(0);
    }

    public Mob(String name, int maxHitPoints, int attackPower, int defencePoints, Weapon weapon) {
        this(name, maxHitPoints, attackPower, defencePoints);
        setWeapon(weapon);
    }

    @Override
    public int getCost() {
        return super.getCost() + getWeaponAttackPower() + getDefencePoints();
    }

    @Override
    public int getDamage() {
        int damage = getAttackPower();
        if(weapon != null) damage += ThreadLocalRandom.current().nextInt(weapon.getAttackPower());
        return damage;
    }

    /*@Override
    public void receiveDamage(short damage)
    {
        if(damage > defencePoints) hitPoints -= damage - defencePoints;
        else hitPoints--;
    }*/

    @Override
    public ArrayList<Item> getItemsAfterDeath() {
        ArrayList<Item> items = super.getItemsAfterDeath();
        items.addAll(equipmentItems.values());
        items.add(weapon);
        return items;
    }

    @Override
    public int getDefencePoints()
    {
        return super.getDefencePoints() + equipmentItems.values().stream().mapToInt(Equipment::getDefencePoints).sum();
    }

    public int getWeaponAttackPower() { return weapon != null ? weapon.getAttackPower() : 0; }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public HashMap<Equipment.EquipmentType, Equipment> getEquipmentItems()
    {
        return equipmentItems;
    }
}
