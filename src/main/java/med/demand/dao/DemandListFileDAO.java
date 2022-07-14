package med.demand.dao;

import med.demand.db.Database;
import med.demand.model.DemandListFileDetails;

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

public class DemandListFileDAO {


    public static void deleteDemandLisFile(long id) throws SQLException {
        PreparedStatement ps = null;
        try (Connection con = Database.getConnection();) {
            String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            ps = con.prepareStatement("DELETE FROM demandlistfile  where id=?");
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
    public static List<DemandListFileDetails> getAllFiles() throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<DemandListFileDetails> dataList = new ArrayList<>();
        try (Connection con = Database.getConnection();) {
            ps = con.prepareStatement("select d.id, d.name from demandlistfile d order by d.id desc");

            rs = ps.executeQuery();
            while (rs.next()) {
                DemandListFileDetails detail = new DemandListFileDetails();
                detail.setId(rs.getLong("id"));
                detail.setFileName(rs.getString("name"));

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


