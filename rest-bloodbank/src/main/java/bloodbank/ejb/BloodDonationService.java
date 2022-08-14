package bloodbank.ejb;

import static bloodbank.utility.MyConstants.PU_NAME;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;
import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import bloodbank.entity.BloodDonation;

@Singleton
public class BloodDonationService implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LogManager.getLogger();

	@PersistenceContext(name = PU_NAME)
	protected EntityManager em;
	
	@Inject
	protected Pbkdf2PasswordHash pbAndjPasswordHash;
	
	public List<BloodDonation> getAllBloodDonations() {
	    CriteriaBuilder cb = em.getCriteriaBuilder();
	    CriteriaQuery<BloodDonation> cq = cb.createQuery(BloodDonation.class);
	    cq.select(cq.from(BloodDonation.class));
	    return em.createQuery(cq).getResultList();
	}
	
	public BloodDonation getBloodDonationById(int id) {
		return em.find(BloodDonation.class, id);
	}
	
	@Transactional
	public BloodDonation persistBloodDonation(BloodDonation newBloodDonation) {
		em.persist(newBloodDonation);
		return newBloodDonation;
	}
	
	 /**
     * this will update a blood donation
     * 
     * @param id - id of entity to update
     * @param donationWithUpdates - entity with updated information
     * @return Entity with updated information
     */
	@Transactional
	public BloodDonation updateBloodDonationById(int id, BloodDonation donationWithUpdates) {
		BloodDonation donationToBeUpdated = getBloodDonationById(id);
	    if (donationToBeUpdated != null) {
	        em.refresh(donationToBeUpdated);
	        em.merge(donationWithUpdates);
	        em.flush();
	    }
	    return donationToBeUpdated;
	}
	
	/**
     * deletes a BloodDonation by id
     * 
     * @param id - BloodDonation id to delete
     */
    @Transactional
    public void deleteBloodDonationById(int id) {
    	BloodDonation donationToBeDeleted = getBloodDonationById(id);
		if (donationToBeDeleted != null) {
			em.refresh(donationToBeDeleted);
			em.remove(donationToBeDeleted);
		}
    } 
}
