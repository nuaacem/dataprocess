package rivers.yeah.research.datacollect.weibo.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.apache.log4j.Logger;

import rivers.yeah.research.datacollect.weibo.model.WeiboDetail;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public class WeiboDetailDao {
	
	private static Logger logger = Logger.getLogger(WeiboDetailDao.class);
	
	public int save(WeiboDetail weiboDetail) {
		
		int count = 0;
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO weibo_detail")
			.append(" (").append("weibo_id")
			.append(", ").append("forward_num")
			.append(", ").append("comment_num")
			.append(", ").append("like_num")
			.append(", ").append("modify_time")
			.append(") values(?,?,?,?,?);");
		DBManager dbm = new DBManager();
		PreparedStatement prep = dbm.getPreStmt(sql.toString(), false);
		try {
			prep.setLong(1, weiboDetail.getId());
			prep.setInt(2, weiboDetail.getForwardNum());
			prep.setInt(3, weiboDetail.getCommentNum());
			prep.setInt(4, weiboDetail.getLikeNum());
			prep.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
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

}
