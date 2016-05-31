package cn.cem.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.cem.Util.CookieUtil;
import cn.cem.Util.XmlReaderUtil;

/**
 * 用户名与密码检验
 * @author HXF
 *
 */
public class UserCheckServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		resp.setContentType("application/json;charset=utf-8");
		
		Cookie cookie;
		boolean flag = false;
		int hour=1;
		PrintWriter pw = resp.getWriter();
		String VerWrong="codewrong";
		String username=req.getParameter("username");
		String password=req.getParameter("password");
		String code=req.getParameter("code");
		String isAutoLogin=req.getParameter("isAutoLogin");
		
		HttpSession session = req.getSession();
		String rand = (String) session.getAttribute("verificationCode");
		
		if (code.equals(rand) == false) {
			pw.println("{\"jsonData\":\"" + VerWrong + "\"}");
		}else {
			try {
				String filePath = this.getClass().getResource("/").toURI().getPath() + "ower.xml";
				ArrayList<HashMap<String, Object>> list=XmlReaderUtil.ReadOwer(filePath);
				
				for(HashMap<String, Object> map:list){
					Object oweruser=map.get("username");
					Object owerpassword=map.get("password");
					if (owerpassword.equals(password) && oweruser.equals(username)) {
						flag=true;
					}
				}

				pw.println("{\"jsonData\":\"" + flag + "\"}");
				if (true==flag) {
					cookie = new Cookie("autoLoginUser", "true"); 
					if (isAutoLogin.equals("checked")) {
						hour=336;
					}
					CookieUtil.createMy(cookie, hour, resp);
				}
			} catch (Exception e) {
				
			}
		}
	}
}
