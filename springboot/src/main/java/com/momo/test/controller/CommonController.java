package com.momo.test.controller;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.momo.test.exception.ErrorException;
import com.momo.test.pojo.Album;
import com.momo.test.pojo.User;
import com.momo.test.service.AlbumService;
import com.momo.test.utils.ResponseUtils;
import com.momo.test.utils.UUIDUtils;

@Controller
@RequestMapping("/common")
public class CommonController {

	// 资源文件路径
	public static final String RESOURCE_PATH = CommonController.class.getClassLoader().getResource("").getPath();

	@Autowired
	private AlbumService albumService;
	
	// 跳转home
	@RequestMapping("/home")
	public String home() {
		return "index";
	}

	@RequestMapping("/about")
	public String about() {
		return "about";
	}

	@RequestMapping("/blog")
	public String blog() {
		return "blog";
	}

	@RequestMapping("/contact")
	public String contact() {
		return "contact";
	}

	@RequestMapping("/portfolio")
	public String portfolio(Album album,Model moded,HttpSession session,HttpServletResponse response) {
		
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
		
		return "portfolio";
	}


	@RequestMapping(value = { "/company" })
	public String company() {
		return "company";
	}

	@RequestMapping("/uploadImg")
	public void uploadImg(@RequestParam("file") MultipartFile[] files, HttpServletResponse response,
			HttpSession session) throws Exception {
		try {
			if (files != null && files.length > 0) {
				if (!Files.exists(Paths.get("images"))) {
					Files.createDirectories(Paths.get("images"));
				}
				User user = (User) session.getAttribute("user");
				if (user == null || user.getId() ==null ) {
					ResponseUtils.sendMessage(response, false, "您还没有登录，请先去登录");
				}
				if (!Files.exists(Paths.get("images/"+user.getId()))) {
					Files.createDirectories(Paths.get("images/"+user.getId()));
				}
				for (MultipartFile file : files) {
					String fileName = UUIDUtils.getUUID32();
					System.out.println(fileName);
					Files.copy(file.getInputStream(), Paths.get("images/"+user.getId()+"/"+fileName), StandardCopyOption.REPLACE_EXISTING);
				}
				ResponseUtils.sendMessage(response, true, "图片上传成功");
				return;
			} else {
				ResponseUtils.sendMessage(response, false, "请选择一张图片");
				return;
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			ResponseUtils.sendMessage(response, true, "服务器繁忙请稍候重试");
		}
	}

	@RequestMapping("/downloadImg/{fileName}")
	public void getFile(@PathVariable String fileName, HttpServletResponse response) {
		try {
			response.getOutputStream().write(Files.readAllBytes(Paths.get(fileName)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
