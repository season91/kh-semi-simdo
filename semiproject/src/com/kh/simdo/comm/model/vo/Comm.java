package com.kh.simdo.comm.model.vo;

import java.sql.Date;
import java.sql.Timestamp;

public class Comm {
	private int qstn_No;
	private int user_No;
	private String qstn_Type;
	private String qstn_Content;
	private Date qstn_Reg_Date;
	private String attachdFile;
	private String qstn_Title;
	private String user_Nm;
	private String qstn_Coment;
	public int getQstn_No() {
		return qstn_No;
	}
	public void setQstn_No(int qstn_No) {
		this.qstn_No = qstn_No;
	}
	public int getUser_No() {
		return user_No;
	}
	public void setUser_No(int user_No) {
		this.user_No = user_No;
	}
	public String getQstn_Type() {
		return qstn_Type;
	}
	public void setQstn_Type(String qstn_Type) {
		this.qstn_Type = qstn_Type;
	}
	public String getQstn_Content() {
		return qstn_Content;
	}
	public void setQstn_Content(String qstn_Content) {
		this.qstn_Content = qstn_Content;
	}
	public Date getQstn_Reg_Date() {
		return qstn_Reg_Date;
	}
	public void setQstn_Reg_Date(Date qstn_Reg_Date) {
		this.qstn_Reg_Date = qstn_Reg_Date;
	}
	public String getAttachdFile() {
		return attachdFile;
	}
	public void setAttachdFile(String attachdFile) {
		this.attachdFile = attachdFile;
	}
	public String getQstn_Title() {
		return qstn_Title;
	}
	public void setQstn_Title(String qstn_Title) {
		this.qstn_Title = qstn_Title;
	}
	public String getUser_Nm() {
		return user_Nm;
	}
	public void setUser_Nm(String user_Nm) {
		this.user_Nm = user_Nm;
	}
	public String getQstn_Coment() {
		return qstn_Coment;
	}
	public void setQstn_Coment(String qstn_Coment) {
		this.qstn_Coment = qstn_Coment;
	}
	@Override
	public String toString() {
		return "Comm [qstn_No=" + qstn_No + ", user_No=" + user_No + ", qstn_Type=" + qstn_Type + ", qstn_Content="
				+ qstn_Content + ", qstn_Reg_Date=" + qstn_Reg_Date + ", attachdFile=" + attachdFile + ", qstn_Title="
				+ qstn_Title + ", user_Nm=" + user_Nm + ", qstn_Coment=" + qstn_Coment + "]";
	}
   
	

}
	
	