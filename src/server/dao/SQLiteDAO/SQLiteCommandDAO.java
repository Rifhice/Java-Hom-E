package server.dao.SQLiteDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import server.dao.abstractDAO.CommandDAO;
import server.dao.abstractDAO.DAOException;
import server.models.Behaviour;
import server.models.Command;

public class SQLiteCommandDAO extends CommandDAO {

	public SQLiteCommandDAO(Connection connect) {
		super(connect);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Command create(Command obj) throws DAOException {
		return null;
	}



	@Override
	public int update(Command obj) throws DAOException {
		return 0;
	}

	@Override
	public int delete(int id) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArrayList<Command> getAll() throws DAOException {
		ArrayList<Command> commands = new ArrayList<Command>();
        String sql = "SELECT * FROM Commands";

        try {
            PreparedStatement prepStat = this.connect.prepareStatement(sql);
            ResultSet rs = prepStat.executeQuery();
            Command command = null;
            while (rs.next()) {
            	//TODO manque pleins de parametres
            	command = new Command();
            	command.setName(rs.getString("name"));
            	command.setId(rs.getInt("id"));
            	commands.add(command);
            }
        } catch (SQLException e) {
            throw new DAOException("DAOException : CommandDAO getAll() :" + e.getMessage(), e);
        }
        return commands;
	}
	
}
