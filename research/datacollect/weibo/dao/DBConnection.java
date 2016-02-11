package rivers.yeah.research.datacollect.weibo.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
	private static final String DBDRIVER = "com.mysql.jdbc.Driver";
//	private static final String DBURL = "jdbc:mysql://121.199.38.98:3306/comproperty?useUnicode=true&characterEncoding=utf-8&autoReconnect=true";
//	private static final String DBUSER = "xzw";
//	private static final String DBPASSWORD = "Gt771130";
	
	private static final String DBNAME = "weibocrawler";
	private static final String DBURL = "jdbc:mysql://localhost:3306/"+DBNAME+"?useUnicode=true&characterEncoding=utf-8&autoReconnect=true";
	private static final String DBUSER = "root";
	private static final String DBPASSWORD = "root";
	
	private Connection conn = null;
	public DBConnection() throws Exception {
		try {
			Class.forName(DBDRIVER);
			this.conn = DriverManager.getConnection(DBURL, DBUSER, DBPASSWORD);
		} catch (Exception e) {
			throw e;
		}
	}
	public Connection getConnection(){
		return this.conn;
	}
	public void close() throws Exception {
		if(this.conn != null) {
			try {
				this.conn.close();
			} catch (Exception e) {
				throw e;
			}
		}
	}	
}
