/**
 * File: SecurityUser.java Course materials (22S) CST 8277
 *
 * @author Teddy Yap
 * @author Shariar (Shawn) Emami
 * @author (original) Mike Norman
 * 
 * Updated by:  Group NN
 *   041004996, Jenya, Pribylov (as from ACSIS)
 *   040923573, Jacob, Crocker (as from ACSIS)
 *   studentId, firstName, lastName (as from ACSIS)
 *   studentId, firstName, lastName (as from ACSIS)
 * 
 */
package bloodbank.entity;

import java.io.Serializable;
import java.security.Principal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import bloodbank.rest.serializer.SecurityRoleSerializer;

@SuppressWarnings("unused")

/**
 * User class used for (JSR-375) Java EE Security authorization/authentication
 */

//TODO - make into JPA Entity and add all the necessary annotations
@Entity
@Table(name = "security_user")
@Access(AccessType.FIELD)
public class SecurityUser implements Serializable, Principal {
    /** explicit set serialVersionUID */
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    protected int id;
    
    @Basic(optional = false)
    @Column(name = "username")
    protected String username;
    
    @Basic(optional = false)
    @Column(name = "password_hash")
    protected String pwHash;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    protected Person person;
    
    @ManyToMany(cascade = {CascadeType.PERSIST})
    @JoinTable(name = "user_has_role", joinColumns = @JoinColumn(name = "user_id", referencedColumnName="user_id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id"))
    protected Set<SecurityRole> roles = new HashSet<SecurityRole>();

    public SecurityUser() {
        super();
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPwHash() {
        return pwHash;
    }
    public void setPwHash(String pwHash) {
        this.pwHash = pwHash;
    }

    // TODO:  setup custom Json Serializer
    @JsonInclude(Include.NON_NULL)
    @JsonSerialize(using = SecurityRoleSerializer.class)
    public Set<SecurityRole> getRoles() {
        return roles;
    }
    public void setRoles(Set<SecurityRole> roles) {
        this.roles = roles;
    }

    public Person getPerson() {
        return person;
    }
    public void setPerson(Person person) {
        this.person = person;
    }

    // Principal
    @Override
    public String getName() {
        return getUsername();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        // only include member variables that really contribute to an object's identity
        // i.e. if variables like version/updated/name/etc. change throughout an object's lifecycle,
        // they shouldn't be part of the hashCode calculation
        return prime * result + Objects.hash(getId());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj instanceof SecurityUser otherSecurityUser) {
            // see comment (above) in hashCode(): compare using only member variables that are
            // truly part of an object's identity
            return Objects.equals(this.getId(), otherSecurityUser.getId());
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("SecurityUser [id=").append(id).append(",username=").append(username).append("]");
        return builder.toString();
    }
    
}