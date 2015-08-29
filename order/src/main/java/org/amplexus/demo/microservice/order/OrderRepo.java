package org.amplexus.demo.microservice.order;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepo extends CrudRepository <OrderModel, String> {
}
