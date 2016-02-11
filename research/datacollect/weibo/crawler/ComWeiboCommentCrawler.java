package rivers.yeah.research.datacollect.weibo.crawler;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import net.sf.json.JSONObject;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import rivers.yeah.research.datacollect.weibo.model.Comment;
import rivers.yeah.research.datacollect.weibo.model.User;
import rivers.yeah.research.datacollect.weibo.service.CommentService;
import rivers.yeah.research.datacollect.weibo.utils.AnalyseWeibo;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.WebResponse;


public class ComWeiboCommentCrawler extends AbstractComWeiboCrawler {
	
	public ComWeiboCommentCrawler() throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		super("", "");
		login();
	}
	
	public ComWeiboCommentCrawler(String username, String password) throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		super(username, password);
		login();
	}
	
	@Override
	protected Document getRollingDocument(String domainId, String id,
			int pageId, int pagebar) throws IOException {
		return null;
	}
	

	@Override
	protected int getTotalPage(String domain) throws IOException {
		WebRequest request = new WebRequest(new URL(domain), HttpMethod.GET);
		WebResponse response = client.loadWebResponse(request);
		String html = response.getContentAsString();
		int csPos = html.lastIndexOf(":");
		if (csPos < 0) {
			return 0;
		}
		int cePos = html.indexOf("}", csPos);
		String countText = html.substring(csPos + 1, cePos);
		if (countText.equals("null") || countText.equals("")) {
			return 0;
		}
		int count = Integer.parseInt(countText);
		if (count == 0) {
			return 0;
		}		
		int tsPos = html.indexOf("totalpage\":");
		if (tsPos < 0) {
			return 0;
		}
		return Integer.parseInt(html.substring(tsPos + 11, html.indexOf(",", tsPos)));
	}
	
	public Comment extractOneComment(Element element) {
		long id = Long.parseLong(element.attr("comment_id"));
		Element div_wbtext = element.select(".WB_text").first();
		User user = extractUserInfo(div_wbtext);
		long uid = user.getUid();
		String content = div_wbtext.ownText().substring(1).replaceAll("\\'", "\\\\'");
		Element div_wbfunc = element.select(".WB_func").first();
		String commentTime = AnalyseWeibo.getFormulaDate(div_wbfunc.select(".WB_from").first().text());
		int heat = 0;
		String likeText = div_wbfunc.select("em").last().text();
		if (!likeText.equals("")) {
			heat = Integer.parseInt(likeText);
		}
		return new Comment(id, uid, content, heat, commentTime);
	}
	
	public boolean extractComment(String mid, String filter, int pageId) throws IOException {
		
		long weiboId = Long.parseLong(AnalyseWeibo.mid2Id(mid));
		StringBuilder url = new StringBuilder("http://weibo.com/aj/v6/comment/big?ajwvr=6");
		url.append("&id=").append(weiboId)
		  .append("&filter=").append(filter)
		  .append("&page=").append(pageId);
		WebRequest request = new WebRequest(new URL(url.toString()), HttpMethod.GET);
		WebResponse response = client.loadWebResponse(request);
		String html = response.getContentAsString();
		JSONObject json = JSONObject.fromObject(html);
		JSONObject data = (JSONObject)json.get("data");
		html = (String)data.get("html");
		Document doc = Jsoup.parse(html);
		Elements div_listli = doc.select(".list_box>.list_ul>.list_li");
		if (div_listli.size() == 0 || div_listli == null) {
			return false;
		}
		for (Element element : div_listli) {
			Comment comment = extractOneComment(element);
			if (comment != null) {
				comment.setWeiboId(weiboId);
				comment.setMid(mid);
				CommentService.addComment(comment);
			}
		}
		return false;
	}
	
	@Override
	public boolean extractWeiboFromPage(String domain, int pageId)
			throws FailingHttpStatusCodeException, MalformedURLException,
			IOException {
		long weiboId = Long.parseLong(AnalyseWeibo.mid2Id(domain));
		String url = "http://weibo.com/aj/v6/comment/big?ajwvr=6&id=" + weiboId + "&filter=hot&page=" + pageId; 
		WebRequest request = new WebRequest(new URL(url.toString()), HttpMethod.GET);
		WebResponse response = client.loadWebResponse(request);
		String html = response.getContentAsString();
		if (html.contains("count\":0}")) {
			url = "http://weibo.com/aj/v6/comment/big?ajwvr=6&id=" + weiboId + "&filter=0&page=" + pageId; 
			request = new WebRequest(new URL(url.toString()), HttpMethod.GET);
			response = client.loadWebResponse(request);
			html = response.getContentAsString();
		}
		if (html.contains("count\":0}")) {
			return true;
		}
		JSONObject json = JSONObject.fromObject(html);
		JSONObject data = (JSONObject)json.get("data");
		html = (String)data.get("html");
		Document doc = Jsoup.parse(html);
		Elements div_listli = doc.select(".list_box>.list_ul>.list_li");
		
		for (Element element : div_listli) {
			Comment comment = extractOneComment(element);
			if (comment != null) {
				comment.setWeiboId(weiboId);
				CommentService.addComment(comment);
			}
		}
		return false;
	}

	@Override
	public void crawl(int pages) throws FailingHttpStatusCodeException,
			MalformedURLException, IOException {
		BufferedReader br = new BufferedReader(new FileReader(new File("./cfg/comment_crawl_list.txt")));
		String line = br.readLine();
		int i = 1;
		while (line != null && !line.equals("")) {
			System.out.println("NO." + i++ + ",Mid:" + line);
			crawl(line,pages);
			line = br.readLine();
		}
		br.close();
	}
	
	public void crawl(String domain, int pages) throws FailingHttpStatusCodeException,
			MalformedURLException, IOException {
		StringBuilder url = new StringBuilder("http://weibo.com/aj/v6/comment/big?ajwvr=6&filter=hot");
		url.append("&id=").append(AnalyseWeibo.mid2Id(domain));
		int totalpage = getTotalPage(url.toString());

		String filter = "";
		if (0 == totalpage) {
			filter = "0";
			totalpage = getTotalPage("http://weibo.com/aj/v6/comment/big?ajwvr=6&id=" + AnalyseWeibo.mid2Id(domain));
		} else {
			filter = "hot";
		}
		if (pages > totalpage) {
			pages = totalpage;
		}
		System.out.println("page to crawl = " + pages);

		for (int i = 1; i <= pages; i++) {
			System.out.println("page:" + i);
			if (extractComment(domain, filter, i)) {
				return;
			}
		}
	}
	
	@Override
	public void crawl(String domain) throws FailingHttpStatusCodeException,
			MalformedURLException, IOException {
		StringBuilder url = new StringBuilder("http://weibo.com/aj/v6/comment/big?ajwvr=6&filter=hot");
		url.append("&id=").append(AnalyseWeibo.mid2Id(domain));
		int totalpage = getTotalPage(url.toString());
		String filter = "";
		if (0 == totalpage) {
			filter = "0";
			totalpage = getTotalPage("http://weibo.com/aj/v6/comment/big?ajwvr=6&id=" + AnalyseWeibo.mid2Id(domain));
		} else {
			filter = "hot";
		}
		System.out.println("totalpage = " + totalpage);
		for (int i = 1; i <= totalpage; i++) {
			System.out.println("page:" + i);
			if (extractComment(domain, filter, i)) {
				return;
			}
		}
	}
	
	/**
	 * 根据文件中的MID列表爬取评论
	 */
	public void crawl() throws FailingHttpStatusCodeException,
			MalformedURLException, IOException {
		BufferedReader br = new BufferedReader(new FileReader(new File("./cfg/comment_crawl_list.txt")));
		String line = br.readLine();
		int i = 1;
		while (line != null && !line.equals("")) {
			System.out.println("NO." + i++ + ",Mid:" + line);
			crawl(line);
			line = br.readLine();
		}
		br.close();
	}
	
	public static void main(String[] args) throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		ComWeiboCommentCrawler cwcc = new ComWeiboCommentCrawler();
		
		cwcc.crawl();
	}

}
