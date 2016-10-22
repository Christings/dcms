package com.web.action.xtgl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.web.bean.result.RoleUserResult;
import com.web.bean.result.UserResult;
import com.web.bean.result.UserRoleResult;
import com.web.core.action.BaseController;
import com.web.entity.OperLog;
import com.web.entity.Role;
import com.web.entity.User;
import com.web.entity.UserRole;
import com.web.example.UserExample;
import com.web.service.RoleSerivce;
import com.web.service.UserRoleService;
import com.web.service.UserService;
import com.web.util.AllResult;
import com.web.util.UUIDGenerator;
import com.web.util.fastjson.FastjsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户角色关系 访问接口
 *
 * @author 杜延雷
 * @date 2016-08-12
 */
@RestController
@RequestMapping("/user/role")
public class UserRoleController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserRoleController.class);

    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private RoleSerivce roleSerivce;
    @Autowired
    private UserService userService;

    /**
     * 单个添加用户角色
     */
    @RequestMapping(value = "/add", method = {RequestMethod.POST,RequestMethod.GET})
    public Object add(UserRole userRole, HttpServletRequest request) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("request param: [userRole: {}]", JSON.toJSONString(userRole));
        }

        // TODO 需要添加判断
        if (StringUtils.isEmpty(userRole.getRoleId())) {
            return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "角色ID不能为空");
        }else if(StringUtils.isEmpty(userRole.getUserId())){
            return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "用户ID不能为空");
        }

        try {
            userRole.setId(UUIDGenerator.generatorRandomUUID());
            int result = userRoleService.save(userRole);

            if(result > 0){
                operLogService.addSystemLog(OperLog.operTypeEnum.insert, OperLog.actionSystemEnum.userRole,
                        JSON.toJSONString(userRole));
            }

            return AllResult.ok();
        } catch (Exception e) {
            LOGGER.error("save userRole object error. : {}", JSON.toJSONString(userRole), e);
        }

        return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误,添加角色-用户关系失败");
    }

    /**
     * 根据角色批量保存用户
     *
     * @param {roleId,userIds,request}
     * @return
     */
    @RequestMapping(value = "/batchUsers", method = RequestMethod.POST)
    public Object batchUsers(String roleId,String userIds, HttpServletRequest request) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("request param: [roleId: {},userIds:{}]",roleId,userIds );
        }

        // TODO 需要添加判断
        if (StringUtils.isEmpty(roleId)) {
            return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "角色ID不能为空");
        }else if(StringUtils.isEmpty(userIds)){
            return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "用户ID不能为空");
        }

        String [] userIdArr = userIds.split(",");
        if(userIdArr.length<=0){
            return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "用户ID传递有误,格式[用户ID1,用户ID2,...]");
        }

        try {
            userRoleService.batchRoleUser(roleId,userIdArr);

            operLogService.addSystemLog(OperLog.operTypeEnum.insert, OperLog.actionSystemEnum.userRole,
                    "角色ID："+roleId+",用户IDs:["+userIdArr+"]");

            return AllResult.ok();
        } catch (Exception e) {
            LOGGER.error("batchUsers save object error. : {}", e);
        }

        return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误,根据角色ID批量保存用户失败");
    }

    /**
     * 根据用户批量保存角色
     *
     * @param {userId,roleIds,request}
     * @return
     */
    @RequestMapping(value = "/batchRoles", method = RequestMethod.POST)
    public Object batchRoles(String userId,String roleIds, HttpServletRequest request) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("request param: [userId: {},roleIds:{}]",userId,roleIds );
        }

        // TODO 需要添加判断
        if (StringUtils.isEmpty(userId)) {
            return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "用户ID不能为空");
        }else if(StringUtils.isEmpty(roleIds)){
            return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "角色ID不能为空");
        }

        String [] reloIdArr = roleIds.split(",");
        if(reloIdArr.length<=0){
            return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "角色ID传递有误,格式[角色ID1,角色ID2,...]");
        }

        try {
            userRoleService.batchUserRole(userId,reloIdArr);

            operLogService.addSystemLog(OperLog.operTypeEnum.insert, OperLog.actionSystemEnum.userRole,
                    "用户ID："+userId+",角色IDs:["+reloIdArr+"]");

            return AllResult.okJSON("保存成功");
        } catch (Exception e) {
            LOGGER.error("batchRoles save object error. : {}", e);
        }

        return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误,根据用户ID批量保存角色失败");
    }

    /**
     * 根据角色Id 获取所有角色用户关系数据
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/getRoleId", method = RequestMethod.POST)
    public Object getRoleId(String roleId,HttpServletRequest request) {
        try {
            if(StringUtils.isEmpty(roleId)){
                return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "角色ID不能为空");
            }

            List<UserRole> userRoleList = userRoleService.getRoleUser(roleId);

            if(null == userRoleList || userRoleList.size() == 0){
                return AllResult.build(1, "未查询到相关数据");
            }

            // 增加日志
            operLogService.addSystemLog(OperLog.operTypeEnum.select, OperLog.actionSystemEnum.userRole,null);

            return AllResult.okJSON(userRoleList);
        } catch (Exception e) {
            LOGGER.error("userRole object error. getRoleId ", e);
            operLogService.addSystemLog(OperLog.operTypeEnum.select, OperLog.actionSystemEnum.userRole, null,
                    OperLog.logLevelEnum.error);

        }

        return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误,获取角色用户时失败");
    }

    /**
     * 根据角色Id 获取所有相关用户信息
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/getUsers", method = RequestMethod.POST)
    public Object getUsers(String roleId,HttpServletRequest request) {
        try {
            if(StringUtils.isEmpty(roleId)){
                return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "角色ID不能为空");
            }

            List<UserRole> userRoleList = userRoleService.getRoleUser(roleId);

            if(null == userRoleList || userRoleList.size() == 0){
                return AllResult.build(1, "未查询到相关数据");
            }

            RoleUserResult roleUserResult = new RoleUserResult();
            roleUserResult.setRole(roleSerivce.getById(userRoleList.get(0).getRoleId()));
            for(UserRole userRole:userRoleList){
                roleUserResult.add(userService.getById(userRole.getUserId()));
            }

            // 增加日志
            // operLogService.addSystemLog(OperLog.operTypeEnum.select, OperLog.actionSystemEnum.userRole,null);

            //去除不需要的字段
            String jsonStr = JSON.toJSONString(roleUserResult,
                    FastjsonUtils.newIgnorePropertyFilter("records","password","updateName","updateDate","createName","createDate"),
                    SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty);

            return AllResult.okJSON(JSON.parse(jsonStr));
        } catch (Exception e) {
            LOGGER.error("userRole object error. getUsers ", e);
            operLogService.addSystemLog(OperLog.operTypeEnum.select, OperLog.actionSystemEnum.userRole, null,
                    OperLog.logLevelEnum.error);

        }

        return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误,获取角色下所有用户信息失败");
    }

    /**
     * 根据角色Id 获取所有相关用户信息
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/getUserAll", method = RequestMethod.POST)
    public Object getUserAll(String roleId,HttpServletRequest request) {
        try {
            if(StringUtils.isEmpty(roleId)){
                return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "角色ID不能为空");
            }

            Role role = roleSerivce.getById(roleId);
            if(null == role){
                return AllResult.buildJSON(HttpStatus.NOT_FOUND.value(), "未找到角色数据");
            }

            //返回的结果集合
            RoleUserResult roleUserResult = new RoleUserResult();
            roleUserResult.setRole(role);

            //查找关联数据
            List<UserRole> userRoleList = userRoleService.getRoleUser(roleId);
            List<String> userIdList = new ArrayList<>();
            for(UserRole userRole:userRoleList){
                userIdList.add(userRole.getUserId());
            }

            //查询所有未删除用户
            UserExample example = new UserExample();
            UserExample.Criteria criteria = example.createCriteria();
            criteria.andStatusNotEqualTo((short)2);
            List<User> users = userService.getExample(example);
            for(User user:users){
                UserResult userResult = new UserResult();
                BeanUtils.copyProperties(user,userResult);
                if(userIdList.size()>0 && userIdList.contains(user.getId())){
                    userResult.setChecked(1);
                }
                roleUserResult.add(userResult);
            }

            //去除不需要的字段
            String jsonStr = JSON.toJSONString(roleUserResult,
                    FastjsonUtils.newIgnorePropertyFilter("users","password","updateName","updateDate","createName","createDate"),
                    SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty);

            return AllResult.okJSON(JSON.parse(jsonStr));
        } catch (Exception e) {
            LOGGER.error("userRole object error. getUserAll ", e);
            operLogService.addSystemLog(OperLog.operTypeEnum.select, OperLog.actionSystemEnum.userRole, null,
                    OperLog.logLevelEnum.error);

        }

        return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误,获取角色下所有用户信息失败");
    }

    /**
     * 根据用户Id 获取所有用户角色关系数据
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/getUserId", method = RequestMethod.POST)
    public Object getUserId(String userId,HttpServletRequest request) {
        try {
            if(StringUtils.isEmpty(userId)){
                return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "用户ID不能为空");
            }

            List<UserRole> userRoleList = userRoleService.getUserRole(userId);

            if(null == userRoleList || userRoleList.size() == 0){
                return AllResult.build(1, "未查询到相关数据");
            }

            // 增加日志
//            operLogService.addSystemLog(OperLog.operTypeEnum.select, OperLog.actionSystemEnum.userRole,null);

            return AllResult.okJSON(userRoleList);
        } catch (Exception e) {
            LOGGER.error("userRole object error. getUserId ", e);
            operLogService.addSystemLog(OperLog.operTypeEnum.select, OperLog.actionSystemEnum.userRole, null,
                    OperLog.logLevelEnum.error);
        }

        return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误,获取用户角色时失败");
    }

    /**
     * 根据用户Id 获取所有用户角色关系数据
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/getRoles", method = RequestMethod.POST)
    public Object getRoles(String userId,HttpServletRequest request) {
        try {
            if(StringUtils.isEmpty(userId)){
                return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "用户ID不能为空");
            }

            List<UserRole> userRoleList = userRoleService.getUserRole(userId);

            if(null == userRoleList || userRoleList.size() == 0){
                return AllResult.build(1, "未查询到相关数据");
            }

            UserRoleResult userRoleResult = new UserRoleResult();
            userRoleResult.setUser(userService.getById(userRoleList.get(0).getUserId()));
            for(UserRole userRole:userRoleList){
                userRoleResult.add(roleSerivce.getById(userRole.getRoleId()));
            }

            // 增加日志
            // operLogService.addSystemLog(OperLog.operTypeEnum.select, OperLog.actionSystemEnum.userRole,null);

            //去除不需要的字段
            String jsonStr = JSON.toJSONString(userRoleResult,
                    FastjsonUtils.newIgnorePropertyFilter("password","updateName","updateDate","createName","createDate"),
                    SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty);

            return AllResult.okJSON(JSON.parse(jsonStr));
        } catch (Exception e) {
            LOGGER.error("userRole object error. getRoles ", e);
            operLogService.addSystemLog(OperLog.operTypeEnum.select, OperLog.actionSystemEnum.userRole, null,
                    OperLog.logLevelEnum.error);
        }

        return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误,获取用户下所有角色信息失败");
    }

    /**
     * 获取所有用户角色关系
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/getAll", method = {RequestMethod.GET,RequestMethod.POST})
    public Object getAll(HttpServletRequest request) {
        try {
            List<UserRole> userRoleList = userRoleService.getAll();

            if(null == userRoleList || userRoleList.size() == 0){
                return AllResult.build(1, "未查询到相关数据");
            }

            // 增加日志
//            operLogService.addSystemLog(OperLog.operTypeEnum.select, OperLog.actionSystemEnum.userRole,null);

            return AllResult.okJSON(userRoleList);
        } catch (Exception e) {
            LOGGER.error("userRole object error. getAll ", e);
            operLogService.addSystemLog(OperLog.operTypeEnum.select, OperLog.actionSystemEnum.userRole, null,
                    OperLog.logLevelEnum.error);
        }

        return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误,获取所有角色用户失败");
    }
}
