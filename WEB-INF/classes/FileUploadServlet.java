//File upload servlet from https://javatutorial.net/java-servlet-file-upload
//Modified by WM search for WM for changes
//Instructions:
//  - start tomcat server on port 8081
//  - in browser type localhost:8081/FileUploadServlet/upload

//package net.javatutorial.tutorials;               //Commented out WM
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
//@WebServlet(name = "uploadServlet", urlPatterns = { "/upload" }, loadOnStartup = 1)   //Commented out WM

//@MultipartConfig is required as per https://stackoverflow.com/questions/2422468/how-to-upload-files-to-server-using-jsp-servlet
@MultipartConfig(fileSizeThreshold = 6291456, // 6 MB                                 
		maxFileSize = 10485760L, // 10 MB                                               
		maxRequestSize = 20971520L // 20 MB                                             
)                            
  
public class FileUploadServlet extends HttpServlet {                                    
	private static final long serialVersionUID = 5619951677845873534L;
	
	private static final String UPLOAD_DIR = "uploads";
	//@Override                                                                         //Commented out WM
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {				 
		
		PrintWriter writer = response.getWriter(); 
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		writer.append("<!DOCTYPE html>\r\n")
		.append("<html>\r\n")
		.append("    <head>\r\n")
		.append("        <title>File Upload Form</title>\r\n")
		.append("    </head>\r\n")
		.append("    <body>\r\n");
		
		writer.append("<h1>Upload file</h1>\r\n");
		
		//https://stackoverflow.com/questions/2422468/how-to-upload-files-to-server-using-jsp-servlet
		writer.append("<form action=\"/FileUploadServlet/upload\" method=\"POST\" enctype=\"multipart/form-data\">\n"); //Modified WM
		//.append("enctype=\"multipart/form-data\">\r\n");                                                              //Commented out WM
		
		writer.append("<input type=\"file\" name=\"fileName1\"/><br/><br/>\r\n");
		//writer.append("<input type=\"file\" name=\"fileName2\"/><br/><br/>\r\n");  //commented out WM
		writer.append("<input type=\"submit\" value=\"Submit\"/>\r\n");
		writer.append("</form>\r\n");
		writer.append("    </body>\r\n").append("</html>\r\n");
	}
	
	//@Override                                                                         //Commented out WM
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
				
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		// gets absolute path of the web application
		String applicationPath = request.getServletContext().getRealPath("");
		// constructs path of the directory to save uploaded file
		String uploadFilePath = applicationPath + File.separator + UPLOAD_DIR;
		// creates upload folder if it does not exists
		File uploadFolder = new File(uploadFilePath);
		if (!uploadFolder.exists()) {
			uploadFolder.mkdirs();
		}
		
		PrintWriter writer = response.getWriter();
		
		// write all files in upload folder
		for (Part part : request.getParts()) {
			if (part != null && part.getSize() > 0) {
				String fileName = part.getSubmittedFileName();
				String contentType = part.getContentType();
				
				// allows only JPEG files to be uploaded                            //Commented out WM
				//if (!contentType.equalsIgnoreCase("image/jpeg")) {                //Commented out WM
				//	continue;                                                       //Commented out WM
				//}                                                                 //Commented out WM
				
				part.write(uploadFilePath + File.separator + fileName);
				
				writer.append("File successfully uploaded to " 
						+ uploadFolder.getAbsolutePath() 
						+ File.separator
						+ fileName
						+ "<br>\r\n");
			}
		}
		
	}
}
