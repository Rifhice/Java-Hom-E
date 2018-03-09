package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import models.User;

public interface UserDAO  {
	public User getByPseudo(String pseudo);
}
