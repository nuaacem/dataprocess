package nuaa.ggx.pos.dataprocess.util;

import java.util.Date;

public class TimeWordsSplit {
	public static long getKeyHours(String key) {
		String dateString = key.substring(0, key.indexOf(" "));
		int hour = Integer.parseInt(key.substring(key.indexOf(" ") + 1));
		String[] dateStrings = dateString.split("-");
		int year = Integer.parseInt(dateStrings[0]) - 1900;
		int month = Integer.parseInt(dateStrings[1]) - 1;
		int day = Integer.parseInt(dateStrings[2]);
		Date date = new Date(year, month, day, hour, 0);
		return (long)date.getTime() / (1000 * 60 * 60);
	}
	
	public static long getKeyDays(String key) {
		String[] dateStrings = key.split("-");
		int year = Integer.parseInt(dateStrings[0]) - 1900;
		int month = Integer.parseInt(dateStrings[1]) - 1;
		int day = Integer.parseInt(dateStrings[2]);
		Date date = new Date(year, month, day, 8, 0);
		return (long)date.getTime() / (1000 * 60 * 60 * 24);
	}
	
	public static void main(String[] args) {
		Date now = new Date();
		now.setHours(8);
		System.out.println(now.getTime()/(1000*60*60*24));
		System.out.println(getKeyDays("2016-05-31"));
	}
}
