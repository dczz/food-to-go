package com.dczz.ftg.restaurant;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.dczz.ftg.pending.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "restaurant")
@Getter
public class Restaurant implements Serializable {

  @Id
  @Column(unique = true, columnDefinition = "varchar(64)")
  @GeneratedValue(generator = "id_generator")
  @GenericGenerator(name = "id_generator", strategy = "com.dczz.ftg.infrastructure.IdGenerator")
  private String restaurantId;

  private String name;

  private String type;

  @Embedded
  @AttributeOverride(name = "city", column = @Column(name = "service_city"))
  @AttributeOverride(name = "zip", column = @Column(name = "service_zip"))
  @AttributeOverride(name = "state", column = @Column(name = "service_state"))
  private Address serviceAddress;

  private Date serviceTime;

}
