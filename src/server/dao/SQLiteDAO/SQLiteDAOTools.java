package server.dao.SQLiteDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class SQLiteDAOTools{

	public static int getLastId(Connection connect) {
        String sqlGetLastId = "SELECT last_insert_rowid()";
        PreparedStatement prepStatLastId = null;
		try {
			prepStatLastId = connect.prepareStatement(sqlGetLastId);
			return prepStatLastId.executeQuery().getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public static int getLastId(Connection connect, String table) {
        String sqlGetLastId = "SELECT last_insert_rowid() FROM "+table;
        PreparedStatement prepStatLastId = null;
        try {
            prepStatLastId = connect.prepareStatement(sqlGetLastId);
            return prepStatLastId.executeQuery().getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
        
    }
	
}
