package com.momo.test.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.momo.test.dao.UserDao;
import com.momo.test.pojo.User;
import com.momo.test.service.UserService;
import com.momo.test.utils.MD5Utils;
import com.momo.test.utils.SerialNumberUtils;

@Service
@Transactional(readOnly=true)
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	
	@Override
	public User findUserByUserName(String username) throws Exception {
		return userDao.findUserByUsername(username);
		 
	}

	@Override
	public List<User> findAll() throws Exception {
		return userDao.findAll();
	}

	@Override
	@Transactional(readOnly=false)
	public User login(User user) throws Exception {
		if(StringUtils.isBlank(user.getUsername())){
			throw new RuntimeException("请您输入用户名");
		}
		if(StringUtils.isBlank(user.getPassword())){
			throw new RuntimeException("请您输入密码");
		}
		User existUser = userDao.findUserByUsername(user.getUsername());
		if(existUser == null){
			throw new RuntimeException("用户名或密码不正确");
		}
		String md5Str = MD5Utils.getMD5Str(user.getPassword());
		System.out.println(md5Str);
		if(!md5Str.equals(existUser.getPassword())){
			throw new RuntimeException("用户名或密码不正确");
		}
		existUser.setLastLoginTime(new Date());
		userDao.save(existUser);
		return existUser;
	}

	@Override
	@Transactional(readOnly=false)
	public void register(User user) throws Exception {
		if(StringUtils.isBlank(user.getUsername())){
			throw new RuntimeException("t_username 用户名不能为空");
		}
		if(StringUtils.isBlank(user.getPassword())){
			throw new RuntimeException("t_password 密码不能为空");
		}
		if(StringUtils.isBlank(user.getRealName())){
			throw new RuntimeException("t_realName 真实姓名不能为空");
		}
		if(StringUtils.isBlank(user.getShowName())){
			throw new RuntimeException("t_showName 昵称不能为空");
		}
		if(user.getPhone() ==null){
			throw new RuntimeException("t_phone 手机号不能为空");
		}
		user.setCreateTime(new Date());
		user.setId(Long.valueOf(SerialNumberUtils.getSerialNumber(System.currentTimeMillis())));
		user.setPassword(MD5Utils.getMD5Str(user.getPassword()));
		userDao.save(user);
	}
	
	
	
}
