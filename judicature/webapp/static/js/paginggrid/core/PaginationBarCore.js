/**
 * 分页Bar核心
 */
function PaginationBarCore(settings) {
	this.__init__(settings);	
}

PaginationBarCore.prototype = {
	__init__ : function(settings) {
		this.pagination = new Pagination();
		if (settings) {
			this.setPageSize(settings.pageSize || 15);
			this.setCallback(settings.callback);
			this.setDataSize(settings.dataSize || 0);
			this._autofetch = settings.autofetch === false ? false : true;
		} else {
			this.setCallback(null);
		}
	},
	renderTo : function(ele, replace) {
		this.__containerCreated__ || (this.__containerCreated__ = this.buildBarContainer(this));
		this.__barCreated__ || (this.__barCreated__ = this.buildBar(this));
		this.renderToCallback(ele, replace, this);
		this._autofetch && this.pagination.go(0);
		return this;
	},
	getDataSize : function() {
		return this.pagination.getDataSize();
	},
	setDataSize : function(dataSize) {
		this.pagination.setDataSize(dataSize);
	    if (this.__containerCreated__) {
		   this.buildBar(this);
	    }
	    return this;
    },
	getPageSize : function() {
		return this.pagination.getPageSize();
	},
	setPageSize : function(pageSize) {
        this.pagination.setPageSize(pageSize);
	    if (this.__containerCreated__) {
		   this.buildBar(this);
	    }
	    return this;
    },
	setCallback : function(callback) {
		var $this = this;
		var func = function(pageIndex) {
			if (typeof(callback) === 'function') {callback(pageIndex);}
			$this.buildBar($this);
		}
		this.pagination.setCallback(func);
		return this;
	},
	getCurrentPage : function() {
        return this.pagination.getCurrentPage();
    },
	getPageCount : function() {
		return this.pagination.getPageCount();
	},
	refresh : function(index) {
		this.pagination.go(isNaN(index) ? this.pagination.getCurrentPage() : index);
	},
	buildBarContainer : function(hosts) {/*由子类实现*/return false;},
	buildBar : function(hosts) {/*由子类实现*/return false},
	renderToCallback : function(ele, replace, hosts) {/*由子类实现*/}
}