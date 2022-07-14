package med.demand.dao;

import med.demand.db.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;


import med.demand.model.SuppliersDetails;

import java.util.ArrayList;
        import java.util.Date;
import java.util.List;

/**
 *
 * @author ndhara
 */
public class SuppliersDAO {


    public static void addSupplier(SuppliersDetails detail, int userid) throws SQLException {
        PreparedStatement ps = null;
        try (Connection con = Database.getConnection();) {
            String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            ps = con.prepareStatement("INSERT  INTO suppliers (name,address,phoneno,email,profitpercent) VALUES(?,?,?,?,?)");
            ps.setString(1, detail.getName());
            ps.setString(2, detail.getAddress());
            ps.setString(3, detail.getPhoneNo());
            ps.setString(4, detail.getEmail());
            ps.setFloat(5, detail.getProfitPercent());

            ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null,
                    ex);
            throw new SQLException(ex);
        }
        finally {
            ps.close();
        }

    }


    public static void editSupplier(SuppliersDetails detail, int userid) throws SQLException {
        PreparedStatement ps = null;
        try (Connection con = Database.getConnection();) {
            String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            ps = con.prepareStatement("UPDATE suppliers set name=?,address=?,phoneno=?,email=?,profitpercent=? where id=?");
            ps.setString(1, detail.getName());
            ps.setString(2, detail.getAddress());
            ps.setString(3, detail.getPhoneNo());
            ps.setString(4, detail.getEmail());
            ps.setFloat(5, detail.getProfitPercent());
            ps.setFloat(6, detail.getId());

            ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null,
                    ex);
            throw new SQLException(ex);
        }
        finally {
            ps.close();
        }

    }
    public static void deleteSupplier(long id) throws SQLException {
        PreparedStatement ps = null;
        try (Connection con = Database.getConnection();) {
            String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            ps = con.prepareStatement("DELETE FROM suppliers  where id=?");
            ps.setLong(1, id);
            ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null,
                    ex);
        }
        finally {
            ps.close();
        }

    }

    public static List<SuppliersDetails> getSuppliers() throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<SuppliersDetails> dataList = new ArrayList<>();
        try (Connection con = Database.getConnection();) {
            ps = con.prepareStatement("select id,name,address,phoneno,email,profitpercent from suppliers order by id desc");

            rs = ps.executeQuery();
            while (rs.next()) {
                SuppliersDetails detail = new SuppliersDetails();
                detail.setId(rs.getLong("id"));
                detail.setName(rs.getString("name"));
                detail.setAddress(rs.getString("address"));
                detail.setPhoneNo(rs.getString("phoneno"));
                detail.setEmail(rs.getString("email"));
                detail.setProfitPercent(rs.getFloat("profitpercent"));
                dataList.add(detail);

            }

        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null,
                    ex);
        }
        finally {
            ps.close();
        }

        return dataList;

    }

    public static List<SuppliersDetails> getSupplierByName(String name) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<SuppliersDetails> dataList = new ArrayList<>();
        try (Connection con = Database.getConnection();) {
            ps = con.prepareStatement("select id,name,address,phoneno,email,profitpercent from suppliers  where name=?");
            ps.setString(1,name);
            rs = ps.executeQuery();
            rs.next();
                SuppliersDetails detail = new SuppliersDetails();
                detail.setId(rs.getLong("id"));
                detail.setName(rs.getString("name"));
                detail.setAddress(rs.getString("address"));
                detail.setPhoneNo(rs.getString("phoneno"));
                detail.setEmail(rs.getString("email"));
                detail.setProfitPercent(rs.getFloat("profitpercent"));
                dataList.add(detail);


        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null,
                    ex);
        }
        finally {
            ps.close();
        }

        return dataList;

    }


    public static List<SuppliersDetails> getSuppliersForMedicine(String med) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<SuppliersDetails> dataList = new ArrayList<>();
        try (Connection con = Database.getConnection();) {
            ps = con.prepareStatement("select DISTINCT s.id,s.name,s.profitpercent from suppliers s, mapping map, medicine m where s.id=map.supplier and m.name=? and m.id=map.medicine");
            ps.setString(1,med);
            rs = ps.executeQuery();
            while (rs.next()) {
                SuppliersDetails detail = new SuppliersDetails();
                detail.setId(rs.getLong("id"));
                detail.setName(rs.getString("name")+"  --->"+rs.getFloat("profitpercent")+"%");
                dataList.add(detail);

            }

        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null,
                    ex);
        }
        finally {
            ps.close();
        }

        return dataList;

    }
}

