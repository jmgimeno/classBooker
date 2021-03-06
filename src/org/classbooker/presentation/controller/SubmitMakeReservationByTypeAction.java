/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classbooker.presentation.controller;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.classbooker.dao.exception.DAOException;
import org.classbooker.dao.exception.IncorrectBuildingException;
import org.classbooker.dao.exception.IncorrectCapacityException;
import org.classbooker.dao.exception.IncorrectTypeException;
import org.classbooker.entity.Reservation;
import org.classbooker.presentation.view.AcceptReservationByTypeForm;
import org.classbooker.presentation.view.ConfirmationForm;
import org.classbooker.presentation.view.ExceptionInfo;
import org.classbooker.presentation.view.ReservationByTypeInsertionForm;
import org.classbooker.service.ReservationMgrService;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 *
 * @author Mauro Churata
 */
public class SubmitMakeReservationByTypeAction implements ActionListener{
    ReservationByTypeInsertionForm reservationByTypeInsertionForm;
    ReservationMgrService services;
    public JFrame parent;
    public SubmitMakeReservationByTypeAction(ReservationByTypeInsertionForm form, JFrame frame){
       reservationByTypeInsertionForm = form;
       this.parent = frame;
       
   }
    public void setServices(ReservationMgrService services){
       this.services = services;
   }
    public void actionPerformed(ActionEvent e){
   
       if("".equals(reservationByTypeInsertionForm.nif.getText())){
           JOptionPane.showMessageDialog(null, 
                        "Please, Can you put user nif?", 
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
       }
       String nif = reservationByTypeInsertionForm.nif.getText();
       if("".equals(reservationByTypeInsertionForm.type.getText())){
           JOptionPane.showMessageDialog(null, 
                        "Please, Can you put the type of room?", 
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
       }
       String type = reservationByTypeInsertionForm.type.getText();
       if("".equals(reservationByTypeInsertionForm.buildingName.getText())){
           JOptionPane.showMessageDialog(null, 
                        "Please, Can you put building name?", 
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
       }
       String buildingName = reservationByTypeInsertionForm.buildingName.getText();
       if("".equals(reservationByTypeInsertionForm.capacity.getText())){
           JOptionPane.showMessageDialog(null, 
                        "Please, Can you put a capacity?", 
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
       }
       int capacity = Integer.parseInt(reservationByTypeInsertionForm.capacity.getText());
       DateTimeFormatter formatter = DateTimeFormat.forPattern("dd-MM-yyyy HH:mm");
       if("".equals(reservationByTypeInsertionForm.dateIni.getText())){
           JOptionPane.showMessageDialog(null, 
                        "Please, Can you put a Date?", 
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
       }
       DateTime dateIni = formatter.parseDateTime(reservationByTypeInsertionForm.dateIni.getText());
       
       reservationByTypeInsertionForm.parent.getContentPane().removeAll();
      
        try{
//            Reservation makeReservationByType(String nif, String type, String buildingName, int capacity, DateTime date)
            Reservation reservation = services.makeReservationByType(nif, type, buildingName, capacity, dateIni);
//            ConfirmationForm confirm = new ConfirmationForm("Add Reservation");
//            reservationByTypeInsertionForm.parent.getContentPane().add(confirm,BorderLayout.CENTER);
          
            reservationByTypeInsertionForm.parent.getContentPane().removeAll();
         
            AcceptReservationByTypeForm form = new AcceptReservationByTypeForm(parent,reservation,services);
            reservationByTypeInsertionForm.parent.getContentPane().add(form,BorderLayout.CENTER);
     
            reservationByTypeInsertionForm.parent.revalidate();
     
        }
        catch(IncorrectBuildingException exc){ 
           JOptionPane.showMessageDialog(null, 
                        "The Name of Building is incorrect. Please, write again", 
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
        }
        catch(IncorrectCapacityException exc){ 
           JOptionPane.showMessageDialog(null, 
                        "The Capacity is incorrect. Please, write again", 
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
        }
        catch(IncorrectTypeException exc){ 
           JOptionPane.showMessageDialog(null, 
                        "The Type of the room is incorrect. Please, write again", 
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
        }
        catch(DAOException exc){ //AlreadyExistingBuildingException exc){
           exc.printStackTrace(); 
           ExceptionInfo exception = new ExceptionInfo("Something is going wrong! Ups!");
           reservationByTypeInsertionForm.parent.getContentPane().add(exception,BorderLayout.CENTER);
        }
        finally{
           reservationByTypeInsertionForm.parent.revalidate();                        
        }
   }
}
