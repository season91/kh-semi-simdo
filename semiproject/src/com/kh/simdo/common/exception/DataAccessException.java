package com.kh.simdo.common.exception;

import com.kh.simdo.common.code.ErrorCode;

//예외처리가 강제되지 않는 예외클래스 생성
//select절은 commit이 필요하지않으니까. 그래서 RuntimeException 예외처리 만든거임. 무조건발생하는게 아니니깐.
//Spring 프레임워크에 이름이 DataAccessException임. 
public class DataAccessException extends CustomException{

	private static final long serialVersionUID = 521587827126031031L;

	// SQL Exception을 unchecked로 감싸기 위해 사용하는 예외
	// 반드시 예외에 대한 로그를 남겨야 한다. 
	public DataAccessException(ErrorCode error, Exception e) {
		super(error,e);
	}
	
}

