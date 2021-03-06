///*
// * Copyright 2015 etao.com All right reserved. This software is the
// * confidential and proprietary information of etao.com ("Confidential
// * Information"). You shall not disclose such Confidential Information and shall
// * use it only in accordance with the terms of the license agreement you entered
// * into with etao.com .
// */
//package org.dlut.mycloudmanage.controller.admin;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.commons.lang3.StringUtils;
//import org.dlut.mycloudmanage.biz.PerformanceMonitorBiz;
//import org.dlut.mycloudmanage.common.constant.MenuEnum;
//import org.dlut.mycloudmanage.common.constant.UrlConstant;
//import org.dlut.mycloudmanage.common.property.utils.MyPropertiesUtil;
//import org.dlut.mycloudmanage.common.utils.MyJsonUtils;
//import org.dlut.mycloudmanage.controller.common.BaseController;
//import org.dlut.mycloudserver.client.common.Pagination;
//import org.dlut.mycloudserver.client.common.performancemonitor.PerformanceMonitorDTO;
//import org.dlut.mycloudserver.client.common.performancemonitor.QueryPerformanceMonitorCondition;
//import org.dlut.mycloudserver.client.common.usermanage.RoleEnum;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//
///**
// * 类MonitorController.java的实现描述：TODO 类实现描述
// * 
// * @author luojie 2015年4月28日 下午3:14:57
// */
//@Controller
//public class MonitorController extends BaseController {
//
//    private static Logger         log = LoggerFactory.getLogger(MonitorController.class);
//
//    @Resource(name = "performanceMonitorBiz")
//    private PerformanceMonitorBiz performanceMonitorBiz;
//
//    @RequestMapping(value = UrlConstant.ADMIN_MONITOR_LIST)
//    public String monitorList(HttpServletRequest request, HttpServletResponse response, ModelMap model,
//                              Integer currentPage) {
//        if (currentPage == null) {
//            currentPage = 1;
//        }
//        model.put("currentPage", currentPage);
//        String errorDesc = this.setDefaultEnv(request, response, model);
//        if (errorDesc != null) {
//            return this.goErrorPage(errorDesc);
//        }
//        this.setShowMenuList(RoleEnum.ADMIN, MenuEnum.ADMIN_MONITOR_LIST, model);
//        model.put("screen", "admin/monitor_list");
//        model.put("js", "admin/monitor_list");
//        return "default";
//    }
//
//    @RequestMapping(value = UrlConstant.ADMIN_MONITOR_GETLIST, produces = { "application/json;charset=UTF-8" })
//    @ResponseBody
//    public String getMonitorListByAjax(Integer currentPage) {
//        JSONObject json = new JSONObject();
//
//        if (currentPage == null || currentPage <= 0) {
//            currentPage = 1;
//        }
//        QueryPerformanceMonitorCondition queryPerformanceMonitorCondition = new QueryPerformanceMonitorCondition();
//        int PAGESIZE = Integer.parseInt(MyPropertiesUtil.getValue("pagesize"));
//        queryPerformanceMonitorCondition.setLimit(PAGESIZE);
//        queryPerformanceMonitorCondition.setOffset((currentPage - 1) * PAGESIZE);
//        Pagination<PerformanceMonitorDTO> pagination = performanceMonitorBiz.query(queryPerformanceMonitorCondition);
//        if (pagination == null) {
//            return MyJsonUtils.getFailJsonString(json, "获取性能数据失败");
//        }
//
//        JSONArray data = new JSONArray();
//        for (PerformanceMonitorDTO performanceMonitorDTO : pagination.getList()) {
//            JSONObject monitorData = new JSONObject();
//            monitorData.put("id", performanceMonitorDTO.getId());
//            monitorData.put("aliaseName", performanceMonitorDTO.getAliaseName());
//            monitorData.put("ip", performanceMonitorDTO.getIp());
//            monitorData.put("status", performanceMonitorDTO.getPerformanceMonitorStatus().getDesc());
//            monitorData.put("cores", performanceMonitorDTO.getCores());
//            monitorData.put("loadAverage", performanceMonitorDTO.getLoadAverage());
//            monitorData.put("totalMem", performanceMonitorDTO.getTotalMem());
//            monitorData.put("usedMem", performanceMonitorDTO.getUsedMem());
//            monitorData.put("sendRate", performanceMonitorDTO.getSendRate());
//            monitorData.put("receiveRate", performanceMonitorDTO.getReceiveRate());
//            data.add(monitorData);
//        }
//        return MyJsonUtils.getSuccessJsonString(json, "获取数据成功", data.toString());
//    }
//
//    @RequestMapping(value = UrlConstant.ADMIN_MONITOR_ADD_FORM)
//    public String monitorAddForm(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
//        String errorDesc = this.setDefaultEnv(request, response, model);
//        if (errorDesc != null) {
//            return this.goErrorPage(errorDesc);
//        }
//
//        this.setShowMenuList(RoleEnum.ADMIN, MenuEnum.ADMIN_MONITOR_LIST, model);
//        model.put("screen", "admin/monitor_add_form");
//        model.put("js", "admin/monitor_add");
//        return "default";
//    }
//
//    @RequestMapping(value = UrlConstant.ADMIN_MONITOR_ADD, produces = { "application/json;charset=UTF-8" })
//    @ResponseBody
//    public String monitorAdd(HttpServletRequest request, HttpServletResponse response, ModelMap model,
//                             String aliaseName, String ip) {
//
//        JSONObject json = new JSONObject();
//
//        if (StringUtils.isBlank(aliaseName)) {
//            return MyJsonUtils.getFailJsonString(json, "名不能为空");
//        }
//
//        if (StringUtils.isBlank(ip)) {
//            return MyJsonUtils.getFailJsonString(json, "ip不能为空");
//        }
//
//        PerformanceMonitorDTO performanceMonitorDTO = new PerformanceMonitorDTO();
//        performanceMonitorDTO.setAliaseName(aliaseName);
//        performanceMonitorDTO.setIp(ip);
//        int id = performanceMonitorBiz.createPerformanceMonitor(performanceMonitorDTO);
//        if (id == 0) {
//            return MyJsonUtils.getFailJsonString(json, "创建失败");
//        }
//        return MyJsonUtils.getSuccessJsonString(json, "");
//    }
//
//    @RequestMapping(value = UrlConstant.ADMIN_MONITOR_REMOVE, produces = { "application/json;charset=UTF-8" })
//    @ResponseBody
//    public String monitorRemove(HttpServletRequest request, HttpServletResponse response, ModelMap model, Integer id) {
//        JSONObject json = new JSONObject();
//
//        if (id == null) {
//            return MyJsonUtils.getFailJsonString(json, "id不能为空");
//        }
//
//        if (!performanceMonitorBiz.deletePerformanceMonitor(id)) {
//            return MyJsonUtils.getFailJsonString(json, "删除失败");
//        }
//        return MyJsonUtils.getSuccessJsonString(json, "");
//    }
//}
