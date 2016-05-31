package cn.cem.Util;

import java.io.File;

import java.util.List;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class ExcelCreate {

	public static void CreateExcel(List<String[]> listaf, String onputString) {
		try {

			WritableWorkbook book = Workbook.createWorkbook(new File(onputString));

			WritableSheet sheet = book.createSheet("output", 0);

			for (int x = 0; x < listaf.size(); x++) {
				String[] str = (String[]) listaf.get(x);
				for (int y = 0; y < str.length; y++) {
					Label label = new Label(y, x, str[y]);
					sheet.addCell(label);
				}
			}

			book.write();
			book.close();

		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
