package com.dczz.ftg.pending;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class Address implements Serializable {

  private String zip;

  private String city;

  private String state;

  public Address () {
  }

  public Address (String zip, String city, String state) {
    this.zip = zip;
    this.city = city;
    this.state = state;
  }
}
