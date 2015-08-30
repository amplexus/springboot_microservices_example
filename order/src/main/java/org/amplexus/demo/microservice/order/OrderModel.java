package org.amplexus.demo.microservice.order;

import java.util.HashSet;
import java.util.Set;
import java.util.Date;
import javax.persistence.*;

@Entity
public class OrderModel {

    public enum OrderStatus { PENDING, OPEN, CLOSED } 

    @Id
    @GeneratedValue
    private long orderId;

    private long customerId;
    private Date creationDate;
    private Date modificationDate;
    private Date completionDate;
    private OrderStatus status;

    @OneToMany(mappedBy = "order")
    private Set<OrderItemModel> orderItems = new HashSet<OrderItemModel>();

    public long getOrderId() {
        return orderId;
    }
    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getCustomerId() {
        return customerId;
    }
    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public OrderStatus getStatus() {
        return status;
    }
    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Date getCreationDate() {
        return creationDate;
    }
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getModificationDate() {
        return modificationDate;
    }
    public void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
    }

    public Set<OrderItemModel> getOrderItems() {
        return orderItems;
    }
}
