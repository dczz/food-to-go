package com.dczz.ftg.pending;

import java.util.Date;

public interface PlaceOrderService {

  /**
   * 顾客提交交付地址与交付时间
   *
   * @param pendingId  预订单
   * @param deliveryAddress 交付地址
   * @param deliveryTime    交付时间
   */
  PlaceOrderServiceResult updateDeliveryInfo (String pendingId, Address deliveryAddress, Date deliveryTime);

}
