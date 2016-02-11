package rivers.yeah.research.datacollect.weibo.crawler;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import net.sf.json.JSONObject;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import rivers.yeah.research.datacollect.weibo.model.User;
import rivers.yeah.research.datacollect.weibo.model.Weibo;
import rivers.yeah.research.datacollect.weibo.service.UserService;
import rivers.yeah.research.datacollect.weibo.service.WeiboService;
import rivers.yeah.research.datacollect.weibo.utils.AnalyseWeibo;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.WebResponse;


public class ComWeiboSearchCrawler extends AbstractComWeiboCrawler {
	
	public ComWeiboSearchCrawler() throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		super("", "");
		login();
	}
	
	public ComWeiboSearchCrawler(String username, String password) throws FailingHttpStatusCodeException, MalformedURLException, IOException {
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
		return 0;
	}
	
	public Weibo extractOneWeibo(Element element, String domainId){
		Element div_commentinfo = element.select("div.comment_info").first();
		long forwardId = 0;
		if (div_commentinfo != null) {
			Element div_wbinfo = div_commentinfo.select("div").first();
			User ouser = extractUserInfo(div_wbinfo);
			UserService.addUser(ouser);

			long uid = ouser.getUid();
			String content = element.select(".comment_txt").first().text().trim();
			int mediaType = AnalyseWeibo.getMediaType(element.select("div.WB_media_wrap").first());
			
			Element a_pubTime = element.select("div.feed_from.W_textb>a.W_textb").first();
			String pubTime = AnalyseWeibo.getFormulaDate(a_pubTime.text().trim());
			
			String[] nums = AnalyseWeibo.getNums(element.select("div.feed_func>div.feed_action").first());int forwardNum = 0;
			int commentNum = 0;
			int likeNum = 0;
			if (!nums[0].equals("")) {
				forwardNum = Integer.parseInt(nums[0]);
			}
			if (!nums[1].equals("")) {
				commentNum = Integer.parseInt(nums[1]);
			}
			if (!nums[2].equals("")) {
				likeNum = Integer.parseInt(nums[2]);
			}

			long id = 0;
			if (nums[3] != null && !nums[3].equals("")) {
				id = Long.parseLong(nums[3]);
			}
			
			String mid = AnalyseWeibo.Id2Mid(String.valueOf(id));
			forwardId = id;

			Weibo oweibo = new Weibo(id, mid, uid, forwardId, content, mediaType, domainId
					, forwardNum, commentNum, likeNum, pubTime);

			if (0 != id) {
				WeiboService.addWeibo(oweibo);
			}
		}
		Element div_wbinfo = element.select(".content>.feed_content").first();
		if (div_wbinfo == null) {
			return null;
		}
		User user = extractUserInfo(div_wbinfo);
		UserService.addUser(user);

		long id = Long.parseLong(element.select(".WB_feed_detail").first().parent().attr("mid"));
		String mid = AnalyseWeibo.Id2Mid(String.valueOf(id));
		long uid = user.getUid();
		String content = div_wbinfo.select(".comment_txt").first().text().trim();
		int mediaType = AnalyseWeibo.getMediaType(div_wbinfo.select("div.WB_media_wrap").first());
		
		Element a_pubTime = element.select(".content>div.feed_from.W_textb>a.W_textb").first();
		String pubTime = AnalyseWeibo.getFormulaDate(a_pubTime.text().trim());
		
		String[] nums = AnalyseWeibo.getNums(element.select(".feed_list_item>div.feed_action.clearfix").first());
		int forwardNum = 0;
		int commentNum = 0;
		int likeNum = 0;
		if (!nums[0].equals("")) {
			forwardNum = Integer.parseInt(nums[0]);
		}
		if (!nums[1].equals("")) {
			commentNum = Integer.parseInt(nums[1]);
		}
		if (!nums[2].equals("")) {
			likeNum = Integer.parseInt(nums[2]);
		}

		return new Weibo(id, mid, uid, forwardId, content, mediaType, domainId
				, forwardNum, commentNum, likeNum, pubTime);
		
	}
	
	@Override
	public boolean extractWeiboFromPage(String domain, int pageId)
			throws FailingHttpStatusCodeException, MalformedURLException,
			IOException {
		String domainId = URLEncoder.encode(URLEncoder.encode(domain, "utf-8"),"utf-8");
		String url = "http://s.weibo.com/weibo/" + domainId + "?page=" + pageId; 
		WebRequest request = new WebRequest(new URL(url.toString()), HttpMethod.GET);
		WebResponse response = client.loadWebResponse(request);
		String html = response.getContentAsString();
		if (html.contains("islogin']='0")) {
			login();
		}
		int i = html.indexOf("\"pl_weibo_direct\"") - 7;
		if (i < 0) {
			return true;
		}
		int j = html.indexOf("</script>", i) - 1;
		html = html.substring(i, j); 
		JSONObject json = JSONObject.fromObject(html);
		String s = (String)json.get("html");
		Document doc = Jsoup.parse(s);
		
		Elements div_wb = doc.select("div.WB_cardwrap.S_bg2.clearfix");
		for (Element element : div_wb) {
			Weibo weibo = extractOneWeibo(element, domain);
			if (weibo != null) {
				WeiboService.addWeibo(weibo);
			}
		}
		
		return false;
	}

	@Override
	public void crawl(int pages) throws FailingHttpStatusCodeException,
			MalformedURLException, IOException {
		login();
		crawl("刘翔", 2);
	}	
	
	public void crawl(String keyword, int pages) throws FailingHttpStatusCodeException,
			MalformedURLException, IOException {
		for (int i = 1; i <= pages; i++) {
			if (extractWeiboFromPage(keyword, i)) {
				break;
			}
		}
	}

	@Override
	public void crawl(String domain) throws FailingHttpStatusCodeException,
			MalformedURLException, IOException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void crawl() throws FailingHttpStatusCodeException,
			MalformedURLException, IOException {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args) throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		ComWeiboSearchCrawler cwsc = new ComWeiboSearchCrawler();
		cwsc.crawl(2);
	}

	
}
