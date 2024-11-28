package com.anirudhm.dinetime.dao;

import com.anirudhm.dinetime.models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {

    @Autowired
    SessionFactory sessionFactory;

    public void saveUser(User user) {
        Session session = sessionFactory.openSession();
        Transaction t = session.beginTransaction();
        session.persist(user);
        t.commit();
    }

    public User getUserByUsername(String username) {
        Session session = sessionFactory.openSession();
        User user = null;
        try {
            String hql = "FROM User u WHERE u.username = :username";
            user = session.createQuery(hql, User.class)
                    .setParameter("username", username)
                    .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return user;
    }
}
