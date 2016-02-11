package nuaa.ggx.pos.dataprocess.lda.util;

public class LdaUtil {

	public static String converseZeroPad(int number, int width) {
		
		StringBuffer result = new StringBuffer("");
		
		for (int i = 0; i < width - Integer.toString(number).length(); i++) {
			result.append("0");
		}	
		result.append(Integer.toString(number));

		return result.toString();
	}
}
