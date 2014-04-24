/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classBooker.entity;
import javax.persistence.*;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author josepma
 * 
 */
@Entity
@Table(name="BUILDING")
public class Building {
    
    @Id
    @Column (name="NAME")
    private String name;

    
    @OneToMany (mappedBy="building", cascade=CascadeType.PERSIST, fetch=FetchType.EAGER)
    private List<Room> rooms;
    
    public Building() {
        this.rooms=new ArrayList<Room>();
        this.name="";
    }
    
    public Building (String Name){
        this.rooms=new ArrayList<Room>();
        this.name = name;
       
    }
    
    public String getBuildingName(){
        return name;
    }
    
    public void setBuildingName(String Name){
        this.name=name;
    }
    
    @Override
    public boolean equals(Object obj){
        return (obj instanceof Building) && 
                ((Building) obj).name.equals(this.name) &&
                ((Building) obj).rooms.size() == this.rooms.size();          
    }
    
    @Override
    public String toString(){
        return "Club name="+name + "teams="+rooms;       
    }
}