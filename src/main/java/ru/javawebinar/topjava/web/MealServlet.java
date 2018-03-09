package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealdaoImpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
  private static final Logger log = getLogger(UserServlet.class);
  private static String INSERT_OR_EDIT = "meal.jsp";
  private static String LIST_MEALS = "meals.jsp";
  private MealDao mealDao;

  public MealServlet() {
    super();
    mealDao = new MealdaoImpl();
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    log.debug("redirect to meals(post)");
    req.setCharacterEncoding("UTF-8");
    String mealId = req.getParameter("id");

    Meal meal = new Meal(mealId.isEmpty() ? null : Integer.parseInt(mealId),
      LocalDateTime.parse(req.getParameter("dateTime")),
      req.getParameter("description"),
      Integer.valueOf(req.getParameter("calories")));

    if (mealId.isEmpty()) {
      mealDao.add(meal);
      log.info("Add meal to list: " + meal);
    } else {
      mealDao.update(meal.getId(), meal);
      log.info("Update meal: " + mealId);
    }
    req.setAttribute("meals", MealsUtil.getListWithExceed(mealDao.getAllMeals(), MealdaoImpl.caloriesPerDay));
    resp.sendRedirect("meals");
  }

//  @Override
//  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//    log.debug("redirect to meals");
//    String forward = "";
//    String action = request.getParameter("action");
//
//    if (action.equalsIgnoreCase("delete")) {
//        int id = Integer.parseInt(request.getParameter("id"));
//        log.info("Meal was deleted: " + id);
//        mealDao.delete(id);
//        forward = LIST_MEALS;
//        request.setAttribute("meals", MealsUtil.getListWithExceed(mealDao.getAllMeals(), MealdaoImpl.caloriesPerDay));
//    } else if (action.equalsIgnoreCase("create")) {
//        forward = INSERT_OR_EDIT;
//        Meal meal = new Meal(LocalDateTime.now(), "", 0);
//        log.info("Meal was created: " + meal.toString());
//        request.setAttribute("meal", meal);
//    } else if (action.equalsIgnoreCase("edit")){
//        forward = INSERT_OR_EDIT;
//        int id = Integer.parseInt(request.getParameter("id"));
//        Meal meal = mealDao.getById(id);
//        log.info("Meal was edited: " + meal.toString());
//        request.setAttribute("meal", meal);
//    } else if (action.equalsIgnoreCase("listMeals")){
//        forward = LIST_MEALS;
//        log.info("Get all meals");
//        request.setAttribute("meals", MealsUtil.getListWithExceed(mealDao.getAllMeals(), MealdaoImpl.caloriesPerDay));
//    } else {
//        forward = LIST_MEALS;
//        log.info("Get all meals");
//        request.setAttribute("meals", MealsUtil.getListWithExceed(mealDao.getAllMeals(), MealdaoImpl.caloriesPerDay));
//    }
//
//    request.getRequestDispatcher(forward).forward(request, response);
//
//
////    List<MealWithExceed> list = MealsUtil.getFilteredWithExceeded(MealsUtil.mealList, LocalTime.MIN, LocalTime.MAX, 2000);
////    request.setAttribute("mealList", list);
////    request.getRequestDispatcher("/meals.jsp").forward(request, response);
////    response.sendRedirect("meals.jsp");
//  }
}
