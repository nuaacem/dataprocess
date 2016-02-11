package rivers.yeah.research.datacollect.weibo.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import rivers.yeah.research.datacollect.weibo.model.Comment;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public class CommentDao {
	
	private static Logger logger = Logger.getLogger(WeiboDao.class);
	
	public List<Comment> getCommentsByMid(String mid) {
		List<Comment> commentList = new ArrayList<Comment>();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT id,uid,weibo_id,mid,content,heat,comment_time FROM ").append("comment")
			.append(" where( 1=1 ")
			.append(") AND mid = '")
			.append(mid + "' ORDER BY heat DESC LIMIT 50;");
		DBManager dbm = new DBManager();
		PreparedStatement prep = dbm.getPreStmt(sql.toString(), false);
		ResultSet rs = null;	
		try
		{
			rs = prep.executeQuery();
			if(null != rs)
			{
				rs.beforeFirst();
				while(rs.next())
				{
					Comment comment = new Comment(rs.getLong(1), rs.getLong(2),
							rs.getString(5), rs.getInt(6), rs.getString(7));
					comment.setWeiboId(rs.getLong(3));
					comment.setMid(rs.getString(4));
					commentList.add(comment);
				}
			}
		}
		catch (SQLException e)
		{
			commentList = null;
			e.printStackTrace();
		}
		finally
		{
			DBManager.closeRS(rs);
			dbm.close();
		}
		return commentList;
	}
	
	public int save(Comment comment) {

		int count = 0;
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO ").append("comment")
			.append(" (").append("id")
			.append(", ").append("uid")
			.append(", ").append("weibo_id")
			.append(", ").append("mid")
			.append(", ").append("content")
			.append(", ").append("heat")
			.append(", ").append("comment_time")
			.append(", ").append("collect_time")
			.append(", ").append("modify_time")
			.append(") VALUES(?,?,?,?,?,?,?,?,?);");
		DBManager dbm = new DBManager();
		PreparedStatement prep = dbm.getPreStmt(sql.toString(), false);
		try {
			prep.setLong(1, comment.getId());
			prep.setLong(2, comment.getUid());
			prep.setLong(3, comment.getWeiboId());
			prep.setString(4, comment.getMid());
			prep.setString(5, comment.getContent());
			prep.setInt(6, comment.getHeat());
			prep.setString(7, comment.getCommentTime());
			prep.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
			prep.setTimestamp(9, new Timestamp(System.currentTimeMillis()));
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
	
	public int update(Comment comment) {
		
		int count = 0;
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE comment SET ")
		   .append("mid = ?")
		   .append(",content = ?")
		   .append(",heat = ?")
		   .append(",modify_time = ?")
		   .append(" WHERE id = ?");
		DBManager dbm = new DBManager();
		PreparedStatement prep = dbm.getPreStmt(sql.toString(), false);
		try {
			prep.setString(1, comment.getMid());
			prep.setString(2, comment.getContent());
			prep.setInt(3, comment.getHeat());
			prep.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
			prep.setLong(5, comment.getId());
			count = prep.executeUpdate();
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		dbm.close();
		return count;
	}
	
	public int saveOrUpdate(Comment comment) {
		
		int count = 0;
		String selectSql = "SELECT COUNT(*) FROM comment WHERE id = ?";
		int selectCount = 0;
		DBManager dbm = new DBManager();
		ResultSet rs = null;
		PreparedStatement prep = dbm.getPreStmt(selectSql, false);
		try {
			prep.setLong(1, comment.getId());
			rs = prep.executeQuery();
			if((null!=rs) && rs.next())
			{
				selectCount = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (0 == selectCount) {
			count = save(comment);
		} else {
			count = update(comment);
		}
		dbm.close();
		return count;
	}
	
}
