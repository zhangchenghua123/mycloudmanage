/*
 * Copyright 2014 etao.com All right reserved. This software is the
 * confidential and proprietary information of etao.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with etao.com .
 */
package org.dlut.mycloudmanage.controller.admin;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dlut.mycloudmanage.biz.ClassBiz;
import org.dlut.mycloudmanage.common.constant.MenuEnum;
import org.dlut.mycloudmanage.common.constant.UrlConstant;
import org.dlut.mycloudmanage.common.property.utils.MyPropertiesUtil;
import org.dlut.mycloudmanage.controller.common.BaseController;
import org.dlut.mycloudserver.client.common.MyCloudResult;
import org.dlut.mycloudserver.client.common.Pagination;
import org.dlut.mycloudserver.client.common.classmanage.ClassDTO;
import org.dlut.mycloudserver.client.common.classmanage.QueryClassCondition;
import org.dlut.mycloudserver.client.common.usermanage.QueryUserCondition;
import org.dlut.mycloudserver.client.common.usermanage.RoleEnum;
import org.dlut.mycloudserver.client.common.usermanage.UserDTO;
import org.dlut.mycloudserver.client.service.usermanage.IUserManageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

/**
 * 类ClassController.java的实现描述：TODO 类实现描述
 * 
 * @author xuyizhen Dec 11, 2014 5:12:18 PM
 */
@Controller
public class ClassController extends BaseController {

    private static Logger      log = LoggerFactory.getLogger(ClassController.class);

    @Resource(name = "classBiz")
    private ClassBiz           classBiz;

    @Resource(name = "userManageService")
    private IUserManageService userManageService;

    /**
     * 管理员-课程-显示列表
     * 
     * @param request
     * @param response
     * @param model
     * @param currentPage
     * @param perPage
     * @return
     */

    @RequestMapping(value = UrlConstant.ADMIN_CLASS_LIST)
    public String adminClassList(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                                 Integer currentPage) {

        String errorDesc = this.setDefaultEnv(request, response, model);
        if (errorDesc != null) {
            return this.goErrorPage(errorDesc);
        }
        if (currentPage == null)
            currentPage = 1;
        int PAGESIZE = Integer.parseInt(MyPropertiesUtil.getValue("pagesize"));
        QueryClassCondition queryClassCondition = new QueryClassCondition();
        queryClassCondition.setLimit(PAGESIZE);
        queryClassCondition.setOffset((currentPage - 1) * PAGESIZE);
        Pagination<ClassDTO> pageClassDTO = this.classBiz.query(queryClassCondition);
        if (pageClassDTO.getTotalPage() >= 1 && (currentPage < 1 || currentPage > pageClassDTO.getTotalPage()))
            return this.goErrorPage("该页面不存在");
        List<ClassDTO> classList = pageClassDTO.getList();
        model.put("classList", classList);
        model.put("currentPage", currentPage);
        model.put("totalPage", pageClassDTO.getTotalPage());

        this.setShowMenuList(RoleEnum.ADMIN, MenuEnum.ADMIN_CLASS_LIST, model);
        model.put("screen", "admin/class_list");
        model.put("js", "admin/class_list");
        return "default";
    }

    /**
     * 管理员-课程-删除 处理异步请求，返回JSON
     * 
     * @param classId
     * @return
     */
    @RequestMapping(value = UrlConstant.ADMIN_CLASS_REMOVE, produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public String removeClass(int classId) {
        JSONObject json = new JSONObject();
        json.put("isLogin", true);
        json.put("isAuth", true);
        if (this.classBiz.deleteClass(classId)) {
            log.info("删除" + classId + "成功");
            json.put("isSuccess", true);
            json.put("message", "删除成功");
            json.put("data", "");
            return json.toString();
        }
        log.info("删除" + classId + "失败");
        json.put("isSuccess", false);
        json.put("message", "该课程不存在");
        json.put("data", "");

        return json.toString();

    }

    /**
     * 管理员-课程-删除全部 处理异步请求，返回JSON 没有实现
     * 
     * @return
     */
    @RequestMapping(value = UrlConstant.ADMIN_CLASS_REMOVEALL, produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public String removeAll() {
        JSONObject json = new JSONObject();
        json.put("isLogin", true);
        json.put("isAuth", true);
        json.put("isSuccess", true);
        json.put("message", "删除成功");
        json.put("data", "");
        return json.toString();
    }

    @RequestMapping(value = UrlConstant.ADMIN_CLASS_ADD_FORM)
    public String addClassForm(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        this.setShowMenuList(RoleEnum.ADMIN, MenuEnum.ADMIN_CLASS_LIST, model);

        QueryUserCondition queryUserCondition = new QueryUserCondition();
        queryUserCondition.setRole(RoleEnum.TEACHER);
        MyCloudResult<Pagination<UserDTO>> result = userManageService.query(queryUserCondition);
        if (!result.isSuccess()) {
            log.warn("查询用户失败，原因：" + result.getMsgInfo());
            return this.goErrorPage("查询用户失败");
        }
        Pagination<UserDTO> paginateion = result.getModel();
        model.put("userList", paginateion.getList());

        model.put("screen", "admin/class_add_form");
        model.put("js", "admin/class_list");
        return "default";
    }

    @RequestMapping(value = UrlConstant.ADMIN_CLASS_ADD, produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public String addClass(HttpServletRequest request, HttpServletResponse response, ModelMap model, String className,
                           String teacherAccount) {

        JSONObject json = new JSONObject();
        json.put("isLogin", true);
        json.put("isAuth", true);

        if (className.isEmpty()) {
            json.put("isSuccess", false);
            json.put("message", "课程名不能为空");
            return json.toString();
        }

        // 检查该老师的课程是否已经存在
        QueryClassCondition queryClassCondition = new QueryClassCondition();
        queryClassCondition.setClassName(className);
        queryClassCondition.setTeacherAccount(teacherAccount);
        if (this.classBiz.query(queryClassCondition).getTotalCount() > 0) {
            json.put("isSuccess", false);
            json.put("message", "要添加的记录已经存在");
            return json.toString();
        }

        ClassDTO classDTO = new ClassDTO();
        classDTO.setClassName(className);
        classDTO.setTeacherAccount(teacherAccount);
        if (this.classBiz.createClass(classDTO) > 0) {
            json.put("isSuccess", true);
            json.put("message", "添加成功");
            return json.toString();
        }
        json.put("isSuccess", false);
        json.put("message", "添加失败");
        return json.toString();
    }

    /**
     * 返回编辑表单
     * 
     * @param request
     * @param response
     * @param model
     * @param classId
     * @return
     */
    @RequestMapping(value = UrlConstant.ADMIN_CLASS_EDIT_FORM)
    public String editForm(HttpServletRequest request, HttpServletResponse response, ModelMap model, int classId) {
        String errorDesc = this.setDefaultEnv(request, response, model);
        if (errorDesc != null) {
            return this.goErrorPage(errorDesc);
        }
        // 查询要修改的内容，以显示在编辑表单中
        QueryClassCondition queryclassCondition = new QueryClassCondition();
        queryclassCondition.setClassId(classId);
        ClassDTO classDTO = this.classBiz.query(queryclassCondition).getList().get(0);
        model.put("class", classDTO);
        this.setShowMenuList(RoleEnum.ADMIN, MenuEnum.ADMIN_CLASS_LIST, model);
        model.put("screen", "admin/class_edit_form");
        model.put("js", "admin/class_list");
        return "default";
    }

    /**
     * 处理更新异步请求，返回JSON
     * 
     * @param request
     * @param response
     * @param model
     * @param classId
     * @param className
     * @param teacherAccount
     * @return
     */
    @RequestMapping(value = UrlConstant.ADMIN_CLASS_EDIT, produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public String editClass(HttpServletRequest request, HttpServletResponse response, ModelMap model, String className,
                            String teacherAccount, int classId) {
        JSONObject json = new JSONObject();
        json.put("isLogin", true);
        json.put("isAuth", true);

        if (className.isEmpty()) {
            json.put("isSuccess", false);
            json.put("message", "课程名不能为空");
            return json.toString();
        }

        // 检查教师是否存在
        QueryClassCondition queryclassCondition2 = new QueryClassCondition();
        queryclassCondition2.setTeacherAccount(teacherAccount);
        Pagination<ClassDTO> page2 = this.classBiz.query(queryclassCondition2);

        if (page2.getTotalCount() <= 0) {
            json.put("isSuccess", false);
            json.put("message", "要编辑的教师不存在");
            return json.toString();
        }

        ClassDTO classDTO = new ClassDTO();
        classDTO.setClassId(classId);
        classDTO.setClassName(className);
        classDTO.setTeacherAccount(teacherAccount);
        if (this.classBiz.updateClass(classDTO)) {
            json.put("isSuccess", true);
            json.put("message", "更新成功");
            return json.toString();
        }
        json.put("isSuccess", false);
        json.put("message", "更新失败");
        return json.toString();

    }
}
