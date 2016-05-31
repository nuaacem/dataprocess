package cn.cem.Cig;

/**
 * @author HXF
 * 路径的转化
 */
public class SysDirPathConsts
{
	public static final String DISK_PATH_SPLITTER = "\\";
	public static final String SITE_PATH_SPLITTER = "/";
	
	private static final SysCfg sysCfg = SysCfg.getInstance();
	
	private static final String PROJECT_DISK_PATH = sysCfg.getPreProcessedLocalPath();
	
	private static final String FRONTIER_FOLDER = "frontier";
	
	private static final String WBST_FILE_FOLDER = "wbstnews";	
	private static final String SBJT_FILE_FOLDER = "sbjtnews";
	private static final String DB_PIC = "pic";
	private static final String WBST_CFG = "wbstcfg";
	
	private static final String PICTURE = "picture";
	private static final String PICTURE_ECPD = "encyclopedia";
	
	private static final String TEMP_IO_FOLDER = "tempio";
	
	private static final String WBST_DATA_IMPORT = "WbstDataImport";
	private static final String WBST_DATA_EXPORT = "WbstDataExport";
	
	private static final String SBJT_DATA_IMPORT = "SbjtDataImport";
	private static final String SBJT_DATA_EXPORT = "SbjtDataExport";
	
	private static final String SBJT_FILE_IMPORT = "SbjtFileImport";
	private static final String SBJT_FILE_EXPORT = "SbjtFileExport";
	private static final String SBJT_FILE_ZIPPED = "SbjtFileZipped";
	
	private static final String USER_DATA_IMPORT = "UserDataImport";
	private static final String USER_DATA_EXPORT = "UserDataExport";
	
	private static final String UPLOAD_TEMP = "UploadTemp";
	private static final String UPLOAD_REPOSITORY = "UploadRepository";
	private static final String DOWNLOAD_REPOSITORY = "DownloadRepository";
	
	public static final String getProjectDiskPath()
	{
		return PROJECT_DISK_PATH;
	}
	
	public static String getWBST_FILE_FOLDER() {
		return WBST_FILE_FOLDER;
	}
	
	public static String getSBJT_FILE_FOLDER() {
		return SBJT_FILE_FOLDER;
	}
	
	public static final String getFfOnDisk()
	{
		StringBuffer path = new StringBuffer();
		path.append(PROJECT_DISK_PATH)
			.append(FRONTIER_FOLDER)
			.append(DISK_PATH_SPLITTER);
		
		return path.toString();
	}
	
	public static final String getWffOnDisk()
	{
		StringBuffer path = new StringBuffer();
		path.append(PROJECT_DISK_PATH)
			.append(WBST_FILE_FOLDER)
			.append(DISK_PATH_SPLITTER);
		
		return path.toString();
	}
	
	public static final String getSffOnDisk()
	{
		StringBuffer path = new StringBuffer();
		path.append(PROJECT_DISK_PATH)
			.append(SBJT_FILE_FOLDER)
			.append(DISK_PATH_SPLITTER);
		
		return path.toString();
	}
	
	public static final String getDpOnDisk()
	{
		StringBuffer path = new StringBuffer();
		path.append(PROJECT_DISK_PATH)
			.append(DB_PIC)
			.append(DISK_PATH_SPLITTER);
		
		return path.toString();
	}
	
	private static final String getSubFolderOfTIOF(boolean isOnDisk, String folder)
	{
		StringBuffer path = new StringBuffer();
		String splitter = DISK_PATH_SPLITTER;
		if(isOnDisk)
		{
			path.append(PROJECT_DISK_PATH);
			splitter = DISK_PATH_SPLITTER;
		}
		path.append(TEMP_IO_FOLDER)
			.append(splitter)
			.append(folder)
			.append(splitter);
		
		return path.toString();
	}
	
	public static final String getSdeOnDisk()
	{
		return getSubFolderOfTIOF(true, SBJT_DATA_EXPORT);
	}
	
	public static final String getSdiOnDisk()
	{
		return getSubFolderOfTIOF(true, SBJT_DATA_IMPORT);
	}
	
	public static final String getSfeOnDisk()
	{
		return getSubFolderOfTIOF(true, SBJT_FILE_EXPORT);
	}
	
	public static final String getSfiOnDisk()
	{
		return getSubFolderOfTIOF(true, SBJT_FILE_IMPORT);
	}
	
	public static final String getSfzOnDisk()
	{
		return getSubFolderOfTIOF(true, SBJT_FILE_ZIPPED);
	}
	
	public static final String getWdeOnDisk()
	{
		return getSubFolderOfTIOF(true, WBST_DATA_EXPORT);
	}
	
	public static final String getWdiOnDisk()
	{
		return getSubFolderOfTIOF(true, WBST_DATA_IMPORT);
	}

	public static final String getUdeOnDisk()
	{
		return getSubFolderOfTIOF(true, USER_DATA_EXPORT);
	}
	
	public static final String getUdiOnDisk()
	{
		return getSubFolderOfTIOF(true, USER_DATA_IMPORT);
	}
	
	public static final String getUploadTempOnDisk()
	{
		return getSubFolderOfTIOF(true, UPLOAD_TEMP);
	}
	
	public static final String getUploadRepositoryOnDisk()
	{
		return getSubFolderOfTIOF(true, UPLOAD_REPOSITORY);
	}

	public final String getDownloadRepositoryOnDisk()
	{
		return getSubFolderOfTIOF(true, DOWNLOAD_REPOSITORY);
	}
	
	public static final String getRltvDownloadRepository()
	{
		StringBuffer folder = new StringBuffer();
		
		folder.append(SysDirPathConsts.TEMP_IO_FOLDER)
			.append(SysDirPathConsts.SITE_PATH_SPLITTER)
			.append(SysDirPathConsts.DOWNLOAD_REPOSITORY)
			.append(SysDirPathConsts.SITE_PATH_SPLITTER);
		
		return folder.toString();
	}

	public static final String getWbstCfgOnDisk()
	{
		StringBuffer path = new StringBuffer();
		path.append(PROJECT_DISK_PATH)
			.append(WBST_CFG)
			.append(DISK_PATH_SPLITTER);
		
		return path.toString();
	}
	
	public static final String getWbstFileFolderOnDisk()
	{
		StringBuffer path = new StringBuffer();
		path.append(PROJECT_DISK_PATH)
			.append(WBST_FILE_FOLDER)
			.append(DISK_PATH_SPLITTER);
		
		return path.toString();
	}
	
	public static final String getSbjtFileFolderOnDisk()
	{
		StringBuffer path = new StringBuffer();
		path.append(PROJECT_DISK_PATH)
			.append(SBJT_FILE_FOLDER)
			.append(DISK_PATH_SPLITTER);
		
		return path.toString();
	}
	
	public static final String getEcpdPicOnDisk()
	{
		StringBuffer path = new StringBuffer();
		path.append(PROJECT_DISK_PATH)
			.append(PICTURE)
			.append(DISK_PATH_SPLITTER)
			.append(PICTURE_ECPD)
			.append(DISK_PATH_SPLITTER);
		
		return path.toString();
	}
	
}
