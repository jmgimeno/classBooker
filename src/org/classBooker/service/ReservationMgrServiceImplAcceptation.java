/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.classBooker.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.classBooker.dao.ReservationDAO;
import org.classBooker.dao.SpaceDAO;
import org.classBooker.dao.UserDAO;
import org.classBooker.dao.exception.AlreadyExistingBuildingException;
import org.classBooker.dao.exception.AlredyExistReservationException;
import org.classBooker.dao.exception.IncorrectBuildingException;
import org.classBooker.dao.exception.IncorrectReservationException;
import org.classBooker.dao.exception.IncorrectRoomException;
import org.classBooker.dao.exception.IncorrectTypeException;
import org.classBooker.dao.exception.IncorrectUserException;
import org.classBooker.entity.Building;
import org.classBooker.entity.Reservation;
import org.classBooker.entity.ReservationUser;
import org.classBooker.entity.Room;
import org.classBooker.entity.User;
import org.classBooker.util.ReservationResult;
import org.joda.time.DateTime;

/**
 *
 * @author abg7
 */
public class ReservationMgrServiceImplAcceptation implements ReservationMgrService {

    private Reservation reservation;
    private UserDAO userDao;
    private ReservationDAO reservationDao;
    private SpaceDAO spaceDao;

    public void setSpaceDao(SpaceDAO spaceDao) {
        this.spaceDao = spaceDao;
    }

    public void setUserDao(UserDAO userDao) {
        this.userDao = userDao;
    }

    public void setReservationDao(ReservationDAO reservationDao) {
        this.reservationDao = reservationDao;
    }

    @Override
    public Reservation modifyReservation(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Reservation> findReservationByNif(String nif) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Reservation findReservationById(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Reservation> findReservationById(String buildingName, String roomNumber) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Reservation findReservationById(String buildingName, String roomNumber, DateTime date) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Reservation> findReservationByType(String type) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Reservation> findReservationByType(String type, DateTime date) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Reservation> getAllReservations() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteReservation(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Room> suggestionSpace(String roomNb, String building, DateTime date) throws IncorrectBuildingException, IncorrectRoomException {
        Room room = spaceDao.getRoomByNbAndBuilding(roomNb, building);
        List<Room> suggestedRoomsByTypeAndCapacity = spaceDao.getAllRoomsByTypeAndCapacity(room.getClass().toString(), room.getCapacity(), building);
        List<Room> finalSuggestedRooms = new ArrayList<>();
        Reservation res;
        for (Room r : suggestedRoomsByTypeAndCapacity) {
            res = reservationDao.getReservationByDateRoomBulding(date, roomNb, building);
            if(res == null){
                finalSuggestedRooms.add(r);
            }
        }
        return finalSuggestedRooms;
    }

    @Override
    public ReservationUser getCurrentUserOfDemandedRoom(String roomNb, String building, DateTime datetime)  {
        Reservation res = reservationDao.getReservationByDateRoomBulding(datetime, roomNb, building);
        if(res == null) return null;
        return res.getrUser();
    }
    
    @Override
    public void acceptReservation(Reservation reservation) throws IncorrectReservationException, IncorrectUserException, IncorrectRoomException, AlredyExistReservationException{
        reservationDao.addReservation(reservation);
    }

    @Override
    public Reservation makeReservationBySpace(long roomld, String nif, DateTime initialTime) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    @Override
    public ReservationResult makeCompleteReservationBySpace(String nif, String roomNb, String buildingName, DateTime resDate) throws Exception{
        Room room = spaceDao.getRoomByNbAndBuilding(roomNb, buildingName);
        Reservation reservation = makeReservationBySpace(room.getRoomId(), nif, resDate);
         
        if(reservation != null){
            ReservationUser reservationUser = (ReservationUser) userDao.getUserByNif(nif);
            return new ReservationResult(reservation, reservationUser);
        }
         
        List<Room> suggestedRooms = suggestionSpace(roomNb, buildingName, resDate);
        return new ReservationResult(suggestedRooms);
    }

}
