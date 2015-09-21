package org.amplexus.demo.microservice.customer;

import java.io.*;
import java.net.*;
import java.util.Arrays;

import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import org.springframework.boot.test.*;
import org.springframework.http.*;
// import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class CustomerServiceTest {
 
    private String host;
    private int port;

    @Before
    public void setup() {
        host = System.getProperty("test.host", "localhost");
        port = Integer.parseInt(System.getProperty("test.port", "8080"));
    }

    @Test
    public void testGetWithNoCustomersShouldReturnEmptySet() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        ResponseEntity<String> response = new TestRestTemplate().exchange("http://" + this.host + ":" + this.port + "/customers", HttpMethod.GET, new HttpEntity<Void>(headers), String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
