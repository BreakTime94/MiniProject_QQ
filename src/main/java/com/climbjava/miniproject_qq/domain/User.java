package com.climbjava.miniproject_qq.domain;

public abstract class User {
	//-----------------필드
	private int userNo;		// 회원번호 * 회원가입시 자동증가
	private String name; 	// 회원 이름
	private String id;		// 회원 ID
	private String pw;		// 회원 PW

	//-----------------생성자
	public User() {
		// TODO Auto-generated constructor stub
	}

	public User(int userNo, String name, String id, String pw) {
		this.userNo = userNo;
		this.name = name;
		this.id = id;
		this.pw = pw;
	}

	//-----------------getter/setter
	public int getUserNo() {
		return userNo;
	}
	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}


	//-----------------투스트링 
	@Override
	public String toString() {
		return "[회원번호 : " + userNo + " ], [이름 : " + name + " ], [ID : " + id + " ], [PW : " + pw + " ]";
	}


}