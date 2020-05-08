//////////////////////////////////////////////
//
// 分页类 Pagination
// 只负责分页的核心功能
//
//////////////////////////////////////////////

/**
 * 分页类
 * @param pageSize 每页记录数
 * @param callback 分页回调，导航页面时将调用该回调
 */
function Pagination(pageSize, callback) {
	this.__init__(pageSize, callback);
}

Pagination.prototype = {
	__init__ : function(pageSize, callback) {
		this._callback = callback;
		this.setPageSize(pageSize || 15);
		this._dataSize = 0;    
		this._currentPageIndex = 0;
		this._pageCount = 0;
	},
	first : function() {
		if (this._currentPageIndex != 0) {this.go(0);}
	},
	previous : function() {
		if (this._currentPageIndex > 0) {this.go(this._currentPageIndex - 1);}
	},
	next : function() {
		if (this._currentPageIndex < this._pageCount - 1) {this.go(this._currentPageIndex + 1);}
	},
	last : function() {
		if (this._currentPageIndex < this._pageCount - 1) {this.go(this._pageCount - 1);}
	},
	go : function(pageIndex) {
		pageIndex = parseInt(pageIndex) || 0;
		if (pageIndex < 0 || this._pageCount === 0) {
			this._currentPageIndex = 0;
		} else if (pageIndex > this._pageCount - 1) {
			this._currentPageIndex = this._pageCount - 1;
		} else {
			this._currentPageIndex = pageIndex;
		}

		if (typeof(this._callback) === 'function') {this._callback(this._currentPageIndex);}
	},
	getCurrentPage : function() {
		return this._currentPageIndex;
	},
	setPageSize : function(pageSize) {
		if (!isNaN(pageSize)) {
			this._pageSize = pageSize;
			this._calcPageCount();
		}
		return this;
	},
	getPageSize : function() {
		return this._pageSize;
	},
	getDataSize : function() {
		return this._dataSize || 0;
	},
	setDataSize : function(dataSize) {
		this._dataSize  = dataSize;
		this._calcPageCount();
		return this;
	},
	getPageCount : function() {
		return this._pageCount;
	},
	setCallback : function(callback) {
		this._callback = callback;
		return this;
	},
	_calcPageCount : function() {
		this._pageCount = Math.ceil(this._dataSize/this._pageSize);
	}
}