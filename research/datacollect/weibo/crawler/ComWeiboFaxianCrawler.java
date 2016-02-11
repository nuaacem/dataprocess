package rivers.yeah.research.datacollect.weibo.crawler;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import rivers.yeah.research.datacollect.weibo.model.Domain;
import rivers.yeah.research.datacollect.weibo.model.Weibo;
import rivers.yeah.research.datacollect.weibo.service.DomainService;
import rivers.yeah.research.datacollect.weibo.service.WeiboService;
import rivers.yeah.research.datacollect.weibo.utils.Constants;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.WebResponse;


public class ComWeiboFaxianCrawler extends AbstractComWeiboCrawler {
	
	private static Logger logger = Logger.getLogger(ComWeiboFaxianCrawler.class);

	public ComWeiboFaxianCrawler() throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		super("", "");
		login();
	}
	
	public ComWeiboFaxianCrawler(String username, String password) throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		super(username, password);
		login();
	}
	
	@Override
	protected Document getRollingDocument(String domainId, String id,int pageId, int pagebar) 
			throws IOException {
		StringBuffer url = new StringBuffer("http://d.weibo.com/p/aj/v6/mblog/mbloglist?ajwvr=6");
		url.append("&domain=").append(domainId)
		   .append("&pre_page=").append(pageId)
		   .append("&page=").append(pageId)
		   .append("&pl_name=Pl_Core_MixedFeed__5&id=").append(id)
		   .append("&feed_type=1&pagebar=").append(pagebar);
		WebRequest requestOne = new WebRequest(new URL(url.toString()), HttpMethod.GET);

		WebResponse jsonOne = client.loadWebResponse(requestOne);
		JSONObject jsonObj = JSONObject.fromObject(jsonOne.getContentAsString());
		String data = (String)jsonObj.get("data");
		return Jsoup.parse(data);
	}
	
	@Override
	protected int getTotalPage(String domain) throws IOException {
		StringBuffer url = new StringBuffer("http://d.weibo.com/p/aj/v6/mblog/mbloglist?ajwvr=6&pl_name=Pl_Core_MixedFeed__5&pre_page=1&page=1&pagebar=1&feed_type=1");
		url.append("&domain=").append(domain).append("&id=").append(domain);
		WebRequest request = new WebRequest(new URL(url.toString()), HttpMethod.GET);
		WebResponse response = client.loadWebResponse(request);
		String html = response.getContentAsString();
		int sPos = html.indexOf("countPage=");
		if (sPos < 0) {
			response = client.loadWebResponse(request);
			html = response.getContentAsString();
			sPos = html.indexOf("countPage=");
			if (sPos < 0) {
				return 0;
			}
		}
		sPos += 10;
		int ePos = html.indexOf("\\", sPos);
		return Integer.parseInt(html.substring(sPos,ePos));
	}
	
	@Override
	public boolean extractWeiboFromPage(String domain, int pageId)
			throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		
		String url = "http://d.weibo.com/" + domain + "?page=" + pageId; 
		WebRequest request = new WebRequest(new URL(url.toString()), HttpMethod.GET);
		WebResponse response = client.loadWebResponse(request);
		String html = response.getContentAsString();
		if (html.contains("islogin']='0")) {
			login();
		}
		int i = html.indexOf(":\"Pl_Core_MixedFeed") - 41;
		if (i < 0) {
			return false;
		}
		int j = html.indexOf("</script>", i) - 1;
		html = html.substring(i, j); 
		JSONObject json = JSONObject.fromObject(html);
		String s = (String)json.get("html");
		Document doc = Jsoup.parse(s);
		Elements div_wb = doc.select(".WB_cardwrap.WB_feed_type");
		
		if (div_wb.size() == 0) {
			return false;
		}
		
		Document doc1 = getRollingDocument(domain, domain, pageId, 0);
		Document doc2 = getRollingDocument(domain, domain, pageId, 1);
		
		div_wb.addAll(doc1.select(".WB_feed_type"));
		div_wb.addAll(doc2.select(".WB_feed_type"));
		for (Element element : div_wb) {
			Weibo weibo = extractOneWeibo(element, domain);
			if (weibo != null) {
				WeiboService.addWeibo(weibo);
			}
		}
		return true;
	}
	
	public List<String> crawlCategory() throws IOException {
		
		List<String> categories = new ArrayList<String>(38);
		
		StringBuffer url = new StringBuffer("http://d.weibo.com/");
		WebRequest request = new WebRequest(new URL(url.toString()), HttpMethod.GET);

		WebResponse response = client.loadWebResponse(request);
		String html = response.getContentAsString();
		if (html.contains("islogin']='0")) {
			login();
		}
		int i = html.indexOf("pl.content.miniTab") - 7;
		if (i < 0) {
			return categories;
		}
		int j = html.indexOf("</script>", i) - 1;
		html = html.substring(i, j);
		html = (String)JSONObject.fromObject(html).get("html");
		Document doc = Jsoup.parse(html);
		Elements elements = doc.select(".tlink");
		for (Element element : elements) {
			String href = element.attr("href");
			int endDomain = href.indexOf("?");
			categories.add(href.substring(1, endDomain));
			Domain domain = new Domain(href.substring(1, endDomain), element.text().trim(),
					new Timestamp(System.currentTimeMillis()));
			DomainService.addDomain(domain);
		}
		
		return categories;
	}
	
	@Override
	public void crawl(int pages) throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		int j = Constants.CATEGORIES_ARRAY.length;
		for (int i = 1; i < j; i++) {
			crawl(String.valueOf(i), pages);
		}
	}
	
	@Override
	public void crawl() throws FailingHttpStatusCodeException,
			MalformedURLException, IOException {
		List<String> categories = DomainService.getCategories();
		int j = categories.size();
		logger.info("Domian num:" + j);
		for (int i = 0; i < j; i++) {
			int pages = getTotalPage(categories.get(i));
			for (int k = 0; k < 3; k++) {
				if (pages < 1) {
					pages = getTotalPage(categories.get(i));
				} else {
					break;
				}
			}
			logger.info(i + ". domain:" + categories.get(i) + ", total pages : " + pages);
			crawl(categories.get(i), pages);
		}
	}
	
	@Override
	public void crawl(String domain) throws FailingHttpStatusCodeException,
			MalformedURLException, IOException {
		
		int pages = getTotalPage(domain);
		logger.info("domain:" + domain + ", total pages : " + pages);
		crawl(domain, pages);
	}
	
	public void crawl(String domain, int pages) throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		for (int i = 1; i <= pages; i++) {			
			logger.info("page:"+i);
			for (int j = 0; j < 3; j++) {
				if (extractWeiboFromPage(domain, i)) {
					break;
				} 
			}
		}
	}
	
	public static void main(String[] args) throws IOException, InterruptedException {
//		ComWeiboFaxianCrawler cfc = new ComWeiboFaxianCrawler();
//		cfc.crawlCategory();
//		System.out.println(filterOffUtf8Mb4("注意保暖，别冻到咯 (⁎⁍̴̛ᴗ⁍̴̛⁎)！ 务必把我打包送过去，"));
 
	}

}
