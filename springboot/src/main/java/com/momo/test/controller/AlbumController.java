package com.momo.test.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.momo.test.exception.ErrorException;
import com.momo.test.pojo.Album;
import com.momo.test.pojo.User;
import com.momo.test.service.AlbumService;
import com.momo.test.utils.ResponseUtils;

@Controller
@RequestMapping("/album")
public class AlbumController {

	@Autowired
	private AlbumService albumService;
	
	// 新建相册
	@RequestMapping("/saveOrUpdateAlbum")
	public void saveOrUpdateAlbum(HttpSession session,HttpServletResponse response,Album album){
		try {
			// 判断用户是否登陆
			User user = (User) session.getAttribute("user");
			if(user == null || StringUtils.isBlank(user.getId())){
				throw new RuntimeException("您还没有登录，请先去登录！");
			}
			if (StringUtils.isBlank(album.getAlbumId())) {
				album.setUserId(user.getId());
				album.setCover("/images/photoTile.jpg");
				album.setCreateDate(new Date());
				albumService.saveAlbum(album);
				
			}else{
				if (StringUtils.isBlank(album.getCover())) {
					album.setCover("/images/photoTile.jpg");
				}
				album.setUpdateDate(new Date());
				albumService.updateAlbum(album);
			}
			ResponseUtils.sendMessage(response, true, "添加成功");
		} catch(ErrorException e){
			ResponseUtils.sendMessage(response, false, e.getErrorMessage());
		} catch (Exception e) {
			ResponseUtils.sendMessage(response, false, "服务器繁忙,请稍候再试！");
			e.printStackTrace();
		}
	}
	

	/**
	 * 查询相册列表
	 * @return
	 */
	@RequestMapping("/reload")
	public void reload(Album album,HttpSession session,HttpServletResponse response){
		// 判断用户是否登陆
		try {
			User user = (User) session.getAttribute("user");
			if(user == null || StringUtils.isBlank(user.getId())){
				throw new ErrorException("您还没有登录，请先去登录！");
			}
			album.setUserId(user.getId());
			List<Album> list =  albumService.queryAlbumList(album);			
			String lists = JSON.toJSONString(list);
			ResponseUtils.sendMessage(response, true, lists);
		} catch(ErrorException e){
			ResponseUtils.sendMessage(response, false, e.getErrorMessage());
		} catch (Exception e) {
			ResponseUtils.sendMessage(response, false, "服务器繁忙,请稍候再试！");
			e.printStackTrace();
		}
	}
	
	// 单个删除相册
	@RequestMapping("/deleteAlbum")
	public void deleteAlbum(HttpSession session, HttpServletResponse response,String albumId){
		try {
			User user = (User) session.getAttribute("user");
			if(user == null || StringUtils.isBlank(user.getId())){
				throw new ErrorException("您还没有登录，请先去登录！");
			}
			albumService.deleteAlbum(albumId);
			ResponseUtils.sendMessage(response, true,null);
		} catch(ErrorException e){
			ResponseUtils.sendMessage(response, false, e.getErrorMessage());
		} catch (Exception e) {
			ResponseUtils.sendMessage(response, false, "服务器繁忙,请稍候再试！");
			e.printStackTrace();
		}
	}
	@RequestMapping("/getAlbum")
	public void getAlbum(String albumId,HttpServletResponse response){
		try {
			Album album = albumService.getAlbum(albumId);
			ResponseUtils.sendMessage(response, true,album);
		} catch(ErrorException e){
			ResponseUtils.sendMessage(response, false, e.getErrorMessage());
		} catch (Exception e) {
			ResponseUtils.sendMessage(response, false, "服务器繁忙,请稍候再试！");
			e.printStackTrace();
		}
	}
}
