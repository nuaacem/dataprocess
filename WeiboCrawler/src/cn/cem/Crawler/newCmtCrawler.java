package cn.cem.Crawler;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cn.cem.Bean.Comment;
import cn.cem.Bean.User;
import cn.cem.Bean.Weibo;
import cn.cem.Dao.CommentDao;
import cn.cem.Dao.RelationDao;
import cn.cem.Dao.UserDao;
import cn.cem.Dao.WeiboDao;
import cn.cem.Util.DateUtil;
import cn.cem.Util.WeiboUtil;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class newCmtCrawler {
//	public static WebClient client = new WebClient();

	/**
	 * 从weibo表中，前往评论页面
	 * @throws FailingHttpStatusCodeException
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public static void crawlComments(String keyWord) throws FailingHttpStatusCodeException, MalformedURLException, IOException {

		List<Weibo> weiboList = WeiboDao.getWeibos(keyWord);
		
		for (int i = 0; i < weiboList.size(); i++) {
			Weibo weibo = weiboList.get(i);
			String cmtUrl = weibo.getCmtUrl();
//			HtmlPage page;
			String page;
			
			if (cmtUrl !=null && !cmtUrl.equals("null")) {
				cmtUrl = cmtUrl.split("\\?")[0];
//				page = client.getPage(cmtUrl);
				page=httpClient.CookieCrawler(cmtUrl);
				collectComment(page,cmtUrl, weibo.getId(), weibo.getUid());
			}
			
			WeiboDao.updateVisitTime(weibo.getId(), DateUtil.formatDate(new Date()));
		}
	}
	/**
	 * 实现原文评论的下一页，因为1页评论最多10条，可能出现下一页的情况
	 * @param page
	 * @param weiboId
	 * @param uid
	 * @throws FailingHttpStatusCodeException
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	private static void collectComment(String page,String pageUrl, int weiboId, String uid)
			throws FailingHttpStatusCodeException, MalformedURLException,
			IOException {

//		String pageUrl = page.getUrl().toString();

/*		if (pageUrl.contains("login.weibo.cn")) {
			WeiboUtil.login("nuaais@sina.cn", "admin123456",client);
		}*/
		System.out.println("Comment Page : 1");
		Document pagedoc = Jsoup.parse(page);
		extractComments(pagedoc, weiboId, uid);

		Element pagelist = pagedoc.getElementById("pagelist");
		if (pagelist != null) {
			int i = 2;
			boolean flag = true;
			while (true == flag) {
				
				System.out.println("Comment Page : " + i);
				String page2 = httpClient.CookieCrawler(pageUrl + "?page=" + i);
				Document pagedoc2 = Jsoup.parse(page2);
				extractComments(pagedoc2, weiboId, uid);
	
				Element pagelist2 = pagedoc2.getElementById("pagelist");
				if (pagelist2 != null) {
					Elements aTags = pagelist2.getElementsByTag("a");
					if (!aTags.first().text().equals("下页") || i>9) {
						flag = false;
					}
				}
				i++;
			}
		}
	}
	
	/**
	 * 爬取页面中，comment表所需的private String uid;private int weiboId;private String divId;private String content;
	 * @param pagedoc
	 * @param weiboId
	 * @param pubUid
	 */
	private static void extractComments(Document pagedoc, int weiboId,
			String pubUid) {
		Elements rows = pagedoc.getElementsByClass("c");

		if (rows != null && rows.size() > 0) {
			for (int i = 0; i < rows.size(); i++) {
				Element aRow = rows.get(i);
				if (!aRow.hasAttr("id") || aRow.attr("id").startsWith("M")) {
					continue;
				}
				Comment comment = new Comment();
				comment.setWeiboId(weiboId);
				comment.setWid(aRow.attr("id"));
				Element userATag = aRow.getElementsByTag("a").get(0);
				if (userATag != null) {
					String uid = userATag.attr("href");
					if (uid.startsWith("/u/")) {
						uid = uid.substring(3);
					} else {
						uid = uid.substring(1);
					}

					String name = userATag.text();
					User user = new User();
					user.setUid(uid);
					user.setName(name);
					UserDao.addUser(user);
					comment.setUid(uid);
					RelationDao.buildRelation(pubUid, uid);
				}
				Element cmt = aRow.getElementsByClass("ctt").get(0);
				if (cmt != null) {
					String content = cmt.text().replaceAll("'", "\'");
					comment.setContent(content);
				}
				CommentDao.addComment(comment);
			}
		}
	}
	 
	/**
	 * 程序入口，抓取评论，作为测试
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			crawlComments("房价");
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
