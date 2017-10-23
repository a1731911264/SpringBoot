package com.momo.test.service;

import java.util.List;

import com.momo.test.pojo.User;

public interface UserService {
	
	public User findUserByUserName(String username) throws Exception;
	
	public List<User> findAll() throws Exception;
	
	public User login(User user) throws Exception;
	
	public void register(User user) throws Exception;
	
	public void updateHeadUrl(User user) throws Exception;
	
	public void updateUserInfo(User user) throws Exception;
}
