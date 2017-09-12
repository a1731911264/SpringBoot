package com.momo.test.controller;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
	@RequestMapping("/new")
	public void newAlbum(HttpSession session,HttpServletResponse response,Album album){
		try {
			// 判断用户是否登陆
			User user = (User) session.getAttribute("user");
			if(user == null || StringUtils.isBlank(user.getId())){
				throw new RuntimeException("您还没有登录，请先去登录！");
			}
			album.setUserId(user.getId());
			albumService.saveAlbum(album);
			ResponseUtils.sendMessage(response, true, "添加成功");
		} catch(ErrorException e){
			ResponseUtils.sendMessage(response, false, e.getErrorMessage());
		} catch (Exception e) {
			ResponseUtils.sendMessage(response, false, "服务器繁忙,请稍候再试！");
			e.printStackTrace();
		}
	}
}
