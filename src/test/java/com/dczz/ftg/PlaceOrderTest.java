package com.dczz.ftg;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import com.dczz.ftg.pending.Address;
import com.dczz.ftg.pending.PendingOrder;
import com.dczz.ftg.pending.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * 针对PendingOrder的测试，所以mock掉Service与Repository
 */
class PlaceOrderTest {

  RestaurantRepository restaurantRepository;
  PendingOrder pendingOrder = new PendingOrder();
  Address deliveryAddress = mockAddress();
  Date deliveryTime = mockTime();

  @BeforeEach
  void setUp(){
    restaurantRepository = mock(RestaurantRepository.class);
  }

  private void verifyRepository () {
    verify(restaurantRepository, times(1)).isRestaurantAvailable(deliveryAddress, deliveryTime);
  }

  @Test
  @DisplayName("用户期望一份在三个小时候后送到xxxx地址的订单")
  void should_be_apply_update_delivery_info () {
    //通过交付地址与时间能找到可以提供服务的商店
    when(restaurantRepository.isRestaurantAvailable(deliveryAddress, deliveryTime)).thenReturn(true);
    final boolean result = pendingOrder.updateDeliveryInfo(restaurantRepository, deliveryAddress, deliveryTime);
    verifyRepository();
    assertTrue(result);
  }

  @Test
  @DisplayName("用户期望一份在三个小时候后送到xxxx地址的订单，但是没有找到符合条件的商店")
  void should_be_apply_update_delivery_info_but_not_found_restaurant () {
    //通过交付地址与时间能找到可以提供服务的商店
    when(restaurantRepository.isRestaurantAvailable(deliveryAddress, deliveryTime)).thenReturn(false);
    final boolean result = pendingOrder.updateDeliveryInfo(restaurantRepository, deliveryAddress, deliveryTime);
    verifyRepository();
    assertFalse(result);
  }

  private Date mockTime () {
    return Date.from(LocalDateTime.now().plusHours(3).atZone(ZoneId.systemDefault()).toInstant());
  }

  private Address mockAddress () {
    final Address address = new Address();
    address.setDesc("xxxx");
    return address;
  }

}
