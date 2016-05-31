package cn.cem.Util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 文本处理的一些方法
 * @author HXF
 * 
 */
public class TextUtil {
	
	/**
	 * 测试字符串是否为空
	 * @param str
	 * @return
	 */
	public static boolean isNull(String str) {
		return (null == str || 0 == str.length());
	}

	/**
	 * 补充html换行符
	 * @param str
	 * @return
	 */
	public static String subString(String str) {
		if (isNull(str)) {
			return "";
		} else {
			str = str.substring(0, 50);
			str = str.replaceAll("\r\n", "，");
			str = str.replace("<p>", "");
			str = str.replace("</p>", "");
			str = str.replace("　　", "");
			str += "...";
			return str;
		}
	}

	/**
	 * 从字符串转换成整形
	 * @param str
	 * @return
	 */
	public static int String2Int(String str) {
		try {
			int value = Integer.valueOf(str);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 将中文进行编码成UTF8
	 * @param str
	 * @return
	 */
	public static String toUTF8(String str) {
		String str2 = "";
		if (!isNull(str)) {
			try {
				str2 = URLEncoder.encode(str, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return str2;
	}

	/**
	 * 通过url传的参数，将其字符串格式转化为utf8
	 * @param str
	 * @return
	 */
	public static String httpToUTF8(String str){
		String str2 = "";
		if (!isNull(str)) {
			try {
				str2 = new String(str.getBytes("ISO-8859-1"), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return str2;		
	}
	
	public static String GetOnlyNum(String strings) {
		
        Pattern pattern = Pattern.compile("[^0-9]");
        Matcher matcher = pattern.matcher(strings);
        String all = matcher.replaceAll("");
		return all;
		
	}
}
