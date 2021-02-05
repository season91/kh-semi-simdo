package com.kh.simdo.mypage.model.vo;

public class UserFmsline {
	
	private int fmslineNo;
	private int userNo;
	private String fmlContent;
	private String mvNo;
	private String mvTitle;
	private String thumbnail;
	
	public int getFmslineNo() {
		return fmslineNo;
	}

	public void setFmslineNo(int fmslineNO) {
		this.fmslineNo = fmslineNO;
	}

	public int getUserNo() {
		return userNo;
	}
	
	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}
	
	public String getFmlContent() {
		return fmlContent;
	}
	
	public void setFmlContent(String fmlContent) {
		this.fmlContent = fmlContent;
	}
	
	public String getMvNo() {
		return mvNo;
	}
	
	public void setMvNo(String mvNo) {
		this.mvNo = mvNo;
	}
	
	public String getMvTitle() {
		return mvTitle;
	}
	
	public void setMvTitle(String mvTitle) {
		this.mvTitle = mvTitle;
	}
	
	public String getThumbnail() {
		return thumbnail;
	}
	
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	@Override
	public String toString() {
		return "[fmslineNo:" + fmslineNo + ", userNo:" + userNo + ", fmlContent:" + fmlContent + ", mvNo:"
				+ mvNo + ", mvTitle:" + mvTitle + ", thumbnail:" + thumbnail + "]";
	}
	
}
