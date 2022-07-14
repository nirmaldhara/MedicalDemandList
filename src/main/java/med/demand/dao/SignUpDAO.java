/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package med.demand.dao;

import med.demand.db.Database;
import med.demand.model.SignUp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ndhara
 */
public class SignUpDAO {
    
    
    public static void addSignup(SignUp signup) throws SQLException {
        try(Connection con = Database.getConnection();) {
           String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
            PreparedStatement ps = con.prepareStatement("INSERT INTO login (userid,password,status,name,emailid,mobile,gender,createdate) VALUES(?,?,?,?,?,?,?,?)");
            ps.setString(1, signup.getUserid());
            ps.setString(2, signup.getPassword());
            ps.setInt(3, signup.getStatus());
            ps.setString(4,signup.getName());
            ps.setString(5, signup.getEmailid());
            ps.setString(6, signup.getMobile());
            ps.setString(7, signup.getGender());
            ps.setString(8, signup.getCreatedate());
          
            ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null,
                    ex);
            throw  new SQLException();
            
        }
    }
    
    
    public static boolean isUserExist(String userid) throws SQLException {
        boolean exist=false;
        try(Connection con = Database.getConnection();) {
           String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
            PreparedStatement ps = con.prepareStatement("SELECT * FROM  login where userid=?");
            ps.setString(1, userid);
           ResultSet rs= ps.executeQuery();
           if(rs.next()){
               exist=true;
           }

        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null,
                    ex);
            throw  new SQLException();
            
        }
        return exist;
    }
    
    public static boolean isEmailExist(String userid) throws SQLException {
        boolean exist=false;
        try(Connection con = Database.getConnection();) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM  login where emailid=?");
            ps.setString(1, userid);
           ResultSet rs= ps.executeQuery();
           if(rs.next()){
               exist=true;
           }

        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null,
                    ex);
            throw  new SQLException();
            
        }
        return exist;
    }
    
    
}
