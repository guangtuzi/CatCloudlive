package com.catlivevideo.living.dao.impl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import com.catlivevideo.living.dao.Dao;

public class BaseDao implements Dao{
	private static String url;
	private static String driver;
	private static String userName;
	private static String password;

	static {
		Properties props = new Properties();
		try {
			props.load(BaseDao.class.getClassLoader().getResourceAsStream("com/catlivevideo/living/config.properties"));
		} catch (IOException e) {
      e.printStackTrace();
		}
		if (props != null) {
			url = props.getProperty("url");
			driver = props.getProperty("driver");
			userName = props.getProperty("userName");
			password = props.getProperty("password");

			try {
				Class.forName(driver);
			} catch (ClassNotFoundException e) {
			}
		}
	}


	@Override
	public Connection getConn() throws Exception {
		return DriverManager.getConnection(url, userName, password);
	}

	@Override
	public void close(ResultSet rs, Statement statement, Connection conn)
			throws Exception {
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (statement != null) {
				statement.close();
				statement = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
