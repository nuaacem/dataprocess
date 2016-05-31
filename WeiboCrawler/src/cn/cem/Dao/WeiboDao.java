package cn.cem.Dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.cem.Bean.Weibo;
import cn.cem.Util.DBManager;

/**
 * 微博相关的JDBC
 * @author HXF
 *
 */
public class WeiboDao {
	
	/**
	 * 获取某个主题所有微博
	 * @param keyword
	 * @return
	 */
	public static List<Weibo> getWeibos(String keyword) {
		
		List<Weibo> weiboList = new ArrayList<Weibo>();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from ").append("weibo").append(" where item=?;");
		Object[] aParams=new Object[]{keyword};
		
		DBManager dbm = new DBManager();
		ResultSet rs = null;	
		try
		{
			rs = dbm.retrieveByPreStmt(sql.toString(),aParams);
			if(null != rs)
			{
				rs.beforeFirst();
				while(rs.next())
				{
					Weibo weibo = new Weibo();
					weibo.setId(rs.getInt(1));
					weibo.setWid(rs.getString(2));
					weibo.setItem(rs.getString(3));
					weibo.setUid(rs.getString(4));
					weibo.setZanNum(rs.getString(5));
					weibo.setZfNum(rs.getString(6));
					weibo.setCmtNum(rs.getString(7));
					weibo.setContent(rs.getString(8));
					weibo.setPubTime(rs.getString(9));
					weibo.setCmtUrl(rs.getString(10));
					weiboList.add(weibo);
				}
			}
		}
		catch (SQLException e)
		{
			weiboList = null;
			e.printStackTrace();
		}
		finally
		{
			DBManager.closeRS(rs);
			dbm.close();
		}
		return weiboList;
	}
	
	/**
	 * 新增用户记录
	 * @param weibo
	 * @param keyword
	 * @return
	 */
	public static int addWeibo(Weibo weibo,String keyword) {
		
		int agk = 0;		
		StringBuffer sql = new StringBuffer();
		sql.append("insert into ").append("weibo")
			.append(" (").append("wid")
			.append(", ").append("uid")
			.append(", ").append("item")
			.append(", ").append("zanNum")
			.append(", ").append("zfNum")
			.append(", ").append("cmtNum")
			.append(", ").append("content")
			.append(", ").append("pubTime")
			.append(", ").append("cmtUrl")
			.append(") values('").append(weibo.getWid())
			.append("', '").append(weibo.getUid())
			.append("', '").append(keyword)
			.append("', '").append(weibo.getZanNum())
			.append("', '").append(weibo.getZfNum())
			.append("', '").append(weibo.getCmtNum())
			.append("', '").append(weibo.getContent())
			.append("', '").append(weibo.getPubTime())
			.append("', '").append(weibo.getCmtUrl())
			.append("');");
		DBManager dbm = new DBManager();
		ResultSet rs = null;
//		dbm.retrieveByStmt("SET NAMES utf8mb4;" );
		rs = dbm.insertByStmtAGK(sql.toString());
		try {
			if((null!=rs) && rs.next())
			{
				agk = rs.getInt(1);				
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}		
		finally
		{
			DBManager.closeRS(rs);
			dbm.close();
		}
		return agk;
	}
	
	/**
	 * 每次变动记录，更新一次访问时间
	 * @param weiboId
	 * @param nowTime
	 * @return
	 */
	public static boolean updateVisitTime(int weiboId,String nowTime) 
	{
		boolean state = false;
		StringBuffer sql = new StringBuffer();

		sql.append("update ").append("weibo")
			.append(" set ").append("lastVisitTime")
			.append(" = '").append(nowTime)
			.append("' where ").append("id")
			.append(" = ").append(weiboId)
			.append(";");
		DBManager dbm = new DBManager();
		int count = dbm.updateByStmt(sql.toString());
		state = (0<count) ? true : false;
		dbm.close();
		return state;
	}
	
	/**
	 * 删除所有微博
	 */
	public static void deleteAll() 
	{
		StringBuffer sql = new StringBuffer();

		sql.append("delete from weibo;");
		DBManager dbm = new DBManager();
		
		dbm.deleteByStmt(sql.toString());
		dbm.close();
	}
	
	/**
	 * 获取前台展示格式的微博
	 * @param keyWord
	 * @return
	 */
	public static List<Weibo> GetResult(String keyWord) {
		
		List<Weibo> weiboList = new ArrayList<Weibo>();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT `user`.`name`,weibo.cmtUrl,weibo.pubTime,weibo.content,weibo.zanNum,weibo.zfNum,weibo.cmtNum FROM weibo INNER JOIN `user` ON weibo.uid=`user`.uid WHERE weibo.item=?;");
		Object[] aParams=new Object[]{keyWord};
		
		DBManager dbm = new DBManager();
		ResultSet rs = null;	
		
		try
		{
			rs = dbm.retrieveByPreStmt(sql.toString(),aParams);
			if(null != rs)
			{
				rs.beforeFirst();
				while(rs.next())
				{
					Weibo weibo = new Weibo();
					
					weibo.setUid(rs.getString(1));
					weibo.setCmtUrl(rs.getString(2));
					weibo.setPubTime(rs.getString(3));
					weibo.setContent(rs.getString(4));
					weibo.setZanNum(rs.getString(5));
					weibo.setZfNum(rs.getString(6));
					weibo.setCmtNum(rs.getString(7));
					weiboList.add(weibo);
				}
			}
		}
		catch (SQLException e)
		{
			weiboList = null;
			e.printStackTrace();
		}
		finally
		{
			DBManager.closeRS(rs);
			dbm.close();
		}
		return weiboList;
	}
}
