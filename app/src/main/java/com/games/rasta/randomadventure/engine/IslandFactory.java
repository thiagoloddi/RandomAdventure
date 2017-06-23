package com.games.rasta.randomadventure.engine;

import android.util.Log;

import com.games.rasta.randomadventure.GameApplication;
import com.games.rasta.randomadventure.models.Coords;
import com.games.rasta.randomadventure.models.Island;
import com.games.rasta.randomadventure.models.Tile;

import java.util.ArrayList;
import java.util.List;

import static com.games.rasta.randomadventure.engine.MapFactory.BEACH;
import static com.games.rasta.randomadventure.engine.MapFactory.CLEAR_WATER;
import static com.games.rasta.randomadventure.engine.MapFactory.FOREST;
import static com.games.rasta.randomadventure.engine.MapFactory.HILL;
import static com.games.rasta.randomadventure.engine.MapFactory.LAND;
import static com.games.rasta.randomadventure.engine.MapFactory.SEA_WATER;

public class IslandFactory {

  private final Integer MAX_AREA = 250;
  private final Integer MAX_RADIUS = 25;
  private final int MAX_RIVER_LENGTH = 10;
  private final Double ALPHA = 0.98;

  private Island island;

  IslandFactory(MapFactory map) {

  }

  private Island initIsland() {
    Island island = new Island(MAX_RADIUS);
    for(int i = 0; i < island.size(); i++) {
      for(int j = 0; j < island.size(); j++) {
        island.put(new Tile(new Coords(j, i), CLEAR_WATER));
      }
    }
    return island;
  }

  public Island createIsland() {
    Log.d(GameApplication.TAG, "-- CREATING ISLAND");
    this.island = this.initIsland();
    Integer area = 9;
    int maxArea = (int) Math.floor(MAX_AREA*(0.7 + 0.6*Math.random()));
    List<Coords> newLands = new ArrayList<>();
    Log.d(GameApplication.TAG, "-- maxArea: " + maxArea);

    int x0 = (MAX_RADIUS - 1) / 2;
    int y0 = x0;
    island.put(new Tile(new Coords(x0, y0), LAND));
    island.put(new Tile(new Coords(x0, y0 + 1), LAND));
    island.put(new Tile(new Coords(x0, y0 - 1), LAND));
    island.put(new Tile(new Coords(x0 + 1, y0), LAND));
    island.put(new Tile(new Coords(x0 - 1, y0), LAND));
    island.put(new Tile(new Coords(x0 + 1, y0 + 1), LAND));
    island.put(new Tile(new Coords(x0 + 1, y0 - 1), LAND));
    island.put(new Tile(new Coords(x0 - 1, y0 + 1), LAND));
    island.put(new Tile(new Coords(x0 - 1, y0 - 1), LAND));

    while(area < maxArea) {
      Log.d(GameApplication.TAG, "--- SPAWN LAND - area: " + area);

      for(int i = 0; i < MAX_RADIUS; i++) {
        for(int j = 0; j < MAX_RADIUS; j++) {
          if(island.get(j, i).getType() == LAND) {
//            Log.d(GameApplication.TAG, "land: (" + j + ", " + i + ")");
            newLands.addAll(createLand(island, j, i));
          }
        }
      }

      List<Coords> uniq = new ArrayList<>();
      for(Coords coord: newLands) {
        for(int i = 0; i < uniq.size(); i++) {
          Coords u = uniq.get(i);
          if(u.getX() == coord.getX() && u.getY() == coord.getY()) continue;

          if(i == uniq.size() - 1) {
            uniq.add(coord);
          }
        }
        if(uniq.size() == 0) uniq.add(coord);
      }

      Utils.fill(uniq, island);
      area += uniq.size();
//      Log.d(GameApplication.TAG, "-- area: " + Integer.toString(area));
//      Log.d(GameApplication.TAG, "-- uniq: " + uniq.size());

      newLands.clear();
    }

    this.spawnLake();
    this.spawnBeach();
    this.spawnForest();
    this.spawnHill();
    island.updateTilesAttributes();
//    this.spawnRiver();
    return island;
  }

  private List<Coords> createLand(Island island, int x0, int y0) {
//    Log.d(GameApplication.TAG, "--- SPAWN LAND - " + new Coords(x0, y0).print());
    List<Coords> coords = new ArrayList<>();

    for(int m = -1; m < 2; m++) {
      for(int n = -1; n < 2; n++) {
        if(n != 0 && m != 0) continue;

        int x1 = x0 + m;
        int y1 = y0 + n;
        Double random = Math.random();
        if((x1 > 0 && y1 > 0) && (x1 < MAX_RADIUS -1 && y1 < MAX_RADIUS - 1) && island.get(x1, y1).getType() == CLEAR_WATER && random > ALPHA)
          coords.add(new Coords(x1, y1));
      }
    }
    return coords;
  }

  private  void spawnForest() {
    Log.d(GameApplication.TAG, "--- SPAWN FOREST");

    for(int i = 0; i < island.size(); i++) {
      for(int j = 0; j < island.size(); j++) {
        int tile = island.get(j, i).getType();
        if(tile == LAND && Math.random() > 0.5) island.put(new Tile(new Coords(j, i), FOREST));
      }
    }
  }

  private  void spawnHill() {
    Log.d(GameApplication.TAG, "--- SPAWN HILL");

    for(int i = 0; i < island.size(); i++) {
      for(int j = 0; j < island.size(); j++) {
        int tile = island.get(j, i).getType();
        if((tile == LAND || tile == FOREST) && Math.random() > 0.8) island.put(new Tile(new Coords(j, i), HILL));
      }
    }
  }

  private void spawnBeach() {
    Log.d(GameApplication.TAG, "--- SPAWN BEACH");

    for(int i = 0; i < island.size(); i++) {
      for(int j = 0; j < island.size(); j++) {
        int tile = island.get(j, i).getType();
        if(tile == LAND) {
          int seaCount = 0;
          for(int m = -1; m < 2; m++) {
            for(int n = -1; n < 2; n++) {
              int x = j + n;
              int y = i + m;
              if(x >= 0 && y >= 0 && x < MAX_RADIUS && y < MAX_RADIUS) {
                if(island.get(x, y).getType() == SEA_WATER) seaCount++;
              }
            }
          }

          if(seaCount > 2) island.put(new Tile(new Coords(j, i), BEACH));

        }
      }
    }
  }

  private void spawnLake() {
    Log.d(GameApplication.TAG, "--- SPAWN LAKE");
    island.put(new Tile(new Coords(0, 0), SEA_WATER));
    int count = 1;
    while(count > 0) {
//      Log.d(GameApplication.TAG, "count: " + count);
      count = 0;

      for(int i = 0; i < island.size(); i++) {
        for(int j = 0; j < island.size(); j++) {
          int tile = island.get(j, i).getType();
          if(tile == SEA_WATER) {

            for(int m = -1; m < 2; m++) {
              for(int n = -1; n < 2; n++) {
                if(n != 0 && m != 0) continue;

                int x1 = j + m;
                int y1 = i + n;
                if(island.inBounds(x1, y1) && island.get(x1, y1).getType() != LAND && island.get(x1, y1).getType() != SEA_WATER) {
                  count++;
                  island.put(new Tile(new Coords(x1, y1), SEA_WATER));
                }

              }
            }

          }
        }
      }
    }
  }

  private void spawnRiver() {
    List<Tile> coastalTiles = island.getCoastalTiles();
    int totalRivers = (int) Math.ceil(2 + Math.random() * 1.5);
    int spawnedRivers = 0;
    List<Tile> landTiles = new ArrayList<>();
    while(spawnedRivers < totalRivers) {
      int index = (int) Math.ceil(Math.random() * coastalTiles.size());
      Tile tile = coastalTiles.get(index);

      boolean riverDone = false;
      List<Tile> riverTiles = new ArrayList<>();
      Tile riverTile = tile;
      while(!riverDone) {
        for(int m = -1; m < 2; m++) {
          for(int n = -1; n < 2; n++) {
            int x = riverTile.getCoords().getX() + m;
            int y = riverTile.getCoords().getY() + n;

            if(island.inBounds(x, y) && !island.get(x, y).isCoastal()) landTiles.add(island.get(x, y));
          }
        }

        if(landTiles.size() == 0)
          riverDone = true;

        riverTile = landTiles.get((int) Math.ceil(landTiles.size() * Math.random()));
        riverTiles.add(riverTile);

        if(Math.random() < riverTiles.size() / MAX_RIVER_LENGTH || (riverTiles.size() > 2 && riverTile.getType() == HILL)) {
          riverDone = true;
          spawnedRivers++;

          this.drawRiver(riverTiles);

        }
      }
    }
  }

  public void drawRiver(List<Tile> riverTiles) {
    for(int i = 0; i < riverTiles.size(); i++) {
      Tile tile = riverTiles.get(i);
      if(i == 0) {
        int x = tile.getCoords().getX();
        int y = tile.getCoords().getY();
        if(island.get(x - 1, y).getType() == SEA_WATER) {
          island.get(x, y).setRiverLeft(true);
        }
        else if(island.get(x + 1, y).getType() == SEA_WATER) {
          island.get(x, y).setRiverRight(true);
        }
        else if(island.get(x, y - 1).getType() == SEA_WATER) {
          island.get(x, y).setRiverTop(true);
        }
        else if(island.get(x, y + 1).getType() == SEA_WATER) {
          island.get(x, y).setRiverBottom(true);
        }
      } else {

      }
    }
  }

  private static class Utils {
    private static void fill(List<Coords> lands, Island island) {
//    Log.d(GameApplication.TAG, "-- FILLING ISLAND");
      for(Coords land: lands) {
        island.put(new Tile(land, LAND));
      }
    }
  }



}
