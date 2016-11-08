/*
 * USER: MODEL
 * Updated: 11/8/2016 1:24 AM
 */
package user;

import java.sql.*;
import javax.swing.*;
import db.DBUtilities;


/**
 *
 * @author zerimar
 */

public class UserModel {
    private int userID; 
    private String username;
    private String password;
    private String fName;
    private String mName;
    private String lName;
    private int age;
    private String birthdate;
    private char gender; 
    private String uType;
    private String status;
    
    int affectedRows;
    
    DBUtilities dbu;
    static Connection conn;
  
    
    public int getUserID() {
        return userID;
    }
    
    public void setUserID(int uID) {
        userID = uID;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String u) {
        username = u;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String p) {
        password = p;
    }
    
    public String getFname() {
        return fName;
    }
    
    public void setFname(String f) {
        fName = f;
    }
    
    public String getMname() {
        return mName;
    }
    
    public void setMname(String m) {
        mName = m;
    }
    
    public String getLname() {
        return lName;
    }
    
    public void setLname(String l) {
        lName = l;
    }
    
    public int getAge() {
        return age;
    }
    
    public void setAge(int a) {
        age = a;
    }
    
    public String getBdate() {
        return birthdate;
    }
    
    public void setBdate(String b) {
        birthdate = b;
    }
    
    public char getGender() {
        return gender;
    }
    
    public void setGender(char g) {
        gender = g;
    }
    
    public String getUtype() {
        return uType;
    }
    
    public void setUtype(String u) {
        uType = u;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String s) {
        status = s;
    }
    
    public int addUser(){
        String query;
        PreparedStatement prep;
        
        affectedRows = 0;
        status = "OK";
        try {
                //connection
                dbu = new DBUtilities();
                conn = dbu.connectToMySQL();
                
                
                query = "INSERT INTO USER (username, password, first_name, middle_name, last_name, age, birthdate, gender, user_type, status) VALUES(?,?,?,?,?,?,?,?,?,?)";
                    
                prep = conn.prepareStatement(query);
                prep.setString(1, username);
                prep.setString(2, password);
                prep.setString(3, fName);
                prep.setString(4, mName);
                prep.setString(5, lName);
                prep.setInt(6, age);
                prep.setString(7, birthdate);
                prep.setString(8, String.valueOf(gender));
                prep.setString(9, uType);
                prep.setString(10, status);
              
                
                
                affectedRows = prep.executeUpdate();
                System.out.println("Saved Row: " +affectedRows);
                conn.close();
        }catch(Exception e){
                JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                System.out.println(e.getMessage());
        }
        return affectedRows;
    }
    
    public ResultSet loadUser() {
        Statement stmt;
        ResultSet rs;
        String query;
        rs = null;
        
        try{
            //connection
            dbu = new DBUtilities();
            conn = dbu.connectToMySQL();
            

            query = "SELECT user_id, username, CONCAT(first_name, ' ', middle_name, ' ', last_name) AS Name, age, birthdate, gender, user_type FROM USER WHERE status = 'OK'";
            stmt = conn.createStatement();
            rs= stmt.executeQuery(query);
            //conn.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            System.out.println(e.getMessage());
        }
        
        return rs;  
    }
    
    public void loadOneUser(){
        Statement stmt;
        ResultSet rs;
        String query;
        
        rs=null;
       
        try{
            //connection
            dbu = new DBUtilities();
            conn = dbu.connectToMySQL();
            System.out.println("Loaded");
            
            query = "SELECT * FROM USER WHERE user_id ="+this.getUserID();
            stmt=conn.createStatement();
            rs=stmt.executeQuery(query);
            rs.next();
            this.setUserID(rs.getInt("user_id"));
            setUsername(rs.getString("username"));
            setPassword(rs.getString("password"));
            setFname(rs.getString("first_name"));
            setMname(rs.getString("middle_name"));
            setLname(rs.getString("last_name"));
            setAge(rs.getInt("age"));
            setBdate(rs.getString("birthdate"));
            setGender(rs.getString("gender").charAt(0));
            setUtype(rs.getString("user_type"));
            setStatus(rs.getString("status"));
          
            conn.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR",JOptionPane.ERROR_MESSAGE);
            System.out.println(e.getMessage());
        }
    }
    
    public int updateUser(){
        String query;
        PreparedStatement prep;
        affectedRows = 0;
        
        
        try{
            //connection
            dbu = new DBUtilities();
            conn = dbu.connectToMySQL();
            System.out.println("Updated");
            query = "UPDATE user SET username=?," 
                        + "password=?," 
                        + "first_name=?,"
                        + "middle_name=?," 
                        + "last_name=?," 
                        + "age=?," 
                        + "birthdate=?," 
                        + "gender=?," 
                        + "user_type=?," 
                        + "status=?" 
                        + "WHERE user_id=?";
            
            prep = conn.prepareStatement(query);
            prep.setString(1, username);
            prep.setString(2, password);
            prep.setString(3, fName);
            prep.setString(4, mName);
            prep.setString(5, lName);
            prep.setInt(6, age);
            prep.setString(7, birthdate);
            prep.setString(8, String.valueOf(gender));
            prep.setString(9, uType);
            prep.setString(10, status);
            prep.setInt(11, userID);
            
            
            
            affectedRows = prep.executeUpdate();
            System.out.println("Updated Row: "+affectedRows);
            conn.close();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR",JOptionPane.ERROR_MESSAGE);
            System.out.println(e.getMessage());
        }
        return affectedRows;
    }
    
    public int deleteUser(){
        String query;
        PreparedStatement prep;
        affectedRows = 0;
        status = "DELETED";
        
        try{
            //connection
            dbu = new DBUtilities();
            conn = dbu.connectToMySQL();
            
            query = "UPDATE USER SET status=?" 
                        + "WHERE user_id=?";
            
            prep = conn.prepareStatement(query);
            prep.setString(1, status);
            prep.setInt(2, userID);
            
            affectedRows = prep.executeUpdate();
            System.out.println("Deleted Row: "+affectedRows);
            conn.close();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR",JOptionPane.ERROR_MESSAGE);
            System.out.println(e.getMessage());
        }
        return affectedRows;
    }
}
