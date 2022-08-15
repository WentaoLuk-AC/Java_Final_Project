/**
 * 
 * Updated by:  Group 2
 *  041004996, Jenya, Pribylov (as from ACSIS)
 *   040923573, Jacob, Crocker (as from ACSIS)
 *   041013211, Jade, Mak (as from ACSIS)
 *   040991469, Wentao, Lu (as from ACSIS)
 * 
 */

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

import bloodbank.entity.Address;

@Singleton
public class AddressService implements Serializable { 
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LogManager.getLogger();

	@PersistenceContext(name = PU_NAME)
	protected EntityManager em;
	
	@Inject
	protected Pbkdf2PasswordHash pbAndjPasswordHash;
	
	public List<Address> getAllAddresses() {
	    CriteriaBuilder cb = em.getCriteriaBuilder();
	    CriteriaQuery<Address> cq = cb.createQuery(Address.class);
	    cq.select(cq.from(Address.class));
	    return em.createQuery(cq).getResultList();
	}
	
	
	public Address getAddressById(int id) {
		return em.find(Address.class, id);
	}
	
	@Transactional
	public Address persistAddress(Address newAddress) {
	    em.persist(newAddress);
	    return newAddress;
	}
	
    /**
     * this will update an address
     * 
     * @param id - id of entity to update
     * @param addressWithUpdates - entity with updated information
     * @return Entity with updated information
     */
	@Transactional
	public Address updateAddressById(int id, Address addressWithUpdates) {
		Address addressToBeUpdated = getAddressById(id);
	    if (addressToBeUpdated != null) {
	        em.refresh(addressToBeUpdated);
	        em.merge(addressWithUpdates);
	        em.flush();
	    }
	    return addressToBeUpdated;
	}
	
	/**
	 * this will delete an address by id
	 * 
	 * @param id - address id to delete
	 */
	@Transactional
	public void deleteAddressById(int id) {
		Address addressToBeDeleted = getAddressById(id);
		if (addressToBeDeleted != null) {
			em.refresh(addressToBeDeleted);
			em.remove(addressToBeDeleted);
		}
	}

}