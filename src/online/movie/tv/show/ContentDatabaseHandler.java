/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package online.movie.tv.show;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;


public class ContentDatabaseHandler {
    Connection connect = null;
    
    public void connectDatabase(){
        try{
//            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://localhost/omatsv_db","root","");
            System.out.println("Successfully Connected to Mysql");

        }catch(Exception e){
            System.out.println("Not Connected..");
            e.printStackTrace();
        }
    }
    
    public ResultSet getContent(String query){
        ResultSet resultSet = null;
        try{
            Statement statement ;
            statement = connect.createStatement();
            resultSet = statement.executeQuery(query);
            System.out.println("Successfully Done Query..");
        }catch(Exception e){
            System.out.println("Error in Query..");
            e.printStackTrace();
        }
        return resultSet;
    }
    public ResultSet showUserDetails(String uname){
         ResultSet resultSet = null;
        
        try {
        
            String query ="select id,uname,email,mobile,password,country,gender from user_data where uname= ?";
                    
            PreparedStatement pStatement = connect.prepareStatement(query);
            pStatement.setString(1, uname);
           
            resultSet=pStatement.executeQuery();
            
            
            
        } catch (SQLException ex) {
            Logger.getLogger(AdminDatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultSet;
    }
    
    public void updateUserData(Integer id,String uname, String email, String mobile,String password, String Country,char Gender){
        try{
            String query = "UPDATE user_data SET uname= ?, email= ?, mobile= ?, password= ?, country= ?, gender= ? where id= ?";
            PreparedStatement pStatement = connect.prepareStatement(query);
            pStatement.setString(1, uname);
            pStatement.setString(2, email); 
            pStatement.setString(3, mobile);
            pStatement.setString(4, password);
            pStatement.setString(5, Country);
            pStatement.setString(6, String.valueOf(Gender));
            pStatement.setInt(7, id);
            pStatement.executeUpdate();
            System.out.println("Successfully updated..");
        }catch(Exception e){
            System.out.println("Error in updating");
            e.printStackTrace();
        }
    }
    
    public void insertData(String name,String dept,String credit){
        try{
            String query = "INSERT INTO student (student_name,student_dept,student_credit) values(?,?,?)";
            PreparedStatement pStatement = connect.prepareStatement(query);
            pStatement.setString(1, name);
            pStatement.setString(2, dept);
            pStatement.setString(3, credit);
           
            pStatement.executeUpdate();
            
            System.out.println("Successfully Inserted..");
            
            
        }catch(Exception e){
            System.out.println("Error in inserting");
            e.printStackTrace();
        }
    }
}