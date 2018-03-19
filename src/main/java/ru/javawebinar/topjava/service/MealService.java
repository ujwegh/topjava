package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.Collection;

public interface MealService {

  Meal create(Meal meal, int userId);

  void delete(int id, int userId) throws NotFoundException;

  Meal get(int id, int userId) throws NotFoundException;

  Collection<Meal> getBetweenDate(int userId, LocalDateTime start, LocalDateTime  end) throws NotFoundException;

  void update(Meal meal, int userId);

  Collection<Meal> getAll(int userId);
}
