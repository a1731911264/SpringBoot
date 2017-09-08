package com.momo.test.controller;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.momo.test.pojo.User;
import com.momo.test.utils.ResponseUtils;
import com.momo.test.utils.UUIDUtils;

@Controller
@RequestMapping("/common")
public class CommonController {

	// 资源文件路径
	public static final String RESOURCE_PATH = CommonController.class.getClassLoader().getResource("").getPath();

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
		return "portfolio";
	}

	@RequestMapping(value = { "/portals", "/" })
	public String portals() {
		return "portals";
	}

	@RequestMapping(value = { "/company" })
	public String company() {
		return "company";
	}

	@RequestMapping(value = { "/menu" })
	public String menu() {
		return "menu";
	}

	@RequestMapping("/uploadImg")
	public void uploadImg(@RequestParam("file") CommonsMultipartFile[] files, HttpServletResponse response,
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
				for (CommonsMultipartFile file : files) {
					String fileName = UUIDUtils.getUUID32();
					System.out.println(fileName);
					Files.copy(file.getInputStream(), Paths.get("images/"+user.getId()+"/"+fileName), StandardCopyOption.REPLACE_EXISTING);
				}
				ResponseUtils.sendMessage(response, false, "请选择文件");
				return;
			} else {
				ResponseUtils.sendMessage(response, true, "图片上传成功");
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
