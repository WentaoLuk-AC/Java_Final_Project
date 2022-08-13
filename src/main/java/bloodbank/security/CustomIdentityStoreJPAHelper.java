/*****************************************************************
 * File: CustomIdentityStoreJPAHelper.java
 * Course materials CST 8277
 * 
 * @author Mike Norman
 *
 */
package com.algonquincollege.cst8277.security;

import static com.algonquincollege.cst8277.models.SecurityUser.SECURITY_USER_BY_NAME_QUERY;
import static com.algonquincollege.cst8277.util.MyConstants.PARAM1;
import static com.algonquincollege.cst8277.util.MyConstants.PU_NAME;
import static java.util.Collections.emptySet;

import java.util.Set;
import java.util.stream.Collectors;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import com.algonquincollege.cst8277.models.SecurityRole;
import com.algonquincollege.cst8277.models.SecurityUser;

@Singleton
public class CustomIdentityStoreJPAHelper {

    @PersistenceContext(name = PU_NAME)
    protected EntityManager em;
    
    public SecurityUser findUserByName(String username) {
        SecurityUser user = null;
        try {
            TypedQuery<SecurityUser> q = em.createNamedQuery(SECURITY_USER_BY_NAME_QUERY, SecurityUser.class);
            q.setParameter(PARAM1, username);
            user = q.getSingleResult();
        }
        catch (NoResultException e) {
        	
        }
        return user;
    }

    public Set<String> findRoleNamesForUser(String username) {
        Set<String> roleNames = emptySet();
        SecurityUser securityUser = findUserByName(username);
        if (securityUser != null) {
            roleNames = securityUser.getSecurityRoles().stream().map(s -> s.getRoleName()).collect(Collectors.toSet());
        }
        return roleNames;
    }

    @Transactional
    public void saveSecurityUser(SecurityUser user) {
        em.persist(user);
    }

    @Transactional
    public void saveSecurityRole(SecurityRole role) {
        em.persist(role);
    }

}