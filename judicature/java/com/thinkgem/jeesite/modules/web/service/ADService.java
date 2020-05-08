package com.thinkgem.jeesite.modules.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thinkgem.jeesite.modules.web.dao.ADMapper;
import com.thinkgem.jeesite.modules.web.entity.AD;


/**
 *广告管理
 */
@Service(value="TableADService")
public class ADService {
	@Autowired ADMapper adMapper;
	
	public List<AD> selectAD(String adCode){
		return adMapper.selectAD(adCode);
	}
}
