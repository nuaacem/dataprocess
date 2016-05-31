package cn.cem.Servlet;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.cem.Util.TextUtil;

public class FileDownServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public FileDownServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String filename=TextUtil.httpToUTF8(request.getParameter("filename"));
		String dir=request.getParameter("dir");
        response.setContentType(getServletContext().getMimeType(filename));  
        response.setHeader("contentType", "text/html; charset=utf-8");
        response.setHeader("Content-Disposition","attachment;filename="+filename);  
		
    	String fullFileName = request.getSession().getServletContext().getRealPath("/save/"+dir+"/"+filename);
        InputStream in = new FileInputStream(fullFileName);  
        OutputStream out = response.getOutputStream();
         
        int b;
        try {
        	while((b=in.read())!= -1)  
            {  
                out.write(b);
            }  
            in.close();  
            out.close(); 
		} catch (Exception e) {
			
		}
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
