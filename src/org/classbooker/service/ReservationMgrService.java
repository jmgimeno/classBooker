/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classbooker.service;

import org.classbooker.dao.exception.DAOException;
import java.util.HashMap;
import java.util.List;
import org.classbooker.dao.ReservationDAO;
import org.classbooker.dao.SpaceDAO;
import org.classbooker.dao.UserDAO;
import org.classbooker.entity.Building;
import org.classbooker.entity.Reservation;
import org.classbooker.entity.ReservationUser;
import org.classbooker.entity.Room;
import org.classbooker.entity.User;
import org.classbooker.util.ReservationResult;
import org.joda.time.DateTime;

/**
 *
 * @author josepma
 */
public interface ReservationMgrService {

    /**
     * Makes a complete reservation of a concrete space.
     * @param nif Nif of the user who is doing the reserve.
     * @param roomNb Number of the room which is going to be reserved.
     * @param buildingName Name of the building where the room is.
     * @param resDate Date of the reserve.
     * @return A ReservationResult encapsulating the Reservation done or a list of suggested rooms.
     * @throws Exception If the roomNb or BuildingName doesn't match with a correct Room or Building.
     */
    ReservationResult makeCompleteReservationBySpace(String nif, String roomNb, String buildingName, DateTime resDate)throws DAOException;

    /**
     *
     * @param roomld
     * @param nif
     * @param initialTime
     * @return
     * @throws Exception
     */
    public Reservation makeReservationBySpace(long roomld, String nif, DateTime initialTime) throws DAOException ;
    /**
     * Finds an avaliable reservation acording to the specified features.
     * @param nif Nif of the user who makes the reservation.
     * @param type Type of room.
     * @param buildingName The building where we want to make the reservation.
     * @param capacity Capacity of the room.
     * @param date Date of the reservation.
     * @return The reservation done.
     */
    Reservation makeReservationByType(String nif, String type, String buildingName, int capacity, DateTime date) throws DAOException;

    /**
     *
     * @param id
     * @return
     */
        public Reservation modifyReservation(long id);

    /**
     *
     * @param nif
     * @return
     */
    public List<Reservation> findReservationByNif(String nif);

    /**
     *
     * @param id
     * @return
     */
    public Reservation findReservationById(long id)throws DAOException; //???? És el id d'una reserva?

    /**
     *
     * @param buildingName
     * @param roomNumber
     * @return
     */
    public List<Reservation> findReservationByBuildingAndRoomNb(String buildingName, String roomNumber) throws DAOException;

    /**
     *
     * @param buildingName
     * @param roomNumber
     * @param date
     * @return
     */
   
    public Reservation findReservationBySpaceAndDate(String buildingName, String roomNumber, DateTime date) throws DAOException ;


    /**
     *
     * @param type
     * @return
     */
    public List<Reservation> findReservationByType(String type);

    /**
     *
     * @param type
     * @param date
     * @return
     */
    public List<Reservation> findReservationByType(String type, DateTime date);

    /**
     *
     * @return
     */
    public List<Reservation> getAllReservations();

    /**
     *
     * @param id
     */
    public void deleteReservation(long id) throws DAOException;

    /**
     * Suggests alternative similar rooms when the specified Room is reserved in the date given.
     * @param roomNb Already reserved room number.
     * @param building Building where the already reserved room is.
     * @param resDate Date and time of the reserve.
     * @return A list of non reserved rooms similars to the given by parameters.
     * @throws IncorrectTypeException If the room type is incorrect.
     * @throws IncorrectBuildingException If the building is incorrect.
     * @throws IncorrectRoomException If the Room number is incorrect.
     */
    List<Room> suggestionSpace(String roomNb, String building, DateTime resDate) throws DAOException;

    /**
     * Returns the user who have the reservation of a room in a specific date.
     * @param roomNb The room number.
     * @param building The building where the room is.
     * @param datetime The date and time of the reserve we are finding.
     * @return The user who have the reservation of that room.
     * @throws org.classBooker.dao.exception.IncorrectBuildingException
     * @throws org.classBooker.dao.exception.IncorrectRoomException
     */
    ReservationUser getCurrentUserOfDemandedRoom(String roomNb, String building, DateTime datetime);

    /**
     * Accepts a reservation.
     * @param reservation The reservation to accept.
     * @throws IncorrectReservationException If the reservation is not correct.
     * @throws IncorrectUserException If the user is not correct.
     * @throws IncorrectRoomException If the room is not correct.
     * @throws AlredyExistReservationException If the reservation is already done.
     */
    void acceptReservation(Reservation reservation) throws DAOException;
   
    List<Room> obtainAllRoomsWithSameFeatures(String type, int capacity, String building, DateTime date)throws DAOException;
    
    /**
     * Get a list of reservations from his own Nif
     * @param nif, this is the nif that we would use to search into the DB 
     * @return List of Reservation made by that Nif
     */
    public List <Reservation> getReservationsByNif(String nif) ;
    
    /**
     * Get a list of reservation using different fields for filtering 
     * the list that it obtained before by Nif
     * @param nif, this is the nif that we would use to search into the DB
     * @param startDate, this is the start date of a Reservation
     * @param endDate, this is the end date of a Reservation
     * @param buildingName, this is the building's name of a Reservation
     * @param roomNb, this is the room number of a Reservation
     * @param capacity, this is the capacity of a Reservation
     * @param roomType, this is the room type of a Reservation
     * @return List of Reservations filtered by some fields customized. 
     * @throws DAOException 
     */
    public List <Reservation> getFilteredReservation(String nif, 
                                                  DateTime startDate,
                                                  DateTime endDate, 
                                                  String buildingName,
                                                  String roomNb,
                                                  int capacity,
                                                  String roomType) 
            throws DAOException;
    /**
     * Set SpaceDAO
     * @param spaceDao 
     */
   public void setSpaceDao(SpaceDAO spaceDao) ;
   /**
    * Set UserDAO
    * @param userDao 
    */
   public void setUserDao(UserDAO userDao);
   /**
    * Set ReservationDAO
    * @param reservationDao 
    */
   public void setReservationDao(ReservationDAO reservationDao); 
}
