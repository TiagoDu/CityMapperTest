package com.tiago.citymapperproject.features.lineroute.model;

import java.util.List;

public class LineSequenceResponse {

  private String id;
  private String name;
  private List<LineStopStation> stations;
  private List<LineStopPointSequence> stopPointSequences;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<LineStopStation> getStations() {
    return stations;
  }

  public List<LineStopPointSequence> getStopPointSequences() {
    return stopPointSequences;
  }
}
