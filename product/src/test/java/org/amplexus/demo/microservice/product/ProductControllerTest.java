package org.amplexus.demo.microservice.product;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.data.domain.*;
import org.springframework.http.*;

import org.junit.*;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.*;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { Main.class })
public class ProductControllerTest {

    @Test
    public void testCreateProductSuccess() {

        ObjectMapper mapper = mock(ObjectMapper.class);
        ProductValidator validator = mock(ProductValidator.class);
        ProductRepo repo = mock(ProductRepo.class);
        ProductModel dummyModel = mock(ProductModel.class);
        when(repo.save(any(ProductModel.class))).thenReturn(dummyModel);
        ProductController controller = new ProductController(repo, validator, mapper);

        ProductModel model = new ProductModel();
        model.setProductId("test");
        model.setProductName("Joe Blogg");
        model.setUnitCost(0.99);
        model.setUnitPrice(1.99);

        ProductModel createdModel = controller.create(model);

        Assert.assertEquals(createdModel, dummyModel);
        verify(repo, times(1)).save(model);
    }

    @Test
    public void testFindAllProductsSuccess() {

        ObjectMapper mapper = mock(ObjectMapper.class);
        ProductValidator validator = mock(ProductValidator.class);
        ProductRepo repo = mock(ProductRepo.class);

        ProductController controller = new ProductController(repo, validator, mapper);

        Page page = mock(Page.class);
        when(repo.findAll(any(PageRequest.class))).thenReturn(page);
        ArrayList iterable = mock(ArrayList.class);
        when(page.getContent()).thenReturn(iterable);

        int pageNumber = 1;
        int count = 2;
        Sort.Direction direction = Sort.Direction.ASC;
        String sortProperty = "productName";

        Iterable i = controller.findAll(pageNumber, count, direction, sortProperty);
        verify(repo, times(1)).findAll(any(PageRequest.class));
        Assert.assertEquals(iterable, i);
    }

    @Test
    public void testFindProductSuccess() {
    }

    @Test
    public void testUpdateProductSuccess() {
    }

    @Test
    public void testDeleteProductSuccess() {
    }
}
