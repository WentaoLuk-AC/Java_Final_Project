/***************************************************************************
 * File: BloodBank.java Course materials (22S) CST 8277
 * 
 * @author Teddy Yap
 * @author Shariar (Shawn) Emami
 * @author Mike Norman
 * 
 * Updated by:  Group 2
 *   041004996, Jenya, Pribylov (as from ACSIS)
 *   040923573, Jacob, Crocker (as from ACSIS)
 *   041013211, Jade, Mak (as from ACSIS)
 *   040991469, Wentao, Lu (as from ACSIS)
 * 
 */
package bloodbank.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import bloodbank.rest.serializer.BloodBankSerializer;

/**
 * The persistent class for the blood_bank database table.
 */
@Entity
@Table( name = "blood_bank")
@AttributeOverride(name="id", column=@Column(name="bank_id"))
@NamedQuery( name = BloodBank.ALL_BLOODBANKS_QUERY_NAME, query = "SELECT distinct bb FROM BloodBank bb left JOIN FETCH bb.donations")
@NamedQuery( name = BloodBank.SPECIFIC_BLOODBANKS_QUERY_NAME, query = "SELECT distinct bb FROM BloodBank bb left JOIN FETCH bb.donations where bb.id=:param1")
@NamedQuery( name = BloodBank.IS_DUPLICATE_QUERY_NAME, query = "SELECT count(bb) FROM BloodBank bb where bb.name=:param1")
@Inheritance( strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn( columnDefinition = "bit(1)", name = "privately_owned", discriminatorType = DiscriminatorType.INTEGER)
@JsonSerialize( using = BloodBankSerializer.class )
public abstract class BloodBank extends PojoBase implements Serializable { 
    private static final long serialVersionUID = 1L;

    public static final String ALL_BLOODBANKS_QUERY_NAME = "BloodBank.findAll";
    public static final String SPECIFIC_BLOODBANKS_QUERY_NAME = "BloodBank.findByName";
    public static final String IS_DUPLICATE_QUERY_NAME = "BloodBank.isDuplicate";
    
    @Basic( optional = false)
    @Column( name = "name", nullable = false, length = 100)
    private String name;

    @OneToMany( cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy="bank")
    private Set< BloodDonation> donations = new HashSet<>();

    @Transient
    private boolean isPublic;

    public BloodBank() {
        super();
    }

    public BloodBank( boolean isPublic) {
        this();
        this.isPublic = isPublic;
    }

    public boolean isPublic() {
        return isPublic;
    }

    // simplify Json body, skip BloodDonations
    @JsonIgnore
    public Set< BloodDonation> getDonations() {
        return donations;
    }

    public void setDonations( Set< BloodDonation> donations) {
        this.donations = donations;
    }

    public void setName( String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    //Inherited hashCode/equals is NOT sufficient for this entity class
    
    /**
     * Very important:  use getter's for member variables because JPA sometimes needs to intercept those calls<br/>
     * and go to the database to retrieve the value
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        // only include member variables that really contribute to an object's identity
        // i.e. if variables like version/updated/name/etc. change throughout an object's lifecycle,
        // they shouldn't be part of the hashCode calculation
        
        // the database schema for the BLOOD_BANK table has a UNIQUE constraint for the NAME column,
        // so we should include that in the hash/equals calculations
        
        return prime * result + Objects.hash(getId(), getName());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        
        if (obj instanceof BloodBank otherBloodBank) {
            // see comment (above) in hashCode(): compare using only member variables that are
            // truly part of an object's identity
            return Objects.equals(this.getId(), otherBloodBank.getId()) &&
                Objects.equals(this.getName(), otherBloodBank.getName());
        }
        return false;
    }
}