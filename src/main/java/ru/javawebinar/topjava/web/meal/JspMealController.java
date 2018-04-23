package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
public class JspMealController extends AbstractMealController {

    protected JspMealController(MealService service) {
        super(service);
    }

    @GetMapping("/meals")
    public String getAll(HttpServletRequest request, Model model) {
        model.addAttribute("meals", super.getAll());
    return "meals";
    }
//
//
//    @RequestMapping(value = "/delete", method = RequestMethod.GET)
//    public String delete(HttpServletRequest request) {
//        super.delete(getId(request));
//        return "redirect:/meals";
//    }
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(HttpServletRequest request, Model model) {
        model.addAttribute("meal", new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000));
        return "/mealForm";
    }


    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update(HttpServletRequest request, Model model) {
        model.addAttribute("meal", super.get(getId(request)));
        return "/mealForm";
    }

    @RequestMapping(value = "/delete")
    public String delete(HttpServletRequest request) {
        super.delete(getId(request));
        return "redirect:/meals";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String createOrUpdate(HttpServletRequest request) {
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String action = request.getParameter("action");
        if (action == null) {
            Meal meal = new Meal(
                    LocalDateTime.parse(request.getParameter("dateTime")),
                    request.getParameter("description"),
                    Integer.parseInt(request.getParameter("calories")));

            if (request.getParameter("id").isEmpty()) {
                super.create(meal);
            } else {
                super.update(meal, getId(request));
            }
        }
        return "redirect:/meals";
    }

    @RequestMapping(value = "/filter", method = RequestMethod.POST)
    public String getBetween(HttpServletRequest request, Model model) {
        LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = parseLocalTime(request.getParameter("endTime"));
        model.addAttribute("meals", super.getBetween(startDate, startTime, endDate, endTime));
        return "meals";
    }



    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }

}
