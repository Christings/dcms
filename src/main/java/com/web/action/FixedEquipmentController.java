package com.web.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.web.core.action.BaseController;
import com.web.core.util.page.Page;
import com.web.entity.FixedEquipment;
import com.web.entity.OperLog;
import com.web.example.FixedEquipmentExample;
import com.web.service.FixedEquipmentService;
import com.web.util.AllResult;
import com.web.util.UUIDGenerator;
import com.web.util.fastjson.FastjsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 机柜设备管理  前端访问接口
 * 
 * @author 杜延雷
 * @date 2016-08-25
 */
@Controller
@RequestMapping("/fixed/equipment")
public class FixedEquipmentController extends BaseController {
	private static final Logger LOGGER = LoggerFactory.getLogger(FixedEquipmentController.class);

	@Autowired
	FixedEquipmentService fixedEquipmentService;

	/**
	 * 添加设备
	 */
	@RequestMapping(value = "/add", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public Object add(FixedEquipment fixedEquipment, HttpServletRequest request) {

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [fixedEquipment: {}]", JSON.toJSONString(fixedEquipment));
		}
		// TODO 需要添加判断
		if (StringUtils.isEmpty(fixedEquipment.getEquName()) || "".equals(fixedEquipment.getEquName().trim())) {
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "名称不能为空");
		} else if (StringUtils.isEmpty(fixedEquipment.getEquType())|| "".equals(fixedEquipment.getEquType().trim())) {
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "类型不能为空");
		}

		FixedEquipmentExample example = new FixedEquipmentExample();
		FixedEquipmentExample.Criteria criteria = example.createCriteria();
		criteria.andEquNameEqualTo(fixedEquipment.getEquName());
		criteria.andEquTypeEqualTo(fixedEquipment.getEquType());
		if(fixedEquipmentService.count(example)>0){
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "名称已经存在不可以重复");
		}

		try {
			fixedEquipment.setId(UUIDGenerator.generatorRandomUUID());
			if(StringUtils.isEmpty(fixedEquipment.getRsoPath())&&StringUtils.isEmpty(fixedEquipment.getMaxPath())){
				fixedEquipment.setStatus(1);
				fixedEquipment.setStatusMsg("Rso文件不存在、Max文件不存在");
			}else if(StringUtils.isEmpty(fixedEquipment.getRsoPath())){
				fixedEquipment.setStatus(1);
				fixedEquipment.setStatusMsg("Rso文件不存在");
			}else if(StringUtils.isEmpty(fixedEquipment.getMaxPath())){
				fixedEquipment.setStatus(1);
				fixedEquipment.setStatusMsg("Max文件不存在");
			}
			int result = fixedEquipmentService.save(fixedEquipment);

			// 增加日志
			operLogService.addSystemLog(OperLog.operTypeEnum.insert,
					OperLog.actionSystemEnum.fixedEquipment,
					JSON.toJSONString(fixedEquipment));

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("save result: {}", result);
			}

			//去除不需要的字段
			String jsonStr = JSON.toJSONString(fixedEquipment,
					FastjsonUtils.newIgnorePropertyFilter("updateName","updateDate","createName","createDate"),
					SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty);

			return AllResult.okJSON(JSON.parse(jsonStr));
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("save FixedEquipment fail:", e.getMessage());
			return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误,添加失败");
		}

	}

	/**
	 * 修改设备
	 */
	@RequestMapping(value = "/update", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public Object update(FixedEquipment fixedEquipment, HttpServletRequest request) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [fixedEquipment: {}]", JSON.toJSONString(fixedEquipment));
		}
		// TODO 需要添加判断
		if(StringUtils.isEmpty(fixedEquipment.getId())|| "".equals(fixedEquipment.getId().trim())){
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "id不能为空");
		}else if (StringUtils.isEmpty(fixedEquipment.getEquName())|| "".equals(fixedEquipment.getEquName().trim())) {
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "名称不能为空");
		} else if (StringUtils.isEmpty(fixedEquipment.getEquType())) {
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "类型不能为空");
		}

		FixedEquipmentExample example = new FixedEquipmentExample();
		FixedEquipmentExample.Criteria criteria = example.createCriteria();
		criteria.andEquNameEqualTo(fixedEquipment.getEquName());
		criteria.andEquTypeEqualTo(fixedEquipment.getEquType());

		List<FixedEquipment> fixedEquipmentList = fixedEquipmentService.getExample(example);
		if(fixedEquipmentList.size()>0 && fixedEquipment.getEquName().equals(fixedEquipmentList.get(0).getEquName())){
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "名称已经存在不可以重复");
		}

		try {
			if(StringUtils.isEmpty(fixedEquipment.getRsoPath())&&StringUtils.isEmpty(fixedEquipment.getMaxPath())){
				fixedEquipment.setStatus(1);
				fixedEquipment.setStatusMsg("Rso文件不存在、Max文件不存在");
			}else if(StringUtils.isEmpty(fixedEquipment.getRsoPath())){
				fixedEquipment.setStatus(1);
				fixedEquipment.setStatusMsg("Rso文件不存在");
			}else if(StringUtils.isEmpty(fixedEquipment.getMaxPath())){
				fixedEquipment.setStatus(1);
				fixedEquipment.setStatusMsg("Max文件不存在");
			}
			int result = fixedEquipmentService.updateById(fixedEquipment);

			if(result > 0){
				// 增加日志
				operLogService.addSystemLog(OperLog.operTypeEnum.update,
						OperLog.actionSystemEnum.fixedEquipment,
						JSON.toJSONString(fixedEquipment,SerializerFeature.IgnoreNonFieldGetter));
			}

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("update result: {}", result);
			}

			//去除不需要的字段
			String jsonStr = JSON.toJSONString(fixedEquipment,
					FastjsonUtils.newIgnorePropertyFilter("updateName","updateDate","createName","createDate"),
					SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty);

			return AllResult.okJSON(JSON.parse(jsonStr));
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("update FixedEquipment fail:", e.getMessage());
			return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误,修改失败");
		}
	}

	/**
	 * 删除设备
	 */
	@RequestMapping(value = "/delete", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public Object delete(String id, HttpServletRequest request) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [id: {}]", id);
		}
		// TODO 需要添加判断
		if (StringUtils.isEmpty(id)) {
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "id不能为空");
		}

		try {
			FixedEquipment fixedEquipment = fixedEquipmentService.getById(id);
			if(null == fixedEquipment){
				return AllResult.buildJSON(HttpStatus.NOT_FOUND.value(), "未查询到相关数据");
			}
			int result = fixedEquipmentService.deleteById(id);

			if(result > 0){
				// 增加日志
				operLogService.addSystemLog(OperLog.operTypeEnum.delete,
						OperLog.actionSystemEnum.fixedEquipment,
						JSON.toJSONString(fixedEquipment));
			}

			return AllResult.ok();
		} catch (Exception e) {
			LOGGER.error("delete FixedEquipment fail:", e.getMessage());
			return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误, 删除失败");
		}
	}

	/**
	 * 获取设备信息
	 */
	@RequestMapping(value = "/get", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public Object getById(String id, HttpServletRequest request) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [id: {}]", id);
		}
		// TODO 需要添加判断
		if (StringUtils.isEmpty(id)) {
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "id不能为空");
		}

		try {
			FixedEquipment fixedEquipment = fixedEquipmentService.getById(id);

			if(null != fixedEquipment){
				// 增加日志
				operLogService.addSystemLog(OperLog.operTypeEnum.select,
						OperLog.actionSystemEnum.fixedEquipment,
						JSON.toJSONString(fixedEquipment,SerializerFeature.IgnoreNonFieldGetter));
			}else{
				return AllResult.buildJSON(HttpStatus.NOT_FOUND.value(), "未找到用户数据");
			}

			//去除不需要的字段
			String jsonStr = JSON.toJSONString(fixedEquipment,
					FastjsonUtils.newIgnorePropertyFilter("updateName","updateDate","createName","createDate"),
					SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty);

			return AllResult.okJSON(JSON.parse(jsonStr));
		} catch (Exception e) {
			LOGGER.error("get FixedEquipment fail:", e.getMessage());
			return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误, 获取设备信息失败");
		}
	}


	/**
	 * 查询所有设备信息
	 */
	@RequestMapping(value="/getAll",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public Object getAll(HttpServletRequest request){

		try {
			List<FixedEquipment> fixedEquipments = fixedEquipmentService.getAll();

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("fixedEquipments result: {}", JSON.toJSONString(fixedEquipments));
			}

			if(null == fixedEquipments || fixedEquipments.size() == 0){
				return AllResult.buildJSON(1, "未获取到数据");
			}

			//去除不需要的字段
			String jsonStr = JSON.toJSONString(fixedEquipments,
					FastjsonUtils.newIgnorePropertyFilter("updateName","updateDate","createName","createDate"),
					SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty);

			return AllResult.okJSON(JSON.parse(jsonStr));
		} catch (Exception e) {
			LOGGER.error("getAll FixedEquipment fail:", e.getMessage());
			return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误,获取全部用户失败") ;
		}
	}

	/**
	 * 分页获取设备信息
	 *
	 * @param pageNum
	 *            当前页
	 * @param pageSize
	 *            显示多少行
	 */
	@RequestMapping(value="/datagrid",method= {RequestMethod.POST, RequestMethod.GET})
	@ResponseBody
	public Object getDataGrid(@RequestParam(value = "pageNum") int pageNum,
							  @RequestParam(value = "pageSize") int pageSize,
							  @RequestParam(value = "equType") String equType,
							  @RequestParam(required = false,value = "equName") String equName,
							  HttpServletRequest request) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [page: {}, count: {}]", pageNum, pageSize);
		}

		// 校验参数
		if (pageNum < 1 || pageSize < 1) {
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "参数异常");
		}else if(StringUtils.isEmpty(equType)){
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "类型不能为空");
		}

		try {

			FixedEquipmentExample example = new FixedEquipmentExample();
			// 排序设置
			FixedEquipmentExample.Criteria criteria = example.createCriteria();
			criteria.andEquTypeEqualTo(equType);
			// 条件设置
			if(!StringUtils.isEmpty(equName)){
				criteria.andEquNameLike("%"+equName+"%");
			}

			Page<FixedEquipment> queryResult = fixedEquipmentService.getScrollData(pageNum, pageSize, example);


			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("queryResult record count: {}", queryResult.getRecords().size());
			}

			//去除不需要的字段
			String jsonStr = JSON.toJSONString(queryResult,
					FastjsonUtils.newIgnorePropertyFilter("updateName","updateDate","createName","createDate"),
					SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty);

			return AllResult.okJSON(JSON.parse(jsonStr));

		} catch (Exception e) {
			LOGGER.error("get datagrid data error. page: {}, count: {}",pageNum, pageSize,  e);
		}

		return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误");
	}
}
