package rivers.yeah.research.datacollect.weibo.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.apache.log4j.Logger;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import rivers.yeah.research.datacollect.weibo.model.User;


public class UserDao {
	
	private static Logger logger = Logger.getLogger(WeiboDao.class);

	public int save(User user) {
		
		int count = 0;
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO ").append("user")
			.append(" (").append("uid")
			.append(", ").append("nickname")
			.append(", ").append("approve_state")
			.append(", ").append("vip_level")
			.append(", ").append("daren_level")
			.append(", ").append("fan_num")
			.append(", ").append("follow_num")
			.append(", ").append("collect_time")
			.append(", ").append("modify_time")
			.append(") values(?,?,?,?,?,?,?,?,?);");
		DBManager dbm = new DBManager();
		PreparedStatement prep = dbm.getPreStmt(sql.toString(), false);
		try {
			prep.setLong(1, user.getUid());
			prep.setString(2, user.getNickname());
			prep.setInt(3, user.getApproveState());
			prep.setInt(4, user.getVipLevel());
			prep.setInt(5, user.getDarenLevel());
			prep.setInt(6, user.getFanNum());
			prep.setInt(7, user.getFollowNum());
			prep.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
			prep.setTimestamp(9, new Timestamp(System.currentTimeMillis()));
			count = prep.executeUpdate();
		} catch (MySQLIntegrityConstraintViolationException e) {
			//DO-NOTHING
		}
		catch (SQLException e) {
			logger.error(e.toString());
		}
		dbm.close();
		return count;
	}
	
	public int update(User user) {
		
		int count = 0;
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE ").append("user ")
			.append("SET approve_state = ?")
			.append(",vip_level = ?")
			.append(",daren_level = ?")
			.append(",fan_num = ?")
			.append(",follow_num = ?")
			.append(",modify_time = ?")
			.append(" WHERE uid = ?");
		DBManager dbm = new DBManager();
		PreparedStatement prep = dbm.getPreStmt(sql.toString(), false);
		try {
			prep.setInt(1, user.getApproveState());
			prep.setInt(2, user.getVipLevel());
			prep.setInt(3, user.getDarenLevel());
			prep.setInt(4, user.getFanNum());
			prep.setInt(5, user.getFollowNum());
			prep.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
			prep.setLong(7, user.getUid());
			count = prep.executeUpdate();
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}		
		dbm.close();
		return count;
	}
	
	public int saveOrUpdate(User user) {
		
		int count = 0;
		String selectSql = "SELECT COUNT(*) FROM user WHERE uid = ?";
		int selectCount = 0;
		DBManager dbm = new DBManager();
		ResultSet rs = null;
		PreparedStatement prep = dbm.getPreStmt(selectSql, false);
		try {
			prep.setLong(1, user.getUid());
			rs = prep.executeQuery();
			if((null!=rs) && rs.next())
			{
				selectCount = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (0 == selectCount) {
			count = save(user);
		} else {
			count = update(user);
		}
		dbm.close();
		return count;
	}
	
}
