(function() {
	window.FlowGrid = function(settings) {
		BaseGrid.apply(this, arguments);
		if (settings && settings.renderCallback) {
			this._renderCallback = settings.renderCallback;
		}
	}

	FlowGrid.prototype = new BaseGrid();
	FlowGrid.prototype.renderToCallback = function(ele, replace, hosts) {
		if (replace && ele !== document.body) {
			ele.parentNode.insertBefore(this._grid, ele);
			ele.parentNode.removeChild(ele);
		} else {
			ele.appendChild(this._grid);
		}
	}
	FlowGrid.prototype.buildGrid = function(hosts) {
		this._grid = document.createElement("div");
		this._grid.className = "";
		return this._grid;
	}
	FlowGrid.prototype.buildHead = function(hosts) {
		return true;
	}
	FlowGrid.prototype.buildBody = function(hosts, data) {
		var $this = this;

		while(this._grid.firstChild) {
			this._grid.removeChild(this._grid.firstChild);
		}
		
		if (data && this._renderCallback) {
			for (var i = 0; i < data.length; i++) {
				var datum = data[i];
				var ele = document.createElement("div");
				ele.className = "banjiba";
				ele.innerHTML = this._renderCallback(data[i], i);
				this._grid.appendChild(ele);
			}
		}
		
		return true;
	}
	FlowGrid.prototype.buildFoot = function(hosts) {
		return true;
	}
})();