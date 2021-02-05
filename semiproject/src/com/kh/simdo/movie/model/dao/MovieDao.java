package com.kh.simdo.movie.model.dao;

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
 * @Author : 조아영
   @Date : 2021. 2. 4.
   @work :
 */
public class MovieDao {
	
	JDBCTemplate jdt = JDBCTemplate.getInstance();
	
	
	//영화 상세조회
	public Movie selectDetail(Connection conn, String mvNo){
		Movie movie = null;
		PreparedStatement pstm = null;
		ResultSet rset = null;
		
		try {
			String sql = "select * from  mv_basic_info where mv_no = ?";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, mvNo);
			rset = pstm.executeQuery();
			if(rset.next()) {
				movie = new Movie();
				movie.setMvTitle(rset.getString("mv_title"));
				movie.setScore(rset.getInt("score"));
				movie.setDirector(rset.getString("director"));
				movie.setGenre(rset.getString("genre"));
				movie.setReleaseDate(rset.getDate("release_date"));
				movie.setNation(rset.getString("nation"));
				movie.setRuntime(rset.getInt("runtime"));
				movie.setPlot(rset.getString("plot"));
				movie.setRating(rset.getString("rating"));
				movie.setPoster(rset.getString("poster"));
			}
		} catch (SQLException e) {
			throw new DataAccessException(ErrorCode.SM01, e);
		} finally {
			jdt.close(rset, pstm);
		}
		return movie;
	}
	

	//장르별 조회
	public List<Movie> selectGenre(Connection conn, String genre){
		System.out.println("장르다오");
		List<Movie> res = new ArrayList<Movie>();
		Movie movie = null;
		PreparedStatement pstm = null;
		ResultSet rset = null;
		
		
		try {
			String sql ="select * from mv_basic_info where genre like '%"+genre+"%'";
			pstm = conn.prepareStatement(sql);
			rset = pstm.executeQuery();
			while(rset.next()) {
				movie = new Movie();
				movie.setMvNo(rset.getString("mv_no"));
				movie.setMvTitle(rset.getString("mv_title"));
				movie.setScore(rset.getInt("score"));
				movie.setDirector(rset.getString("director"));
				movie.setGenre(rset.getString("genre"));
				movie.setReleaseDate(rset.getDate("release_date"));
				movie.setNation(rset.getString("nation"));
				movie.setRuntime(rset.getInt("runtime"));
				movie.setPlot(rset.getString("plot"));
				movie.setRating(rset.getString("rating"));
				movie.setPoster(rset.getString("poster"));
				System.out.println(movie);
				res.add(movie);

			}
			System.out.println(res);
		} catch (SQLException e) {
			throw new DataAccessException(ErrorCode.SM01, e);
		} finally {
			jdt.close(rset, pstm);
		}
		
		
		return res;
	}
	//나라별 조회
	public List<Movie> selectNation(Connection conn, String nation){
		System.out.println("나라다오");
		List<Movie> res = new ArrayList<Movie>();
		Movie movie = null;
		PreparedStatement pstm = null;
		ResultSet rset = null;

		
		try {
			String sql = "select * from  mv_basic_info where nation like '%"+nation+"%'";
			pstm = conn.prepareStatement(sql);
			rset = pstm.executeQuery();
			while(rset.next()) {
				movie = new Movie();
				movie.setMvNo(rset.getString("mv_no"));
				movie.setMvTitle(rset.getString("mv_title"));
				movie.setScore(rset.getInt("score"));
				movie.setDirector(rset.getString("director"));
				movie.setGenre(rset.getString("genre"));
				movie.setReleaseDate(rset.getDate("release_date"));
				movie.setNation(rset.getString("nation"));
				movie.setRuntime(rset.getInt("runtime"));
				movie.setPlot(rset.getString("plot"));
				movie.setRating(rset.getString("rating"));
				movie.setPoster(rset.getString("poster"));
				res.add(movie);
			}
		} catch (SQLException e) {
			throw new DataAccessException(ErrorCode.SM01, e);
		} finally {
			jdt.close(rset, pstm);
		}
		return res;
	}
	//검색 조회
	public List<Movie> selectSearchMovie(Connection conn, String searchTitle) {
		
		List<Movie> res = new ArrayList<Movie>();
		Movie movie = null;
		PreparedStatement pstm = null;
		ResultSet rset = null;
		
		try {
			String sql = "select * from mv_basic_info where mv_no in "
					+ "(select mv_no from (select mv_no, replace(mv_title,' ','') as title from MV_BASIC_INFO) "
					+ "where title like '%"+searchTitle+"%')";
			pstm = conn.prepareStatement(sql);
			rset = pstm.executeQuery();
			
			while(rset.next()) {
				movie = new Movie();
				movie.setMvNo(rset.getString("mv_no"));
				movie.setMvTitle(rset.getString("mv_title"));
				movie.setScore(rset.getInt("score"));
				movie.setDirector(rset.getString("director"));
				movie.setGenre(rset.getString("genre"));
				movie.setReleaseDate(rset.getDate("release_date"));
				movie.setNation(rset.getString("nation"));
				movie.setRuntime(rset.getInt("runtime"));
				movie.setRating(rset.getString("rating"));
				movie.setPoster(rset.getString("poster"));
				res.add(movie);
			}
		} catch (SQLException e) {
			throw new DataAccessException(ErrorCode.SM01, e);
		} finally {
			jdt.close(rset, pstm);
		}
		System.out.println(res);
		return res;
	}

	
		
	public int insertMovieInfo(Connection conn, Movie movie) {
		//commit rollback 해야하는 기능
		
		int res = 0;
		PreparedStatement pstm = null;
		try {
			
			String query = "insert into mv_basic_info(mv_no, mv_title, mv_titleorg, director, genre, release_date, plot, nation, runtime, rating, thumbnail, poster) "+
					"values(?,?,?,?,?,?,?,?,?,?,?,?)";
			pstm = conn.prepareStatement(query);
			pstm.setString(1,movie.getMvNo());
			pstm.setString(2, movie.getMvTitle());
			pstm.setString(3, movie.getMvTitleorg());
			pstm.setString(4, movie.getDirector());
			pstm.setString(5, movie.getGenre());
			pstm.setDate(6, movie.getReleaseDate());
			pstm.setString(7, movie.getPlot());
			pstm.setString(8, movie.getNation());
			pstm.setInt(9, movie.getRuntime());
			pstm.setString(10, movie.getRating());
			pstm.setString(11, movie.getThumbnail());
			pstm.setString(12, movie.getPoster());
			res = pstm.executeUpdate(); 
			// 실행하면 실제로 db에 dml구문이 실행되며 row 한줄이 들어간다, 리턴값은 쿼리로인해 영향(추가변경삭제)받은 row의 수를 반환! 1줄추가되면 res가 1, 실패라면 0
			// res로 결과반영여부 판단
		} catch (SQLException e) {
			throw new DataAccessException(ErrorCode.IM01,e);
		} finally {
			jdt.close(pstm);
		}

		return res;
	}

}
