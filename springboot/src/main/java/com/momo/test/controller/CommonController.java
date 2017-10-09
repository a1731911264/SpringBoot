package com.momo.test.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
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
import com.momo.test.pojo.Image;
import com.momo.test.pojo.User;
import com.momo.test.service.AlbumService;
import com.momo.test.service.ImageService;
import com.momo.test.utils.ResponseUtils;
import com.momo.test.utils.UUIDUtils;

@Controller
@RequestMapping("/common")
public class CommonController {

	// 资源文件路径
	public static final String RESOURCE_PATH;
	static {
		String temp = CommonController.class.getClassLoader().getResource("").getPath()+"static/images/";
		RESOURCE_PATH = temp.substring(1);
	}

	@Autowired
	private AlbumService albumService;
	@Autowired
	private ImageService imageService;
	
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
	public String portfolio() {
		
//		// 判断用户是否登陆
//		try {
//			User user = (User) session.getAttribute("user");
//			if(user == null || StringUtils.isBlank(user.getId())){
//				throw new RuntimeException("您还没有登录，请先去登录！");
//			}
//			album.setUserId(user.getId());
//			List<Album> list =  albumService.queryAlbumList(album);	
//			if(list==null || list.size()==0){
//				moded.addAttribute("albums", null);
//			}else{
//				moded.addAttribute("albums", list);
//			}
//			moded.addAttribute("Album", album);
//		} catch(ErrorException e){
//			ResponseUtils.sendMessage(response, false, e.getErrorMessage());
//		} catch (Exception e) {
//			ResponseUtils.sendMessage(response, false, "服务器繁忙,请稍候再试！");
//			e.printStackTrace();
//		}
		
		return "portfolio";
	}


	@RequestMapping(value = { "/company" })
	public String company() {
		return "company";
	}

	@RequestMapping("/uploadImg")
	public void uploadImg(MultipartFile file, HttpServletResponse response,
			HttpSession session,String albumId) throws Exception {
		InputStream inputStream = file.getInputStream();
		try{
			if (inputStream != null) {
				if(StringUtils.isBlank(albumId)){
					System.out.println("相册id为空");
					ResponseUtils.sendMessage(response, false, "服务器繁忙请稍候重试");
					return;
				}
				User user = (User) session.getAttribute("user");
				if (user == null || user.getId() ==null ) {
					ResponseUtils.sendMessage(response, false, "您还没有登录，请先去登录");
				}
				if (!Files.exists(Paths.get(RESOURCE_PATH+user.getId()))) {
					Files.createDirectories(Paths.get(RESOURCE_PATH+user.getId()));
				}
				String fileName = UUIDUtils.getUUID32();
				System.out.println(fileName);
				Files.copy(inputStream, Paths.get(RESOURCE_PATH+user.getId()+"/"+fileName), StandardCopyOption.REPLACE_EXISTING);
				// 将图片信息存储到数据库中
				Image image = new Image();
				image.setImageId(fileName);
				image.setCreateTime(new Date());
				image.setStatus(0);
				image.setImageSize(String.valueOf(file.getSize()));
				image.setAlbumId(albumId);
				image.setOriginalName(file.getOriginalFilename());
				image.setImageUrl(RESOURCE_PATH+user.getId()+"/"+fileName);
				imageService.saveImage(image);
				ResponseUtils.sendMessage(response, true, "图片上传成功");
				return;
			} else {
				ResponseUtils.sendMessage(response, false, "请选择一张图片");
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			ResponseUtils.sendMessage(response, false, "服务器繁忙请稍候重试");
		}finally{
			inputStream.close();
		}
	}

	@RequestMapping("/downloadImg/{fileName}")
	public void getFile(@PathVariable String fileName, HttpServletResponse response,HttpSession session) throws Exception {
		ServletOutputStream outputStream = response.getOutputStream();
		try {
			User user = (User) session.getAttribute("user");
			outputStream.write(Files.readAllBytes(Paths.get(RESOURCE_PATH+user.getId()+"/"+fileName)));
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			outputStream.close();
		}
	}
}
