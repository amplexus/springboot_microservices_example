package org.amplexus.demo.microservice.product;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.PagingAndSortingRepository;

@Repository
public interface ProductRepo extends PagingAndSortingRepository <ProductModel, String> {
    @Query("select p from ProductModel p where UPPER(p.productName) like UPPER(?1)")
    List search(String term);
}
