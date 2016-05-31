package cn.cem.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.cem.Crawler.newWeiboCrawler;
import cn.cem.Util.DateUtil;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;

/**
 * @author HXF
 * 
 */
public class CrawlerServlet extends HttpServlet {

	private static final long serialVersionUID = 6916644166824320981L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		resp.setContentType("application/json;charset=UTF-8");
		
		String keyWord = req.getParameter("keyWord");
		String startpg = req.getParameter("start");
		String endpg = req.getParameter("end");
		String time =req.getParameter("time");
		String wTime =req.getParameter("wTime");
		
		int hour=Integer.parseInt(time);
		int day=Integer.parseInt(wTime);
		req.getSession().setAttribute("keyWord", keyWord);
		
//		Timer timer = new Timer(); 
//	    timer.schedule(new CmtTask(keyWord), 3600 * 1000* hour);
//		timer.schedule(new WeiboTask(keyWord), 3600 * 1000* 24, 3600 * 1000* 24*day);
		
		int start = Integer.parseInt(startpg);
		int end = Integer.parseInt(endpg);
		
		String stime=DateUtil.formatDate(new Date());
		System.out.println("程序开始时间："+stime);
		try {
			newWeiboCrawler.crawlSearch(keyWord, start, end);
			
			String etime=DateUtil.formatDate(new Date());
			String period=stime+"  -  "+etime;			
			PrintWriter pw=resp.getWriter();
			pw.println("{\"jsonData\":\"" + period + "\"}");
			
		} catch (FailingHttpStatusCodeException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
