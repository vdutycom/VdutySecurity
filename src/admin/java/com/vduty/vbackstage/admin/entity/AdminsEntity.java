package com.vduty.vbackstage.admin.entity;

import com.vduty.vbackstage.entity.UsersBaseEntity;

public class AdminsEntity extends UsersBaseEntity {

	public AdminsEntity()
	{
		
	}
		
	public AdminsEntity(String name, String password) {
		super(name, password);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 8878068271767756552L;
	String trueName;
	String nickName;

	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

}
