/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.classbooker.service.exception;

/**
 *
 * @author Antares
 */
public class InexistentFileException extends ServiceException{
    
    /**
     * Creates a new instance of <code>InexistentFileException</code> without
     * detail message.
     */
    public InexistentFileException() {
    }

    /**
     * Constructs an instance of <code>InexistentFileException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public InexistentFileException(String msg) {
        super(msg);
    }
    
}
