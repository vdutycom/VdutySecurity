package com.vduty.vbackstage.controller;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vduty.vbackstage.admin.entity.AdminsEntity;
import com.vduty.vbackstage.admin.mapper.AdminMapper;
import com.vduty.vbackstage.user.entity.UsersEntity;
import com.vduty.vbackstage.user.mapper.UserMapper;
import com.vduty.vbackstage.utils.RandomUtils;

@Controller
@RequestMapping("/regist")
public class UserReg {

	static final Logger logger = LogManager.getLogger(UserReg.class);
	
	@Autowired
	UserMapper userMapper;
	
	@Autowired
	AdminMapper adminMapper;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	
	/**
	 * 用户注册
	 * @return
	 */
	@RequestMapping("")
	public String regist(Model model)
	{
		String password = "1111";		
		logger.info("current password:"+password); 
        String userName = RandomUtils.generateString(6).toLowerCase(); 			       
		AdminsEntity admin  = new AdminsEntity(); 
		admin.setName(userName);
		admin.setNickName(userName);
		
		password = passwordEncoder.encode(password);
		admin.setPassword(password);
		int[] intArr =  {1,2,3,5,6,7,8,9,0}; 
		admin.setPhone("138" + RandomUtils.toFixdLengthString(RandomUtils.getNotSimple(  intArr,8), 8));
		admin.setEmail(userName + "@vduty.com");
			
		adminMapper.insert(admin);	
		
		model.addAttribute("user",admin);
		
		return "views/regist";		
	}
	
	/**
	 * 提交用户注册信息
	 */
	@RequestMapping("/save")
	public String  saveUser()
	{
		
		return "views/registresult";
		
	}
	
}
