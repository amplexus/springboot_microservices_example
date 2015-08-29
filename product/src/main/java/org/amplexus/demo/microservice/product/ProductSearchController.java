package org.amplexus.demo.microservice.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/product_search")
public class ProductSearchController {

    private final ProductRepo repository;

    @Autowired
    public ProductSearchController(ProductRepo repository) {
        this.repository = repository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List search(@RequestParam("q") String queryTerm) {
        List productModelList = repository.search("%"+queryTerm+"%");
        return productModelList == null ? new ArrayList<>() : productModelList;
    }
}

