package com.games.rasta.randomadventure.engine.map;

import android.util.Log;

import com.games.rasta.randomadventure.GameApplication;
import com.games.rasta.randomadventure.models.Coords;
import com.games.rasta.randomadventure.models.Island;
import com.games.rasta.randomadventure.models.Tile;

import java.util.ArrayList;
import java.util.List;

public class IslandFactory {

  private final Integer MAX_AREA = 250;
  private final Integer MAX_RADIUS = 25;
  private final int MAX_RIVER_LENGTH = 10;

  private Island island;
  private LandFactory landFactory;
  private WaterFactory waterFactory;

  public IslandFactory(LandFactory landFactory, WaterFactory waterFactory) {
    this.landFactory = landFactory;
    this.waterFactory = waterFactory;
  }

  private void init() {
    this.island = new Island();

    for(int i = 0; i < MAX_RADIUS; i++) {
      for(int j = 0; j < MAX_RADIUS; j++) {
        island.put(new Tile(new Coords(j, i), Tile.NULL));
      }
    }

    this.waterFactory.init(island);
    this.landFactory.init(island);
  }


  public Island createIsland() {
    Log.d(GameApplication.TAG, "-- CREATING ISLAND");

    int maxArea = (int) Math.floor(MAX_AREA*(0.7 + 0.6*Math.random()));
    Log.d(GameApplication.TAG, "-- maxArea: " + maxArea);

    int x0 = (MAX_RADIUS - 1) / 2;
    int y0 = x0;

    this.init();
    this.landFactory.spawnLand(x0, y0, maxArea);
    this.waterFactory.spawnSea();
    this.waterFactory.spawnLakes();
    this.landFactory.spawnBeach();
    this.landFactory.spawnForest();
    this.landFactory.spawnHill();
    island.print();
    this.waterFactory.spawnRiver();

    return island;
  }
}
