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

}