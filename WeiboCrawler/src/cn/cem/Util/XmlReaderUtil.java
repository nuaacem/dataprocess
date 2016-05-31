package cn.cem.Util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * xml读取类
 * @author HXF
 *
 */
public class XmlReaderUtil {
	
@SuppressWarnings("unchecked")
	
public static ArrayList<HashMap<String, Object>> ReadOwer(String filePath){
		
		File xmlFile = new File(filePath);
		FileInputStream fis = null;
		Iterator<Element> tickets;
		Element ticket=null;
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

		try {
			fis = new FileInputStream(xmlFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.err.println("File is not exsit!");
		}
		
		SAXReader saxReader = new SAXReader();
		try {
			Document doc = saxReader.read(fis);
			Element rootElem = doc.getRootElement();
			tickets= rootElem.elementIterator();
			while (tickets.hasNext()) {
				
				ticket = tickets.next();
				HashMap<String, Object> map= new HashMap<String, Object>();

				String[] strings = ticket.getText().split(",");
				map.put("username", strings[0]);
				map.put("password", strings[1]);
				list.add(map);
			   }
			
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return list;
	}			
}
