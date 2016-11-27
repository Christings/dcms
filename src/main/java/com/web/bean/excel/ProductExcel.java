package com.web.bean.excel;

import com.web.entity.Category;
import com.web.entity.Product;

/**
 * product excel实体
 * 
 * @author qgl
 */
public class ProductExcel {
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

	@Override
	public String toString() {
		return "Bean{" + "a='" + a + '\'' + ", b='" + b + '\'' + ", c='" + c + '\'' + ", d='" + d + '\'' + ", e='" + e + '\'' + ", f='"
				+ f + '\'' + ", g='" + g + '\'' + ", h='" + h + '\'' + ", i='" + i + '\'' + ", j='" + j + '\'' + ", k='" + k + '\''
				+ ", l='" + l + '\'' + ", m='" + m + '\'' + ", n='" + n + '\'' + ", o='" + o + '\'' + ", p='" + p + '\'' + ", q='" + q
				+ '\'' + ", r='" + r + '\'' + ", s='" + s + '\'' + ", t='" + t + '\'' + '}';
	}

	public Product setProductEntity(Category category) {
		Product product = new Product();
		product.setName(this.a);
		product.setCategory(category);
		product.setBrand(this.c);
		product.setFrontImgName(this.d);
		product.setBackImgName(this.e);
		product.setHeight((this.f == null || "".equals(this.f)) ? null : Integer.parseInt(this.f));
		product.setWeight((this.o == null || "".equals(this.o)) ? null : Float.parseFloat(this.o));
		product.setPower((this.r == null || "".equals(this.r)) ? null : Float.parseFloat(this.r));
		return product;
	}
}
