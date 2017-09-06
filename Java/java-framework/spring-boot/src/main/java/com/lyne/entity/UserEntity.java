package com.lyne.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "users")
public class UserEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "Id")
	private Long id;
	@Column(name = "UserName")
	private String userName;

	@Column(name = "PassWord")
	private String passWord;

	@Column(name = "User_Sex")
	private String userSex;
	
	@Column(name = "Nick_Name")
	private String nickName;

	public UserEntity() {
		super();
	}

	public UserEntity(String userName, String passWord, String userSex) {
		super();
		this.passWord = passWord;
		this.userName = userName;
		this.userSex = userSex;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getUserSex() {
		return userSex;
	}

	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	@Override
	public String toString() {
		return String.format("UserEntity{id=%d, userName=%s, passWord=%s, userSex=%s, nickName=%s}", id, userName, passWord, userSex, nickName);
	}

}
