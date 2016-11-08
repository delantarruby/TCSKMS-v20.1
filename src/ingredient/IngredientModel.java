/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ingredient;
import java.sql.*;
import javax.swing.*;
import db.DBUtilities;
import java.text.SimpleDateFormat;
/**
 *
 * @author zany
 */
public class IngredientModel {
    private int id;
    private String name;
    private String supplier;
    private String date;
    private String category;
    private float qty;
    private float price;
    private String stat;
    
    DBUtilities dbu;
    static Connection conn;
    
    
    public int getID(){
        return id;
    }
    
    public void setID(int i){
        id=i;
    }
    
    public String getName(){
        return name;
    }
    
    public void setName(String i){
        name=i;
    }
    
    public String getSupplier(){
        return supplier;
    }
    public void setSupplier(String i){
        supplier=i;
    }
    
    public String getDate(){
        return date;
    }
    
    public void setDate(String i){
        date=i;
    }
    
    public String getCategory(){
        return category;
    }
    
    public void setCategory(String i){
        category=i;
    }
    
    public float getQty(){
        return qty;
    }
    
    public void setQty(float i){
        qty=i;
    }
    
    public float getPrice(){
        return price;
    }
    
    public void setPrice(float i){
        price=i;
    }
    
    public String getStatus(){
        return stat;
    }
    
    public void setStatus(String i){
        stat=i;
    }
    

    
    public int addIngredient(){
        int affectedRows;
        String query;
        affectedRows = 0;
        
        try {
                dbu = new DBUtilities();
                conn = dbu.connectToMySQL();
                System.out.println("Added");
                
                query = "INSERT INTO ingredient (date_time_edited,ingredient_name, supplier_name, category, qty, price, status) values(STR_TO_DATE(?,'%c/%e/%Y %T'),?,?,?,?,?,?)";
                
                PreparedStatement prep = conn.prepareStatement(query);
                prep.setString(1,date);
                prep.setString(2, name);
                prep.setString(3, supplier);
                prep.setString(4, category);
                prep.setFloat(5, qty);
                prep.setFloat(6, price);
                prep.setString(7, stat);
                
                
                affectedRows = prep.executeUpdate();
                System.out.println("Saved Row: " +affectedRows);
                conn.close();
        }catch(Exception e){
                JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                System.out.println(e.getMessage());
        }
        return affectedRows;
    }
    
    public ResultSet loadIngredient(){
        ResultSet rs;
        Statement stmt;
        String query;
        rs = null;
        
        try{
            dbu = new DBUtilities();
            conn = dbu.connectToMySQL();
            System.out.println("List");
            query = "SELECT ingredient_id, ingredient_name,supplier_name,date_time_edited,category,qty,price FROM ingredient WHERE status = 'OK' ";
            stmt = conn.createStatement();
            rs= stmt.executeQuery(query);
            //conn.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            System.out.println(e.getMessage());
        }
    return rs;  
    }
    
    public int deleteIngredient(){
        int affectedRows;
        String query;
        affectedRows=0;
        
        try{
            dbu = new DBUtilities();
            conn = dbu.connectToMySQL();
            System.out.println("Status Changed to DELETED");
            query = "UPDATE ingredient SET status='DELETED'WHERE ingredient_id =?";
                       
            
            PreparedStatement prepStatement = conn.prepareStatement(query);
            prepStatement.setInt(1,id);
           
            affectedRows = prepStatement.executeUpdate();
            System.out.println("Updated Row: "+affectedRows);
            conn.close();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR",JOptionPane.ERROR_MESSAGE);
            System.out.println(e.getMessage());
        }
        return affectedRows;
    }
    
    public int updateIngredient(){
        String query;
        int affectedRows;
        affectedRows = 0;
        
        try{
            dbu = new DBUtilities();
            conn = dbu.connectToMySQL();
            System.out.println("Updated");
            query = "UPDATE ingredient SET ingredient_name=?," 
                        + "supplier_name=?," 
                        + "category=?,"
                        + "qty=?," 
                        + "price=?,"
                        + "status=?"
                        +"WHERE ingredient_id =?";
                       
            
            PreparedStatement prepStatement = conn.prepareStatement(query);
            prepStatement.setString(1,name);
            prepStatement.setString(2,supplier);
            prepStatement.setString(3,category);
            prepStatement.setFloat(4,qty);
            prepStatement.setFloat(5,price);
            prepStatement.setString(6,stat);
            prepStatement.setInt(7,id);
            affectedRows = prepStatement.executeUpdate();
            System.out.println("Updated Row: "+affectedRows);
            conn.close();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR",JOptionPane.ERROR_MESSAGE);
            System.out.println(e.getMessage());
        }
        return affectedRows;
    }
   
   public void loadOneIngredient(){
        ResultSet rs;
        Statement stmt;
        String query;
        
        rs=null;
        
        try{
            dbu = new DBUtilities();
            conn = dbu.connectToMySQL();
            System.out.println("Loaded");
         
            query = "SELECT * FROM ingredient WHERE ingredient_id ="+this.getID();
            stmt=conn.createStatement();
            rs=stmt.executeQuery(query);
            rs.next();
            this.setID(rs.getInt("ingredient_id"));
            setName(rs.getString("ingredient_name"));
            setSupplier(rs.getString("supplier_name"));
            setCategory(rs.getString("category"));
            setQty(rs.getFloat("qty"));
            setPrice(rs.getFloat("price"));
            conn.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR",JOptionPane.ERROR_MESSAGE);
            System.out.println(e.getMessage());
        }
    }
     
   
}
