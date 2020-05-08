package com.thinkgem.jeesite.modules.resource.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.resource.dao.ADColumnsDao;
import com.thinkgem.jeesite.modules.resource.dao.ADDao;
import com.thinkgem.jeesite.modules.resource.dao.TableCategoryDao;
import com.thinkgem.jeesite.modules.resource.entity.TableAD;
import com.thinkgem.jeesite.modules.resource.entity.TableADColumns;
import com.thinkgem.jeesite.modules.resource.entity.TableCategory;

/**
 *分类Service
 */
@Service
@Transactional(readOnly = true)
public class ADService extends CrudService<TableCategoryDao,TableCategory> {
	@Autowired
	ADColumnsDao adColumnsDao;
	@Autowired
	ADDao adDao;
	
	
	public Page<TableADColumns> find(Page<TableADColumns> page, TableADColumns adColumn) {
		adColumn.setPage(page);
		page.setList(adColumnsDao.getADColumnsList());
		return page;
	}

	@Transactional(readOnly = false)
	public void addADColumn(TableADColumns adColumn) {
		adColumn.setId(UUID.randomUUID().toString());
		adColumnsDao.insert(adColumn);
	}
	@Transactional(readOnly = false)
	public void updateADColumn(TableADColumns adColumn) {
		adColumnsDao.update(adColumn);
	}

	public TableADColumns selectADById(String adId) {
		return adColumnsDao.selectADColumnsById(adId);
	}

	public TableADColumns getADColumnByCode(String adCode) {
		return adColumnsDao.getADColumnByCode(adCode);
	}
	//删除广告栏目位
	@Transactional(readOnly=false)
	public int deleteADColumn(String adColumnsId) {
		return adColumnsDao.delete(adColumnsId);
	}
	//查询广告位下广告的个数
	
	public int selectADCountById(String adColumnsId) {
		return adDao.selectADCountById(adColumnsId);
	}

	public Page<TableAD> SelectADList(Page<TableAD> page, TableAD ad) {
		ad.setPage(page);
		page.setList(adDao.getADList(ad.getColId()));
		return page;
	}
	@Transactional(readOnly=false)
	public void addAd(TableAD ad) {
		int seq = adDao.getMaxADseq(ad.getColId());
		ad.setSeq(seq+1);
		ad.setId(UUID.randomUUID().toString());
		adDao.insert(ad);
	}
	
	@Transactional(readOnly=false)
	public void delete(String adId) {
		adDao.deleteAD(adId);
	}
	@Transactional(readOnly=false)
	public void update(TableAD adFirst) {
		adDao.update(adFirst);
	}
}

