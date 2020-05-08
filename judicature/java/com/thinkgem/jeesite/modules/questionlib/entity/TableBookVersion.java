/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.questionlib.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.sys.entity.Office;

/**
 * 书籍检查Entity
 * @author 书籍检查
 * @version 2016-09-26
 */
public class TableBookVersion extends DataEntity<TableBookVersion> {
	
	private static final long serialVersionUID = 1L;
	private String verId;		// ver_id
	private String bookId;		// book_id
	private String bookName;		// 电子书名称
	private String verCode;		// 版本号
	private String bookCode;		// 唯一标识(机构代码-图书格式-出版形式-资源属性-编码)
	private String bookType;		// 电子书格式(参见biz_metadata)
	private String resourceType;		// 版权(参见biz_metadata)
	private String platform;		// platform
	private String publishType;		// 出版形式(参见biz_metadata)
	private String makingType;		// 著作形式(参见biz_metadata)
	private String bookCatagoryId;		// 电子书分类
	private String bundleId;		// 同bs_books.bundle_id
	private String bookKeywords;		// 关键词
	private String bookAuthors;		// 作者
	private String bookAuthorsSummary;		// 作者简介
	private String bookSummary;		// 电子书简介
	private String isbn;		// isbn
	private String publishDate;		// 图书出版日期
	private String publisher;		// 出版社
	private String paperPrice;		// 纸质价格
	private String iphonePrice;		// 参考价格
	private String ipadPrice;		// 参考价格
	private String androidphonePrice;		// 参考价格
	private String androidpadPrice;		// 参考价格
	private String coverUrl;		// 封面URL
	private String bookUrl;		// 电子书URL
	private String bookSize;		// 电子书大小
	private String bookResolution;		// 分辨率
	private Office office;		// 机构ID(创建者创建电子书时所处的机构ID)
	private Date updatetime;		// 更新时间
	private Long updater;		// 修改人
	private String bookState;		// 电子书状态(参见biz_metadata)
	private String probationRate;		// 试读比例
	private String checkId;		// 审核标识
	private String deleteFlag;		// 非0表示已删除
	private String memo;		// memo
	private String translator;		// 译者
	private String chiefEditor;		// 主编
	private String wordCount;		// 字数
	private String longTitle;		// 长标题
	private String shortTitle;		// 短标题
	private String promotionWords;		// 促销句
	private String recommendedWords;		// 推荐语
	private String bookPrice;		// 电子书定价
	private String copyrighterId;		// 版权方ID
	private String copyrightBegintime;		// 版权开始日期(yyyymmdd)
	private String copyrightEndtime;		// 版权截止日期(yyyymmdd)
	private String catalogue;		// 目录
	private String promotionPrice;		// 非ios价格
	private String resourceId;		// 资源库id
	private String bookResourceType;		// 资源类型,书籍,期刊
	private String seriesName;		// series_name
	private String checkerId;		// checker_id
	private String checkState;		// 状态
	private String isOpen;		// 打开
	private String isCover;		// 封面
	private String isCopyright;		// 版权页
	private String isCatalog;		// 目录
	private String isImage;		// 图片
	private String isLayout;		// 排版
	private String isCode;		// 乱码
	private String otherDesc;		// 其他
	
	private String isMy;
	public TableBookVersion() {
		super();
	}

	public TableBookVersion(String id){
		super(id);
	}

	@Length(min=0, max=32, message="ver_id长度必须介于 0 和 32 之间")
	public String getVerId() {
		return verId;
	}

	public void setVerId(String verId) {
		this.verId = verId;
	}
	
	@Length(min=0, max=32, message="book_id长度必须介于 0 和 32 之间")
	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}
	
	@Length(min=0, max=1000, message="电子书名称长度必须介于 0 和 1000 之间")
	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	
	@Length(min=0, max=15, message="版本号长度必须介于 0 和 15 之间")
	public String getVerCode() {
		return verCode;
	}

	public void setVerCode(String verCode) {
		this.verCode = verCode;
	}
	
	@Length(min=0, max=500, message="唯一标识(机构代码-图书格式-出版形式-资源属性-编码)长度必须介于 0 和 500 之间")
	public String getBookCode() {
		return bookCode;
	}

	public void setBookCode(String bookCode) {
		this.bookCode = bookCode;
	}
	
	@Length(min=0, max=6, message="电子书格式(参见biz_metadata)长度必须介于 0 和 6 之间")
	public String getBookType() {
		return bookType;
	}

	public void setBookType(String bookType) {
		this.bookType = bookType;
	}
	
	@Length(min=0, max=6, message="版权(参见biz_metadata)长度必须介于 0 和 6 之间")
	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}
	
	@Length(min=0, max=6, message="platform长度必须介于 0 和 6 之间")
	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}
	
	@Length(min=0, max=6, message="出版形式(参见biz_metadata)长度必须介于 0 和 6 之间")
	public String getPublishType() {
		return publishType;
	}

	public void setPublishType(String publishType) {
		this.publishType = publishType;
	}
	
	@Length(min=0, max=6, message="著作形式(参见biz_metadata)长度必须介于 0 和 6 之间")
	public String getMakingType() {
		return makingType;
	}

	public void setMakingType(String makingType) {
		this.makingType = makingType;
	}
	
	@Length(min=0, max=32, message="电子书分类长度必须介于 0 和 32 之间")
	public String getBookCatagoryId() {
		return bookCatagoryId;
	}

	public void setBookCatagoryId(String bookCatagoryId) {
		this.bookCatagoryId = bookCatagoryId;
	}
	
	
	public String getBundleId() {
		return bundleId;
	}

	public void setBundleId(String bundleId) {
		this.bundleId = bundleId;
	}
	
	@Length(min=0, max=100, message="关键词长度必须介于 0 和 100 之间")
	public String getBookKeywords() {
		return bookKeywords;
	}

	public void setBookKeywords(String bookKeywords) {
		this.bookKeywords = bookKeywords;
	}
	
	@Length(min=0, max=100, message="作者长度必须介于 0 和 100 之间")
	public String getBookAuthors() {
		return bookAuthors;
	}

	public void setBookAuthors(String bookAuthors) {
		this.bookAuthors = bookAuthors;
	}
	
	@Length(min=0, max=1000, message="作者简介长度必须介于 0 和 1000 之间")
	public String getBookAuthorsSummary() {
		return bookAuthorsSummary;
	}

	public void setBookAuthorsSummary(String bookAuthorsSummary) {
		this.bookAuthorsSummary = bookAuthorsSummary;
	}
	
	@Length(min=0, max=1000, message="电子书简介长度必须介于 0 和 1000 之间")
	public String getBookSummary() {
		return bookSummary;
	}

	public void setBookSummary(String bookSummary) {
		this.bookSummary = bookSummary;
	}
	
	@Length(min=0, max=32, message="isbn长度必须介于 0 和 32 之间")
	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	
	@Length(min=0, max=32, message="图书出版日期长度必须介于 0 和 32 之间")
	public String getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}
	
	@Length(min=0, max=120, message="出版社长度必须介于 0 和 120 之间")
	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	
	public String getPaperPrice() {
		return paperPrice;
	}

	public void setPaperPrice(String paperPrice) {
		this.paperPrice = paperPrice;
	}
	
	public String getIphonePrice() {
		return iphonePrice;
	}

	public void setIphonePrice(String iphonePrice) {
		this.iphonePrice = iphonePrice;
	}
	
	public String getIpadPrice() {
		return ipadPrice;
	}

	public void setIpadPrice(String ipadPrice) {
		this.ipadPrice = ipadPrice;
	}
	
	public String getAndroidphonePrice() {
		return androidphonePrice;
	}

	public void setAndroidphonePrice(String androidphonePrice) {
		this.androidphonePrice = androidphonePrice;
	}
	
	public String getAndroidpadPrice() {
		return androidpadPrice;
	}

	public void setAndroidpadPrice(String androidpadPrice) {
		this.androidpadPrice = androidpadPrice;
	}
	
	@Length(min=0, max=255, message="封面URL长度必须介于 0 和 255 之间")
	public String getCoverUrl() {
		return coverUrl;
	}

	public void setCoverUrl(String coverUrl) {
		this.coverUrl = coverUrl;
	}
	
	@Length(min=0, max=255, message="电子书URL长度必须介于 0 和 255 之间")
	public String getBookUrl() {
		return bookUrl;
	}

	public void setBookUrl(String bookUrl) {
		this.bookUrl = bookUrl;
	}
	
	@Length(min=0, max=20, message="电子书大小长度必须介于 0 和 20 之间")
	public String getBookSize() {
		return bookSize;
	}

	public void setBookSize(String bookSize) {
		this.bookSize = bookSize;
	}
	
	@Length(min=0, max=12, message="分辨率长度必须介于 0 和 12 之间")
	public String getBookResolution() {
		return bookResolution;
	}

	public void setBookResolution(String bookResolution) {
		this.bookResolution = bookResolution;
	}
	
	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	
	public Long getUpdater() {
		return updater;
	}

	public void setUpdater(Long updater) {
		this.updater = updater;
	}
	
	@Length(min=0, max=6, message="电子书状态(参见biz_metadata)长度必须介于 0 和 6 之间")
	public String getBookState() {
		return bookState;
	}

	public void setBookState(String bookState) {
		this.bookState = bookState;
	}
	
	@Length(min=0, max=11, message="试读比例长度必须介于 0 和 11 之间")
	public String getProbationRate() {
		return probationRate;
	}

	public void setProbationRate(String probationRate) {
		this.probationRate = probationRate;
	}
	
	@Length(min=0, max=32, message="审核标识长度必须介于 0 和 32 之间")
	public String getCheckId() {
		return checkId;
	}

	public void setCheckId(String checkId) {
		this.checkId = checkId;
	}
	
	@Length(min=0, max=4, message="非0表示已删除长度必须介于 0 和 4 之间")
	public String getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	
	@Length(min=0, max=1000, message="memo长度必须介于 0 和 1000 之间")
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	@Length(min=0, max=100, message="译者长度必须介于 0 和 100 之间")
	public String getTranslator() {
		return translator;
	}

	public void setTranslator(String translator) {
		this.translator = translator;
	}
	
	@Length(min=0, max=100, message="主编长度必须介于 0 和 100 之间")
	public String getChiefEditor() {
		return chiefEditor;
	}

	public void setChiefEditor(String chiefEditor) {
		this.chiefEditor = chiefEditor;
	}
	
	@Length(min=0, max=11, message="字数长度必须介于 0 和 11 之间")
	public String getWordCount() {
		return wordCount;
	}

	public void setWordCount(String wordCount) {
		this.wordCount = wordCount;
	}
	
	@Length(min=0, max=200, message="长标题长度必须介于 0 和 200 之间")
	public String getLongTitle() {
		return longTitle;
	}

	public void setLongTitle(String longTitle) {
		this.longTitle = longTitle;
	}
	
	@Length(min=0, max=15, message="短标题长度必须介于 0 和 15 之间")
	public String getShortTitle() {
		return shortTitle;
	}

	public void setShortTitle(String shortTitle) {
		this.shortTitle = shortTitle;
	}
	
	@Length(min=0, max=500, message="促销句长度必须介于 0 和 500 之间")
	public String getPromotionWords() {
		return promotionWords;
	}

	public void setPromotionWords(String promotionWords) {
		this.promotionWords = promotionWords;
	}
	
	@Length(min=0, max=1000, message="推荐语长度必须介于 0 和 1000 之间")
	public String getRecommendedWords() {
		return recommendedWords;
	}

	public void setRecommendedWords(String recommendedWords) {
		this.recommendedWords = recommendedWords;
	}
	
	public String getBookPrice() {
		return bookPrice;
	}

	public void setBookPrice(String bookPrice) {
		this.bookPrice = bookPrice;
	}
	
	@Length(min=0, max=11, message="版权方ID长度必须介于 0 和 11 之间")
	public String getCopyrighterId() {
		return copyrighterId;
	}

	public void setCopyrighterId(String copyrighterId) {
		this.copyrighterId = copyrighterId;
	}
	
	@Length(min=0, max=30, message="版权开始日期(yyyymmdd)长度必须介于 0 和 30 之间")
	public String getCopyrightBegintime() {
		return copyrightBegintime;
	}

	public void setCopyrightBegintime(String copyrightBegintime) {
		this.copyrightBegintime = copyrightBegintime;
	}
	
	@Length(min=0, max=30, message="版权截止日期(yyyymmdd)长度必须介于 0 和 30 之间")
	public String getCopyrightEndtime() {
		return copyrightEndtime;
	}

	public void setCopyrightEndtime(String copyrightEndtime) {
		this.copyrightEndtime = copyrightEndtime;
	}
	
	@Length(min=0, max=10000, message="目录长度必须介于 0 和 10000 之间")
	public String getCatalogue() {
		return catalogue;
	}

	public void setCatalogue(String catalogue) {
		this.catalogue = catalogue;
	}
	
	public String getPromotionPrice() {
		return promotionPrice;
	}

	public void setPromotionPrice(String promotionPrice) {
		this.promotionPrice = promotionPrice;
	}
	
	@Length(min=0, max=60, message="资源库id长度必须介于 0 和 60 之间")
	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	
	@Length(min=0, max=10, message="资源类型,书籍,期刊长度必须介于 0 和 10 之间")
	public String getBookResourceType() {
		return bookResourceType;
	}

	public void setBookResourceType(String bookResourceType) {
		this.bookResourceType = bookResourceType;
	}
	
	@Length(min=0, max=100, message="series_name长度必须介于 0 和 100 之间")
	public String getSeriesName() {
		return seriesName;
	}

	public void setSeriesName(String seriesName) {
		this.seriesName = seriesName;
	}
	
	@Length(min=0, max=32, message="checker_id长度必须介于 0 和 32 之间")
	public String getCheckerId() {
		return checkerId;
	}

	public void setCheckerId(String checkerId) {
		this.checkerId = checkerId;
	}
	
	@Length(min=0, max=1, message="状态长度必须介于 0 和 1 之间")
	public String getCheckState() {
		return checkState;
	}

	public void setCheckState(String checkState) {
		this.checkState = checkState;
	}
	
	@Length(min=0, max=1, message="打开长度必须介于 0 和 1 之间")
	public String getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(String isOpen) {
		this.isOpen = isOpen;
	}
	
	@Length(min=0, max=1, message="封面长度必须介于 0 和 1 之间")
	public String getIsCover() {
		return isCover;
	}

	public void setIsCover(String isCover) {
		this.isCover = isCover;
	}
	
	@Length(min=0, max=1, message="版权页长度必须介于 0 和 1 之间")
	public String getIsCopyright() {
		return isCopyright;
	}

	public void setIsCopyright(String isCopyright) {
		this.isCopyright = isCopyright;
	}
	
	@Length(min=0, max=1, message="目录长度必须介于 0 和 1 之间")
	public String getIsCatalog() {
		return isCatalog;
	}

	public void setIsCatalog(String isCatalog) {
		this.isCatalog = isCatalog;
	}
	
	@Length(min=0, max=1, message="图片长度必须介于 0 和 1 之间")
	public String getIsImage() {
		return isImage;
	}

	public void setIsImage(String isImage) {
		this.isImage = isImage;
	}
	
	@Length(min=0, max=1, message="排版长度必须介于 0 和 1 之间")
	public String getIsLayout() {
		return isLayout;
	}

	public void setIsLayout(String isLayout) {
		this.isLayout = isLayout;
	}
	
	@Length(min=0, max=1, message="乱码长度必须介于 0 和 1 之间")
	public String getIsCode() {
		return isCode;
	}

	public void setIsCode(String isCode) {
		this.isCode = isCode;
	}
	
	@Length(min=0, max=200, message="其他长度必须介于 0 和 200 之间")
	public String getOtherDesc() {
		return otherDesc;
	}

	public void setOtherDesc(String otherDesc) {
		this.otherDesc = otherDesc;
	}

	public String getIsMy() {
		return isMy;
	}

	public void setIsMy(String isMy) {
		this.isMy = isMy;
	}
}