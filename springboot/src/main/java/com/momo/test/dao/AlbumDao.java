package com.momo.test.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.momo.test.pojo.Album;
import com.momo.test.pojo.User;

public interface AlbumDao extends JpaRepository<Album, String> {

	@Query("from Album where status = 0 and albumName like %:albumName% and userId =:userId order by createDate desc ")
	public  List<Album> queryAlbumList(@Param("albumName")String albumName, @Param("userId")String userId);
	 
	@Query("from Album where status =:status and userId =:userId order by createDate desc ")
	public 	List<Album> queryAlbumListAll(@Param("userId")String userId,@Param("status")int status);
}
