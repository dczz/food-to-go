package com.dczz.ftg.pending;

public class PlaceOrderServiceResult {

  private boolean status;

  private PendingOrder pendingOrder;

  public boolean isSuccess () {
    return status;
  }

  public PlaceOrderServiceResult (boolean status, PendingOrder pendingOrder) {
    this.status = status;
    this.pendingOrder = pendingOrder;
  }

  public PendingOrder getPendingOrder () {
    return pendingOrder;
  }
}
