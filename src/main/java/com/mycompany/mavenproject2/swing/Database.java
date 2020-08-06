/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject2.swing;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author wahin
 */
public class Database {
     public Connection con;
     public Database(String url)
     {
         getConnection(url);
     }
     public  void getConnection(String url)
     {
         try {
             Class.forName("com.mysql.cj.jdbc.Driver");
             con = DriverManager.getConnection(url,"root","");
         } catch (ClassNotFoundException | SQLException ex) {
            // Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
         }
     }
     public void Insert_new_user(String name,String email,String username,String password,int balance,String contact)
     {
         try {
             PreparedStatement stmt;
             stmt = con.prepareStatement("INSERT INTO students values(?,?,?,?,?,?,?)");
             stmt.setString(1, name);
             stmt.setString(2, email);
             stmt.setString(3, username);
             stmt.setString(4, password);
             stmt.setInt(5,balance);
             stmt.setString(6,contact);
             stmt.setInt(7,0);
             stmt.executeUpdate();
             System.out.println(password);
         } catch (SQLException ex) {
             Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
         }
     }
     
     public boolean check_username_password(String username,String password)
     {
         try {
             PreparedStatement stmt;
             stmt = con.prepareStatement("Select Password from students where username=?");
             stmt.setString(1, username);
             //System.out.println(username);
             ResultSet rs = stmt.executeQuery();
             rs.next();
             String real_passwd = rs.getString("Password");
             if(real_passwd.equals(password))
                 return true;
         } catch (SQLException ex) {
             Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
         }
          return false;
     }
     public boolean check_username_exists(String username) //checks if username already exists 
     {
         try {
             PreparedStatement stmt;
             System.out.println(username);
             stmt = con.prepareStatement("Select password from students where username=?");
             stmt.setString(1, username);
             ResultSet rs = stmt.executeQuery();
             if(rs.next())
                 return true; //returns true if it exists
         } catch (SQLException ex) {
             Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
         }
         return false;
     }
     public ResultSet get_user_data(String username) throws NullPointerException
     {
         ResultSet rs=null;
         try {
             PreparedStatement stmt;
             stmt = con.prepareStatement("Select * from students where username=?");
             stmt.setString(1,username);
             rs = stmt.executeQuery();
             return rs;
         } catch (SQLException ex) {
            // Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
         } 
         return rs;
     }
     
     public ResultSet get_all_data() throws NullPointerException
     {
         ResultSet rs = null;
         try{
             Statement stmt = con.createStatement();
             rs = stmt.executeQuery("Select * from students");
         } catch (SQLException ex) {
           //  Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
         }
         return rs;
     }
     public void updateBal(String username,int bal,int curr_bal)
     {
         try {
             int new_bal = curr_bal+bal;
             System.out.println("new bal is "+new_bal+" initial was "+curr_bal);
             PreparedStatement stmt;
             stmt = con.prepareStatement("Update students Set Balance=? where username=? ");
             stmt.setInt(1,new_bal);
             stmt.setString(2,username);
             stmt.executeUpdate();
         } catch (SQLException ex) {
             Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
         } 
     }
     public int getBal(String username)
     {
         int bal=0;
         try {
             PreparedStatement stmt;
             stmt = con.prepareStatement("Select Balance from students where username=?");
             stmt.setString(1,username);
             ResultSet rs = stmt.executeQuery();
             rs.next();
             bal = rs.getInt("Balance");
         } catch (SQLException ex) {
             Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
         }
         return bal;   
     }
     public ResultSet getLocData(String loc)
     {
         ResultSet rs=null;
         try {
             PreparedStatement stmt;
             stmt = con.prepareStatement("Select * from Locations where Locations=?");
             stmt.setString(1, loc);
             rs = stmt.executeQuery();
             rs.next();
             return rs;
         } catch (SQLException ex) {
             Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
         return rs;
     }
     public ResultSet getDriver()
     {
         ResultSet rs=null;
         try {
             PreparedStatement stmt;
             stmt = con.prepareStatement("Select * from drivers");
             rs = stmt.executeQuery();
             return rs;
         } catch (SQLException ex) {
             Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
     }
     public ResultSet getFreq()
     {
         try {
             PreparedStatement stmt;
             stmt = con.prepareStatement("SELECT Location, COUNT(Location)AS Frequency FROM drivers WHERE Status=0 GROUP BY Location ORDER BY COUNT(Location) DESC" );
             ResultSet rs = stmt.executeQuery();
             return rs;
         } catch (SQLException ex) {
             Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
         }
         return null;
     }
     public void fromxtoy(String x,String y)
     {
         try {
             PreparedStatement stmt;
             stmt = con.prepareStatement("Update drivers Set Location=? where Location=? AND Status=0 And Rating>0 Limit 1");
             stmt.setString(1,y);
             stmt.setString(2,x);
             stmt.executeUpdate();
         } catch (SQLException ex) {
             Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
         }
     }
    public ResultSet isAvailable(int type,String loc)
    {
         try {
             PreparedStatement stmt;
             stmt = con.prepareStatement("Select * from drivers where Status=0 And Location=? And CarType=? And Rating>0 Order BY Rating Desc");
             stmt.setString(1,loc);
             stmt.setInt(2,type);
             return stmt.executeQuery();
         } catch (SQLException ex) {
             Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
         }
         return null;
    }
    public void changeStatus(String carnum,int status)
    {
         try {
             PreparedStatement stmt;
             stmt = con.prepareStatement("Update drivers Set Status=? where CarNum=?");
             stmt.setInt(1,status);
             stmt.setString(2,carnum);
             stmt.executeUpdate();
         } catch (SQLException ex) {
             Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
         }
             
    }
     public void fromxtoy(String x,String y,String CarNum)
     {
         try {
             PreparedStatement stmt;
             stmt = con.prepareStatement("Update drivers Set Location=? where Location=? AND CarNum=? and Rating>0 Limit 1");
             stmt.setString(1,y);
             stmt.setString(2,x);
             stmt.setString(3, CarNum);
             stmt.executeUpdate();
         } catch (SQLException ex) {
             Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
         }
     }
     public int getCurrBal(String username)
     {
         try {
             PreparedStatement stmt;
             stmt = con.prepareStatement("Select Balance from students where username=?");
             stmt.setString(1, username);
             ResultSet rs = stmt.executeQuery();
             rs.next();
             return rs.getInt("Balance");
         } catch (SQLException ex) {
             Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
         }
         return 0;
     }
     public void changeStatusStudent(String username,int status)
    {
         try {
             PreparedStatement stmt;
             stmt = con.prepareStatement("Update students Set Status=? where username=?");
             stmt.setInt(1,status);
             stmt.setString(2,username);
             stmt.executeUpdate();
         } catch (SQLException ex) {
             Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
         }
             
    }
     
    public boolean isInCab(String username)  
    {
        try {
             PreparedStatement stmt;
             stmt = con.prepareStatement("Select * from students where username=?");
             stmt.setString(1,username);
             ResultSet rs = stmt.executeQuery();
             rs.next();
             if(rs.getInt("Status")==1)
                 return true;
         } catch (SQLException ex) {
             Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
         }
        return false;
    }
}
