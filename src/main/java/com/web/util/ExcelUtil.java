package com.web.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jxls.reader.ReaderBuilder;
import net.sf.jxls.reader.XLSReadStatus;
import net.sf.jxls.reader.XLSReader;

public class ExcelUtil {
    private final static String xmlConfig = "product.xml";

    public List read(String excelPath) throws Exception {
        InputStream inputXML = new BufferedInputStream(ExcelUtil.class.getResourceAsStream(xmlConfig));
        XLSReader mainReader = ReaderBuilder.buildFromXML(inputXML);
        InputStream inputXLS = new BufferedInputStream(new FileInputStream(excelPath));

        Bean bean = new Bean();
        Map beans = new HashMap();
        beans.put("bean", bean);
        List<Bean> list = new ArrayList<Bean>();
        XLSReadStatus readStatus = mainReader.read(inputXLS, beans);
        return list;

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

    }
}
