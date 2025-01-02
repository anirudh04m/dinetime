package com.anirudhm.dinetime.dao;

import com.anirudhm.dinetime.models.Restaurant;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RestaurantDao {

    @Autowired
    SessionFactory sessionFactory;

    public List<Restaurant> getAllRestaurants() {
        Session session = sessionFactory.openSession();
        List<Restaurant> restaurants = null;
        try {
            restaurants = session.createQuery("from Restaurant ", Restaurant.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return restaurants;
    }

    public Restaurant getRestaurantById(int id) {
        Session session = sessionFactory.openSession();
        Restaurant restaurant = null;
        try {
            restaurant = session.get(Restaurant.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return restaurant;
    }
}
