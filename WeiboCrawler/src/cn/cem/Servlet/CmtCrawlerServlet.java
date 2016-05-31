package cn.cem.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.cem.Crawler.CmtCrawler;
import cn.cem.Util.DateUtil;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;

/**
 * 微博抓取接口
 * @author HXF
 *
 */
public class CmtCrawlerServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("application/json;charset=UTF-8");

		String starttimeString=DateUtil.formatDate(new Date());
		String keyWord=req.getSession().getAttribute("keyWord").toString();

		try {
			CmtCrawler.crawlComments(keyWord);
			
			String endtimeString=DateUtil.formatDate(new Date());
			String time=starttimeString+"  -  "+endtimeString;			
			PrintWriter pw=resp.getWriter();
			pw.println("{\"jsonData\":\"" + time + "\"}");
			
		} catch (FailingHttpStatusCodeException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
