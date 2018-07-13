package com.tiago.citymapperproject.features.stoppoints.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity public class StopPoint {

  @PrimaryKey @NonNull private String id;
  private String naptanId;
  private String commonName;
  private Double distance = 0.0D;
  private String coordinatesHash;

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

  public String getCommonName() {
    return commonName;
  }

  public void setCommonName(String commonName) {
    this.commonName = commonName;
  }

  public Double getDistance() {
    return distance;
  }

  public void setDistance(Double distance) {
    this.distance = distance;
  }

  public void setCoordinatesHash(String hash) {
    this.coordinatesHash = hash;
  }

  public String getCoordinatesHash() {
    return coordinatesHash;
  }
}
