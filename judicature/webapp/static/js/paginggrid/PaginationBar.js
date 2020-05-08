(function() {
window.PaginationBar = function(settings) {
	PaginationBarCore.apply(this, arguments);
	if (settings)
	{
		this.setClassName(settings.className);
	}
}

PaginationBar.prototype = new PaginationBarCore();
PaginationBar.prototype.setClassName = function(className) {
	this._className = className;
	if (this._bar){
		this._bar.className = this._className || "xiayie";		
	}
	return this;
}
PaginationBar.prototype.renderToCallback = function(ele, replace, hosts) {
	if (replace && ele !== document.body) {
		ele.parentNode.insertBefore(this._bar, ele);
		ele.parentNode.removeChild(ele);
	} else {
		ele.appendChild(this._bar);
	}
}
PaginationBar.prototype.buildBarContainer = function(hosts) {
	this._bar = document.createElement("div");
	this.setClassName(this._className);
	return true;
}
PaginationBar.prototype.buildBar = function(hosts) {
	if (!this._bar) return;
	var $this = this;
	var index = hosts.getCurrentPage();
	var count = hosts.getPageCount();
	var trueIndex = index + 1;
	var e = null;

	if (this._barbuided) {
		this._curPage.html('第' + trueIndex + '页');
		this._totalPage.html('共' + count + '页');
		return;
	}

	$('<span>|&lt;</span>').appendTo($(this._bar));
	
	e = $('<span><a href="#">首页</a></span>');
	e.appendTo($(this._bar));
	e.find("a").bind({"click": function(event) {
			preventDefault(event)
			hosts.pagination.first();
		}
	});
	
	$('<span>&lt;&lt;</span>').appendTo($(this._bar));
	
	e = $('<span><a href="#">上一页</a></span>');
	e.appendTo($(this._bar));
	e.find("a").bind({"click": function(event) {
			preventDefault(event)
			hosts.pagination.previous();
		}
	});
	
	e = $('<span><a href="#">下一页</a></span>');
	e.appendTo($(this._bar));
	e.find("a").bind({"click": function(event) {
			preventDefault(event)
			hosts.pagination.next();
		}
	});
	
	$('<span>&gt;&gt;</span>').appendTo($(this._bar));
	
	e = $('<span><a href="#">尾页</a></span>');
	e.appendTo($(this._bar));
	e.find("a").bind({"click": function(event) {
			preventDefault(event)
			hosts.pagination.last();
		}
	});
	
	$('<span>&gt;|</span>').appendTo($(this._bar));
	this._curPage = $('<span class="thePageIndex">第' + trueIndex + '页</span>');
	this._totalPage = $('<span class="theTotalPage">共' + count + '页</span>');
	this._curPage.appendTo($(this._bar));
	this._totalPage.appendTo($(this._bar));

	this._barbuided = true;
	return true;
}

function preventDefault(event) {
	if (event.preventDefault) {
		event.preventDefault();
	} else {
		window.event.returnValue = false;
	}
}
})();