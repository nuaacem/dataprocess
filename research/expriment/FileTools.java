package rivers.yeah.research.expriment;

import java.io.File;

import org.junit.Test;

public class FileTools {
	
	@Test
	public void changeFilename() {
		String s = "[YYDM-11FANS][Ghost_in_The_Shell_S.A.C._2nd_GIG][BDRIP][01][X264_AAC][1280X720]";
		File dir = new File("E:FTPDownload/test");
		File[] files = dir.listFiles();
		for (File file : files) {
			String filename = file.getName();
			int a = filename.indexOf("[");
			int b = filename.indexOf("]");
			String no = filename.substring(a + 1, b);
			if (no.length() == 1) {
				no = "0" + no;
			}
			String newname = "[YYDM-11FANS][Ghost_in_The_Shell_S.A.C._2nd_GIG][BDRIP][" + no + "][X264_AAC][1280X720].ssa";
//			System.out.println(file.getParent()+ File.separator + newname);
			file.renameTo(new File(file.getParent()+ File.separator + newname));
		}
	}
}
