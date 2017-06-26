package com.games.rasta.randomadventure.engine.map;

import com.games.rasta.randomadventure.models.Coords;
import com.games.rasta.randomadventure.models.Dungeon;
import com.games.rasta.randomadventure.models.Tile;

public class DungeonFactory {

  private static final int WIDTH = 15;
  private Dungeon dungeon;

  public void init() {
    this.dungeon = new Dungeon(WIDTH);

    for(int i = 0; i < WIDTH; i++) {
      for(int j = 0; j < WIDTH; j++) {
        dungeon.put(new Tile(new Coords(j, i), Tile.NULL));
      }
    }
  }

  public Dungeon createDungeon(Coords coords) {
    this.init();
    return dungeon;
  }

  public class Cave {
    public void createDungeon() {
      init();
    }

  }
}
