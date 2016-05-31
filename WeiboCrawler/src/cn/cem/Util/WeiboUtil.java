package cn.cem.Util;

import java.io.IOException;
import java.net.MalformedURLException;

import org.jsoup.nodes.Element;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlCheckBoxInput;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

import cn.cem.Bean.User;

/**
 * @author HXF 微博爬虫的一些公用方法
 */
public class WeiboUtil {

	/**
	 * 将userName和uid保存到user中
	 * @param aUser
	 * @param user
	 */
	public static void analysisUser(Element aUser, User user) {
		String userName = aUser.text();
		if (userName != null && userName != "") {
			user.setName(userName);
		}
		String userUrl = aUser.attr("href");
//		<a class="nk" href="http://weibo.cn/13991799?vt=4&amp;PHPSESSID=">环球搞笑趣闻</a> 截掉前16个字符，剩下给str
		if (userName != null && userName != "") {
			user.setUid(userUrl.substring(16));
		}
	}
	
	/**
	 * 模拟浏览器，用户登录
	 * @param username
	 * @param password
	 * @return 登录是否成功的状态
	 * @throws FailingHttpStatusCodeException
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public static boolean loginWap(String username, String password,WebClient client)
			throws FailingHttpStatusCodeException, MalformedURLException,
			IOException {

		client.getOptions().setJavaScriptEnabled(false);
		client.getOptions().setCssEnabled(false);
		boolean state = false;
		HtmlPage page = client.getPage("http://login.weibo.cn/login/");

		HtmlTextInput ln = (HtmlTextInput) page.getElementByName("mobile");
		page.setFocusedElement((HtmlElement) page.getElementByName("mobile"));
		page.tabToNextElement();
		HtmlPasswordInput pwd = (HtmlPasswordInput) page.tabToNextElement();

		ln.setText(username);
		pwd.setText(password);

		HtmlCheckBoxInput ckb = (HtmlCheckBoxInput) page
				.getElementByName("remember");
		ckb.setChecked(true);

		HtmlSubmitInput btn = (HtmlSubmitInput) page.getElementByName("submit");
		HtmlPage page2 = btn.click();
		if (!page2.getUrl().toString().contains("login.weibo.cn")) {
			state = true;
		}
		return state;
	}
	
	public static boolean login(String username, String password,WebClient client)
			throws FailingHttpStatusCodeException, MalformedURLException,
			IOException {
		client.getOptions().setJavaScriptEnabled(false);
		client.getOptions().setCssEnabled(false);
		boolean state = false;
		HtmlPage page = client.getPage("http://login.weibo.cn/login/");
		
				return false;
	}
	
}
