package com.kh.simdo.communication.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kh.simdo.common.code.ErrorCode;
import com.kh.simdo.common.exception.DataAccessException;
import com.kh.simdo.common.jdbc.JDBCTemplate;
import com.kh.simdo.common.util.file.FileVO;
import com.kh.simdo.communication.model.vo.Communication;
import com.kh.simdo.movie.model.vo.Movie;
/**
 * 
 * @author 김백관
 *
 */
public class CommunicationDao {
	
	JDBCTemplate jdt = JDBCTemplate.getInstance();
	//게시판 테이블에 게시글 저장
	public int insertComm(Connection conn, Communication communication) {
		int res = 0;
		
		String sql = "insert into COMM (QSTN_NO, USER_NM,QSTN_TITLE, QSTN_Content,QSTN_TYPE) values(sc_qstn_No.nextval, ?,?, ?,?)";
		
		PreparedStatement pstm = null;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, communication.getUserNm());
			pstm.setString(2, communication.getQstnTitle());
			pstm.setString(3, communication.getQstnContent());
			pstm.setString(4, communication.getQstnType());
			res = pstm.executeUpdate();
		} catch (SQLException e) {
			throw new DataAccessException(ErrorCode.IB01, e);
		}finally {
			jdt.close(pstm);
		}
		
		return res;
	}
	
	
		public int insertFile(Connection conn, FileVO fileData) {
			int res = 0;
			String qstn_no = "";
			//1. 새로 등록되는 게시글의 파일 정보 저장
			//	typeIdx값이 시퀀스 currval
			if(fileData.getTypeIdx() == null) {
				qstn_no = "'b'||sc_qstn_no.currval";
			//2. 수정할 때 사용자가 파일을 추가 등록해서 파일 정보 저장
			//	수정할 게시글의 bdIdx값
			}else {
				qstn_no = "'" + fileData.getTypeIdx() + "'" ;
			}
			
			String sql = "insert into tb_file "
					+ "(f_idx,type_idx,origin_file_name,rename_file_name,save_path) "
					+ "values(sc_file_idx.nextval,"+qstn_no+",?,?,?)";
			
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
		public List<FileVO> selectFileWithBoard(Connection conn, String bdIdx) {
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
		
		
	/**
	 * @author 조아영
	 */
		// 페이징 범위 구하기
		public int[] selectPaging(Connection conn, int page) {
			PreparedStatement pstm = null;
			ResultSet rset = null;
			String sql = "select count(*) from comm";
			int[] startEnd = new int[2];
			try {
				pstm = conn.prepareStatement(sql);
				rset = pstm.executeQuery();
				System.out.println("첫실행 다오");
				int totalContent = 0;
				int totalPage = 0;
				
				if(rset.next()) {
					totalContent += rset.getInt(1);
				}
				
				if(totalContent == 0) {
					return null;
				}
				
				// 최종 전체 페이지 개수
				totalPage =  totalContent / 10;
				if(totalContent % 10 > 0) {
					//나머지가 있다면 1더해준다.
					totalPage++;
				}
				
				// 페이징 범위 계산
				// 시작 끝 페이지
				int startPage, endPage;
				startPage = ((page -1)/10)*10+1;
				endPage = startPage + 10 -1;
				if(endPage > totalPage) {
					endPage = totalPage;
				}
				
				// 시작과 끝 결과(숫자) 전달해줄 배열
				startEnd[0]=startPage;
				startEnd[1]=endPage;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return startEnd;
		}
		
		/**
		 * @author 조아영
		 */
		
		// 주어진조건에 맞는 리스트 보기
		public List<Map<String, Object>>  selectQnaList(Connection conn, int page, int userNo) {
			Communication communication = null;
			PreparedStatement pstm = null;
			ResultSet rset = null;
			List<Map<String, Object>> res = new ArrayList();
			String sql = "select * from (select rownum as num, QSTN_NO, QSTN_TITLE, QSTN_REG_DATE from"
					+ "(select * from comm c join \"USER\" u using(user_no) where user_no = ? order by  QSTN_REG_DATE desc))  "
					+ "where num >= ? and num <= ?";
			System.out.println("조건찾는 다오");
			int pagePerList = 10;
			int startPage = (page - 1) * pagePerList + 1 ; // 시작
			int endPage =  startPage + pagePerList - 1 ; // 끝
			
			try {
				pstm = conn.prepareStatement(sql);
				pstm.setInt(1, userNo);
				pstm.setInt(2, startPage);
				pstm.setInt(3, endPage);
				rset = pstm.executeQuery();
				
				while(rset.next()) {
					Map<String, Object> commandMap = new HashMap<String, Object>();
					communication = new Communication();
					String qnaNo = String.valueOf(rset.getInt("QSTN_NO"));
					
					communication.setQstnTitle(rset.getString("QSTN_TITLE"));
					communication.setQstnRegDate(rset.getDate("QSTN_REG_DATE"));
					
					commandMap.put("qnaNo", qnaNo);
					commandMap.put("comm", communication);
					res.add(commandMap);
					
				}
				
				
			} catch (SQLException e) {
				throw new DataAccessException(ErrorCode.AUTH01, e);
			} finally {
				
			}
			
			return res;
			
		}
		
		/**
		 * @author 조아영
		 */
		
		public Communication selectCommByQstnNo(Connection conn, int qstnNo) {
			Communication communication = null;
			PreparedStatement pstm = null;
			ResultSet rset = null;
			String sql = "select * "
					+ "from comm where qstn_no = ?";
			try {
				pstm = conn.prepareStatement(sql);
				pstm.setInt(1, qstnNo);
				rset = pstm.executeQuery();
				if(rset.next()) {
					communication = new Communication();
					communication.setQstnNo(rset.getInt("qstn_no"));
					communication.setUserNm(rset.getString("user_nm"));
					communication.setQstnRegDate(rset.getDate("qstn_reg_date"));
					communication.setQstnTitle(rset.getString("qstn_title"));
					communication.setQstnContent(rset.getString("qstn_content"));
					communication.setQstnComent(rset.getString("qstn_coment"));
				}
			} catch (SQLException e) {
				throw new DataAccessException(ErrorCode.SB01, e);
			}finally {
				jdt.close(rset, pstm);
			}
			return communication;
		}
}
		


