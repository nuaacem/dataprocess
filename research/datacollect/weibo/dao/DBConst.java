package rivers.yeah.research.datacollect.weibo.dao;

import rivers.yeah.research.common.config.SysCfg;

public class DBConst 
{
	private static SysCfg sysCfg = SysCfg.getInstance();
	
    // mysql的驱动类，定义为常量CLASS_NAME
	public static final String DRIVER_NAME = sysCfg.getDriverName();
	
	private static final String SUB_PROTOCOL = sysCfg.getSubProtocol();
	
    // 数据库的主机，定义为常量HOST
    public static final String HOST = sysCfg.getHost();
    
    // 数据库的端口，定义为常量PORT
    public static final String PORT = sysCfg.getPort();
    
    // 数据库的名称，定义为常量DATABASE
    public static final String DATABASE_NAME = sysCfg.getDatabaseName();

    // 编码类型
    private static final String CHARACTER_ENCODING = sysCfg.getCharacterEncoding();
    
    private static final String USE_UNICODE = sysCfg.getUseUnicode();
    
    private static final String AUTO_RECONNECT = sysCfg.getAutoReconnect();
    
    // 数据库的连接地址，定义为常量CONNET_URL
    public static final String CONNET_URL = "jdbc:" + SUB_PROTOCOL 
    			+ "://" + DBConst.HOST 
    			+ ":" + DBConst.PORT 
    			+ "/" + DATABASE_NAME 
		    	+ "?useUnicode=" + USE_UNICODE
		    	+ "&characterEncoding=" + CHARACTER_ENCODING
		    	+ "&autoReconnect=" + AUTO_RECONNECT;
    
    // 数据库的用户名，定义为常量USER_NAME
    public static final String USERNAME = sysCfg.getUsername();
    
    // 数据库的密码，定义为常量PASSWORD
    public static final String PASSWORD = sysCfg.getPassword();
}