/**
 * @Name TestDonationRecord.java
 * @author Wentao Lu 040991469
 * 
 */


package bloodbank;


import static bloodbank.utility.MyConstants.APPLICATION_API_VERSION;
import static bloodbank.utility.MyConstants.APPLICATION_CONTEXT_ROOT;
import static bloodbank.utility.MyConstants.DEFAULT_ADMIN_USER;
import static bloodbank.utility.MyConstants.DEFAULT_ADMIN_USER_PASSWORD;
import static bloodbank.utility.MyConstants.DEFAULT_USER;
import static bloodbank.utility.MyConstants.DEFAULT_USER_PASSWORD;
import static bloodbank.utility.MyConstants.DONATION_RECORD_RESOURCE_NAME;
import static bloodbank.utility.MyConstants.PERSON_RESOURCE_NAME;
import static bloodbank.utility.MyConstants.CUSTOMER_ADDRESS_SUBRESOURCE_NAME;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsEmptyCollection.empty;

import java.lang.invoke.MethodHandles;
import java.net.URI;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.logging.LoggingFeature;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import bloodbank.entity.Address;
import bloodbank.entity.DonationRecord;
import bloodbank.entity.Person;


@TestMethodOrder ( MethodOrderer.MethodName.class)
public class TestAddress {
	
	private static final Class<?> _thisClaz = MethodHandles.lookup().lookupClass();
	private static final Logger logger = LogManager.getLogger(_thisClaz);
	
	static final String HTTP_SCHEMA = "http";
    static final String HOST = "localhost";
    static final int PORT = 8080;

    // test fixture(s)
    static URI uri;
    static HttpAuthenticationFeature adminAuth;
    static HttpAuthenticationFeature userAuth;
    
    @BeforeAll
    public static void oneTimeSetUp() throws Exception {
        logger.debug("oneTimeSetUp");
        uri = UriBuilder
            .fromUri(APPLICATION_CONTEXT_ROOT + APPLICATION_API_VERSION)
            .scheme(HTTP_SCHEMA)
            .host(HOST)
            .port(PORT)
            .build();
        adminAuth = HttpAuthenticationFeature.basic(DEFAULT_ADMIN_USER, DEFAULT_ADMIN_USER_PASSWORD);
        userAuth = HttpAuthenticationFeature.basic(DEFAULT_USER, DEFAULT_USER_PASSWORD);
    }
    
    protected WebTarget webTarget;
    @BeforeEach
    public void setUp() {
        Client client = ClientBuilder.newClient(
            new ClientConfig().register(MyObjectMapperProvider.class).register(new LoggingFeature()));
        webTarget = client.target(uri);
    }
    
    @Test
    public void test01_admin_read_all_addresses() throws JsonMappingException, JsonProcessingException {
    	
    	Response response = webTarget
    			.register(adminAuth)
				.path(CUSTOMER_ADDRESS_SUBRESOURCE_NAME)
				.request()
				.get();
		assertThat(response.getStatus(), is(200));
		List<Address> addrs = response.readEntity(new GenericType<List<Address>>() {
		});
		assertThat(addrs, is(not(empty())));
    }
    
    @Test
    public void test02_user_read_all_addresses() throws JsonMappingException, JsonProcessingException {
    	
    	Response response = webTarget
    			.register(userAuth)
				.path(CUSTOMER_ADDRESS_SUBRESOURCE_NAME)
				.request()
				.get();
		assertThat(response.getStatus(), is(403));

    }
    
    @Test
    public void test03_admin_read_one_addresses() throws JsonMappingException, JsonProcessingException {
    	
    	int addressId = 1;
    	
    	Response response = webTarget
    			.register(adminAuth)
				.path(CUSTOMER_ADDRESS_SUBRESOURCE_NAME + "/" + addressId)
				.request()
				.get();
		assertThat(response.getStatus(), is(200));
    }
    
    @Test
    public void test04_user_read_one_addresses() throws JsonMappingException, JsonProcessingException {
    	int addressId = 1;

    	Response response = webTarget
    			.register(userAuth)
				.path(CUSTOMER_ADDRESS_SUBRESOURCE_NAME + "/" + addressId)
				.request()
				.get();
		assertThat(response.getStatus(), is(200));

    }

    
    

}



















