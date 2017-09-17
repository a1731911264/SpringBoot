package com.momo.test.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers;
import org.springframework.data.domain.Sort;
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
		//删除标志
		album.setStatus(0);
		// 设置创建时间
		album.setCreateDate(LocalDateTime.now());
		Album save = albumDao.save(album);
		if (save ==null) {
			throw new ErrorException("新建相册失败，请稍候再试！");
		}
	}
	@Override
	@Transactional(readOnly=false)
	public void updateAlbum(Album album) throws Exception {
		if(StringUtils.isBlank(album.getAlbumName())){
			throw new RuntimeException("请您填写相册名称");
		}
		albumDao.saveAndFlush(album);
	}
	@Override
	public List<Album> queryAlbumList(Album album) throws ErrorException {
		List<Album> lists = null;
		if (StringUtils.isBlank(album.getAlbumName())) {
			
			Sort by = Sort.by("createDate");
			by.descending();
			lists = albumDao.findAll(by);
			 
		}else{
			 lists = albumDao.queryAlbumList(album.getAlbumName());
		}
		
		return lists ;
	}

}
