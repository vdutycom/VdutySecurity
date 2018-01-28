package com.vduty.vbackstage.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.vduty.security.VsInterceptor;
import com.vduty.vbackstage.configs.Constants;
import com.vduty.vbackstage.utils.HttpUtils;
import com.vduty.vbackstage.utils.RegexUtil;

import io.jsonwebtoken.Jwts;

@Component
// @ServletComponentScan
@WebFilter(urlPatterns = { Constants.ADMIN_PATH + "/*", Constants.USERCENTER_PATH + "/*" })
public class MyFilterSecurityInterceptor extends VsInterceptor implements Filter {
	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	StringRedisTemplate stringRedisTemplate;

	@Override
	public void destroy() {
		log.info("" + getClass() + " destroy");

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse rep, FilterChain chain)
			throws IOException, ServletException {

		log.info("" + getClass() + " doFilter : url:");

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse res = (HttpServletResponse) rep;
		log.info("doFilter:" + HttpUtils.getCurUrl(request));
		
		String curUrl = HttpUtils.getCurUrl(request);

		curUrl = curUrl.replace(HttpUtils.getHost(request), "");
		log.info("" + getClass() + " doFilter : url:" + curUrl);

		String token = request.getHeader(Constants.JWT_HEADER_NAME);
		Cookie[] cookies = request.getCookies();
		if (RegexUtil.isNull(token) && RegexUtil.isNotNull(cookies))
		{
			 for (Cookie c : cookies) {
		            if (c.getName().equals(Constants.JWT_HEADER_NAME))
		            {
		               token = c.getValue();
		               log.info(Constants.JWT_HEADER_NAME+" cookie:"+c.getValue());
		               break;
		            }
		        }
		}
		if (RegexUtil.isNotNull (token)) {
			// parse the token.
			String username = Jwts.parser().setSigningKey(Constants.JWT_SECURITY_KEY)
					.parseClaimsJws(token.replace(Constants.JWT_TOKEN_FLAG, "")).getBody().getSubject();

			System.out.print("filetr check username : " + username);
			
			if (RegexUtil.isNotNull(username)) {

				// 登录判断
				if (RegexUtil.isNull( stringRedisTemplate.opsForValue().get(username))) {

					/* 保存要登录后访问的地址 */
					stringRedisTemplate.opsForValue().set(Constants.REFER_URL_KEY + username, curUrl); 

					if (curUrl.startsWith(Constants.ADMIN_PATH)) {

						res.sendRedirect("/adminlogin/");
						return;
					}
					if (curUrl.startsWith(Constants.USERCENTER_PATH)) {

						res.sendRedirect("/login/");
						return;
					}

				} else// 判断权限
				{

					
					
					chain.doFilter(req, rep);

				}

			} else {

			}
			return;
		} else {
			// 未登录；

			res.getWriter().print("{\"message\":\"---未登录\"}");
			res.setHeader("Content-type", "application/json;utf-8 +");
			res.flushBuffer();

			return;

		}

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		log.info("" + getClass() + " init");

	}

}