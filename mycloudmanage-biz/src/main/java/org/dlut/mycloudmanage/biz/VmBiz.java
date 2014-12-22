/*
 * Copyright 2014 etao.com All right reserved. This software is the
 * confidential and proprietary information of etao.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with etao.com .
 */
package org.dlut.mycloudmanage.biz;

import javax.annotation.Resource;

import org.dlut.mycloudserver.client.common.MyCloudResult;
import org.dlut.mycloudserver.client.common.Pagination;
import org.dlut.mycloudserver.client.common.vmmanage.QueryVmCondition;
import org.dlut.mycloudserver.client.common.vmmanage.VmDTO;
import org.dlut.mycloudserver.client.service.vmmanage.IVmManageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 类ClassBiz.java的实现描述：TODO 类实现描述
 * 
 * @author xuyizhen Dec 11, 2014 4:27:41 PM
 */
@Service
public class VmBiz {
	private static Logger log = LoggerFactory.getLogger(VmBiz.class);

	@Resource(name = "vmManageService")
	private IVmManageService vmManageService;

	/**
	 * 根据uuid获取虚拟机
	 * 
	 * @param vmUuid
	 * @return
	 */
	public VmDTO getVmByUuid(String vmUuid) {
		MyCloudResult<VmDTO> result = this.vmManageService.getVmByUuid(vmUuid);
		if (!result.isSuccess()) {
			log.warn("调用vmManageService.getVmByUuid()出错，" + result.getMsgCode()
					+ ":" + result.getMsgInfo());
			return null;
		}
		return result.getModel();
	}

	/**
	 * 创建新的虚拟机，必须设置vmVcpu、vmMemory、imageUuid、userAccount、showType、
	 * showPassword，可选：classId、desc
	 * 
	 * @param vmDTO
	 * @return 新创建的vm的uuid
	 */
	public String createVm(VmDTO vmDTO) {
		MyCloudResult<String> result = this.vmManageService.createVm(vmDTO);
		if (!result.isSuccess()) {
			log.warn("调用vmManageService.createVm()出错，" + result.getMsgCode()
					+ ":" + result.getMsgInfo());
			return null;
		}
		return result.getModel();
	}

	/**
	 * 克隆虚拟机，必须设置vmVcpu、vmMemory、userAccount、showType、showPassword，
	 * 可选：classId、desc
	 * 
	 * @param destVmDTO
	 * @param srcVmUuid
	 * @return
	 */
	public String cloneVm(VmDTO destVmDTO, String srcVmUuid) {
		MyCloudResult<String> result = this.vmManageService.cloneVm(destVmDTO,
				srcVmUuid);
		if (!result.isSuccess()) {
			log.warn("调用vmManageService.cloneVm()出错，" + result.getMsgCode()
					+ ":" + result.getMsgInfo());
			return null;
		}
		return result.getModel();
	}

	/**
	 * 开启虚拟机
	 * 
	 * @param vmUuid
	 * @return
	 */
	public boolean startVm(String vmUuid) {
		MyCloudResult<Boolean> result = this.vmManageService.startVm(vmUuid);
		if (!result.isSuccess()) {
			log.warn("调用vmManageService.startVm()出错，" + result.getMsgCode()
					+ ":" + result.getMsgInfo());
			return false;
		}
		return result.getModel();
	}

	/**
	 * 强制关闭虚拟机
	 * 
	 * @param vmUuid
	 * @return
	 */
	public boolean forceShutDownVm(String vmUuid) {
		MyCloudResult<Boolean> result = this.vmManageService
				.forceShutDownVm(vmUuid);
		if (!result.isSuccess()) {
			log.warn("调用vmManageService.forceShutDown()出错，"
					+ result.getMsgCode() + ":" + result.getMsgInfo());
			return false;
		}
		return result.getModel();
	}

	/**
	 * 根据条件统计虚拟机个数
	 * 
	 * @param queryVmCondition
	 * @return
	 */
	public int countQuery(QueryVmCondition queryVmCondition) {
		MyCloudResult<Integer> result = this.vmManageService
				.countQuery(queryVmCondition);

		if (!result.isSuccess()) {
			log.warn("调用vmManageService.countQuery()出错，" + result.getMsgCode()
					+ ":" + result.getMsgInfo());
			return 0;
		}
		return result.getModel();
	}

	/**
	 * 根据条件查询虚拟机列表
	 * 
	 * @param queryVmCondition
	 * @return
	 */
	public Pagination<VmDTO> query(QueryVmCondition queryVmCondition) {
		MyCloudResult<Pagination<VmDTO>> result = this.vmManageService
				.query(queryVmCondition);
		if (!result.isSuccess()) {
			log.warn("调用vmManageService.query()出错，" + result.getMsgCode() + ":"
					+ result.getMsgInfo());
			return null;
		}
		return result.getModel();
	}

	/**
	 * 删除虚拟机
	 * 
	 * @param vmUuid
	 * @return
	 */
	public boolean deleteVm(String vmUuid) {
		MyCloudResult<Boolean> result = this.vmManageService.deleteVm(vmUuid);
		if (!result.isSuccess()) {
			log.warn("调用vmManageService.deleteVm()出错，" + result.getMsgCode()
					+ ":" + result.getMsgInfo());
			return false;
		}
		return result.getModel();
	}

	/**
	 * 更新虚拟机
	 * 
	 * @param vmDTO
	 * @return
	 */
	public boolean updateVm(VmDTO vmDTO) {
		MyCloudResult<Boolean> result = this.vmManageService.updateVm(vmDTO);
		if (!result.isSuccess()) {
			log.warn("调用vmManageService.updateVm()出错，" + result.getMsgCode()
					+ ":" + result.getMsgInfo());
			return false;
		}
		return result.getModel();
	}
	
    /**
     * 在数据库中将硬盘绑定到虚拟机，如果此时虚拟机正在运行，则会将硬盘挂载到虚拟机上
     * 
     * @param vmUuid
     * @param diskUuid
     * @return
     */
    public boolean  attachDisk(String vmUuid, String diskUuid){
    	 MyCloudResult<Boolean> result = this.vmManageService.attachDisk(vmUuid, diskUuid);
    	 if(!result.isSuccess()){
    		 log.warn("调用vmManageService.attachDisk()出错，" + result.getMsgCode()
 					+ ":" + result.getMsgInfo());
 			return false;
    	 }
    	 return result.getModel();
   }

    /**
     * 在数据库中将硬盘和虚拟机解绑定，如果此时虚拟机正在运行，则会将硬盘从虚拟机中卸载
     * 
     * @param diskUuid
     * @return
     */
    public boolean detachDisk(String diskUuid){
    	MyCloudResult<Boolean> result = this.vmManageService.detachDisk(diskUuid);
    	 if(!result.isSuccess()){
    		 log.warn("调用vmManageService.detachDisk()出错，" + result.getMsgCode()
 					+ ":" + result.getMsgInfo());
 			return false;
    	 }
    	 return result.getModel();
    	
    }

}
