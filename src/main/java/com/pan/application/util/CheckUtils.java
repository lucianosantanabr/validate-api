package com.pan.application.util;

import java.util.Collection;
import java.util.Map;

public class CheckUtils {

  private CheckUtils() {
    throw new IllegalStateException("Utility class");
  }

  public static boolean isNull(Object obj) {
    return obj == null;
  }

  public static boolean notNull(Object obj) {
    return obj != null;
  }

  public static boolean empty(String str) {
    return isNull(str) || str.isBlank();
  }

  public static boolean notEmpty(String str) {
    return notNull(str) && !str.isBlank();
  }

  public static boolean empty(Object[] array) {
    return isNull(array) || array.length == 0;
  }

  public static boolean notEmpty(Object[] array) {
    return notNull(array) && array.length > 0;
  }

  public static boolean empty(Collection<?> coll) {
    return isNull(coll) || coll.isEmpty();
  }

  public static boolean notEmpty(Collection<?> coll) {
    return notNull(coll) && !coll.isEmpty();
  }

  public static boolean empty(Map<?, ?> map) {
    return isNull(map) || map.isEmpty();
  }

  public static boolean notEmpty(Map<?, ?> map) {
    return notNull(map) && !map.isEmpty();
  }

  public static boolean positive(Long value) {
    return notNull(value) && (value > 0);
  }

  public static boolean negative(Long value) {
    return notNull(value) && (value < 0);
  }

  public static boolean positive(Double value) {
    return notNull(value) && (value > 0);
  }

  public static boolean negative(Double value) {
    return notNull(value) && (value < 0);
  }
}
