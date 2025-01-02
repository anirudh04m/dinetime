package com.anirudhm.dinetime.dao;

import com.anirudhm.dinetime.models.Order;
import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderDao {

    @Autowired
    SessionFactory sessionFactory;

    public void saveOrder(Order order) {
        Session session = sessionFactory.openSession();
        Transaction t = session.beginTransaction();
        session.persist(order);
        t.commit();
    }

    public List<Order> getOrdersByUserId(int userId) {
        Session session = sessionFactory.openSession();
        List<Order> orders = null;
        try {
            orders = session.createQuery("FROM Order o WHERE o.user.userId = :userId", Order.class)
                    .setParameter("userId", userId)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return orders;
    }


    public List<Order> getAllOrders() {
        Session session = sessionFactory.openSession();
        List<Order> orders = null;
        try {
            orders = session.createQuery("from Order ", Order.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return orders;
    }

    public Order getOrderById(int id) {
        Session session = sessionFactory.openSession();
        Order order = null;
        try {
            order = session.get(Order.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return order;
    }

    public void updateOrder(Order order) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.merge(order);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public List<Order> getOrdersByRestaurantId(Integer restaurantId) {
        Session session = sessionFactory.openSession();
        List<Order> orders = null;
        try {
            orders = session.createQuery("FROM Order o WHERE o.restaurant.resId = :resId", Order.class)
                    .setParameter("resId", restaurantId)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return orders;
    }

    public List<Order> findPageOrdersByRestaurantId(Integer restaurantId, int page, int pageSize) {
        Session session = sessionFactory.openSession();
        String queryString = "SELECT o FROM Order o WHERE o.restaurant.id = :restaurantId";
        TypedQuery<Order> query = session.createQuery(queryString, Order.class);
        query.setParameter("restaurantId", restaurantId);
        query.setFirstResult((page) * pageSize);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

    public long countOrdersByRestaurantId(Integer restaurantId) {
        Session session = sessionFactory.openSession();
        String queryString = "SELECT COUNT(o) FROM Order o WHERE o.restaurant.id = :restaurantId";
        return session.createQuery(queryString, Long.class)
                .setParameter("restaurantId", restaurantId)
                .getSingleResult();
    }

}
