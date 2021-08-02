package com.dczz.ftg;

import java.util.Date;
import java.util.Optional;

import com.dczz.ftg.pending.Address;
import com.dczz.ftg.pending.PendingOrder;
import com.dczz.ftg.pending.PendingOrderRepository;
import com.dczz.ftg.pending.PlaceOrderService;
import com.dczz.ftg.pending.PlaceOrderServiceImpl;
import com.dczz.ftg.pending.RestaurantRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * 针对PlaceOrderService进行测试，所以PendingOrder领域对象与Repository仓储都需要mock，专注的目标是"Service"
 */
class PlaceOrderServiceTest {

  PlaceOrderService placeOrderService;

  PendingOrderRepository pendingOrderRepository;

  RestaurantRepository restaurantRepository;

  @Test
  @DisplayName("当用户去更新预订单时，如果能'找到'预订单,预订单的逻辑会被执行")
  void should_be_update_place_order_info () {
    pendingOrderRepository = mock(PendingOrderRepository.class);
    //当调用repository时，无论传入什么参数都保证返回mock的PendingOrder
    final PendingOrder pendingOrder = mock(PendingOrder.class);
    when(pendingOrderRepository.findById(any())).thenReturn(Optional.of(pendingOrder));
    placeOrderService = new PlaceOrderServiceImpl(pendingOrderRepository, restaurantRepository);

    String pendingOrderId = null;
    Address deliveryAddress = mockGoodAddress();
    Date deliveryTime = new Date();
    //由于这里只针对Service进行测试，所以这里只需要校验PendingOrder的updateDeliveryInfo被执行，这个测试就达到目的了
    //当placeOrderService.updateDeliveryInfo
    placeOrderService.updateDeliveryInfo(pendingOrderId, deliveryAddress, deliveryTime);
    //pendingOrder.updateDeliveryInfo(restaurantRepository, deliveryAddress, deliveryTime)会被调用1次
    verify(pendingOrder, times(1)).updateDeliveryInfo(restaurantRepository, deliveryAddress, deliveryTime);
  }

  private Address mockGoodAddress () {
    final Address address = new Address();
    address.setDesc("xxxxx");
    return address;
  }

}
