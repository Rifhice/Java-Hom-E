package server.dao;

import java.sql.Connection;
import java.util.ArrayList;

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
	 * Create a new object and return it. 
	 * @param obj
	 * @return T, the obj created or null if an error occurred.
	 */
	public abstract T create(T obj) throws DAOException;
	
	/**
	 * Get an existing object by his id.
	 * @param id, int
	 * @return T
	 */
	public abstract T getById(int id) throws DAOException;
	
	/**
	 * Modify an existing object.
	 * @param obj
	 * @return boolean
	 */
	public abstract boolean update(T obj) throws DAOException;
	
	/**
	 * Delete the object with id equals to the given one.
	 * @param id, int
	 * @return int, the number of rows deleted.
	 */
	public abstract int delete(int id) throws DAOException;
	
	/**
     * Get all the existing objects.
     * @return ArrayList<T>, an ArrayList of all the objects.
     */
	public abstract ArrayList<T> getAll() throws DAOException;
}
