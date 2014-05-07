/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classBooker.service;

import org.classBooker.dao.ReservationDAO;
import org.classBooker.entity.Reservation;
import org.junit.Test;
import static org.junit.Assert.*;
import org.jmock.Expectations;
import static org.jmock.Expectations.any;
import static org.jmock.Expectations.returnValue;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.runner.RunWith; 
import org.jmock.Sequence;
import java.util.List;
import java.util.ArrayList;
import org.classBooker.dao.exception.IncorrectUserException;
import org.classBooker.entity.Building;
import org.classBooker.entity.ClassRoom;
import org.classBooker.entity.ProfessorPas;
import org.classBooker.entity.ReservationUser;
import org.classBooker.entity.Room;
import org.joda.time.DateTime;
import org.junit.Before;

/**
 *
 * @author Santiago Hijazo i Mauro Churata
 */

@RunWith(JMock.class)
public class ReservationMgrServiceImplQueryTest {
    
    Mockery context = new JUnit4Mockery();
    ReservationUser rUser;
    ReservationDAO resDao;
    DateTime startDate;
    DateTime endDate;
    Reservation res;
    Room room;
    Building building;
    Sequence seq;
    ReservationMgrServiceImplQuery rmsQ;
    List<Reservation> lres;
   
    @Before
    public void setup(){
        lres = new ArrayList<>();
        
        resDao = context.mock(ReservationDAO.class);
        rUser = new ProfessorPas("12345678","Ralph@aus.com","Ralph");
        building = new Building("Tibbers Building");
        room = new ClassRoom (building,"2.3",30);
        startDate = new DateTime(1,2,3,4,5);
        endDate = new DateTime(2,3,4,5,6);
        res = new Reservation(startDate, rUser, room);
        
        seq = context.sequence("seq");
        rmsQ = new ReservationMgrServiceImplQuery();
        
        rmsQ.setResDao(resDao);
        
//        lres.add(res);
    }
 
    @Test 
    public void noReservationExist() throws Exception{
      
      final List <Reservation> lreser = new ArrayList<>();
      final String nif ="01234567";
       
      context.checking(new Expectations(){{
        oneOf(resDao).getAllReservationByUserNif(nif);
        will(returnValue(lreser));
      }});
      
      List <Reservation> expected = rmsQ.getReservationsByNif(nif);
      assertEquals("Error",expected,lres);
    }
    
    @Test 
    public void noReservationFilteredExist() throws Exception{
      
      final List <Reservation> lreser = new ArrayList<>();
      final String nif ="01234567";
      final DateTime startD = null;
      final DateTime endD = null;
      final String buildingName = null;
      final long roomNb = 0;
      final int capacity = 0;
      final String roomType = null;
       
      context.checking(new Expectations(){{
        oneOf(resDao).getAllReservationByUserNif(nif);
        will(returnValue(lreser));
      }});
      
      List <Reservation> expected = rmsQ.getFilteredReservation(nif,
                                                                startD,
                                                                endD,
                                                                buildingName,
                                                                roomNb,
                                                                capacity,
                                                                roomType);
      assertEquals("Error",expected,lres);
    }
    
    //@Test 
    public void noReservationExistBis() throws Exception{
      
      final List <Reservation> lreser = new ArrayList<>();
      lreser.add(new Reservation());
      final String nif ="01234567";
       
      context.checking(new Expectations(){{
        oneOf(resDao).getAllReservationByUserNif(nif);
        will(returnValue(lreser));
      }});
      
      List <Reservation> expected = rmsQ.getReservationsByNif(nif);
      assertEquals("Error",expected.get(0),lres.get(0));
    }
   /*
    //@Test // Pendiente con Ribó
    public void oneReservationByUserID(){
       
       final long id = 123;
       
       context.checking(new Expectations(){{
        oneOf(resDao).getReservationById(id);
        will (returnValue(res));
       }});
       
    }
    
    @Test
    public void oneReservationByDateRoomBuilding() throws Exception{
       
       final String roomId = "2.4";
       final String building = "EPS";
       
       context.checking(new Expectations(){{
        //oneOf(resDao).getReservationByDateRoomBulding(dateTime, roomId, building);
        will (returnValue(res));
       }});
       
       Reservation expected = rmsQ.getReservation(dateTime, roomId, building);
       assertEquals("Reservation already done",expected,res);
    }
    
    //@Test
    public void noReservationByUserNif() throws Exception{
     
      final String id = "Josep";  
       
      context.checking(new Expectations(){{
        oneOf(resDao).getAllReservationByUserNif(id);
        will(returnValue(null));
      }});
     rmsQ.getReservations(id);
     
//     assertEquals("Error conversion", null, rmsQ.getReservations(id));
    }
           
    @Test
    public void MoreReservationByUserNif() throws Exception {
       
        final List <Reservation> resList = new ArrayList<Reservation>();
        final String building = "EPS";
        context.checking(new Expectations(){{
            oneOf(resDao).getAllReservationByBuilding(building);
            will(returnValue(resList));
        }});
        List<Reservation> expected = rmsQ.getReservations(building);
        assertEquals("Reservation already done",expected,resList);
    }
    */
}
