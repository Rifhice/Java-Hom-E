package server.dao.SQLiteDAO;

import java.sql.*;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import server.dao.abstractDAO.DAOException;
import server.dao.abstractDAO.EnvironmentVariableDAO;
import server.factories.AbstractDAOFactory;
import server.models.environmentVariable.ContinuousValue;
import server.models.environmentVariable.DiscreteValue;
import server.models.environmentVariable.EnvironmentVariable;
import server.models.environmentVariable.Value;

public class SQLiteEnvironmentVariableDAO extends EnvironmentVariableDAO {

    // ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
    public SQLiteEnvironmentVariableDAO(Connection connectionDriver) {
        super(connectionDriver);
    }

    // ================= //
    // ==== METHODS ==== //
    // ================= //

    @Override
    public EnvironmentVariable create(EnvironmentVariable obj) throws DAOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public EnvironmentVariable getById(int id) throws DAOException {
        EnvironmentVariable ev = null;
        
        String sql = "SELECT EV.id AS id, EV.name AS name, EV.description AS description, EV.unit AS unit "
                + "FROM EnvironmentVariables AS EV "
                + "WHERE EV.id = ? "
                + ";";
        
        try {
            PreparedStatement prepStat = this.connect.prepareStatement(sql);
            prepStat.setInt(1, id);
            ResultSet rs = prepStat.executeQuery();
            if(rs.next()) { 
                do {
                    ev = new EnvironmentVariable();
                    ev.setId(rs.getInt("id"));
                    ev.setName(rs.getString("name"));
                    ev.setDescription(rs.getString("description"));
                    ev.setUnit(rs.getString("unit"));
                    
                    Value v = getValue(ev);
                    ev.setValue(v);    
                } while (rs.next());  
            }            
        }
        catch(SQLException e) {
            throw new DAOException("DAOException : EnvironmentVariable getById("+id+") : " + e.getMessage(),e);
        }   
        
        return ev;
    }

    @Override
    public int update(EnvironmentVariable obj) throws DAOException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int delete(int id) throws DAOException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public ArrayList<EnvironmentVariable> getAll() throws DAOException {
        ArrayList<EnvironmentVariable> evs = new ArrayList<EnvironmentVariable>();
        
        String sql = "SELECT Ev.id AS id, Ev.name AS name, Ev.description AS description "
                + "FROM EnvironmentVariables AS Ev "
                + ";";
        
        try {
            PreparedStatement prepStat = this.connect.prepareStatement(sql);
            ResultSet rs = prepStat.executeQuery();
            if(rs.next()) { 
                do {
                    EnvironmentVariable ev = null;     
                    ev = new EnvironmentVariable();
                    ev.setId(rs.getInt("id"));
                    ev.setName(rs.getString("name"));
                    ev.setDescription(rs.getString("description"));   
                    
                    Value v = getValue(ev);
                    ev.setValue(v);
                    
                    evs.add(ev);
                } while (rs.next());  
            }            
        }
        catch(SQLException e) {
            throw new DAOException("DAOException : EnvironmentVariable getAll() : " + e.getMessage(),e);
        }   
        
        return evs;
    }
    
    
    // ========================= //
    // ===== HELPER METHODS ==== //
    // ========================= //   
    public Value getValue(EnvironmentVariable ev) throws DAOException {
        Value v = null;
        String sql = "SELECT V.id, "
                + "DV.fk_vvalue_id AS DVfk_vvalue_id, DV.current_value AS DVcurrent_value, DV.possible_values AS DVpossible_values, "
                + "CV.fk_vvalue_id AS CVfk_vvalue_id, CV.value_min AS CVvalue_min, CV.value_max AS CVvalue_max, "
                + "CV.current_value AS CVcurrent_value, CV.precision AS CVprecision "
                + "FROM Vvalues AS V "
                + "JOIN EnvironmentVariables AS EV ON EV.fk_vvalue_id = V.id "
                + "LEFT JOIN ContinuousVValues AS CV ON CV.fk_vvalue_id = V.id "
                + "LEFT JOIN DiscreteVValues AS DV ON DV.fk_vvalue_id = V.id "
                + "WHERE EV.id = ? "
                + ";";

        try {
            PreparedStatement prepStat = this.connect.prepareStatement(sql);
            prepStat.setInt(1, ev.getId());
            ResultSet rs = prepStat.executeQuery();
            if(rs.next()) { 
                if(rs.getInt("CVfk_vvalue_id") != 0) {
                    v = new ContinuousValue();
                    v.setId(rs.getInt("CVfk_vvalue_id"));
                    ((ContinuousValue) v).setValueMin(rs.getDouble("CVvalue_min"));
                    ((ContinuousValue) v).setValueMax(rs.getDouble("CVvalue_max"));
                    ((ContinuousValue) v).setCurrentValue(rs.getDouble("CVcurrent_value"));
                    ((ContinuousValue) v).setPrecision(rs.getDouble("CVprecision"));
                    ev.setValue(v);                }
                else {
                    v = new DiscreteValue();
                    v.setId(rs.getInt("DVfk_vvalue_id"));
                    ((DiscreteValue) v).setCurrentValue(rs.getString("DVcurrent_value"));
                    
                    JSONObject JSONpossibleValues = new JSONObject(rs.getString("DVpossible_values"));
                    JSONArray JSONarray = JSONpossibleValues.getJSONArray("possibleValues");
                    ArrayList<String> possibleValues = new ArrayList(JSONarray.toList());
                    ((DiscreteValue) v).setPossibleValues(possibleValues);
                    
                    ev.setValue(v);
                }
            }
        } catch(SQLException e) {
            throw new DAOException("DAOException : EnvironmentVariable getValue(" + ev.getId() + ") : " + e.getMessage(),e);
        }
        return v;
    }



    // ============== //
    // ==== MAIN ==== //
    // ============== // 
    public static void main (String args[]) {
        EnvironmentVariableDAO test = AbstractDAOFactory.getFactory(AbstractDAOFactory.SQLITE_DAO_FACTORY).getEnvironmentVariableDAO();
        System.out.println(test.getById(1));
    }



}
