package com.momo.test.service;

import java.util.List;

import com.momo.test.pojo.Album;

public interface AlbumService {

	public void saveAlbum(Album album) throws Exception;

	public void updateAlbum(Album album) throws Exception;

	public List<Album> queryAlbumList(Album album) throws Exception;
	
	public void deleteAlbum(String albumId) throws Exception;
	
	public Album getAlbum(String albumId) throws Exception;
}
