package rivers.yeah.research.expriment;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;

import rivers.yeah.research.datacollect.weibo.crawler.ComWeiboCommentCrawler;
import rivers.yeah.research.datacollect.weibo.crawler.ComWeiboFaxianCrawler;

public class CrawlData {
	
	private static final ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
	public static void main(String[] args) {
		ses.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				try {
					ComWeiboFaxianCrawler cwfc = new ComWeiboFaxianCrawler();
					cwfc.crawl();
				} catch (FailingHttpStatusCodeException e) {
					e.printStackTrace();
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}, 30, 12 * 60, TimeUnit.MINUTES);
	}
	@Test
	public void crawlAll() throws FailingHttpStatusCodeException, MalformedURLException, IOException{
		ses.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				try {
					System.out.println("a");
//					ComWeiboFaxianCrawler cwfc = new ComWeiboFaxianCrawler();
//					cwfc.crawl();
				} catch (FailingHttpStatusCodeException e) {
					e.printStackTrace();
				}
//				catch (MalformedURLException e) {
//					e.printStackTrace();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
			}
		}, 0, 2, TimeUnit.SECONDS);
		System.out.println();
	}
	
	@Test
	public void crawlFaxian() throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		ComWeiboFaxianCrawler cwfc = new ComWeiboFaxianCrawler();
		cwfc.crawl("102803_ctg1_1388_-_ctg1_1388",7);
		cwfc.crawl("102803_ctg1_2588_-_ctg1_2588",7);
		cwfc.crawl("102803_ctg1_4788_-_ctg1_4788",7);
		cwfc.crawl("102803_ctg1_5588_-_ctg1_5588",7);
		cwfc.crawl("102803_ctg1_5788_-_ctg1_5788",7);
	}
	
	@Test
	public void crawlComment() throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		ComWeiboCommentCrawler cwcc = new ComWeiboCommentCrawler();
		cwcc.crawl("CmF9llI8c",5);
	}
}
