package com.catlivevideo.living.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public interface Dao {
	Connection getConn() throws Exception;
	void close(ResultSet rs,Statement statement,Connection conn)throws Exception;

}
