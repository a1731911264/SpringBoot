package com.momo.test.service.impl;

import java.time.LocalDateTime;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.momo.test.dao.AlbumDao;
import com.momo.test.exception.ErrorException;
import com.momo.test.pojo.Album;
import com.momo.test.service.AlbumService;
import com.momo.test.utils.UUIDUtils;

@Transactional(readOnly=true)
@Service
public class AlbumServiceImpl implements AlbumService {

	@Autowired
	private AlbumDao albumDao; 
	
	@Override
	@Transactional(readOnly=false)
	public void saveAlbum(Album album) throws Exception {
		if(StringUtils.isBlank(album.getAlbumName())){
			throw new RuntimeException("请您填写相册名称");
		}
		// 设置相册主键
		String uuid32 = UUIDUtils.getUUID32();
		System.out.println(uuid32);
		album.setAlbumId(uuid32);
		
		// 设置默认封面图
//		album.
		// 设置相册状态
		album.setStatus(0);
		// 设置创建时间
		album.setCreateDate(LocalDateTime.now());
		Album save = albumDao.save(album);
		if (save ==null) {
			throw new ErrorException("新建相册失败，请稍候再试！");
		}
	}

}
