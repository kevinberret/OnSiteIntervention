package fi.haagahelia.serverprogramming.OnSiteIntervention;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.json.simple.JSONObject;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class WebLayerTests {
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void testAuthentication() throws Exception {
		// Testing authentication with correct credentials
		this.mockMvc.perform(post("/api/login")
			.content("{\"username\":\"admin\", \"password\":\"admin\"}"))
			.andDo(print()).andExpect(status().isOk());

	    // Testing authentication with wrong credentials
        this.mockMvc.perform(post("/api/login")
    		.content("{\"username\":\"admin\", \"password\":\"12345987254\"}"))
    		.andDo(print()).andExpect(status().is4xxClientError());
	}
	
	@Test
	public void testGetCustomers() throws Exception {
		// first get token
		MvcResult result = this.mockMvc.perform(post("/api/login")
			.content("{\"username\":\"admin\", \"password\":\"admin\"}"))
			.andDo(print()).andExpect(status().isOk()).andReturn();
		
		String token = result.getResponse().getHeader("Authorization");
		
		assertThat(token).isNotNull();

	    // try to get data with token in header
        this.mockMvc.perform(get("/api/customers")
    		.header("Authorization", token))
    		.andDo(print()).andExpect(status().isOk());
        
        // try to get data without token in header
        this.mockMvc.perform(get("/api/customers"))
    		.andDo(print()).andExpect(status().is4xxClientError());
        
    	// try to get data with wrong token in header
        this.mockMvc.perform(get("/api/customers")
			.header("Authorization", "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbi50ZXN0IiwiZXhwIjoxNTQzMjQ3NjA5fQ.cMR5t4UfkAHykUG7x10E3KMGtx4nD8ng9-3T55zrzQ0O0y32dXZxUtxMcCU11PqcWDyb8VIOWFn-JqhyOgLVOg"))
    		.andDo(print()).andExpect(status().is5xxServerError());
	}
	
	@Test
	public void testPostCustomer() throws Exception {
		JSONObject customer = new JSONObject();
		JSONObject address = new JSONObject();
		address.put("city", "London");
		address.put("street", "Main Street");
		address.put("zip", "00520");
		address.put("number", "222");
		customer.put("firstname", "Sarah");
		customer.put("lastname", "Croche");
		customer.put("lastname", "Croche");
		customer.put("address", address);
		
		// first get token
		MvcResult result = this.mockMvc.perform(post("/api/login")
			.content("{\"username\":\"admin\", \"password\":\"admin\"}"))
			.andDo(print()).andExpect(status().isOk()).andReturn();
		
		String token = result.getResponse().getHeader("Authorization");
		
		assertThat(token).isNotNull();

	    // try to post data with token in header
        this.mockMvc.perform(post("/api/customers")
    		.header("Authorization", token)
    		.header("Content-Type", "application/json")
    		.content(customer.toString()))
    		.andDo(print()).andExpect(status().isOk());
        
        // try to post data with wrong token in header
        this.mockMvc.perform(post("/api/customers")
    		.header("Authorization", "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbi50ZXN0IiwiZXhwIjoxNTQzMjQ3NjA5fQ.cMR5t4UfkAHykUG7x10E3KMGtx4nD8ng9-3T55zrzQ0O0y32dXZxUtxMcCU11PqcWDyb8VIOWFn-JqhyOgLVOg")
    		.header("Content-Type", "application/json")
    		.content(customer.toString()))
    		.andDo(print()).andExpect(status().is5xxServerError());
        
        // try to post data without token in header
        this.mockMvc.perform(post("/api/customers")
    		.header("Content-Type", "application/json")
    		.content(customer.toString()))
    		.andDo(print()).andExpect(status().is4xxClientError());
	}
}