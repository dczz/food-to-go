package com.dczz.ftg.pending;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

import com.dczz.ftg.restaurant.RestaurantRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@DataJpaTest
class PlaceOrderRepositoryTest {

  @Autowired
  private PendingOrderRepository repository;
  @Autowired
  private RestaurantRepository restaurantRepository;

  private PendingOrder pendingOrder;

  @BeforeEach
  void setUp () {
    pendingOrder = repository.save(new PendingOrder());
  }

  @Test
  void should_be_save_entity () {
    assertTrue(Objects.nonNull(pendingOrder.getPendingOrderId()));
    assertEquals(32, pendingOrder.getPendingOrderId().length());
  }

  @Test
  void should_be_delete_entity () {
    repository.delete(pendingOrder);
    final Optional<PendingOrder> deleted = repository.findById(pendingOrder.getPendingOrderId());
    assertTrue(deleted.isEmpty());
  }

  @Test
  void should_be_update_entity(){
    final Optional<PendingOrder> pendingOrderOptional = repository.findById(this.pendingOrder.getPendingOrderId());
    pendingOrderOptional.ifPresent(pendingOrder -> {
      Address deliveryAddress = makeDeliveryAddress();
      Date deliveryTime = Date.from(LocalDateTime.now().plusHours(3).atZone(ZoneId.systemDefault()).toInstant());
      pendingOrder.updateDeliveryInfo(restaurantRepository, deliveryAddress, deliveryTime);
      repository.save(pendingOrder);
      Assertions.assertTrue(Objects.nonNull(pendingOrder.getPendingOrderId()));
      log.info(pendingOrder.toString());
    });
  }

  private Address makeDeliveryAddress () {
    return new Address("010","beijing","xxx省xxx市xxx街道xx小区1栋2-1");
  }
}
