function HScrollGrid(settings) {
	DataGroup.apply(this, arguments);
	if (settings && settings.renderCallback) {
		this._renderCallback = settings.renderCallback;
		this._className = settings.className;
		this._bodyClassName = settings.bodyClassName;
	}
}

HScrollGrid.prototype = new DataGroup();
HScrollGrid.prototype.renderToCallback = function(ele, replace, hosts) {
	if (replace && ele !== document.body) {
		ele.parentNode.insertBefore(this._grid, ele);
		ele.parentNode.removeChild(ele);
	} else {
		ele.appendChild(this._grid);
	}
}
HScrollGrid.prototype.buildGroup = function(hosts) {
	this._grid = document.createElement("div");
	this._grid.className = this._className;
	return this._grid;
}
HScrollGrid.prototype.buildBody = function(hosts, data) {
	var $this = this;

	while(this._grid.firstChild) {
		this._grid.removeChild(this._grid.firstChild);
	}
	
	if (data && this._renderCallback) {
		for (var i = 0; i < data.length; i++) {
			var datum = data[i];
			var ele = document.createElement("div");
			ele.className = this._bodyClassName;
			ele.innerHTML = this._renderCallback(data[i]);
			this._grid.appendChild(ele);
		}
	}
	
	return true;
}