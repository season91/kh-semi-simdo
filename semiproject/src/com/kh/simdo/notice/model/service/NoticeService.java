package com.kh.simdo.notice.model.service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kh.simdo.common.jdbc.JDBCTemplate;
import com.kh.simdo.common.util.file.FileVO;
import com.kh.simdo.communication.model.vo.Communication;
import com.kh.simdo.movie.model.vo.Movie;
import com.kh.simdo.notice.model.dao.NoticeDao;
import com.kh.simdo.notice.model.vo.Notice;


/**
 * 
 * @author 김백관
 *
 */
public class NoticeService {
	JDBCTemplate jdt = JDBCTemplate.getInstance();
	NoticeDao noticeDao = new NoticeDao();
	
	// 처음에 출력해줄 페이징 개수 불러오기
	public int[] selectPagingList(int page) {
		Connection conn = jdt.getConnection();
		int[] res = new int[2];
		try {
			res = noticeDao.selectPaging(conn, page);
		} finally {
			jdt.close(conn);
		}
		
		return res;
	}
	
	// 주어진 페이지의 게시글 불러오기
	public List<Notice> selectNoticeList(int page){
		Connection conn = jdt.getConnection();
		List<Notice> res = new ArrayList<Notice>();
		try {
			res = noticeDao.selectNoticeList(conn, page);
		} finally {
			jdt.close(conn);
		}
		
		return res;
		
	}
}
