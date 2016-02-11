package rivers.yeah.research.datacollect.weibo.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import rivers.yeah.research.datacollect.weibo.model.Weibo;


public class WeiboDao {
	
	private static Logger logger = Logger.getLogger(WeiboDao.class);
	
	public String getDomainByMid(String mid) {
		
		String domain = "";
		String sql = "SELECT domain_id FROM weibo WHERE mid = '" + mid + "';";
		DBManager dbm = new DBManager();
		ResultSet rs = null;	
		PreparedStatement prep = dbm.getPreStmt(sql, false);
		try {
			rs = prep.executeQuery();
			if(null != rs)
			{
				rs.beforeFirst();
				while(rs.next())
				{
					domain = rs.getString(1);
				}
			}
		} catch (SQLException e) {
			logger.error(e.toString());
		} finally {
			dbm.close();
		}
		return domain;
	}
	
	public static List<Weibo> getWeibos() {
		List<Weibo> weiboList = new ArrayList<Weibo>();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from ").append("weibo")
			.append(" where( 1=1 ")
			.append(")");
		DBManager dbm = new DBManager();
		ResultSet rs = null;	
		try
		{
			rs = dbm.retrieveByStmt(sql.toString());
			if(null != rs)
			{
				rs.beforeFirst();
				while(rs.next())
				{
					Weibo weibo = new Weibo();
					weibo.setId(rs.getLong(1));
					weibo.setMid(rs.getString(2));
					weibo.setUid(rs.getLong(3));
					weibo.setForwardId(rs.getLong(4));
					weibo.setContent(rs.getString(5));
					weibo.setMediaType(rs.getInt(6));
					weibo.setDomainId(rs.getString(7));
					weibo.setForwardNum(rs.getInt(8));
					weibo.setCommentNum(rs.getInt(9));
					weibo.setLikeNum(rs.getInt(10));
					weibo.setPubTime(rs.getString(11));
					weibo.setModifyTime(rs.getTimestamp(12));
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
	
	public int save(Weibo weibo) {
		
		int count = 0;
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO weibo")
			.append(" (").append("id")
			.append(", ").append("mid")
			.append(", ").append("uid")
			.append(", ").append("forward_id")
			.append(", ").append("content")
			.append(", ").append("media_type")
			.append(", ").append("domain_id")
			.append(", ").append("forward_num")
			.append(", ").append("comment_num")
			.append(", ").append("like_num")
			.append(", ").append("pub_time")
			.append(", ").append("collect_time")
			.append(", ").append("modify_time")
			.append(") values(?,?,?,?,?,?,?,?,?,?,?,?,?);");
		DBManager dbm = new DBManager();
		PreparedStatement prep = dbm.getPreStmt(sql.toString(), false);
		try {
			prep.setLong(1, weibo.getId());
			prep.setString(2, weibo.getMid());
			prep.setLong(3, weibo.getUid());
			prep.setLong(4, weibo.getForwardId());
			prep.setString(5, weibo.getContent());
			prep.setInt(6, weibo.getMediaType());
			prep.setString(7, weibo.getDomainId());
			prep.setInt(8, weibo.getForwardNum());
			prep.setInt(9, weibo.getCommentNum());
			prep.setInt(10, weibo.getLikeNum());
			prep.setString(11, weibo.getPubTime());
			prep.setTimestamp(12, new Timestamp(System.currentTimeMillis()));
			prep.setTimestamp(13, new Timestamp(System.currentTimeMillis()));
			count = prep.executeUpdate();
		} catch (MySQLIntegrityConstraintViolationException e) {
			//DO-NOTHING
		}
		catch (SQLException e) {
			logger.error(e.getMessage());
		}
		dbm.close();
		return count;
	}
	
	public int update(Weibo weibo) {

		int count = 0;
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE ").append("weibo SET ")
			.append("content = ?")
			.append(",forward_num = ?")
			.append(",comment_num = ?")
			.append(",like_num = ?")
			.append(",modify_time = ?")
			.append(" WHERE id = ?");
		DBManager dbm = new DBManager();
		PreparedStatement prep = dbm.getPreStmt(sql.toString(), false);
		try {
			prep.setString(1, weibo.getContent());
			prep.setInt(2, weibo.getForwardNum());
			prep.setInt(3, weibo.getCommentNum());
			prep.setInt(4, weibo.getLikeNum());
			prep.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
			prep.setLong(6, weibo.getId());
			count = prep.executeUpdate();
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		dbm.close();
		return count;
	}
	
	public int saveOrUpdate(Weibo weibo) {
		
		int count = 0;
		
		String selectSql = "SELECT COUNT(*) FROM weibo WHERE id = ?";

		int selectCount = 0;
		
		DBManager dbm = new DBManager();
		ResultSet rs = null;
		PreparedStatement prep = dbm.getPreStmt(selectSql, false);
		try {
			prep.setLong(1, weibo.getId());
			rs = prep.executeQuery();
			if((null!=rs) && rs.next())
			{
				selectCount = rs.getInt(1);
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		
		if (0 == selectCount) {
			count = save(weibo);
		} else {
			count = update(weibo);
		}
		dbm.close();
		return count;
	}
}
