package rivers.yeah.research.datacollect.weibo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RelationDao {
	public static boolean buildRelation(String uid1,String uid2) {
		boolean state = false;
		
		StringBuffer preSql = new StringBuffer();
		StringBuffer sql = new StringBuffer();
		preSql.append("select * from ").append("relation")
				.append(" where(")
				.append("uid1 = '").append(uid1)
				.append("' and ")					
				.append("uid2 = '").append(uid2)
				.append("');");
		DBManager dbm = new DBManager();
		ResultSet rs1 = null;	
		ResultSet rs2 = null;
		try
		{
			rs1 = dbm.retrieveByStmt(preSql.toString());
//			System.out.println(preSql.toString());
			if(null != rs1 && rs1.next())
			{
				int num = rs1.getInt(3);
				num++;
				sql.append("update ").append("relation")
					.append(" set ").append("num")
					.append(" = ").append(num)
					.append(" where(").append("uid1")
					.append(" = '").append(uid1)
					.append("' and ").append("uid2")
					.append(" = '").append(uid2)
					.append("');");
				int count = dbm.updateByStmt(sql.toString());
				state = (0<count) ? true : false;
					
			}
			else {
				sql.append("insert into ").append("relation")
					.append(" (").append("uid1")
					.append(", ").append("uid2")
					.append(", ").append("num")
					.append(") values('").append(uid1)
					.append("', '").append(uid2)
					.append("', '").append(1)
					.append("');");
				rs2 = dbm.insertByStmtAGK(sql.toString());
				if((null!=rs2) && rs2.next())
				{
					state = true;				
				}
				else {
					state = false;
				}
			}
			System.out.println(sql.toString());
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		finally
		{
			DBManager.closeRS(rs1);
			DBManager.closeRS(rs2);
			dbm.close();
		}
		return state;
	}
}
