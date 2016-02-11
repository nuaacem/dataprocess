package rivers.yeah.research.datacollect.weibo.service;

import org.junit.Test;

import rivers.yeah.research.datacollect.weibo.dao.WeiboDao;
import rivers.yeah.research.datacollect.weibo.dao.WeiboDetailDao;
import rivers.yeah.research.datacollect.weibo.model.Weibo;
import rivers.yeah.research.datacollect.weibo.model.WeiboDetail;

public class WeiboService {
	
	private static final WeiboDao weiboDao = new WeiboDao();
	private static final WeiboDetailDao weiboDetailDao = new WeiboDetailDao();
	
	public static void addWeibo(Weibo weibo) {		
		weiboDao.saveOrUpdate(weibo);
		weiboDetailDao.save(new WeiboDetail(weibo.getId(), weibo.getForwardNum(),
				weibo.getCommentNum(), weibo.getLikeNum(), weibo.getModifyTime()));
	}
	
	public static String getDomainByMid(String mid) {
		return weiboDao.getDomainByMid(mid);
	}
	
	@Test
	public void addWeiboTest() {
		Weibo weibo = new Weibo(1l, "a", 1l, 1l, "a", 1, "a", 1, 1, 1, "aa");
		weiboDetailDao.save(new WeiboDetail(weibo.getId(), weibo.getForwardNum(),
				weibo.getCommentNum(), weibo.getLikeNum(), weibo.getModifyTime()));
	}
}
