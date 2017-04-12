package com.gxl.common.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class UploadUtils {

	static WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
	static ServletContext  servletContext = webApplicationContext.getServletContext();
	private static SimpleDateFormat sf=new SimpleDateFormat("yyyyMMddHHmmss");
	
	public static String uploadFeedbackPic(Integer userid,MultipartFile[] base64Pic) {
		if(base64Pic==null||base64Pic.length<=0)
			return "";
		OutputStream outputStream=null;
		String names="";
		try {
			int i=0;
			for(MultipartFile pic:base64Pic){
				//源文件名
				String origin=pic.getOriginalFilename();//文件名(已包括后缀)
				String filename=sf.format(new Date())+"_"+i+"."+origin.substring(origin.lastIndexOf(".")+1);
				names=names+filename+",";
				String path = servletContext.getRealPath("/WEB-INF/upload/feedback/");
				File file=new File(path+userid);
				if(!file.exists())
					file.mkdir();
				path = path +userid+"/"+filename;
				outputStream=new FileOutputStream(path);
				outputStream.write(pic.getBytes());
				outputStream.flush();
				outputStream.close();				
				++i;
			}			
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		return names.substring(0, names.length()-1);
	}
	
	public static String uploadHeadPic(Integer userid,String base64Pic) {
		if(base64Pic==null||base64Pic.equals(""))
			return "";
		OutputStream outputStream=null;
		String filename="";
		try {
			BASE64Decoder decoder=new BASE64Decoder();
			byte[] imgs=decoder.decodeBuffer(base64Pic);
			filename=sf.format(new Date())+".jpg";
			String path = servletContext.getRealPath("/WEB-INF/upload/head_img/");
			File file=new File(path+userid);
			if(!file.exists())
				file.mkdir();			
			path= path  +userid+"/"+filename;
			outputStream=new FileOutputStream(path);
			outputStream.write(imgs);
			outputStream.flush();
			outputStream.close();							
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		return filename;
	}
	
	public static void text() {
		String names="";
		try {
			 ByteArrayOutputStream outputStream = null;  
            BufferedImage bufferedImage = ImageIO.read(new File("1.png"));  
            outputStream = new ByteArrayOutputStream();  
            ImageIO.write(bufferedImage, "png", outputStream);  
            // 对字节数组Base64编码  
            BASE64Encoder encoder = new BASE64Encoder(); 
            String oril=encoder.encode(outputStream.toByteArray());
            FileOutputStream write = new FileOutputStream(new File("3.png"));
            BASE64Decoder decoder = new BASE64Decoder(); 
            byte[] decoderBytes = decoder.decodeBuffer(oril);  
            write.write(decoderBytes);  
            write.close();  
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
