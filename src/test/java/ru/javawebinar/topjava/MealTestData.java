package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
  public static final int MEAL_ID = START_SEQ + 2;
  public static final int ADMINMEAL_ID = START_SEQ + 8;


  public static final Meal testMeal1 = new Meal(MEAL_ID, LocalDateTime.of(2015, Month.MAY,
    30, 10, 0), "Test-Завтрак", 500);
  public static final Meal testMeal2 = new Meal(MEAL_ID + 1, LocalDateTime.of(2015, Month.MAY, 30, 13, 0),
    "Обед", 1000);
  public static final Meal testMeal3 = new Meal(MEAL_ID + 2, LocalDateTime.of(2015, Month.MAY, 30, 20, 0),
    "Ужин", 500);
  public static final Meal testMeal4 = new Meal(MEAL_ID + 3, LocalDateTime.of(2015, Month.MAY, 31, 10, 0),
    "Завтрак", 1000);
  public static final Meal testMeal5 = new Meal(MEAL_ID + 4, LocalDateTime.of(2015, Month.MAY, 31, 13, 0),
    "Обед", 500);
  public static final Meal testMeal6 = new Meal(MEAL_ID + 5, LocalDateTime.of(2015, Month.MAY, 31, 20, 0),
    "Ужин", 510);
  public static final Meal testAdminMeal1 = new Meal(ADMINMEAL_ID, LocalDateTime.of(2015, Month.MAY, 31, 13, 0),
    "Обед", 500);
  public static final Meal testAdminMeal2 = new Meal(ADMINMEAL_ID + 1, LocalDateTime.of(2015, Month.MAY, 31, 20, 0),
    "Ужин", 510);


  public static void assertMatch(Meal actual, Meal expected) {
    assertThat(actual).isEqualTo(expected);
  }

  public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
    assertMatch(actual, Arrays.asList(expected));
  }

  public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
    assertThat(actual).isEqualTo(expected);
  }
}
