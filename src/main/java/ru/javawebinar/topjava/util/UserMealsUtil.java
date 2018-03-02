package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with correctly exceeded field
      //      Реализовать метод UserMealsUtil.getFilteredWithExceeded:
//      -  должны возвращаться только записи между startTime и endTime
//        -  поле UserMealWithExceed.exceed должно показывать,
//        превышает ли сумма калорий за весь день параметра метода caloriesPerDay
//
//      Т.е UserMealWithExceed - это запись одной еды, но поле exceeded будет одинаково для всех записей за этот день.
//
//      - Проверте результат выполнения ДЗ (можно проверить логику в http://topjava.herokuapp.com , список еды)
//      - Оцените Time complexity вашего алгоритма, если он O(N*N)- попробуйте сделать O(N).
      Map<LocalDateTime, Integer> map = mealList.stream()
        .collect(Collectors.groupingBy(UserMeal::getDateTime, Collectors.summingInt(UserMeal::getCalories)));

      List<UserMealWithExceed> list = mealList.stream()
        .filter((m) -> TimeUtil.isBetween(m.getDateTime().toLocalTime(), startTime, endTime))
        .map(m -> new UserMealWithExceed(m.getDateTime(), m.getDescription(), m.getCalories(), map.get(m.getDateTime()) > caloriesPerDay))
        .collect(Collectors.toList());
      return list;
    }
}
