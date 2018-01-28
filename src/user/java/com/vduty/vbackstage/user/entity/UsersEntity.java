package com.vduty.vbackstage.user.entity;

import com.vduty.vbackstage.entity.UsersBaseEntity;


public class UsersEntity extends UsersBaseEntity  {

	public  UsersEntity(String name)
	{
		super(name);
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7591789588053668466L;

	public UsersEntity(String name,String password) {
		super(name, password);
		// TODO Auto-generated constructor stub
	}

	int type;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}
