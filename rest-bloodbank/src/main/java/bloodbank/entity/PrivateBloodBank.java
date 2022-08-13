/***************************************************************************
 * File: PrivateBloodBank.java Course materials (22S) CST 8277
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

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue( "1")
public class PrivateBloodBank extends BloodBank implements Serializable {
    private static final long serialVersionUID = 1L;

    public PrivateBloodBank() {
        super(false);
    }
}