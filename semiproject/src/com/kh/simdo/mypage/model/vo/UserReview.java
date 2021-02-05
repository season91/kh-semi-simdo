package com.kh.simdo.mypage.model.vo;

import java.sql.Date;

/**
 * 
 * @Author : MinHee
 * @Date : 2021. 2. 3.
 * @work :
 */
public class UserReview {
	
	private int reviewNo;
	private int userNo;
	private double score;
	private Date rvRegDate;
	private String rvContent;
	private Date watchDate;
	private String mvNo;
	private String mvTitle;
	private String thumbnail;
	
	public int getReviewNo() {
		return reviewNo;
	}

	public void setReviewNo(int reviewNo) {
		this.reviewNo = reviewNo;
	}

	public int getUserNo() {
		return userNo;
	}
	
	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}
	
	public double getScore() {
		return score;
	}
	
	public void setScore(double score) {
		this.score = score;
	}
	
	public Date getRvRegDate() {
		return rvRegDate;
	}
	
	public void setRvRegDate(Date rvRegDate) {
		this.rvRegDate = rvRegDate;
	}
	
	public String getRvContent() {
		return rvContent;
	}
	
	public void setRvContent(String rvContent) {
		this.rvContent = rvContent;
	}
	
	public Date getWatchDate() {
		return watchDate;
	}
	
	public void setWatchDate(Date watchDate) {
		this.watchDate = watchDate;
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
		return "[reviewNo:" + reviewNo + ", userNo:" + userNo + ", score:" + score + ", rvRegDate:"
				+ rvRegDate + ", rvContent:" + rvContent + ", watchDate:" + watchDate + ", mvNo:" + mvNo + ", mvTitle:"
				+ mvTitle + ", thumbnail:" + thumbnail + "]";
	}
	
}
