package com.games.rasta.randomadventure.models;

import java.util.ArrayList;
import java.util.List;

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

  public boolean equals(Coords coords) {
    return this.getX() == coords.getX() && this.getY() == coords.getY();
  }

  public static List<Coords> unique(List<Coords> list) {
    List<Coords> unique = new ArrayList<>();
    for(Coords coord: list) {
      for(int i = 0; i < unique.size(); i++) {
        Coords u = unique.get(i);
        if(u.getX() == coord.getX() && u.getY() == coord.getY()) continue;

        if(i == unique.size() - 1) {
          unique.add(coord);
        }
      }
      if(unique.size() == 0) unique.add(coord);
    }
    return unique;
  }
}
