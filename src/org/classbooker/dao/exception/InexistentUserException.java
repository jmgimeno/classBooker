/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classbooker.dao.exception;

import org.apache.log4j.Logger;


/**
 *
 * @author Marc Sole, Carles Mònico
 */
public class InexistentUserException extends DAOException {
    
    /**
     *
     */
    public InexistentUserException(){
        
    }
    
    /**
     *
     * @param msg
     */
    public InexistentUserException(String msg) {
        super(msg);
    }
    public InexistentUserException(Logger log,String nameFunction) {
        log.error("in"+ nameFunction +"error:"+ this.getMessage());
    }
    
}