/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package med.demand.dao;

import med.demand.db.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ndhara
public static boolean isLoggedin() {

boolean flag = false;
try (Connection con = Database.getConnection();) {
ResultSet rs = null;
PreparedStatement ps = con.prepareStatement("select * from login_session");
rs = ps.executeQuery();
if (rs != null && rs.next()) {
flag = true;
}
} catch (Exception e) {

}

return flag;
}


 */
public class LoginDAO {
    public static void removeSession() {
        try (Connection con = Database.getConnection();) {
            PreparedStatement ps = con.prepareStatement("DELETE FROM login_sessionwhere");
            ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null,
                    ex);
        }
    }

    public static void insertSession(String usrid) {
        try (Connection con = Database.getConnection();) {
            PreparedStatement ps = con.prepareStatement("INSERT INTO login_session (userid,logedin) VALUES(?,?)");
            ps.setString(1, usrid);
            ps.setString(2, "1");
            ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null,
                    ex);
        }
    }

    public static boolean isValiduser(String userid, String password) {
        boolean flag = false;
        try (Connection con = Database.getConnection();) {
            ResultSet rs = null;
            PreparedStatement ps = con.prepareStatement("select * from login where (userid=?  or id=?)and password=?");
            ps.setString(1, userid);
            ps.setString(2, userid);
            ps.setString(3, password);
            rs = ps.executeQuery();
            if (rs != null && rs.next()) {
                flag = true;
            }
        } catch (Exception e) {

        }
        return flag;
    }

}
