package com.dczz.ftg.pending;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant,String> {

  List<Restaurant> findAvailableRestaurants(Address deliveryAddress, Date deliveryTime);

  boolean isRestaurantAvailable (Address deliveryAddress, Date deliveryTime);

}
