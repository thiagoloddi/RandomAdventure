package com.games.rasta.randomadventure.presentation.activities;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.LinearLayout;

import com.games.rasta.randomadventure.GameApplication;
import com.games.rasta.randomadventure.R;
import com.games.rasta.randomadventure.models.Coords;
import com.games.rasta.randomadventure.models.Island;
import com.games.rasta.randomadventure.models.Tile;
import com.games.rasta.randomadventure.presentation.adapters.MapAdapter;
import com.games.rasta.randomadventure.presentation.fragments.MapFragment;
import com.games.rasta.randomadventure.presentation.fragments.PadFragment;
import com.games.rasta.randomadventure.presentation.fragments.StatusBarFragment;

public class GameActivity extends BaseActivity {

  private int windowWidth;
  private int windowHeight;
  private int mapWidth;
  private int zoom;
  private Island map;
  private MapFragment mapFragment;
  private PadFragment padFragment;
  private LinearLayout mapContainer;
  private Coords playerPosition;
  private Coords playerMapPostion;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_game);

    this.map = getGameApplication().getMapFactory().createMap();
    this.zoom = 0;
    this.playerPosition = map.getInitialPosition();
    map.get(playerPosition).setExplored(true);
    map.setExplored(playerPosition);

    DisplayMetrics windowSize = new DisplayMetrics();
    getWindowManager().getDefaultDisplay().getMetrics(windowSize);
    this.windowWidth = windowSize.widthPixels;
    this.windowHeight = windowSize.heightPixels;
    createView();

    this.mapFragment = MapFragment.newInstance();
    getSupportFragmentManager().beginTransaction().add(R.id.map_fragment_container, mapFragment, MapFragment.NAME).commit();

    this.padFragment = PadFragment.newInstance();
    getSupportFragmentManager().beginTransaction().add(R.id.pad_fragment_container, padFragment, PadFragment.NAME).commit();

    StatusBarFragment statusBarFragment = StatusBarFragment.newInstance();
    getSupportFragmentManager().beginTransaction().add(R.id.status_bar_fragment_container, statusBarFragment, StatusBarFragment.NAME).commit();
  }

  private void createView() {
    this.mapContainer = (LinearLayout) findViewById(R.id.map_fragment_container);

    this.mapWidth = (int) (Island.MAX_WIDTH * (Math.floor(windowWidth/Island.MAX_WIDTH)));
    int margin = (windowWidth - mapWidth) / 2;
    LinearLayout.LayoutParams lp = ((LinearLayout.LayoutParams) mapContainer.getLayoutParams());
    lp.setMargins(margin, margin, margin, margin);
    lp.width = mapWidth;
    lp.height = mapWidth;
    mapContainer.setLayoutParams(lp);

    Log.d(GameApplication.TAG, "windowHeight: " + getWindowHeight());
    Log.d(GameApplication.TAG, "mapWidth: " + mapWidth);

    int padHeight = getWindowHeight() - mapWidth - 30;
    Log.d(GameApplication.TAG, "padHeight: " + padHeight);
    LinearLayout padContainer = (LinearLayout) findViewById(R.id.pad_fragment_container);
    LinearLayout.LayoutParams lp2 = (LinearLayout.LayoutParams) padContainer.getLayoutParams();
    lp2.height = mapWidth / 2;
    lp2.width = mapWidth / 2;
    padContainer.setLayoutParams(lp2);
  }

  public Island getMap() {
    return map;
  }

  public int getZoom() {
    return zoom;
  }

  public void setZoom(int zoom) {
    this.zoom = Math.min(Island.MAX_ZOOM, Math.max(0, zoom));
  }

  public int getWindowWidth() {
    return windowWidth;
  }

  public int getWindowHeight() { return windowHeight; }

  public int getMapWidth() {
    return mapWidth;
  }

  public Coords getPlayerPosition() {
    return playerPosition;
  }

  public void setPlayerPosition(Coords playerPosition) {
    Tile tile = map.get(playerPosition);
    if(map.inBounds(tile.getCoords()) && tile.getType() != Tile.CLEAR_WATER && tile.getType() != Tile.SEA_WATER) {
      this.playerPosition = playerPosition;
      map.setExplored(playerPosition);
      ((MapAdapter) mapFragment.mapContainer.getAdapter()).notifyDataSetChanged();
    }
  }

  public MapFragment getMapFragment() {
    return mapFragment;
  }
}