package com.tiago.citymapperproject.features.lineroute.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity public class LineStopPoint {

  private String id;
  private String parentId;

  public String getId() {
    return id;
  }

  public String getParentId() {
    return parentId;
  }
}
