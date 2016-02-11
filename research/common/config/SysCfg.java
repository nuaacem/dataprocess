package rivers.yeah.research.common.config;

import java.io.File;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class SysCfg 
{
	private final String elemSysCfg = "sys_cfg";
	private final String elemLocalPath = "local_path";
	private final String elemDatabase = "database";
	private final String elemSubProtocol = "sub_protocol";
	private final String elemDriverName = "driver_name";
	private final String elemHost = "host";
	private final String elemPort = "port";
	private final String elemDatabaseName = "database_name";
	private final String elemUseUnicode = "use_unicode";
	private final String elemCharacterEncoding = "character_encoding";
	private final String elemAutoReconnect = "auto_reconnect";
	private final String elemUsername = "username";
	private final String elemPassword = "password";
	
	private String localPath;
	
	private String driverName;
	private String subProtocol;
	private String host;
	private String port;
	private String databaseName;
	private String useUnicode;
	private String characterEncoding;
	private String autoReconnect;
	private String username;
	private String password;
	
	public String getLocalPath() {
		return localPath;
	}	
	public String getDriverName() {
		return driverName;
	}
	public String getSubProtocol() {
		return subProtocol;
	}
	public String getHost() {
		return host;
	}
	public String getPort() {
		return port;
	}
	public String getDatabaseName() {
		return databaseName;
	}
	public String getUseUnicode() {
		return useUnicode;
	}
	public String getCharacterEncoding() {
		return characterEncoding;
	}
	public String getAutoReconnect() {
		return autoReconnect;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	private void setLocalPath(String localPath) {
		this.localPath = localPath;
	}
	private void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	private void setSubProtocol(String subProtocol) {
		this.subProtocol = subProtocol;
	}
	private void setHost(String host) {
		this.host = host;
	}
	private void setPort(String port) {
		this.port = port;
	}
	private void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}
	private void setUseUnicode(String useUnicode) {
		this.useUnicode = useUnicode;
	}
	private void setCharacterEncoding(String characterEncoding) {
		this.characterEncoding = characterEncoding;
	}
	private void setAutoReconnect(String autoReconnect) {
		this.autoReconnect = autoReconnect;
	}
	private void setUsername(String username) {
		this.username = username;
	}
	private void setPassword(String password) {
		this.password = password;
	}
	private static void setSysCfg(SysCfg sysCfg) {
		SysCfg.sysCfg = sysCfg;
	}
	
	private static SysCfg sysCfg = new SysCfg();
	
	private SysCfg()
	{
		String fileName = "sys_cfg.xml";
		try
		{
			String filePath = this.getClass().getResource("/").toURI().getPath() + fileName;
			this.parse(filePath);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static SysCfg getInstance()
	{
		return SysCfg.sysCfg;
	}
	
	public String getPreProcessedLocalPath()
	{
		String preProcessed = this.getLocalPath();
		if(!preProcessed.endsWith("\\"))
		{
			preProcessed += "\\";
		}
		
		return preProcessed;
	}
	
	private void parse(String filePath)
	{
		try
		{
			File file = new File(filePath);
			SAXReader saxReader = new SAXReader();
			Document doc = saxReader.read(file);
			
			Element rootElem = doc.getRootElement();
			String localPath = rootElem.elementText(this.elemLocalPath);
			
			Element databaseElem = rootElem.element(this.elemDatabase);
			String driverName = databaseElem.elementText(this.elemDriverName);
			String subProtocol = databaseElem.elementText(this.elemSubProtocol);
			String host = databaseElem.elementText(this.elemHost);
			String port = databaseElem.elementText(this.elemPort);
			String databaseName = databaseElem.elementText(this.elemDatabaseName);
			String useUnicode = databaseElem.elementText(this.elemUseUnicode);
			String characterEncoding = databaseElem.elementText(this.elemCharacterEncoding);
			String autoReconnect = databaseElem.elementText(this.elemAutoReconnect);
			String username = databaseElem.elementText(this.elemUsername);
			String password = databaseElem.elementText(this.elemPassword);
			
			this.setLocalPath(localPath);
			this.setDriverName(driverName);
			this.setSubProtocol(subProtocol);
			this.setHost(host);
			this.setPort(port);
			this.setDatabaseName(databaseName);
			this.setUseUnicode(useUnicode);
			this.setCharacterEncoding(characterEncoding);
			this.setAutoReconnect(autoReconnect);
			this.setUsername(username);
			this.setPassword(password);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			
		}
	}
}
