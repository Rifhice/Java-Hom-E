package factories;

import java.sql.Connection;

import dao.DriverConnection;
import dao.UserDAO;

public class OracleDAOFactory extends AbstractDAOFactory {
    protected Connection connect = DriverConnection.getInstance(DriverConnection.ORACLE_DRIVER); 

    @Override
    public UserDAO getUserDAO() {
        // TODO Auto-generated method stub
        return null;
    }

}
