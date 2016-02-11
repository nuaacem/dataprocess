package rivers.yeah.research.datacollect.weibo.crawler;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import net.sf.json.JSONObject;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import rivers.yeah.research.datacollect.weibo.model.Weibo;
import rivers.yeah.research.datacollect.weibo.service.WeiboService;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.WebResponse;


public class ComWeiboUserCrawler extends AbstractComWeiboCrawler {
	
	public ComWeiboUserCrawler() throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		super("", "");
		login();
	}
	
	public ComWeiboUserCrawler(String username, String password) throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		super(username, password);
		login();
	}
	
	@Override
	protected Document getRollingDocument(String domainId, String id, int pageId, int pagebar)
			throws IOException {
		StringBuffer url = new StringBuffer("http://weibo.com/p/aj/mblog/mbloglist?");
		url.append("&domain=").append(domainId)
		   .append("&pre_page=").append(pageId)
		   .append("&page=").append(pageId)
		   .append("&id=").append(id)
		   .append("&pagebar=").append(pagebar);
		WebRequest requestOne = new WebRequest(new URL(url.toString()), HttpMethod.GET);

		WebResponse jsonOne = client.loadWebResponse(requestOne);
		JSONObject jsonObj = JSONObject.fromObject(jsonOne.getContentAsString());
		String data = (String)jsonObj.get("data");
		return Jsoup.parse(data);
	}

	@Override
	protected int getTotalPage(String domain) throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public void crawl(int pages) throws FailingHttpStatusCodeException,	MalformedURLException, IOException {
		login();
		crawl("1642591402", pages);
	}
	
	public void crawl(String oid, int pages) throws FailingHttpStatusCodeException,	MalformedURLException, IOException {
		
		for (int i = 1; i <= pages; i++) {			
			System.out.println(i);
			if (extractWeiboFromPage(oid, i)) {
				break;
			}
		}
	}

	@Override
	public boolean extractWeiboFromPage(String oid, int pageId)
			throws FailingHttpStatusCodeException, MalformedURLException,IOException {
		String url = "http://weibo.com/" + oid + "?page=" + pageId; 
//		HtmlPage page = client.getPage(url);
		WebRequest request = new WebRequest(new URL(url.toString()), HttpMethod.GET);
		WebResponse response = client.loadWebResponse(request);

		String html = response.getContentAsString();
		
		int a = html.indexOf("domain']");
		if (a < 0) {
			return true;
		}
		a += 10;
		int b = html.indexOf("'", a);
		
		String domainId = html.substring(a, b);
		String id = domainId + oid;
		
		if (html.contains("islogin']='0")) {
			login();
		}
		
		int i = html.indexOf(":\"Pl_Official_MyProfileFeed") - 41;
		if (i < 0) {
			return true;
		}
		int j = html.indexOf("</script>", i) - 1;
		html = html.substring(i, j); 
		JSONObject json = JSONObject.fromObject(html);
		String s = (String)json.get("html");
		Document doc = Jsoup.parse(s);
		Elements div_wb = doc.select(".WB_cardwrap.WB_feed_type");
		if (div_wb.size() == 0) {
			return true;
		}
		
		Document doc1 = getRollingDocument(domainId, id, pageId, 0);
		Document doc2 = getRollingDocument(domainId, id, pageId, 1);
		
		div_wb.addAll(doc1.select("div.WB_feed_type"));
		div_wb.addAll(doc2.select("div.WB_feed_type"));
		for (Element element : div_wb) {
			Weibo weibo = extractOneWeibo(element, domainId);
			if (weibo != null) {
				weibo.setUid(Long.parseLong(oid));
				WeiboService.addWeibo(weibo);
			}
		}
		return false;
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
		ComWeiboUserCrawler cwuc = new ComWeiboUserCrawler();
		cwuc.crawl(1);
	}

	

}
