package org.amplexus.demo.microservice.customer;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepo extends CrudRepository <CustomerModel, String> {
}
