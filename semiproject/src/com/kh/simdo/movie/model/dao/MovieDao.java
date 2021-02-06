package com.kh.simdo.movie.model.dao;

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
import com.kh.simdo.movie.model.vo.Movie;
import com.kh.simdo.mypage.model.vo.UserFmsline;
import com.kh.simdo.mypage.model.vo.UserReview;
/**
 * 
 * @Author : 조아영
   @Date : 2021. 2. 4.
   @work :
 */
public class MovieDao {
	
	JDBCTemplate jdt = JDBCTemplate.getInstance();

	
	/**
	 * 
	 * @Author :
	   @Date : 2021. 2. 5.
	   @param conn
	   @param mvNo
	   @return 유저닉네임과 명대사객체, 제네릭 미설정이유는 객체 타입이 2개이기 떄문임. (UserFmsline, String)
	   @work :영화상세내용에 보여줄 명대사 리스트
	 */
	
	public List selectFmslineByMvNo(Connection conn, String mvNo){
		UserFmsline userFmsline = null;
		List reviewList = new ArrayList();
		
		PreparedStatement pstm = null;
		ResultSet rset = null;
		
		try {
			
			String sql = "select u.fmsline_no, u.user_no, u.fml_content, u.mv_no, u.mv_title, u.thumbnail, us.user_nm from user_fmsline u "
					+"inner join \"USER\" us on(u.user_no = us.user_no) where u.mv_no = ? and us.is_leave = 0";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, mvNo);
			rset = pstm.executeQuery();
			
			while(rset.next()) {

				userFmsline = new UserFmsline();
				userFmsline.setFmslineNo(rset.getInt("fmsline_no"));
				userFmsline.setUserNo(rset.getInt("user_no"));
				userFmsline.setFmlContent(rset.getString("fml_content"));
				userFmsline.setMvNo(rset.getString("mv_no"));
				userFmsline.setMvTitle(rset.getString("mv_title"));
				userFmsline.setThumbnail(rset.getString("thumbnail"));
				Map<String, Object> commandMap = new HashMap<String, Object>();
				commandMap.put("fmsline", userFmsline);
				commandMap.put("nick", rset.getString("user_nm"));
				
				reviewList.add(commandMap);
				
			}
		} catch (SQLException e) {
			throw new DataAccessException(ErrorCode.SU01, e);
		} finally {
			jdt.close(rset, pstm);
		}
		System.out.println(reviewList);
		return reviewList;
	}
	
	
	/**
	 * 
	 * @Author : 조아영
	   @Date : 2021. 2. 5.
	   @param conn
	   @param mvNo
	   @return 유저닉네임과 리뷰객체, 제네릭 미설정이유는 객체 타입이 2개이기 떄문임. (UserReview, String)
	   @work :영화상세내용에 보여줄 리뷰 리스트
	 */
	public List selectReviewByMvNo(Connection conn, String mvNo){
		UserReview userReview = null;
		List reviewList = new ArrayList();
		
		PreparedStatement pstm = null;
		ResultSet rset = null;
		
		try {
			
			String sql = "select u.review_no, u.user_no, u.score, u.rv_reg_date, u.rv_content, u.watch_date, u.mv_no, u.mv_title, u.thumbnail, us.user_nm from user_review u "
					+"inner join \"USER\" us on(u.user_no = us.user_no) where u.mv_no = ? and us.is_leave = 0 order by u.rv_reg_date desc";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, mvNo);
			rset = pstm.executeQuery();
			
			while(rset.next()) {
				userReview = new UserReview();
				Map<String, Object> commandMap = new HashMap<String, Object>();
				
				userReview.setReviewNo(rset.getInt("review_no"));
				userReview.setUserNo(rset.getInt("user_no"));
				userReview.setScore(rset.getDouble("score"));
				userReview.setRvRegDate(rset.getDate("rv_reg_date"));
				userReview.setRvContent(rset.getString("rv_content"));
				userReview.setWatchDate(rset.getDate("watch_date"));
				userReview.setMvNo(rset.getString("mv_no"));
				userReview.setMvTitle(rset.getString("mv_title"));
				userReview.setThumbnail(rset.getString("thumbnail"));
				commandMap.put("review", userReview);
				commandMap.put("nick", rset.getString("user_nm"));
				
				reviewList.add(commandMap);
				
			}
		} catch (SQLException e) {
			throw new DataAccessException(ErrorCode.SU01, e);
		} finally {
			jdt.close(rset, pstm);
		}

		return reviewList;
	}
	
	
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
	
	// 후기순 조회, 후기 2개이상인 것들만, 탈퇴유저후기 제외
	public List<Movie> selectMovieByReviewCount(Connection conn){
		Movie movie = null;
		List<Movie> res = new ArrayList<Movie>();
		PreparedStatement pstm = null;
		ResultSet rset = null;
		
		try {
			String sql = "select * from mv_basic_info where mv_no in (select mv_no from (select r.mv_no, count(*) as cnt from user_review r inner join \"USER\" u using (user_no) where u.is_leave = 0 group by mv_no having count(*)>=2 order by cnt desc))";
			
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
	/**
	 * 
	 * @Author : 조아영
	   @Date : 2021. 2. 6.
	   @param conn
	   @param count
	   @return
	   @work :
	 */
	public List<Movie> selectMovieBySocre(Connection conn, int count){
		List<Movie> res = new ArrayList<Movie>();
		Movie movie = null;
		PreparedStatement pstm = null;
		ResultSet rset = null;
		try {
			String sql = "select * from mv_basic_info where mv_no in (select  mv_no from (select  mv_no, AVG(score) AS POINT from user_review  group by mv_no having AVG(score) > 4 order by 2 desc) where rownum <= ?)";
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, count);
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
				res.add(movie);

			}
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
