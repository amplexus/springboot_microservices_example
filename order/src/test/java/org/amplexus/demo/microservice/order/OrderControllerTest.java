package org.amplexus.demo.microservice.order;

import java.util.*;

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

        OrderItemModel orderItem = new OrderItemModel();
        orderItem.setProductId(100);
        orderItem.setQuantityOrdered(10);
        orderItem.setQuantityUnfulfilled(5);
        OrderItemModel.OrderItemStatus itemStatus = OrderItemModel.OrderItemStatus.BACKORDER;
        orderItem.setStatus(itemStatus);

        OrderModel order = new OrderModel();
        Date orderCreationDate = new Date();
        order.setCreationDate(orderCreationDate);
        order.setModificationDate(orderCreationDate);
        OrderModel.OrderStatus orderStatus = OrderModel.OrderStatus.PENDING;
        order.setStatus(orderStatus);
        order.getOrderItems().add(orderItem);

        when(repo.save(any(OrderModel.class))).thenReturn(order);
        OrderController controller = new OrderController(repo);

        OrderModel createdOrder = controller.create(order);

        Assert.assertEquals(createdOrder, order);
        verify(repo, times(1)).save(order);
    }

    @Test
    public void testFindAllOrdersSuccess() {

        //OrderRepo repo = mock(OrderRepo.class);
        //Iterable dummyIterable = mock(Iterable.class);
        //when(repo.findAll()).thenReturn(dummyIterable);
        //OrderController controller = new OrderController(repo);

        //Iterable i = controller.findAll();
        //verify(repo, times(1)).findAll();
        //Assert.assertEquals(dummyIterable, i);
    }
}
