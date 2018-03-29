package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.junit.Assert.*;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
  "classpath:spring/spring-app.xml",
  "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

  static {
    // Only for postgres driver logging
    // It uses java.util.logging and logged via jul-to-slf4j bridge
    SLF4JBridgeHandler.install();
  }

  @Autowired
  private MealService service;

  @Test
  public void get() throws Exception {
    Meal meal = service.get(MEAL_ID, USER_ID);
    assertMatch(meal, testMeal1);
  }

  @Test
  public void delete() throws Exception {
    service.delete(MEAL_ID, USER_ID);
    assertMatch(service.getAll(USER_ID), testMeal6, testMeal5, testMeal4, testMeal3, testMeal2);
  }

  @Test
  public void getBetweenDateTimes() throws Exception{
    List<Meal> meals = service.getBetweenDateTimes(LocalDateTime.of(2015, Month.MAY,
      30, 10, 0), LocalDateTime.of(2015, Month.MAY,
      30, 20, 0), USER_ID);
    assertMatch(meals, testMeal3, testMeal2, testMeal1);
  }

  @Test(expected = NotFoundException.class)
  public void notFoundDelete() throws Exception {
    service.delete(1,1);
  }

  @Test(expected = NotFoundException.class)
  public void getNotFound() throws Exception {
    service.get(1,1);
  }

  @Test(expected = NotFoundException.class)
  public void notFoundUpdate() throws Exception {
    Meal newMeal = new Meal(MEAL_ID, testMeal1.getDateTime(), "Обновленный завтрак", 1500);
    service.update(newMeal, USER_ID+3);
  }

  @Test
  public void getAll() throws Exception {
    List<Meal> meals = service.getAll(USER_ID);
    assertMatch(meals, testMeal6, testMeal5, testMeal4, testMeal3, testMeal2, testMeal1);
  }

  @Test
  public void update() throws Exception {
    Meal newMeal = new Meal(MEAL_ID, testMeal1.getDateTime(), "Обновленный завтрак", 1500);
    service.update(newMeal, USER_ID);
    assertMatch(service.get(MEAL_ID, USER_ID), newMeal);
  }

  @Test
  public void create() throws Exception {
    Meal newMeal = new Meal(null, (LocalDateTime.of(2015, Month.JUNE, 30, 10, 0)), "Новый Завтрак", 500);
    Meal created = service.create(newMeal, USER_ID);
    assertMatch(service.getAll(USER_ID), created, testMeal6, testMeal5, testMeal4, testMeal3, testMeal2, testMeal1);
  }
}
