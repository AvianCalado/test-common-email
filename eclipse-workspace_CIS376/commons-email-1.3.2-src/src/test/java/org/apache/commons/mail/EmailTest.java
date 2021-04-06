package org.apache.commons.mail;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class EmailTest {

	private static final String[] TEST_EMAILS = { "ad@BVX.com", "aabcd@c.org", "GoDaddy@y.org" };
	
	private EmailConcrete email;
	
	@Before
	public void setUpEmaiLTest() throws Exception {
		email = new EmailConcrete();
		
	}
	
	@After
	public void  tearDownEmailTest() throws Exception {
		
	}
	
	//Test 1.0- Email  addBcc(String... emails)
	@Test
	public void testAddBcc() throws Exception {
		
		email.addBcc(TEST_EMAILS);
		
		assertEquals(3,email.getBccAddresses().size());
		
	}
	
	//Test 2.0- Email  addCc(String email)
	@Test 
	public void testAddCC() throws Exception {
		email.addBcc(TEST_EMAILS);
		String testAddCCEmail = "Aviaan@c.org";
		email.addCc(testAddCCEmail);
		
		assertEquals(1,email.getCcAddresses().size());
		
	}
	
	//Test 3.0- void     addHeader(String name, String value)
	@Test
	public void testAddHeader() throws Exception {
		final String testName = "Avian";
		final String testValue = "Hiya";
		
		
		email.addHeader(testName, testValue);
		
		assertEquals(1,email.headers.size());
	}
	
	//Test 3.1- void     addHeader(String name, String value)
	// will expect an error to be thrown
	@Test ( expected = IllegalArgumentException.class)
	public void testAddHeaderEmptyName() throws Exception {
		
		final String testValue = "Hiya";
		
		email.addHeader("", testValue);
		
		String expectedMessage = "name can not be null or empty";

		assertTrue(expectedMessage, true);
		
	}
	
	//Test 3.2- void     addHeader(String name, String value)
	@Test ( expected = IllegalArgumentException.class)
	public void testAddHeaderEmptyValue() throws Exception {
		final String testName = "Avian";
		
		email.addHeader(testName, "");

		String expectedMessage = "name can not be null or empty";
		
		assertTrue(expectedMessage, true);
	}
	
	//Test 4.0 - Email  addReplyTo(String email, String name)
	@Test 
	public void testAddReplyTo() throws Exception {
		
		email.addBcc(TEST_EMAILS);
		Email temp = email.addReplyTo("aabcd@c.org","Baron");
		 
		assertEquals(temp.getReplyToAddresses(),email.getReplyToAddresses());
		
	}
	
	//Test 5.0 - void     buildMimeMessage()
	// Throws an exception because connection fails.
	@Test ( expected = EmailException.class)
	public void testBuildMimeMessage() throws Exception {
		email.addBcc(TEST_EMAILS);
		
		String testAddCCEmail = "Aviaan@c.org";
		final String testName = "Avian";
		final String testValue = "Hiya";
		
		//Fill out the message fields and connections
		email.addHeader(testName, testValue);
		email.addCc(testAddCCEmail);
		email.addReplyTo("ad@BVX.com","Baron");
		email.setFrom("aabcd@c.org");
		email.addTo("GoDaddy@y.org");
		email.setSubject("Create a website");
		email.setPopBeforeSmtp(true, "ad@BVX.com", testName, testValue);
		email.setBounceAddress("GoDaddy@y.org");
		
		
		Session aSession = null;
		email.createMimeMessage(aSession);
		email.setHostName(testAddCCEmail);
		email.buildMimeMessage();

		String expectedMessage = "Connect failed";

		assertTrue(expectedMessage, true); 
		
	}
	
	
	
	  //Test 5.1 - void     buildMimeMessage()
	//Test will fail because address is required
	  @Test ( expected = EmailException.class)
	  public void testBuildMimeMessageFail() throws Exception {
			Session aSession = null;
			String testTempEmail = "Aviaan@c.org";
			
			email.createMimeMessage(aSession);
			email.setHostName(testTempEmail);
			email.buildMimeMessage();
			String expectedMessage = "From address required";

			assertTrue(expectedMessage, true);
	  }
	
	
}
