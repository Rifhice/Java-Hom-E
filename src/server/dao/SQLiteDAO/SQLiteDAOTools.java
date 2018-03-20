package server.dao.SQLiteDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import server.dao.abstractDAO.DAO;
import server.dao.abstractDAO.DAOException;

public class SQLiteDAOTools{

	public static int getLastId(Connection connect) {
        String sqlGetLastId = "SELECT last_insert_rowid()";
        PreparedStatement prepStatLastId = null;
		try {
			prepStatLastId = connect.prepareStatement(sqlGetLastId);
			return prepStatLastId.executeQuery().getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
        
	}
	
}
