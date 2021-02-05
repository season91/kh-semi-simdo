package com.kh.simdo.user.model.service;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.kh.simdo.common.code.ConfigCode;
import com.kh.simdo.common.exception.DataAccessException;
import com.kh.simdo.common.exception.ToAlertException;
import com.kh.simdo.common.jdbc.JDBCTemplate;
import com.kh.simdo.common.mail.MailSender;
import com.kh.simdo.common.util.http.HttpUtils;
import com.kh.simdo.user.model.dao.UserDao;
import com.kh.simdo.user.model.vo.User;

/**
 * 
 * @Author : MinHee
 * @Date : 2021. 2. 3.
 * @work :
 */
public class UserService {
	
	UserDao userDao = new UserDao();
	JDBCTemplate jdt = JDBCTemplate.getInstance();
	
	public User userAuthenticate(String userEmail, String userPw) {
		
		Connection conn = jdt.getConnection();
		User res = null;
		try {
			res = userDao.userAuthenticate(conn, userEmail, userPw);
		}finally {
			jdt.close(conn);
		}
		return res;
		
	}
	
	public User selectUserByEmail(String userEmail) {
		
		Connection conn = jdt.getConnection();
		User res = null;
		try {
			res = userDao.selectUserByEmail(conn, userEmail);
		}finally {
			jdt.close(conn);
		}
		return res;
		
	}
	
	public void authenticateEmail(HttpServletRequest request, User user) {
		
		String subject = "Simdo:wm 회원가입을 위한 인증 번호입니다.";
		String htmlText = "";
		
		HttpUtils http = new HttpUtils();
		//header 저장
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/x-www-form-urlencoded");
		
		//인증 번호 생성
		int randomNum = (int)(Math.random()*100000 + 100000);
		String authNum = Integer.toString(randomNum);
		request.getSession().setAttribute("authNum", authNum);
		
		//파라미터 저장
		Map<String, String> params = new HashMap<String, String>();
		params.put("template", "temp_join");
		params.put("userEmail", user.getUserEmail());
		params.put("authNum", authNum);
		
		//url 저장
		String url = ConfigCode.DOMAIN+"/mail";
		
		htmlText = http.post(url, http.urlEncodedForm(params), headers);
		String to = user.getUserEmail();
		new MailSender().sendEmail(subject, htmlText, to);
		
	}
	
	public void findUserPwEmail(User user) {
		
		String subject = "Simdo:wm 계정 비밀번호입니다.";
		String htmlText = "";
		
		HttpUtils http = new HttpUtils();
		//header 저장
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/x-www-form-urlencoded");
		
		//파라미터 저장
		Map<String, String> params = new HashMap<String, String>();
		params.put("template", "temp_pwsearch");
		params.put("userEmail", user.getUserEmail());
		params.put("userPw", user.getUserPw());
		
		//url 저장
		String url = ConfigCode.DOMAIN+"/mail";
		
		htmlText = http.post(url, http.urlEncodedForm(params), headers);
		String to = user.getUserEmail();
		new MailSender().sendEmail(subject, htmlText, to);
		
	}
	
	public int insertUser(User user) {
		
		Connection conn = jdt.getConnection();
		int res = 0;
		
		try {
			res = userDao.insertUser(conn, user);
			jdt.commit(conn);
		} catch (DataAccessException e) {//여기서 catch되어서 web.xml코드가 발동하지 않은것. catch한건 잡지않음.
			// TODO: handle exception
			jdt.rollback(conn);
			throw new ToAlertException(e.error);
		} finally {
			jdt.close(conn);
		}
		
		return res;
		
	}
	
	public int insertKakaoUser(User user) {
		
		Connection conn = jdt.getConnection();
		int res = 0;
		
		try {
			res = userDao.insertKakaoUser(conn, user);
			jdt.commit(conn);
		} catch (DataAccessException e) {//여기서 catch되어서 web.xml코드가 발동하지 않은것. catch한건 잡지않음.
			// TODO: handle exception
			jdt.rollback(conn);
			throw new ToAlertException(e.error);
		} finally {
			jdt.close(conn);
		}
		
		return res;
		
	}
	
	public int insertNaverUser(User user) {
		
		Connection conn = jdt.getConnection();
		int res = 0;
		
		try {
			res = userDao.insertNaverUser(conn, user);
			jdt.commit(conn);
		} catch (DataAccessException e) {//여기서 catch되어서 web.xml코드가 발동하지 않은것. catch한건 잡지않음.
			// TODO: handle exception
			jdt.rollback(conn);
			throw new ToAlertException(e.error);
		} finally {
			jdt.close(conn);
		}
		
		return res;
		
	}
	
	public int updateUser(User userBefore, User userAfter) {
		
		Connection conn = jdt.getConnection();
		int res = 0;
		try {
			res = userDao.updateUser(conn, userBefore, userAfter);
			jdt.commit(conn);
		} catch (DataAccessException e) {
			// TODO: handle exception
			jdt.rollback(conn);
			throw new ToAlertException(e.error);
		} finally {
			jdt.close(conn);
		}
		
		return res;
		
	}
	
	public int deleteUser(String userEmail) {
		
		Connection conn = jdt.getConnection();
		int res = 0;
		
		try {
			res = userDao.deleteUser(conn, userEmail);
			jdt.commit(conn);
		} catch (DataAccessException e) {
			// TODO: handle exception
			jdt.rollback(conn);
			throw new ToAlertException(e.error);
		} finally {
			jdt.close(conn);
		}
		return res;
		
	}
	
}
