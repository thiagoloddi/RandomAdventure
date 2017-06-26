package com.games.rasta.randomadventure.engine.map;

import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;

import com.games.rasta.randomadventure.GameApplication;
import com.games.rasta.randomadventure.models.Island;

public class MapFactory {
  private final Integer HEIGHT = 101;
  private final Integer WIDTH = 101;
  private final Integer Xc = 50;
  private final Integer Yc = 50;

  public static final int SEA_WATER = 1;
  public static final int LAND = 2;
  public static final int CLEAR_WATER = 3;
  public static final int BEACH = 4;
  public static final int FOREST = 5;
  public static final int HILL = 6;

  private final Integer NULL = 0;

  private SparseArray<SparseIntArray> map;
  private IslandFactory islandFactory;

  public MapFactory(IslandFactory islandFactory) {
    Log.d(GameApplication.TAG, "- INITIALIZING MAP...");
    this.initMap();

    this.islandFactory = islandFactory;
  }

  public Island createMap() {
    return this.islandFactory.createIsland();
  }

  public void initMap() {
    map = new SparseArray<>(HEIGHT);
    for(int i = 0; i < HEIGHT; i++) {
      map.put(i, new SparseIntArray(WIDTH));
      for(int j = 0; j < WIDTH; j++) {
        if(j ==  Xc && i == Yc)
          map.get(i).append(j, LAND);
        else
          map.get(i).append(j, SEA_WATER);
      }
    }
    Log.d(GameApplication.TAG, "- DONE!");
  }
}
