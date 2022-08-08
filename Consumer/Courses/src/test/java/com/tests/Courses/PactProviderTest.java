package com.tests.Courses;

import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import com.rahulshettyacademy.controller.AllCourseData;
import com.rahulshettyacademy.repository.CoursesRepository;

import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.StateChangeAction;
import au.com.dius.pact.provider.junitsupport.loader.PactBroker;
import au.com.dius.pact.provider.junitsupport.loader.PactFolder;
import au.com.dius.pact.provider.junitsupport.loader.PactBrokerAuth;



@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Provider("StudentService")
@PactFolder("pacts")//url


public class PactProviderTest {
	
	@LocalServerPort
	public int port;
	
	@Autowired
	  CoursesRepository repository;

	
	
	@TestTemplate
	@ExtendWith(PactVerificationInvocationContextProvider.class)
	public void pactVerificationTest(PactVerificationContext context)
	{
		context.verifyInteraction();
		
	}
	
	@BeforeEach
	public void setup(PactVerificationContext context)
	{
		
		context.setTarget(new HttpTestTarget("localhost",port));
	}
	
	@State(value= "Enrolling new student to the course",action= StateChangeAction.SETUP)
	public void enrollNewStudent()
	
	{
		
	}
	
	@State(value= "Enrolling existing student to the course",action= StateChangeAction.TEARDOWN)
	public void coursesExistTearDown()
	
	{
		
	}
	
	@State(value= "Retreive student details",action= StateChangeAction.SETUP)
	public void getStudentDetails()
	
	{
		
	}
	
	@State(value= "Add new student",action= StateChangeAction.TEARDOWN)
	public void addNewStudent()
	
	{
		
	}
	
	
	}
	
}
