package com.tiago.citymapperproject.core.utils;

import java.util.List;

public class ListUtils {

  public static <T> T getOrNull(List<T> list, int index) {
    T result = null;

    try {
      result = list.get(index);
    } catch (IndexOutOfBoundsException ex) {
      result = null;
    }

    return result;
  }
}
