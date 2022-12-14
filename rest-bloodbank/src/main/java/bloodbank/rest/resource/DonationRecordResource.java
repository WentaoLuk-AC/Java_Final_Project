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
import static bloodbank.utility.MyConstants.DONATION_RECORD_RESOURCE_NAME;
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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import bloodbank.ejb.DonationRecordService;
import bloodbank.entity.DonationRecord;

@Path(DONATION_RECORD_RESOURCE_NAME)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DonationRecordResource {
	private static final Logger LOG = LogManager.getLogger();

    @EJB
    protected DonationRecordService service;

    @Inject
    protected SecurityContext sc;

    @GET
    @RolesAllowed({ADMIN_ROLE})
    public Response getDonationRecords() {
        LOG.debug("retrieving all donation records ...");
        List<DonationRecord> donationRecords = service.getAllDonationRecords();
        Response response = Response.ok(donationRecords).build();
        return response;
    }

    @GET
    @RolesAllowed({ADMIN_ROLE, USER_ROLE})
    @Path(RESOURCE_PATH_ID_PATH)
    public Response getDonationRecordById(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id) {
        LOG.debug("try to retrieve specific donation record " + id);
        DonationRecord donationRecord = service.getDonationRecordById(id);
        Response response = Response.ok(donationRecord).build();
        return response;
    }

    @POST
    @RolesAllowed({ADMIN_ROLE})
    public Response addDonationRecord(DonationRecord newDonationRecord) {
    	LOG.debug("Adding a new donation record = {}", newDonationRecord);
    	DonationRecord newDonationRecordWithIdTimestamps = service.persistDonationRecord(newDonationRecord);
        Response response = Response.ok(newDonationRecordWithIdTimestamps).build();
        return response;
    }

    @PUT
    @RolesAllowed({ADMIN_ROLE})
    @Path(RESOURCE_PATH_ID_PATH)
    public Response updateDonationRecord(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id, DonationRecord updatingDonationRecord) {
        LOG.debug("Updating a specific donation record with id = {}", id);
        DonationRecord updatedDonationRecord = service.updateDonationRecordById(id, updatingDonationRecord);
        Response response = Response.ok(updatedDonationRecord).build();
        return response;
    }
    
	@DELETE
	@RolesAllowed({ADMIN_ROLE})
	@Path(RESOURCE_PATH_ID_PATH)
	public Response deleteDonationRecord(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id) {
		LOG.debug("Deleting a specific donation record with id = {}", id);
		service.deleteDonationRecordById(id);
		Response response = Response.ok().build();
		return response;
	}
    

}