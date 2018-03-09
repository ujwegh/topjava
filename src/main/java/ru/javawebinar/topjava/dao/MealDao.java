package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;
import java.util.Map;

public interface MealDao {
  void add(Meal meal);

  void update(int id, Meal meal);

  void delete(int id);

  Meal getById(int id);

  Collection<Meal> getAllMeals();

  Map<Integer, Meal> getMealMap();
}
