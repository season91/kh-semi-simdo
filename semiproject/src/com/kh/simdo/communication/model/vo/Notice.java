package com.kh.simdo.communication.model.vo;

import java.sql.Date;

public class Notice {

		private int noticeNo;
		private String ntTitle;
		private String ntContent;
		private Date regDate;
		private String writer;
		public int getNoticeNo() {
			return noticeNo;
		}
		public void setNoticeNo(int noticeNo) {
			this.noticeNo = noticeNo;
		}
		public String getNtTitle() {
			return ntTitle;
		}
		public void setNtTitle(String ntTitle) {
			this.ntTitle = ntTitle;
		}
		public String getNtContent() {
			return ntContent;
		}
		public void setNtContent(String ntContent) {
			this.ntContent = ntContent;
		}
		public Date getRegDate() {
			return regDate;
		}
		public void setRegDate(Date regDate) {
			this.regDate = regDate;
		}
		public String getWriter() {
			return writer;
		}
		public void setWriter(String writer) {
			this.writer = writer;
		}
		@Override
		public String toString() {
			return "Notice [noticeNo=" + noticeNo + ", ntTitle=" + ntTitle + ", ntContent=" + ntContent + ", regDate="
					+ regDate + ", writer=" + writer + "]";
		}
		
}
