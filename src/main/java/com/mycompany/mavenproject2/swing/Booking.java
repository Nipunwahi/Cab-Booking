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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
public class Booking {
    static int rides=0;
    public static void redistribute()
    {
        if(rides%1==0)
        {
            try {
                Database db = new Database("jdbc:mysql://localhost:3306/mydb?useLegacyDatetimeCode=false&serverTimezone=Asia/Kolkata");
                ResultSet rs = db.getFreq();
                rs.next();
                String loc = rs.getString("Location");
                System.out.println(loc);
                String least_loc =null;
                while(rs.next())
                {
                    least_loc = rs.getString("Location");
                    System.out.println(least_loc);
                }
                
                db.fromxtoy(loc, least_loc);
            } catch (SQLException ex) {
                Logger.getLogger(Booking.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public static boolean sameloc(String start,String end)
    {
        return start.equals(end);
    }
    public static boolean checkSufficientBalance(String username,int fare)
    {
        Database db = new Database("jdbc:mysql://localhost:3306/mydb?useLegacyDatetimeCode=false&serverTimezone=Asia/Kolkata");
        int bal = db.getBal(username);
        return !(bal<300||bal<fare);
    }
    
}
