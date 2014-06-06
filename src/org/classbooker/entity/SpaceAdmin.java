/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classbooker.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 *
 * @author josepma
 */
@Entity
@DiscriminatorValue(value="SPC_U")
public class SpaceAdmin extends ReservationUser{

    public SpaceAdmin() {
        super();
    }

    public SpaceAdmin(String nif, String email, String name, String password) {
        super(nif, email, name, password);
    }
    
    
}
