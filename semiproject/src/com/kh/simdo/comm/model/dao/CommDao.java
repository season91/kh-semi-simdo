package com.kh.simdo.comm.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.kh.simdo.comm.model.vo.Comm;
import com.kh.simdo.common.code.ErrorCode;
import com.kh.simdo.common.exception.DataAccessException;
import com.kh.simdo.common.jdbc.JDBCTemplate;
import com.kh.simdo.common.util.file.FileVO;

public class CommDao {
	
	JDBCTemplate jdt = JDBCTemplate.getInstance();
	//게시판 테이블에 게시글 저장
	public int insertBoard(Connection conn, Comm comm) {
		int res = 0;
		String sql = "insert into Comm "
				+ "(bd_idx,user_id,title,content) "
				+ "values('b' || sc_board_idx.nextval, ? , ?, ?)";
		
		PreparedStatement pstm = null;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, comm.getUser_nm());
			pstm.setString(2, comm.getQstn_title());
			pstm.setString(3, comm.getQstn_content());
			res = pstm.executeUpdate();
		} catch (SQLException e) {
			throw new DataAccessException(ErrorCode.IB01, e);
		}finally {
			jdt.close(pstm);
		}
		
		return res;
	
}
	//파일테이블에 파일 정보 저장
		public int insertFile(Connection conn, FileVO fileData) {
			int res = 0;
			String bdIdx = "";
			//1. 새로 등록되는 게시글의 파일 정보 저장
			//	typeIdx값이 시퀀스 currval
			if(fileData.getTypeIdx() == null) {
				bdIdx = "'b'||sc_board_idx.currval";
			//2. 수정할 때 사용자가 파일을 추가 등록해서 파일 정보 저장
			//	수정할 게시글의 bdIdx값
			}else {
				bdIdx = "'" + fileData.getTypeIdx() + "'" ;
			}
			
			String sql = "insert into tb_file "
					+ "(f_idx,type_idx,origin_file_name,rename_file_name,save_path) "
					+ "values(sc_file_idx.nextval,"+bdIdx+",?,?,?)";
			
			PreparedStatement pstm = null;
			try {
				pstm = conn.prepareStatement(sql);
				pstm.setString(1, fileData.getOriginFileName());
				pstm.setString(2, fileData.getRenameFileName());
				pstm.setString(3, fileData.getSavePath());
				res = pstm.executeUpdate();
			} catch (SQLException e) {
				throw new DataAccessException(ErrorCode.IF01, e);
			}finally {
				jdt.close(pstm);
			}
			
			return res;
		}
	
		//게시글 상세
		public Comm selectBoardDetail(Connection conn, String bdIdx) {
			Comm comm = null;
			PreparedStatement pstm = null;
			ResultSet rset = null;
			String sql = "select "
					+ "bd_idx, user_nm, qstn_reg_date, qstn_title, qstn_content "
					+ "from tb_board "
					+ "where bd_idx = ?";
			try {
				pstm = conn.prepareStatement(sql);
				pstm.setString(1, bdIdx);
				rset = pstm.executeQuery();
				if(rset.next()) {
					comm = new Comm();
					comm.setBdIdx(rset.getString(1));
					comm.setUserId(rset.getString(2));
					comm.setRegDate(rset.getDate(3));
					comm.setTitle(rset.getString(4));
					comm.setContent(rset.getString(5));
				}
			} catch (SQLException e) {
				throw new DataAccessException(ErrorCode.SB01, e);
			}finally {
				jdt.close(rset, pstm);
			}
			return board;
		}
		
		public List<FileVO> selectFileWithBoard(Connection conn, String bdIdx){
			 List<FileVO> res = null;
			 PreparedStatement pstm = null;
			 ResultSet rset = null;
			 String sql = "select f_idx,type_idx,origin_file_name,rename_file_name,"
			 		+ "save_path, reg_date, is_del "
			 		+ "from tb_file "
			 		+ "where type_idx = ?";
			
			 res = new ArrayList<FileVO>();
			 try {
				pstm = conn.prepareStatement(sql);
				pstm.setString(1, bdIdx);
				rset = pstm.executeQuery();
				
				while(rset.next()) {
					FileVO fileVO = new FileVO();
					fileVO.setfIdx(rset.getInt(1));
					fileVO.setTypeIdx(rset.getString(2));
					fileVO.setOriginFileName(rset.getString(3));
					fileVO.setRenameFileName(rset.getString(4));
					fileVO.setSavePath(rset.getString(5));
					fileVO.setRegDate(rset.getDate(6));
					fileVO.setIsDel(rset.getInt(7));
					res.add(fileVO);
				}
			} catch (SQLException e) {
				throw new DataAccessException(ErrorCode.SF01, e);
			}
			 return res;
		}
		
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
