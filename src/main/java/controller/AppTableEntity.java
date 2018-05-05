/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.Date;
import java.sql.Timestamp;

/**
 *
 * @author Amir Ingher
 */
public class AppTableEntity {
    
    private int ID;
    private String somedata;
    private double lat;
    private double lon;
    private String keyelement;
    private Timestamp reg_date;
    private double darange;
    
    public AppTableEntity(int id, String somedata, double lat, double lon, String keyelement,
            Timestamp date, double rang){
        
        this.ID = id;
        this.somedata = somedata;
        this.lat = lat;
        this.lon = lon;
        this.keyelement = keyelement;
        this.reg_date = date;
        this.darange = rang;
                
        
        
    }
    
    public AppTableEntity(int id, String somedata, double lat, double lon, String keyelement, double rang){
        this.ID = id;
        this.somedata = somedata;
        this.lat = lat;
        this.lon = lon;
        this.keyelement = keyelement;
        
        this.darange = rang;
        
    }
    
    public AppTableEntity(int id, String somedata, double lat, double lon){
        this.ID = id;
        this.somedata = somedata;
        this.lat = lat;
        this.lon = lon;
        
        this.darange = 5;
        
    }
    
    public int getID(){
        return this.ID;
    }
    
    public double getRang(){
        return this.darange;
    }
    
    public double getLat(){
        return this.lat;
    }
    
    public double getLon(){
        
        return this.lon;
    }
    
    public String getSomeData(){
        
        return this.somedata;
    }
    
    public String getKeyElement(){
        return this.keyelement;
    }
    public Timestamp getDate(){
        
        return this.reg_date;
    }
    
    
    public String toString(){
        
        return "{'id':'"+this.ID+"',"+"'somedata':'"+this.somedata+"',"+"'lat':'"+this.lat+"',"+"'lon':'"+this.lon+"',"
                +"'darange':'"+this.darange+"',"+"'keyelement':'"+this.keyelement+"',"
                +"'reg_data':'"+this.reg_date+"'}";
    }
    
    
}
