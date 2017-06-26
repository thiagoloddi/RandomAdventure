package com.games.rasta.randomadventure.presentation.adapters;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.games.rasta.randomadventure.GameApplication;
import com.games.rasta.randomadventure.R;
import com.games.rasta.randomadventure.models.Coords;
import com.games.rasta.randomadventure.models.Map;
import com.games.rasta.randomadventure.models.Tile;
import com.games.rasta.randomadventure.presentation.activities.GameActivity;

import java.util.ArrayList;
import java.util.List;

public class MapAdapter extends BaseAdapter {

  public static final int ISLAND = 1;
  public static final int DUNGEON = 2;
  public static final int NUM_COLUMNS = 15;


  private Map map;
  private int type;
  private GameActivity activity;

  public MapAdapter(Map map, int type, GameActivity activity) {
    this.map = map;
    this.type = type;
    this.activity = activity;
  }

  @Override
  public int getCount() {
    return NUM_COLUMNS*NUM_COLUMNS;
  }

  @Override
  public Object getItem(int position) {
    return null;
  }

  @Override
  public long getItemId(int position) {
    return 0;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {

    switch(type) {
      case ISLAND: return getIslandView(convertView, position);
      case DUNGEON: return getDungeonView(convertView, position);
    }

    return convertView;
  }

  private View getDungeonView(View convertView, int position) {
    return convertView;
  }

  private View getIslandView(View convertView, int position) {
    Coords playerPosition = activity.getPlayerPosition();
    int mapSize = activity.getMap().size();

    Tile tile = activity.getMap().get(getTileCoords(position, playerPosition, mapSize));
//      Log.d(GameApplication.TAG, "x: " + x);
//      Log.d(GameApplication.TAG, "y: " + y);

    if(convertView == null) {
      convertView = createViewLayout();
    }

    if(tile.isExplored())
      convertView.setBackground(activity.getResources().getDrawable(getTileResource(tile.getType()), null));
    else
      convertView.setBackground(activity.getResources().getDrawable(getTileResource(R.drawable.tile_null), null));

    ImageView riverImg = (ImageView) ((RelativeLayout) convertView).getChildAt(0);
    Drawable riverDrawable = null;

    if(tile.hasRiver() && tile.isExplored()) {
      int resourceId = tile.getRiverResourceId();
      if(resourceId == 0) {
        resourceId = getRiverPath(tile);
        tile.setRiverResourceId(resourceId);
      }
      riverDrawable = activity.getResources().getDrawable(resourceId, null);
    }
    riverImg.setImageDrawable(riverDrawable);
//      Log.d(GameApplication.TAG, "getView: " + position);

    ImageView playerImg = (ImageView) ((RelativeLayout) convertView).getChildAt(1);
    updatePlayerImage(playerImg, tile, playerPosition);

    return convertView;
  }

  private RelativeLayout createViewLayout() {
    RelativeLayout convertView = new RelativeLayout(activity);
    LinearLayoutCompat.LayoutParams lp = new LinearLayoutCompat.LayoutParams(activity.getMapWidth()/NUM_COLUMNS, activity.getMapWidth()/NUM_COLUMNS);
    convertView.setLayoutParams(lp);

    ImageView img1 = new ImageView(activity);
    ImageView img2 = new ImageView(activity);
    convertView.addView(img1);
    convertView.addView(img2);

    return convertView;
  }

  private void updatePlayerImage(ImageView playerImg, Tile tile, Coords playerPosition) {
    Drawable playerDrawable = null;

    if(tile.getCoords().equals(playerPosition)) {
      playerDrawable = activity.getResources().getDrawable(R.drawable.shield, null);
    }
    playerImg.setImageDrawable(playerDrawable);

  }

  private int getTileResource(int type) {
    switch(type) {
      case Tile.SEA_WATER: return R.drawable.tile_sea_water;
      case Tile.LAND: return R.drawable.tile_grass;
      case Tile.CLEAR_WATER: return R.drawable.tile_clear_water;
      case Tile.BEACH: return R.drawable.tile_beach;
      case Tile.FOREST: return R.drawable.tile_forest;
      case Tile.HILL: return R.drawable.tile_hill;
      default: return R.drawable.tile_null;
    }
  }

  private int getRiverPath(Tile tile) {
    Log.d(GameApplication.TAG, "start: " + tile.getRiverStart());
    Log.d(GameApplication.TAG, "end: " + tile.getRiverEnd());
    List<Integer> riverPaths = new ArrayList<>();
    if(tile.getRiverStart().equals(Tile.BOTTOM) && tile.getRiverEnd().equals(Tile.RIGHT) || tile.getRiverStart().equals(Tile.RIGHT) && tile.getRiverEnd().equals(Tile.BOTTOM)) {
      riverPaths.add(R.drawable.river_br1);
      riverPaths.add(R.drawable.river_br2);
      riverPaths.add(R.drawable.river_br3);
      return riverPaths.get((int) Math.floor(riverPaths.size() * Math.random()));
    }
    if(tile.getRiverStart().equals(Tile.BOTTOM) && tile.getRiverEnd().equals(Tile.LEFT) || tile.getRiverStart().equals(Tile.LEFT) && tile.getRiverEnd().equals(Tile.BOTTOM)) {
      riverPaths.add(R.drawable.river_bl1);
      riverPaths.add(R.drawable.river_bl2);
      riverPaths.add(R.drawable.river_bl3);
      return riverPaths.get((int) Math.floor(riverPaths.size() * Math.random()));
    }
    if(tile.getRiverStart().equals(Tile.RIGHT) && tile.getRiverEnd().equals(Tile.TOP) || tile.getRiverStart().equals(Tile.TOP) && tile.getRiverEnd().equals(Tile.RIGHT)) {
      riverPaths.add(R.drawable.river_tr1);
      riverPaths.add(R.drawable.river_tr2);
      riverPaths.add(R.drawable.river_tr3);
      return riverPaths.get((int) Math.floor(riverPaths.size() * Math.random()));
    }
    if(tile.getRiverStart().equals(Tile.TOP) && tile.getRiverEnd().equals(Tile.LEFT) || tile.getRiverStart().equals(Tile.LEFT) && tile.getRiverEnd().equals(Tile.TOP)) {
      riverPaths.add(R.drawable.river_tl1);
      riverPaths.add(R.drawable.river_tl2);
      riverPaths.add(R.drawable.river_tl3);
      return riverPaths.get((int) Math.floor(riverPaths.size() * Math.random()));
    }

    if(tile.getRiverStart().equals(Tile.RIGHT) && tile.getRiverEnd().equals(Tile.LEFT) || tile.getRiverStart().equals(Tile.LEFT) && tile.getRiverEnd().equals(Tile.RIGHT)) {
      riverPaths.add(R.drawable.river_lr1);
      riverPaths.add(R.drawable.river_lr2);
      riverPaths.add(R.drawable.river_lr3);
      return riverPaths.get((int) Math.floor(riverPaths.size() * Math.random()));
    }
    if(tile.getRiverStart().equals(Tile.BOTTOM) && tile.getRiverEnd().equals(Tile.TOP) || tile.getRiverStart().equals(Tile.TOP) && tile.getRiverEnd().equals(Tile.BOTTOM)) {
      riverPaths.add(R.drawable.river_tb1);
      riverPaths.add(R.drawable.river_tb2);
      riverPaths.add(R.drawable.river_tb3);
      return riverPaths.get((int) Math.floor(riverPaths.size() * Math.random()));
    }


    if(tile.getRiverStart().equals(Tile.RIGHT) && tile.getRiverEnd().equals(Tile.CENTER)) {
      return R.drawable.river_cr1;
    }
    if(tile.getRiverStart().equals(Tile.LEFT) && tile.getRiverEnd().equals(Tile.CENTER)) {
      return R.drawable.river_cl1;
    }
    if(tile.getRiverStart().equals(Tile.TOP) && tile.getRiverEnd().equals(Tile.CENTER)) {
      return R.drawable.river_ct1;
    }
    if(tile.getRiverStart().equals(Tile.BOTTOM) && tile.getRiverEnd().equals(Tile.CENTER)) {
      return R.drawable.river_cb1;
    }

    return Integer.parseInt(null);
  }

  private Coords getTileCoords(int position, Coords playerPosition, int mapSize) {
    int x = position % NUM_COLUMNS;
    int y = position / NUM_COLUMNS;
    int radius = (NUM_COLUMNS - 1) / 2;
    if(playerPosition.getX() > radius) x += Math.min(playerPosition.getX(), mapSize - (radius + 1)) - radius;
    if(playerPosition.getY() > radius) y += Math.min(playerPosition.getY(), mapSize - (radius + 1)) - radius;

    return new Coords(x, y);
  }
}