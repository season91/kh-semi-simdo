package com.kh.simdo.comm.model.vo;

import java.sql.Date;
import java.sql.Timestamp;

public class Comm {
	private int qstn_no;
	private int user_no;
	private String qstn_type;
	private String qstn_content;
	private Date qstn_reg_date;
	private String attachdfile;
	private String qstn_title;
	private String user_nm;
	private String qstn_coment;
	
	public int getQstn_no() {
		return qstn_no;
	}
	public void setQstn_no(int qstn_no) {
		this.qstn_no = qstn_no;
	}
	public int getUser_no() {
		return user_no;
	}
	public void setUser_no(int user_no) {
		this.user_no = user_no;
	}
	public String getQstn_type() {
		return qstn_type;
	}
	public void setQstn_type(String qstn_type) {
		this.qstn_type = qstn_type;
	}
	public String getQstn_content() {
		return qstn_content;
	}
	public void setQstn_content(String qstn_content) {
		this.qstn_content = qstn_content;
	}
	public Date getQstn_reg_date() {
		return qstn_reg_date;
	}
	public void setQstn_reg_date(Date qstn_reg_date) {
		this.qstn_reg_date = qstn_reg_date;
	}
	public String getAttachdfile() {
		return attachdfile;
	}
	public void setAttachdfile(String attachdfile) {
		this.attachdfile = attachdfile;
	}
	public String getQstn_title() {
		return qstn_title;
	}
	public void setQstn_title(String qstn_title) {
		this.qstn_title = qstn_title;
	}
	public String getUser_nm() {
		return user_nm;
	}
	public void setUser_nm(String user_nm) {
		this.user_nm = user_nm;
	}
	public String getQstn_coment() {
		return qstn_coment;
	}
	public void setQstn_coment(String qstn_coment) {
		this.qstn_coment = qstn_coment;
	}
	@Override
	public String toString() {
		return "Comm [qstn_no=" + qstn_no + ", user_no=" + user_no + ", qstn_type=" + qstn_type + ", qstn_content="
				+ qstn_content + ", qstn_reg_date=" + qstn_reg_date + ", attachdfile=" + attachdfile + ", qstn_title="
				+ qstn_title + ", user_nm=" + user_nm + ", qstn_coment=" + qstn_coment + "]";
	}
	
	

}

	