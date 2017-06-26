package com.games.rasta.randomadventure.models;

public class Player {
  private String name;
  private int level;
  private int xp;

  private int attack;
  private int defense;
  private int speed;

  private int maxHealth;
  private int currentHealth;

  public Player(String name) {
    this.name = name;

    this.level = 1;
    this.xp = 0;
    this.attack = 10;
    this.defense = 10;
    this.speed = 10;
    this.maxHealth = 100;
    this.currentHealth = 100;

  }
}
