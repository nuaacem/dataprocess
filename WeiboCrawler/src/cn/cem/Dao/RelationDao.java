package cn.cem.Dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import cn.cem.Util.DBManager;

/**
 * 用户1在用户2每次出现了评论，记为1次互动，并不断叠加
 * @author HXF
 *
 */
public class RelationDao {
	
	/**
	 * 在relation中，新建uid1与uid2的关系
	 * @param uid1
	 * @param uid2
	 * @return
	 */
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
	
	/**
	 * 删除所有记录
	 */
	public static void deleteAll() 
	{
		StringBuffer sql = new StringBuffer();

		sql.append("delete from relation;");
		DBManager dbm = new DBManager();
		
		dbm.deleteByStmt(sql.toString());
		dbm.close();
	}
}
