package com.momo.test.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.momo.test.pojo.Image;

public interface ImageDao extends JpaRepository<Image, String> {
	
	public List<Image> findImageByAlbumId(String albumId) throws Exception;

}
