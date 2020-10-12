package com.rk.util;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class EmailUtil {
	@Autowired
	private JavaMailSender sender;
	
	/**
	 * Actual Logic
	 * @param to
	 * @param subject
	 * @param text
	 * @param cc
	 * @param bcc
	 * @param file
	 * @return
	 */
	public boolean send(
			String to,
			String subject,
			String text,
			String cc[],
			String bcc[],
			MultipartFile file
			)
	{
		boolean sent;
		try {
			//1. Create MimeMessage Object
			MimeMessage message = sender.createMimeMessage();
			
			//2. Use helper class object
			MimeMessageHelper helper = new MimeMessageHelper(message,file!=null?true:false);
			
			//3. set details to object
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(text);
			if(cc!=null && cc.length>0)
				helper.setCc(cc);
			if(bcc!=null && bcc.length>0)
				helper.setBcc(bcc);
			
			if(file!=null) {
				helper.addAttachment(file.getOriginalFilename(), file);
			}
			
			//4. send email
			sender.send(message);
			
			sent = true;
		} catch (Exception e) {
			sent = false;
			e.printStackTrace();
		}
		
		return sent;
	}
	
	/**
	 * Overloaded method
	 * @param to
	 * @param subject
	 * @param text
	 * @return
	 */
	public boolean send(
			String to,
			String subject,
			String text)
	{
		return send(to, subject, text, null, null, null);
	}
	
	/**
	 * Overloaded method
	 * @param to
	 * @param subject
	 * @param text
	 * @param file
	 * @return
	 */
	public boolean send(
			String to,
			String subject,
			String text,
			MultipartFile file)
	{
		return send(to, subject, text, null, null, file);
	}
}


