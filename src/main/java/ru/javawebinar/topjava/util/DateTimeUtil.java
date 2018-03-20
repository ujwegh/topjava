package ru.javawebinar.topjava.util;

import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
  private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

  public static boolean isBetween(LocalTime lt, LocalTime startTime, LocalTime endTime) {
    return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) <= 0;
  }

  public static String toString(LocalDateTime ldt) {
    return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
  }

  public static LocalTime parseLocalTime(String str) {
    return StringUtils.isEmpty(str) ? null : LocalTime.parse(str);
  }

  public static LocalDate parseLocalDate(String str) {
    return StringUtils.isEmpty(str) ? null : LocalDate.parse(str);
  }
}
