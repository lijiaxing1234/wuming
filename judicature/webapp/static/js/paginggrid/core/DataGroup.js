
/**
 * <code>DataGroup</code>
 * <p>Data view compoent's base Class</p>
 */
function DataGroup(settings) {

}

DataGroup.prototype = {
	renderTo : function(ele, replace) {
		this.__group__ || (this.__group__ = this.buildGroup(this));
		this.__body__ || (this.__body__ = this.buildBody(this, this._data));
		this.renderToCallback(ele, replace, this);
		return this;
	},
	getData : function() {
		return this._data;
	},
	setData : function(data) {
		this._data = data || [];
		this.dataChange(this);
		if (this.__group__) {
			this.buildBody(this, this._data);
		}
		return this;
	},
	getGroup : function() {
		return this.__group__;
	},
	getBody : function() {
		return this.__body__;
	},
	buildGroup : function(hosts) {/*implements by subclass, build container*/return null},
	buildBody : function(hosts, data) {/*implements by subclass, to show data*/return null},
	renderToCallback : function(ele, replace, hosts) {/*implements by subclass, render to dom*/},
	dataChange : function(hosts) {/*implements by subclass, private*/}
}