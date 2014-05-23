/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classBooker.service;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.classBooker.dao.ReservationDAO;
import org.classBooker.dao.ReservationDAOImpl;
import org.classBooker.dao.SpaceDAO;
import org.classBooker.dao.SpaceDAOImpl;
import org.classBooker.dao.exception.IncorrectBuildingException;
import org.classBooker.entity.Building;
import org.classBooker.entity.Reservation;
import org.classBooker.entity.ReservationUser;
import org.classBooker.entity.Room;
import org.joda.time.DateTime;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author sht1
 */
public class ReservationMgrServiceImplQueryIntegTest {
   
   ReservationUser rUser;
   ReservationDAOImpl resDao;
   SpaceDAOImpl spaDao;
   DateTime startDate;
   DateTime endDate;
   Reservation res1,res2,res3,res4,res5;
   Room room;
   Building building;
   List<Reservation>lres,lreser;
   ReservationMgrServiceImplQuery rmsQ;
   EntityManager em;
    
    private String nif;
    private DateTime startD;
    private DateTime endD;
    private String buildingName;
    private String roomNb;
    private int capacity;
    private String roomType;
    
    @Before
    public void setup() throws Exception{
        lres = new ArrayList<>();
        lreser = new ArrayList<>();
        rmsQ = new ReservationMgrServiceImplQuery();
        createEm();
    }
    
    @Test 
    public void zeroReservationsByNif() throws Exception{    
     
      List <Reservation> tested = rmsQ.getReservationsByNif("123456");
      
      assertEquals("0 reservations",0,tested.size());
    }
 
    @Test 
    public void ReservationsByNif() throws Exception{
       
      searchReservationsByFields("12345678",null,null,null,null,0,null);
      List <Reservation> tested = rmsQ.getReservationsByNif(nif);
      
      assertEquals("12 reservations for this nif",12,tested.size());
    }
   
    @Test 
    public void ReservationWithoutFieldsForFiltering() throws Exception{
      searchReservationsByFields("12345678",null,null,null,null,0,null);     
      
      List <Reservation> tested = rmsQ.getFilteredReservation(nif,
                                                              startD,
                                                              endD,
                                                              buildingName,
                                                              roomNb,
                                                              capacity,
                                                              roomType);
      assertEquals("12 reservations for this parameters",12,tested.size());
     
    }
     
    @Test 
    public void ReservationFilteredByDates() throws Exception{
     
      searchReservationsByFields("12345678",new DateTime(2014,6,10,9,0),
              new DateTime(2014,6,10,10,0),null,null,0,null);       
      List <Reservation> tested = rmsQ.getFilteredReservation(nif,
                                                              startD,
                                                              endD,
                                                              buildingName,
                                                              roomNb,
                                                              capacity,
                                                              roomType);
      
      assertEquals("Just One reservation in this time",1,tested.size());
      
    }
    
    @Test 
    public void ReservationFilteredByBuildingName() throws Exception{
      searchReservationsByFields("12345678",null,null,"Rectorate Building",null,0,null); 
      
      List <Reservation> tested = rmsQ.getFilteredReservation(nif,
                                                              startD,
                                                              endD,
                                                              buildingName,
                                                              roomNb,
                                                              capacity,
                                                              roomType);
      assertEquals("Same size",7,tested.size());
      //assertEquals("First reservation",res1,tested.get(0));
    }
    
    @Test 
    public void ReservationFilteredByRoomNb() throws Exception{
      searchReservationsByFields("12345678",null,null,"Rectorate Building",
                                 "1.0",0,null);
      
      List <Reservation> tested = rmsQ.getFilteredReservation(nif,
                                                              startD,
                                                              endD,
                                                              buildingName,
                                                              roomNb,
                                                              capacity,
                                                              roomType);
      assertEquals("Same size",7,tested.size());
      
    }
    /*
    @Test 
    public void ReservationFilteredByCapacity() throws Exception{
      searchReservationsByFields("12345678",null,null,null,null,50,null);
      getStartExpectations("12345678",lres);
      List <Reservation> tested = rmsQ.getFilteredReservation(nif,
                                                              startD,
                                                              endD,
                                                              buildingName,
                                                              roomNb,
                                                              capacity,
                                                              roomType);
      assertEquals("Same size",1,tested.size());
      assertEquals("Fifth reservation",res5,tested.get(0));
    }
    
    @Test 
    public void ReservationFilteredByRoomType() throws Exception{
      searchReservationsByFields("12345678",null,null,null,null,0,"MeetingRoom");
      getStartExpectations("12345678",lres);
      getExpectationsWithRoomType("MeetingRoom",res1);
      List <Reservation> tested = rmsQ.getFilteredReservation(nif,
                                                              startD,
                                                              endD,
                                                              buildingName,
                                                              roomNb,
                                                              capacity,
                                                              roomType);
      assertEquals("Same size",1,tested.size());
      assertEquals("First reservation",res1,tested.get(0));
    }
    
    @Test 
    public void ReservationFilteredByAllFields() throws Exception{
      searchReservationsByFields("12345678",
                                  new DateTime(2014,5,9,12,0),
                                  new DateTime(2014,5,9,13,0),
                                  "Main Library","4.4",
                                  50,
                                  "ClassRoom");
      getStartExpectations("12345678",lres);
      getExpectationsWithRoomNbAndBuilding("4.4","Main Library",
                                            res5.getRoom());
      getExpectationsWithRoomType("ClassRoom",res5);
      List <Reservation> tested = rmsQ.getFilteredReservation(nif,
                                                              startD,
                                                              endD,
                                                              buildingName,
                                                              roomNb,
                                                              capacity,
                                                              roomType);
      assertEquals("Same size",1,tested.size());
      assertEquals("First reservation",res5,tested.get(0));
    }
    
    @Test 
    public void findReservationBySpace() throws IncorrectBuildingException{
      final List<Reservation> returnlres = new ArrayList<>();
      returnlres.add(res1);
      context.checking(new Expectations(){{
            oneOf(resDao).getAllReservationByBuilding("Rectorate Building");
            will(returnValue(returnlres));
        }});
      List <Reservation> tested = rmsQ.findReservationByBuildingAndRoomNb("Rectorate Building","2.3");
      assertEquals("Same Size",1,tested.size());
      assertEquals("First reservation",res1,tested.get(0));
    }
    
    @Test 
    public void finsReservationBySpaceBis() throws IncorrectBuildingException{
      final List<Reservation> returnlres = new ArrayList<>();
      returnlres.add(res3);
      returnlres.add(res4);
      context.checking(new Expectations(){{
            oneOf(resDao).getAllReservationByBuilding("Faculty");
            will(returnValue(returnlres));
        }});
      List <Reservation> tested = rmsQ.findReservationByBuildingAndRoomNb("Faculty","2.3");
      assertEquals("Same Size",1,tested.size());
      assertEquals("Only one reservation",res4,tested.get(0));
    }
    
    @Test
    public void IncorrectFields() throws Exception{
       
       searchReservationsByFields("12345678",null,null,"2.04",null,0,null); 
       
       List<Reservation> tested = rmsQ.getFilteredReservation(nif, 
                                                              startDate, 
                                                              endDate, 
                                                              buildingName, 
                                                              roomNb, 
                                                              capacity, 
                                                              roomType);
       
       assertEquals("Non rerserves, building is incorrect",lreser,tested);
    }
    
    @Test 
    public void validateDatesNoInit() throws Exception{
     
      searchReservationsByFields("12345678",null,new DateTime(2014,5,9,13,0)
                                 ,null,null,0,null);
      
      List <Reservation> tested = rmsQ.getFilteredReservation(nif,
                                                              startD,
                                                              endD,
                                                              buildingName,
                                                              roomNb,
                                                              capacity,
                                                              roomType);
        
        assertEquals("Non reserves,StartDate incorrect",lreser,tested);
    }
    
    @Test 
    public void validateDatesNoEnd() throws Exception{
     
      searchReservationsByFields("12345678",new DateTime(2014,5,9,12,0),null
                                 ,null,null,0,null);
      
      List <Reservation> tested = rmsQ.getFilteredReservation(nif,
                                                              startD,
                                                              endD,
                                                              buildingName,
                                                              roomNb,
                                                              capacity,
                                                              roomType);
        
        assertEquals("Non reserves,EndDate incorrect",lreser,tested);
    }
    
    @Test 
    public void validateDateEndBeforeInit() throws Exception{
     
      searchReservationsByFields("12345678",new DateTime(2014,5,9,12,0),
                                  new DateTime(2014,5,9,11,0),
                                 null,null,0,null);
      
      List <Reservation> tested = rmsQ.getFilteredReservation(nif,
                                                              startD,
                                                              endD,
                                                              buildingName,
                                                              roomNb,
                                                              capacity,
                                                              roomType);
        
        assertEquals("Non reserves,EndDate is before than startDate",
                                                                lreser,tested);
    }
   */
    
    private void createEm() throws Exception{
        resDao = new ReservationDAOImpl();
        spaDao = new SpaceDAOImpl();
        em = getEntityManager();
        resDao.setEm(em);
        spaDao.setEm(em);
        resDao.setsDao(spaDao);
        rmsQ.setResDao(resDao);
        rmsQ.setSpaDao(spaDao);
    }
    
    private EntityManager getEntityManager() throws Exception{
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("classBookerIntegration");
        return emf.createEntityManager();  
    }

     private void searchReservationsByFields(String nif, 
                                          DateTime startDate,
                                          DateTime endDate, 
                                          String buildingName,
                                          String roomNb,
                                          int capacity,
                                          String roomType){
        this.nif = nif;
        this.startD = startDate;
        this.endD = endDate;
        this.buildingName = buildingName;
        this.roomNb = roomNb;
        this.capacity = capacity;
        this.roomType = roomType;    
    }
}
