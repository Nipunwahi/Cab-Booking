/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject2.swing;

/**
 *
 * @author wahin
 */
import java.sql.*;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
public class Location {
 
    String name;
    int x_cod;
    int y_cod;
    
    public static int get_distance(String start,String end)
    {
        try {
            Database db =  new Database("jdbc:mysql://localhost:3306/mydb?useLegacyDatetimeCode=false&serverTimezone=Asia/Kolkata");
            ResultSet rs_end =  db.getLocData(end);
            ResultSet rs_start =  db.getLocData(start);
            int x_start = rs_start.getInt("x_cod");
            int y_start = rs_start.getInt("y_cod");
            int x_end = rs_end.getInt("x_cod");
            int y_end = rs_end.getInt("y_cod");
            return java.lang.Math.abs(x_start + y_start -  x_end - y_end);
        } catch (SQLException ex) {
            Logger.getLogger(Location.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    public static int get_price(String start,String end,int x)
    {
        int dist = get_distance(start, end);
        int price = 5 + x;
        return price*dist;
    }
}
