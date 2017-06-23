package com.games.rasta.randomadventure.models;

public class Coords {
  private final int x;
  private final int y;

  public Coords(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public String print() {
    return "(" + Integer.toString(x) + ", " + Integer.toString(y) + ")";
  }
}
