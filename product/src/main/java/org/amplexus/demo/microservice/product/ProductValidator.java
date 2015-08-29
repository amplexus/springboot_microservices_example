package org.amplexus.demo.microservice.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.*;

@Component
public class ProductValidator implements Validator {

    private final ProductRepo repo;

    @Autowired
    public ProductValidator(ProductRepo repo) {
        this.repo = repo;
    }

    @Override
    public boolean supports(Class<?>clazz) {
        return ProductModel.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ProductModel product = (ProductModel)target;
        if (!repo.exists(product.getProductId())) {
            errors.rejectValue("productId", "inventory.productId.invalid", "Product ID is invalid");
        }
    }
}
