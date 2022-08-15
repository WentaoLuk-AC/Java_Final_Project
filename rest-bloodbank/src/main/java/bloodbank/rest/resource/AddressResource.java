/**
 * File: PersonResource.java Course materials (22S) CST 8277\

 * 

 *
 * @author Teddy Yap
 * @author Shariar (Shawn) Emami
 * @author (original) Mike Norman
 * 
 * Updated by:  Group 2
 *   041004996, Jenya, Pribylov (as from ACSIS)
 *   040923573, Jacob, Crocker (as from ACSIS)
 *   041013211, Jade, Mak (as from ACSIS)
 *   040991469, Wentao, Lu (as from ACSIS)
 */


package bloodbank.rest.resource;

import static bloodbank.utility.MyConstants.CUSTOMER_ADDRESS_SUBRESOURCE_NAME;
import static bloodbank.utility.MyConstants.ADMIN_ROLE;
import static bloodbank.utility.MyConstants.RESOURCE_PATH_ID_ELEMENT;
import static bloodbank.utility.MyConstants.RESOURCE_PATH_ID_PATH;
import static bloodbank.utility.MyConstants.USER_ROLE;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.security.enterprise.SecurityContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import bloodbank.ejb.AddressService;
import bloodbank.entity.Address;

@Path(CUSTOMER_ADDRESS_SUBRESOURCE_NAME)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AddressResource {
	private static final Logger LOG = LogManager.getLogger();

	@EJB
	protected AddressService service;

	@Inject
	protected SecurityContext sc;

	@GET
    @RolesAllowed({ADMIN_ROLE})
	public Response getAddress() {
		LOG.debug("retrieving all addresses ...");
		List<Address> addresses = service.getAllAddresses();
		Response response = Response.ok(addresses).build();
		return response;
	}

	@GET
	@RolesAllowed({ADMIN_ROLE, USER_ROLE})
	@Path(RESOURCE_PATH_ID_PATH)
	public Response getAddressById(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id) {
		LOG.debug("try to retrieve specific address " + id);
		Response response;
		try {
			Address address = service.getAddressById(id);
			if (address == null) {
				response = Response.status(Status.NOT_FOUND).build();
			} else {
				response = Response.ok(address).build();
			}

		} catch (Exception e) {
			e.printStackTrace();
			response = Response.status(Status.NOT_FOUND).build();
		}
		return response;
	}

	@POST
	@RolesAllowed({ADMIN_ROLE})
	public Response addAddress(Address newAddress) {
		LOG.debug("Adding a new blood address = {}", newAddress);
		Address newAddressWithIdTimestamps = service.persistAddress(newAddress);
		Response response = Response.ok(newAddressWithIdTimestamps).build();
		return response;
	}

	@PUT
	@RolesAllowed({ADMIN_ROLE})
	@Path(RESOURCE_PATH_ID_PATH)
	public Response updateAddress(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id, Address updatingAddress) {
		LOG.debug("Updating a specific address with id = {}", id);
		Address updatedAddress = service.updateAddressById(id, updatingAddress);
		Response response = Response.ok(updatedAddress).build();
		return response;
	}
	
	@DELETE
	@RolesAllowed({ADMIN_ROLE})
	@Path(RESOURCE_PATH_ID_PATH)
	public Response deleteAddress(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id) {
		LOG.debug("Updating a specific address with id = {}", id);
		service.deleteAddressById(id);
		Response response = Response.ok().build();
		return response;
	}
}