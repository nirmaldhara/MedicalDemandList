package med.demand.dao;



        import med.demand.db.Database;
        import med.demand.model.MappingDetails;

        import java.sql.*;
        import java.text.SimpleDateFormat;
        import java.util.ArrayList;
        import java.util.Date;
        import java.util.List;
        import java.util.logging.Level;
        import java.util.logging.Logger;

public class MappingDAO {

    public static long addMapping(long medId,long supId, int userid) throws SQLException {
        PreparedStatement ps = null;
        long id=-1;
        try (Connection con = Database.getConnection();) {
            String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            ps = con.prepareStatement("INSERT OR REPLACE INTO mapping (medicine,supplier) VALUES(?,?)");
            ps.setLong(1, medId);
            ps.setLong(2, supId);
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


    public static void deleteMappingBySupplier(long id) throws SQLException {
        PreparedStatement ps = null;
        try (Connection con = Database.getConnection();) {
            String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            ps = con.prepareStatement("DELETE FROM mapping  where supplier=?");
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


    public static void deleteMapping(long id) throws SQLException {
        PreparedStatement ps = null;
        try (Connection con = Database.getConnection();) {
            String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            ps = con.prepareStatement("DELETE FROM mapping  where id=?");
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

    public static void deleteMappingByMedicine(long id) throws SQLException {
        PreparedStatement ps = null;
        try (Connection con = Database.getConnection();) {
            String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            ps = con.prepareStatement("DELETE FROM mapping  where medicine=?");
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
    public static List<MappingDetails> getMapping() throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<MappingDetails> dataList = new ArrayList<>();
        try (Connection con = Database.getConnection();) {
            ps = con.prepareStatement("select  map.id, m.name medname, s.name supname from medicine m, suppliers s, mapping map where m.id=map.medicine and s.id=map.supplier group by medname, supname order by map.id desc ");

            rs = ps.executeQuery();
            while (rs.next()) {
                MappingDetails detail = new MappingDetails();
                System.out.println(rs.getString("medname"));
                detail.setMedName(rs.getString("medname"));
                detail.setSupName(rs.getString("supname"));
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

}