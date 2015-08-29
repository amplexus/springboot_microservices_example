package org.amplexus.demo.microservice.customer;

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
public class CustomerControllerTest {

    @Test
    public void testCreateCustomerSuccess() {

        CustomerRepo repo = mock(CustomerRepo.class);
        CustomerModel dummyModel = mock(CustomerModel.class);
        when(repo.save(any(CustomerModel.class))).thenReturn(dummyModel);
        CustomerController controller = new CustomerController(repo);

        CustomerModel model = new CustomerModel();
        model.setCustomerId("test");
        model.setCustomerName("Joe Blogg");

        CustomerModel createdModel = controller.create(model);

        Assert.assertEquals(createdModel, dummyModel);
        verify(repo, times(1)).save(model);
    }

    @Test
    public void testFindAllCustomersSuccess() {

        CustomerRepo repo = mock(CustomerRepo.class);
        Iterable dummyIterable = mock(Iterable.class);
        when(repo.findAll()).thenReturn(dummyIterable);
        CustomerController controller = new CustomerController(repo);

        Iterable i = controller.findAll();
        verify(repo, times(1)).findAll();
        Assert.assertEquals(dummyIterable, i);
    }
}
