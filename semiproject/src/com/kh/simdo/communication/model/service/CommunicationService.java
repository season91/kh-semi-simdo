package com.kh.simdo.communication.model.service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;
import com.kh.simdo.common.exception.DataAccessException;
import com.kh.simdo.common.exception.ToAlertException;
import com.kh.simdo.common.jdbc.JDBCTemplate;
import com.kh.simdo.common.util.file.FileUtil;
import com.kh.simdo.common.util.file.FileVO;
import com.kh.simdo.communication.model.dao.CommunicationDao;
import com.kh.simdo.communication.model.vo.Communication;
import com.kh.simdo.communication.model.vo.Notice;

public class CommunicationService {

	JDBCTemplate jdt = JDBCTemplate.getInstance();
	CommunicationDao communicationDao = new CommunicationDao();

	
	/**
	 * @author 김백관
	 * 게시글 정보/파일 업로드
	 */
	
	public <Int> void insertComm(String userNm, Int userNo,HttpServletRequest request) {
		Connection conn = jdt.getConnection();
		//게시글 저장
		System.out.println("서비스안도니??");
		// 문의  파일업로드내용  이부분이 업로드가 안되고있다.
		Map<String,List> commData = new FileUtil().fileUpload(request);
		System.out.println("util실행후 ");
		Communication communication = new Communication();
		
		System.out.println("vo넣은거 시작");
		communication.setUserNm(userNm);
		communication.setUserNo((int) userNo);
		
		// 문의 텍스트내용이고 
		communication.setQstnTitle(commData.get("qstntitle").get(0).toString());
		communication.setQstnContent(commData.get("qstncontent").get(0).toString());
		communication.setQstnType(commData.get("qstntype").get(0).toString());
		
		System.out.println("vo넣은거끝");
		
		try {
			communicationDao.insertComm(conn, communication);
			for(FileVO fileData : (List<FileVO>)commData.get("fileData")) {
				System.out.println("파일 넣는중  ");
				communicationDao.insertFile(conn, fileData);
			}

			jdt.commit(conn);
			
		}catch(DataAccessException e) {
			jdt.rollback(conn);
			throw new ToAlertException(e.error);
		}finally {
			jdt.close(conn);
		}
		
	}
	

	/**
	 * 
	 * @Author : 조아영
	   @Date : 2021. 2. 8.
	   @param res
	   @return
	   @work : jsp로 보내기 위해 list->json 파싱
	 */
	 public List parseJson(List res) {
			List list = new ArrayList();
			Map<String, Object> commandMap = new HashMap<String, Object>();
			for (int i = 0; i < res.size(); i++) {
				String json = new Gson().toJson(res.get(i));
				//System.out.println("전"+json);
			commandMap = new Gson().fromJson(json, Map.class);
			//System.out.println("후"+commandMap.get("qstnNo"));
				list.add(commandMap);
			}
			return list;
	}
	 
	 

	/**
	 * @author 조아영
	 */
	
	// 유저용 문의사항 처음에 출력해줄 페이징 개수 불러오기
		public int[] selectPagingByQna(int page, int userNo) {
			Connection conn = jdt.getConnection();
			int[] res = new int[2];
			try {
				res = communicationDao.selectPagingByQna(conn, page, userNo);
			} finally {
				jdt.close(conn);
			}
			
			return res;
		}
		
	/**
	 * @author 조아영
	 */
	
	// 관리자용 문의사항 처음에 출력해줄 페이징 개수 불러오기
		public int[] selectPagingByAllQna(int page) {
			Connection conn = jdt.getConnection();
			int[] res = new int[2];
			try {
				res = communicationDao.selectPagingByAllQna(conn, page);
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
	
	// 관리자용 n페이지의 모든 유저 문의사항 글갖고 오기
	public List<Map<String, Object>> selectAllQnaList(int page){
		Connection conn = jdt.getConnection();
		List<Map<String, Object>> res = new ArrayList<Map<String, Object>>();
		try {
			res = communicationDao.selectAllQnaList(conn, page);
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
	

	// 유저용 문의사항 수정
	public int updateQna(int qstnNo, String qnaTitle, String qnaContent) {
		Connection conn = jdt.getConnection();
		int res = 0;
		try {
			res = communicationDao.updateQna(conn, qstnNo, qnaTitle, qnaContent);
			jdt.commit(conn);
		} catch (DataAccessException e) {
			jdt.rollback(conn);
			throw new ToAlertException(e.error);
		} finally {
			jdt.close(conn);
		}
		
		return res;
		
	}
	
	public int deleteQna(int qstnNo) {
		Connection conn = jdt.getConnection();
		int res = 0;
		try {
			res = communicationDao.deleteQna(conn, qstnNo);
			jdt.commit(conn);
		} catch (DataAccessException e) {
			jdt.rollback(conn);
			throw new ToAlertException(e.error);
		} finally {
			jdt.close(conn);
		}
		
		return res;
	}
	
	// 문의사항 답변추가
	public int updateQnaComent(String coment, int qstnNo) {
		Connection conn = jdt.getConnection();
		int res = 0;
		try {
			res = communicationDao.updateQnaComent(conn, qstnNo, coment);
			jdt.commit(conn);
		} catch (DataAccessException e) {
			jdt.rollback(conn);
			throw new ToAlertException(e.error);
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
	
	/**
	 * @author MinHee
	 */
	public int insertNotice(Notice notice) {
		Connection conn = jdt.getConnection();
		int res = 0;
		
		try {
			res = communicationDao.insertNotice(conn, notice);
			jdt.commit(conn);
		} catch (DataAccessException e) {
			jdt.rollback(conn);
			throw new ToAlertException(e.error);
		} finally {
			jdt.close(conn);
		}
		
		return res;
	}
	
	/**
	 * @author MinHee
	 */
	public int updateNotice(Notice notice) {
		Connection conn = jdt.getConnection();
		int res = 0;
		
		try {
			res = communicationDao.updateNotice(conn, notice);
			jdt.commit(conn);
		} catch (DataAccessException e) {
			jdt.rollback(conn);
			throw new ToAlertException(e.error);
		} finally {
			jdt.close(conn);
		}
		
		return res;
	}
	
	/**
	 * @author MinHee
	 */
	public int deleteNotice(int noticeNo) {
		Connection conn = jdt.getConnection();
		int res = 0;
		
		try {
			res = communicationDao.deleteNotice(conn, noticeNo);
			jdt.commit(conn);
		} catch (DataAccessException e) {
			jdt.rollback(conn);
			throw new ToAlertException(e.error);
		} finally {
			jdt.close(conn);
		}
		
		return res;
	}
		
}