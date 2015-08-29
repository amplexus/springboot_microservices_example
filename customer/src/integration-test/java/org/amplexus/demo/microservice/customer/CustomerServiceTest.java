package org.amplexus.demo.microservice.customer;

import java.io.*;
import java.net.*;
import java.util.Arrays;

import org.junit.*;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Main.class)
@WebAppConfiguration
@IntegrationTest({"server.port=0"}) // Start up the server on a random port, which can be discovered with a @Value("${local.server.port}") annotaded int variable
public class CustomerServiceTest {
 
    private MockMvc mockMvc;

    @Value("${local.server.port}")
    private int port;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testGetWithNoCustomersShouldReturnEmptySet() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        ResponseEntity<String> response = new TestRestTemplate().exchange("http://localhost:" + this.port + "/customers", HttpMethod.GET, new HttpEntity<Void>(headers), String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertThat(response.getBody(), equalTo("[]"));
    }

    @Test
    public void testGetWithNoCustomersShouldReturnEmptySetV2() throws Exception {
        mockMvc.perform(get("/customers"))
            .andExpect(status().isOk())
            // .andExpect(jsonPath("$", hasSize(0)))
            ;
    }

    @Test
    public void testGetWithInvalidUriShouldFail() throws Exception {
        mockMvc.perform(get("/customers2"))
            .andExpect(status().isNotFound());// http://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/test/web/servlet/result/StatusResultMatchers.html
    }
}
