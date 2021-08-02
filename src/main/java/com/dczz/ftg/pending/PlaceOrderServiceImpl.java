package com.dczz.ftg.pending;

import java.util.Date;

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
  public PlaceOrderServiceResult updateDeliveryInfo (String pendingOrderId, Address deliveryAddress, Date deliveryTime) {
    final PendingOrder pendingOrder = pendingOrderRepository.findById(pendingOrderId).orElseGet(PendingOrder::new);
    boolean success = pendingOrder.updateDeliveryInfo(restaurantRepository, deliveryAddress, deliveryTime);
    return new PlaceOrderServiceResult(success, pendingOrder);
  }
}
