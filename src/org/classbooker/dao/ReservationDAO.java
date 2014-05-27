/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classbooker.dao;

import org.classbooker.dao.exception.DAOException;
import java.util.List;
import javax.persistence.EntityManager;
import org.classbooker.entity.Reservation;
import org.joda.time.DateTime;

/**
 *
 * @author josepma, Marc Solé, Carles Mònico
 */
public interface ReservationDAO {

    /**
     * Set the Entitymanager
     * @param em EntityManager
     */
    void setEm(EntityManager em);

    /**
     * Set the UserDAO
     * @param uDao UserDAO
     */
    void setuDao(UserDAO uDao);
    
    /**
     * Set the SpaceDAO
     * @param sDao SpaceDAO
     */
    void setsDao(SpaceDAO sDao);
    
    
    
    /**
     * Add a reservation in the database
     *
     * @param reservation
     * @return an identifier of the reservation added.
     * @throws IncorrectReservationException 
     * when the reservation is null or the date is null.
     * @throws IncorrectUserException 
     * when the user is null or the nif of the user is null.
     * @throws IncorrectRoomException
     * when the Room is null or the number of the room is null.
     * @throws AlredyExistReservationException
     */
    long addReservation(Reservation reservation)
                                           throws DAOException;
    
    /**
     * Add a reservation in the database by params
     * @param nif
     * @param roomId
     * @param buildingName name of the building
     * @param dateTime the time of reservation
     * @return an identifier of the reservation added.
     * @throws IncorrectReservationException 
     * when the reservation is null or the date is null
     * @throws IncorrectUserException 
     * when the user is null or the nif of the user is null
     * @throws IncorrectRoomException
     * when the Room is null or the number of the room is null
     * @throws AlredyExistReservationException
     */
    long addReservation (String nif, String roomId, 
                          String buildingName, DateTime dateTime)
                                           throws DAOException;

    /**
     * Remove reservation by id.
     * @param id identifier of the reservation.
     * @throws IncorrectReservationException 
     * when the Room is null or the number of the room is null
     */

    void removeReservation(long id) throws DAOException;
    
    
    void removeReservation(DateTime datetime, String roomNb,String buildingName) 
                                            throws DAOException;
    
    /**
     * Get a reservation by id.
     * @param id identifier of the reservation.
     * @return the reservation equals that identifier
     */
    Reservation getReservationById(long id);
    
    /**
     * Get a reservation by date, Room
     * @param dateTime the time of reservation.
     * @param roomNb identifier of the room.
     * @param buildingName name of the building.
     * @return the reservation equals that parameters, if not exist return null
     */
    Reservation getReservationByDateRoomBulding(DateTime dateTime, 
                                                    String roomNb, 
                                                    String buildingName)
                                                    throws DAOException;
                                                           
                                                            
    
    /**
     * Get all Reservation
     * @return a List of all reservations if 
     * not exist reservations return a empty list
     */
    List<Reservation> getAllReservation();
    
    /**
     * Get all reservations by User Nif.
     * @param nif
     * @return a list of reservations when non exist reservations 
     * returns empty list
     * @throws IncorrectUserException
     * when the user doesn't exist.
     */
    List<Reservation> getAllReservationByUserNif(String nif)
                                            throws DAOException;

    /**
     * Get all reservation by building.
     * @param name
     * @return a list of reservations when non exist reservations 
     * returns empty list
     * @throws IncorrectBuildingException
     * when the Building doesn't exist.
     */
    List<Reservation> getAllReservationByBuilding(String name) 
                                            throws DAOException;

    /**
     * Get all reservation by room.
     * @param id
     * @return a list of reservations when non exist reservations 
     * returns empty list
     * @throws IncorrectRoomException
     * when the Room doesn't exist.
     */
    List<Reservation> getAllReservationByRoom(long id) 
                                            throws DAOException;
 
    /**
     * Tear down date of reservation Table
     */
    void tearDown();
}
