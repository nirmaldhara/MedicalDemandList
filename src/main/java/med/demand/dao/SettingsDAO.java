package med.demand.dao;

import med.demand.db.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SettingsDAO {

    public static Map<String,String> getBillStructure() {

        Map<String,String> billStructure=new HashMap<>();


        try (Connection con = Database.getConnection();) {
            ResultSet rs = null;
            PreparedStatement ps = con.prepareStatement("select * from bill_structure");
            rs = ps.executeQuery();
            while (rs != null && rs.next()) {
                if(rs.getString("fieldname").equalsIgnoreCase("transactionId")){
                    billStructure.put("transactionId",rs.getString("fieldvalue"));
                }
                if(rs.getString("fieldname").equalsIgnoreCase("date")){
                    billStructure.put("date",rs.getString("fieldvalue"));
                }
                if(rs.getString("fieldname").equalsIgnoreCase("paymentGateway")){
                    billStructure.put("paymentGateway",rs.getString("fieldvalue"));
                }
                if(rs.getString("fieldname").equalsIgnoreCase("billingOffice")){
                    billStructure.put("billingOffice",rs.getString("fieldvalue"));
                }
                if(rs.getString("fieldname").equalsIgnoreCase("consumerId")){
                    billStructure.put("consumerId",rs.getString("fieldvalue"));
                }
                if(rs.getString("fieldname").equalsIgnoreCase("name")){
                    billStructure.put("name",rs.getString("fieldvalue"));
                }
                if(rs.getString("fieldname").equalsIgnoreCase("invoiceNo")){
                    billStructure.put("invoiceNo",rs.getString("fieldvalue"));
                }
                if(rs.getString("fieldname").equalsIgnoreCase("billPaidFor")){
                    billStructure.put("billPaidFor",rs.getString("fieldvalue"));
                }
                if(rs.getString("fieldname").equalsIgnoreCase("paymentMode")){
                    billStructure.put("paymentMode",rs.getString("fieldvalue"));
                }
                if(rs.getString("fieldname").equalsIgnoreCase("received")){
                    billStructure.put("received",rs.getString("fieldvalue"));
                }

            }
        } catch (Exception e) {

        }

        return billStructure;
    }

    public static boolean saveBillStructure(Map<String,String> structure) {

        PreparedStatement ps = null;

        try (Connection con = Database.getConnection();) {

            for (Map.Entry<String,String> entry : structure.entrySet()) {
                ps = con.prepareStatement("INSERT OR REPLACE INTO bill_structure (fieldname,fieldvalue) VALUES(?,?)");
                ps.setString(1, entry.getKey());
                ps.setString(2, entry.getValue());
                ps.executeUpdate();
            }

        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null,
                    ex);
        }
        return true;

    }

    public static String getServiceCharges() {

        String serviceCharge = "0.0";
        try (Connection con = Database.getConnection();) {
            ResultSet rs = null;
            PreparedStatement ps = con.prepareStatement("select * from settings where fieldname='service_charge'");
            rs = ps.executeQuery();
            if (rs != null && rs.next()) {
                serviceCharge=rs.getString("value");
            }
        } catch (Exception e) {

        }

        return serviceCharge;
    }

    public static boolean saveSettings(String fieldname, String value) {

        PreparedStatement ps = null;

        try (Connection con = Database.getConnection();) {

            ps = con.prepareStatement("INSERT OR REPLACE INTO settings (fieldname,value) VALUES(?,?)");
            ps.setString(1, fieldname);
            ps.setString(2, value);
            ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null,
                    ex);
        }
        return true;
    }
}
