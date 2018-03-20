package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {
  protected final Logger log = LoggerFactory.getLogger(getClass());
  private int userId = AuthorizedUser.id();

  @Autowired
  private MealService service;

  public Collection<MealWithExceed> getAll() {
    log.info("getAll");
    return MealsUtil.getFilteredWithExceeded(service.getAll(userId), AuthorizedUser.getCaloriesPerDay(), LocalTime.MIN, LocalTime.MAX);
  }

  public Meal get(int id) {
    log.info("get {}", id);
    return service.get(id, userId);
  }

  public Meal create(Meal meal) {
    log.info("create {}", meal);
    checkNew(meal);
    return service.create(meal, userId);
  }

  public void delete(int id) {
    log.info("delete {}", id);
    service.delete(id, userId);
  }

  public void update(Meal meal, int id) {
    log.info("update {} with id={}, user id={}", meal, id, userId);
    assureIdConsistent(meal, id);
    service.update(meal, userId);
  }

  public List<MealWithExceed> getBetweenDate(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
    log.info("getBetweenDate: Date {} and {}, Time {} and {}", startDate, endDate, startTime, endTime);

    Collection<Meal> meals;
    if (startDate == null && endDate != null) {
      meals = service.getBetweenDate(userId, LocalDateTime.of(LocalDate.MIN, LocalTime.MIN), LocalDateTime.of(endDate, LocalTime.MAX));
    } else if (startDate != null && endDate == null) {
      meals = service.getBetweenDate(userId, LocalDateTime.of(startDate, LocalTime.MIN), LocalDateTime.of(LocalDate.MAX, LocalTime.MAX));
    } else if (startDate != null && endDate != null) {
      meals = service.getBetweenDate(userId, LocalDateTime.of(startDate, LocalTime.MIN), LocalDateTime.of(endDate, LocalTime.MAX));
    } else {
      meals = service.getBetweenDate(userId, LocalDateTime.of(LocalDate.MIN, LocalTime.MIN), LocalDateTime.of(LocalDate.MAX, LocalTime.MAX));
    }

    return MealsUtil.getFilteredWithExceeded(meals, AuthorizedUser.getCaloriesPerDay(), startTime != null ? startTime : LocalTime.MIN, endTime != null ? endTime : LocalTime.MAX);

  }
}
