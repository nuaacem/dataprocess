package cn.cem.Crawler;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cn.cem.Dao.UserDao;
import cn.cem.Dao.WeiboDao;
import cn.cem.Bean.User;
import cn.cem.Bean.Weibo;
import cn.cem.Util.DateUtil;
import cn.cem.Util.TextUtil;
import cn.cem.Util.WeiboUtil;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * 微博抓取的类
 * @author HXF
 */
public class WeiboCrawler {

	public static WebClient client = new WebClient();
	
	/**
	 * 对某个微博主题进行爬取
	 * @param keyword
	 * @param pageStart
	 * @param pageEnd
	 * @throws FailingHttpStatusCodeException
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public static void crawlSearch(String keyword, int pageStart, int pageEnd)
			throws FailingHttpStatusCodeException, MalformedURLException,
			IOException {
		String searchString = "http://weibo.cn/search/mblog?hideSearchFrame=&keyword="
				+ TextUtil.toUTF8(keyword) + "&page=";
		for (int i = pageStart; i <= pageEnd; i++) {
			HtmlPage page = client.getPage(searchString + i);
			System.out.println("page:" + i);
			collectInfo(page,keyword);
		}
	}
	
	/**
	 * 在1页中进行内容抓取
	 * @param page
	 * @throws FailingHttpStatusCodeException
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	private static void collectInfo(HtmlPage page,String keyWord)
			throws FailingHttpStatusCodeException, MalformedURLException,
			IOException {

//		 判断是否登录
/*		if (page.getUrl().toString().contains("login.weibo.cn")) {
			WeiboUtil.login("nuaais@sina.cn", "admin123456", client);
		}
*/
		String aString=page.asXml();
		Document pagedoc = Jsoup.parse(aString);

//		获取整页评论的信息，并进行进一步细分。
		Elements rows = pagedoc.getElementsByClass("c");

		if (rows != null && rows.size() > 0) {
			for (int i = 0; i < rows.size(); i++) {
				
				Weibo weibo = new Weibo();
				User user = new User();
				Element aRow = rows.get(i);

//				存入每条微博的id				
				weibo.setWid(aRow.attr("id"));

//				获取该条微博中的微博用户名以及uid，并存入user数据库
				Elements nks = aRow.getElementsByClass("nk");
				if (nks != null && !nks.isEmpty()) {
					Element aUser = nks.get(0);
					WeiboUtil.analysisUser(aUser, user);
					UserDao.addUser(user);
				} else {
					continue;
				}
				
//				开始准备weibo表的数据，首先是uid
				weibo.setUid(user.getUid());
				
//				微博内容
				Elements ctts = aRow.getElementsByClass("ctt");
				if (ctts != null) {
					Element ctt = ctts.get(0);
					weibo.setContent(ctt.text().replaceAll("'", "\'"));
				}
				
//				微博点赞数、转发数、评论数
				Elements weiboNums=aRow.select("div>a");
				
				ArrayList<Integer> list1=new ArrayList<Integer>();
				
				for (int j = 0; j < weiboNums.size(); j++) {
					
					String href=(weiboNums.get(j)).attr("href");
					if (href.contains("attitude") ||href.contains("repost") ||href.contains("comment")) {
						list1.add(j);
					}
					
				}
				if (list1.size()==4) {
					list1.remove(0);
				}
				
				Element zanNum=weiboNums.get(list1.get(0));
				Element zfNum=weiboNums.get(list1.get(1));
				Element cmtNum=weiboNums.get(list1.get(2));
				
				weibo.setZanNum(TextUtil.GetOnlyNum(zanNum.text()));
				weibo.setZfNum(TextUtil.GetOnlyNum(zfNum.text()));
				weibo.setCmtNum(TextUtil.GetOnlyNum(cmtNum.text()));
				
//				微博评论地址
				String cmtUrl=cmtNum.attr("href");
				weibo.setCmtUrl(cmtUrl);
				
//				发微博的时间
				String time = aRow.getElementsByClass("ct").first().text();
				if (time != null && time != "") {
					String pubTime = DateUtil.getFormulaDate(time);
					weibo.setPubTime(pubTime);
				}

				WeiboDao.addWeibo(weibo,keyWord);
			}
		}
	}
	
	
	/**
	 * 程序入口，抓取评论，作为测试
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			crawlSearch(" ",1,3);
		} catch (FailingHttpStatusCodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
