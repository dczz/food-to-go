package com.dczz.ftg.infrastructure.persistence;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.dczz.ftg.pending.Address;
import com.dczz.ftg.restaurant.Restaurant;
import com.dczz.ftg.restaurant.RestaurantRepositoryCustom;

public class RestaurantRepositoryCustomImpl implements RestaurantRepositoryCustom {

  @PersistenceContext
  EntityManager em;

  @Override
  public List<Restaurant> findAvailableRestaurants (Address deliveryAddress, Date deliveryTime) {
    final TypedQuery<Restaurant> query = em.createQuery("select r from restaurant r where r.serviceAddress = ?1 and r.serviceTime > ?2", Restaurant.class);
    query.setParameter(1, deliveryAddress);
    query.setParameter(2, deliveryTime);
    return query.getResultList();
  }

  @Override
  public boolean isRestaurantAvailable (Address deliveryAddress, Date deliveryTime) {
    final TypedQuery<Long> query = em.createQuery("select count(r) from restaurant r where r.serviceAddress = ?1 and r.serviceTime > ?2", Long.class);
    query.setParameter(1, deliveryAddress);
    query.setParameter(2, deliveryTime);
    return query.getSingleResult() > 0;
  }
}
