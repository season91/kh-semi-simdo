package com.kh.simdo.communication.model.vo;

import java.sql.Date;
import java.sql.Timestamp;

public class Communication {
	private int qstnNo;
	private int userNo;
	private String qstnType;
	private String qstnContent;
	private Date qstnRegDate;
	private String attachdFile;
	private String qstnTitle;
	private String userNm;
	private String qstnComent;
	public int getQstnNo() {
		return qstnNo;
	}
	public void setQstnNo(int qstnNo) {
		this.qstnNo = qstnNo;
	}
	public int getUserNo() {
		return userNo;
	}
	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}
	public String getQstnType() {
		return qstnType;
	}
	public void setQstnType(String qstnType) {
		this.qstnType = qstnType;
	}
	public String getQstnContent() {
		return qstnContent;
	}
	public void setQstnContent(String qstnContent) {
		this.qstnContent = qstnContent;
	}
	public Date getQstnRegDate() {
		return qstnRegDate;
	}
	public void setQstnRegDate(Date qstnRegDate) {
		this.qstnRegDate = qstnRegDate;
	}
	public String getAttachdFile() {
		return attachdFile;
	}
	public void setAttachdFile(String attachdFile) {
		this.attachdFile = attachdFile;
	}
	public String getQstnTitle() {
		return qstnTitle;
	}
	public void setQstnTitle(String qstnTitle) {
		this.qstnTitle = qstnTitle;
	}
	public String getUserNm() {
		return userNm;
	}
	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}
	public String getQstnComent() {
		return qstnComent;
	}
	public void setQstnComent(String qstnComent) {
		this.qstnComent = qstnComent;
	}
	@Override
	public String toString() {
		return "Comm [qstnNo=" + qstnNo + ", userNo=" + userNo + ", qstnType=" + qstnType + ", qstnContent="
				+ qstnContent + ", qstnRegDate=" + qstnRegDate + ", attachdFile=" + attachdFile + ", qstnTitle="
				+ qstnTitle + ", userNm=" + userNm + ", qstnComent=" + qstnComent + "]";
	}
   
}