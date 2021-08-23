package com.dczz.ftg.pending;

import java.io.Serializable;

import javax.persistence.Table;

import lombok.Data;

@Data
@Table(name = "pending_order_line_item")
public class PendingOrderLineItem implements Serializable {

}