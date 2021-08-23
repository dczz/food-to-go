package com.dczz.ftg.restaurant;

import java.util.Date;
import java.util.List;

import com.dczz.ftg.pending.Address;

public interface RestaurantRepositoryCustom {

  List<Restaurant> findAvailableRestaurants(Address deliveryAddress, Date deliveryTime);

  boolean isRestaurantAvailable (Address deliveryAddress, Date deliveryTime);
}
