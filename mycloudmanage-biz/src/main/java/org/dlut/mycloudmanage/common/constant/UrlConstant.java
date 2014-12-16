/*
 * Copyright 2014 etao.com All right reserved. This software is the
 * confidential and proprietary information of etao.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with etao.com .
 */
package org.dlut.mycloudmanage.common.constant;

/**
 * 类UrlConstant.java的实现描述：TODO 类实现描述
 * 
 * @author luojie.lj 2014年9月25日 上午11:01:57
 */
public class UrlConstant {
	/**
	 * 登陆页面
	 */
	public static final String LOGIN_URL = "/login";

	/**
	 * 登出页面
	 */
	public static final String LOGOUT_URL = "/logout";

	/**
	 * 错误页面
	 */
	public static final String ERROR_URL = "/error";

	public static final String DEFAULT_URL = "index";

	/**
	 * 学生角色登陆后的默认页
	 */
	public static final String STUDENT_DEFAULT_URL = "/student/vm/list";

	/**
	 * 教师角色登陆后的默认页
	 */
	public static final String TEACHER_DEFAULT_URL = "/teacher/index";

	/**
	 * 管理员登陆后的默认页
	 */
	public static final String ADMIN_DEFAULT_URL = "/admin/index";

	public static final String ADMIN_ACCOUNT_STUDENT_LIST = "/admin/account/student/list"; // 管理员-账号管理-学生列表
	public static final String ADMIN_ACCOUNT_TEACHER_LIST = "/admin/account/teacher/list"; // 管理员-账号管理-教师列表
	public static final String ADMIN_ACCOUNT_ADMIN_LIST = "/admin/account/admin/list"; // 管理员-账号管理-管理员列表

	/**
	 * 管理员-物理机-操作
	 */
	public static final String ADMIN_HOST_LIST = "/admin/host/list";
	public static final String ADMIN_HOST_REMOVE = "/admin/host/remove.do";
	public static final String ADMIN_HOST_REMOVEALL = "/admin/host/removeAll.do";
	public static final String ADMIN_HOST_ADD_FORM = "/admin/host/add/form";
	public static final String ADMIN_HOST_ADD = "/admin/host/add.do";
	public static final String ADMIN_HOST_EDIT_FORM = "/admin/host/edit/form";
	public static final String ADMIN_HOST_EDIT = "/admin/host/edit.do";

	/**
	 * 管理员-课程-操作
	 */
	public static final String ADMIN_CLASS_LIST = "/admin/class/list";
	public static final String ADMIN_CLASS_REMOVE = "/admin/class/remove.do";
	public static final String ADMIN_CLASS_REMOVEALL = "/admin/class/removeAll.do";
	public static final String ADMIN_CLASS_ADD_FORM = "/admin/class/add/form";
	public static final String ADMIN_CLASS_ADD = "/admin/class/add.do";
	public static final String ADMIN_CLASS_EDIT_FORM = "/admin/class/edit/form";
	public static final String ADMIN_CLASS_EDIT = "/admin/class/edit.do";

	/**
	 * 学生-虚拟机-操作
	 */
	public static final String STUDENT_VM_LIST = "/student/vm/list";
	public static final String STUDENT_VM_EDIT_FORM = "/student/vm/edit/form";
	public static final String STUDENT_VM_EDIT = "/student/vm/edit.do";
	public static final String STUDENT_VM_START = "/student/vm/start.do";
	public static final String STUDENT_VM_SHUTDOWN = "/student/vm/shutdown.do";

	/**
	 * 教师-虚拟机-操作
	 */
	public static final String TEACHER_VM_LIST = "/teacher/vm/list";
	public static final String TEACHER_VM_EDIT_FORM = "/teacher/vm/edit/form";
	public static final String TEACHER_VM_EDIT = "/teacher/vm/edit.do";
	public static final String TEACHER_VM_START = "/teacher/vm/start.do";
	public static final String TEACHER_VM_SHUTDOWN = "/teacher/vm/shutdown.do";

}
