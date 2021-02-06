package com.kh.simdo.common.util.file;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import com.kh.simdo.common.code.ConfigCode;
import com.kh.simdo.common.code.ErrorCode;
import com.kh.simdo.common.exception.ToAlertException;
import com.oreilly.servlet.multipart.FilePart;
import com.oreilly.servlet.multipart.MultipartParser;
import com.oreilly.servlet.multipart.ParamPart;
import com.oreilly.servlet.multipart.Part;
//  파일 업로드
//	renameFilename 생성
//  저장경로 생성
//	저장경로를 생성하고 파일저장 
//  파일메타정보, 파라미터를 Map에 저장
//	파라미터 저장
//	파일메타정보 저장
public class FileUtil {
	private final int maxSize = 1024 * 1024 * 10;
	//파일을 업로드 하고
	//파일 테이블에 저장할 파일메타정보와 파라미터 값을 저장해서 Map에 담아 return
	Map<String,List> multiParamMap = new HashMap<String,List>();
	
	public Map<String,List> fileUpload(HttpServletRequest request){
		//파일메타정보를 저장할 리스트
		List<FileVO> fileDataList = new ArrayList<FileVO>();
		//파라미터를 저장할 리스트
		MultipartParser mp;
		try {
			mp = new MultipartParser(request, maxSize);
			Part part;
			while((part = mp.readNextPart()) != null) {
				if(part.isParam()) {
					ParamPart params = (ParamPart)part;
					List<String> paramList = getParamValue(params);
					multiParamMap.put(params.getName(), paramList);
				}else if(part.isFile()){
					FilePart userFile = (FilePart)part;
					//업로드된 파일이 존재해야 getFileName이 null이 아니다.
					if(userFile.getFileName() != null) {
						FileVO fileData = getFileData(userFile); //file테이블에 저장할 파일메타정보를 반환
						fileDataList.add(fileData); //fileDataList에 파일메타정보를 저장
						saveFile(userFile, fileData); //파일을 업로드
					}
				}
			}
			multiParamMap.put("fileData",fileDataList); //수정한 코드
		} catch (IOException e) {
			throw new ToAlertException(ErrorCode.FILE01);
		}
		return multiParamMap;
	}
	
	public void deleteFile(String path) {
		//수정된 코드
		File file = new File(ConfigCode.UPLOAD_PATH+path);
		file.delete();
	}
	
	private List<String> getParamValue(ParamPart params) throws UnsupportedEncodingException{
		String paramName = params.getName();
		String paramValue = params.getStringValue("UTF-8");

		List<String> paramList;
		
		if(multiParamMap.get(paramName) == null) {
			paramList = new ArrayList<String>();
			paramList.add(paramValue);
		}else {
			paramList = multiParamMap.get(paramName);
			paramList.add(paramValue);
		}
		
		return paramList;
	}
	
	private FileVO getFileData(FilePart userFile) throws UnsupportedEncodingException {
		//파일 제목 인코딩 문제해결
		String originFileName = new String(userFile.getFileName().getBytes("iso-8859-1"),"UTF-8");
		//파일을 저장할 때는 유니크한 파일명으로 저장
		String renameFileName = getRenameFileName(originFileName);
		FileVO fileData = new FileVO();
		fileData.setOriginFileName(originFileName);
		fileData.setRenameFileName(renameFileName);
		//수정된 코드
		fileData.setSavePath(getSubPath());
		return fileData;
	}
	
	private void saveFile(FilePart userFile, FileVO fileData) throws IOException {
		String path = ConfigCode.UPLOAD_PATH+fileData.getSavePath();
		new File(path).mkdirs(); //수정한 코드
		File file = new File(path + fileData.getRenameFileName()); //수정한 코드
		userFile.writeTo(file);
	}
	
	private String getSubPath() {
		//저장경로 생성
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int date = cal.get(Calendar.DAY_OF_MONTH);
		String subPath = year + "/" + month + "/" + date + "/";
		
		return subPath;
	}
	
	private String getRenameFileName(String originFileName) {
		//유니크한 파일명 생성
		UUID renameFileID = UUID.randomUUID();
		String renameFileName = renameFileID.toString() 
				//마지막 .을 기준으로 .뒤의 문자열을 잘라서 반환 : 확장자
				+ originFileName.substring(originFileName.lastIndexOf("."));
		return renameFileName;
	}
}
