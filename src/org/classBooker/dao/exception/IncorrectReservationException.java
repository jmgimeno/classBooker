/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classBooker.dao.exception;

/**
 *
 * @author Marc Sole, Carles Mònico
 */
public class IncorrectReservationException extends Exception{
    
    /**
     *
     */
    public IncorrectReservationException(){
        
    }

    /**
     *
     * @param message
     */
    public IncorrectReservationException(String message){
        super(message);
    }
    
}
