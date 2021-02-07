package com.kh.simdo.comm.model.service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.kh.simdo.comm.model.dao.CommDao;
import com.kh.simdo.comm.model.vo.Comm;
import com.kh.simdo.common.exception.DataAccessException;
import com.kh.simdo.common.exception.ToAlertException;
import com.kh.simdo.common.jdbc.JDBCTemplate;
import com.kh.simdo.common.util.file.FileUtil;
import com.kh.simdo.common.util.file.FileVO;
import com.kh.simdo.movie.model.vo.Movie;

public class CommService {

	JDBCTemplate jdt = JDBCTemplate.getInstance();
	CommDao commDao = new CommDao();

	public void insertComm(String userId,HttpServletRequest request) {
		Connection conn = jdt.getConnection();
		//게시글 저장
		Map<String,List> commData = new FileUtil().fileUpload(request);
		
		Comm comm = new Comm();
		comm.setUserNm(userId);
		comm.setQstnTitle(commData.get("qstn_Title").get(0).toString());
		comm.setQstnContent(commData.get("qstn_Content").get(0).toString());
		
		try {
			commDao.insertComm(conn, comm);
			for(FileVO fileData : (List<FileVO>)commData.get("fileData")) {
				commDao.insertFile(conn, fileData);
			}
			jdt.commit(conn);
		}catch(DataAccessException e) {
			jdt.rollback(conn);
			throw new ToAlertException(e.error,e);
		}finally {
			jdt.close(conn);
		}
	}
	
	public Map<String,Object> selectBoardDetail(String bdIdx){
		
		Map<String,Object> commandMap = new HashMap<String, Object>();
		Connection conn = jdt.getConnection();
		
		try {
			//게시글 정보 가져오기
			Comm comm = commDao.selectCommView(conn, bdIdx);
			List<FileVO> fileList = commDao.selectFileWithBoard(conn, bdIdx);
			commandMap.put("comm", comm);
			commandMap.put("fileList", fileList);
		}finally {
			jdt.close(conn);
		}
		
		return commandMap;
	}

	/**
	 * @author 조아영
	 */
	
	// 처음에 출력해줄 페이징 개수 불러오기
		public int[] selectPagingList(int page) {
			Connection conn = jdt.getConnection();
			int[] res = new int[2];
			try {
				res = commDao.selectPaging(conn, page);
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
		
	// 주어진 페이지의 게시글 불러오기
	public List<Map<String, Object>> selectQnaList(int page, int userNo){
		Connection conn = jdt.getConnection();
		List<Map<String, Object>> res = new ArrayList<Map<String, Object>>();
		try {
			res = commDao.selectQnaList(conn, page, userNo);
		} finally {
			jdt.close(conn);
		}
		
		return res;
		
	}
		
	/**
	 * @author 조아영
	 */
		
		public Comm selectCommByQstnNo(int qstnNo) {
			Connection conn = jdt.getConnection();
			Comm res = null;
			try {
				res = commDao.selectCommByQstnNo(conn, qstnNo);
			} finally {
				jdt.close(conn);
			}
			
			return res;
		}
}