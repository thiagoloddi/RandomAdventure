package com.games.rasta.randomadventure.models;

/**
 * Created by thiago on 22/06/17.
 */

public class Tile {

  public static final int NULL = 0;

  private final Coords coords;
  private final int type;
  private boolean isCoastal = false;
  private boolean riverLeft = false;
  private boolean riverRight = false;
  private boolean riverUp = false;
  private boolean riverDown = false;


  public Tile(Coords coords, int type) {
    this.coords = coords;
    this.type = type;
  }

  public Coords getCoords() {
    return coords;
  }

  public int getType() {
    return type;
  }

  public boolean isCoastal() {
    return isCoastal;
  }

  public void setCoastal(boolean coastal) {
    isCoastal = coastal;
  }

  public boolean isRiverLeft() {
    return riverLeft;
  }

  public void setRiverLeft(boolean riverLeft) {
    this.riverLeft = riverLeft;
  }

  public boolean isRiverRight() {
    return riverRight;
  }

  public void setRiverRight(boolean riverRight) {
    this.riverRight = riverRight;
  }

  public boolean isRiverUp() {
    return riverUp;
  }

  public void setRiverTop(boolean riverUp) {
    this.riverUp = riverUp;
  }

  public boolean isRiverDown() {
    return riverDown;
  }

  public void setRiverBottom(boolean riverDown) {
    this.riverDown = riverDown;
  }
}
