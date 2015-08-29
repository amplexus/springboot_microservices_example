package org.amplexus.demo.microservice.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


import org.junit.*;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { Main.class })
public class OrderControllerTest {
    @Test
    public void testCreateOrderSuccess() {

        OrderRepo repo = mock(OrderRepo.class);
        OrderModel dummyModel = mock(OrderModel.class);
        when(repo.save(any(OrderModel.class))).thenReturn(dummyModel);
        OrderController controller = new OrderController(repo);

        OrderModel model = new OrderModel();
        model.setOrderId("test");
        model.setOrderName("Joe Blogg");

        OrderModel createdModel = controller.create(model);

        Assert.assertEquals(createdModel, dummyModel);
        verify(repo, times(1)).save(model);
    }

    @Test
    public void testFindAllOrdersSuccess() {

        OrderRepo repo = mock(OrderRepo.class);
        Iterable dummyIterable = mock(Iterable.class);
        when(repo.findAll()).thenReturn(dummyIterable);
        OrderController controller = new OrderController(repo);

        Iterable i = controller.findAll();
        verify(repo, times(1)).findAll();
        Assert.assertEquals(dummyIterable, i);
    }
}


