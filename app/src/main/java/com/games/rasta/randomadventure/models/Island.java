package com.games.rasta.randomadventure.models;

import android.util.Log;
import android.util.SparseArray;

import com.games.rasta.randomadventure.GameApplication;

import java.util.ArrayList;
import java.util.List;

import static com.games.rasta.randomadventure.engine.map.MapFactory.CLEAR_WATER;
import static com.games.rasta.randomadventure.engine.map.MapFactory.SEA_WATER;

public class Island extends Map {

  public static final int MAX_ZOOM = 9;
  public static final int MAX_WIDTH = 25;

//  private SparseArray<SparseArray<Tile>> map;

  public Island() {
    super(MAX_WIDTH);
  }

  public void updateCoastalTiles() {
    for(int i = 0; i < radius; i ++) {
      for(int j = 0; j < radius; j ++) {
        Tile tile = this.get(j, i);
        if(tile.getType() != SEA_WATER && tile.getType() != CLEAR_WATER) {
          List<Tile> tiles = getSurroundingTiles(tile, SEA_WATER);
          if(tiles.size() > 0) tile.setCoastal(true);
        }
      }
    }
  }

  public void updateLakeTiles() {
    for(int i = 0; i < radius; i ++) {
      for(int j = 0; j < radius; j ++) {
        Tile tile = this.get(j, i);

        if(tile.getType() != SEA_WATER && tile.getType() != CLEAR_WATER) {

          List<Tile> tiles = getSurroundingTiles(tile, CLEAR_WATER);
          if(tiles.size() > 0) {
            tile.setHasLake(true);
          }
        }
      }
    }
  }

  public List<Tile> getCoastalTiles() {
    List<Tile> tiles = new ArrayList<>();

    for(int i = 0; i < radius; i ++) {
      for(int j = 0; j < radius; j ++) {
        Tile tile = this.get(j, i);
//        Log.d(GameApplication.TAG, "isCoastal: " + tile.isCoastal());
        if(tile.isCoastal()) {
          tiles.add(tile);
        }
      }
    }
    return tiles;
  }

  public void setExplored(Coords charPosition) {
    List<Tile> surrounding = getAllSurroundingTiles(charPosition);
    for(Tile t: surrounding) {
      t.setExplored(true);
    }
  }

  public Coords getInitialPosition() {
    int coord = (MAX_WIDTH - 1) / 2;
    return new Coords(coord, coord);
  }
}
