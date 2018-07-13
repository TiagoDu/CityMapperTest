package com.tiago.citymapperproject.features.lineroute.model;

public class LineStation {

  private String name;
  private float distance;
  private boolean selected;
  private boolean closest;

  public LineStation(String name, boolean selected, float distance) {
    this.name = name;
    this.selected = selected;
    this.distance = distance;
  }

  public String getName() {
    return name;
  }

  public float getDistance() {
    return distance;
  }

  public boolean isSelected() {
    return selected;
  }

  public void setClosest(boolean closest) {
    this.closest = closest;
  }

  public boolean isClosest() {
    return closest;
  }
}
