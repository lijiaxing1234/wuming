/*
<div class="Productb1"><a href="#"><img src="images/xiao29.jpg" /></a></div>
<div class="Productb3"><a href="#"><img src="images/xiao31.jpg" /></a></div>
*/
(function() {
	window.HScrollBar = function (settings) {
		PaginationBarCore.apply(this, arguments);
		this._preBar = settings.preBar;
		this._nextBar = settings.nextBar;
		this._isTemplate = settings.isTemplate;
	}

	HScrollBar.prototype = new PaginationBarCore();
	HScrollBar.prototype.renderToCallback = function(ele, replace, hosts) {
		if (this._isTemplate !== true) {
			ele.insertBefore(this._preBar, ele.firstChild);
			ele.appendChild(this._nextBar);
		}
		$(this._preBar).find("a").bind("focus", function() {
			$(this).blur();
		});
		$(this._nextBar).find("a").bind("focus", function() {
			$(this).blur();
		});
	}
	HScrollBar.prototype.buildBarContainer = function(hosts) {
		return true;
	}
	HScrollBar.prototype.buildBar = function(hosts) {
		var $this = this;
		var index = hosts.getCurrentPage();
		var count = hosts.getPageCount();
		var dataSize = hosts.getDataSize();

		if (!this._preBar) {
			this._preBar = document.createElement("div");
			this._preBar.className = "Productb1";
			this._preBar.appendChild(createA('<img src="' + path + '/images/xiao29.jpg" />'));
		} else {
			this._preBar.onclick = function(event) {
				hosts.pagination.previous();
			}
		}
		if (!this._nextBar) {
			this._nextBar = document.createElement("div");
			this._nextBar.className = "Productb3";
			this._nextBar.appendChild(createA('<img src="' + path + '/images/xiao31.jpg" />'));;
		} else {
			this._nextBar.onclick = function(event) {
				hosts.pagination.next();
			}
		}
		
		return true;
	}
	HScrollBar.prototype.show = function() {
		$(this._preBar).show();
		$(this._nextBar).show();
		return this;
	}
	HScrollBar.prototype.hide = function() {
		$(this._preBar).hide();
		$(this._nextBar).hide();
		return this;
	}
	
	function createA(html) {
		var a = document.createElement("a");
			a.innerHTML = html;
			a.href = "#";
			a.onclick = function(event) {
				event = event || window.event;
				if (event.preventDefault) {
					event.preventDefault();
				} else {
					window.event.returnValue = false;
				}
				return false;
			}
			/*a.onfocus = function(event) {
				this.blur();
			}*/
		return a;
	}
})();