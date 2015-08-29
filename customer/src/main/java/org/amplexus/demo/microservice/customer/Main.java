package org.amplexus.demo.microservice.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@EnableAutoConfiguration // Springboot will perform classpath scanning to ascertain what capabilities the microservices needs to have
public class Main {
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Main.class);
	// CustomerModel customer = new CustomerModel();
	// customer.setCustomerId("c680494");
	// customer.setCustomerName("Craig Jackson");
	// CustomerRepo repo = ctx.getBean(CustomerRepo.class);
	// repo.save(customer);
	// for (CustomerModel model : repo.findAll()) {
	//     System.out.println(model.getCustomerId());
	// }
    }
}
