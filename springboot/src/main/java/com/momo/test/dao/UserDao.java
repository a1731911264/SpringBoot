package com.momo.test.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.momo.test.pojo.User;



/*@CacheConfig(cacheNames="user")*/
public interface UserDao extends JpaRepository<User, Long> {

	/*@Cacheable(key="#p0")*/
	public User findUserByUsername(String username) throws Exception;
}
