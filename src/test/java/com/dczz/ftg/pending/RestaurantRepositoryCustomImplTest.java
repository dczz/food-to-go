package com.dczz.ftg.pending;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import com.dczz.ftg.restaurant.Restaurant;
import com.dczz.ftg.restaurant.RestaurantRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Sql(scripts = "classpath:schema.sql")
class RestaurantRepositoryCustomImplTest {

  @Autowired
  RestaurantRepository restaurantRepository;

  @Test
  @DisplayName("商店是否可用-执行自定义查询")
  void should_execute_jpql(){
    Address deliveryAddress = goodAddress();
    final Date deliveryTime = goodDeliveryTime();
    mockData();
    final boolean restaurantAvailable = restaurantRepository.isRestaurantAvailable(deliveryAddress, deliveryTime);
    Assertions.assertTrue(restaurantAvailable);
  }

  private void mockData () {
    Date serviceTime = mockServiceDate();
    Address serviceAddress = mockServiceAddress();
    restaurantRepository.save(new Restaurant(null,"店铺","123",serviceAddress,serviceTime));
  }

  private Address mockServiceAddress () {
    return goodAddress();
  }

  private Date mockServiceDate () {
    final LocalDateTime parse = LocalDateTime.parse("2021-11-01 10:00:00", DateTimeFormatter.ofPattern(TimePattern()));
    return Date.from(parse.atZone(ZoneId.systemDefault()).toInstant());
  }

  private String TimePattern () {
    return "yyyy-MM-dd HH:mm:ss";
  }

  private Address goodAddress () {
    return new Address("010","beijing","dongsi");
  }

  private Date goodDeliveryTime(){
    final LocalDateTime serviceTime = LocalDateTime.parse("2021-10-10 10:00:00", DateTimeFormatter.ofPattern(TimePattern()));
    return Date.from(serviceTime.atZone(ZoneId.systemDefault()).toInstant());
  }
}