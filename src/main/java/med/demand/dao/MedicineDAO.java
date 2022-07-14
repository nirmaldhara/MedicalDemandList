package med.demand.dao;

import med.demand.db.Database;
import med.demand.model.MedicineDetails;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MedicineDAO {

    public static long addMedicine(MedicineDetails detail, int userid) throws SQLException {
        PreparedStatement ps = null;
        long id=-1;
        try (Connection con = Database.getConnection();) {
            String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            ps = con.prepareStatement("INSERT  INTO medicine (name) VALUES(?)");
            ps.setString(1, detail.getName());
            id=ps.executeUpdate();


        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null,
                    ex);
            throw  new SQLException(ex);
        }
        finally {
            ps.close();
        }
return id;
    }


    public static void editMedicine(MedicineDetails detail, int userid) throws SQLException {
        PreparedStatement ps = null;
        try (Connection con = Database.getConnection();) {
            String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            ps = con.prepareStatement("UPDATE medicine set name=? where id=?");
            ps.setString(1, detail.getName());
            ps.setFloat(2, detail.getId());

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
    public static void deleteMedicine(long id) throws SQLException {
        PreparedStatement ps = null;
        try (Connection con = Database.getConnection();) {
            String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            ps = con.prepareStatement("DELETE FROM medicine  where id=?");
            ps.setLong(1, id);
            ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null,
                    ex);
            throw  new SQLException(ex);
        }
        finally {
            ps.close();
        }

    }

    public static long addMapping(long medId,long supId) throws SQLException {
        PreparedStatement ps = null;
        long id=-1;
        try (Connection con = Database.getConnection();) {
            String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            ps = con.prepareStatement("INSERT INTO mapping (medicine,supplier) VALUES(?,?)");
            ps.setLong(1, medId);
            ps.setLong(2, medId);
            id=ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null,
                    ex);
        }
        finally {
            ps.close();
        }
        return id;
    }




    public static List<MedicineDetails> getMedicine() throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<MedicineDetails> dataList = new ArrayList<>();
        try (Connection con = Database.getConnection();) {
            ps = con.prepareStatement("select m.id,m.name from medicine m order by m.id desc");

            rs = ps.executeQuery();
            while (rs.next()) {
                MedicineDetails detail = new MedicineDetails();
                detail.setId(rs.getLong("id"));
                detail.setName(rs.getString("name"));

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

    public static List<MedicineDetails> getMedicineByName(String name) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<MedicineDetails> dataList = new ArrayList<>();
        try (Connection con = Database.getConnection();) {
            ps = con.prepareStatement("select m.id,m.name from medicine m where m.name=?");
    ps.setString(1,name);
            rs = ps.executeQuery();
            while (rs.next()) {
                MedicineDetails detail = new MedicineDetails();
                detail.setId(rs.getLong("id"));
                detail.setName(rs.getString("name"));

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