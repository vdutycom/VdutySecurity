package com.vduty.vbackstage.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;

import com.vduty.vbackstage.configs.Constants;
import com.vduty.vbackstage.utils.RegexUtil;

import io.jsonwebtoken.Jwts;

public abstract class BaseController {

	protected static final Logger logger = LogManager.getLogger(HomeController.class);

	protected String getCurUserName( HttpServletRequest req, HttpServletResponse res) {
		
		
		
		String token = req.getHeader(Constants.JWT_HEADER_NAME);
		Cookie[] cookies = req.getCookies();
		if (RegexUtil.isNull(token) && RegexUtil.isNotNull(cookies))
		{
			 for (Cookie c : cookies) {
		            if (c.getName().equals(Constants.JWT_HEADER_NAME))
		            {
		            	token = c.getValue();
		            logger.info(Constants.JWT_HEADER_NAME+" cookie:"+c.getValue());
		               break;
		            }
		        }
		}
		if (token != null) {
			// parse the token.
			String username = Jwts.parser().setSigningKey(Constants.JWT_SECURITY_KEY)
					.parseClaimsJws(token.replace(Constants.JWT_TOKEN_FLAG, "")).getBody().getSubject();
			return username;
		}
		

		return null;

	}
	
	/**
	 * 成功接口数据
	 * @param req
	 * @param message
	 * @param data
	 * @return
	 */
	protected Map<String,Object> dataSucc(HttpServletRequest req,int errorcode,String message,Object data)
	{
		Map<String,Object> result  = new HashMap<String,Object>();
		
		result.put("errorCode", 1);
		result.put("message",message);
		result.put("result",data);
		
		
		
		return result;
		
		
	}
	
	
	/**
	 * 成功接口数据
	 * @param req
	 * @param message
	 * @param data
	 * @return
	 */
	protected Map<String,Object> dataSucc()
	{
		Map<String,Object> result  = new HashMap<String,Object>();
		
		result.put("errorCode", 1);
		result.put("message","成功");
	
		
		
		
		return result;
		
		
	}
	
	
	/**
	 * 成功接口数据
	 * @param req
	 * @param message
	 * @param data
	 * @return
	 */
	protected Map<String,Object> dataFail(HttpServletRequest req,int errorCode,String message)
	{
		Map<String,Object> result  = new HashMap<String,Object>();
		
		result.put("errorCode", errorCode);
		result.put("message",message);
		
		
		
		return result;
		
		
	}
	
	/**
	 * 成功接口数据
	 * @param req
	 * @param message
	 * @param data
	 * @return
	 */
	protected Map<String,Object> dataFail()
	{
		Map<String,Object> result  = new HashMap<String,Object>();
		
		result.put("errorCode", -1);
		result.put("message","出错了");	
		
		
		return result;
		
		
	}
	
	
	
	
	
	
	

}
