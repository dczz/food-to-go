package com.dczz.ftg.restaurant;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, String>, RestaurantRepositoryCustom {

}
