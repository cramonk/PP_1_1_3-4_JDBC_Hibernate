package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {

        final UserService userService = new UserServiceImpl();

        // Создание таблицы пользователей
        userService.createUsersTable();

        // Добавление 4 пользователей в таблицу с данными.
        userService.saveUser("Ivan", "Ivanov", (byte) 20);
        userService.saveUser("Petr", "Petrov", (byte) 25);
        userService.saveUser("Nikita", "Nikitin", (byte) 31);
        userService.saveUser("Andrey", "Andreev", (byte) 30);

        // Получение всех пользователей из базы и вывод в консоль.
        userService.getAllUsers();

        // Очистка таблицы пользователей.
        userService.cleanUsersTable();

        // Удаление таблицы пользователей.
        userService.dropUsersTable();
    }
}
