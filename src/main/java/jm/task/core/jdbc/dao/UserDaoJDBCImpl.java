package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private final Connection connection = Util.getConnection();
    private String query;
    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        query = "CREATE TABLE IF NOT EXISTS users (" +
                "id BIGINT AUTO_INCREMENT PRIMARY KEY," +
                "firstname VARCHAR(50)," +
                "lastname VARCHAR(50)," +
                "age TINYINT)";

        try (Statement statement = connection.createStatement()) {
            statement.execute(query);

        } catch (SQLException e) {
            System.out.println("Не получилось создать таблицу в БД - " + e.getMessage());
        }
    }

    public void dropUsersTable() {
        query = "DROP TABLE IF EXISTS users;";
        try (Statement statement = connection.createStatement()) {
            statement.execute(query);

        } catch (SQLException e) {
            System.out.println("Не получилось удалить таблицу из БД - " + e.getMessage());
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        query = "INSERT INTO users (firstname, lastname, age) VALUES (?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.execute();
            System.out.println("Пользователь с именем " + name + " добавлен в базу данных");

        } catch (SQLException e) {
            System.out.println("Не получилось добавить пользователя в БД - " + e.getMessage());
        }
    }

    public void removeUserById(long id) {
        query = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException e) {
            System.out.println("Не получилось удалить пользователя из БД - " + e.getMessage());
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        query = "SELECT * FROM users";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                long id = rs.getLong("id");
                String firstname = rs.getString("firstname");
                String lastname = rs.getString("lastname");
                byte age = rs.getByte("age");
                User user = new User(firstname, lastname, age);
                user.setId(id);
                users.add(user);
                System.out.println(user);
            }
        } catch (SQLException e) {
            System.out.println("Не получилось получить всех пользователей из БД - " + e.getMessage());
        }
        return users;
    }

    public void cleanUsersTable() {
        query = "DELETE FROM users";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.execute();
        } catch (SQLException e) {
            System.out.println("Не получилось очистить таблицу в БД - " + e.getMessage());
        }
    }
}
