package bloodbank.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2022-08-13T18:41:15.541-0400")
@StaticMetamodel(SecurityRole.class)
public class SecurityRole_ {
	public static volatile SingularAttribute<SecurityRole, Integer> id;
	public static volatile SingularAttribute<SecurityRole, String> roleName;
	public static volatile SetAttribute<SecurityRole, SecurityUser> users;
}
