/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classBooker.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.classBooker.dao.exception.AlreadyExistingBuildingException;
import org.classBooker.dao.exception.AlreadyExistingRoomException;
import org.classBooker.dao.exception.IncorrectRoomException;
import org.classBooker.dao.exception.IncorrectTimeException;
import org.classBooker.dao.exception.IncorrectTypeException;
import org.classBooker.dao.exception.NonBuildingException;
import org.classBooker.dao.exception.PersistException;
import org.classBooker.entity.Building;
import org.classBooker.entity.ClassRoom;
import org.classBooker.entity.LaboratoryRoom;
import org.classBooker.entity.MeetingRoom;
import org.classBooker.entity.ProfessorPas;
import org.classBooker.entity.ReservationUser;
import org.classBooker.entity.Room;
import org.classBooker.entity.User;
import org.eclipse.persistence.internal.jpa.EntityManagerImpl;
import org.jmock.Expectations;
import static org.jmock.Expectations.returnValue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author saida, genis
 */
public class SpaceDAOImplIntegTest {
     EntityManager ema;
     SpaceDAOImpl sdi;
     Room room, room2, labRoom, meetRoom, classRoom;
     Building building, building2;
     private Query query;

    /**
     *
     */
    public SpaceDAOImplIntegTest() {
        this.sdi=new SpaceDAOImpl();   
    }
    
    /**
     *
     */
    @Before
    public void setUp() {       
        building=new Building("EPS");
        room=new ClassRoom(building, "2.01", 30);        
        ema = getEntityManager();
        ema.getTransaction().begin();
        ema.persist(room);
        ema.persist(building);    
        ema.getTransaction().commit();
        sdi.setEm(ema); 
    }

    /**
     *
     * @throws Exception
     */
    @Test 
    public void testAddRoom() throws Exception {
        room2 =new ClassRoom(building, "2.08", 30);
        long rom = sdi.addRoom(room2);

        Room roomdb = sdi.getRoomByNbAndBuilding("2.08", building.getBuildingName());
        
             
        assertEquals(roomdb.getBuilding(),room2.getBuilding());
        assertEquals(rom, room2.getRoomId());
        assertEquals(room2.getBuilding(), sdi.getBuildingByName(building.getBuildingName()));
        assertTrue( sdi.getAllRooms().contains(room2));
        assertTrue(sdi.getBuildingByName(building.getBuildingName()).getRooms().contains(room2));
               
                
    }
    
    
    
    /**
     *
     * @throws Exception
     */
    @Test(expected = AlreadyExistingRoomException.class)
    public void testAddExistingRoom() throws Exception{
        building2=new Building("FDE");
        room2 =new ClassRoom(building, "2.05", 30);
        
        sdi.addBuilding(building2);        
        sdi.addRoom(room2);
        sdi.addRoom(room2);
    }
    
    /**
     *
     * @throws Exception
     */
    @Test(expected=NonBuildingException.class)
    public void testAddRoomNoneExistingBuilding()throws Exception{
        building2=new Building("FDE");        
        room2= new ClassRoom (building2, "2.01", 30);
        
        sdi.addRoom(room2);  
    }
    
    /**
     * Test of removeRoom method, of class SpaceDAOImpl.
     * @throws java.lang.Exception
     */
    @Test
    public void testRemoveRoom() throws Exception {
        room2 =new ClassRoom(building, "2.08", 30);
        long rom = sdi.addRoom(room2);
        sdi.removeRoom(room2);
        assertFalse(sdi.getAllRooms().contains(room2));
    }

    /**
     * Test of getRoomById method, of class SpaceDAOImpl.
     */
    @Test
    public void testGetRoomById() {
        assertEquals(room, sdi.getRoomById(room.getRoomId()));
    }

    /**
     * Test of getAllRooms method, of class SpaceDAOImpl.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetAllRooms() throws Exception {
        room2 =new ClassRoom(building, "2.08", 30);
        sdi.addRoom(room2);
        final Set<Room> expected = new HashSet<>();
        expected.add(room);
        expected.add(room2);
        List<Room> result = sdi.getAllRooms();
        Set<Room> resultSet= new HashSet(result);
        assertEquals(expected,resultSet);
        
    }

    /**
     * Test of addBuilding method, of class SpaceDAOImpl.
     * @throws java.lang.Exception
     */
    @Test
    public void testAddBuilding() throws Exception {
        building2=new Building("FDE");              
        sdi.addBuilding(building2);
        assertTrue(sdi.getAllBuildings().contains(building));
    }

    /**
     *
     * @throws Exception
     */
    @Test(expected=AlreadyExistingBuildingException.class)
    public void testAddExixtingBuilding() throws Exception {
        building2=new Building("FDE"); 
        sdi.addBuilding(building);               
        sdi.addBuilding(building);
    }
    
    /**
     * Test of modifyBuilding method, of class SpaceDAOImpl.
     * @throws java.lang.Exception
     */
   // @Test
    public void testModifyRoom() throws Exception {
    }

    /**
     * Test of removeBuilding method, of class SpaceDAOImpl.
     * @throws java.lang.Exception
     */
   @Test
    public void testRemoveBuilding() throws Exception {
        building2 =new Building("FDE");
         sdi.addBuilding(building2);
        sdi.removeBuilding(building2);
        assertFalse(sdi.getAllBuildings().contains(building2));
    }

    /**
     * Test of getBuildingByName method, of class SpaceDAOImpl.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetBuildingByName() throws Exception {   
        assertEquals(building, sdi.getBuildingByName("EPS"));
    }

    /**
     * Test of getAllBuildings method, of class SpaceDAOImpl.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetAllBuildings() throws Exception{
        building2=new Building("FDE"); 
        sdi.addBuilding(building2);  
        final Set<Building> expected = new HashSet<>();
        expected.add(building);
        expected.add(building2);
        List<Building> result = sdi.getAllBuildings();
        Set<Building> resultSet= new HashSet(result);
        assertEquals(expected,resultSet);
    }

    /**
     * Test of getAllRoomsOfOneBuilding method, of class SpaceDAOImpl.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetAllRoomsOfOneBuilding() throws Exception {
        List<Room> rooms = new ArrayList<Room>();
        rooms.add(room);       
        building.setRooms(rooms);
        Set <Room> roomsSet = new HashSet<>(rooms);
        Set <Room> result = new HashSet <>(sdi.getAllRoomsOfOneBuilding(building.getBuildingName()));
        assertEquals(roomsSet, result);   
    }

    /**
     * Test of getAllRoomsOfOneType method, of class SpaceDAOImpl.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetAllRoomsOfOneType() throws Exception {
        labRoom = new LaboratoryRoom(building, "3.01", 10);
        meetRoom = new MeetingRoom(building, "3.02", 50);
        classRoom = new ClassRoom(building, "3.03", 100);
        sdi.addRoom(labRoom);
        sdi.addRoom(meetRoom);
        sdi.addRoom(classRoom);
        List<Room> meetingRooms = new ArrayList<Room>();
        meetingRooms.add(meetRoom);
        building.setRooms(meetingRooms);
        Set <Room> roomsSet = new HashSet<>(meetingRooms);
        Set <Room> result = new HashSet <>(sdi.getAllRoomsOfOneType("MeetingRoom"));
        assertEquals(roomsSet, result);
    }

    /**
     *
     * @throws Exception
     */
    @Test(expected=IncorrectTypeException.class)
    public void testGetAllRoomsOfOneIncorrectType() throws Exception {
        labRoom = new LaboratoryRoom(building, "3.4", 10);
        meetRoom = new MeetingRoom(building, "3.05", 50);
        classRoom = new ClassRoom(building, "3.06", 100);
        sdi.addRoom(labRoom);
        sdi.addRoom(meetRoom);
        sdi.addRoom(classRoom);
       
        List<Room> meetingRooms = new ArrayList<Room>();
        meetingRooms.add(meetRoom);
        Set <Room> roomsSet = new HashSet<>(meetingRooms);
        Set <Room> result = new HashSet <>(sdi.getAllRoomsOfOneType("ComputerRoom"));
        assertEquals(roomsSet, result);
        }
    /**
     * Test of getAllRoomsOfOneTypeAndOneBuilding method, of class SpaceDAOImpl.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetAllRoomsOfOneTypeAndOneBuilding() throws Exception {
        Building building2=new Building("FDE");
        labRoom = new LaboratoryRoom(building, "3.7", 10);
        meetRoom = new MeetingRoom(building, "3.08", 50);
        classRoom = new ClassRoom(building, "3.09", 100);
        Room room5 =new MeetingRoom(building2, "3.10", 30);
        sdi.addBuilding(building2);
        sdi.addRoom(labRoom);
        sdi.addRoom(meetRoom);
        sdi.addRoom(classRoom);
        sdi.addRoom(room5);
        List<Room> meetingRooms = new ArrayList<Room>();
        meetingRooms.add(meetRoom);
        building.setRooms(meetingRooms);
        Set <Room> roomsSet = new HashSet<>(meetingRooms);
        Set <Room> result = new HashSet<>(sdi.getAllRoomsOfOneTypeAndOneBuilding("MeetingRoom", building));
        assertEquals(roomsSet,result);
    }

    /**
     *
     * @throws Exception
     */
    @Test
        public void testGetAllRoomsOfOneTypeAndOneBuildingandcapacity() throws Exception {
        labRoom = new LaboratoryRoom(building, "2.6", 10);
        meetRoom = new MeetingRoom(building, "1.08", 50);
        classRoom = new ClassRoom(building, "3.01", 100);
        Building building2=new Building("FDE");
        Room room5 =new MeetingRoom(building2, "2.09", 30);
        Room room6 = new MeetingRoom(building, "1.07", 30);
        Room room7 = new MeetingRoom(building2, "1.08", 20);
        Room room8 = new MeetingRoom(building, "3.08", 70);
        sdi.addBuilding(building2);
        sdi.addRoom(labRoom);
        sdi.addRoom(meetRoom);
        sdi.addRoom(classRoom);
        sdi.addRoom(room5);
        sdi.addRoom(room6);
        sdi.addRoom(room7);
        sdi.addRoom(room8);
        List<Room> meetingRooms = new ArrayList<Room>();
        meetingRooms.add(meetRoom);
        meetingRooms.add(room8);
        building.setRooms(meetingRooms);
        Set <Room> roomsSet = new HashSet<>(meetingRooms);
        Set <Room> result = new HashSet<>(sdi.getAllRoomsByTypeAndCapacity("MeetingRoom", 50, building.getBuildingName()));
        assertEquals(roomsSet, result );
    }

    /**
     *
     * @throws Exception
     */
    @Test
    public void testgetRoomByNbAndBuilding() throws Exception {
      labRoom = new LaboratoryRoom(building, "2.21", 10);
      sdi.addRoom(labRoom);
      assertEquals(labRoom, sdi.getRoomByNbAndBuilding("2.21", "EPS"));  
    }
    
    private EntityManager getEntityManager() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("classBooker");
        return emf.createEntityManager();
    }

    /**
     *
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception{
        ema = sdi.getEm();
        if (ema.isOpen()) ema.close();
        ema = getEntityManager();
        ema.getTransaction().begin();
        Query query2=ema.createQuery("DELETE FROM Room");
        Query query3=ema.createQuery("DELETE FROM Building");
        int deleteRecords=query2.executeUpdate();
        deleteRecords=query3.executeUpdate();
        ema.getTransaction().commit();
        ema.close();
    }

}