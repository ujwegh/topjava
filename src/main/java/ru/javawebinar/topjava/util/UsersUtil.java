package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.Arrays;
import java.util.List;

public class UsersUtil {
  public static final List<User> userList = Arrays.asList(
      new User("admin", "admin@mail.ru", "123456", Role.ROLE_ADMIN),
      new User("user", "user@yandex.ru", "123456", Role.ROLE_USER)
  );
}
