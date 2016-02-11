package rivers.yeah.research.datacollect.weibo.crawler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import rivers.yeah.research.datacollect.weibo.dao.WeiboDao;
import rivers.yeah.research.datacollect.weibo.model.User;
import rivers.yeah.research.datacollect.weibo.model.Weibo;
import rivers.yeah.research.datacollect.weibo.service.UserService;
import rivers.yeah.research.datacollect.weibo.service.WeiboService;
import rivers.yeah.research.datacollect.weibo.utils.AnalyseWeibo;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlCheckBoxInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

public abstract class AbstractComWeiboCrawler {
	
	public static WebClient client = new WebClient(BrowserVersion.CHROME);
	private String username;
	private String password;
	
	private static Logger logger = Logger.getLogger(AbstractComWeiboCrawler.class);
	
	static {
		client.setAjaxController(new NicelyResynchronizingAjaxController());
		client.getOptions().setJavaScriptEnabled(true);
		client.getOptions().setCssEnabled(false);
		client.getOptions().setThrowExceptionOnScriptError(false);
		client.getOptions().setThrowExceptionOnFailingStatusCode(false);
	}
	
	public AbstractComWeiboCrawler(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	protected boolean login() throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		return login(username, password);
	}
	
	@SuppressWarnings("rawtypes")
	protected static boolean login(String username,String password)
			throws FailingHttpStatusCodeException, MalformedURLException, IOException{
    	HtmlPage page = client.getPage("http://login.sina.com.cn/");
    	if (page.getUrl().toString().equals("http://login.sina.com.cn/")) {
    		HtmlTextInput ln = (HtmlTextInput) page.getElementByName("username");
    		HtmlPasswordInput pwd = (HtmlPasswordInput)page.getElementByName("password");            
            ln.setText("nuaais@sina.cn");
            pwd.setText("admin123456");
            page.setFocusedElement((HtmlCheckBoxInput)page.getElementByName("safe_login"));
            HtmlAnchor a  = (HtmlAnchor)page.tabToNextElement();
            
			Iterator i = a.getChildElements().iterator();
            HtmlSubmitInput btn = (HtmlSubmitInput)i.next();
            btn.click();
            HtmlPage page2 = client.getPage("http://login.sina.com.cn");
            if (page2.getUrl().toString().contains("http://login.sina.com.cn/member")) {
            	return true;
            } else {
				return false;
			}
    	} else {
			return true;
		}
	}
	
	public User extractUserInfo(Element wbInfo) {
		Elements as = wbInfo.select("a");
		
		String nickname = as.first().text();
		String uidText = as.first().attr("usercard");
		int endPos = uidText.indexOf("&");
		long uid = 0;
		if (endPos >= 3) {
			uid = Long.parseLong(uidText.substring(3,endPos));
		} else {
			uid = Long.parseLong(uidText.substring(3));
		}
		int approveState = 0;
		int vipLevel = 0;
		int darenLevel = 0;
		int i = as.size();
		for (int j = 1; j < i; j++) {
			Element a = as.get(j);
			String title = a.attr("title");
			if (title.equals("微博会员")) {
				String aclass = "";
				Element i_element = a.select("em").first();
				if (i_element == null) {
					i_element = a.select("i").first();
				}
				aclass = i_element.attr("class");
				vipLevel = Integer.parseInt(aclass.substring(aclass.length() - 1, aclass.length()));
			} else if (title.equals("微博个人认证")) {
				approveState = 1;
			} else if (title.equals("微博机构认证")) {
				approveState = 2;
			} else if (title.equals("微博达人")) {
				darenLevel = 1;
			}
		}
		return new User(uid, nickname, approveState, vipLevel, darenLevel, 0, 0);
	}
	
	public Weibo extractOneWeibo(Element element, String domainId) {
		
		Element div_wbdetail = element.select("div.WB_detail").first();
		if (div_wbdetail == null) {
			return null;
		}
		
		Element div_wbfeedexpand = div_wbdetail.select("div.WB_feed_expand").first();
		long forwardId = 0;
		
		if (div_wbfeedexpand != null) {
			Element div_wbinfo = div_wbfeedexpand.select(".WB_info").first();
			User ouser = extractUserInfo(div_wbinfo);
			UserService.addUser(ouser);
			
			long id = Long.parseLong(element.attr("omid"));
			String mid = AnalyseWeibo.Id2Mid(String.valueOf(id));

			forwardId = id;
			
			long uid = ouser.getUid();
			String content = div_wbfeedexpand.select(".WB_text").first().text().trim().replaceAll("\\'", "\\\\'");
			int mediaType = AnalyseWeibo.getMediaType(div_wbfeedexpand.select("div.WB_media_wrap").first());
			
			Element a_pubTime = div_wbfeedexpand.select("div.WB_from>a").first();
			String pubTime = a_pubTime.attr("tittle");
			if (pubTime == null || pubTime.equals("")) {
				pubTime = AnalyseWeibo.getFormulaDate(a_pubTime.text().trim());
			}
			
			String[] nums = AnalyseWeibo.getNums(div_wbfeedexpand.select("div.WB_func>div.WB_handle").first());int forwardNum = 0;
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

			Weibo oweibo = new Weibo(id, mid, uid, 0l, content, mediaType, domainId
					, forwardNum, commentNum, likeNum, pubTime);
			WeiboService.addWeibo(oweibo);
		}
		
		Element div_wbinfo = element.select("div.WB_detail>div.WB_info").first();
		long uid = 0;
		if (div_wbinfo != null) {
			User user = extractUserInfo(div_wbinfo);
			UserService.addUser(user);
			uid = user.getUid();
		}
		
		long id = Long.parseLong(element.attr("mid"));
		String mid = AnalyseWeibo.Id2Mid(String.valueOf(id));

		String content = div_wbdetail.select("div.WB_text").first().text().trim().replaceAll("\\'", "\\\\'");
		
		int mediaType = AnalyseWeibo.getMediaType(div_wbdetail.select("div.WB_media_wrap").first());
		
		Element a_pubTime = div_wbdetail.select("div.WB_from>a").first();
		String pubTime = a_pubTime.attr("title");
		if (pubTime == null || pubTime.equals("")) {
			pubTime = AnalyseWeibo.getFormulaDate(a_pubTime.text().trim());
		}
		
		String[] nums = AnalyseWeibo.getNums(element.select("div.WB_feed_handle>div.WB_handle").first());
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
	
	protected abstract Document getRollingDocument(String domainId,String id, int pageId, int pagebar) throws IOException;
	
	public abstract boolean extractWeiboFromPage(String domain, int pageId)
			throws FailingHttpStatusCodeException, MalformedURLException, IOException;
	
	protected abstract int getTotalPage(String domain) throws IOException;
	
	public abstract void crawl() throws FailingHttpStatusCodeException, MalformedURLException, IOException;
	
	public abstract void crawl(int pages) throws FailingHttpStatusCodeException, MalformedURLException, IOException;
	
	public abstract void crawl(String domain) throws FailingHttpStatusCodeException, MalformedURLException, IOException;
	
	public abstract void crawl(String domain, int pages) throws FailingHttpStatusCodeException, MalformedURLException, IOException;
	
	public static void main(String[] args) throws IOException, InterruptedException {
		login("", "");	
		WebRequest request = new WebRequest(new URL("http://weibo.com/aj/v6/comment/big?ajwvr=6&id=3845743456318542&filter=hot"), HttpMethod.GET);
		FileWriter fw = new FileWriter(new File("out.txt"));
		WebResponse jsonTwo = client.loadWebResponse(request);
		JSONObject jsonObj = JSONObject.fromObject(jsonTwo.getContentAsString());
		JSONObject jsonObj3 = (JSONObject)jsonObj.get("data");
		String data = (String)jsonObj3.get("html");
		fw.write(Jsoup.parse(data).toString());
		fw.close();
	}
}
