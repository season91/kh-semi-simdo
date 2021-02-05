package com.kh.simdo.common.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import oracle.jdbc.OracleConnection;
import oracle.jdbc.pool.OracleDataSource;

public class JDBCTemplate {

	
	//JDBCTemplate은 여러번 할 필요가없어서 딱 한번만하는게 메모리도 아끼고 좋지 않겠나? 그럴때 사용하는 패턴.
		//singleton 패턴
		//클래스의 인스턴스가 하나만 생성되어야 할 때 사용하는 디자인 패턴
		//생성자를 private로 만들어 외부에서 JDBCTemplate의 생성을 차단 (하나의 인스턴스만 하는게 목표니까.)
	
	private static JDBCTemplate instance;
	
	public static JDBCTemplate getInstance() {
		if(instance == null) {
			instance = new JDBCTemplate();
		}
		return instance;
	}
	
public JDBCTemplate() {
		
		
	}
	
	//Connection 객체를 만들 메서드
	public Connection getConnection() {
		

		
		//String url = "jdbc:oracle:thin:@simdoDB_medium?TNS_ADMIN=C:/CODE/wallet/Wallet_simdoDB";
		String url = "jdbc:oracle:thin:@simdoDB_medium?TNS_ADMIN=/Users/choayoung91/Documents/CODE/wallet/Wallet_simdoDB";
		
		String user = "admin";
		String password = "Simdodatabase4";
		
		Properties info = new Properties();     
	    info.put(OracleConnection.CONNECTION_PROPERTY_USER_NAME, user);
	    info.put(OracleConnection.CONNECTION_PROPERTY_PASSWORD, password);          
	    info.put(OracleConnection.CONNECTION_PROPERTY_DEFAULT_ROW_PREFETCH, "20");  
		
	    
	    Connection  conn = null;
		try {

		    OracleDataSource ods = new OracleDataSource();
		    ods.setURL(url);    
		    ods.setConnectionProperties(info);
		    
			conn = ods.getConnection();
			//Transaction을 개발자가 관리하기 위해 오토커밋을 fasle로 지정
			conn.setAutoCommit(false);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
		
	}
	
	//commit 수행
	public void commit(Connection conn) {
		try {
			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	//rollback 수행
	public void rollback(Connection conn) {
		try {
			conn.rollback();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//각조합별로 close 묶어주기. 3개짜리 2개짜리 2개
	public void close(ResultSet rset, Statement stmt, Connection conn) {
		try {
			// null이 아니고 닫힌상태가 아니라면 닫아주기.
			if (rset != null && !rset.isClosed()) {
				rset.close();
			}
			if (stmt != null && !stmt.isClosed()) {
				stmt.close();
			}
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	public void close(ResultSet rset, Statement stmt) {
		try {
			// null이 아니고 닫힌상태가 아니라면 닫아주기.
			if (rset != null && !rset.isClosed()) {
				rset.close();
			}
			if (stmt != null && !stmt.isClosed()) {
				stmt.close();
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void close(Statement stmt, Connection conn) {
		try {
			// null이 아니고 닫힌상태가 아니라면 닫아주기.
			if (stmt != null && !stmt.isClosed()) {
				stmt.close();
			}
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void close(Statement stmt) {
		try {
			// null이 아니고 닫힌상태가 아니라면 닫아주기.
		
			if (stmt != null && !stmt.isClosed()) {
				stmt.close();
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void close(Connection conn) {
		try {
		
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
