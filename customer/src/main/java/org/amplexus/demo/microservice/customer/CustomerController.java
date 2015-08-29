package org.amplexus.demo.microservice.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerRepo repository;

    @Autowired
    public CustomerController(CustomerRepo repository) {
        this.repository = repository;
    }
    @RequestMapping(method = RequestMethod.GET)
    public Iterable findAll() {
        return repository.findAll();
    }
    @RequestMapping(method = RequestMethod.POST)
    public CustomerModel create(@RequestBody CustomerModel customer) {
        return repository.save(customer);
    }
}
