package nuaa.ggx.pos.dataprocess.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 系统常量类
 * @author KOC-RY
 *
 */
public class Constants {
	public final static Boolean IS_HOUR = false;
	public final static Boolean IS_DAY = true;
	public final static short IS_SUBJECT = 1;
	public final static short IS_KEYWORD = 0;
	public static ExecutorService WEIBO_CRAWLER_POOL = Executors
			.newFixedThreadPool(4);
}
