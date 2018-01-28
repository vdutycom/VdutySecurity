package com.vduty.vbackstage.entity;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vduty.security.VsAuthorities;
import com.vduty.security.VsUserDetails;
import com.vduty.vbackstage.admin.entity.AuthoritiesEntity;


/**
 * 用户基础类
 * @author Administrator
 * 用户名、密码、类型、权限集合
 */
public class UsersBaseEntity extends  VsUserDetails  {
	
	public  UsersBaseEntity()
	{
		
	}
	
	public  UsersBaseEntity(String name)
	{
		super.setName(name);
	}
	
	public UsersBaseEntity(String name,String password)
	{
		super(name,password);
	}	

	Logger logger = LogManager.getLogger(UsersBaseEntity.class);
	
	/**
	 * 用户的登录类型，普通用户/管理用户
	 */
	int loginType =0 ;//默認普通用戶
	
	
	public int getLoginType() {
		return loginType;
	}

	public void setLoginType(int loginType) {
		this.loginType = loginType;
	}

	private static final long serialVersionUID = 1L;

	long id;

	/** 获取用户id **/
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	 
	  
	

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getLoginTimes() {
		return loginTimes;
	}

	public void setLoginTimes(int loginTimes) {
		this.loginTimes = loginTimes;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPortrait() {
		return portrait;
	}

	public void setPortrait(String portrait) {
		this.portrait = portrait;
	}


	

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

	int sex;
	
	Date createTime;
	Date lastLoginTime;
	String lastLoginIp;
	int status;
	int loginTimes;
	String email;
	String phone;
	String portrait;
	

	
	List<AuthoritiesEntity> permissions;
	

	public List<AuthoritiesEntity> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<AuthoritiesEntity> permissions) {
		this.permissions = permissions;
	}

	
	
	

	@Override
	public Map<String, VsAuthorities> getAuthorities() {
		// TODO Auto-generated method stub

	
		HashMap<String,VsAuthorities > hmAuths = new HashMap<>();
		
		
		List<AuthoritiesEntity> permissions = this.getPermissions();
		try {
		for (AuthoritiesEntity permission : permissions) {	
			if (permission==null)
				break;
			
			hmAuths.put(permission.getName(),permission);
		}
		}
		catch(Exception ex)
		{
			logger.error(ex.getMessage());
		}		
	    
		return  hmAuths;

	}
	

	
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
