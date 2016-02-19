package nuaa.ggx.pos.dataprocess.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import nuaa.ggx.pos.dataprocess.dao.interfaces.IWeiboDao;
import nuaa.ggx.pos.dataprocess.dao.jdbc.DBManager;
import nuaa.ggx.pos.dataprocess.model.Weibo;

public class WeiboDao implements IWeiboDao {
	
	public List<Weibo> getWeibosBetweenTime(String startTime, String endTime) {
		String sql = "select t_consensus.id,t_consensus.content,t_consensus_detail_domain "
				+ "from t_consensus,t_consensus_detail"
				+ " where pub_time > '?'"
				+ " and pub_time < '?'"
				+ " and t_consensus.id = t_consensus_detail.id;";
		DBManager manager = new DBManager();
		ResultSet result = manager.retrieveByPreStmt(sql, new String[]{startTime,endTime});
		List<Weibo> list = new ArrayList<>();
		try {
			while (result.next()) {
				Weibo weibo = new Weibo(result.getInt(1),result.getString(2),result.getString(3));
				list.add(weibo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}
