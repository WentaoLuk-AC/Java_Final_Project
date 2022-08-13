/***************************************************************************
 * File: Person.java Course materials (22S) CST 8277
 * 
 * @author Teddy Yap
 * @author Shariar (Shawn) Emami
 * @author Mike Norman
 * 
 * Updated by:  Group NN
 *   studentId, firstName, lastName (as from ACSIS)
 *   studentId, firstName, lastName (as from ACSIS)
 *   studentId, firstName, lastName (as from ACSIS)
 *   studentId, firstName, lastName (as from ACSIS)
 *   
 */
package bloodbank.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@SuppressWarnings("unused")

/**
 * The persistent class for the person database table.
 */
@Entity
@Table( name = "person")
@NamedQuery( name = Person.ALL_PERSONS_QUERY_NAME, query = "SELECT p FROM Person p")
@NamedQuery( name = Person.QUERY_PERSON_BY_ID, query = "SELECT p FROM Person p where p.id=:param1")
//no need for AttributeOverride as person id column is called id as well.
public class Person extends PojoBase implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String ALL_PERSONS_QUERY_NAME = "Person.findAll";
    public static final String QUERY_PERSON_BY_ID = "Person.findAllByID";
	
    public Person() {
        super();
    }

    @Basic( optional = false)
    @Column( name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Basic( optional = false)
    @Column( name = "last_name", nullable = false, length = 50)
    private String lastName;

	@OneToMany(cascade= CascadeType.MERGE, fetch = FetchType.LAZY, mappedBy = "owner")
    private Set< DonationRecord> donations = new HashSet<>();

    @OneToMany(cascade= CascadeType.MERGE, fetch = FetchType.LAZY, mappedBy = "owner")
    private Set< Contact> contacts = new HashSet<>();

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName( String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName( String lastName) {
        this.lastName = lastName;
    }

    // simplify Json body, skip BloodDonations
    @JsonIgnore
    public Set< DonationRecord> getDonations() {
        return donations;
    }

    public void setDonations( Set< DonationRecord> donations) {
        this.donations = donations;
    }

    // simplify Json body, skip BloodDonations
    @JsonIgnore
    public Set< Contact> getContacts() {
        return contacts;
    }

    public void setContacts( Set< Contact> contacts) {
        this.contacts = contacts;
    }

    public void setFullName( String firstName, String lastName) {
        setFirstName( firstName);
        setLastName( lastName);
    }
    
    //Inherited hashCode/equals is sufficient for this entity class

}