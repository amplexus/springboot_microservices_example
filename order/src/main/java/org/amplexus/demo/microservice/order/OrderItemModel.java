package org.amplexus.demo.microservice.order;

import java.util.HashSet;
import java.util.Set;
import java.util.Date;
import javax.persistence.*;

@Entity
public class OrderItemModel {

    public enum OrderItemStatus { BACKORDER, FULFILLED } 

    @ManyToOne
    private OrderModel order;

    @Id
    @GeneratedValue
    private long orderItemId;

    private long productId;
    private long quantityOrdered;
    private long quantityUnfulfilled;
    private OrderItemStatus status;

    public long getOrderItemId() {
        return orderItemId;
    }
    public void setOrderItemId(long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public OrderItemStatus getStatus() {
        return status;
    }
    public void setStatus(OrderItemStatus status) {
        this.status = status;
    }

    public long getProductId() {
        return productId;
    }
    public void setProductId(long productId) {
        this.productId = productId;
    }

    public long getQuantityOrdered() {
        return quantityOrdered;
    }
    public void setQuantityOrdered(long quantityOrdered) {
        this.quantityOrdered = quantityOrdered;
    }

    public long getQuantityUnfulfilled() {
        return quantityUnfulfilled;
    }
    public void setQuantityUnfulfilled(long quantityUnfulfilled) {
        this.quantityUnfulfilled = quantityUnfulfilled;
    }
}
