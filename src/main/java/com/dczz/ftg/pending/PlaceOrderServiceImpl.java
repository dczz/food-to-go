package com.dczz.ftg.pending;

import java.util.Date;

import com.dczz.ftg.restaurant.RestaurantRepository;
import org.springframework.stereotype.Service;

@Service
public class PlaceOrderServiceImpl implements PlaceOrderService {

  private final PendingOrderRepository pendingOrderRepository;
  private final RestaurantRepository restaurantRepository;

  public PlaceOrderServiceImpl (PendingOrderRepository pendingOrderRepository, RestaurantRepository restaurantRepository) {
    this.pendingOrderRepository = pendingOrderRepository;
    this.restaurantRepository = restaurantRepository;
  }

  @Override
  public PlaceOrderServiceResult updateDeliveryInfo (String pendingId, Address deliveryAddress, Date deliveryTime) {
    final PendingOrder pendingOrder = pendingOrderRepository.findById(pendingId).orElseGet(PendingOrder::new);
    boolean updatedResult = pendingOrder.updateDeliveryInfo(restaurantRepository, deliveryAddress, deliveryTime);
    return new PlaceOrderServiceResult(updatedResult, pendingOrder);
  }
}
