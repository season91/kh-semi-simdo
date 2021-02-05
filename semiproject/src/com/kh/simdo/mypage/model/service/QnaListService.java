package com.kh.simdo.mypage.model.service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.kh.simdo.common.jdbc.JDBCTemplate;
import com.kh.simdo.movie.model.vo.Movie;
import com.kh.simdo.mypage.model.dao.QnaListDao;

/**
 * 
 * @author 조아영
 *
 */
public class QnaListService {
	JDBCTemplate jdt = JDBCTemplate.getInstance();
	QnaListDao qnalistDao = new QnaListDao();
	
	// 처음에 출력해줄 페이징 개수 불러오기
	public int[] selectPagingList(int page) {
		Connection conn = jdt.getConnection();
		int[] res = new int[2];
		try {
			res = qnalistDao.selectPaging(conn, page);
		} finally {
			jdt.close(conn);
		}
		
		return res;
	}
	
	// 주어진 페이지의 게시글 불러오기
	public List<Movie> selectQnaList(int page){
		Connection conn = jdt.getConnection();
		List<Movie> res = new ArrayList<Movie>();
		try {
			res = qnalistDao.selectQnaList(conn, page);
		} finally {
			jdt.close(conn);
		}
		
		return res;
		
	}
	
	
}
