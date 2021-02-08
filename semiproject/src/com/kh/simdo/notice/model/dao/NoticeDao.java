package com.kh.simdo.notice.model.dao;

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
import com.kh.simdo.communication.model.vo.Communication;
import com.kh.simdo.notice.model.vo.Notice;

public class NoticeDao {
	JDBCTemplate jdt = JDBCTemplate.getInstance();
	// 페이징 범위 구하기
		public int[] selectPaging(Connection conn, int page) {
			PreparedStatement pstm = null;
			ResultSet rset = null;
			String sql = "select count(*) from NOTICE";
			int[] startEnd = new int[2];
			try {
				pstm = conn.prepareStatement(sql);
				rset = pstm.executeQuery();
				System.out.println("첫실행 다오");
				int totalContent = 0;
				int totalPage = 0;
				while(rset.next()) {
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
		
	// 공지사항 리스트보기
			public List<Notice> selectNoticeList(Connection conn, int page) {
				
				Notice notice = null;
				PreparedStatement pstm = null;
				ResultSet rset = null;
				List<Notice> res = new ArrayList<Notice>();
				String sql = "select"
						+"NOTICE_NO,NT_TITLE, REG_DATE, WRITER"
						+ " from NOTICE"
						+ "where NOTICE_NO =?";
					System.out.println("공지사항 리스트불러오기");	
				
				int pagePerList = 10;
				int startPage = (page - 1) * pagePerList + 1 ; // 시작
				int endPage =  startPage + pagePerList - 1 ; // 끝
				
				try {
					pstm = conn.prepareStatement(sql);
					pstm.setInt(1, startPage);
					pstm.setInt(2, endPage);
					rset = pstm.executeQuery();
					
					while(rset.next()) {
						
						notice = new Notice();
						notice.setWriter(rset.getString("WRITER"));
						notice.setNtTitle(rset.getString("NT_TITLE"));
						notice.setRegDate(rset.getDate("REG_DATE"));
						
						
						res.add(notice);
					}
				} catch (SQLException e) {
					throw new DataAccessException(ErrorCode.AUTH01, e);
				} finally {
					
				}
				
				return res;
			}
				
		//공지사항상세
		public Notice selectNoticeView(Connection conn, String bdIdx) {
			Notice notice = null;
			PreparedStatement pstm = null;
			ResultSet rset = null;
			String sql = "select "
					+ "NOTICE_NO, WRITER, REG_DATE, NT_TITLE, NT_CONTENT "
					+ "from NOTICE "
					+ "where NOTICE_NO = ?";
			try {
				pstm = conn.prepareStatement(sql);
				pstm.setString(1, bdIdx);
				rset = pstm.executeQuery();
				if(rset.next()) {
					notice = new Notice();
					notice.setNoticeNo(rset.getInt(1));
					notice.setWriter(rset.getString(2));
					notice.setRegDate(rset.getDate(3));
					notice.setNtTitle(rset.getString(4));
					notice.setNtContent(rset.getString(5));
				}
			} catch (SQLException e) {
				throw new DataAccessException(ErrorCode.SB01, e);
			}finally {
				jdt.close(rset, pstm);
			}
			return notice;
		}
}
