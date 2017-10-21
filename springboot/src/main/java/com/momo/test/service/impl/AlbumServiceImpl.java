package com.momo.test.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Example.PropertySelector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.momo.test.dao.AlbumDao;
import com.momo.test.dao.ImageDao;
import com.momo.test.exception.ErrorException;
import com.momo.test.pojo.Album;
import com.momo.test.pojo.Image;
import com.momo.test.service.AlbumService;
import com.momo.test.utils.UUIDUtils;

@Transactional(readOnly = true)
@Service
public class AlbumServiceImpl implements AlbumService {

	private static final PropertySelector P = null;
	@Autowired
	private AlbumDao albumDao;
	@Autowired
	private ImageDao imageDao;

	@Override
	@Transactional(readOnly = false)
	public void saveAlbum(Album album) throws Exception {
		if (StringUtils.isBlank(album.getAlbumName())) {
			throw new ErrorException("请填写相册名称！");
		}
		// 设置相册主键
		String uuid32 = UUIDUtils.getUUID32();
		System.out.println(uuid32);
		album.setAlbumId(uuid32);
		// 删除标志
		album.setStatus(0);
		// 设置创建时间
		album.setCreateDate(new Date());
		Album save = albumDao.save(album);
		if (save == null) {
			throw new ErrorException("新建相册失败，请稍候再试！");
		}
	}

	@Override
	@Transactional(readOnly = false)
	public void updateAlbum(Album album) throws Exception {
		if (StringUtils.isBlank(album.getAlbumName())) {
			throw new ErrorException("请填写相册名称！");
		}
		Album one = albumDao.getOne(album.getAlbumId());
		one.setAlbumName(album.getAlbumName());
		one.setAlbumDesc(album.getAlbumDesc());
		one.setAlbumTheme(album.getAlbumTheme());
		one.setAlbumClassify(album.getAlbumClassify());
		one.setSystemAuthority(album.getSystemAuthority());
		one.setOtherAuthority(album.getOtherAuthority());
		one.setUpdateDate(new Date());
		albumDao.saveAndFlush(one);
	}

	@Override
	public List<Album> queryAlbumList(Album album) throws ErrorException {
		List<Album> lists = null;
		if (StringUtils.isBlank(album.getAlbumName())) {
			lists = albumDao.queryAlbumListAll(album.getUserId(),0);
			 
		}else{
			 lists = albumDao.queryAlbumList(album.getAlbumName(),album.getUserId());
		}
		
		return lists ;
	}

	// 根据相册id 删除照片 并不是真的删除而是修改状态
	@Override
	@Transactional(readOnly = false)
	public void deleteAlbum(String albumId) throws Exception {

		if (StringUtils.isBlank(albumId)) {
			throw new ErrorException("请选择一个相册！");
		}
		List<Image> imageList = imageDao.findImageByAlbumId(albumId);
		if (imageList != null && imageList.size() > 0) {
			// 修改照片状态
			for (Image image : imageList) {
				image.setStatus(1);
			}
			// 保存照片
			imageDao.saveAll(imageList);
		}
		Album album = albumDao.getOne(albumId);
		album.setStatus(1);
		albumDao.save(album);
	}

	@Override
	public Album getAlbum(String albumId) throws Exception {
		if(StringUtils.isBlank(albumId)){
			throw new ErrorException("请先选择一个相册");
		}
		
		return albumDao.getOne(albumId);
	}

}
