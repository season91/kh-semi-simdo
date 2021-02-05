package com.kh.simdo.mypage.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.kh.simdo.common.code.ErrorCode;
import com.kh.simdo.common.exception.DataAccessException;
import com.kh.simdo.common.jdbc.JDBCTemplate;
import com.kh.simdo.movie.model.vo.Movie;
/**
 * 
 * @author 조아영
 *
 */
public class QnaListDao {
	JDBCTemplate jdt = JDBCTemplate.getInstance();
	
	// 페이징 범위 구하기
	public int[] selectPaging(Connection conn, int page) {
		PreparedStatement pstm = null;
		ResultSet rset = null;
		String sql = "select count(*) from MV_BASIC_INFO";
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
	
	// 주어진조건에 맞는 리스트 보기
	public List<Movie> selectQnaList(Connection conn, int page) {
		
		Movie movie = null;
		PreparedStatement pstm = null;
		ResultSet rset = null;
		List<Movie> res = new ArrayList<Movie>();
		String sql = "select * from (select rownum as num, MV_TITLE, RELEASE_DATE from(select * from MV_BASIC_INFO order by  RELEASE_DATE desc)) "
				+ "where num >= ? and num <= ?";
		System.out.println("조건찾는 다오");
		int pagePerList = 10;
		int startPage = (page - 1) * pagePerList + 1 ; // 시작
		int endPage =  startPage + pagePerList - 1 ; // 끝
		
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, startPage);
			pstm.setInt(2, endPage);
			rset = pstm.executeQuery();
			
			while(rset.next()) {
				movie = new Movie();
				movie.setScore(rset.getInt("num"));
				movie.setMvTitle(rset.getString("mv_title"));
				movie.setReleaseDate(rset.getDate("release_date"));
				
				res.add(movie);
			}
		} catch (SQLException e) {
			throw new DataAccessException(ErrorCode.AUTH01, e);
		} finally {
			
		}
		
		return res;
		
	}

}
