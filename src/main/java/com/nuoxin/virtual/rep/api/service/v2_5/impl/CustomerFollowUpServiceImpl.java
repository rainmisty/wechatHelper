package com.nuoxin.virtual.rep.api.service.v2_5.impl;

import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nuoxin.virtual.rep.api.common.bean.PageRequestBean;
import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.mybatis.DoctorMapper;
import com.nuoxin.virtual.rep.api.mybatis.DrugUserDoctorMapper;
import com.nuoxin.virtual.rep.api.mybatis.DrugUserMapper;
import com.nuoxin.virtual.rep.api.mybatis.ProductLineMapper;
import com.nuoxin.virtual.rep.api.service.v2_5.CustomerFollowUpService;
import com.nuoxin.virtual.rep.api.utils.CollectionsUtil;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.CustomerFollowListRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.doctor.CustomerFollowListBean;

import shaded.org.apache.commons.lang3.StringUtils;

@Service
public class CustomerFollowUpServiceImpl implements CustomerFollowUpService{
	
	@Resource
	private DrugUserMapper drugUserMapper;
	@Resource
	private ProductLineMapper productLineMapper;
	@Resource
	private DrugUserDoctorMapper drugUserDoctorMapper;
	@Resource
	private DoctorMapper doctorMapper;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public PageResponseBean<List<CustomerFollowListBean>> list(CustomerFollowListRequestBean request) {
		PageResponseBean pageResponseBean = null;
		int count = 0;
		
		// 获取所有下属(直接&间接) virtualDrugUserIds
		List<Long> virtualDrugUserIds = this.getSubordinateIds(request.getLeaderPath());
		if (CollectionsUtil.isNotEmptyList(virtualDrugUserIds)) {
			count = this.getDoctorsCount(virtualDrugUserIds);
			pageResponseBean = this.getDoctorsList(count, null, virtualDrugUserIds, request);
		} 
		
		// 补偿
		if (pageResponseBean == null) {
			count = 0;
			pageResponseBean = new PageResponseBean(request, count, Collections.emptyList());
		}

		return pageResponseBean;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public PageResponseBean<List<CustomerFollowListBean>> search(CustomerFollowListRequestBean request) {
		PageResponseBean pageResponseBean = null;
		
		// 获取所有下属(直接&间接) virtualDrugUserIds
		List<Long> virtualDrugUserIds = this.getSubordinateIds(request.getLeaderPath());
		if (CollectionsUtil.isNotEmptyList(virtualDrugUserIds)) {
			// 根据 搜索内容,virtualDrugUserIds 获取对应的 doctorIds
			List<Long> doctorIds = drugUserDoctorMapper.search(request.getSearch(), virtualDrugUserIds);
			if (CollectionsUtil.isNotEmptyList(doctorIds)) {
				pageResponseBean = this.getDoctorsList(doctorIds.size(), doctorIds, virtualDrugUserIds, request);
			}
		} 
		
		if (pageResponseBean == null) {
			int count = 0;
			pageResponseBean = new PageResponseBean(request, count, Collections.emptyList());
		}

		return pageResponseBean;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public PageResponseBean<List<CustomerFollowListBean>> screen(CustomerFollowListRequestBean request) {
		PageResponseBean pageResponseBean = null;
		
		List<Long> doctorIds = drugUserDoctorMapper.screen(request.getVirtualDrugUserIds(), request.getProductLineIds());
		if (CollectionsUtil.isNotEmptyList(doctorIds)) {
			pageResponseBean = this.getDoctorsList(doctorIds.size(), doctorIds, request.getVirtualDrugUserIds(), request);
		} 
		
		if (pageResponseBean == null) {
			int count = 0;
			pageResponseBean = new PageResponseBean(request, count, Collections.emptyList());
		}
		
		return pageResponseBean;
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * 获取所有下属(直接&间接) virtualDrugUserIds
	 * @param leaderPath 领导路径
	 * @return List<Long>
	 */
	private List<Long> getSubordinateIds(String leaderPath) {
		return drugUserMapper.getSubordinateIdsByLeaderPath(leaderPath);
	}
	
	private int getDoctorsCount(List<Long> virtualDrugUserIds) {
		return doctorMapper.getDoctorsCount(virtualDrugUserIds);
	}
	/**
	 * 根据 doctorIds,virtualDrugUserIds及分页参数获取列表信息
	 * @param doctorIds
	 * @param virtualDrugUserIds
	 * @param pageRequestBean
	 * @return PageResponseBean<List<CustomerFollowListBean>>
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private PageResponseBean<List<CustomerFollowListBean>> getDoctorsList(int count, List<Long> doctorIds, List<Long> virtualDrugUserIds,
			PageRequestBean pageRequestBean) {

		List<CustomerFollowListBean> list = null;
		if (count > 0) {
			list = doctorMapper.getDoctors(doctorIds, virtualDrugUserIds, pageRequestBean.getCurrentSize(),
					pageRequestBean.getPageSize());
			if (CollectionsUtil.isNotEmptyList(list)) {
				list.forEach(doctor -> {
					String visitTime = doctor.getVisitTime();
					if (StringUtils.isNotBlank(visitTime)) {
						visitTime = visitTime.replace(".0", "");
						doctor.setVisitTime(visitTime);
						doctor.setDoctorId("H".concat(doctor.getDoctorId()));
					}
				});
			}
		}
		
		if(CollectionsUtil.isEmptyList(list)) {
			list = Collections.emptyList();
		}

		return new PageResponseBean(pageRequestBean, count, list);
	}

}
