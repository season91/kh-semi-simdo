package com.kh.simdo.common.mail;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.kh.simdo.common.code.ConfigCode;
import com.kh.simdo.common.code.ErrorCode;
import com.kh.simdo.common.exception.ToAlertException;

public class MailSender {
	
	//모듈화
	//1. Session 객체 생성
	//2. 메세지 작성
	//3. 메세지의 body부분을 작성하기 위해 multipart 객체 생성
	
	public void sendEmail(String subject, String text, String to) {
		
		try {
			//4. 메시지 작성을 위한 MimeMessage 생성
			//	 생성할 때 Session 객체를 전달
			MimeMessage msg = new MimeMessage(getSession());
			
			msg.setFrom(new InternetAddress(ConfigCode.EMAIL.desc));
			msg.setRecipients(Message.RecipientType.TO, to);
			msg.setSubject(subject);
			msg.setContent(getMultipart(text));
			
			//6. javax.mail 라이브러리가 메일 전송을 위해 제공해주는 Transport 클래스의
			//	 send 메서드를 사용해 메일 전송
			Transport.send(msg);
		} catch (MessagingException e) {
			throw new ToAlertException(ErrorCode.MAIL01, e);
		}
		
	}
	
	private Session getSession() {
		
		//1. 네이버 smtp 서버를 사용하기 위해 인증정보
		//	 네이버 id, pw
		PasswordAuthentication pa = new PasswordAuthentication("", "");
		
		//2. 사용할 smtp 서버 정보를 작성
		//	 smtp 서버이름, 포트, tls 통신 가능여부, 사용자 인증 여부
		Properties prop = System.getProperties();
		prop.put("mail.smtp.host", "smtp.naver.com");
		prop.put("mail.smtp.port", "587");
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.starttls.enable", "true");
		
		//3. javax.mail의 Session 객체를 생성하여
		//	 인증정보, smtp 서버 정보를 전달
		Session session = Session.getDefaultInstance(prop, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return pa;
			}
		});
		
		return session;
		
	}
	
	private Multipart getMultipart(String text) throws MessagingException {
		
		//5. smtp 메시지의 body를 작성할 때, 텍스트 이외의 content-type 데이터를
		//	 전송하기 위한 MimeMultipart 객체를 생성해서 메일 본문을 작성
		Multipart multipart = new MimeMultipart();
		MimeBodyPart htmlPart = new MimeBodyPart();
		
		htmlPart.setContent(text, "text/html; charset=UTF-8");
		multipart.addBodyPart(htmlPart);
		
		return multipart;
		
	}
	
}
