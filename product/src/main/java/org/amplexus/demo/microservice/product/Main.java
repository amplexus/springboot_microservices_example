package org.amplexus.demo.microservice.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.h2.server.web.WebServlet;

@ComponentScan
@EnableAutoConfiguration // Springboot will perform classpath scanning to ascertain what capabilities the microservices needs to have
public class Main {
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Main.class);
	// ProductModel product = new ProductModel();
	// product.setProductId("c680494");
	// product.setProductName("Craig Jackson");
	// product.setUnitCost(1.50);
	// product.setUnitPrice(2.50);
	// ProductRepo repo = ctx.getBean(ProductRepo.class);
	// repo.save(product);
	// for (ProductModel model : repo.findAll()) {
	//     System.out.println(model.getProductId());
	// }
    }

    // Enable the H2 DB /console -- not sure if this can be enabled via application.properties instead...
    @Bean
    public ServletRegistrationBean h2servletRegistration() {
        ServletRegistrationBean registration = new ServletRegistrationBean(new WebServlet());
        registration.addUrlMappings("/console/*");
        return registration;
    }
}
