package jm.task.core.jdbc.dao;


import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

import static jm.task.core.jdbc.util.Util.getSessionFactory;

public class UserDaoHibernateImpl implements UserDao {
    private Session session = null;
    private Transaction transaction = null;
    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        try {
            session = getSessionFactory().openSession();
            transaction = session.beginTransaction();

            session.createNativeQuery("CREATE TABLE IF NOT EXISTS Users (id BIGINT AUTO_INCREMENT, " +
                    "name VARCHAR(255) NOT NULL, " +
                    "lastName VARCHAR(255) NOT NULL, " +
                    "age TINYINT NOT NULL, " +
                    "PRIMARY KEY (id));").executeUpdate();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }
    }

    @Override
    public void dropUsersTable() {

        try {
            session = getSessionFactory().openSession();
            transaction = session.beginTransaction();

            session.createNativeQuery("DROP TABLE IF EXISTS users").executeUpdate();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try {
            session = getSessionFactory().openSession();
            transaction = session.beginTransaction();

            session.save(new User(name, lastName, age));

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }
    }


    @Override
    public void removeUserById(long id) {
        try {
            session = getSessionFactory().openSession();
            transaction = session.beginTransaction();

            session.delete(id);

            transaction.commit();
        } catch (Exception e){
            if (transaction != null){
                transaction.rollback();
            }
        }
        session.close();
    }


    @Override
    public List<User> getAllUsers() {
        List<User> users = (List<User>) getSessionFactory().openSession().createQuery("From User").list();
        return users;
        }

    @Override
    public void cleanUsersTable() {
        try {
            session = getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.createNativeQuery("TRUNCATE TABLE users").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }
    }
}
