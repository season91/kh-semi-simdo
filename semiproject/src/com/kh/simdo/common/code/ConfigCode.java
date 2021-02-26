package com.kh.simdo.common.code;

public enum ConfigCode {
	//런칭용
	//DOMAIN("https://www.bookmanager.ga"),
	//개발용
	DOMAIN("http://localhost:9090"),
	EMAIL(""),
	// 파일 저장은 내부에다하면 배포할때 다날라가니까 꼭 외부 파일서버에다가 업로드 할 것
	UPLOAD_PATH("C:\\CODE\\05_Servlet\\resources\\upload\\");
	
	public String desc;
	
	ConfigCode(String desc) {
		// TODO Auto-generated constructor stub
		this.desc = desc;
	}
	
	public String toString() {
		return desc;
	}

}
