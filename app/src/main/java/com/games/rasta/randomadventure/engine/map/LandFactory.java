package com.games.rasta.randomadventure.engine.map;

import android.util.Log;

import com.games.rasta.randomadventure.GameApplication;
import com.games.rasta.randomadventure.models.Coords;
import com.games.rasta.randomadventure.models.Island;
import com.games.rasta.randomadventure.models.Tile;

import java.util.ArrayList;
import java.util.List;

public class LandFactory {

  private static final Double LAND_RATE = 0.98;
  private static final Double FOREST_RATE = 0.5;
  private static final Double HILL_RATE = 0.8;


  private int MAX_X;
  private int MAX_Y;
  private Island island;


  public void init(Island island) {
    this.island = island;
    this.MAX_X = island.size() - 1;
    this.MAX_Y = island.size() - 1;
  }

  public void spawnLand(int x0, int y0, int maxArea) {
    spawnInitialLand(x0, y0);

    int area = 9;
    while(area < maxArea) {
//      Log.d(GameApplication.TAG, "--- SPAWN LAND - area: " + area);
      List<Coords> newLands = new ArrayList<>();

      for(int i = 1; i < MAX_X; i++) {
        for(int j = 1; j < MAX_Y; j++) {
          if(island.get(j, i).getType() == MapFactory.LAND) {
//            Log.d(GameApplication.TAG, "land: (" + j + ", " + i + ")");
            newLands.addAll(getNewLand(j, i));
          }
        }
      }

      List<Coords> unique = Coords.unique(newLands);
      fill(unique);
      area += unique.size();
//      Log.d(GameApplication.TAG, "-- area: " + Integer.toString(area));
//      Log.d(GameApplication.TAG, "-- uniq: " + uniq.size());
    }
  }

  private void spawnInitialLand(int x0, int y0) {
    island.get(x0, y0).setType(Tile.LAND);
    List<Tile> initialTiles = island.getAllSurroundingTiles(island.get(x0, x0));
    for(Tile t: initialTiles) {
      t.setType(Tile.LAND);
    }
  }

  private List<Coords> getNewLand(int x0, int y0) {
//    Log.d(GameApplication.TAG, "--- SPAWN LAND - " + new Coords(x0, y0).print());
    List<Coords> coords = new ArrayList<>();
    List<Tile> adj = island.getSurroundingTiles(new Coords(x0, y0));

    for(Tile t: adj) {
      if(island.inAreaBounds(t) && t.getType() == Tile.NULL && Math.random() > LAND_RATE)
        coords.add(t.getCoords());
    }

    return coords;
  }

  public void fill(List<Coords> lands) {
//    Log.d(GameApplication.TAG, "-- FILLING ISLAND");
    for(Coords land: lands) {
      island.get(land).setType(Tile.LAND);
    }
  }

  public void spawnBeach() {
    Log.d(GameApplication.TAG, "--- SPAWN BEACH");
    for(int i = 0; i < island.size(); i++) {
      for(int j = 0; j < island.size(); j++) {
        Tile tile = island.get(j, i);
        if(tile.getType() == Tile.LAND && island.getAllSurroundingTiles(tile, Tile.SEA_WATER).size() > 2) {
          island.get(tile.getCoords()).setType(Tile.BEACH);
        }
      }
    }
  }

  public void spawnForest() {
    Log.d(GameApplication.TAG, "--- SPAWN FOREST");

    for(int i = 0; i < island.size(); i++) {
      for(int j = 0; j < island.size(); j++) {
        Tile tile = island.get(j, i);
        if(tile.getType() == MapFactory.LAND && Math.random() > FOREST_RATE)
          island.get(tile.getCoords()).setType(Tile.FOREST);
      }
    }
  }

  public void spawnHill() {
    Log.d(GameApplication.TAG, "--- SPAWN HILL");

    for(int i = 0; i < island.size(); i++) {
      for(int j = 0; j < island.size(); j++) {
        Tile tile = island.get(j, i);
        if((tile.getType() == Tile.LAND || tile.getType() == Tile.FOREST) && Math.random() > HILL_RATE)
          island.get(tile.getCoords()).setType(Tile.HILL);
      }
    }
  }

}
