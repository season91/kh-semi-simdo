package com.kh.simdo.common.code;

public enum ErrorCode {
	
	SU01("회원정보를 조회하는 도중 에러가 발생하였습니다.", "/user/login.do"),
	IU01("회원정보를 입력하는 중 에러가 발생했습니다.", "/user/join.do"),
	UU01("회원정보 수정 중 에러가 발생했습니다."),
	DU01("회원정보 삭제 중 에러가 발생했습니다."),
	SRV01("내가 작성한 영화 후기를 조회하는 도중 에러가 발생하였습니다."),
	SRV02("영화별 후기 조회 도중 에러가 발생하였습니다"),
	IRV01("내가 작성한 영화 후기를 등록하는 도중 에러가 발생하였습니다."),
	URV01("내가 작성한 영화 후기를 수정하는 도중 에러가 발생하였습니다."),
	DRV01("내가 작성한 영화 후기를 삭제하는 도중 에러가 발생하였습니다."),
	SFL01("내가 작성한 나만의 명대사를 조회하는 도중 에러가 발생하였습니다."),
	SFL02("영화별 명대사 조회 도중 에러가 발생하였습니다"),
	IFL01("내가 작성한 나만의 명대사를 등록하는 도중 에러가 발생하였습니다."),
	UFL01("내가 작성한 나만의 명대사를 수정하는 도중 에러가 발생하였습니다."),
	DFL01("내가 작성한 나만의 명대사를 삭제하는 도중 에러가 발생하였습니다."),
	IN01("공지사항을 등록하는 도중 에러가 발생하였습니다."),
	UN01("공지사항을 수정하는 도중 에러가 발생하였습니다."),
	DN01("공지사항을 삭제하는 도중 에러가 발생하였습니다."),
	IQ01("문의사항 등록 도중 에러가 발생하였습니다."),
	UQ01("문의사항 수정 도중 에러가 발생하였습니다."),
	DQ01("문의사항 삭제 도중 에러가 발생하였습니다."),
	UQ02("문의사항 답변 도중 에러가 발생하였습니다"),
	SM01("영화정보 조회 중 에러가 발생했습니다."), //select
	IM01("insert중 에러 발생."), //insert
	IB01("게시글 등록 중 에러가 발생했습니다."),
	SB01("게시글 정보를 조회하는 도중 에러가 발생하였습니다"),
	SF01("파일을 조회하는 도중 에러가 발생하였습니다."),
	IF01("파일정보 등록 중 에러가 발생했습니다."),
	UM01("회원정보 수정 중 에러가 발생하였습니다."), //update
	DM01("회원정보 삭제 중 에러가 발생하였습니다."), //delete
	AUTH01("해당 페이지는 로그인 후에 이용하실 수 있습니다."),
	AUTH02("이미 인증이 만료된 링크입니다."),
	AUTH03("게시글은 로그인 후 작성할 수 있습니다."),
	AUTH04("관리자만 접근이 가능합니다."),
	MAIL01("메일 발송 중 에러가 발생했습ㅂ니다."),
	API01("API통신 도중 에러가 발생하였습니다."),
	CD_404("존재하지 않는 경로입니다."),
	FILE01("파일업로드 중 예외가 발생하였습니다."),
	DW01("찜해제 도중 에러가 발생하였습니다."),
	SW01("찜목록 조회중 에러가 발생하였습니다."),
	IW01("찜목록 추가 도중 에러가 발생하였습니다."),
	;
	
	//reslut.jsp를 사용해 띄울 안내문구
	private String errMsg;
	//result.jsp를 사용해 이동시킬 경로 
	private String url = "/index.do";
	
	// index로 이동시킬 경우 에러메시지만 전달받는다.
	ErrorCode(String errMsg){
		this.errMsg = errMsg;
	}
	
	//index외 지정페이지로 이동시 url도 같이 받는다.
	ErrorCode(String errMsg, String url){
		this.errMsg = errMsg;
		this.url = url;
	}

	public String errMsg() {
		return errMsg;
	}
	
	public String url() {
		return url;
	}
}
