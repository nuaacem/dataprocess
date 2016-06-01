package nuaa.ggx.pos.dataprocess.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import kevin.zhang.NLPIR;

public class CallNLPIR {

	public static final String STOP_WORD_PATH = "file/userdict/stopwords.txt";
	// 停用词表
	public static Set<String> stopWordSet = new HashSet<String>();

	private static NLPIR nlpir = new NLPIR();

	static {
		try {
			init("utf-8", 1);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * NLPIR_Init方法第二个参数设置0表示编码为GBK, 1表示UTF8编码(此处结论不够权威)
	 * 
	 * @param charset
	 * @param charsetId
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static boolean init(String charset, int charsetId) throws UnsupportedEncodingException {
		if (!NLPIR.NLPIR_Init("file/dict/".getBytes(charset), charsetId)) {
			System.out.println("NLPIR初始化失败...");
			return false;
		}
		initStopWordTable(STOP_WORD_PATH);
		return true;
	}

	public static void exit() {
		NLPIR.NLPIR_Exit();
	}

	public static void initStopWordTable(String srcFile) {
		try {
			BufferedReader stopWordFileBr = new BufferedReader(
					new InputStreamReader(new FileInputStream(new File(srcFile))));
			stopWordFileBr.read();
			String stopWord = null;
			for (; (stopWord = stopWordFileBr.readLine()) != null;) {
				stopWordSet.add(stopWord);
			}
			stopWordFileBr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String excludeStopWord(String lineInput, String delimit) {
		String[] resultArray = lineInput.split(delimit);
		String word = "";
		// System.out.println("分词结果: " + lineInput);
		for (int i = 0; i < resultArray.length - 1; i++) {
			word = resultArray[i].trim();
			if (word.equals("") || stopWordSet.contains(word)) {
				resultArray[i] = null;
			}
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < resultArray.length - 1; i++) {
			if (resultArray[i] != null && resultArray[i].trim() != "") {
				sb.append(resultArray[i].trim() + " ");
			}
		}
		if (sb.length() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		String finalResult = sb.toString();
		return finalResult;
	}

	public static void loadUserDict(String filename, boolean ifSave) throws IOException {
		String path = "file/userdict/";
		String filePath = path + filename;
		BufferedReader br = new BufferedReader(new FileReader(new File(filePath)));
		String aLine = br.readLine();
		System.out.println("loading user dict...");
		while (aLine != null && aLine != "") {
			nlpir.NLPIR_AddUserWord(aLine.getBytes("UTF-8"));
			aLine = br.readLine();
		}
		System.out.println("load user dict finished!");
		br.close();
		if (ifSave) {
			nlpir.NLPIR_SaveTheUsrDic();
		}
	}

	public static void segWords(String inPath, String outPath, int feature) throws IOException {
		nlpir.NLPIR_FileProcess(inPath.getBytes(), outPath.getBytes(), feature);
	}
	
	public static String segWords(String input) throws IOException {
		return new String(nlpir.NLPIR_ParagraphProcess(input.getBytes(), 0), "UTF-8");
	}

	public static String excludeSign(String string) {

		string = string.replaceAll("O秒拍视频|O网页链接", "").trim();
		char[] chars = string.toCharArray();
		char[] charsEx = new char[chars.length];
		int j = 0;
		for (int i = 0; i < chars.length; i++) {
			if ((chars[i] >= 19968 && chars[i] <= 40869) || (chars[i] >= 97 && chars[i] <= 122)
					|| (chars[i] >= 65 && chars[i] <= 90)) {
				charsEx[j++] = chars[i];
			} else {
				charsEx[j++] = ' ';
			}
		}
		return new String(charsEx).trim();
	}

	public static List<String> segWords(String inFile, int feature, boolean ifDetectNewWord) throws IOException {

		List<String> result = new ArrayList<>();

		if (ifDetectNewWord) {
			newWordDetect(inFile, true);
		}

		BufferedReader br = new BufferedReader(new FileReader(inFile));

		String aLine = "";

		while ((aLine = br.readLine()) != null) {

			if (!aLine.trim().equals("")) {
				aLine = excludeSign(aLine);
				aLine = aLine.replaceAll("O秒拍视频", "");
				aLine = aLine.replaceAll("O网页链接", "").trim();
				byte[] resBytes = nlpir.NLPIR_ParagraphProcess(aLine.getBytes("UTF-8"), feature);
				String segResult = excludeStopWord(new String(resBytes, "UTF-8").replaceAll("@.+?(：|:|\\s)", ""), " ");
				result.add(segResult.trim());
			} else {
				result.add("");
			}

		}

		br.close();
		exit();
		return result;
	}

	public static void segWords(String path, String filename, boolean ifDetectNewWord, boolean hasDomain, int feature)
			throws IOException {

		String inpath = path + "/input/";
		String outpath = path + "/output/";

		if (ifDetectNewWord) {
			newWordDetect(inpath + filename, true);
		}

		String stopWordPath = "file/userdict/stopwords.txt";
		initStopWordTable(stopWordPath);
		File inFile = new File(inpath + filename);
		BufferedReader br = new BufferedReader(new FileReader(inFile));

		File outFile = new File(outpath + filename);
		BufferedWriter bw = new BufferedWriter(new FileWriter(outFile));

		String aLine = br.readLine();
		String domain = "";

		while (aLine != null) {

			if (!aLine.trim().equals("")) {
				if (hasDomain) {
					int i = aLine.indexOf("\t");
					domain = aLine.substring(0, i);
					aLine = aLine.substring(i + 1);
				}

				aLine = aLine.replaceAll("O秒拍视频", "");
				aLine = aLine.replaceAll("O网页链接", "").trim();
				aLine = excludeSign(aLine);
				byte[] resBytes = nlpir.NLPIR_ParagraphProcess(aLine.getBytes("UTF-8"), feature);
				String result = new String(resBytes, "UTF-8");
				String finalResult = excludeStopWord(result.replaceAll("@.+?(：|:|\\s)", ""), " ");
				if (!finalResult.trim().equals("")) {
					if (!domain.equals("")) {
						bw.write("ycdomain:" + domain + "\t");
					}
					bw.write(finalResult.trim());
					bw.newLine();
				} else {
					bw.newLine();
				}
			} else {
				bw.newLine();
			}
			aLine = br.readLine();
		}
		bw.flush();
		br.close();
		bw.close();

		// exit();
	}

	public static void segWords(String inFile, String outFile, boolean ifDetectNewWord, int feature)
			throws IOException {

		if (ifDetectNewWord) {
			newWordDetect(inFile, true);
		}

		String stopWordPath = "file/userdict/stopwords.txt";
		initStopWordTable(stopWordPath);
		BufferedReader br = new BufferedReader(new FileReader(inFile));

		BufferedWriter bw = new BufferedWriter(new FileWriter(outFile));

		String aLine = br.readLine();
		String domain = "";

		while (aLine != null) {

			if (!aLine.trim().equals("")) {
				aLine = aLine.replaceAll("O秒拍视频", "");
				aLine = aLine.replaceAll("O网页链接", "").trim();
				aLine = excludeSign(aLine);
				byte[] resBytes = nlpir.NLPIR_ParagraphProcess(aLine.getBytes("UTF-8"), feature);
				String result = new String(resBytes, "UTF-8");
				String finalResult = excludeStopWord(result.replaceAll("@.+?(：|:|\\s)", ""), " ");
				if (!finalResult.trim().equals("")) {
					if (!domain.equals("")) {
						bw.write("ycdomain:" + domain + "\t");
					}
					bw.write(finalResult.trim());
					bw.newLine();
				} else {
					bw.newLine();
				}
			} else {
				bw.newLine();
			}
			aLine = br.readLine();
		}
		bw.flush();
		br.close();
		bw.close();

		// exit();
	}
	
	public static void newWordDetect(String filePath, boolean ifSave) throws IOException {

		if (!nlpir.NLPIR_NWI_Start()) {
			System.out.println("NLPIR新词发现系统初始化失败...");
			return;
		}
		nlpir.NLPIR_NWI_AddFile(filePath.getBytes());

		if (!nlpir.NLPIR_NWI_Complete()) {
			System.out.println("NLPIR新词发现失败...");
			return;
		}
		System.out.println(new String(nlpir.NLPIR_NWI_GetResult(true)));
		nlpir.NLPIR_NWI_Result2UserDict();
		if (ifSave) {
			nlpir.NLPIR_SaveTheUsrDic();
		}
	}

	public static void genUserDict(String filename) throws IOException {
		String path = "file/userdict/";
		String filePath = path + filename;
		BufferedReader br = new BufferedReader(new FileReader(new File(filePath)));
		FileWriter fw = new FileWriter(new File(filePath + ".out.txt"));
		String aLine = br.readLine();
		while (aLine != null && aLine != "") {
			String[] segs = aLine.split("\t");
			String cixing = segs[2].substring(segs[2].lastIndexOf(" ") + 1);
			fw.write(segs[1] + " " + cixing + "\r\n");
			aLine = br.readLine();
		}
		fw.flush();
		br.close();
		fw.close();
	}

	public static void main(String[] args) throws IOException {
		// System.out.println(excludeSign(" 注意保暖，别冻到咯 (⁎⁍̴̛ᴗ⁍̴̛⁎)！
		// 务必把我打包送过去，热成狗了 "));
		// newWordDetect("CjMiifHZx.txt", false);
		// segWords("0607.txt",true, true, 0);
		// System.out.println(nlpir.NLPIR_DelUsrWord("双鱼牌".getBytes()));
		// segWords("./test/meishi.txt", "./test/meishi22.txt", 0);
		// System.out.println(nlpir.NLPIR_AddUserWord("大傻逼 n".getBytes()));
		// System.out.println(nlpir.NLPIR_DelUsrWord("大傻逼".getBytes()));
		// System.out.println(new
		// String(nlpir.NLPIR_ParagraphProcess("我开了个大傻逼去上街".getBytes("UTF-8"),
		// 1),"UTF-8"));
		String inputString = "【DIY不织布蝴蝶结】超级简单，按图中的样子剪裁出3个形状的布，缝合就好，颜色随自己喜好，做成头绳啊，发夹啊，粘在书签上啊，各种都可以呢。";
		//loadUserDict("userdic.txt", true);
		//System.out.println(new String(nlpir.NLPIR_ParagraphProcess(inputString.getBytes(), 0)));
		System.out.println(excludeStopWord(segWords(inputString), " "));
	}
}