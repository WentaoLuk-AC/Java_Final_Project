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
 * 
 * 
 */

package bloodbank.rest.resource;

import static bloodbank.utility.MyConstants.ADMIN_ROLE;
import static bloodbank.utility.MyConstants.PHONE_RESOURCE_NAME;
import static bloodbank.utility.MyConstants.RESOURCE_PATH_ID_ELEMENT;
import static bloodbank.utility.MyConstants.RESOURCE_PATH_ID_PATH;
import static bloodbank.utility.MyConstants.USER_ROLE;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Singleton;
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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import bloodbank.ejb.PhoneService;
import bloodbank.entity.Phone;

@Path(PHONE_RESOURCE_NAME)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PhoneResource {
	private static final Logger LOG = LogManager.getLogger();

    @EJB
    protected PhoneService service;

    @Inject
    protected SecurityContext sc;

    @GET
    @RolesAllowed({ADMIN_ROLE})
    public Response getPhones() {
        LOG.debug("retrieving all phones ...");
        List<Phone> phones = service.getAllPhones();
        Response response = Response.ok(phones).build();
        return response;
    }

    @GET
    @RolesAllowed({ADMIN_ROLE})
    @Path(RESOURCE_PATH_ID_PATH)
    public Response getPhoneById(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id) {
        LOG.debug("try to retrieve specific phone " + id);
        Phone phone = service.getPhoneById(id);
        Response response = Response.ok(phone).build();
        return response;
    }

    @POST
    @RolesAllowed({ADMIN_ROLE})
    public Response addPhone(Phone newPhone) {
    	LOG.debug("Adding a new phone = {}", newPhone);
    	Phone newPhoneWithIdTimestamps = service.persistPhone(newPhone);
        Response response = Response.ok(newPhoneWithIdTimestamps).build();
        return response;
    }

    @PUT
    @RolesAllowed({ADMIN_ROLE})
    @Path(RESOURCE_PATH_ID_PATH)
    public Response updatePhone(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id, Phone updatingPhone) {
        LOG.debug("Updating a specific phone with id = {}", id);
        Phone updatedPhone = service.updatePhoneById(id, updatingPhone);
        Response response = Response.ok(updatedPhone).build();
        return response;
    }
    
	@DELETE
	@RolesAllowed({ADMIN_ROLE})
	@Path(RESOURCE_PATH_ID_PATH)
	public Response deletePhone(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id) {
		LOG.debug("Deleting a specific phone with id = {}", id);
		service.deletePhoneById(id);
		Response response = Response.ok().build();
		return response;
	}

}