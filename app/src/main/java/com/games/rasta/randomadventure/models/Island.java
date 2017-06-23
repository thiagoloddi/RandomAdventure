package com.games.rasta.randomadventure.models;

import android.util.SparseArray;

import java.util.ArrayList;
import java.util.List;

import static com.games.rasta.randomadventure.engine.MapFactory.SEA_WATER;

public class Island {

  private SparseArray<SparseArray<Tile>> island;
  private int radius;

  public Island(int radius) {
    this.island = new SparseArray<>();
    this.radius = radius;

    this.init();
  }

  private void init() {
    for(int i = 0; i < radius; i++) {
      island.put(i, new SparseArray<Tile>());
      for(int j = 0; j < radius; j++) {
        island.get(i).put(i, null);
      }
    }
  }

  public void put(Tile tile) {
    island.get(tile.getCoords().getY()).put(tile.getCoords().getX(), tile);
  }

  public Tile get(int x, int y) {
    return island.get(y).get(x);
  }

  public int size() {
    return island.size();
  }

  public void updateTilesAttributes() {
    for(int i = 0; i < radius; i ++) {
      for(int j = 0; j < radius; j ++) {
        this.get(j, i).setCoastal(isCoastal(j, i));
      }
    }
  }

  private boolean isCoastal(int x, int y) {
    for(int m = -1; m < 2; m++) {
      for(int n = -1; n < 2; n++) {
        int x1 = x + m;
        int y1 = y + n;
        if(this.inBounds(x1, y1) && this.get(x1, y1).getType() == SEA_WATER) return true;
      }
    }
    return false;
  }

  public List<Tile> getCoastalTiles() {
    List<Tile> tiles = new ArrayList<>();

    for(int i = 0; i < radius; i ++) {
      for(int j = 0; j < radius; j ++) {
        Tile tile = this.get(j, i);
        if(tile.isCoastal()) tiles.add(tile);
      }
    }

    return tiles;
  }

  public boolean inBounds(int x, int y) {
    return x >= 0 && x < radius && y >= 0 && y < radius;
  }

}
