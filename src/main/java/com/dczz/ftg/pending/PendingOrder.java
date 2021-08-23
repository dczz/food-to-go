package com.dczz.ftg.pending;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Version;

import com.dczz.ftg.restaurant.Restaurant;
import com.dczz.ftg.restaurant.RestaurantRepository;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity(name = "pending_order")
@SQLDelete(sql = "UPDATE pending_order SET deleted = 1 AND opt_lock = opt_lock + 1 WHERE id = ? AND opt_lock = ?")
@Where(clause = "deleted <> 1")
public class PendingOrder implements Serializable {

  @Id
  @Column(name = "pending_order_id", unique = true, columnDefinition = "varchar(64)")
  @GeneratedValue(generator = "id_generator")
  @GenericGenerator(name = "id_generator", strategy = "com.dczz.ftg.infrastructure.IdGenerator")
  private String id;

  @Embedded
  @AttributeOverride(name = "zip", column = @Column(name = "delivery_zip"))
  @AttributeOverride(name = "city", column = @Column(name = "delivery_city"))
  @AttributeOverride(name = "state", column = @Column(name = "delivery_state"))
  @AttributeOverride(name = "desc", column = @Column(name = "delivery_desc"))
  private Address deliveryAddress;

  @ManyToOne
  private Restaurant restaurant;

  @ElementCollection
  private List<PendingOrderLineItem> lineItems;

  private Date deliveryTime;

  @Version
  @Column(name = "opt_lock")
  private int optLock;

  private boolean deleted;

  public List<PendingOrderLineItem> getLineItems () {
    return lineItems;
  }

  public void setLineItems (List<PendingOrderLineItem> lineItems) {
    this.lineItems = lineItems;
  }

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


  public String getPendingOrderId () {
    return id;
  }


  @Override
  public String toString () {
    return "PendingOrder{" +
        "pendingOrderId='" + id + '\'' +
        ", deliveryAddress=" + deliveryAddress +
        ", deliveryTime=" + deliveryTime +
        ", optLock=" + optLock +
        ", deleted=" + deleted +
        '}';
  }
}
