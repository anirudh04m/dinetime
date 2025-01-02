package com.anirudhm.dinetime.dao;

import com.anirudhm.dinetime.models.Item;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ItemDao {

    @Autowired
    SessionFactory sessionFactory;

    public void saveItem(Item item) {
        Session session = sessionFactory.openSession();
        Transaction t = session.beginTransaction();
        session.persist(item);
        t.commit();
    }

    public List<Item> getAllItems() {
        Session session = sessionFactory.openSession();
        List<Item> items = null;
        try {
            items = session.createQuery("from Item ", Item.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return items;
    }

    public List<Item> getItemsByRestaurantId(Integer restaurantId) {
        Session session = sessionFactory.openSession();
        List<Item> items = null;
        try {
            items = session.createQuery("FROM Item i WHERE i.restaurant.resId = :resId", Item.class)
                    .setParameter("resId", restaurantId)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return items;
    }

    public List<Item> getItemsByItemsIds(List<Integer> itemIds) {
        Session session = sessionFactory.openSession();
        List<Item> items = null;
        try {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Item> criteriaQuery = builder.createQuery(Item.class);
            Root<Item> itemRoot = criteriaQuery.from(Item.class);

            criteriaQuery.select(itemRoot)
                    .where(itemRoot.get("itemId").in(itemIds));

            items = session.createQuery(criteriaQuery).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return items;
    }
}
