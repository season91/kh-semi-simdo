package com.kh.simdo.common.util.calendar;

import java.util.List;

//import com.kh.simdo.board.model.vo.Board;

//달력에서 사용되는 공통 기능을 정의
public class Util {
	
	//한자리를 두자리로 변경 202111 -> 20210101
	public static String isTwo(String month) {
		return (month.length()<2)?("0"+month):month;
	}
	
	//달력에 토요일 일요일 평일 글자색 변경
	public static String fontColor(int date, int dayOfWeek) {
		int dayCal = (dayOfWeek-1+date)%7;
		if(dayCal==0) {
			return "blue";
		}else if(dayCal==1) {
			return "red";
		}else {
			return "black";
		}
	}
	
	//clist 년 월에 모든 글을 가지고 있음
	//해당 일에 리스트를 뿌려줌
	
	
	/*
	 * public static String getCalView(int i, List<Board> clist) { //
	 * //mdate(yyyymmdd) String d = isTwo(String.valueOf(i)); String res = "";
	 * for(Board board : clist) { if(board.getWatchDate().substring(6,8).equals(d))
	 * { res +=
	 * "<p><a href='./CalController.do?command=detail&reviewNo='"+board.getReviewNo(
	 * )+">"+ ((board.getThumbnail()))+"</a></p>"; } }
	 * 
	 * return res; }
	 */
	 
	 

}
