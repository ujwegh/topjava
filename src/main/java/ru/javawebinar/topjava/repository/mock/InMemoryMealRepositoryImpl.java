package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
  private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
  private AtomicInteger counter = new AtomicInteger(0);

  {
    for (Meal meal : MealsUtil.MEALS)
      save(meal, 1);
  }


  @Override
  public Meal save(Meal meal, int userId) {
    if (!meal.isNew()) {
      update(meal, userId);

    } else {
      meal.setId(counter.incrementAndGet());
      Map<Integer, Meal> mealMap = repository.get(userId);
      if (mealMap != null) {
        mealMap.put(meal.getId(), meal);
        repository.put(userId, mealMap);
      } else {
        mealMap = new ConcurrentHashMap<>();
        mealMap.put(meal.getId(), meal);
        repository.put(userId, mealMap);
      }
    }
    return meal;
  }

  public void update(Meal meal, int userId) {
    Map<Integer, Meal> mealMap = repository.get(userId);
    if (mealMap != null) {
      mealMap.put(meal.getId(), meal);
      repository.put(userId, mealMap);
    }
  }

  @Override
  public boolean delete(int id, int userId) {
    Map<Integer, Meal> map = repository.get(userId);

    return repository.remove(id) != null;
  }

  @Override
  public Meal get(int id, int userId) {
    Map<Integer, Meal> map = repository.get(userId);
    return map != null ? map.get(id) : null;
  }

  @Override
  public Collection<Meal> getAll(int userId) {
    Map<Integer, Meal> map = repository.get(userId);
    if (map != null)
    {
      return map.values().stream()
        .sorted((o1, o2) -> o2.getDateTime().compareTo(o1.getDateTime()))
        .collect(Collectors.toList());
    }
    return Collections.emptyList();
  }

  @Override
  public Collection<Meal> getBetweenDate(int userId, LocalDateTime startDateTime, LocalDateTime endDateTime) {
    Map<Integer, Meal> map = repository.get(userId);
    if (map != null)
    {
      return map.values().stream()
        .filter(meal -> DateTimeUtil.isBetween(meal.getDateTime().toLocalTime(), startDateTime.toLocalTime(), endDateTime.toLocalTime()))
        .collect(Collectors.toList());
    }
    return Collections.emptyList();
  }
}

