package com.catlivevideo.living.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.catlivevideo.living.dao.UserDao;
import com.catlivevideo.living.entity.User;

public class UserDapImpl extends BaseDao implements UserDao {

	@Override
	public User register(String userName, String pwd) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		User user = null;
		try {
			con = getConn();
			String sqlForCheck = "SELECT * FROM user WHERE userName = ?";
			stmt = con.prepareStatement(sqlForCheck);
			stmt.setString(1, userName);
			rs = stmt.executeQuery();	
			if(rs.next()){
				return null;
			}
			
			String sqlForAccountIdCheck = "SELECT * FROM user WHERE accountID = ?";
			String randomDigitString = null;
			do{
				this.close(rs, stmt, con);
				con = getConn();
				stmt = con.prepareStatement(sqlForAccountIdCheck);
				stmt.clearParameters();
				randomDigitString = getRandomDigitString(8);
				stmt.setString(1, randomDigitString);
				rs = stmt.executeQuery();
			}while(rs.next());
			this.close(rs, stmt, con);
			String sqlForInsert = "INSERT INTO user (userName,pwd,accountID,user_icon,lived_count,fans_count,fav_count,money_count,grade_count,isLiving) VALUES (?,?,?,?,?,?,?,?,?,?)";
			con = getConn();
			stmt = con.prepareStatement(sqlForInsert);
			stmt.clearParameters();
			stmt.setString(1, userName);
			stmt.setString(2, pwd);
			stmt.setString(3, randomDigitString);
			stmt.setString(4,"http://");
			stmt.setLong(5, 0);
			stmt.setLong(6, 0);
			stmt.setLong(7, 0);
			stmt.setLong(8, 0);
			stmt.setLong(9, 1);
			stmt.setBoolean(10, false);
			stmt.executeUpdate();
			this.close(rs, stmt, con);
			con = getConn();
			stmt = con.prepareStatement(sqlForCheck);
			stmt.clearParameters();
			stmt.setString(1, userName);
			rs = stmt.executeQuery();
			if(rs.next()){
				user = new User();
				user.setmId(rs.getLong(1)+"");
				user.setmUserName(rs.getString(2));
				user.setmPwd(rs.getString(3));
				user.setmAccountID(rs.getString(4));
				user.setmUser_icon(rs.getString(5));
				user.setmLived_count(rs.getLong(6));
				user.setmFans_count(rs.getLong(7));
				user.setmFav_count(rs.getLong(8));
				user.setmMoney_count(rs.getLong(9));
				user.setmGrade_count(rs.getLong(10));
				user.setmIsLiving(rs.getBoolean(11));
				return user;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return user;
		}finally{
			try {
				close(rs, stmt, con);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		return null;
	}

	private static String getRandomDigitString(int length) {
        String base = "0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

	@Override
	public User login(String userName, String pwd) {

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		User user = null;
		try {
			con = getConn();
			String sqlForCheck = "SELECT * FROM user WHERE userName = ? AND pwd = ?";
			stmt = con.prepareStatement(sqlForCheck);
			stmt.setString(1, userName);
			stmt.setString(2, pwd);
			rs = stmt.executeQuery();	
			if(rs.next()){
				user = new User();
				user.setmId(rs.getLong(1)+"");
				user.setmUserName(rs.getString(2));
				user.setmPwd(rs.getString(3));
				user.setmAccountID(rs.getString(4));
				user.setmUser_icon(rs.getString(5));
				user.setmLived_count(rs.getLong(6));
				user.setmFans_count(rs.getLong(7));
				user.setmFav_count(rs.getLong(8));
				user.setmMoney_count(rs.getLong(9));
				user.setmGrade_count(rs.getLong(10));
				user.setmIsLiving(rs.getBoolean(11));
				return user;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				close(rs, stmt, con);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		return null;
	
	}

	@Override
	public List<User> getLiveList(int page, int size, int isLive) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		User user = null;
		List<User> users = new ArrayList<User>();
		try {
			con = getConn();
			String sqlForCheck = "SELECT * FROM user WHERE isLiving = ? LIMIT ?,?";
			stmt = con.prepareStatement(sqlForCheck);
			stmt.setInt(1, isLive);
			stmt.setInt(2, page*size);
			stmt.setInt(3, size);
			rs = stmt.executeQuery();	
			while(rs.next()){
				user = new User();
				user.setmId(rs.getLong(1)+"");
				user.setmUserName(rs.getString(2));
				user.setmPwd(rs.getString(3));
				user.setmAccountID(rs.getString(4));
				user.setmUser_icon(rs.getString(5));
				user.setmLived_count(rs.getLong(6));
				user.setmFans_count(rs.getLong(7));
				user.setmFav_count(rs.getLong(8));
				user.setmMoney_count(rs.getLong(9));
				user.setmGrade_count(rs.getLong(10));
				user.setmIsLiving(rs.getBoolean(11));
				users.add(user);
			}
			return users;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				close(rs, stmt, con);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		return users;
	}

	@Override
	public long getCount(int isLive) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = getConn();
			String sqlForCheck = "SELECT count(*) FROM user WHERE isLiving = ?";
			stmt = con.prepareStatement(sqlForCheck);
			stmt.setInt(1, isLive);
			rs = stmt.executeQuery();	
			if(rs.next()){
				return Long.parseLong(rs.getString("count(*)"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				close(rs, stmt, con);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		return 0L;
	}

	@Override
	public List<User> getAllLiveList(int page, int size) {

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		User user = null;
		List<User> users = new ArrayList<User>();
		try {
			con = getConn();
			String sqlForCheck = "SELECT * FROM user LIMIT ?,?";
			stmt = con.prepareStatement(sqlForCheck);
			stmt.setInt(1, page*size);
			stmt.setInt(2, size);
			rs = stmt.executeQuery();	
			while(rs.next()){
				user = new User();
				user.setmId(rs.getLong(1)+"");
				user.setmUserName(rs.getString(2));
				user.setmPwd(rs.getString(3));
				user.setmAccountID(rs.getString(4));
				user.setmUser_icon(rs.getString(5));
				user.setmLived_count(rs.getLong(6));
				user.setmFans_count(rs.getLong(7));
				user.setmFav_count(rs.getLong(8));
				user.setmMoney_count(rs.getLong(9));
				user.setmGrade_count(rs.getLong(10));
				user.setmIsLiving(rs.getBoolean(11));
				users.add(user);
			}
			return users;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				close(rs, stmt, con);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		return users;
	
	}

	@Override
	public long getAllCount() {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = getConn();
			String sqlForCheck = "SELECT count(*) FROM user WHERE 1=1";
			stmt = con.prepareStatement(sqlForCheck);
			rs = stmt.executeQuery();	
			if(rs.next()){
				return Long.parseLong(rs.getString("count(*)"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				close(rs, stmt, con);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		return 0L;
	}

	@Override
	public void setLive(String accountID, int isLive) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = getConn();
			String sqlForUpdate = "UPDATE user SET isLiving = ? WHERE  accountID = ?";
			stmt = con.prepareStatement(sqlForUpdate);
			stmt.setInt(1, isLive);
			stmt.setString(2, accountID);
			stmt.executeUpdate();	
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				close(rs, stmt, con);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
	}

}
