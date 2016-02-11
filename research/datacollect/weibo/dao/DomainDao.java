package rivers.yeah.research.datacollect.weibo.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import rivers.yeah.research.datacollect.weibo.model.Domain;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;


public class DomainDao {
	
	private static Logger logger = Logger.getLogger(DomainDao.class);
	
	public Domain getByDomainId(String domainId) {
		
		Domain domain = null;
		
		StringBuffer sql = new StringBuffer();
		sql.append("select id,domain_id,domain,topic_id from ").append("domain")
			.append(" where( 1=1 ")
			.append(") AND domain_id = '")
			.append(domainId)
			.append("';");
		DBManager dbm = new DBManager();
		ResultSet rs = null;
		PreparedStatement prep = dbm.getPreStmt(sql.toString(), false);
		try {
			rs = prep.executeQuery();
			if(null != rs)
			{
				rs.beforeFirst();
				while(rs.next())
				{
					domain = new Domain();
					domain.setId(rs.getInt(1));
					domain.setDomainId(rs.getString(2));
					domain.setDomain(rs.getString(3));
					String topicsString = rs.getString(4);
					if (topicsString != null && !"".equals(topicsString)) {
						String[] ss = topicsString.split(",");
						List<Integer> tlist = new ArrayList<Integer>();
						for (String string : ss) {
							tlist.add(Integer.parseInt(string));
						}
						domain.setTopics(tlist);
					}
				}
			}
		} catch (SQLException e) {
			logger.error(e.toString());
		} 
		dbm.close();
		return domain;
	}
	
	public List<Domain> getAll() {
		List<Domain> domainList = new ArrayList<Domain>();
		StringBuffer sql = new StringBuffer();
		sql.append("select id,domain_id,domain,topic_id from ").append("domain")
			.append(" where( 1=1 ")
			.append(")");
		DBManager dbm = new DBManager();
		ResultSet rs = null;
		PreparedStatement prep = dbm.getPreStmt(sql.toString(), false);
		try {
			rs = prep.executeQuery();
			if(null != rs)
			{
				rs.beforeFirst();
				while(rs.next())
				{
					Domain domain = new Domain();
					domain.setId(rs.getInt(1));
					domain.setDomainId(rs.getString(2));
					domain.setDomain(rs.getString(3));
					String topicsString = rs.getString(4);
					if (topicsString != null && !"".equals(topicsString)) {
						String[] ss = topicsString.split(",");
						List<Integer> tlist = new ArrayList<Integer>();
						for (String string : ss) {
							tlist.add(Integer.parseInt(string));
						}
						domain.setTopics(tlist);
					}
					
					domainList.add(domain);
				}
			}
		} catch (SQLException e) {
			logger.error(e.toString());
		} 
		dbm.close();
		return domainList;
	}
	
	
	public int save(Domain domain) {
		
		int count = 0;
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO domain")
			.append(" (").append("domain_id")
			.append(", ").append("domain")
			.append(", ").append("collect_time")
			.append(") values(?,?,?);");
		DBManager dbm = new DBManager();
		PreparedStatement prep = dbm.getPreStmt(sql.toString(), false);
		try {
			prep.setString(1, domain.getDomainId());
			prep.setString(2, domain.getDomain());
			prep.setTimestamp(3, domain.getCollectTime());
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
	
	public int update(Domain domain) {

		int count = 0;
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE ").append("domain SET ")
			.append("domain = ?")
			.append(" WHERE id = ?");
		DBManager dbm = new DBManager();
		PreparedStatement prep = dbm.getPreStmt(sql.toString(), false);
		try {
			prep.setString(1, domain.getDomain());
			prep.setString(2, domain.getDomainId());
			count = prep.executeUpdate();
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		dbm.close();
		return count;
	}
	
	public int saveOrUpdate(Domain domain) {
		
		int count = 0;
		
		String selectSql = "SELECT COUNT(*) FROM domain WHERE id = ?";

		int selectCount = 0;
		
		DBManager dbm = new DBManager();
		ResultSet rs = null;
		PreparedStatement prep = dbm.getPreStmt(selectSql, false);
		try {
			prep.setString(1, domain.getDomainId());
			rs = prep.executeQuery();
			if((null!=rs) && rs.next())
			{
				selectCount = rs.getInt(1);
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		
		if (0 == selectCount) {
			count = save(domain);
		} else {
			count = update(domain);
		}
		dbm.close();
		return count;
	}
}
