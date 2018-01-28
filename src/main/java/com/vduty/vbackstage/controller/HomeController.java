package com.vduty.vbackstage.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.http.client.utils.URLEncodedUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.http.HttpHeaders;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sun.imageio.plugins.common.SubImageInputStream;
import com.vduty.vbackstage.admin.mapper.AdminMapper;
import com.vduty.vbackstage.configs.Constants;
import com.vduty.vbackstage.entity.UsersBaseEntity;
import com.vduty.vbackstage.mapper.UserBaseMapper;
import com.vduty.vbackstage.redis.utils.RedisObjectSerializer;
import com.vduty.vbackstage.user.entity.UsersEntity;
import com.vduty.vbackstage.user.mapper.UserMapper;
import com.vduty.vbackstage.utils.RegexUtil;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Controller
@RequestMapping("/")
public class HomeController extends BaseController {
	
	@Autowired
	UserMapper userMapper;	
	@Autowired
	UserBaseMapper userBaseMapper;
	
	@Autowired
	AdminMapper adminMapper;
	 
	 @Autowired
	  StringRedisTemplate stringRedisTemplate;	 
	 
	 @Autowired
		private RedisTemplate<String,UsersBaseEntity> userRedisTemplate;
	 
	 
	 @Autowired
		private RedisTemplate redisTemplate;
	 
	 @Autowired 
	 PasswordEncoder passwordEncoder;
	
	/**
	 * 首页
	 * @param request
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("")
	public String index(HttpServletRequest request, HttpSession session, Model model) {

		
	 UsersBaseEntity user = new UsersBaseEntity("aaaa", "ppppppp");
	 
	 userRedisTemplate.opsForValue().set(user.getName(), user);
	 
	 System.out.println(userRedisTemplate.opsForValue().get(user.getName()));
	 
	 user = (UsersBaseEntity)userRedisTemplate.opsForValue().get(user.getName());
		
	System.out.print(user.getName() + user.getPassword());	
	 
		return "views/index";
	}
	
	
    /**
     * 测试页 
     * @param session
     * @param request
     * @param model
     * @return
     */
	@RequestMapping("/home")
	public String home( @RequestHeader HttpHeaders headers, HttpSession session, HttpServletRequest request,Model model) {			
		
		
		
		
		return "views/index";
	}

	

	/**
	 * 登录页 
	 * @param request
	 * @param res
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/login")
	public String login(HttpServletRequest request, HttpServletResponse res, HttpSession session, Model model) {	
		
		
		
		UsersEntity user =   (UsersEntity)redisTemplate.opsForValue().get("luther");
		 
		System.out.print(user.getName());
		
		return "/views/login";
	}
	
	
	@RequestMapping("adminlogin")
	public String adminLogin(HttpServletRequest request, HttpServletResponse res, HttpSession session, Model model) {	
		
		
		return "/views/adminlogin";
	}
	
	@SuppressWarnings("deprecation")
	@RequestMapping(value ="/adminlogin",method = RequestMethod.POST)
	public String adminLoginDo(    HttpServletRequest req,HttpServletResponse res ,Model model)
	{
		
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		
		
		if (RegexUtil.isNull(username))
		{
			model.addAttribute("error", "用户名不能为空");
			return "/adminlogin";
		}
		
		if (RegexUtil.isNull(password))
		{
			try {
				return "/adminlogin/?error=" + URLEncoder.encode("password=null","utf8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		System.out.print(username + "   "  + password);
		
		UsersEntity user = 	 userMapper.getOneByName(username);
		if (user !=null)
		{
			String userPassword = user.getPassword();
			//BCryptPasswordEncoder pe = new BCryptPasswordEncoder(); 
			if ( passwordEncoder.matches(password, userPassword))
			{
				
				//保存权限
				//....
				//jwt
				 String token = Jwts.builder()  
			                .setSubject(username)  
			                .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 24 * 1000))  
			                .signWith(SignatureAlgorithm.HS512, Constants.JWT_SECURITY_KEY)  
			                .compact();  
			        res.addHeader("Authorization", Constants.JWT_TOKEN_FLAG + token); 
				
			      //登录成功
			        userRedisTemplate.opsForValue().set(username, user);
				return "redirect:" + Constants.ADMIN_PATH;
			}
			else
			{
				//登录失败
				return "views/error";
				
			}
			
		}
		else//登录失败
		{
			return "views/error";
		}
		
		
	}
	
	@RequestMapping(value="/adminloginajax" , method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> adminLoginAjax( HttpServletRequest req,HttpServletResponse res,@Valid @RequestBody UsersBaseEntity user,BindingResult bindingResult)
	{		
		
		if (user!=null)
		{
			
			UsersBaseEntity userInfo = adminMapper.getOne(user.getName());
			
			
			if (userInfo !=null)
			{
				String userPassword = userInfo.getPassword();
				//BCryptPasswordEncoder pe = new BCryptPasswordEncoder(); 
				System.out.print(user.getPassword() + "   " + userPassword);
				if ( passwordEncoder.matches(user.getPassword(), userPassword))
				{
					
					//保存权限
					//....
					//jwt
					 String token = Jwts.builder()  
				                .setSubject(userInfo.getName())  
				                .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 24 * 1000))  
				                .signWith(SignatureAlgorithm.HS512, Constants.JWT_SECURITY_KEY)  
				                .compact();  
				        res.addHeader(Constants.JWT_HEADER_NAME, Constants.JWT_TOKEN_FLAG + token); 
				        Cookie cookie = new Cookie(Constants.JWT_HEADER_NAME, Constants.JWT_TOKEN_FLAG + token);  
			            cookie.setMaxAge(30 * 60);// 设置为30min  
			            cookie.setPath("/");  
				        res.addCookie(cookie);
					
				      //登录成功
					   userRedisTemplate.opsForValue().set(userInfo.getName(), userInfo);
					   
					   userInfo.setPassword("");
						return this.dataSucc(req,1,"登录成功",userInfo);
				}
				else
				{
					//登录失败
					return this.dataFail(req,-1,"登录失败，密码错误");
					
				}
				
			}
			else
			{
				//登录失败
				return this.dataFail(req,-1,"登录失败，用户名错误");
			}
			
		}
		else
		{
				
			return this.dataFail(req,-1,"登录失败，参数错误");
		}
	}
	

	/**
	 * 登录成功
	 * @return
	 */
	@RequestMapping("/loginsucc")
	public String loginSucc() {

		return "loginSucc";
	}
	

}
