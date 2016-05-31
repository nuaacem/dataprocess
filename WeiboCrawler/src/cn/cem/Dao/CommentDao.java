package cn.cem.Dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.cem.Bean.Comment;
import cn.cem.Util.DBManager;

/**
 * 关于评论的JDBC执行
 * @author HXF
 *
 */
public class CommentDao {
	
	/**
	 * 返回微博里面全部评论
	 * @return
	 */
	public static List<Comment> getComments(String keyWord) {
		
		List<Comment>CommentList = new ArrayList<Comment>();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT `user`.`name`,`weibo`.cmtUrl,weibo.content,`comment`.content FROM `comment` INNER JOIN `user` ON `comment`.uid=`user`.uid INNER JOIN weibo ON `comment`.weiboId=weibo.id").append(" WHERE weibo.item=?;");
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
					Comment comment = new Comment();
					comment.setUid(rs.getString(1));
					comment.setWeiboUrl(rs.getString(2));
					comment.setWid(rs.getString(3));
					comment.setContent(rs.getString(4));
					CommentList.add(comment);
				}
			}
		}
		catch (SQLException e)
		{
			CommentList = null;
			e.printStackTrace();
		}
		finally
		{
			DBManager.closeRS(rs);
			dbm.close();
		}
		return CommentList;
	}
	
	/**
	 * comment表里面新增一条记录
	 * @param comment
	 * @return
	 */
	public static int addComment(Comment comment) {
		int agk = 0;
		
		StringBuffer sql = new StringBuffer();
		sql.append("insert into ").append("comment")
			.append(" (").append("divId")
			.append(", ").append("uid")
			.append(", ").append("weiboId")
			.append(", ").append("content")
			.append(") values('").append(comment.getWid())
			.append("', '").append(comment.getUid())
			.append("', '").append(comment.getWeiboId())
			.append("', '").append(comment.getContent())
			.append("');");
		DBManager dbm = new DBManager();
		ResultSet rs = null;	
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
	 * 删除所有记录
	 */
	public static void deleteAll() 
	{
		StringBuffer sql = new StringBuffer();

		sql.append("delete from comment;");
		DBManager dbm = new DBManager();
		
		dbm.deleteByStmt(sql.toString());
		dbm.close();
	}
}
