package com.dczz.ftg.pending;

import java.util.Calendar;
import java.util.Date;

public class PendingOrder {

  private String pendingOrderId;

  private Address deliveryAddress;

  private Date deliveryTime;

  public boolean updateDeliveryInfo (RestaurantRepository restaurantRepository, Address deliveryAddress, Date deliveryTime) {
    final Calendar earliestDeliveryTime = Calendar.getInstance();
    earliestDeliveryTime.add(Calendar.HOUR, 1);
    //交付时间在至少在一个小时后，这里包含里业务信息校验（可能考虑商家准备时间与司机配送时间）
    if (deliveryTime.before(earliestDeliveryTime.getTime())) {
      return false;
    }
    //how to access this?
    if (restaurantRepository.isRestaurantAvailable(deliveryAddress, deliveryTime)) {
      this.deliveryAddress = deliveryAddress;
      this.deliveryTime = deliveryTime;
      return true;
    }
    return false;
  }

}
