package com.web.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jdk.internal.org.xml.sax.SAXException;
import net.sf.jxls.reader.ReaderBuilder;
import net.sf.jxls.reader.XLSReadStatus;
import net.sf.jxls.reader.XLSReader;

/**
 * Created by damoguyansi on 2016-11-22.
 */
public class ExcelUtil {
	private final static String xmlConfig = "student.xml";

	public List read() {
		InputStream inputXML = new BufferedInputStream(ExcelUtil.class.getResourceAsStream(xmlConfig));
		XLSReader mainReader;
		String path = ExcelUtil.class.getResource("/").getPath();
		path = path.substring(1, path.indexOf("/WebRoot") + 1) + "WebRoot/Excel/stu.xls";
		try {
			mainReader = ReaderBuilder.buildFromXML(inputXML);
			InputStream inputXLS = new BufferedInputStream(new FileInputStream(path));

			Bean bean = new Bean();
			Map beans = new HashMap();
			beans.put("bean", bean);
			XLSReadStatus readStatus = mainReader.read(inputXLS, beans);
			return students;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	class Bean {
		public String a;
		public String b;
		public String c;
		public String d;
		public String e;
		public String f;
		public String g;
		public String h;
		public String i;
		public String j;
		public String k;
		public String l;
		public String m;
		public String n;
		public String o;
		public String p;
		public String q;
		public String r;
		public String s;
		public String t;

	}

	public static void main(String[] args) {
		ExcelUtil re = new ExcelUtil();
		List<Student> list = re.read();
		System.out.println("ID\t  name\t  subject\t  score");
		for (Student stu : list) {
			System.out.println(stu.getIdname() + "\t  " + stu.getName() + "\t  " + stu.getSubject() + "\t  " + stu.getScorename());
		}
	}
}
