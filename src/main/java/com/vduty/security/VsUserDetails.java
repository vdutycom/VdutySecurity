package com.vduty.security;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;

/**
 * 用户信息
 * 
 * 
 * 
 * 
 * @author Administrator
 *
 */
public class VsUserDetails implements Serializable  {
	
	
	public VsUserDetails()
	{
		
	}
	
	public VsUserDetails(String name)
	{
		this.name = name;
		
		}
	
    public VsUserDetails(String name,String password)
    {
    	this.name= name;
    	this.password = password;
    	
    }

	/**
	 * 
	 */
	private static final long serialVersionUID = -6250606928677536552L;


	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id; 
	}

	public Map<String, VsAuthorities> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Map<String, VsAuthorities> authorities) {
		this.authorities = authorities;
	}

	private String name;
	private String password;
	
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	private long id;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private Map<String,VsAuthorities> authorities = new HashMap<String,VsAuthorities>();
	 
	
	/**
	 * 判断权限
	 * @param auth
	 * @return
	 */
	public boolean HasAuthority(String auth)
	{
		if (null!= this.authorities.get(auth))
		{
			return true;
		}
		
		return false;
	}


	
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	public String getUsername() {
		// TODO Auto-generated method stub
		return this.name;
	}

	

	
	
	
}
