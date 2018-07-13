package com.tiago.citymapperproject.features.arrivaltimes.model;

public class ArrivalTime {

  private String id;
  private String naptanId;
  private String lineId;
  private String lineName;
  private int timeToStation;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getNaptanId() {
    return naptanId;
  }

  public void setNaptanId(String naptanId) {
    this.naptanId = naptanId;
  }

  public String getLineId() {
    return lineId;
  }

  public void setLineId(String lineId) {
    this.lineId = lineId;
  }

  public String getLineName() {
    return lineName;
  }

  public void setLineName(String lineName) {
    this.lineName = lineName;
  }

  public int getTimeToStation() {
    return timeToStation;
  }

  public int getTimeToStationInMin() {
    return timeToStation / 60;
  }

  public void setTimeToStation(int timeToStation) {
    this.timeToStation = timeToStation;
  }
}
