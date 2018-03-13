package dao;

import java.sql.Connection;

public abstract class DAO <T> {
    
    // ====================== //
    // ==== ATTRIBUTES ==== //
    // ====================== //
    protected Connection connect;
    
    // ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
    public DAO(Connection connect) {
        this.connect = connect;
    }
    
    // ================= //
    // ==== METHODS ==== //
    // ================= //
	/**
	 * Create a new object.
	 * @param obj
	 * @return boolean
	 */
	public abstract boolean create(T obj) throws DAOException;
	
	/**
	 * Get an existing object by his id.
	 * @param id
	 * @return T
	 */
	public abstract T getById(String id) throws DAOException;
	
	/**
	 * Modify an existing object.
	 * @param obj
	 * @return boolean
	 */
	public abstract boolean update(T obj) throws DAOException;
	
	/**
	 * Delete the object with id equals to the given one.
	 * @param id, String
	 * @return int, the number of rows deleted
	 */
	public abstract int delete(String id) throws DAOException;
}
