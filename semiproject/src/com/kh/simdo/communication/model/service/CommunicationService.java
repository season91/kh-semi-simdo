package com.kh.simdo.communication.model.service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.kh.simdo.common.exception.DataAccessException;
import com.kh.simdo.common.exception.ToAlertException;
import com.kh.simdo.common.jdbc.JDBCTemplate;
import com.kh.simdo.communication.model.dao.CommunicationDao;
import com.kh.simdo.communication.model.vo.Communication;
import com.kh.simdo.communication.model.vo.Notice;

public class CommunicationService {

	JDBCTemplate jdt = JDBCTemplate.getInstance();
	CommunicationDao communicationDao = new CommunicationDao();

	public int insertComm(Communication communication) {
		int req = 0;
		Connection conn = jdt.getConnection();
		//게시글 저장
		try {
			req=communicationDao.insertComm(conn, communication);
			jdt.commit(conn);
			
		}catch(DataAccessException e) {
			jdt.rollback(conn);
			throw new ToAlertException(e.error,e);
		}finally {
			jdt.close(conn);
		}
		return req;
	}
	

	/**
	 * @author 조아영
	 */
	
	// 문의사항 처음에 출력해줄 페이징 개수 불러오기
		public int[] selectPagingByQna(int page) {
			Connection conn = jdt.getConnection();
			int[] res = new int[2];
			try {
				res = communicationDao.selectPagingByQna(conn, page);
			} finally {
				jdt.close(conn);
			}
			
			return res;
		}
		

	/**
	 * @author 조아영
	 */
	
	// 공지사항 처음에 출력해줄 페이징 개수 불러오기
		public int[] selectPagingByNotice(int page) {
			Connection conn = jdt.getConnection();
			int[] res = new int[2];
			try {
				res = communicationDao.selectPagingByNotice(conn, page);
			} finally {
				jdt.close(conn);
			}
			
			return res;
		}
		
	/**
	 * 
	 * @Author : 조아영
	   @Date : 2021. 2. 7.
	   @param page
	   @param userNo
	   @return
	   @work :
	 */
		
	// 문의사항 주어진 페이지의 게시글 불러오기
	public List<Map<String, Object>> selectQnaList(int page, int userNo){
		Connection conn = jdt.getConnection();
		List<Map<String, Object>> res = new ArrayList<Map<String, Object>>();
		try {
			res = communicationDao.selectQnaList(conn, page, userNo);
		} finally {
			jdt.close(conn);
		}
		
		return res;
		
	}
	/**
	 * 
	 * @Author : 조아영
	   @Date : 2021. 2. 8.
	   @param page
	   @param userNo
	   @return
	   @work :
	 */
	
	// 공지사항 주어진 페이지의 게시글 불러오기
		public List<Map<String, Object>> selectNoticeList(int page){
			Connection conn = jdt.getConnection();
			List<Map<String, Object>> res = new ArrayList<Map<String, Object>>();
			try {
				res = communicationDao.selectNoticeList(conn, page);
			} finally {
				jdt.close(conn);
			}
			
			return res;
			
		}
		
	/**
	 * @author 조아영
	 * 문의사항 상세내용
	 */
	
	public Communication selectQnaByQstnNo(int qstnNo) {
		Connection conn = jdt.getConnection();
		Communication res = null;
		try {
			res = communicationDao.selectQnaByQstnNo(conn, qstnNo);
		} finally {
			jdt.close(conn);
		}
		
		return res;
	}
	
	
	/**
	 * @author 조아영
	 * 공지사항 상세내용
	 */
	public Notice selectNoticeByNoticeNo(int noticeNo) {
		Connection conn = jdt.getConnection();
		Notice res = null;
		try {
			res = communicationDao.selectNoticeByNoticeNo(conn, noticeNo);
		} finally {
			jdt.close(conn);
		}
		
		return res;
	}
		
			
}