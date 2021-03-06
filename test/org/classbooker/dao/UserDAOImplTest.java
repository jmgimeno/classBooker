/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classbooker.dao;

import org.classbooker.dao.UserDAOImpl;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import org.classbooker.dao.exception.AlreadyExistingUserException;
import org.classbooker.dao.exception.InexistentUserException;
import org.classbooker.entity.User;
import org.classbooker.entity.ProfessorPas;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author jpb3
 */
public class UserDAOImplTest {
    UserDAOImpl udao;
    User u;
    List<User> expected; 
    Set<User> expectedSet;
    
    public UserDAOImplTest() {
    }
    
    @Before
    public void setUp() throws Exception{
        expected = new ArrayList<>();
        udao = new UserDAOImpl();
        udao.setEntityManager(Persistence.createEntityManagerFactory("classBooker").createEntityManager());
        u = new ProfessorPas("12345","pepito@gmail.com","Pepito","");
        User us = new ProfessorPas("98765","jaunito@gmail.com","Juanito","");
        addUser(u);
        addUser(us);
        expected.add(u);
        expected.add(us);
        expectedSet = new HashSet(expected);
    }
    
    @Test
    public void testAddUser() throws Exception{
        User u1 = new ProfessorPas("55555","manganito@gmail.com","Manganito","");
        udao.addUser(u1);
        User exp = findUserByNif("55555");
        assertEquals(u1,exp);
    }
    
    @Test
    public void testRemoveUser() throws Exception{
        User user = udao.getUserByNif("12345");
        assertEquals(u,user);
        udao.removeUser(user);
        user = udao.getUserByNif("12345");
        assertNull(user);
    }
    
    @Test(expected = InexistentUserException.class)
    public void removeInexistentUser() throws Exception{
        User user  = new ProfessorPas("9999", "notin@database.com", "Inexistent","");
        udao.removeUser(user);
    }
    
    @Test(expected = AlreadyExistingUserException.class)
    public void testAddRepeatedUser() throws Exception{
        udao.addUser(u);
    }
    
    @Test
    public void testGetInexistingUser(){
        User user = udao.getUserByNif("NotANifInTheDatabase");
        assertNull(user);
    }
    
    @Test
    public void testGetUserByNif() throws Exception {
        User u2 = udao.getUserByNif("12345");
        assertEquals("These two users should be equals",u,u2);
        
    }
    
    @Test
    public void testGetUsersByName() throws Exception {
        List<User> users = udao.getUsersByName("Pepito");
        assertEquals("These two users should be equals",u,users.get(0));
        
    }
    
    @Test
    public void testGetUsersByNameWithEmptyDatabase() throws Exception {
        clear();
        udao.setEntityManager(Persistence.createEntityManagerFactory("classBooker").createEntityManager());
        List<User> users = udao.getUsersByName("Pepito");
        assertEquals(new ArrayList(),users);
        
    }
    
    @Test
    public void testGetMoreThanOneUserByName() throws Exception {
        User u1 = new ProfessorPas("44444","manganito1@gmail.com","Manganito","");
        User u2 = new ProfessorPas("66666","manganito2@gmail.com","Manganito","");
        addUser(u1);
        addUser(u2);
        List<User> expected = new ArrayList<>();
        expected.add(u1);
        expected.add(u2);
        Set<User> expectedSet = new HashSet(expected);
        List<User> users = udao.getUsersByName("Manganito");
        Set<User> userSet = new HashSet(users);
        assertEquals("These two users should be equals",expectedSet,userSet);
        
    }
    
    @Test
    public void testGetAllUsers() throws Exception {
        List<User> users = udao.getAllUsers();
        Set<User> userSet = new HashSet(users);
        assertEquals("These two users should be equals",userSet,expectedSet);
        
    }
    
    @Test
    public void testGetAllUsersWithEmptyDatabase() throws Exception {
        clear();
        udao.setEntityManager(Persistence.createEntityManagerFactory("classBooker").createEntityManager());
        List<User> users = udao.getAllUsers();
        assertEquals(new ArrayList(),users);
    }
    
    
    @After
    public void clear(){
        udao.tearDown();
    }
    
    private void addUser(User u){
        EntityManager em = udao.getEntityManager();
        try{
            em.getTransaction().begin();
            em.persist(u);
            em.getTransaction().commit();
        }
        finally{
            
        }
    }
    
    private User findUserByNif(String nif){
        User u = null;
        
        EntityManager em = udao.getEntityManager();
        
        try{
            em.getTransaction().begin();
            u = em.find(User.class, nif);
            em.getTransaction().commit();
        }
        finally{
            
        }
        return u;
    }
    
}
