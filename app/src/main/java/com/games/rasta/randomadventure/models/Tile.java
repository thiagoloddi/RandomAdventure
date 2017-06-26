package com.games.rasta.randomadventure.models;

import java.util.ArrayList;
import java.util.List;

public class Tile {

  public static final String TOP = "TOP";

  public static final String RIGHT = "RIGHT";
  public static final String LEFT = "LEFT";
  public static final String BOTTOM = "BOTTOM";
  public static final String CENTER = "CENTER";

  public static final int NULL = 0;
  public static final int SEA_WATER = 1;
  public static final int LAND = 2;
  public static final int CLEAR_WATER = 3;
  public static final int BEACH = 4;
  public static final int FOREST = 5;
  public static final int HILL = 6;

  private boolean isCoastal = false;
  private boolean hasRiver = false;
  private boolean hasLake = false;
  private boolean riverSource = false;
  private boolean explored = false;
  private final Coords coords;
  private int type;
  private String riverStart;
  private String riverEnd;
  private int riverResourceId = 0;

  public Tile(Coords coords, int type) {
    this.coords = coords;
    this.type = type;
  }

  public String getRelativePosition(Tile adj) {
    int deltaX = coords.getX() - adj.getCoords().getX();
    int deltaY = coords.getY() - adj.getCoords().getY();

    if(deltaY == 1) return TOP;
    if(deltaY == -1) return BOTTOM;
    if(deltaX == 1) return LEFT;
    if(deltaX == -1) return RIGHT;

    return CENTER;
  }

  public static List<Tile> unique(List<Tile> list) {
    List<Tile> unique = new ArrayList<>();
    for(Tile tile: list) {
      for(int i = 0; i < unique.size(); i++) {
        Tile u = unique.get(i);
        if(u.getCoords().getX() == tile.getCoords().getX() && u.getCoords().getY() == tile.getCoords().getY()) continue;

        if(i == unique.size() - 1) {
          unique.add(tile);
        }
      }
      if(unique.size() == 0) unique.add(tile);
    }
    return unique;
  }

  // GETTERS AND SETTERS
  public Coords getCoords() {
    return coords;
  }

  public int getType() {
    return type;
  }

  public boolean isCoastal() {
    return isCoastal;
  }

  public void setCoastal(boolean coastal) {
    isCoastal = coastal;
  }

  public boolean hasRiver() {
    return hasRiver;
  }

  public void setHasRiver(boolean hasRiver) {
    this.hasRiver = hasRiver;
  }

  public boolean isRiverSource() {
    return riverSource;
  }

  public void setRiverSource(boolean riverSource) {
    this.riverSource = riverSource;
  }

  public String getRiverStart() {
    return riverStart;
  }

  public void setRiverStart(String riverStart) {
    this.riverStart = riverStart;
  }

  public String getRiverEnd() {
    return riverEnd;
  }

  public void setRiverEnd(String riverEnd) {
    this.riverEnd = riverEnd;
  }

  public void setType(int type) {
    this.type = type;
  }

  public boolean hasLake() {
    return hasLake;
  }

  public void setHasLake(boolean hasLake) {
    this.hasLake = hasLake;
  }

  public int getRiverResourceId() {
    return riverResourceId;
  }

  public void setRiverResourceId(int riverResourceId) {
    this.riverResourceId = riverResourceId;
  }

  public boolean isExplored() {
    return explored;
  }

  public void setExplored(boolean explored) {
    this.explored = explored;
  }
}
