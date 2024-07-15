package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class UserDaoHibernateImpl implements UserDao {
    private final SessionFactory sessionFactory = Util.getSessionFactory();
    private Transaction transaction;
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS users (" +
                    "id BIGINT AUTO_INCREMENT PRIMARY KEY," +
                    "firstname VARCHAR(50)," +
                    "lastname VARCHAR(50)," +
                    "age TINYINT)").executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }

    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS users").executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            users = session.createQuery("FROM User").getResultList();
            transaction.commit();
            users.forEach(System.out::println);
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createQuery("DELETE FROM User").executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
}
