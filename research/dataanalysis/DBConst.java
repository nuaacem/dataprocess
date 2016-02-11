package rivers.yeah.research.dataanalysis;

public class DBConst 
{
    // mysql的驱动类，定义为常量CLASS_NAME
    public static final String DRIVER_NAME = "com.mysql.jdbc.Driver";
    // 数据库的主机，定义为常量HOST
    public static final String HOST = "localhost";
    // 数据库的端口，定义为常量PORT
    public static final String PORT = "3306";
    // 数据库的名称，定义为常量DATABASE
    public static final String DATABASE = "test";
//    public static final String DATABASE = "fidsstest";
    // 编码类型
    public static final String CHARSET = "utf-8";
    // 数据库的连接地址，定义为常量CONNET_URL
    public static final String CONNET_URL = "jdbc:mysql://" + DBConst.HOST 
    			+ ":" + DBConst.PORT + "/" + DBConst.DATABASE 
		    	+ "?useUnicode=true&characterEncoding=" + DBConst.CHARSET
		    	+ "&autoReconnect=true";
    // 数据库的用户名，定义为常量USER_NAME
    public static final String USERNAME = "root";
    // 数据库的密码，定义为常量PASSWORD
    public static final String PASSWORD = "root";
}