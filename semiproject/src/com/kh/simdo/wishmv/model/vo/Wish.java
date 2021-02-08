package com.kh.simdo.wishmv.model.vo;

import java.sql.Date;

public class Wish {

	private String wishNo; //시퀀스번호
	private Number userNo; //유저번호
	private Date wishRegDate; //날짜 
	private String mvNo; //영화번호
	private String poster; //포스터 
	public String getWishNo() {
		return wishNo;
	}
	public void setWishNo(String wishNo) {
		this.wishNo = wishNo;
	}
	public Number getUserNo() {
		return userNo;
	}
	public void setUserNo(Number userNo) {
		this.userNo = userNo;
	}
	public Date getWishRegDate() {
		return wishRegDate;
	}
	public void setWishRegDate(Date wishRegDate) {
		this.wishRegDate = wishRegDate;
	}
	public String getMvNo() {
		return mvNo;
	}
	public void setMvNo(String mvNo) {
		this.mvNo = mvNo;
	}
	public String getPoster() {
		return poster;
	}
	public void setPoster(String poster) {
		this.poster = poster;
	}
	@Override
	public String toString() {
		return "Wish [wishNo=" + wishNo + ", userNo=" + userNo + ", wishRegDate=" + wishRegDate + ", mvNo=" + mvNo
				+ ", poster=" + poster + "]";
	}
	
}
