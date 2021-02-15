package com.kh.simdo.communication.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.kh.simdo.communication.model.service.CommunicationService;
import com.kh.simdo.communication.model.vo.Communication;
import com.kh.simdo.communication.model.vo.Notice;
import com.kh.simdo.movie.model.vo.Movie;
import com.kh.simdo.mypage.model.service.MypageService;
import com.kh.simdo.user.model.vo.User;

/**
 * @author 김백관
 */
@WebServlet("/comm/*")
public class CommunicationController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	CommunicationService communicationService = new CommunicationService();
    MypageService mypageService = new MypageService();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CommunicationController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] uriArr = request.getRequestURI().split("/");
		switch(uriArr[uriArr.length - 1]) {
		
		case "write.do" :   //q&a쓰기  김백관
			commWrite(request, response);
			break;
		
		case "upload.do" :  //QnA 게시글업로드 김백관 
			uploadComm(request,response);
			break;
		//case "download.do" : download(request,response); break; //김백관, 구현안하시기로.
		case "noticelist.do": noticeList(request,response); break; //조아영
		case "noticedetail.do" : noticeDetail(request,response); break; //조아영
		case "adminnotice.do": adminNotice(request,response); break; // 조민희. 관리자가 공지사항 작성하기 위해 이동하는 메서드.
		case "adminnoticewrite.do": adminNoticeWrite(request, response); break; //조민희 관리자 공지사항 작성
		case "adminnoticeupdate.do": adminNoticeUpdate(request, response); break; //조민희 관리자 공지사항 수정 화면으로 이동
		case "adminnoticeupdateimpl.do": adminNoticeUpdateImpl(request, response); break; //조민희 관리자 공지사항 수정 기능 수행
		case "adminnoticedelete.do": adminNoticeDelete(request, response); break; //조민희 관리자 공지사항 삭제
		case "adminqnalist.do" : allUserQnalist(request,response); break; // 조아영. 모든유저 문의사항 리스트 구현 / 답변 작성 및 수정 기능 구현.
		case "adminqnacoment.do" : userQnaComent(request,response); break; // 조아영. 유저문의 답변.
		case "adminqnacomentimpl.do" : userQnaComentImple(request,response); break; // 조아영. 유저문의 답변.
		
		default:
			break;
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	
	
	/**
	 * 
	 * @Author : 조아영
	   @Date : 2021. 2. 8.
	   @param res
	   @return
	   @work : jsp로 보내기 위해 list->json 파싱
	 */
	 protected List parseJson(List res) {
			List list = new ArrayList();
			Map<String, Object> commandMap = new HashMap<String, Object>();
			for (int i = 0; i < res.size(); i++) {
				String json = new Gson().toJson(res.get(i));
				//System.out.println("전"+json);
			commandMap = new Gson().fromJson(json, Map.class);
			//System.out.println("후"+commandMap.get("qstnNo"));
				list.add(commandMap);
			}
			return list;
	}
	 
	/**
	 * 
	 * @Author : 조아영
	   @Date : 2021. 2. 8.
	   @param request
	   @param response
	   @throws ServletException
	   @throws IOException
	   @work : 공지사항 페이징처리 및 리스트 불러오기
	 */
	protected void noticeList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 공지사항 목록 리스트
		
		// 페이징 표현 해주는 메서드
		String text = request.getParameter("page");
		List noticeList = new ArrayList();
		int page = 0;
		if(text == null) {
			page++;
		} else {
			page = Integer.parseInt(text);
		}
		

		int[] res = communicationService.selectPagingByNotice(page);
		request.setAttribute("start", res[0]);
		request.setAttribute("end", res[1]);
		
		List<Map<String, Object>> pageRes = communicationService.selectNoticeList(page);
		
		noticeList = parseJson(pageRes);
		request.setAttribute("res", noticeList);
		request.setAttribute("page", page);
		// 유저가 관리자임을 알려줄 것
		User user = (User) request.getSession().getAttribute("user");
		request.setAttribute("admin", user.getAdmin());
		request.getRequestDispatcher("/WEB-INF/view/comm/noticelist.jsp").forward(request, response);
	}

	protected void allUserQnalist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//모든유저 qna 가져와서 페이징처리 해주기
		String text = request.getParameter("page");
		List qnaAllList = new ArrayList();
		int page = 0;
		if(text == null) {
			page++;
		} else {
			page = Integer.parseInt(text);
		}
		int[] res = communicationService.selectPagingByNotice(page);
		request.setAttribute("start", res[0]);
		request.setAttribute("end", res[1]);
		
		List<Map<String, Object>> pageRes = communicationService.selectAllQnaList(page);
		qnaAllList = parseJson(pageRes);
		request.setAttribute("res", qnaAllList);
		request.setAttribute("page", page);
		// 유저가 관리자임을 알려줄 것
		User user = (User) request.getSession().getAttribute("user");
		request.setAttribute("admin", user.getAdmin());
		request.getRequestDispatcher("/WEB-INF/view/comm/adminqnalist.jsp").forward(request, response);
		
	}

	/**
	 * 
	 * @Author : 조아영
	   @Date : 2021. 2. 8.
	   @param request
	   @param response
	   @throws ServletException
	   @throws IOException
	   @work : 페이징된 공지사항 제목 눌렀을때 상세내용 가져오는 메서드. 
	 */
	protected void noticeDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 아영, 게시글 번호 기준으로 notice테이블 정보 가져온다.
		String strNoticeNo = String.valueOf(request.getParameter("noticeno"));
		int noticeNo = Integer.parseInt(strNoticeNo);
		Notice notice = communicationService.selectNoticeByNoticeNo(noticeNo);
		
		//민희, 관리자임을 알게 해주기 위해 코드 추가(수정, 삭제버튼을 위해서)
		User user = (User) request.getSession().getAttribute("user");
		request.setAttribute("admin", user.getAdmin());
		
		// 아영, notice 객체 jsp로 전달
		request.setAttribute("res", notice);
		request.getRequestDispatcher("/WEB-INF/view/comm/noticedetail.jsp").forward(request, response);
	}
	
	protected void userQnaComent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 상세화면으로 이동 후 답변하기.
		String strQnaNo = String.valueOf(request.getParameter("qnano"));
		int qnaNo = Integer.parseInt(strQnaNo);
		User user = (User) request.getSession().getAttribute("user");
		request.setAttribute("admin", user.getAdmin());
		Communication comm = communicationService.selectQnaByQstnNo(qnaNo);
		request.setAttribute("res", comm);
		request.getRequestDispatcher("/WEB-INF/view/comm/adminqnacoment.jsp").forward(request, response);
	}

	protected void userQnaComentImple(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 답변내용 DB에 넣기.
		String strQnaNo = request.getParameter("qstnno");
		int qstnNo = Integer.parseInt(strQnaNo);
		String coment = request.getParameter("coment");

		int res = communicationService.updateQnaComent(coment, qstnNo);
		if(res > 0 ) {
			System.out.println("성공.");
			allUserQnalist(request,response);
		} else { 
			System.out.println("실패");
		}
	}

	

	
	private void commWrite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	request.getRequestDispatcher("/WEB-INF/view/comm/commwrite.jsp")
	.forward(request, response);

	}

	
	private void uploadComm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session=request.getSession();
		User user= (User) session.getAttribute("user");
		
		
		communicationService.insertComm(user.getUserNm(),user.getUserNo(),request);
		
		
		
		request.getRequestDispatcher("/WEB-INF/view/index/index.jsp")
		.forward(request, response);
		}
	

	/**
	 * @author 김백관
	 * 파일 다운로드 //필요시 활성화
	 */
	/*
	private void download(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	String originFileName = request.getParameter("ofname");
	String renameFileName = request.getParameter("rfname");
	String savePath = request.getParameter("savePath");
	response.setHeader("content-disposition", "attachment; filename="+originFileName);
	request.getRequestDispatcher("/file"+savePath+renameFileName)
	.forward(request, response);
}
	 */
	
	/**
	 * @author MinHee
	 * @Date 2021. 02. 15
	 */
	private void adminNotice(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = (User) request.getSession().getAttribute("user");
		request.setAttribute("admin", user.getAdmin());
		
		request.getRequestDispatcher("/WEB-INF/view/comm/adminnotice.jsp")
		.forward(request, response);
	}
	
	/**
	 * @author MinHee
	 * @Date 2021. 02. 15
	 */
	private void adminNoticeWrite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String ntTitle = request.getParameter("ntTitle");
		String ntContent = request.getParameter("ntContent");
		
		Notice notice = new Notice();
		notice.setNtTitle(ntTitle);
		notice.setNtContent(ntContent);
		
		int res = communicationService.insertNotice(notice);
		
		if(res > 0) {
			request.setAttribute("alertMsg", "공지사항이 등록되었습니다.");
			request.setAttribute("url", "/comm/noticelist.do");
			
			request
			.getRequestDispatcher("/WEB-INF/view/common/result.jsp")
			.forward(request, response);
		}else {
			request.setAttribute("alertMsg", "에러 발생");
			request.setAttribute("url", "/comm/noticelist.do");
			
			request
			.getRequestDispatcher("/WEB-INF/view/common/result.jsp")
			.forward(request, response);
		}
	}
	
	/**
	 * @author MinHee
	 * @Date 2021. 02. 15
	 */
	private void adminNoticeUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int noticeNo = Integer.parseInt(request.getParameter("noticeNo"));
		Notice notice = communicationService.selectNoticeByNoticeNo(noticeNo);
		
		User user = (User) request.getSession().getAttribute("user");
		request.setAttribute("admin", user.getAdmin());
		
		request.setAttribute("notice", notice);
		request.getRequestDispatcher("/WEB-INF/view/comm/adminnoticeupdate.jsp")
		.forward(request, response);
	}
	
	/**
	 * @author MinHee
	 * @Date 2021. 02. 15
	 */
	private void adminNoticeUpdateImpl(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String noticeNum = request.getParameter("noticeNo");
		String ntTitle = request.getParameter("ntTitle");
		String ntContent = request.getParameter("ntContent");
		
		int noticeNo = Integer.parseInt(noticeNum);
		
		Notice notice = new Notice();
		notice.setNoticeNo(noticeNo);
		notice.setNtTitle(ntTitle);
		notice.setNtContent(ntContent);
		
		int res = communicationService.updateNotice(notice);
		
		if(res > 0) {
			request.setAttribute("alertMsg", "공지사항이 수정되었습니다.");
			request.setAttribute("url", "/comm/noticelist.do");
			
			request
			.getRequestDispatcher("/WEB-INF/view/common/result.jsp")
			.forward(request, response);
		}else {
			request.setAttribute("alertMsg", "에러 발생");
			request.setAttribute("url", "/comm/noticelist.do");
			
			request
			.getRequestDispatcher("/WEB-INF/view/common/result.jsp")
			.forward(request, response);
		}
	}
	
	/**
	 * @author MinHee
	 * @Date 2021. 02. 15
	 */
	private void adminNoticeDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int noticeNo = Integer.parseInt(request.getParameter("noticeNo"));
		
		int res = communicationService.deleteNotice(noticeNo);
		
		if(res > 0) {
			request.setAttribute("alertMsg", "공지사항이 삭제되었습니다.");
			request.setAttribute("url", "/comm/noticelist.do");
			
			request
			.getRequestDispatcher("/WEB-INF/view/common/result.jsp")
			.forward(request, response);
		}else {
			request.setAttribute("alertMsg", "에러 발생");
			request.setAttribute("url", "/comm/noticelist.do");
			
			request
			.getRequestDispatcher("/WEB-INF/view/common/result.jsp")
			.forward(request, response);
		}
	}
}


