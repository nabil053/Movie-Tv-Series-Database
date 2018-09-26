/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package online.movie.tv.show;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class AdminDatabaseHandler {
    Connection connect = null;
    Integer user_id;
    public static byte[] movieImg, tvSeriesImg;
    
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
   
    public ResultSet adminLoginQuery(String uname, String upass){
            ResultSet resultSet = null;
        try{

            String query = "select uname,password from admin_data where uname=? and password=? and type='admin' or type='super_admin'";
            PreparedStatement pStatement = connect.prepareStatement(query);
            pStatement.setString(1, uname);
           pStatement.setString(2, upass);
           resultSet = pStatement.executeQuery();

            System.out.println("Successfully Done Query..");    
        }catch(Exception e){
            System.out.println("Error in Query..");
            e.printStackTrace();
        }        
        return resultSet;
    }
   
    
    public ResultSet userLoginQuery(String uname, String upass){
            ResultSet resultSet = null;
        try{

            String query = "select uname, password from user_data where uname=? and password=?";
            PreparedStatement pStatement = connect.prepareStatement(query);
            pStatement.setString(1, uname);
           pStatement.setString(2, upass);
           resultSet = pStatement.executeQuery();

            System.out.println("Successfully Done Query..");    
        }catch(Exception e){
            System.out.println("Error in Query..");
            e.printStackTrace();
        }        
        return resultSet;
    }
    public ResultSet getIsSubscribeValue(String uname){
            ResultSet resultSet = null;
        try {
            String query ="SELECT is_subscribed FROM user_data WHERE uname= ?";    
            PreparedStatement pStatement = connect.prepareStatement(query);
            pStatement.setString(1, uname);
            resultSet = pStatement.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(AdminDatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultSet;
    }
    
    public boolean insertUserRegData(String uname, String email, String mobile,String password, String Country,char Gender){
        try{
            String query = "INSERT INTO user_data (uname,email,mobile,password,country,gender) values(?,?,?,?,?,?)";
            PreparedStatement pStatement = connect.prepareStatement(query);
            pStatement.setString(1, uname);
            pStatement.setString(2, email); 
            pStatement.setString(3, mobile);
            pStatement.setString(4, password);
            pStatement.setString(5, Country);
            pStatement.setString(6, String.valueOf(Gender));
            pStatement.executeUpdate();
            
            System.out.println("Successfully Inserted..");
        }catch(Exception e){
            
            System.out.println("Error in inserting");
            e.printStackTrace();
            return  false;
        }
        return true;
    }
    
    public boolean insertAdminRegData(String uname, String email, String mobile,String password, String Country,char Gender,String type){
        try{
            String query = "INSERT INTO admin_data (uname,email,mobile,password,country,gender,type) values(?,?,?,?,?,?,?)";
            PreparedStatement pStatement = connect.prepareStatement(query);
            pStatement.setString(1, uname);
            pStatement.setString(2, email); 
            pStatement.setString(3, mobile);
            pStatement.setString(4, password);
            pStatement.setString(5, Country);
            pStatement.setString(6, String.valueOf(Gender));
            pStatement.setString(7, type);
            pStatement.executeUpdate();
            System.out.println("Successfully Inserted..");
        }catch(Exception e){
            System.out.println("Error in inserting");
            e.printStackTrace();
            return  false;
        }
        return true;
    }
    
    
    
    public void showAdminDetails(JTable AdminData, String valueToSearch){
    
        
        try {
        
            String query ="select * from admin_data where concat(uname,email,mobile,country,type)LIKE ?";
                    
            PreparedStatement pStatement = connect.prepareStatement(query);
          
            pStatement.setString(1, "%"+valueToSearch+"%");
            ResultSet resultSet=pStatement.executeQuery();
            
            DefaultTableModel model = (DefaultTableModel) AdminData.getModel();
            
            Object[] row;
            
            while (resultSet.next()) {                
                row = new Object[8];
                
                row[0] = resultSet.getString(1);
                 row[1] = resultSet.getString(2);
                  row[2] = resultSet.getString(3);
                   row[3] = resultSet.getString(4);
                    row[4] = resultSet.getString(5);
                     row[5] = resultSet.getString(6);
                      row[6] = resultSet.getString(7);
                      row[7] = resultSet.getString(8);
                      model.addRow(row);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(AdminDatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void showUserDetails(JTable AdminData, String valueToSearch){
    
        
        try {
        
            String query ="select * from user_data where concat(uname,email,mobile,country)LIKE ?";
                    
            PreparedStatement pStatement = connect.prepareStatement(query);
           pStatement.setString(1, "%"+valueToSearch+"%");
            ResultSet resultSet=pStatement.executeQuery();
            
            DefaultTableModel model = (DefaultTableModel) AdminData.getModel();
            
            Object[] row;
            
            while (resultSet.next()) {                
                row = new Object[7];
                
                row[0] = resultSet.getString(1);
                 row[1] = resultSet.getString(2);
                  row[2] = resultSet.getString(3);
                   row[3] = resultSet.getString(4);
                    row[4] = resultSet.getString(5);
                     row[5] = resultSet.getString(6);
                      row[6] = resultSet.getString(7);
                      
                      model.addRow(row);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(AdminDatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void updateAdminData(Integer id, String uname, String email, String mobile,String password, String Country,char Gender,String type){
        try{
            String query = "UPDATE admin_data SET uname= ?, email= ?, mobile= ?, password= ?, country= ?, gender= ?, type= ? where id= ?";
            PreparedStatement pStatement = connect.prepareStatement(query);
            pStatement.setString(1, uname);
            pStatement.setString(2, email); 
            pStatement.setString(3, mobile);
            pStatement.setString(4, password);
            pStatement.setString(5, Country);
            pStatement.setString(6, String.valueOf(Gender));
            pStatement.setString(7, type);
            pStatement.setInt(8, id);
            pStatement.executeUpdate();
            System.out.println("Successfully updated..");
        }catch(Exception e){
            System.out.println("Error in updating");
            e.printStackTrace();
        }
    }
    
    public void deleteAdminData(Integer id){
        try{
            String query = "DELETE FROM admin_data where id= ?";
            PreparedStatement pStatement = connect.prepareStatement(query);
            
            pStatement.setInt(1, id);
            pStatement.executeUpdate();
            System.out.println("Successfully Deleted..");
        }catch(Exception e){
            System.out.println("Error in deleting");
            e.printStackTrace();
        }
    }
    
     public void deleteUserData(Integer id){
        try{
            String query = "DELETE FROM user_data where id= ?";
            PreparedStatement pStatement = connect.prepareStatement(query);
            
            pStatement.setInt(1, id);
            pStatement.executeUpdate();
            System.out.println("Successfully Deleted..");
        }catch(Exception e){
            System.out.println("Error in deleting");
            e.printStackTrace();
        }
    }
     
     
    public boolean insertMovieData(String mTitle,String mGenre,String mLanguage,String mCast,
            String mSynopsis,String iframeLink,Integer mReleaseDate,String mDirectedBy,Integer mRuntime,Double mRating,String coverFilePath){
        try{
            String query = "INSERT INTO content (name,genre,language,cast,synopsis,rating,covers,iframe_link,release_date,content_type) values(?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement pStatement = connect.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
            
            InputStream is = new FileInputStream(new File(coverFilePath));
            
            pStatement.setString(1, mTitle);
            pStatement.setString(2, mGenre); 
            pStatement.setString(3, mLanguage);
            pStatement.setString(4, mCast);
            pStatement.setString(5, mSynopsis);
            pStatement.setDouble(6, mRating);
            pStatement.setBlob(7, is);
            pStatement.setString(8, iframeLink);
            pStatement.setInt(9, mReleaseDate);
            pStatement.setString(10, AdminPanel.contentType);
            pStatement.executeUpdate();
            
            ResultSet rs = pStatement.getGeneratedKeys();
            int generatedKey = 0;
            if (rs.next()) {
                generatedKey = rs.getInt(1);
            }
            
            String query2 = "INSERT INTO content_movie (content_id,directed_by,runtime) values(?,?,?)";
            PreparedStatement pStatement2 = connect.prepareStatement(query2);
             pStatement2.setInt(1, generatedKey); 
            pStatement2.setString(2, mDirectedBy); 
            pStatement2.setInt(3, mRuntime);
            pStatement2.executeUpdate();
            
            System.out.println("Movie Successfully Inserted..");
        }catch(Exception e){
            
            System.out.println("Error in inserting");
            e.printStackTrace();
            return  false;
        }
        return true;
    }
    
     public boolean updateMovieData(Integer id, String mContentType,String mTitle,String mGenre,String mLanguage,String mCast,
            String mSynopsis,String iframeLink,Integer mReleaseDate,String mDirectedBy,Integer mRuntime,Double mRating, String coverFileName){
        try{
            String query = "UPDATE content as c, content_movie as m SET c.name= ?,c.genre= ?,c.language= ?,c.cast= ?,c.synopsis= ?,c.rating= ?,c.covers= ?,c.iframe_link= ?,c.release_date= ?,c.content_type= ?,m.directed_by= ?,m.runtime=? where c.id=m.content_id AND c.id= ?";
            //SELECT c.id,c.name,c.genre,c.language,c.cast,c.synopsis,c.rating,c.covers,c.iframe_link,c.writers
            //,c.content_type,m.directed_by,m.runtime from content as c JOIN content_movie as m ON c.id=m.content_id WHERE concat(c.name) Like ?
            InputStream is = new FileInputStream(new File(coverFileName));
            
            
            PreparedStatement pStatement = connect.prepareStatement(query);
            pStatement.setString(1, mTitle);
            pStatement.setString(2, mGenre); 
            pStatement.setString(3, mLanguage);
            pStatement.setString(4, mCast);
            pStatement.setString(5, mSynopsis);
            pStatement.setDouble(6, mRating);
            pStatement.setBlob(7, is); 
            pStatement.setString(8, iframeLink);
            pStatement.setInt(9, mReleaseDate);
            pStatement.setString(10, mContentType);
            pStatement.setString(11, mDirectedBy);
            pStatement.setInt(12, mRuntime);
            pStatement.setInt(13, id);
            pStatement.executeUpdate();
            System.out.println("Successfully updated Movie Data..");
        }catch(Exception e){
            System.out.println("Error in updating");
            e.printStackTrace();
            return  false;
        }
        return  true;
    }
    
    public void deleteMovieData(Integer id) {
       try{
            String query = "DELETE FROM content where id= ? AND content_type='movie'";
            PreparedStatement pStatement = connect.prepareStatement(query);
            
            pStatement.setInt(1, id);
            pStatement.executeUpdate();
            System.out.println("Successfully Deleted..");
        }catch(Exception e){
            System.out.println("Error in deleting");
            e.printStackTrace();
        }
    }
    
    public void showMovieData(JTable movieData, String valueToSearch){
    
        
        try {
        
            String query ="SELECT c.id,c.name,c.genre,c.language,c.cast,c.synopsis,c.rating,c.covers,c.iframe_link,c.release_date,c.content_type,m.directed_by,m.runtime from content as c JOIN content_movie as m ON c.id=m.content_id WHERE concat(c.name) Like ?";
                    
            PreparedStatement pStatement = connect.prepareStatement(query);
           pStatement.setString(1, "%"+valueToSearch+"%");
            ResultSet resultSet=pStatement.executeQuery();
            
            DefaultTableModel model = (DefaultTableModel) movieData.getModel();
            
            Object[] row;
            
            while (resultSet.next()) {                
                row = new Object[13];
                
                row[0] = resultSet.getInt(1);
                 row[1] = resultSet.getString(2);
                  row[2] = resultSet.getString(3);
                   row[3] = resultSet.getString(4);
                    row[4] = resultSet.getString(5);
                     row[5] = resultSet.getString(6);
                      row[6] = resultSet.getDouble(7);
                      
                      //image
                      row[7] = resultSet.getBytes(8);
                      movieImg = resultSet.getBytes("covers");
                      
                      row[8] = resultSet.getString(9);
                      row[9] = resultSet.getInt(10);
                      row[10] = resultSet.getString(11);
                      row[11] = resultSet.getString(12);
                      row[12] = resultSet.getInt(13);
                      
                      model.addRow(row);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(AdminDatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean insertTvSeriesData(String tsTitle,String tsLanguage,String tsGenre,String tsCast,String tsSynopsis,
            String tsEpisode,String tsSeason,String tsShowRunner,String tsCover,String tsIframeLink,
            Integer tsSeasonNo,Integer tsEpisodeNo,Integer tsRuntime,Double tsRating,Integer tsReleaseDate,
            String coverFilePath){
        try{
            String query = "INSERT INTO content (name,genre,language,cast,synopsis,rating,covers,iframe_link,release_date,content_type) values(?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement pStatement = connect.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
            
            InputStream is = new FileInputStream(new File(coverFilePath));
            
            pStatement.setString(1, tsTitle);
            pStatement.setString(2, tsGenre); 
            pStatement.setString(3, tsLanguage);
            pStatement.setString(4, tsCast);
            pStatement.setString(5, tsSynopsis);
            pStatement.setDouble(6, tsRating);
            
            pStatement.setBlob(7, is);
            
            pStatement.setString(8, tsIframeLink);
            pStatement.setInt(9, tsReleaseDate);
            pStatement.setString(10, AdminPanel.contentType);
            pStatement.executeUpdate();
            
            ResultSet rs = pStatement.getGeneratedKeys();
            int content_id = 0;
            if (rs.next()) {
                content_id = rs.getInt(1);
            }
            
            String query2 = "INSERT INTO content_tv (content_id,no_of_seasons,show_runner) values(?,?,?)";
            PreparedStatement pStatement2 = connect.prepareStatement(query2,Statement.RETURN_GENERATED_KEYS);
            pStatement2.setInt(1, content_id); 
            pStatement2.setInt(2,tsSeasonNo); 
            pStatement2.setString(3, tsShowRunner);
            pStatement2.executeUpdate();

            ResultSet rs2 = pStatement2.getGeneratedKeys();
            int tv_Series_id = 0;
            if (rs2.next()) {
                tv_Series_id = rs2.getInt(1);
            }
            
            String query3 = "INSERT INTO content_tv_seasons (name,no_of_episodes,tv_Series_id) values(?,?,?)";
            PreparedStatement pStatement3 = connect.prepareStatement(query3,Statement.RETURN_GENERATED_KEYS);
              
            pStatement3.setString(1, tsSeason); 
            pStatement3.setInt(2, tsEpisodeNo);
            pStatement3.setInt(3,tv_Series_id);
            pStatement3.executeUpdate();
            
            ResultSet rs3 = pStatement3.getGeneratedKeys();
            int tv_season_id = 0;
            if (rs3.next()) {
                tv_season_id = rs3.getInt(1);
            }
            
            String query4 = "INSERT INTO content_tv_episode (name,runtime,tv_Series_id,	tv_season_id) values(?,?,?,?)";
            PreparedStatement pStatement4 = connect.prepareStatement(query4);
            
            pStatement4.setString(1, tsEpisode); 
            pStatement4.setInt(2, tsRuntime);
             pStatement4.setInt(3, tv_Series_id); 
             pStatement4.setInt(4, tv_season_id); 
            pStatement4.executeUpdate();
            System.out.println("Tv Series Successfully Inserted..");
        }catch(Exception e){
            
            System.out.println("Error in inserting");
            e.printStackTrace();
            return  false;
        }
        return true;
    }
    
 
     public boolean updateTvSeriesData(Integer id, String mContentType,String tvTitle,String tvGenre,String tvLanguage,String tvCast,
            String tvSynopsis,String tvIframeLink,Integer tvReleaseDate,Integer tvSeriesID,Integer noOfSeasons,String tvShowRunner,
            Integer tvSeasonId, String tvSeasonName, String tvEpisodeName,Integer tvNoOfEpisode,Integer tvEpisodeId,
            Integer tvRuntime,Double tvRating,String coverFileName){
        try{
            String query = "UPDATE content as c, content_tv as t, content_tv_seasons as s, content_tv_episode as e  SET c.name= ?,c.genre= ?,c.language= ?,c.cast= ?,c.synopsis= ?,c.rating= ?,c.covers= ?,c.iframe_link= ?,c.release_date= ?,c.content_type= ?,t.tv_series_id= ?,t.no_of_seasons= ?,t.show_runner= ?,s.id= ?,s.name= ?,s.no_of_episodes= ?,e.id= ?,e.name= ?,e.runtime= ? where c.id=t.content_id AND t.tv_series_id=s.tv_Series_id AND t.tv_series_id=e.tv_series_id AND s.id=e.tv_season_id AND c.id= ?";
           //SELECT c.id,c.name,c.genre,c.language,c.cast,c.synopsis,c.rating,c.covers,c.iframe_link,c.writers,c.content_type,t.tv_series_id,t.no_of_seasons,t.show_runner,s.id,s.name,s.no_of_episodes,e.id,e.name,e.runtime FROM content as c JOIN content_tv as t ON c.id=t.content_id JOIN content_tv_seasons as s ON t.tv_series_id=s.tv_Series_id JOIN content_tv_episode as e  ON t.tv_series_id=e.tv_series_id AND s.id=e.tv_season_id WHERE concat(c.name) Like ?
            //UPDATE content as c, content_movie as m SET c.name= ?,c.genre= ?,c.language= ?,c.cast= ?,c.synopsis= ?,c.rating= ?,c.covers= ?,c.iframe_link= ?,c.writers= ?,c.content_type= ?,m.directed_by= ?,m.runtime=? where c.id=m.content_id AND c.id= ?
            PreparedStatement pStatement = connect.prepareStatement(query);
            InputStream is = new FileInputStream(new File(coverFileName));
            
            pStatement.setString(1, tvTitle);
            pStatement.setString(2, tvGenre); 
            pStatement.setString(3, tvLanguage);
            pStatement.setString(4, tvCast);
            pStatement.setString(5, tvSynopsis);
            pStatement.setDouble(6, tvRating);
            pStatement.setBlob(7, is); 
            pStatement.setString(8, tvIframeLink);
            pStatement.setInt(9, tvReleaseDate);
            pStatement.setString(10, mContentType);
            pStatement.setInt(11, tvSeriesID); 
            pStatement.setInt(12, noOfSeasons);
            pStatement.setString(13, tvShowRunner);
            pStatement.setInt(14, tvSeasonId);
            pStatement.setString(15, tvSeasonName);
            pStatement.setInt(16, tvNoOfEpisode);
            pStatement.setInt(17, tvEpisodeId);
            pStatement.setString(18, tvEpisodeName);
            pStatement.setInt(19, tvRuntime);
            pStatement.setInt(20, id);
            pStatement.executeUpdate();
            System.out.println("Successfully updated..");
        }catch(Exception e){
            System.out.println("Error in updating");
            e.printStackTrace();
            
            return  false;
        }
        return true;
    }

    
    public void deleteTvSeriesData(Integer id) {
       try{
            String query = "DELETE FROM content where id= ? AND content_type='tv_series'";
            PreparedStatement pStatement = connect.prepareStatement(query);
            
            pStatement.setInt(1, id);
            pStatement.executeUpdate();
            System.out.println("Successfully Deleted..");
        }catch(Exception e){
            System.out.println("Error in deleting");
            e.printStackTrace();
        }
    }

    public void showTvSeriesData(JTable tvSeriesData, String valueToSearch){
    
        
        try {
        
            String query ="SELECT c.id,c.name,c.genre,c.language,c.cast,c.synopsis,c.rating,c.covers,c.iframe_link,c.release_date,c.content_type,t.tv_series_id,t.no_of_seasons,t.show_runner,s.id,s.name,s.no_of_episodes,e.id,e.name,e.runtime FROM content as c JOIN content_tv as t ON c.id=t.content_id JOIN content_tv_seasons as s ON t.tv_series_id=s.tv_Series_id JOIN content_tv_episode as e  ON t.tv_series_id=e.tv_series_id AND s.id=e.tv_season_id WHERE concat(c.name) Like ?";
                    
            PreparedStatement pStatement = connect.prepareStatement(query);
           pStatement.setString(1, "%"+valueToSearch+"%");
            ResultSet resultSet=pStatement.executeQuery();
            
            DefaultTableModel model = (DefaultTableModel) tvSeriesData.getModel();
            
            Object[] row;
            
            while (resultSet.next()) {                
                row = new Object[20];
                
                row[0] = resultSet.getInt(1);
                 row[1] = resultSet.getString(2);
                  row[2] = resultSet.getString(3);
                   row[3] = resultSet.getString(4);
                    row[4] = resultSet.getString(5);
                     row[5] = resultSet.getString(6);
                      row[6] = resultSet.getDouble(7);
                      
                      
                      row[7] = resultSet.getBytes(8);
                      tvSeriesImg = resultSet.getBytes("covers");
                      
                      
                      row[8] = resultSet.getString(9);
                      row[9] = resultSet.getInt(10);
                      row[10] = resultSet.getString(11);
                      row[11] = resultSet.getInt(12);
                      row[12] = resultSet.getInt(13);
                      row[13] = resultSet.getString(14);
                      row[14] = resultSet.getInt(15);
                      row[15] = resultSet.getString(16);
                      row[16] = resultSet.getInt(17);
                      row[17] = resultSet.getInt(18);
                      row[18] = resultSet.getString(19);
                      row[19] = resultSet.getInt(20);
                      
                      
                      model.addRow(row);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(AdminDatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     public ResultSet ShowforgetPass(String email, String uname){
            ResultSet resultSet = null;
        
        try {
        
            String query ="SELECT password FROM user_data WHERE uname= ? AND email= ?";
                    
            PreparedStatement pStatement = connect.prepareStatement(query);
          
            pStatement.setString(1, uname);
             pStatement.setString(2, email);
            resultSet = pStatement.executeQuery();
            
            
            
        } catch (SQLException ex) {
            Logger.getLogger(AdminDatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultSet;
    }
     
     public void updateUserForgetPass(String pass1,String email){
        try{
            String query = "UPDATE user_data SET password= ? Where email=?";
            PreparedStatement pStatement = connect.prepareStatement(query);
            pStatement.setString(1, pass1);
            pStatement.setString(2, email); 

            pStatement.executeUpdate();
            System.out.println("Successfully updated..");
        }catch(Exception e){
            System.out.println("Error in updating");
            e.printStackTrace();
        }
    }
     
     public ResultSet ShowforgetPassAdmin(String email, String uname){
            ResultSet resultSet = null;
        
        try {
        
            String query ="SELECT password FROM admin_data WHERE uname= ? AND email= ?";
                    
            PreparedStatement pStatement = connect.prepareStatement(query);
          
            pStatement.setString(1, uname);
             pStatement.setString(2, email);
            resultSet = pStatement.executeQuery();
            
            
            
        } catch (SQLException ex) {
            Logger.getLogger(AdminDatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultSet;
    }
     
    public void updateAdminForgetPass(String pass1,String email){
        try{
            String query = "UPDATE admin_data SET password= ? Where email=?";
            PreparedStatement pStatement = connect.prepareStatement(query);
            pStatement.setString(1, pass1);
            pStatement.setString(2, email); 

            pStatement.executeUpdate();
            System.out.println("Successfully updated..");
        }catch(Exception e){
            System.out.println("Error in updating");
            e.printStackTrace();
        }
    }
   
    public boolean insertPaymentDetails(int planDay,String name, Integer amount, String account_no, String Transaction_Id,String Email){
        try{
            
            
            ResultSet resultSet = null;
            String q="SELECT id FROM user_data WHERE uname=?";
            PreparedStatement preparedStatement = connect.prepareStatement(q);
            preparedStatement.setString(1, UserLogin.usrname);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                    
                    user_id = resultSet.getInt("id");
            }
            

            
            
            Calendar cal3 = Calendar.getInstance(); // creates calendar
            cal3.getTime();
            java.sql.Timestamp ourJavaDateObject = new java.sql.Timestamp(cal3.getTime().getTime());
            
             Calendar cal = Calendar.getInstance(); // creates calendar
             cal.setTime(new Date()); // sets calendar time/date
               cal.add(Calendar.DAY_OF_MONTH, 7); // adds one hour
               cal.getTime();
               java.sql.Timestamp endTime = new java.sql.Timestamp(cal.getTime().getTime());
            
            
            String query = "INSERT INTO subscription_plan (user_id,name,dateTime,endDateTime,amount,account_no,Transaction_id,email) values(?,?,?,?,?,?,?,?)";
            PreparedStatement pStatement = connect.prepareStatement(query);
            pStatement.setInt(1, user_id);
            pStatement.setString(2, name); 
            pStatement.setTimestamp(3, ourJavaDateObject);
            pStatement.setTimestamp(4, endTime);
            pStatement.setInt(5, amount);
            pStatement.setString(6, account_no);
            pStatement.setString(7, Transaction_Id);
            pStatement.setString(8, Email);
            pStatement.executeUpdate();
            
            
            
            String q2="Update user_data SET is_subscribed= ? WHERE id= ?";
            PreparedStatement pStatement2 = connect.prepareStatement(q2);
            pStatement2.setString(1, "pending");
            pStatement2.setInt(2, user_id); 
            pStatement2.executeUpdate();
            
            System.out.println("Successfully Inserted Payment info!!!!");
        }catch(Exception e){
            System.out.println("Error in inserting");
            e.printStackTrace();
            return  false;
        }
        return true;
    }
    
    public void userPaymentDetails(JTable paymentData, String valueToSearch){
        try {
        
            String query ="select * from subscription_plan where name LIKE ?";
            PreparedStatement pStatement = connect.prepareStatement(query);
           pStatement.setString(1, "%"+valueToSearch+"%");
            ResultSet resultSet=pStatement.executeQuery();
            
            DefaultTableModel model = (DefaultTableModel) paymentData.getModel();

            Object[] row;
            while (resultSet.next()) {                
                row = new Object[9];
                row[0] = resultSet.getString(1);
                 row[1] = resultSet.getString(2);
                  row[2] = resultSet.getString(3);
                   row[3] = resultSet.getString(4);
                    row[4] = resultSet.getString(5);
                     row[5] = resultSet.getString(6);
                      row[6] = resultSet.getString(7);
                      row[7] = resultSet.getString(8);
                      row[8] = resultSet.getString(9);
                        
                      model.addRow(row);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(AdminDatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void userPaymentDetailsDelete(Integer id){
        try{
            String query = "DELETE FROM subscription_plan where id= ?";
            PreparedStatement pStatement = connect.prepareStatement(query);
            
            pStatement.setInt(1, id);
            pStatement.executeUpdate();
            System.out.println("Successfully Deleted..");
        }catch(Exception e){
            System.out.println("Error in deleting");
            e.printStackTrace();
        }
    }

   public boolean userPaymentCodeSend(Integer uPaymentID, String uPaymentCode) {
        try{
            String query = "UPDATE subscription_plan SET code= ? where user_id= ?";
            PreparedStatement pStatement = connect.prepareStatement(query);
            pStatement.setString(1, uPaymentCode);
            pStatement.setInt(2, uPaymentID);
            pStatement.executeUpdate();
            System.out.println("Successfully updated..");
        }catch(Exception e){
            System.out.println("Error in updating");
            e.printStackTrace();
            return false;
        }
        return  true;
    }
   
   

    
    public boolean verifyUserQuery(String code){
         
        try{
            String query = "UPDATE user_data SET is_subscribed=? WHERE id IN(select id from subscription_plan WHERE code=?)";
            PreparedStatement pStatement = connect.prepareStatement(query);
            pStatement.setString(1, "yes");
           pStatement.setString(2, code);
           pStatement.executeUpdate();

            System.out.println("Successfully Done Query..");    
        }catch(Exception e){
            System.out.println("Error in Query..");
            e.printStackTrace();
            return false;
        }        
        return true;
    }
   
    public ResultSet getSubscriberDates(){
            ResultSet resultSet = null;
        try {
            String query ="SELECT dateTime,endDateTime FROM subscription_plan WHERE user_id= ?";    
            PreparedStatement pStatement = connect.prepareStatement(query);
            pStatement.setInt(1, 5);
            resultSet = pStatement.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(AdminDatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultSet;
    }
    
    public boolean isSubscriptionOver(String uname) {
        try{
           String q2="Update user_data SET is_subscribed= ? WHERE uname= ?";
            PreparedStatement pStatement2 = connect.prepareStatement(q2);
            pStatement2.setString(1, "no");
            pStatement2.setString(2, uname); 
            pStatement2.executeUpdate();
            
            String query = "DELETE FROM subscription_plan where user_id IN(SELECT id FROM user_data WHERE uname= ?)";
            PreparedStatement pStatement = connect.prepareStatement(query);
            pStatement.setString(1, uname);
            pStatement.executeUpdate();
            System.out.println("Successfully updated..");
        }catch(Exception e){
            System.out.println("Error in updating");
            e.printStackTrace();
            return false;
        }
        return  true;
    }
    
    public boolean insertFAQ(String qstn, String ans){
        try{
            String query = "INSERT INTO faq (question,answer) values(?,?)";
            PreparedStatement pStatement = connect.prepareStatement(query);
            pStatement.setString(1, qstn);
            pStatement.setString(2, ans); 
            pStatement.executeUpdate();
            System.out.println("Successfully Inserted..");
        }catch(Exception e){
            
            System.out.println("Error in inserting");
            e.printStackTrace();
            return  false;
        }
        return true;
    }
    
    public void updateFaq(Integer Id, String qstn, String ans){
        try{
            String query = "UPDATE faq SET question= ?, answer= ? Where eid=?";
            PreparedStatement pStatement = connect.prepareStatement(query);
            pStatement.setString(1, qstn);
            pStatement.setString(2, ans); 
            pStatement.setInt(2, Id);
            
            pStatement.executeUpdate();
            System.out.println("Successfully updated..");
        }catch(Exception e){
            System.out.println("Error in updating");
            e.printStackTrace();
        }
    }
    
    public void deleteFaq(Integer id) {
       try{
            String query = "DELETE FROM faq where id= ?";
            PreparedStatement pStatement = connect.prepareStatement(query);
            pStatement.setInt(1, id);
            pStatement.executeUpdate();
            System.out.println("Successfully Deleted..");
        }catch(Exception e){
            System.out.println("Error in deleting");
            e.printStackTrace();
        }
    }
    
    public void showFaq(JTable faqData, String valueToSearch){
        try {
            String query ="select * from faq where concat(question) LIKE ?";
                    
            PreparedStatement pStatement = connect.prepareStatement(query);
           pStatement.setString(1, "%"+valueToSearch+"%");
            ResultSet resultSet=pStatement.executeQuery();
            
            DefaultTableModel model = (DefaultTableModel) faqData.getModel();
            
            Object[] row;
            
            while (resultSet.next()) {                
                row = new Object[7];
                
                row[0] = resultSet.getString(1);
                 row[1] = resultSet.getString(2);
                  row[2] = resultSet.getString(3);
                      model.addRow(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminDatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ResultSet showFaqContent(){
            ResultSet resultSet = null;
        try{
            String query = "select question,answer from faq";
            PreparedStatement pStatement = connect.prepareStatement(query);
            resultSet=pStatement.executeQuery();

            System.out.println("Successfully Done Query..");    
        }catch(Exception e){
            System.out.println("Error in Query..");
            e.printStackTrace();
        }        
        return resultSet;
    }
    
}