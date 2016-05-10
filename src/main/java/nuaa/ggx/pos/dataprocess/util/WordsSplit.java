package nuaa.ggx.pos.dataprocess.util;

import java.util.Date;

public class WordsSplit {
	public static String[] wordsSplit(String wordsList) {
		String[] str = wordsList.split(" ");
		for (int i=0; i<str.length; i++) {
			if(!(str[i].equals("") || str[i] == null) && str[i].indexOf('/') > 0){
				str[i] = str[i].substring(0, str[i].lastIndexOf('/'));
			}
		}
		return str;
	}

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
	
	public static void main(String[] args) {
		Date now = new Date();
		System.out.println(now.getTime()/(1000*60*60));
	}
}
