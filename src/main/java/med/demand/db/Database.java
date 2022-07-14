/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package med.demand.db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ndhara
 */
public class Database {

    private static String conString = "jdbc:sqlite:/db/med.db";
    private static Connection con = null;

    public Database() {
        try {
            
            Class.forName("org.sqlite.JDBC");
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null,
                    ex);
        }
    }

    public static Connection getConnection() {
        try {
           
            con = DriverManager.getConnection(conString);
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null,
                    ex);
            ex.printStackTrace();
            con = null;
        }
        return con;
    }


    // ################################################################################
    // # SETTING TABLE
    // ################################################################################

//    public List<Setting> getSettings() {
//        List<Setting> result = new ArrayList<Setting>();
//        try {
//            Statement st = getConnection().createStatement();
//            ResultSet r = st.executeQuery("SELECT * FROM setting");
//            while (r.next()) {
//                result.add(new Setting(r.getString("name"), r
//                        .getString("value")));
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null,
//                    ex);
//            result = new ArrayList<Setting>();
//        }
//        return result;
//    }

//    public void createSetting(Setting s) {
//        try {
//            Statement st = getConnection().createStatement();
//            st.execute("INSERT INTO setting (name,value) VALUES(" + s.getName()
//                    + "," + s.getValue() + ")");
//        } catch (SQLException ex) {
//            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null,
//                    ex);
//        }
//    }

//    public void updateSetting(Setting s) {
//        try {
//            Statement st = getConnection().createStatement();
//            st.execute("UPDATE setting SET value = " + s.getValue()
//                    + " WHERE name = " + s.getName());
//        } catch (SQLException ex) {
//            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null,
//                    ex);
//        }
//    }

//    public Setting findSetting(String name) {
//        Setting setting = null;
//        try {
//            Statement st = getConnection().createStatement();
//            ResultSet rs = st
//                    .executeQuery("SELECT * FROM setting WHERE name = " + name);
//            while (rs.next()) {
//                setting = new Setting(rs.getString("name"),
//                        rs.getString("value"));
//            }
//        } catch (Exception ex) {
//            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null,
//                    ex);
//            setting = null;
//        }
//        return setting;
//    }
}