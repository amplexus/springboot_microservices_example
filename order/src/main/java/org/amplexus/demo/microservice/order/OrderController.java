package org.amplexus.demo.microservice.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderRepo repository;
    @Autowired
    public OrderController(OrderRepo repository) {
        this.repository = repository;
    }
    @RequestMapping(method = RequestMethod.GET)
    public Iterable findAll() {
        return repository.findAll();
    }
    @RequestMapping(method = RequestMethod.POST)
    public OrderModel create(@RequestBody OrderModel order) {
        return repository.save(order);
    }
}
