package com.games.rasta.randomadventure.models;

import android.util.Log;
import android.util.SparseArray;

import com.games.rasta.randomadventure.GameApplication;

import java.util.ArrayList;
import java.util.List;

public class Map {

  protected SparseArray<SparseArray<Tile>> map;
  protected int radius;

  public Map(int radius) {
    this.radius = radius;
    this.init();
  }

  private void init() {
    this.map = new SparseArray<>();
    for(int i = 0; i < radius; i++) {
      map.put(i, new SparseArray<Tile>());
      for(int j = 0; j < radius; j++) {
        map.get(i).put(i, null);
      }
    }
  }

  public void put(Tile tile) {
    map.get(tile.getCoords().getY()).put(tile.getCoords().getX(), tile);
  }

  public Tile get(int x, int y) {
    return map.get(y).get(x);
  }

  public Tile get(Coords coords) {
    return map.get(coords.getY()).get(coords.getX());
  }

  public int size() {
    return map.size();
  }

  public List<Tile> getSurroundingTiles(Tile center) {
    return this.getSurroundingTiles(center.getCoords());
  }

  public List<Tile> getSurroundingTiles(Coords coords) {
    List<Tile> surrounding = new ArrayList<>();
    for(int m = -1; m < 2; m++) {
      for(int n = -1; n < 2; n++) {
        if((m != 0 && n != 0) || (m == 0 && n == 0)) continue;
        int x = coords.getX() + m;
        int y = coords.getY() + n;
        if(this.inBounds(x, y)) {
          surrounding.add(this.get(x, y));
        }
      }
    }
    return surrounding;
  }

  public List<Tile> getSurroundingTiles(Tile center, int type) {
    List<Tile> surrounding = new ArrayList<>();
    for(int m = -1; m < 2; m++) {
      for(int n = -1; n < 2; n++) {
        if((m != 0 && n != 0) || (m == 0 && n == 0)) continue;
        int x = center.getCoords().getX() + m;
        int y = center.getCoords().getY() + n;
        if(this.inBounds(x, y)) {
          Tile tile = this.get(x, y);
//          Log.d(GameApplication.TAG, "type: " + tile.getType());
          if(tile.getType() == type) surrounding.add(tile);
        }
      }
    }
    return surrounding;
  }

  public List<Tile> getAllSurroundingTiles(Tile center) {
    return this.getAllSurroundingTiles(center.getCoords());
  }

  public List<Tile> getAllSurroundingTiles(Coords center) {
    List<Tile> surrounding = new ArrayList<>();
    for(int m = -1; m < 2; m++) {
      for(int n = -1; n < 2; n++) {
        if(m == 0 && n == 0) continue;
        int x = center.getX() + m;
        int y = center.getY() + n;
        if(this.inBounds(x, y)) {
          surrounding.add(this.get(x, y));
        }
      }
    }
    return surrounding;
  }

  public List<Tile> getAllSurroundingTiles(Tile center, int type) {
    List<Tile> surrounding = new ArrayList<>();
    for(int m = -1; m < 2; m++) {
      for(int n = -1; n < 2; n++) {
        if(m == 0 && n == 0) continue;
        int x = center.getCoords().getX() + m;
        int y = center.getCoords().getY() + n;
        if(this.inBounds(x, y)) {
          Tile tile = this.get(x, y);
//          Log.d(GameApplication.TAG, "type: " + tile.getType());
          if(tile.getType() == type) surrounding.add(tile);
        }
      }
    }
    return surrounding;
  }

  public boolean inBounds(int x, int y) {
    return x >= 0 && x < radius && y >= 0 && y < radius;
  }

  public boolean inBounds(Coords coords) {
    return this.inBounds(coords.getX(), coords.getY());
  }


  public boolean inAreaBounds(Tile t) {
    return t.getCoords().getX() >= 1 && t.getCoords().getX() < radius - 1 && t.getCoords().getY() >= 1 && t.getCoords().getY() < radius - 1;
  }

  public void print() {
    String header = "   ";
    for(int i = 0; i < radius; i++) {
      header += i % 10 + " ";
    }
    Log.d(GameApplication.TAG, header);

    for(int i = 0; i < radius; i++) {
      String line = "\n" + i % 10 + ": ";
      for(int j = 0; j < radius; j++) {
        line += this.get(j, i).getType() + " ";
      }
      Log.d(GameApplication.TAG, line);
    }
  }

  public List<List<Tile>> getArea(int x0, int y0, int radius) {
    int offsetX = x0 - (radius - 1)/2;
    int offsetY = y0 - (radius - 1)/2;

    List<List<Tile>> area = new ArrayList<>();

    for(int y = 0; y < radius; y++) {
      area.add(new ArrayList<Tile>());
      for(int x = 0; x < radius; x++) {
        int x1 = x + offsetX;
        int y1 = y + offsetY;
        if(inBounds(x1, y1))
          area.get(y).add(this.get(x1, y1));
        else
          area.get(y).add(new Tile(new Coords(x, y), Tile.NULL));
      }
    }

    return area;
  }

}
