package com.momo.test.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
				albumService.saveAlbum(album);
				album.setCreateDate(LocalDateTime.now());
			}else{
				if (StringUtils.isBlank(album.getCover())) {
					album.setCover("/static/images/photoTile.jpg");
				}
				album.setUpdateDate(LocalDateTime.now());
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
	@RequestMapping("/queryAlbumList")
	public String queryAlbumList(Album album,Model moded,HttpSession session,HttpServletResponse response){
		// 判断用户是否登陆
		try {
			User user = (User) session.getAttribute("user");
			if(user == null || StringUtils.isBlank(user.getId())){
				throw new RuntimeException("您还没有登录，请先去登录！");
			}
			album.setUserId(user.getId());
			List<Album> list =  albumService.queryAlbumList(album);			
			moded.addAttribute("albums", list);
			moded.addAttribute("Album", album);
		} catch(ErrorException e){
			ResponseUtils.sendMessage(response, false, e.getErrorMessage());
		} catch (Exception e) {
			ResponseUtils.sendMessage(response, false, "服务器繁忙,请稍候再试！");
			e.printStackTrace();
		}
		return "forward:/common/portfolio";
	}
}
