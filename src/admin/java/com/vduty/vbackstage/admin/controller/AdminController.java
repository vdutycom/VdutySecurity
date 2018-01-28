package com.vduty.vbackstage.admin.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vduty.vbackstage.admin.entity.AdminsEntity;
import com.vduty.vbackstage.admin.mapper.AdminMapper;
import com.vduty.vbackstage.configs.Constants;
import com.vduty.vbackstage.controller.BaseController;
import com.vduty.vbackstage.entity.UsersBaseEntity;
import com.vduty.vbackstage.utils.RegexUtil;

import io.jsonwebtoken.Jwts;

@Controller
@RequestMapping(Constants.ADMIN_PATH)
public class AdminController extends BaseController {

	@Autowired
	AdminMapper adminMapper;

	@Autowired
	RedisTemplate<String,UsersBaseEntity> userRedisTemplate;

	@RequestMapping("")
	/**
	 * 
	 * @param model
	 * @return
	 */
	public String adminHome(Model model,HttpServletRequest req,HttpServletResponse res) {

		String userName =  this.getCurUserName(req, res);	
					
		UsersBaseEntity user = (UsersBaseEntity) userRedisTemplate.opsForValue().get(userName);
		
		System.out.print("首页获取username："+user.getName());
		
		model.addAttribute("user", user);

		return "views/admin/index";
	}

	@ResponseBody
	@RequestMapping("/userdata")
	public Map<String, Object> adminHomeData(HttpServletRequest req) {

		String auth = req.getHeader(Constants.JWT_HEADER_NAME);
		if (RegexUtil.isNotNull(auth)) {
			String Username = Jwts.parser().setSigningKey(Constants.JWT_SECURITY_KEY)
					.parseClaimsJws(auth.replace(Constants.JWT_TOKEN_FLAG, "")).getBody().getSubject();

			UsersBaseEntity user = (UsersBaseEntity) userRedisTemplate.opsForValue().get(Username);

			user.setPassword("");

			return this.dataSucc(req,1, "", user);
		} else {
			return this.dataFail(req, 0,null);
		}

	}

	@RequestMapping(Constants.USERCENTER_PATH)
	public String userManager() {
		return "views/admin/usermanager";
	}

}
