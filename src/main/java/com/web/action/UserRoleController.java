package com.web.action;

import com.alibaba.fastjson.JSON;
import com.web.core.action.BaseController;
import com.web.core.util.page.Page;
import com.web.entity.UserRole;
import com.web.service.RoleUserService;
import com.web.util.AllResult;
import com.web.util.StringUtil;
import com.web.util.UUIDGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by think on 2016/7/16.
 */
@Controller
@RequestMapping("/userRole")
public class UserRoleController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRoleController.class);

    @Autowired
    private RoleUserService roleUserService;

    /**
     * 单个关联用户与角色
     * @param userRole
     * @param request
     * @return
     */
    @RequestMapping(value = "/addUserRole" ,method= RequestMethod.POST)
    @ResponseBody
    public Object addUserRole(@RequestBody UserRole userRole, HttpServletRequest request){
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("request param: [userRole: {}]", JSON.toJSONString(userRole));
        }
        if(userRole.getRoleid()==null|| StringUtil.isEmpty(userRole.getRoleid())){
            return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "请求参数异常:角色ID不能为空");
        }else if (userRole.getUserid()==null|| StringUtil.isEmpty(userRole.getUserid())){
            return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "请求参数异常:用户ID不能为空");
        }
        try {
            userRole.setId(UUIDGenerator.generatorRandomUUID());
           int result=roleUserService.save(userRole);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("save result: {}", result);
            }
            return AllResult.okJSON(userRole);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("save User fail:", e.getMessage());
            return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误,用户关联角色失败");
        }

    }


    /**
     * 批量关联用户与角色
     * @param userId
     * @param roleIds
     * @param request
     * @return
     */
    @RequestMapping(value = "/addUserRoleList" ,method= RequestMethod.POST)
    @ResponseBody
    public Object addUserRoleList(@RequestParam(value = "userId") String userId, @RequestParam(value = "roleIds") String  roleIds, HttpServletRequest request){
        List<UserRole> userRoleList=new ArrayList<UserRole>();
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("request param: roleIds: {}]",roleIds);
        }
        if(StringUtil.isEmpty(userId)){
            return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "请求参数异常:用户ID不能为空");
        }
        try{
        if(StringUtil.isEmpty(roleIds)){
            int result=roleUserService.deleteByUserId(userRoleList.get(0).getUserid());
            return AllResult.okJSON(userRoleList);
        }else{
            String[] roles=roleIds.split(",");
            for(String roleId:roles){
                UserRole userRole=new UserRole();
                userRole.setId(UUIDGenerator.generatorRandomUUID());
                userRole.setUserid(userId);
                userRole.setRoleid(roleId);
                userRoleList.add(userRole);
            }
            int result=roleUserService.saveList(userRoleList);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("save result: {}", result);
            }
            return AllResult.okJSON(userRoleList);
          }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("save User fail:", e.getMessage());
            return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误,用户关联角色失败");
        }
    }

    /**
     * 根据用户ID查询相关角色
     * @param pageNum
     * @param pageSize
     * @param userId
     * @param request
     * @return
     */
    @RequestMapping(value = "/getUserRoleListByuserId" ,method= RequestMethod.POST)
    @ResponseBody
    public Object getUserRoleListByuserId(@RequestParam(value = "pageNum") int pageNum, @RequestParam(value = "pageSize") int pageSize,@RequestParam(value = "userId") String userId, HttpServletRequest request) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("request param: [userRole: {}]",userId);
        }

            if (userId == null || StringUtil.isEmpty(userId)) {
                return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "请求参数异常:userId不能为空");
            }

        try {
                Page<UserRole> page = roleUserService.getScrollDataByUserId(pageNum,pageSize,userId);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("save result: {}", page);
            }
            return AllResult.okJSON(page);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("save User fail:", e.getMessage());
            return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误,用户关联角色失败");
        }
    }

    /**
     * 根据角色ID分页查询相关用户
     * @param pageNum
     * @param pageSize
     * @param roleId
     * @param request
     * @return
     */
    @RequestMapping(value = "/getUserRoleListByroleId" ,method= RequestMethod.POST)
    @ResponseBody
    public Object getUserRoleListByroleId(@RequestParam(value = "pageNum") int pageNum, @RequestParam(value = "pageSize") int pageSize,@RequestParam(value = "roleId") String roleId, HttpServletRequest request) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("request param: [userRole: {}]",roleId);
        }

        if (roleId == null || StringUtil.isEmpty(roleId)) {
            return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "请求参数异常:roleId不能为空");
        }

        try {
            Page<UserRole> page = roleUserService.getScrollDataByRoleId(pageNum,pageSize,roleId);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("save result: {}", page);
            }
            return AllResult.okJSON(page);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("save User fail:", e.getMessage());
            return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误,根据角色分页查询失败");
        }
    }

    /**
     * 根据ID删除用户角色关联关系
     * @param userId
     * @param request
     * @return
     */
    @RequestMapping(value = "/deleteByuserId" ,method= RequestMethod.POST)
    @ResponseBody
    public Object deleteByuserId(@RequestParam(value = "userId") String  userId, HttpServletRequest request){
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("request param: [userId: {}]",userId);
        }
        if(StringUtil.isEmpty(userId)){
            return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "请求参数异常:userId不能为空");
        }
        try {
           int result=roleUserService.deleteByUserId(userId);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("save result: {}", result);
            }
            return AllResult.okJSON(userId);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("save User fail:", e.getMessage());
            return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误,根据用户删除失败");
        }
    }


}
