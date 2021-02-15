package com.kh.simdo.user.model.vo;

import java.sql.Date;

/**
 * 
 * @Author : MinHee
 * @Date : 2021. 2. 3.
 * @work :
 */
public class User {
	
	private int userNo;
	private String userEmail;
	private String userPw; //DB type수정필요
	private String userTel; //DB type수정필요
	private String userNm;
	private String userGender;
	private Date userBirth;
	private int isLeave;
	private String userProfile; //DB column_name 수정필요
	private String admin;

	public int getUserNo() {
		return userNo;
	}
	
	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}
	
	public String getAdmin() {
		return admin;
	}

	public void setAdmin(String admin) {
		this.admin = admin;
	}

	public String getUserEmail() {
		return userEmail;
	}
	
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	
	public String getUserPw() {
		return userPw;
	}
	
	public void setUserPw(String userPw) {
		this.userPw = userPw;
	}
	
	public String getUserTel() {
		return userTel;
	}
	
	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}
	
	public String getUserNm() {
		return userNm;
	}
	
	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}
	
	public String getUserGender() {
		return userGender;
	}
	
	public void setUserGender(String userGender) {
		this.userGender = userGender;
	}
	
	public Date getUserBirth() {
		return userBirth;
	}
	
	public void setUserBirth(Date userBirth) {
		this.userBirth = userBirth;
	}
	
	public int getIsLeave() {
		return isLeave;
	}
	
	public void setIsLeave(int isLeave) {
		this.isLeave = isLeave;
	}
	
	public String getUserProfile() {
		return userProfile;
	}
	
	public void setUserProfile(String userProfile) {
		this.userProfile = userProfile;
	}

	@Override
	public String toString() {
		return "User [userNo=" + userNo + ", userEmail=" + userEmail + ", userPw=" + userPw + ", userTel=" + userTel
				+ ", userNm=" + userNm + ", userGender=" + userGender + ", userBirth=" + userBirth + ", isLeave="
				+ isLeave + ", userProfile=" + userProfile + ", admin=" + admin + "]";
	}

	
	
}
