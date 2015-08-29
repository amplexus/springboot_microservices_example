package org.amplexus.demo.microservice.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductRepo repo;
    private final ProductValidator validator;
    private final ObjectMapper objectMapper;

    @Autowired
    public ProductController(ProductRepo repo, ProductValidator validator, ObjectMapper objectMapper) {
        this.repo = repo;
        this.validator = validator;
        this.objectMapper = objectMapper;
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(validator); // Register the validator so Spring knows we want to customise the default data binder for this controller
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ProductModel find(@PathVariable String id) {
        ProductModel model = repo.findOne(id);
        if (model == null) {
            throw new ProductNotFoundException();
        } else {
            return model;
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public Iterable findAll(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
                            @RequestParam(value = "count", defaultValue = "10", required = false) int count,
                            @RequestParam(value = "order", defaultValue = "ASC", required = false) Sort.Direction direction,
                            @RequestParam(value = "sort", defaultValue = "productName", required = false) String sortProperty) {
        Page result = repo.findAll(new PageRequest(page, count, new Sort(direction, sortProperty)));
        return result.getContent();
    }

    @RequestMapping(method = RequestMethod.POST)
    public ProductModel create(@RequestBody @Valid ProductModel product) {
        return repo.save(product);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public HttpEntity update(@PathVariable String id, HttpServletRequest request) throws IOException {
        ProductModel existing = find(id);
        ProductModel updated = objectMapper.readerForUpdating(existing).readValue(request.getReader());

        MutablePropertyValues propertyValues = new MutablePropertyValues();
        propertyValues.add("productId", updated.getProductId());
        propertyValues.add("productName", updated.getProductName());
        propertyValues.add("unitCost", updated.getUnitCost());
        propertyValues.add("unitPrice", updated.getUnitPrice());

        DataBinder binder = new DataBinder(updated);
        binder.addValidators(validator);
        binder.bind(propertyValues);
        binder.validate();

        if (binder.getBindingResult().hasErrors()) {
            return new ResponseEntity<>(binder.getBindingResult().getAllErrors(), HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(updated, HttpStatus.ACCEPTED);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public HttpEntity delete(@PathVariable String id) {
        ProductModel model = find(id);
        repo.delete(model);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    static class ProductNotFoundException extends RuntimeException {
    }
}
