package rivers.yeah.research.datacollect.weibo.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class AnalyseWeibo {
	
	public static String getFormulaDate(String date0) {
		String date;

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		DateFormat ddf = new SimpleDateFormat("MM月dd日 HH:mm");
		DateFormat dddf = new SimpleDateFormat("MM-dd HH:mm");

		String regex1 = "今天\\s\\d{2}:\\d{2}";
		String regex2 = "\\d{1,2}分钟";
		String regex3 = "\\d{1,2}月\\d{1,2}日\\s\\d{2}:\\d{2}";
		String regex4 = "\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{2}:\\d{2}";
		String regex5 = "\\d{1,2}秒";

		Pattern p = Pattern.compile(regex1);
		Matcher m = p.matcher(date0);
		if (m.find()) {
			date = df.format(new Date()).substring(0, 11)
					+ m.group().substring(3);
			return date;
		}

		p = Pattern.compile(regex2);
		m = p.matcher(date0);
		if (m.find()) {
			Date d = new Date();
			long t = d.getTime() - Integer.parseInt(m.group().split("分钟")[0])
					* 60 * 1000;
			date = df.format(new Date(t));
			return date;
		}

		p = Pattern.compile(regex3);
		m = p.matcher(date0);
		if (m.find()) {
			try {
				date = df.format(new Date()).substring(0, 5)
						+ dddf.format(ddf.parse(m.group()));
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
		
		p = Pattern.compile(regex5);
		m = p.matcher(date0);
		if (m.find()) {
			Date d = new Date();
			long t = d.getTime() - Integer.parseInt(m.group().split("秒")[0]) * 1000;
			date = df.format(new Date(t));
			return date;
		}
		
		return df.format(new Date());
	}

	// 62进制字典
	private static String str62keys = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

	public static String mid2Id(String mid) {
		String id = "";
		for (int i = mid.length() - 4; i > -4; i = i - 4) // 从最后往前以4字节为一组读取URL字符
		{
			int offset1 = i < 0 ? 0 : i;
			int len = i < 0 ? mid.length() % 4 : 4;
			String str = mid.substring(offset1, offset1 + len);

			str = str62toInt(str);
			if (offset1 > 0) // 若不是第一组，则不足7位补0
			{
				while (str.length() < 7) {
					str = "0" + str;
				}
			}
			id = str + id;
		}

		return id;
	}

	public static String Id2Mid(String id) {
		String mid = "", strTemp;
		int startIdex, len;

		for (int i = id.length() - 7; i > -7; i = i - 7) // 从最后往前以7字节为一组读取mid
		{
			startIdex = i < 0 ? 0 : i;
			len = i < 0 ? id.length() % 7 : 7;
			strTemp = id.substring(startIdex, startIdex + len);
			String temp = intToStr62(Integer.parseInt(strTemp));
			if (i > 0 && temp.length() < 4) {
				int offset = 4 - temp.length();
				for (int j = 0; j < offset; j++) {
					temp = "0" + temp;
				}
			}
			mid = temp + mid;
		}
		return mid;
	}

	// 62进制转成10进制
	public static String str62toInt(String str62) {
		long i64 = 0;
		for (int i = 0; i < str62.length(); i++) {
			long vi = (long) Math.pow(62, (str62.length() - i - 1));
			char t = str62.charAt(i);
			i64 += vi * getInt10(String.valueOf(t));
		}
		return String.valueOf(i64);
	}

	// 10进制转成62进制
	public static String intToStr62(int int10) {
		String s62 = "";
		int r = 0;
		while (int10 != 0) {
			r = int10 % 62;
			s62 = get62key(r) + s62;
			int10 = int10 / 62;
		}
		return s62;
	}

	// 获取key对应的62进制整数
	private static long getInt10(String key) {
		return str62keys.indexOf(key);
	}

	// 获取62进制整数对应的key
	private static String get62key(int int10) {
		if (int10 < 0 || int10 > 61)
			return "";
		return str62keys.substring(int10, int10 + 1);
	}
	
	public static String[] getNums(Element divWBHandle) {
		String[] nums = new String[4];		
		nums[0] = "";
		nums[1] = "";
		nums[2] = "";
		nums[3] = "";
		if (divWBHandle == null) {
			return nums;
		}
		Elements spans = divWBHandle.select("span.line");
		for (Element span : spans) {
			String numText = span.text();
			if (numText.contains("转发")) {
				numText = numText.substring(2).trim();
				nums[0] = numText;
			} else if (numText.contains("评论")) {
				numText = numText.substring(2).trim();
				nums[1] = numText;
			} else if (span.select(".icon_praised_b").size()>0) {
				String midText = span.parent().attr("action-data");
				if (!midText.equals("")) {
					nums[3] = midText.substring(4);
				}
				nums[2] = numText;
			}				
		}
		return nums;
	}
	
	/**
	 * 0 无media,1-仅图片,2-仅视频
	 * @param divWBMediaWrap
	 * @return
	 */
	public static int getMediaType(Element divWBMediaWrap) {
		if (divWBMediaWrap == null) {
			return 0;
		}
		boolean hasImg = false;
		boolean hasVideo = false;
		boolean hasLink = false;
		Elements div_mediaboxs = divWBMediaWrap.select(".media_box");
		for (Element div_mediabox : div_mediaboxs) {
			if (div_mediabox.select("ul>li.WB_pic").size() > 0) {
				hasImg = true;
			}
			if (div_mediabox.select("ul>li.WB_video").size() > 0) {
				hasVideo = true;
			}
			Element div_wbfeed;
			if ((div_wbfeed = div_mediabox.select(".WB_feed_spec").first()) != null) {
				if (div_wbfeed.select("span.icon_playvideo").size() > 0) {
					hasVideo = true;
				}
			}
		}
		if (hasImg) {
			if (hasVideo) {
				if (hasLink) {
					return 7;
				} else {
					return 4;
				}
			} else {
				if (hasLink) {
					return 5;
				} else {
					return 1;
				}
			}
		} else {
			if (hasVideo) {
				if (hasLink) {
					return 6;
				} else {
					return 2;
				}
			} else {
				if (hasLink) {
					return 3;
				} else {
					return 0;
				}
			}
		}
	}
		
	public static void main(String[] args) {
		System.out.println(Id2Mid("3850884130157241"));
		System.out.println(mid2Id("ClfMK0jIf"));
	}
	// public static void testUser(User user)
	// {
	// user.setUid("100");
	// }
	// public static void main(String[] args) {
	// User user = new User();
	// testUser(user);
	// System.out.println(user.getUid());
	// }
}
