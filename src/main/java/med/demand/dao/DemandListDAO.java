package med.demand.dao;

import med.demand.db.Database;
import med.demand.model.DemandListDetails;
import org.sqlite.SQLiteException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DemandListDAO {

    public static void deleteDemandList(long id) throws SQLException {
        PreparedStatement ps = null;
        try (Connection con = Database.getConnection();) {
            String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            ps = con.prepareStatement("DELETE FROM demandlist  where id=?");
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

    public static void deleteDemandListByFile(long id) throws SQLException {
        PreparedStatement ps = null;
        try (Connection con = Database.getConnection();) {
            String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            ps = con.prepareStatement("DELETE FROM demandlist  where filename=?");
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
    public static long getFileId(String fileName) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        long id=-1;

        try (Connection con = Database.getConnection();) {
            ps = con.prepareStatement("select id from demandlistfile where name=?");
            ps.setString(1,fileName);
            rs = ps.executeQuery();
            if (rs.next()) {

               id=rs.getLong(1);

            }

        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null,
                    ex);
        }
        finally {
            ps.close();
        }



        return id;

    }
    public static long addDemandListFile(String fileName,int userid) throws SQLException {
        PreparedStatement ps = null;
        long id=-1;

        ///if exist
        id=getFileId(fileName);
        if(id ==-1) {

            try (Connection con = Database.getConnection();) {
                String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                ps = con.prepareStatement("INSERT INTO demandlistfile (name) VALUES(?)");
                ps.setString(1, fileName);
                id = ps.executeUpdate();

                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        id = generatedKeys.getLong(1);
                    } else {
                        throw new SQLException("Creating user failed, no ID obtained.");
                    }
                }
            } catch (SQLiteException ex) {

                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, "File already exist",
                        ex);
                id = getFileId(fileName);

            } catch (SQLException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null,
                        ex);
            } finally {
                ps.close();
            }
        }
        return id;
    }

    public static long addDemandList(List<DemandListDetails> lstDetail, long fileName, int userid) throws SQLException {
        PreparedStatement ps = null;
        long id=-1;
        try (Connection con = Database.getConnection();) {
            ps = con.prepareStatement("INSERT OR REPLACE INTO demandlist (medicine,supplier,quantity,filename) VALUES(?,?,?,?)");
            for (DemandListDetails detail:lstDetail) {
                ps.setLong(1, detail.getMedId());
                ps.setLong(2, detail.getSupId());
                ps.setLong(3, detail.getQuantity());
                ps.setLong(4, fileName);
                id=ps.executeUpdate();
            }



        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null,
                    ex);
        }
        finally {
            ps.close();
        }
        return id;
    }

    public static List<DemandListDetails> getDemandList(long file) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<DemandListDetails> dataList = new ArrayList<>();
        try (Connection con = Database.getConnection();) {
            ps = con.prepareStatement("select  d.id,d.quantity, m.name medname, s.name supname from medicine m, suppliers s, demandlist d where m.id=d.medicine and s.id=d.supplier and d.filename=? group by medname, supname order by d.id desc ");
            ps.setLong(1,file);
            rs = ps.executeQuery();
            while (rs.next()) {
                DemandListDetails detail = new DemandListDetails();
                detail.setMedName(rs.getString("medname"));
                detail.setSupName(rs.getString("supname"));
                detail.setQuantity(rs.getLong("quantity"));
                detail.setId(rs.getLong("id"));
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

    public static List<DemandListDetails> getDemandList(long file,long supplier) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<DemandListDetails> dataList = new ArrayList<>();
        try (Connection con = Database.getConnection();) {
                ps = con.prepareStatement("select  d.id,d.quantity, m.name medname, s.name supname from medicine m, suppliers s, demandlist d where m.id=d.medicine and s.id=d.supplier and d.filename=? and s.id=? group by medname, supname order by d.id desc ");
            ps.setLong(1,file);
            ps.setLong(2,supplier);
            rs = ps.executeQuery();
            while (rs.next()) {
                DemandListDetails detail = new DemandListDetails();
                detail.setMedName(rs.getString("medname"));
                detail.setSupName(rs.getString("supname"));
                detail.setQuantity(rs.getLong("quantity"));

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
