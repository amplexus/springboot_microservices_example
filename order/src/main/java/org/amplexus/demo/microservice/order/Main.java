package org.amplexus.demo.microservice.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@EnableAutoConfiguration // Springboot will perform classpath scanning to ascertain what capabilities the microservices needs to have
public class Main {
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Main.class);
	// OrderModel order = new OrderModel();
	// order.setOrderId("c680494");
	// order.setOrderName("Craig Jackson");
	// OrderRepo repo = ctx.getBean(OrderRepo.class);
	// repo.save(order);
	// for (OrderModel model : repo.findAll()) {
	//     System.out.println(model.getOrderId());
	// }
    }
}
