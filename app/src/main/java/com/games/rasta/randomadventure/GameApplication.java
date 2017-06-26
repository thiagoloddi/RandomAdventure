package com.games.rasta.randomadventure;

import android.app.Application;
import android.util.Log;

import com.games.rasta.randomadventure.engine.map.DungeonFactory;
import com.games.rasta.randomadventure.engine.map.IslandFactory;
import com.games.rasta.randomadventure.engine.map.LandFactory;
import com.games.rasta.randomadventure.engine.map.MapFactory;
import com.games.rasta.randomadventure.engine.map.WaterFactory;

public class GameApplication extends Application {
  public final static String TAG = "MACONHA";
  private MapFactory mapFactory;
  private DungeonFactory dungeonFactory;

  @Override
  public void onCreate() {
    super.onCreate();

    Log.d(TAG, "STARTING APPLICATION");

    LandFactory landFactory = new LandFactory();
    WaterFactory waterFactory = new WaterFactory();
    IslandFactory islandFactory = new IslandFactory(landFactory, waterFactory);

    this.mapFactory = new MapFactory(islandFactory);
    this.dungeonFactory = new DungeonFactory();
  }

  public MapFactory getMapFactory() {
    return mapFactory;
  }

  public DungeonFactory getDungeonFactory() {
    return dungeonFactory;
  }
}