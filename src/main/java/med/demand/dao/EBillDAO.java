/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package med.demand.dao;

import med.demand.db.Database;
import med.demand.model.EBillDetails;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ndhara
 */
public class EBillDAO {

    public static void addBill(EBillDetails detail, int userid) {
        PreparedStatement ps = null;
        try (Connection con = Database.getConnection();) {
            String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            ps = con.prepareStatement("INSERT OR REPLACE INTO ebill (transaction_id,invoice_no,payment_date,billing_office,consumer_id,consumer_name,bill_paid_for,payment_mode,recieved_amount,comisson,created,user_id) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");
            ps.setString(1, detail.getTransactionID());
            ps.setString(2, detail.getInvoiceNumber());
            ps.setString(3, detail.getPaymentDate());
            ps.setString(4, detail.getBillingOffice());
            ps.setString(5, detail.getConsumerId());
            ps.setString(6, detail.getCustomerName());
            ps.setString(7, detail.getBillPaidFor());
            ps.setString(8, detail.getPaymentMode());
            ps.setString(9, detail.getReceivedAmount());
            ps.setString(10, detail.getServiceCharge());
            // ps.setFloat(9, Float.parseFloat(detail.getServiceCharge()));
            ps.setString(11, date);
            ps.setInt(12, userid);
            ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null,
                    ex);
        }

    }
    
    
    public static List<EBillDetails> getBillData() {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<EBillDetails> dataList = new ArrayList<>();
        try (Connection con = Database.getConnection();) {
            String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            ps = con.prepareStatement("select * from ebill where created like ?");
            ps.setString(1, date + "%");

            rs = ps.executeQuery();
            while (rs.next()) {
                EBillDetails detail = new EBillDetails();
                detail.setTransactionID(rs.getString("transaction_id"));
                detail.setPaymentDate(rs.getString("payment_date"));
                detail.setBillingOffice(rs.getString("billing_office"));
                detail.setConsumerId(rs.getString("consumer_id"));
                detail.setCustomerName(rs.getString("consumer_name"));
                detail.setBillPaidFor(rs.getString("bill_paid_for"));
                detail.setReceivedAmount(rs.getString("recieved_amount"));
                detail.setServiceCharge(rs.getString("comisson"));
                detail.setInvoiceNumber(rs.getString("invoice_no"));
                detail.setPaymentMode(rs.getString("payment_mode"));
                detail.setCreated(rs.getString("created"));
                dataList.add(detail);

            }

        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null,
                    ex);
        }

        return dataList;

    }

    public static List<EBillDetails> getBillData(String key) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<EBillDetails> dataList = new ArrayList<>();
        try (Connection con = Database.getConnection();) {
            String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
            ps = con.prepareStatement("select * from ebill where consumer_id like ? or consumer_name like ?");
            ps.setString(1, "%"+key + "%");
            ps.setString(2, "%"+key + "%");

            rs = ps.executeQuery();
            while (rs.next()) {
                EBillDetails detail = new EBillDetails();
                detail.setTransactionID(rs.getString("transaction_id"));
                detail.setPaymentDate(rs.getString("payment_date"));
                detail.setBillingOffice(rs.getString("billing_office"));
                detail.setConsumerId(rs.getString("consumer_id"));
                detail.setCustomerName(rs.getString("consumer_name"));
                detail.setBillPaidFor(rs.getString("bill_paid_for"));
                detail.setReceivedAmount(rs.getString("recieved_amount"));
                detail.setServiceCharge(rs.getString("comisson"));
                detail.setInvoiceNumber(rs.getString("invoice_no"));
                detail.setPaymentMode(rs.getString("payment_mode"));
                dataList.add(detail);

            }

        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null,
                    ex);
        }

        return dataList;

    }

    public static void deleteTotal() throws SQLException {
        PreparedStatement ps = null;
        try (Connection con = Database.getConnection();) {
            ps = con.prepareStatement("delete from totalbill");
            ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null,
                    ex);
            throw new SQLException();

        }

    }

    public static void addTotal(Double bill, Double servicecharge) throws SQLException {
        PreparedStatement ps = null;
        deleteTotal();
        try (Connection con = Database.getConnection();) {
            ps = con.prepareStatement("INSERT INTO totalbill (billamount,servicechargeamount) VALUES(?,?)");
            ps.setDouble(1, bill);
            ps.setDouble(2, servicecharge);
            ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null,
                    ex);
            throw new SQLException();

        }

    }

    public static Map getTotal() throws SQLException {
        Map m = new HashMap();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try (Connection con = Database.getConnection();) {

            ps = con.prepareStatement("select * from totalbill");

            rs = ps.executeQuery();
            if (rs.next()) {
                m.put("tbill", rs.getString("billamount"));
                m.put("tservice", rs.getString("servicechargeamount"));
            }

        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null,
                    ex);
            throw new SQLException();

        }
        return m;
    }

    public static List<EBillDetails> getBillDataByDate(String fromDate, String toDate) {

        Double totalBill = 0.0;
        Double totalService = 0.0;
        ResultSet rs = null;
        PreparedStatement ps = null;
        List<EBillDetails> dataList = new ArrayList<>();
        try (Connection con = Database.getConnection();) {
            ps = con.prepareStatement("select * from ebill where created between ? and ?");
            ps.setString(1, fromDate);
            ps.setString(2, toDate);

            rs = ps.executeQuery();
            while (rs.next()) {
                EBillDetails detail = new EBillDetails();
                detail.setTransactionID(rs.getString("transaction_id"));
                detail.setPaymentDate(rs.getString("payment_date"));
                detail.setBillingOffice(rs.getString("billing_office"));
                detail.setConsumerId(rs.getString("consumer_id"));
                detail.setCustomerName(rs.getString("consumer_name"));
                detail.setBillPaidFor(rs.getString("bill_paid_for"));
                detail.setReceivedAmount(rs.getString("recieved_amount"));
                detail.setServiceCharge(rs.getString("comisson"));
                detail.setInvoiceNumber(rs.getString("invoice_no"));
                detail.setPaymentMode(rs.getString("payment_mode"));
                totalBill = totalBill + Double.parseDouble(rs.getString("recieved_amount"));
                totalService = totalService + Double.parseDouble(rs.getString("comisson"));

                dataList.add(detail);

            }

            addTotal(totalBill, totalService);

        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null,
                    ex);
        }

        return dataList;

    }
}
