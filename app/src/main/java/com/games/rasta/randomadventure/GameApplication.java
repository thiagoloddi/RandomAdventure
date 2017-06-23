package com.games.rasta.randomadventure;

import android.app.Application;
import android.util.Log;

import com.games.rasta.randomadventure.engine.MapFactory;

public class GameApplication extends Application {
  public final static String TAG = "MACONHA";
  private MapFactory mapFactory;

  @Override
  public void onCreate() {
    super.onCreate();

    Log.d(TAG, "STARTING APPLICATION");
    this.mapFactory = new MapFactory();
  }

  public MapFactory getMapFactory() {
    return mapFactory;
  }
}