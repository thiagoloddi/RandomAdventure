package com.games.rasta.randomadventure.engine.map;

import android.util.Log;

import com.games.rasta.randomadventure.GameApplication;
import com.games.rasta.randomadventure.models.Coords;
import com.games.rasta.randomadventure.models.Island;
import com.games.rasta.randomadventure.models.Tile;

import java.util.ArrayList;
import java.util.List;

public class WaterFactory {

  private final int MAX_RIVER_LENGTH = 15;

  private Island island;

  public void init(Island island) {
    this.island = island;
  }

  public void spawnLakes() {
    for(int i = 0; i < island.size(); i++) {
      for(int j = 0; j < island.size(); j++) {
        Tile tile = island.get(j, i);
        if(tile.getType() == Tile.NULL)
          island.get(tile.getCoords()).setType(Tile.CLEAR_WATER);
      }
    }
  }

  public void spawnSea() {
    Log.d(GameApplication.TAG, "--- SPAWN LAKE");
    island.put(new Tile(new Coords(0, 0), Tile.SEA_WATER));
    int count = 1;
    while(count > 0) {
//      Log.d(GameApplication.TAG, "count: " + count);
      count = 0;
      for(int i = 0; i < island.size(); i++) {
        for(int j = 0; j < island.size(); j++) {
          Tile tile = island.get(j, i);
          if(tile.getType() == Tile.SEA_WATER) {
            List<Tile> surroundings = island.getSurroundingTiles(tile);
            for(Tile t: surroundings) {
              if(t.getType() != Tile.LAND && t.getType() != Tile.SEA_WATER) {
                count++;
                island.get(t.getCoords()).setType((Tile.SEA_WATER));
              }
            }
          }
        }
      }
    }

    island.updateCoastalTiles();
    island.updateLakeTiles();
  }

  public void spawnRiver() {
    List<Tile> coastalTiles = island.getCoastalTiles();
    Log.d(GameApplication.TAG, "coastalTiles: " + coastalTiles.size());

    int totalRivers = (int) Math.floor(2 + Math.random() * 3);
    int spawnedRivers = 0;

    RiverFactory riverFactory = new RiverFactory();

    while(spawnedRivers < totalRivers) {
      List<Tile> riverTiles = riverFactory.newRiver(coastalTiles);

      if (riverTiles != null) {
        riverFactory.updateRiverTiles(riverTiles);
        spawnedRivers++;

        Log.d(GameApplication.TAG, "-- RIVER " + spawnedRivers + " (" + riverTiles.size() + ")");

        for(Tile t: riverTiles) {
          Log.d(GameApplication.TAG, t.getCoords().print());
          Log.d(GameApplication.TAG, t.getRiverStart() + " -> " + t.getRiverEnd());
        }
      }


//      for(Tile t: riverTiles) {
//      }

    }
  }

  private class RiverFactory {

    private List<Tile> newRiver(List<Tile> coastalTiles) {
      List<Tile> riverTiles = new ArrayList<>();
      Tile riverTile = coastalTiles.get((int) Math.floor(Math.random() * coastalTiles.size()));
      riverTiles.add(riverTile);
      while (true) {
        List<Tile> availableTiles = getAvailableTiles(riverTile, riverTiles);

        if (availableTiles.size() == 0)
          return null;
        else {
          riverTile = availableTiles.get((int) Math.floor(availableTiles.size() * Math.random()));
          riverTiles.add(riverTile);

          if (riverDone(riverTiles, riverTile)) {
            return riverTiles;
          }
        }
//        Log.d(GameApplication.TAG, "size: " + availableTiles.size());
//        Log.d(GameApplication.TAG, "random: " + random);
//        Log.d(GameApplication.TAG, "%: " + ((double) riverTiles.size() / MAX_RIVER_LENGTH));
      }
    }

    private boolean riverDone(List<Tile> riverTiles, Tile riverTile) {
      return (
          riverTiles.size() > 5 &&
          (
              Math.random() < (double) riverTiles.size() / MAX_RIVER_LENGTH ||
              (riverTile.getType() == MapFactory.HILL && riverTiles.size() > 10) ||
              riverTile.getType() == MapFactory.CLEAR_WATER
          )
      );
    }

    private List<Tile> getAvailableTiles(Tile riverTile, List<Tile> riverTiles) {
      List<Tile> availableTiles = new ArrayList<>();
      List<Tile> surroundings = island.getSurroundingTiles(riverTile);

      for (Tile t : surroundings) {
        if (!t.isCoastal() && !t.hasRiver() && t.getType() != Tile.SEA_WATER && t.getType() != Tile.CLEAR_WATER)
          availableTiles.add(t);
      }

      List<Tile> repeatedTiles = new ArrayList<>();
      for(Tile t: availableTiles) {
        for(Tile u: riverTiles) {
          if(t.getCoords().getX() == u.getCoords().getX() && t.getCoords().getY() == u.getCoords().getY())
            repeatedTiles.add(t);
        }
      }

      for(Tile t: repeatedTiles) {
        availableTiles.remove(t);
      }

      return availableTiles;
    }

    private void updateRiverTiles(List<Tile> riverTiles) {
      for (int i = 0; i < riverTiles.size(); i++) {
        Tile prev;
        Tile current = riverTiles.get(i);

        if (i == 0) {
          List<Tile> surrounding = island.getSurroundingTiles(riverTiles.get(0), MapFactory.SEA_WATER);
          prev = surrounding.get((int) Math.floor(surrounding.size() * Math.random()));
        } else {
          prev = riverTiles.get(i - 1);
        }

        current.setHasRiver(true);
        current.setRiverStart(current.getRelativePosition(prev));

        if (i == riverTiles.size() - 1) {
          current.setRiverEnd(Tile.CENTER);
        } else {
          current.setRiverEnd(current.getRelativePosition(riverTiles.get(i + 1)));
        }
      }
    }
  }

}
