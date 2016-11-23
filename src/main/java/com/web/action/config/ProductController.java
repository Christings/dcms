package com.web.action.config;

import static com.web.util.AllResult.buildJSON;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.web.bean.form.ProductForm;
import com.web.bean.util.FileUtilBean;
import com.web.core.action.BaseController;
import com.web.core.util.page.Page;
import com.web.entity.OperLog;
import com.web.entity.Product;
import com.web.example.ProductExample;
import com.web.service.ProductService;
import com.web.util.AllResult;
import com.web.util.Constant;
import com.web.util.FileUtil;
import com.web.util.PropertiesUtil;
import com.web.util.UUIDGenerator;
import com.web.util.ZipUtil;
import com.web.util.fastjson.FastjsonUtils;
import com.web.util.validation.GroupBuilder;
import com.web.util.validation.ValidationHelper;

/**
 * 设备型号管理接口
 * 
 * @author 杜延雷
 * @date 2016-11-14
 */
@RestController
@RequestMapping("/product")
public class ProductController extends BaseController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

	@Autowired
	ProductService productSerivce;

	/**
	 * 添加
	 */
	@RequestMapping(value = "/add", method = { RequestMethod.POST, RequestMethod.GET })
	public Object add(ProductForm form) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [ProductForm: {}]", JSON.toJSONString(form));
		}

		// 验证参数
		String errorTip = ValidationHelper.build()
				// 必输条件验证
				.addGroup(GroupBuilder.build(form.getName()).notEmpty().maxLength(255), "名称必须提供且最大长度255位")
				.addGroup(GroupBuilder.build(form.getTypeId()).notEmpty().maxLength(32), "类型ID必须提供且最大长度32位")
				.addGroup(GroupBuilder.build(form.getBrand()).notEmpty().maxLength(255), "生产厂商必须提供且最大长度255位")
				.addGroup(GroupBuilder.build(form.getHeight()).notNull().minValue(0), "高度U必须提供且最小值为0")
				// 非必输项
				.addGroup(GroupBuilder.buildOr(form.getWeight()).isNull().minValue(0f), "重量最小值为0")
				.addGroup(GroupBuilder.buildOr(form.getPower()).isNull().minValue(0f), "额定功率最小值为0").validate();

		if (StringUtils.isNotEmpty(errorTip)) {
			return buildJSON(HttpStatus.BAD_REQUEST.value(), errorTip);
		}

		try {
			Product product = new Product();
			BeanUtils.copyProperties(form, product, "id", "extra");

			product.setId(UUIDGenerator.generatorRandomUUID());

			// 保存
			productSerivce.save(product);

			// 增加日志
			operLogService.addSystemLog(OperLog.operTypeEnum.insert, OperLog.actionSystemEnum.product, JSON.toJSONString(product),
					OperLog.logLevelEnum.success);

			// 去除不需要的字段
			String jsonStr = JSON.toJSONString(product,
					FastjsonUtils.newIgnorePropertyFilter("extra", "updateName", "updateDate", "createName", "createDate"),
					SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty);

			return AllResult.okJSON(JSON.parse(jsonStr));
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("save Product fail:", e.getMessage());
			operLogService.addSystemLog(OperLog.operTypeEnum.insert, OperLog.actionSystemEnum.product, null,
					OperLog.logLevelEnum.error);
		}
		return buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误");
	}

	/**
	 * 修改
	 */
	@RequestMapping(value = "/update", method = { RequestMethod.POST, RequestMethod.GET })
	public Object update(ProductForm form) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [ProductForm: {}]", JSON.toJSONString(form));
		}

		// 验证参数
		String errorTip = ValidationHelper.build()
				// 必输条件验证
				.addGroup(GroupBuilder.build(form.getId()).notEmpty().maxLength(32), "ID必须提供且最大长度32位")
				.addGroup(GroupBuilder.build(form.getName()).notEmpty().maxLength(255), "名称必须提供且最大长度255位")
				.addGroup(GroupBuilder.build(form.getTypeId()).notEmpty().maxLength(32), "类型ID必须提供且最大长度32位")
				.addGroup(GroupBuilder.build(form.getBrand()).notEmpty().maxLength(255), "生产厂商必须提供且最大长度255位")
				.addGroup(GroupBuilder.build(form.getHeight()).notNull().minValue(0), "高度U必须提供且最小值为0")

				// 非必输项
				.addGroup(GroupBuilder.buildOr(form.getWeight()).isNull().minValue(0f), "重量最小值为0")
				.addGroup(GroupBuilder.buildOr(form.getPower()).isNull().minValue(0f), "额定功率最小值为0").validate();

		if (StringUtils.isNotEmpty(errorTip)) {
			return buildJSON(HttpStatus.BAD_REQUEST.value(), errorTip);
		}

		try {
			Product product = productSerivce.getById(form.getId());
			if (null == product) {
				return buildJSON(HttpStatus.BAD_REQUEST.value(), "未查询到信息");
			}

			Product p = new Product();
			BeanUtils.copyProperties(form, p, "extra");

			productSerivce.updateById(p);

			// 增加日志
			operLogService.addSystemLog(OperLog.operTypeEnum.update, OperLog.actionSystemEnum.product,
					JSON.toJSONString(p, SerializerFeature.IgnoreNonFieldGetter), OperLog.logLevelEnum.success);

			// 去除不需要的字段（返回前端数据）
			String jsonStr = JSON.toJSONString(p,
					FastjsonUtils.newIgnorePropertyFilter("extra", "updateName", "updateDate", "createName", "createDate"),
					SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty);

			return AllResult.okJSON(JSON.parse(jsonStr));
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("update Product fail:", e.getMessage());
			operLogService.addSystemLog(OperLog.operTypeEnum.update, OperLog.actionSystemEnum.product, null,
					OperLog.logLevelEnum.error);
		}
		return buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误");
	}

	/**
	 * 调整状态预留
	 */
	@RequestMapping(value = "/status", method = { RequestMethod.POST, RequestMethod.GET })
	public Object status(String id, Short status) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [id: {}, status: {}]", id, status);
		}

		return AllResult.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = { RequestMethod.POST, RequestMethod.GET })
	public Object delete(String id) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [id: {}]", id);
		}

		if (StringUtils.isEmpty(id) || "".equals(id.trim())) {
			return buildJSON(HttpStatus.BAD_REQUEST.value(), "ID必须提供");
		}

		try {
			Product product = productSerivce.getById(id);
			if (null == product) {
				return AllResult.build(1, "设备信息不存在");
			}
			productSerivce.deleteById(id);

			// 增加日志
			operLogService.addSystemLog(OperLog.operTypeEnum.delete, OperLog.actionSystemEnum.product,
					JSON.toJSONString(product, SerializerFeature.IgnoreNonFieldGetter), OperLog.logLevelEnum.success);

			return AllResult.ok();
		} catch (Exception e) {
			e.getStackTrace();
			LOGGER.error("delete Product fail:", e.getMessage());
			operLogService.addSystemLog(OperLog.operTypeEnum.delete, OperLog.actionSystemEnum.product, null,
					OperLog.logLevelEnum.error);
		}
		return buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误");
	}

	/**
	 * 获取详情
	 */
	@RequestMapping(value = "/get", method = { RequestMethod.POST, RequestMethod.GET })
	public Object getById(String id) {
		// 1.验证参数
		String errorTip = ValidationHelper.build()
				// 必输条件验证
				.addGroup(GroupBuilder.build(id).notEmpty().maxLength(32), "ID必须提供且长度最大为32位").validate();

		if (StringUtils.isNotEmpty(errorTip)) {
			return buildJSON(HttpStatus.BAD_REQUEST.value(), errorTip);
		}

		try {
			Product product = productSerivce.getById(id);

			if (null == product) {
				return buildJSON(0, "未查询到信息");
			}

			// 增加日志
			// operLogService.addSystemLog(OperLog.operTypeEnum.select,
			// OperLog.actionSystemEnum.product,JSON.toJSONString(product,
			// SerializerFeature.IgnoreNonFieldGetter));

			// 去除不需要的字段（返回前端数据）
			String jsonStr = JSON.toJSONString(product,
					FastjsonUtils.newIgnorePropertyFilter("extra", "updateName", "updateDate", "createName", "createDate"),
					SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty);

			return AllResult.okJSON(JSON.parse(jsonStr));
		} catch (Exception e) {
			e.getStackTrace();
			LOGGER.error("get Product fail:", e.getMessage());
			operLogService.addSystemLog(OperLog.operTypeEnum.select, OperLog.actionSystemEnum.product, null,
					OperLog.logLevelEnum.error);
		}
		return buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误");
	}

	/**
	 * 查询所有
	 */
	@RequestMapping(value = "/getAll", method = { RequestMethod.POST, RequestMethod.GET })
	public Object getAll() {

		try {
			List<Product> products = productSerivce.getAll();

			if (null == products || products.size() == 0) {
				return AllResult.build(1, "未查询到信息");
			}

			// 去除不需要的字段
			String jsonStr = JSON.toJSONString(products,
					FastjsonUtils.newIgnorePropertyFilter("extra", "updateName", "updateDate", "createName", "createDate"),
					SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty);

			return AllResult.okJSON(JSON.parse(jsonStr));
		} catch (Exception e) {
			e.getStackTrace();
			LOGGER.error("getAll Product fail:", e.getMessage());
			operLogService.addSystemLog(OperLog.operTypeEnum.select, OperLog.actionSystemEnum.product, null,
					OperLog.logLevelEnum.error);
		}
		return buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误");
	}

	/**
	 * 分页获取信息
	 */
	@RequestMapping(value = "/datagrid", method = { RequestMethod.POST, RequestMethod.GET })
	public Object getDatagrid(ProductForm form) {

		// 1.验证参数
		String errorTip = ValidationHelper.build()
				// 必输条件验证
				.addGroup(GroupBuilder.build(form.getPageNum()).notNull().minValue(1), "页码必须从1开始")
				.addGroup(GroupBuilder.build(form.getPageSize()).notNull().minValue(1), "每页记录数量最少1条").validate();

		if (!StringUtils.isEmpty(errorTip)) {
			return buildJSON(HttpStatus.BAD_REQUEST.value(), errorTip);
		}

		try {
			ProductExample example = new ProductExample();
			ProductExample.Criteria criteria = example.createCriteria();
			// 条件设置
			if (StringUtils.isNotEmpty(form.getName())) {
				criteria.andNameLike("%" + form.getName().trim() + "%");
			}
			if (StringUtils.isNotEmpty(form.getTypeId())) {
				criteria.andTypeIdEqualTo(form.getTypeId());
			}
			if (StringUtils.isNotEmpty(form.getBrand())) {
				criteria.andBrandLike("%" + form.getBrand().trim() + "%");
			}
			if (null != form.getHeight()) {
				criteria.andHeightEqualTo(form.getHeight());
			}
			if (null != form.getWeight()) {
				criteria.andWeightEqualTo(form.getWeight());
			}
			if (null != form.getPower()) {
				criteria.andPowerEqualTo(form.getPower());
			}
			if (null != form.getStatus()) {
				criteria.andStatusEqualTo(form.getStatus());
			}

			// 设置排序条件
			StringBuffer orderBy = new StringBuffer("");
			if (StringUtils.isNotEmpty(form.getSortName())) {
				if ("name".equalsIgnoreCase(form.getSortName())) {
					orderBy.append("name " + ("asc".equalsIgnoreCase(form.getSortDesc()) ? "asc" : "desc") + ",");
				} else if ("typeId".equalsIgnoreCase(form.getSortName())) {
					orderBy.append("type_id " + ("asc".equalsIgnoreCase(form.getSortDesc()) ? "asc" : "desc") + ",");
				} else if ("brand".equalsIgnoreCase(form.getSortName())) {
					orderBy.append("brand " + ("asc".equalsIgnoreCase(form.getSortDesc()) ? "asc" : "desc") + ",");
				} else if ("height".equalsIgnoreCase(form.getSortName())) {
					orderBy.append("height " + ("asc".equalsIgnoreCase(form.getSortDesc()) ? "asc" : "desc") + ",");
				} else if ("weight".equalsIgnoreCase(form.getSortName())) {
					orderBy.append("weight " + ("asc".equalsIgnoreCase(form.getSortDesc()) ? "asc" : "desc") + ",");
				} else if ("power".equalsIgnoreCase(form.getSortName())) {
					orderBy.append("power " + ("asc".equalsIgnoreCase(form.getSortDesc()) ? "asc" : "desc") + ",");
				} else if ("status".equalsIgnoreCase(form.getSortName())) {
					orderBy.append("status " + ("asc".equalsIgnoreCase(form.getSortDesc()) ? "asc" : "desc") + ",");
				}
			}

			orderBy.append("create_date desc,id asc");
			example.setOrderByClause(orderBy.toString());

			Page<Product> queryResult = productSerivce.getScrollData(form.getPageNum(), form.getPageSize(), example);

			// 去除不需要的字段
			String jsonStr = JSON.toJSONString(queryResult,
					FastjsonUtils.newIgnorePropertyFilter("extra", "updateName", "updateDate", "createName", "createDate"),
					SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty);

			return AllResult.okJSON(JSON.parse(jsonStr));
		} catch (Exception e) {
			e.getStackTrace();
			LOGGER.error("get datagrid data error. page: {}, count: {}", form.getPageNum(), form.getPageSize(), e);
			operLogService.addSystemLog(OperLog.operTypeEnum.select, OperLog.actionSystemEnum.product, null,
					OperLog.logLevelEnum.error);
		}

		return buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误");
	}

	/**
	 * 上传
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/upload", method = { RequestMethod.POST, RequestMethod.GET })
	public Object upload(HttpServletRequest request) {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		String targetPath = "3source";
		try {
			List<FileUtilBean> fileList = FileUtil.uploadFiles(multipartRequest, targetPath, false);
			String path = PropertiesUtil.getProperty(PropertiesUtil.FILE_UPLOAD_PATH) + targetPath;

			// 解析3source文件
		} catch (Exception e) {
			e.printStackTrace();
			return buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误");
		}

		return buildJSON(1, "上传成功!");
	}

	/**
	 * 3source文件处理
	 */
	private static void source3Handle(FileUtilBean fileUtilBean) throws Exception {
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
		String rsoYpPath = PropertiesUtil.getProperty(PropertiesUtil.FILE_UPLOAD_PATH) + "rso";
		for (File f : rsoPathFile.listFiles()) {
			File outFile = new File(rsoYpPath, f.getName());
			FileUtil.copy(f, outFile, true);
		}

		// 3、复制图片到服务器
		String imagesPath = basePath + "/images";
		File imagesPathFile = new File(imagesPath);
		String imgYpPath = PropertiesUtil.getProperty(PropertiesUtil.FILE_UPLOAD_PATH) + "images";
		for (File f : imagesPathFile.listFiles()) {
			File outFile = new File(imgYpPath, f.getName());
			FileUtil.copy(f, outFile, true);
		}

		// 4、读取excel
		String excelPath = basePath + "/product.xls";
		File excelFile = new File(excelPath);

	}

	public static void main(String[] args) throws Exception {
		FileUtilBean bean = new FileUtilBean();
		bean.setNewFileName("IBM System x3850 x5.3source");
		bean.setFileName("IBM System x3850 x5.3source");
		bean.setFileParentPath("D:\\opt\\test");
		bean.setFileExt("3source");
		bean.setFileRealPath(bean.getFileParentPath() + "/" + bean.getNewFileName());
		source3Handle(bean);
		System.out.println("dddddddddd");
	}
}
