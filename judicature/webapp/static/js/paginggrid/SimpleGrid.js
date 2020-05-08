(function() {
	window.SimpleGrid = function(settings) {
		BaseGrid.apply(this, arguments);
		if (settings) {
			this.setClassName(settings.className);
			this.setHeadClassName(settings.headClassName);
			this.setBodyClassName(settings.bodyClassName);
			this.setFootClassName(settings.footClassName);
			this.onclick = settings.click;
			this.ondblclick = settings.doubleclick;
			this.dblenable = settings.doubleclickenabel;
		}
	}

	SimpleGrid.prototype = new BaseGrid();
	SimpleGrid.prototype.renderToCallback = function(ele, replace, hosts) {
		if (replace && ele !== document.body) {
			ele.parentNode.insertBefore(this._grid, ele);
			ele.parentNode.removeChild(ele);
		} else {
			ele.appendChild(this._grid);
		}
	}
	SimpleGrid.prototype.buildGrid = function(hosts) {
		this._grid = document.createElement("table");
		this._grid.className = this._className;
		return this._grid;
	}
	SimpleGrid.prototype.buildHead = function(hosts) {
		this._head = document.createElement("thead");
		this._head.className = this._headClassName;
		var tr = document.createElement("tr");
		var columns = hosts.getColumns();
		var $this = this;
		for (var i = 0; i < columns.length; i++) {
			var column = columns[i];
			var th = document.createElement("th");
			if (!isNaN(parseInt(column.width))) {
				th.width = column.width;
			}
			th.innerHTML = column.header;
			if (column.sortable) {
				var sortevent = column.dblclicksort ? "dblclick" : "click";
				th["on" + sortevent] = function(hosts, index) {
					return function() {				
						if ( this.className.indexOf("desc") != -1 )
							sortTable($this, index, "asc");
						else if ( this.className.indexOf("asc") != -1 )
							sortTable($this, index, "");
						else
							sortTable($this, index, "desc");
					}
				}(this, i);	
			}
			tr.appendChild(th);
		}
		this._head.appendChild(tr);
		this._grid.appendChild(this._head);
		return true;
	}
	SimpleGrid.prototype.buildBody = function(hosts, data) {
		var $this = this;
		var body = this._body;
		this._body = document.createElement("tbody");
		this._body.className = this._bodyClassName;
		var columns = hosts.getColumns();

		if (data) {
			if (hosts.sortType && hosts.sortField) {
				sortData(data, this.sortField, this.sortType, this.sort);
			}
			for (var i = 0; i < data.length; i++) {
				var tr = document.createElement("tr");
				var datum = data[i];
				for (var j = 0; j < columns.length; j++) {
					var column = columns[j];
					var td = document.createElement("td");
					var value = "";
					if (typeof(column.callback) === "function") {
						value = column.callback(datum,i);
					} else {
						value = datum[column.field];
					}
					td.innerHTML = value;
					if (typeof(this.onclick) === "function") {
						td.onclick = function(datum, row, col) {
							return function(event) {
								$this.onclick(datum, row, col);
							}
						}(datum, i, j);
					}
					if (this.dblenable && typeof(this.ondblclick) === "function") {
						td.ondblclick = function(datum, row, col) {
							return function(event) {
								$this.ondblclick(datum, row, col);
							}
						}(datum, i, j);
					}
					tr.appendChild(td);
				}
				this._body.appendChild(tr);
			}
		}
		
		if (body) {
			this._grid.insertBefore(this._body, body);
			this._grid.removeChild(body);
		} else {
			this._grid.appendChild(this._body);
		}
		return true;
	}
	SimpleGrid.prototype.buildFoot = function(hosts) {
		this._foot = document.createElement("tfoot");
		this._foot.className = this._footClassName;
		this._grid.appendChild(this._foot);
		return true;
	}
	SimpleGrid.prototype.dataChange = function(hosts) {
		this.dataBackup = [];
		var data = hosts.getData() || [];
		for (var i = 0; i < data.length; i++) {
			this.dataBackup.push(data[i]);
		}
	}

	SimpleGrid.prototype.setClassName = function(className) {
		this._className = className;
		this._grid && (this._grid.className = className);
		return this;
	}
	SimpleGrid.prototype.setHeadClassName = function(className) {
		this._headClassName = className;
		this._head && (this._head.className = className);
		return this;
	}
	SimpleGrid.prototype.setBodyClassName = function(className) {
		this._bodyClassName = className;
		this._body && (this._body.className = className);
		return this;
	}
	SimpleGrid.prototype.setFootClassName = function(className) {
		this._footClassName = className;
		this._foot && (this._foot.className = className);
		return this;
	}

	function sortTable(hosts, colIndex, sortType) {
		var columns = hosts.getColumns();
		var column = columns[colIndex];
		hosts.sortType = sortType;
		hosts.sortField = column.field;
		hosts.sort = column.sort;

		var i, j;
		var tr = hosts._head.childNodes[0];
		var th = tr.childNodes[colIndex];
		for (i = 0; i < tr.childNodes.length ; i++) {
			var node = tr.childNodes[i];
			node.className = node.className.replace(" asc", "").replace(" desc","");
		}		

		if (!sortType) {
			// hosts != this
			hosts.buildBody(hosts, hosts.dataBackup);
			return;
		}
		th.className += " " + sortType;

		var data = sortData(hosts.getData(), column.field, sortType, hosts.sort);
		hosts.buildBody(hosts, data);
	}

	function sortData(data, field, sortType, sort) {
		var a, b;
		for (i = 0; i < data.length - 1; i++) {
			a = data[i][field];
			for (j = i + 1; j < data.length; j++) {
				b = data[j][field];
				var result = (sort || compare)(a,b);
				if ( (sortType === "desc" && result === -1) || (sortType === "asc" && result === 1) ) {
					var temp = data[i];
					data[i] = data[j];
					data[j] = temp;
					a = b;
				}
			}
		}
		return data;
	}

	function compare(a, b) {
		a = parseFloat(a) || a;
		b = parseFloat(b) || b;
		return a > b ? 1 : (a == b ? 0 : -1);
	}
})();