package com.kh.simdo.common.util.http;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import com.kh.simdo.common.code.ErrorCode;
import com.kh.simdo.common.exception.ToAlertException;

public class HttpUtils {
	// 우리서버가 우리서버한테 하는 통신임. fetch는 클라이언트가 우리 서버한테 요청하는 비동기통신임.
	// HttpURLConnection : http, https 통신을 모두 지원
	// http 통신을 위해 필요한 것들
	// 1. 시작줄(url, method)
	// 2. header
	// 3. body
	
	// 기능 메서드 분리
	// 0. get/post 통신 메서드
	// 1. HttpURLConnection 객체
	// 2. 헤더작성
	// 3. 바디작성
	// 4. 응답받기
	// 5. urlEncoded 포맷팅
	HttpURLConnection conn = null;
	
	
	// get 방식인데 헤더가 없는 친구 
	public String get(String url) {
		String res = "";
		//close해줘야해서 밖으로 뺀다.
		
		try {
			// 커넥션만들구 헤더셋팅후 응답받기
			setConnection(url,"GET");
			res = getResponse();
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new ToAlertException(ErrorCode.API01,e);
		} 

		return res;
	}
	
	// get 방식인데 헤더가 있는 친구
	public String get(String url, Map<String, String> headers) {
		String res = "";
		//close해줘야해서 밖으로 뺀다.
		
		try {
			// 커넥션만들구 헤더셋팅후 응답받기
			setConnection(url,"GET");
			setHeaders(headers);
			res = getResponse();
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new ToAlertException(ErrorCode.API01,e);
		} 

		return res;
	}
	// post방식은 헤더와 바디가 있다.
	public String post(String url, String body, Map<String, String> headers) {
		String res = "";
		try {
			setConnection(url, "POST");
			setHeaders(headers);
			setBody(body);
			res = getResponse();
			
		} catch (IOException e) {
			throw new ToAlertException(ErrorCode.API01,e);
		}
		return res;
	}
	// 5. urlEncoded => &name=value&name=value&~ 이런거 포맷팅 , 바깥에서 사용할 메서드 
	// 외부에서 바디데이터 뿌릴수 있도록 만든 인코디드 폼
	// 전에 오더메뉴 할때 / if문 걸었던거랑 같은 거 쓸거임
	public String urlEncodedForm(Map<String, String> params) {
		String res = "";

		try {
			
			for (String key : params.keySet()) {
				// 아스키코드표로 표현가능하게끔 문자를 인코딩
				res += "&" + URLEncoder.encode(key, "UTF-8") + "=" + URLEncoder.encode(params.get(key), "UTF-8");
			}
			
			if (res.length() > 0) {
				res = res.substring(1);
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return res;
	}
	
	// 1. HttpURLConnection 객체
	public void setConnection(String url, String method) throws IOException {
		URL u = new URL(url);
		conn = (HttpURLConnection) u.openConnection();
		// get 방식 : url로 통신하니까 헤더도 필요없고 바디도 필요없다. 
		conn.setRequestMethod(method);
		
		//post일 경우 doOutput 옵션을 true로 지정해 출력스트림 사용 여부를 true로 지정
		if(method.equals("POST")) {
			conn.setDoOutput(true);
		}
		
		// conn 셋팅할때 함께 셋팅해주면 좋은 코드
		// 통신이 지연되 경우 통신을 종료할 시간을 10초로 지정. 10초기다렸다가 닫아줄거임
		conn.setConnectTimeout(10000);
		conn.setReadTimeout(10000);
		
	}
	// 2. 헤더작성
	private void setHeaders(Map<String,String> headers) {
		// map 렝쓰가 0이라면 안돌꺼임
		for (String key : headers.keySet()) {
			conn.addRequestProperty(key, headers.get(key));
			
		}
	}
	
	// 3. 바디작성 (http의 바디부분)
	private void setBody(String body) throws IOException {
		// output스트림 열고 내용 넣음녀 됨
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
		try {
			bw.write(body);
			bw.flush();
		} finally {
			bw.close();
		}
	}
	
	// 4. 응답받기
	private String getResponse() throws IOException {
		
		String res = "";
		BufferedReader br = null;
		
		int status = conn.getResponseCode();
		// 응답코드 200번대인지 확인
		if(status >= 200 && status < 300) {
			// 맞다면 body정보를 inputStream으로 읽어서 문자열에 저장
			// http 통신이니까 대량의 데이터가 오고 갈 수 있는 환경인 상황. 최대한 신경써서 처리해야한다.속도때문에 버퍼리더사용
			
			// 혹시 모르니까 try-finally로 감싸주기.
			try {
				br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				// 내용이많아서 readline으로 다 못읽음. 반복 돌려줘야함
				// 한줄씩 읽는 line
				String line;
				StringBuffer sb = new StringBuffer();
				while((line = br.readLine()) != null) {
					sb.append(line);
				}
				res = sb.toString();
			} finally {
				br.close();
			}

		} else {
			throw new ToAlertException(ErrorCode.API01, new Exception(status+"가 응답되었습니다."));
		}

		return res;
	}
	
}
