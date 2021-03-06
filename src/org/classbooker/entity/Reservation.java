/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classbooker.entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;
import javax.persistence.*;
import org.joda.time.DateTime;

/**
 *
 * @author josepma, Carles Mònico Bonell, Marc Solé Farré
 */
@Entity
@Table (name="RESERVATION")
public class Reservation {
    @Id
    @Column (name="IDENTIFIER")
    @GeneratedValue
    private long reservationId;
  
    @Column (name="DATE")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)   
    private Calendar reservationDate;   
    
    @ManyToOne
    @JoinColumn(name = "USERT", referencedColumnName = "NIF")
    private ReservationUser rUser;
    
    @ManyToOne
    @JoinColumn(name = "ROOM", referencedColumnName = "ROOMID")
    private Room room;
    
    private static final int HASH = 5;
    private static final int HASH_MULTI = 79;

    public Reservation() {
    }

    public Reservation(DateTime reservationDate, ReservationUser rUser, Room room) {
        this.reservationDate = changeDateToCalendar(reservationDate);
        //this.DT = reservationDate;
        this.rUser = rUser;
        this.room = room;
    }

    public long getReservationId() {
        return reservationId;
    }

    public DateTime getReservationDate() {
        return new DateTime(this.reservationDate);
    }

    
    public void setReservationDate(DateTime reservationDate) {
        this.reservationDate = changeDateToCalendar(reservationDate);     
    }

    public ReservationUser getrUser() {
        return rUser;
    }

    public void setrUser(ReservationUser rUser) {
        this.rUser = rUser;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    @Override
    public int hashCode() {
        int hash = HASH;
        hash = HASH_MULTI * hash + Objects.hashCode(this.reservationDate);
        hash = HASH_MULTI * hash + Objects.hashCode(this.rUser);
        hash = HASH_MULTI * hash + Objects.hashCode(this.room);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Reservation other = (Reservation) obj;
        return Objects.equals(this.reservationDate, other.reservationDate) 
               && Objects.equals(this.rUser, other.rUser) 
               && Objects.equals(this.room, other.room);
    }
    
    private Calendar changeDateToCalendar(DateTime reservationDate){
        return reservationDate.withMinuteOfHour(0).withSecondOfMinute(0)
                .withMillisOfSecond(0).toCalendar(Locale.getDefault());
    }

    @Override
    public String toString() {
        return "Reservation { Date: " + reservationDate.getTime().toString() +
                ", Building Name: " + room.getBuilding().getBuildingName() +
                ", Room number: " + room.getNumber() +
                ", Room capacity: " + room.getCapacity() +
                ", type: " + room.getClass().getSimpleName() +
                ", User: " + rUser.getName() + "}";
    }

}
