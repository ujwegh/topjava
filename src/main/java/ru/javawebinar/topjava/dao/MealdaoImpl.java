package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealdaoImpl implements MealDao {
  private static List<Meal> mealList;
  public static int caloriesPerDay = 2000;
  private Map<Integer, Meal> mealMap = new ConcurrentHashMap<>();
  private AtomicInteger counter = new AtomicInteger(0);
  {
    mealList.addAll(MealsUtil.mealList);
  }


  public static List<MealWithExceed> getListWithExceed(List<Meal> mealList, int caloriesPerDay) {
    return MealsUtil.getFilteredWithExceeded(mealList, LocalTime.MIN, LocalTime.MAX, caloriesPerDay);
  }

  @Override
  public void add(Meal meal) {
    if (meal.getId() != null) {
      update(meal.getId(), meal);
    }
    else {
      meal.setId(counter.incrementAndGet());
      mealMap.put(meal.getId(), meal);
    }
  }

  @Override
  public void update(int id, Meal meal) {
    mealMap.put(id, meal);
  }

  @Override
  public void delete(int id) {
    mealMap.remove(id);
  }

  @Override
  public Meal getById(int id) {
    return mealMap.get(id);
  }

  @Override
  public Collection<Meal> getAllMeals() {
    return mealMap.values();
  }

  @Override
  public Map<Integer, Meal> getMealMap() {
    return mealMap;
  }
}
