package com.momo.test.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.momo.test.dao.AlbumDao;
import com.momo.test.dao.ImageDao;
import com.momo.test.pojo.Album;
import com.momo.test.pojo.Image;
import com.momo.test.service.ImageService;
@Service
public class ImageServiceImpl implements ImageService {

	
	
	@Autowired
	private ImageDao imageDao;
	@Autowired
	private AlbumDao albumDao;
	
	@Override
	public void saveImage(Image image) throws Exception {
		imageDao.save(image);
	}

}
