package com.rahulshettyacademy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tests.controller.LibraryController;
import com.tests.controller.StudentsPrices;
import com.tests.controller.SpecificStudent;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslJsonArray;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;

@SpringBootTest
@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "StudentService")
public class PactConsumerTest {
	
	@Autowired
	private LibraryController libraryController;
	
	@Pact(consumer="EnrollService")
	public RequestResponsePact PactallStudentsDetailsConfig(PactDslWithProvider builder)
	{
		return builder.given("Student exists")
		.uponReceiving("getting all student details")
		.path("/allStudentDetails")
		.willRespondWith()
		.status(200)
		.body(PactDslJsonArray.arrayMinLike(3)
				.stringType("student_name")
				.stringType("section")
				.integerType("roll_no", 10)
				.stringType("class").closeObject()).toPact();
					
		
	}
	//  PactFlow
	
	// -> PactFlow Server  (contract file to server)

		@Pact(consumer="EnrollService")
		public RequestResponsePact PactallStudentDetailsPriceCheck(PactDslWithProvider builder)
		{
			return builder.given("student exist")
			.uponReceiving("getting all Student details")
			.path("/allStudentDetails")
			.willRespondWith()
			.status(200)
			.body(PactDslJsonArray.arrayMinLike(3)
					
					.integerType("price", 10)
					.closeObject()).toPact();
							
		}
		
		@Pact(consumer = "BooksCatalogue")
		public RequestResponsePact getStudentByName(PactDslWithProvider builder)
		
		{
			return builder.given("Student Stephen exist")
			.uponReceiving("Get the Stephen Student details")
			.path("/getStudentByName/Stephen")
			.willRespondWith()
			.status(200)
			.body(new PactDslJsonBody()
					.integerType("price",44)
					.stringType("category","mobile")).toPact();
			
		}
	
	
	

	@Test
	@PactTestFor(pactMethod="PactallStudentsDetailsPriceCheck",port = "9999")
	
	public void testAllStudentsSum(MockServer mockServer) throws JsonMappingException, JsonProcessingException
	
	{
		
		String expectedJson ="{\"booksPrice\":250,\"StudentsPrice\":30}";
		libraryController.setBaseUrl(mockServer.getUrl());
		
		StudentsPrices StudentsPrices = libraryController.getStudentPrices();
		ObjectMapper obj = new ObjectMapper();
		String jsonActual = obj.writeValueAsString(StudentsPrices);
		
		Assertions.assertEquals(expectedJson, jsonActual);
	
		
	}
	
	@Test
	@PactTestFor(pactMethod="getStudentByName",port = "9999")
	
	public void testByStudentName(MockServer mockServer) throws JsonMappingException, JsonProcessingException
	
	{
		
		libraryController.setBaseUrl(mockServer.getUrl());
		
		String expectedJson = "{\"Student\":{\"book_name\":\"Stephen\",\"id\":\"fdsefr343\",\"isbn\":\"fdsefr3\",\"aisle\":43,\"author\":\"Rahul Shetty\"},\"price\":44,\"category\":\"mobile\"}";
		
		SpecificStudent specificStudent =libraryController.getStudentFullDetails("Stephen");
		
		ObjectMapper obj = new ObjectMapper();
		String jsonActual = obj.writeValueAsString(specificStudent);
		
		Assertions.assertEquals(expectedJson, jsonActual);
		
		
	}
	@Pact(consumer = "BooksCatalogue")
	public RequestResponsePact getStudentByNameNotExist(PactDslWithProvider builder)
	
	{
		return builder.given("Student Stephen does not exist","name","Stephen")
		.uponReceiving("Stephen Student Does not exist")
		.path("/getStudentByName/Stephen")
		.willRespondWith()
		.status(404)
		.toPact();
		
	}
	
	@Test
	@PactTestFor(pactMethod="getStudentByNameNotExist",port = "9999")
	
	public void testByStudentNameNotExist(MockServer mockServer) throws JsonMappingException, JsonProcessingException
	
	{
		
		libraryController.setBaseUrl(mockServer.getUrl());
		
		String expectedJson = "{\"Student\":{\"book_name\":\"Stephen\",\"id\":\"fdsefr343\",\"isbn\":\"fdsefr3\",\"aisle\":43,\"author\":\"Rahul Shetty\"},\"msg\":\"StephenCategory and price details are not available at this time\"}";
		
		SpecificStudent specificStudent =libraryController.getStudentFullDetails("Stephen");
		
		ObjectMapper obj = new ObjectMapper();
		String jsonActual = obj.writeValueAsString(specificStudent);
		
		Assertions.assertEquals(expectedJson, jsonActual);
		
		
	}

	
	
	
	
	
	
	
	
	
	

	
	
}
