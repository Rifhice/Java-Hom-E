package server.dao.SQLiteDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import server.dao.abstractDAO.DAOException;
import server.dao.abstractDAO.RightDAO;
import server.dao.abstractDAO.UserDAO;
import server.factories.AbstractDAOFactory;
import server.models.Role;
import server.models.User;
import server.models.Right;

public class SQLiteRightDAO extends RightDAO{


	// ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
	
	public SQLiteRightDAO(Connection connectionDriver) {
		super(connectionDriver);
	}

    // ================= //
    // ==== METHODS ==== //
    // ================= //
	
	@Override
	public Right create(Right obj) throws DAOException {
		 Right right = obj;
	        String sql = "INSERT INTO rights "
	                + "(denomination, description) VALUES "
	                + "(?,?)";

	        // Insert the object
	        int created = 0;
	        try {
	            PreparedStatement prepStat = this.connect.prepareStatement(sql);
	            prepStat.setString(1, obj.getDenomination());
	            prepStat.setString(2, obj.getDescription());
	            created = prepStat.executeUpdate();

	            // Get the id generated for this object
	            if(created > 0) {
	                right.setId(SQLiteDAOTools.getLastId(connect));
	            }
	            else {
	                right = null;
	            }
	        } catch (SQLException e) {
	            throw new DAOException("DAOException : RoleDAO create(" + obj.getDenomination() + ") :" + e.getMessage(), e); 
	        }
	        return right;
	}

	@Override
	public Right getById(int id) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public ArrayList<Right> getByUser(User user) throws DAOException {
		Right right = null;   	
		ArrayList<Right> rights = new ArrayList<Right>();
    	String sql = "SELECT  Ri.id AS id, Ri.denomination AS denomination, "
    			+ "Ri.description AS description, U.pseudo AS pseudo " 
                + "FROM Users AS U "
                + "JOIN Owns AS O ON O.fk_user_id = U.id "
                + "JOIN OwnsByDefault AS OBD ON OBD.fk_role_id = U.fk_role_id "
                + "JOIN Rights AS Ri ON OBD.fk_right_id = Ri.id "         
                + "WHERE U.id = ?;";

    	try {
    		PreparedStatement prepStat = this.connect.prepareStatement(sql);
    		prepStat.setInt(1, user.getId());
            ResultSet rs = prepStat.executeQuery();
            if(rs.next()) {	
            	do {
            		right = new Right();
                    right.setId(rs.getInt("id"));
                    right.setDenomination(rs.getString("denomination")); 
                    right.setDescription(rs.getString("description"));
                    rights.add(right);
            	}while (rs.next());  
            }
    		
    	}
    	catch(SQLException e) {
    		throw new DAOException("DAOException : RoleDAO getbyId(" + user.getPseudo() + ") : " + e.getMessage(),e);
    		
    	}
    	
        return rights;
	}

	@Override
	public int update(Right obj) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(int id) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public ArrayList<Right> getAll() throws DAOException {
		Right right = null;   	
		ArrayList<Right> rights = new ArrayList<Right>();
    	String sql = "SELECT  Ri.id AS id, Ri.denomination AS denomination, "
    			+ "Ri.description AS description " 
                + "FROM Rights AS Ri ";

    	try {
    		PreparedStatement prepStat = this.connect.prepareStatement(sql);
            ResultSet rs = prepStat.executeQuery();
            if(rs.next()) {	
            	do {
            		right = new Right();
                    right.setId(rs.getInt("id"));
                    right.setDenomination(rs.getString("denomination")); 
                    right.setDescription(rs.getString("description"));
                    rights.add(right);
            	}while (rs.next());  
            }
    		
    	}
    	catch(SQLException e) {
    		throw new DAOException("DAOException : RightDAO getAll(" + right.getDenomination() + ") : " + e.getMessage(),e);
    		
    	}
    	
        return rights;
	}
	
	public static void main(String args[]) {
		try {
			RightDAO test = AbstractDAOFactory.getFactory(AbstractDAOFactory.SQLITE_DAO_FACTORY).getRightDAO();
			User user = AbstractDAOFactory.getFactory(AbstractDAOFactory.SQLITE_DAO_FACTORY).getUserDAO().getById(2);
	        //System.out.println("Les droits de Kevin : \n" + test.getByUser(user));
	        //System.out.println("\n Tous les droits : \n" +  test.getAll());
		}
		catch (DAOException e) {
			e.printStackTrace();
		}
		
	}

}
