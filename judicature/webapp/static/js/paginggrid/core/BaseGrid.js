//////////////////////////////////////////////
//
// 表格核心类 BaseGrid
// 只负责生成表格的核心功能
//
//////////////////////////////////////////////

/**
 * 表格核心类
 */
function BaseGrid(settings) {
	if (settings) {
		this.setColumns(settings.columns);		
	}
}

BaseGrid.prototype = {
	renderTo : function(ele, replace) {
		this.__grid__ || (this.__grid__ = this.buildGrid(this));
		this.__headCreated__ || (this.__headCreated__ = this.buildHead(this));
		this.__bodyCreated__ || (this.__bodyCreated__ = this.buildBody(this, this._data));
		this.__footCreated__ || (this.__footCreated__ = this.buildFoot(this));
		this.renderToCallback(ele, replace, this);
		return this;
	},	
	getColumns : function() {
		return this._columns;
	},
	setColumns : function(columns) {
		this._columns = columns || [];
		if (this.__grid__)
		{
			this.buildGrid(this);
			this.buildHead(this);
			this.buildBody(this, this._data);
			this.buildFoot(this);
			this.renderToCallback(this.__grid__, true, this);
		}
	},
	getData : function() {
		return this._data;
	},
	setData : function(data) {
		this._data = data || [];
		this.dataChange(this);
		if (this.__grid__) {
			this.buildBody(this, this._data);
		}
		return this;
	},
	buildGrid : function(hosts) {/*由子类实现*/return false},
	buildHead : function(hosts) {/*由子类实现*/return false},
	buildBody : function(hosts, data) {/*由子类实现*/return false},
	buildFoot : function(hosts) {/*由子类实现*/return false},
	renderToCallback : function(ele, replace, hosts) {/*由子类实现*/},
	dataChange : function(hosts) {/*由子类实现,在数据发生变化时进行处理,非公开接口*/}
}