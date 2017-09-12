package com.momo.test.controller;


import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.momo.test.exception.ErrorException;
import com.momo.test.pojo.User;
import com.momo.test.service.UserService;
import com.momo.test.utils.ResponseUtils;

@Controller
@RequestMapping("/user")
public class UserController {

	private static Log log = LogFactory.getLog(UserController.class);

	@Autowired
	private UserService userService;

	@RequestMapping(value = { "/toLogin"})
	public String toLogin() {
		return "login";
	}

	@RequestMapping("/index")
	public String index() {
		return "index";
	}

	@RequestMapping("/login")
	public void login(User user, Model model, HttpSession session, HttpServletResponse response) {
		try {
			log.info("用户：" + user.getUsername() + "开始登录");
			User login = userService.login(user);
			session.setAttribute("user", login);
			ResponseUtils.sendMessage(response, true, null);
			log.info("用户：" + user.getUsername() + "完成登录");
		} catch (ErrorException e) {
			ResponseUtils.sendMessage(response, false, e.getMessage());
		} catch (Exception e) {
			ResponseUtils.sendMessage(response, false, "服务器繁忙，请稍后再试");
			e.printStackTrace();
		}
	}

	// 跳转到注册页面
	@RequestMapping(value = "/toRegister")
	public String toRegister() {
		return "register";
	}

	// 校验用户名
	@RequestMapping("/checkUsername")
	public void checkUsername(String username, HttpServletResponse response) {
		try {
			User user = userService.findUserByUserName(username);
			if (user == null) {
				ResponseUtils.sendMessage(response, true, "用户名可以使用");
			} else {
				ResponseUtils.sendMessage(response, true, "用户名已经被注册");
			}
		} catch (Exception e) {
			e.printStackTrace();
			ResponseUtils.sendMessage(response, false, "服务器繁忙，请稍后再试");
		}
	}

	@RequestMapping("/register")
	public void register(User user, HttpServletResponse response, HttpSession session, String registerCode) {
		try {
			if (StringUtils.isBlank(registerCode)) {
				throw new RuntimeException("t_registerCode 请输入验证码");
			}
			if (!registerCode.equalsIgnoreCase((String) session.getAttribute("imgCode"))) {
				throw new RuntimeException("t_registerCode 您输入的验证码有误");
			}
			userService.register(user);
			ResponseUtils.sendMessage(response, true,null);
			session.removeAttribute("imgCode");
			log.info("用户：" + user.getUsername() + "完成注册");
		} catch (ErrorException e) {
			ResponseUtils.sendMessage(response, false, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			ResponseUtils.sendMessage(response, false, "服务器繁忙，请稍后再试");
		}
	}

	// 异步校验验证码接口
	@RequestMapping("/checkValidateCode")
	public void checkValidateCode(String code, String name, HttpSession session, HttpServletResponse response) {
		String imgCode = (String) session.getAttribute(name);
		if (StringUtils.isBlank(code)) {
			ResponseUtils.sendMessage(response, false, "请输入验证码");
			return;
		}
		if (!code.equalsIgnoreCase(imgCode)) {
			ResponseUtils.sendMessage(response, false, "您输入的验证码有误");
		} else {
			ResponseUtils.sendMessage(response, true, "验证码正确");
		}
	}
	// 退出登录
	@RequestMapping("/logout")
	public String logout(HttpSession session){
		User user = (User) session.getAttribute("user");
		if(user ==null){
			return "redirect:portals";
		}
		log.info("用户："+user.getUsername()+"退出系统！");
		session.removeAttribute("user");
		return "redirect:portals";
	}
	// 头像上传
	@RequestMapping(value="uploadHead")
	public void uploadHead(String headUrl,HttpSession session,HttpServletResponse response){
		try {
			if(StringUtils.isBlank(headUrl)){
				ResponseUtils.sendMessage(response, false, "请选择一张图片");
				return;
			}
			System.out.println(headUrl.length());
			User user = (User) session.getAttribute("user");
			if(user !=null && StringUtils.isNotBlank(user.getId())){
				// 更新session数据
				user.setHeadUrl(headUrl);
				userService.updateHeadUrl(user);
				ResponseUtils.sendMessage(response, true, headUrl);
				return;
			}
			ResponseUtils.sendMessage(response, false, "您还没有登录或登录过期，请重新登录！");
		} catch (Exception e) {
			e.printStackTrace();
			ResponseUtils.sendMessage(response, false, "服务器繁忙，请稍候再试");
		}
		
	}
}
