package server.dao.SQLiteDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import server.dao.abstractDAO.CommandDAO;
import server.dao.abstractDAO.DAOException;
import server.factories.AbstractDAOFactory;
import server.models.Actuator;
import server.models.Behaviour;
import server.models.Command;
import server.models.commandValue.CommandValue;
import server.models.commandValue.ContinuousCommandValue;
import server.models.commandValue.DiscreteCommandValue;

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
            	command = new Command();
            	command.setName(rs.getString("name"));
            	command.setId(rs.getInt("id"));
            	command.setKey(rs.getString("key"));
            	command.setDescription(rs.getString("description"));
            	command.setCommandValues(getAllCommandValues(command.getId()));
            	command.setActuator(new Actuator(rs.getInt("fk_actuator_id")));
            	commands.add(command);
            }
        } catch (SQLException e) {
            throw new DAOException("DAOException : CommandDAO getAll() :" + e.getMessage(), e);
        }
        return commands;
	}
	
	public ArrayList<CommandValue> getAllCommandValues(int id){
		ArrayList<CommandValue> commandValues = new ArrayList<CommandValue>();
        String sql = "SELECT * FROM CommandValues WHERE fk_command_id = ?";

        try {
            PreparedStatement prepStat = this.connect.prepareStatement(sql);
            prepStat.setInt(1, id);
            ResultSet rs = prepStat.executeQuery();
            while (rs.next()) {
            	commandValues.addAll(getAllContinuousCommandValues(rs.getInt("id")));
            	commandValues.addAll(getAllDiscreteCommandValues(rs.getInt("id")));
            }
        } catch (SQLException e) {
            throw new DAOException("DAOException : CommandDAO getAll() :" + e.getMessage(), e);
        }
        return commandValues;
	}
	
	public ArrayList<ContinuousCommandValue> getAllContinuousCommandValues(int id){
		ArrayList<ContinuousCommandValue> commandValues = new ArrayList<ContinuousCommandValue>();
        String sql = "SELECT * FROM ContinuousCommandValues WHERE fk_commandValue_id = ?";

        try {
            PreparedStatement prepStat = this.connect.prepareStatement(sql);
            prepStat.setInt(1, id);
            ResultSet rs = prepStat.executeQuery();
            ContinuousCommandValue command = null;
            while (rs.next()) {
            	command = new ContinuousCommandValue();
            	command.setValueMin(rs.getDouble("min"));
            	command.setValueMax(rs.getDouble("max"));
            	command.setPrecision(rs.getDouble("precision"));
            	commandValues.add(command);
            }
        } catch (SQLException e) {
            throw new DAOException("DAOException : CommandDAO getAll() :" + e.getMessage(), e);
        }
        return commandValues;
	}
	
	public ArrayList<DiscreteCommandValue> getAllDiscreteCommandValues(int id){
		ArrayList<DiscreteCommandValue> commandValues = new ArrayList<DiscreteCommandValue>();
        String sql = "SELECT * FROM DiscreteCommandValues WHERE fk_commandValue_id = ?";

        try {
            PreparedStatement prepStat = this.connect.prepareStatement(sql);
            prepStat.setInt(1, id);
            ResultSet rs = prepStat.executeQuery();
            DiscreteCommandValue command = null;
            while (rs.next()) {
            	command = new DiscreteCommandValue();
            	ArrayList<String> values = new ArrayList<>();
            	JSONObject json = new JSONObject(rs.getString("possible_values"));
            	JSONArray array = json.getJSONArray("possibleValues");
            	for (int i = 0; i < array.length(); i++) {
					values.add(array.getString(i));
				}
            	command.setPossibleValues(values);
            	commandValues.add(command);
            }
        } catch (SQLException e) {
            throw new DAOException("DAOException : CommandDAO getAll() :" + e.getMessage(), e);
        }
        return commandValues;
	}
	
	public static void main(String[] args) {
		try {
			System.out.println(AbstractDAOFactory.getFactory(0).getCommandDAO().getAll());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
