package rivers.yeah.research.dataanalysis;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MatrixConvert {
	
	public static Map<Long, Map<Integer, Long>> relations = new HashMap<Long, Map<Integer, Long>>();
	public static Map<Long, Integer> idmap = new HashMap<Long, Integer>();
	public static List<Long> relationmap = new ArrayList<Long>();
	
	public static void getRelations() throws SQLException {
		DBManager dbm = new DBManager();
		
		String sql = "SELECT DISTINCT(personId) FROM personrelation UNION SELECT DISTINCT(guanzhuId) FROM personrelation ORDER BY personId";
		PreparedStatement ps = dbm.getPreStmt(sql, false);
		ps.execute(sql);
		ResultSet rs2 = ps.getResultSet();
		int i = 0;
		while (rs2.next()) {
			long rid = rs2.getLong(1);
			idmap.put(rid, i);
			relationmap.add(rid);
			i++;
		}
		
		String sql2 = "SELECT * FROM personrelation";
		ps.execute(sql2);
		ResultSet rs = ps.getResultSet();
		while (rs.next()) {
			long personId = rs.getLong(1);
			long guanzhuId = rs.getLong(2);
			if (relations.containsKey(personId)) {
				relations.get(personId).put(idmap.get(guanzhuId), guanzhuId);
			} else {
				Map<Integer, Long> guanzhuList = new HashMap<Integer, Long>();
				guanzhuList.put(idmap.get(guanzhuId), guanzhuId);
				relations.put(personId, guanzhuList);
			}
		}		
		dbm.close();
	}	
	public static void outputRelationMatrix(String filepath, String filepath2) throws IOException {
//		for (Map.Entry<Long, List<Long>> entry : relations.entrySet()) {
//			long key = entry.getKey();
//			List<Long> relationList = entry.getValue();
//			int id = idmap.get(key);
//			for (Long relationId : relationList) {
//				int rid = idmap.get(relationId);
//			}
//		}
		File out = new File(filepath);
		if (!out.exists()) {
			out.createNewFile();
		}
		File out2 = new File(filepath2);
		if (!out2.exists()) {
			out2.createNewFile();
		}
		FileWriter fw = new FileWriter(out);
		FileWriter fw2 = new FileWriter(out2);
		fw.write("\t");
		int l = relationmap.size() - 1;
		for (int i = 0 ; i < l; i++) {
			fw.write(String.valueOf(relationmap.get(i)) + "\t");
		}
		fw.write(String.valueOf(relationmap.get(l)) + "\r\n");
		
		fw2.write("[");
		
		for (int i = 0, h = relationmap.size(); i < h; i++) {
			long pid = relationmap.get(i);
			fw.write(String.valueOf(pid));
			Map<Integer, Long> relationList = relations.get(pid);
			if (relationList != null) {
				for (int j = 0; j < h - 1; j++) {
					if (relationList.containsKey(j)) {
						fw.write("\t" + 1);
						fw2.write(1 + ",");
					} else {
						fw.write("\t" + 0);
						fw2.write(0 + ",");
					}
				}
				if (relationList.containsKey(h - 1)) {
					fw.write("\t" + 1);
					fw2.write(1 + ";");
				} else {
					fw.write("\t" + 0);
					fw2.write(0 + ";");
				}
				fw.write("\r\n");
			} else {
				for (int j = 0; j < h - 1; j++) {
					fw.write("\t" + 0);
					fw2.write(0 + ",");
				}
				fw.write("\t" + 0);
				fw2.write(0 + ";");
				fw.write("\r\n");
			}
		}
		fw2.write("]");
		fw2.flush();
		fw.close();
		fw2.close();
	}
	
	public static void main(String[] args) {
		try {
			getRelations();
			outputRelationMatrix("out2.txt","mat2.txt");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
		System.out.println();
	}
}
