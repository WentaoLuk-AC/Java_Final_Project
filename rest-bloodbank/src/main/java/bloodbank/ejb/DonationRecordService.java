package bloodbank.ejb;

import static bloodbank.utility.MyConstants.PU_NAME;
import static bloodbank.utility.MyConstants.PARAM1;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;
import javax.transaction.Transactional;

import bloodbank.entity.DonationRecord;

@Singleton
public class DonationRecordService  implements Serializable{
	
    private static final long serialVersionUID = 1L;
        
    @PersistenceContext(name = PU_NAME)
    protected EntityManager em;
    
    @Inject
    protected Pbkdf2PasswordHash pbAndjPasswordHash;
    
    public List<DonationRecord> getAllDonationRecords(){
    	TypedQuery<DonationRecord> drQuery = em.createNamedQuery("DonationRecord.findAll", DonationRecord.class);
    	return drQuery.getResultList();
    }
    
    public DonationRecord getDonationRecordById(int id) {
    	TypedQuery<DonationRecord> drQuery = em.createNamedQuery("DonationRecord.findById", DonationRecord.class);
    	drQuery.setParameter(PARAM1, id);
    	return drQuery.getSingleResult();
    }
    
	@Transactional
	public DonationRecord persistDonationRecord(DonationRecord record) {
		em.persist(record);
		return record;
	}
	
	@Transactional
	public DonationRecord updateDonationRecordById(int id, DonationRecord updatedRecord) {
		DonationRecord selectedRecord = getDonationRecordById(id);
		if (selectedRecord != null) {
			em.refresh(selectedRecord);
			selectedRecord.setTested(updatedRecord.getTested() == 1);
			selectedRecord.setDonation(updatedRecord.getDonation());
			selectedRecord.setOwner(updatedRecord.getOwner());
			em.merge(selectedRecord);
			em.flush();
		}
		return selectedRecord;
	}
	
	@Transactional
	public DonationRecord deleteDonationRecordById(int id) {
		DonationRecord record = getDonationRecordById(id);
		if (record != null) {
			em.refresh(record);
			em.remove(record);
			em.flush();
		}
		return record;
	}


}
