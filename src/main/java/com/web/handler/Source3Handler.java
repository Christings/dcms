package com.web.handler;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.web.bean.excel.ProductExcel;
import com.web.bean.util.FileUtilBean;
import com.web.entity.Category;
import com.web.entity.Product;
import com.web.example.CategoryExample;
import com.web.service.CategoryService;
import com.web.service.ProductService;
import com.web.util.Constant;
import com.web.util.ExcelUtil;
import com.web.util.FileUtil;
import com.web.util.ZipUtil;

/**
 * 说明：3source文件处理类
 *
 * @author damoguyansi
 * @date 2016-11-25
 */
@Component
public class Source3Handler {
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private ProductService productService;

	/**
	 * 3source文件处理
	 */
	public void source3Handle(FileUtilBean fileUtilBean) throws Exception {
		try {
			// 1、重命名3source为zip
			String oldFilePath = fileUtilBean.getFileRealPath();
			String newFilePath = fileUtilBean.getFileParentPath() + "/" + fileUtilBean.getNewFileName() + "_2.zip";
			File source3File = new File(fileUtilBean.getFileRealPath());
			File newSource3File = new File(newFilePath);
			source3File.renameTo(newSource3File);

			// 2、压缩文件
			String basePath = newFilePath.substring(0, newFilePath.lastIndexOf("."));
			ZipUtil.unZip(newSource3File, basePath, Constant.SOURCE_PASS);

			// 3、RSO文件复制到服务器
			String rsoPath = basePath + "/3dfiles";
			File rsoPathFile = new File(rsoPath);
			String rsoYpPath = Constant.FILE_UPLOAD_PATH + File.separator + "rso";
			for (File f : rsoPathFile.listFiles()) {
				File outFile = new File(rsoYpPath, f.getName());
				FileUtil.copy(f, outFile, true);
			}

			// 3、读取excel
			String excelPath = basePath + "/product.xls";
			File excelFile = new File(excelPath);
			InputStream in = new FileInputStream(excelFile);
			List<ProductExcel> excelList = ExcelUtil.excelToList(in, null, ProductExcel.class, ExcelUtil.productFieldMap, null);
			in.close();
			if (null == excelList || excelList.size() <= 0) {
				delete3Source(fileUtilBean);
				throw new Exception("数据为空！");
			}
			String frontImgName = excelList.get(0).d;
			String backImgName = excelList.get(0).e;
			if ((null == frontImgName || "".equals(frontImgName)) && (null == backImgName || "".equals(backImgName))) {
				throw new Exception("图片名称不能为空!");
			}

			// 4、复制图片到服务器
			String imagesPath = basePath + "/images";
			File imagesPathFile = new File(imagesPath);
			String imgYpPath = Constant.FILE_UPLOAD_PATH + File.separator + "images";
			boolean imgFlag = false;
			if (null != frontImgName && !"".equals(frontImgName)) {
				File frontFile = new File(imagesPath, frontImgName);
				if (frontFile.exists()) {
					FileUtil.copy(frontFile, new File(imgYpPath, frontFile.getName()), true);
					imgFlag = true;
				}
			}
			if (null != backImgName && !"".equals(backImgName)) {
				File backFile = new File(imagesPath, backImgName);
				if (backFile.exists()) {
					FileUtil.copy(backFile, new File(imgYpPath, backFile.getName()), true);
					imgFlag = true;
				}
			}
			if (!imgFlag) {
				throw new Exception("前视图图片与后视图图片不能都为空！");
			}

			// 5、保存型号数据
			CategoryExample te = new CategoryExample();
			CategoryExample.Criteria c1 = te.createCriteria();
			c1.andNameEqualTo(excelList.get(0).b);
			List<Category> categoryList = categoryService.getByExample(te);
			if (null == categoryList || categoryList.size() <= 0) {
				delete3Source(fileUtilBean);
				throw new Exception("分类不存在！");
			}

			ProductExcel pe = excelList.get(0);
			Product product = pe.setProductEntity(categoryList.get(0));
			productService.save(product);

			// 6、保存扩展数据

			// 7、删除解压文件
			File sourceFile = new File(basePath);
			FileUtil.delete(sourceFile);
			FileUtil.delete(new File(newFilePath));
		} catch (Exception e) {
			delete3Source(fileUtilBean);
			throw e;
		}
	}

	private void delete3Source(FileUtilBean fileUtilBean) {
		try {
			String oldFilePath = fileUtilBean.getFileRealPath();
			String newFilePath = fileUtilBean.getFileParentPath() + "/" + fileUtilBean.getNewFileName() + "_2.zip";
			File source3File = new File(fileUtilBean.getFileRealPath());
			File newSource3File = new File(newFilePath);
			String basePath = newFilePath.substring(0, newFilePath.lastIndexOf("."));

			// 1、删除服务器图片
			String imagesPath = basePath + "/images";
			File imagesPathFile = new File(imagesPath);
			String imgYpPath = Constant.FILE_UPLOAD_PATH + File.separator + "images";
			for (File f : imagesPathFile.listFiles()) {
				File outFile = new File(imgYpPath, f.getName());
				FileUtil.delete(outFile);
			}
			// 2、删除服务器RSO文件
			String rsoPath = basePath + "/3dfiles";
			File rsoPathFile = new File(rsoPath);
			String rsoYpPath = Constant.FILE_UPLOAD_PATH + File.separator + "rso";
			for (File f : rsoPathFile.listFiles()) {
				File outFile = new File(rsoYpPath, f.getName());
				FileUtil.delete(outFile);
			}

			// 3、删除解压文件
			File sourceFile = new File(basePath);
			FileUtil.delete(sourceFile);
			FileUtil.delete(new File(newFilePath));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
