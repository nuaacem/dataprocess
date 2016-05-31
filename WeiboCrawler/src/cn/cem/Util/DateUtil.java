package cn.cem.Util;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 对于时间的一些处理方法
 * @author HXF
 *
 */
public class DateUtil {
	
	/**
	 * 格式日期 yyyy-MM-dd HH:mm:ss
	 * @param date
	 * @return
	 */
	public static String formatDate(Date date)
	{
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(date);
	}
	
	/**
	 * 格式时间 E dd MMM yyyy hh:mm:ss a
	 * @param time
	 * @return
	 */
	public static String getTime(Long time){
		Date d = new Date(time);
		Format simpleFormat = new SimpleDateFormat("E dd MMM yyyy hh:mm:ss a");
		String date = simpleFormat.format(d);
		return date;
	}
	
	/**
	 * 微博时间格式化
	 * @param date0
	 * @return
	 */
	public static String getFormulaDate(String date0) {
		String date;
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		DateFormat ddf = new SimpleDateFormat("MM月dd日 HH:mm");
		DateFormat dddf = new SimpleDateFormat("MM-dd HH:mm");
		
		String regex1 = "今天\\s\\d{2}:\\d{2}";
		String regex2 = "\\d{1,2}分钟";
		String regex3 = "\\d{2}月\\d{2}日\\s\\d{2}:\\d{2}";
		String regex4 = "\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}";
		
		Pattern p = Pattern.compile(regex1);
		Matcher m = p.matcher(date0);		
		if (m.find()) {
			date = df.format(new Date()).substring(0, 11) + m.group().substring(3);
			return date;
		} 
		
		p = Pattern.compile(regex2);
		m = p.matcher(date0);
		if (m.find()) {
			Date d = new Date();
			long t = d.getTime()-Integer.parseInt(m.group().split("分钟")[0])*60*1000;			
			date = df.format(new Date(t));
			return date;
		}
		
		p = Pattern.compile(regex3);
		m = p.matcher(date0);
		if (m.find()) {
			try {
				date = df.format(new Date()).substring(0,5) + dddf.format(ddf.parse(m.group()));
				return date;
			} catch (ParseException e) {
				e.printStackTrace();
				return "";
			}
		}
		p = Pattern.compile(regex4);
		m = p.matcher(date0);
		if (m.find()) {
			date = m.group();
			return date;
		}
		return "";
	}
	
}
