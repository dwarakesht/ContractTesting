package com.rahulshettyacademy;

import org.json.JSONException;
import org.junit.Assert;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.tests.controller.Library;

@SpringBootTest
public class testsIT {

	//mvn test
	//TestRestTemplate Rest Assured
	@org.junit.jupiter.api.Test
	public void getAuthorNameBooksTest() throws JSONException
	{
		String expected= "[\r\n" + 
				"    {\r\n" + 
				"        \"student_name\": \"Stephen\",\r\n" + 
				"        \"id\": \"abcd4\",\r\n" + 
				"        \"Section\": \"A\",\r\n" + 
				"        \"roll_no\": 4,\r\n" + 
				"        \"Class\": \"10\"\r\n" + 
				"    },\r\n" + 
				"    {\r\n" + 
				"        \"Student_name\": \"Saunders\",\r\n" + 
				"        \"id\": \"fdsefr343\",\r\n" + 
				"        \"Section\": \"B\",\r\n" + 
				"        \"roll_no\": 2,\r\n" + 
				"        \"Class\": \"10\"\r\n" + 
				"    }\r\n" + 
				"]";
		TestRestTemplate restTemplate =new TestRestTemplate();
	ResponseEntity<String>	response =restTemplate.getForEntity("http://localhost:8080/getStudent/class?student_name=Stephen", String.class);
	System.out.println(response.getStatusCode());
	System.out.println(response.getBody());
	JSONAssert.assertEquals(expected, response.getBody(), false);
	
		
	}
	
	@Test
	public void addStudentIntegrationTest()
	{
		TestRestTemplate restTemplate =new TestRestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<Library> request = new HttpEntity<Library>(buildLibrary(),headers);
		ResponseEntity<String>	response =	restTemplate.postForEntity("http://localhost:8080/addStudent", request, String.class);
		Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());
		Assert.assertEquals(buildLibrary().getId(),response.getHeaders().get("unique").get(0));
			
	}

	
	
}
