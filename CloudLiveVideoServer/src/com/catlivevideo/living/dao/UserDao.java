package com.catlivevideo.living.dao;

import java.util.List;

import com.catlivevideo.living.entity.User;

public interface UserDao {
	User register(String userName,String pwd);
	User login(String userName,String pwd);
	List<User> getLiveList(int page,int size,int isLive);
	List<User> getAllLiveList(int page,int size);
	long getCount(int isLive);
	long getAllCount();
	void setLive(String accountID,int isLive);
	
	

}
