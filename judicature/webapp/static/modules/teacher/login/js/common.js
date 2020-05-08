!
function() {
	
    "use strict";
    function $17(element) {
        if (arguments.length > 1) {
            for (var i = 0,
            elements = [], length = arguments.length; length > i; i++) elements.push($17(arguments[i]));
            return elements
        }
        return "[object String]" == Object.prototype.toString.call(element) && (element = document.getElementById(element)),
        element
    }
    function extend(child, parent) {
        var key;
        for (key in parent) parent.hasOwnProperty(key) && (child[key] = parent[key])
    }
    function include(child, parent) {
        var key;
        for (key in parent) parent.hasOwnProperty(key) && (child.prototype[key] = parent[key])
    }
    var version = "3.1.0",
    Life = function() {
        var life;
        return life = function(dady) {
            return extend(this, dady),
            this.init.apply(this, arguments),
            !1
        },
        life.prototype.init = function() {},
        life.prototype.parent = life,
        life.prototype.extend = function(dady) {
            var key;
            for (key in dady) dady.hasOwnProperty(key) && (this[key] = dady[key]);
            return this
        },
        life.include = function(dady) {
            var key;
            for (key in dady) dady.hasOwnProperty(key) && (this.prototype[key] = dady[key]);
            return this
        },
        life.extend = function(dady) {
            var key;
            for (key in dady) dady.hasOwnProperty(key) && (this[key] = dady[key]);
            return this
        },
        life
    };
    extend(window, {
        $17: $17
    }),
    extend(window.$17, {
        version: version,
        extend: extend,
        include: include,
        Life: Life,
        Model: new Life
    })
} (),
function() {
    function shtml2dat(s) {
        return s.replace(/^([^?]+)\.vpage(.*)$/, "$1.api$2")
    }
    var f = jQuery.ajax;
    jQuery.ajax = function(url, opts) {
        return "object" == typeof url && (opts = url, url = void 0),
        opts = opts || {},
        url && (opts.url = url),
        opts.url && ( - 1 != opts.url.indexOf("://") ? /^http:\/\/[^/] * 17zuoye[ ^ /]*/.test(opts.url) && (opts.url = shtml2dat(opts.url)) : opts.url = shtml2dat(opts.url)),
        f.call(this, opts)
    }
} (),
function($17) {
    "use strict";
    function namespace() {
        var space = arguments[0],
        str = "window.";
        space = space.split(".");
        for (var i = 0,
        len = space.length; len > i; i++) str += space[i],
        eval(i == len - 1 && 2 == arguments.length ? "if(!" + str + "){ " + str + " = '" + arguments[1] + "';}": "if(!" + str + "){ " + str + " = {};}"),
        str += ".";
        return ! 0
    }
    function proxy(fun) {
        var self = this;
        return function() {
            return fun.apply(self, arguments)
        }
    }
    function guid(format) {
        return format.toLowerCase().replace(/[xy]/g,
        function(c) {
            var r = 16 * Math.random() | 0,
            v = "x" == c ? r: 3 & r | 8;
            return v.toString(16)
        }).toUpperCase()
    }
    function getQuery(item) {
        var svalue = location.search.match(new RegExp("[?&]" + item + "=([^&]*)(&?)", "i"));
        return svalue ? decodeURIComponent(svalue[1]) : ""
    }
    function getQueryParams() {
        for (var p = {},
        query = window.location.search.substring(1), vars = query.split("&"), i = 0; i < vars.length; i++) {
            var pair = vars[i].split("=", 2);
            2 == pair.length && pair[0] && (p[decodeURIComponent(pair[0])] = decodeURIComponent(pair[1]))
        }
        return p
    }
    function getBaseLocation() {
        var p = window.location.href.indexOf("?");
        return - 1 == p ? window.location.href: window.location.href.substr(0, p)
    }
    function getHashQuery(item) {
        var svalue = location.hash.match(new RegExp("[#&?]" + item + "=([^&]*)(&?)", "i"));
        return svalue ? decodeURIComponent(svalue[1]) : ""
    }
    function replaceAll(target, str1, str2) {
        return target.replace(new RegExp(str1, "gm"), str2)
    }
    function listSort(arr, field, order) {
        for (var refer = [], result = [], order = "asc" == order ? "asc": "desc", index = null, i = 0; i < arr.length; i++) refer[i] = arr[i][field] + ":" + i;
        for (refer.sort(), "desc" == order && refer.reverse(), i = 0; i < refer.length; i++) index = refer[i].split(":"),
        index = index[index.length - 1],
        result[i] = arr[index];
        return result
    }
    function strPad(str, padStr, padLength, position) {
        for (var i = 0,
        s = ""; i != padLength;) s += padStr.toString(),
        i++;
        return position = position || "l",
        str = "l" == position ? s.concat(str) : str.concat(s),
        "l" == position ? str.substring(str.length - padLength, str.length) : str.substring(0, padLength)
    }
    function setCookieOneDay(name, value, day) {
        var date = new Date;
        date.setTime(date.getTime() + 24 * (day ? day: 1) * 60 * 60 * 1e3),
        $.cookie(name, value ? value: "", {
            path: "/",
            expires: date
        })
    }
    function getCookieWithDefault(name) {
        var value = $.cookie(name);
        return value ? value: ""
    }
    function Model(param) {
        var key;
        for (key in param) param.hasOwnProperty(key) && (this[key] = param[key]);
        return this
    }
    function getSMSVerifyCode($this, data, second) {
        var timerCount, timer;
        if (second = second ? second: 60, $this.addClass("btn_disable"), data.success) timerCount = second;
        else if (timerCount = data.timer || null, null == timerCount) return $this.removeClass("btn_disable"),
        !1;
        timer = $.timer(function() {
            0 >= timerCount ? ($this.removeClass("btn_disable"), $this.find("span, strong").html("免费获取短信验证码"), $this.siblings(".init, .hint, .msgInfo").html(""), timerCount = second, timer.stop()) : $this.find("span, strong").html(--timerCount + "秒之后可重新发送")
        }),
        timer.set({
            time: 1e3
        }),
        timer.play()
    }
    function detectZoom() {
        var ra = 0,
        screen = window.screen,
        ua = navigator.userAgent.toLowerCase();
        return~ua.indexOf("firefox") ? void 0 !== window.devicePixelRatio && (ra = window.devicePixelRatio) : ~ua.indexOf("msie") || ~ua.indexOf("rident/") ? screen.deviceXDPI && screen.logicalXDPI && (ra = screen.deviceXDPI / screen.logicalXDPI) : void 0 !== window.outerWidth && void 0 !== window.innerWidth && (ra = window.outerWidth / window.innerWidth),
        ra && (ra = Math.round(100 * ra)),
        100 !== ra && ra >= 95 && 105 >= ra && (ra = 100),
        ra
    }
    function getMonthTotalDay(year, month, day) {
        var date = new Date,
        def = new Date(year ? year: date.getFullYear(), month ? month: date.getMonth() + 1, day ? day: 0);
        return def.getDate()
    }
    $17.extend($17, {
        proxy: proxy,
        namespace: namespace,
        guid: guid,
        getBaseLocation: getBaseLocation,
        getQuery: getQuery,
        getQueryParams: getQueryParams,
        getHashQuery: getHashQuery,
        replaceAll: replaceAll,
        listSort: listSort,
        strPad: strPad,
        setCookieOneDay: setCookieOneDay,
        getCookieWithDefault: getCookieWithDefault,
        getSMSVerifyCode: getSMSVerifyCode,
        getMonthTotalDay: getMonthTotalDay,
        detectZoom: detectZoom
    })
} ($17),
function($17) {
    "use strict";
    function isNumber(value) {
        var reg = /^[0-9]+$/;
        return $17.isBlank(value) || !reg.test(value) ? !1 : !0
    }
    function isBlank(str) {
        return "undefined" == typeof str || "null" == String(str) || "" == $.trim(str)
    }
    function isZipCode(value) {
        var req = /^[0-9]{6}$/;
        return $17.isBlank(value) || !req.test(value) ? !1 : !0
    }
    function isCnString(value) {
        if (!value) return ! 1;
        var req = /^[\u4e00-\u9fa5]+$/;
        return value = value.replace(/\s+/g, ""),
        req.test(value)
    }
    function isValidCnName(value) {
        if (!value) return ! 1;
        value = $.trim(value);
        var req = /[\u3002|\uFF1F|\uFF01|\uFF0C|\u3001|\uFF1B|\uFF1A|\u300C|\u300D|\u300E|\u300F|\u2018|\u2019|\u201C|\u201D|\uFF08|\uFF09|\u3014|\u3015|\u3010|\u3011|\u2014|\u2026|\u2013|\uFF0E|\u300A|\u300B|\u3008|\u3009]+/;
        return req.test(value) ? !1 : (req = /^[\u2E80-\uFE4F]+$/, req.test(value))
    }
    function isMobile(value) {
        value += "";
        var reg = /^1[0-9]{10}$/;
        return value && 11 == value.length && reg.test(value) ? !0 : !1
    }
    function isEmail(value) {
        var req = /^[-_.A-Za-z0-9]+@[-_.A-Za-z0-9]+(\.[-_.A-Za-z0-9]+)+$/;
        return value && req.test(value)
    }
    function isFunction(func) {
        return !! func && !func.nodeName && func.constructor != String && func.constructor != RegExp && func.constructor != Array && /function/i.test(func + "")
    }
    $17.extend($17, {
        isNumber: isNumber,
        isBlank: isBlank,
        isZipCode: isZipCode,
        isCnString: isCnString,
        isMobile: isMobile,
        isEmail: isEmail,
        isFunction: isFunction,
        isValidCnName: isValidCnName
    })
} ($17),
function($17) {
    "use strict";
    function _strftime(_format, diff, type, _date_) {
        var _date = null == _date_ ? new Date: _date_;
        switch (type) {
        case "Y":
        case "y":
            _date.setFullYear(_date.getFullYear() + diff);
            break;
        case "M":
            _date.setMonth(_date.getMonth() + diff);
            break;
        case "D":
        case "d":
            _date.setDate(_date.getDate() + diff);
            break;
        case "H":
        case "h":
            _date.setHours(_date.getHours() + diff);
            break;
        case "m":
            _date.setMinutes(_date.getMinutes() + diff);
            break;
        case "S":
        case "s":
            _date.setSeconds(_date.getSeconds() + diff);
            break;
        case "W":
        case "w":
            _date.setDate(_date.getDate() + 7 * diff)
        }
        return (_format + "").replace(/%([a-zA-Z])/g,
        function(m, f) {
            var formatter = formats && formats[f];
            switch (typeof formatter) {
            case "function":
                return formatter.call(formats, _date);
            case "string":
                return _strftime(formatter, date)
            }
            return f
        })
    }
    function dateUtils() {
        switch (arguments.length) {
        case 0:
            return _strftime("%Y-%M-%d", 0, "d", null);
        case 1:
            return _strftime(arguments[0], 0, "d", null);
        case 2:
            return _strftime(arguments[0], arguments[1], "d", null);
        case 3:
            return _strftime(arguments[0], arguments[1], arguments[2], null);
        case 4:
            return _strftime(arguments[0], arguments[1], arguments[2], arguments[3]);
        default:
            return _strftime("%Y-%M-%d")
        }
    }
    function dateDiff(start, end, type, format, dayLength) {
        var startDate = $17.strPad(start, "0", 20, "r"),
        endDate = $17.strPad(end, "0", 20, "r"),
        diff = null;
        switch (startDate = new Date(startDate.substring(0, 4), startDate.substring(5, 7), startDate.substring(8, 10), startDate.substring(11, 13), startDate.substring(14, 16), startDate.substring(17, 19)), endDate = new Date(endDate.substring(0, 4), endDate.substring(5, 7), endDate.substring(8, 10), endDate.substring(11, 13), endDate.substring(14, 16), endDate.substring(17, 19)), diff = Date.parse(endDate) - Date.parse(startDate), format = format || "%d %h:%m:%s", dayLength = dayLength || 0, type) {
        case "W":
        case "w":
            return Math.floor(diff / 6048e5);
        case "D":
        case "d":
            return Math.floor(diff / 864e5);
        case "H":
        case "h":
            return Math.floor(diff / 36e5);
        case "m":
            return Math.floor(diff / 6e4);
        case "S":
        case "s":
            return Math.floor(diff / 1e3);
        case "timer":
            return format = format.replace(/%d/g, 0 == dayLength ? Math.floor(diff / 864e5) : $17.strPad(Math.floor(diff / 864e5), "0", dayLength)),
            format = format.replace(/%h/g, $17.strPad(Math.floor(diff / 36e5) % 24, "0", 2)),
            format = format.replace(/%m/g, $17.strPad(Math.floor(diff / 6e4) % 60, "0", 2)),
            format = format.replace(/%s/g, $17.strPad(Math.floor(diff / 1e3) % 60, "0", 2));
        default:
            return null
        }
    }
    function myDate(info) {
        var info = info.split(/:|-|\s/g);
        return new Date(info[0], info[1], info[2], info[3], info[4], info[5])
    }
    var formats = {
        s: function(date) {
            return $17.strPad(date.getSeconds(), "0", 2)
        },
        m: function(date) {
            return $17.strPad(date.getMinutes(), "0", 2)
        },
        h: function(date) {
            return $17.strPad(date.getHours(), "0", 2)
        },
        d: function(date) {
            return $17.strPad(date.getDate(), "0", 2)
        },
        M: function(date) {
            return $17.strPad(date.getMonth() + 1, "0", 2)
        },
        y: function(date) {
            return $17.strPad(date.getYear() % 100, "0", 2)
        },
        Y: function(date) {
            return date.getFullYear()
        },
        w: function(date) {
            return date.getDay()
        },
        W: function(date) {
            var _week = ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"];
            return _week[date.getDay()]
        }
    };
    $17.extend($17, {
        DateUtils: dateUtils,
        DateDiff: dateDiff,
        Date: myDate
    })
} ($17),
function($17) {
    "use strict";
    function hash(name, value) {
        if (!$17.isBlank(name)) {
            var clearReg = new RegExp("(&" + name + "=[^&]*)|(\\b" + name + "=[^&]*&)|(\\b" + name + "=[^&]*)", "ig");
            if (null === value) location.hash = location.hash.replace(clearReg, "");
            else {
                value += "";
                var temp = location.hash.replace(clearReg, "");
                temp += ( - 1 != temp.indexOf("=") ? "&": "") + name + "=" + encodeURIComponent(value),
                location.hash = temp
            }
        }
    }
    function urlCallback(prams, callback) {
        for (var time = (new Date).getTime(), _v = null, i = 0, len = prams.length; len > i; i++) {
            if (_v = $17.getQuery(prams[i]), "" == _v) return;
            $17.namespace("__hashCallback__" + time + "." + prams[i], _v)
        }
        var callFun = $17.proxy(callback);
        callFun(eval("__hashCallback__" + time))
    }
    function hashCallback(prams, callback) {
        for (var time = (new Date).getTime(), _v = null, i = 0, len = prams.length; len > i; i++) {
            if (_v = $17.getHashQuery(prams[i]), "" == _v) return;
            $17.namespace("__hashCallback__" + time + "." + prams[i], _v),
            hash(prams[i], null)
        }
        hash("=^_^", "");
        var callFun = $17.proxy(callback);
        callFun(eval("__hashCallback__" + time))
    }
    $17.extend($17, {
        hashCallback: hashCallback,
        urlCallback: urlCallback
    })
} ($17),
function($17) {
    "use strict";
    function getClassId(classId) {
        return classId = classId.toUpperCase(),
        $.trim("C" == classId.substring(0, 1) ? classId.substring(1, classId.length) : classId)
    }
    function setSelect(elem, keys, values, def) {
        $(elem).html("");
        for (var i = 0,
        len = keys.length; len > i; i++) $(elem).append(keys[i] == def ? '<option value ="' + keys[i] + '" selected="selected">' + values[i] + "</option>": '<option value ="' + keys[i] + '">' + values[i] + "</option>")
    }
    function backToTop(time) {
        top.$("html, body").animate({
            scrollTop: "0px"
        },
        time || 0)
    }
    function promptAlert() {
        switch (arguments.length) {
        case 1:
            $.prompt("<div class='w-ag-center'>" + arguments[0] + "</div>", {
                title: "系统提示",
                buttons: {
                    "知道了": !0
                },
                position: {
                    width: 500
                }
            });
            break;
        case 2:
            $.prompt("<div class='w-ag-center'>" + arguments[0] + "</div>", {
                title: "系统提示",
                buttons: {
                    "知道了": !0
                },
                position: {
                    width: 500
                },
                submit: arguments[1],
                close: arguments[1]
            });
            break;
        case 3:
            $.prompt("<div class='w-ag-center'>" + arguments[0] + "</div>", {
                title: "系统提示",
                buttons: {
                    "知道了": !0
                },
                position: {
                    width: 500
                },
                submit: arguments[1],
                close: arguments[2]
            })
        }
    }
    function wordLengthLimit(wordLen, defaultLen) {
        if ($17.isBlank(wordLen)) return ! 1;
        defaultLen = $17.isBlank(defaultLen) ? 140 : defaultLen;
        var i = "<span>还可以输入" + (defaultLen - wordLen) + "个字</span>",
        s = "<span style='color: #ff1100'>已超出" + -(defaultLen - wordLen) + "个字</span>",
        t = 0 > defaultLen - wordLen ? s: i;
        return t
    }
    function minusPlusInputEvent(opt, callback) {
        var $defaultOpt = {
            currentCount: 0,
            minCount: 0,
            maxCount: 100,
            box: document,
            multiple: 1,
            plus: ".vox-plus-btn",
            minus: ".vox-minus-btn",
            intCount: ".vox-count-int"
        };
        opt && $.extend($defaultOpt, opt),
        $defaultOpt.currentCount <= $defaultOpt.minCount && $($defaultOpt.minus).addClass("w-btn-disabled"),
        $defaultOpt.currentCount >= $defaultOpt.maxCount && $($defaultOpt.plus).addClass("w-btn-disabled"),
        $($defaultOpt.box).on("click", $defaultOpt.minus,
        function() {
            var $this = $(this),
            currentCountBox = $this.siblings("input");
            if ($this.hasClass("w-btn-disabled")) return ! 1;
            var currentCount = parseInt(currentCountBox.val()) - $defaultOpt.multiple; (currentCount <= $defaultOpt.minCount || !$17.isNumber(currentCountBox.val())) && (currentCount = 0, $this.addClass("w-btn-disabled")),
            currentCountBox.val(currentCount),
            $this.siblings($defaultOpt.plus).removeClass("w-btn-disabled"),
            callback && ($defaultOpt.currentCount = currentCount, callback($defaultOpt))
        }),
        $($defaultOpt.box).on("click", $defaultOpt.plus,
        function() {
            var $this = $(this),
            currentCountBox = $this.siblings("input");
            if ($this.hasClass("w-btn-disabled")) return ! 1;
            var currentCount = parseInt(currentCountBox.val()) + $defaultOpt.multiple; (currentCount >= $defaultOpt.maxCount || !$17.isNumber(currentCountBox.val())) && (currentCount = $defaultOpt.maxCount, $this.addClass("w-btn-disabled")),
            currentCountBox.val(currentCount),
            $this.siblings($defaultOpt.minus).removeClass("w-btn-disabled"),
            callback && ($defaultOpt.currentCount = currentCount, callback($defaultOpt))
        }),
        $($defaultOpt.box).on("blur", ".v-count-int",
        function() {
            var $this = $(this),
            currentCount = parseInt($this.val());
            $this.siblings($defaultOpt.minus).removeClass("w-btn-disabled"),
            $this.siblings($defaultOpt.plus).removeClass("w-btn-disabled"),
            currentCount % $defaultOpt.multiple != 0 && (currentCount = $defaultOpt.multiple - currentCount % $defaultOpt.multiple + currentCount, $this.val(currentCount)),
            currentCount < $defaultOpt.minCount || !$17.isNumber(currentCount) ? ($this.val($defaultOpt.minCount), $this.siblings($defaultOpt.minus).addClass("w-btn-disabled")) : currentCount >= $defaultOpt.maxCount && ($this.val($defaultOpt.maxCount), $this.siblings($defaultOpt.plus).addClass("w-btn-disabled")),
            callback && ($defaultOpt.currentCount = currentCount, callback($defaultOpt))
        })
    }
    function blockUI(message) {
        message = $17.isBlank(message) ? "数据加载中...": message,
        $.blockUI({
            css: {
                border: "none",
                padding: "15px",
                backgroundColor: "#fff",
                "-webkit-border-radius": "10px",
                "-moz-border-radius": "10px",
                "border-radius": "10px",
                opacity: .7,
                color: "#000",
                baseZ: 2001,
                timeout: 2e3
            },
            message: '<h2><img width="40" height="40" src="/public/skin/leak/html/images/loading.gif"/>' + message + "</h2>"
        }),
        setTimeout(function() {
            $.unblockUI()
        },
        5e3)
    }
    function unBlockUI() {
        $.unblockUI()
    }
    function getOperatingSystem() {
        var userAgent = navigator.userAgent || navigator.vendor || window.opera;
        return userAgent.match(/iPad/i) || userAgent.match(/iPhone/i) || userAgent.match(/iPod/i) ? "iOS": userAgent.match(/Android/i) ? "Android": "unknown"
    }
    jQuery.fn.getClassId = function() {
        var classId = $(this).val().toUpperCase();
        return $.trim("C" == classId.substring(0, 1) ? classId.substring(1, classId.length) : classId)
    },
    jQuery.fn.backToCenter = function(time) {
        return top.$("html, body").animate({
            scrollTop: $(this).offset().top
        },
        time || 1e3),
        this
    },
    $17.extend($17, {
        setSelect: setSelect,
        backToTop: backToTop,
        getClassId: getClassId,
        alert: promptAlert,
        wordLengthLimit: wordLengthLimit,
        blockUI: blockUI,
        unBlockUI: unBlockUI,
        getOperatingSystem: getOperatingSystem,
        minusPlusInputEvent: minusPlusInputEvent
    })
} ($17),
function($17) {
    "use strict";
    function copyToClipboard($target, $button, $info, $info1, $callBack) {
        if (window.clipboardData) $button.on("click",
        function() {
            return window.clipboardData.setData("Text", $target.val()),
            "copyInfo" == $info ? $(".copyInfo").show().attr("is-show", 1) : (alert("复制成功，请使用 ctrl + v 贴到您需要的地方！"), $callBack && $callBack()),
            !0
        });
        else {
            var clip = new ZeroClipboard.Client;
            clip.setHandCursor(!0),
            $info1 ? clip.glue($info, $info1) : clip.glue("clip_button", "clip_container"),
            clip.addEventListener("mouseover",
            function() {
                clip.setText($target.val())
            }),
            clip.addEventListener("complete",
            function() {
                return "copyInfo" == $info ? $(".copyInfo").show().attr("is-show", 1) : (alert("复制成功，请使用 ctrl + v 贴到您需要的地方！"), $callBack && $callBack()),
                !0
            })
        }
        return ! 1
    }
    $17.extend($17, {
        copyToClipboard: copyToClipboard
    })
} ($17),
window.onerror = function(errMsg, file, line) {
    var userId = $.cookie ? $.cookie("uid") : "",
    useragent = navigator && navigator.userAgent ? navigator.userAgent: "No browser information";
    encodeURI(useragent);
    var url = 'http://log.17zuoye.net/log?_c=vox_logs:js_errors_logs&_l=3&_log={"userId":"' + userId + '","errMsg":"' + errMsg + '","file":"' + file + '","line":"' + line + '","useragent":"' + useragent + '"}';
    $("<img />").attr("src", url).css("display", "none").appendTo($("body"))
},
function($, $17) {
    "use strict";
    function delegate(config) {
        for (var item in config) {
            var selecter = item.split("->")[0],
            event = item.split("->")[1];
            $(selecter).on(event, config[item])
        }
    }
    $17.extend($17, {
        delegate: delegate
    })
} (jQuery, $17),
$.vox || ($.vox = {}),
$.vox.select = function(target, mt) {
    var $self = $(target),
    $b = $self.find("b.title"),
    $ul = $self.find("ul"),
    $focus = $ul.find(".active");
    return $ul.width($self.width() + 12),
    mt && $ul.css("top", mt),
    $b.html($focus.text()).attr("data-value", $focus.attr("data-value")),
    $self.delegate("a", "click",
    function() {
        $ul.slideDown(100)
    }),
    $self.delegate("li", "click",
    function() {
        $ul.slideUp(100),
        $focus = $(this),
        $focus.radioClass("active"),
        $b.html($focus.text()).attr("data-value", $focus.attr("data-value"))
    }),
    $ul.mouseleave(function() {
        $ul.slideUp(100)
    }),
    !0
},
$.vox.selectFocus = function(target, value) {
    var $target = $(target),
    $focus = $target.find("li[data-value='" + value + "']").radioClass("active");
    return $target.find("b.title").attr("data-value", $focus.attr("data-value")).html($focus.text()),
    !0
},
$.fn.page = function(option) {
    function draw($target, def) {
        if ("normal" == def.model) {
            var current = parseInt(def.current),
            maxNum = parseInt(def.maxNumber),
            _total = parseInt(def.total),
            _start = current - Math.floor(def.maxNumber / 2),
            _end = current + Math.floor(def.maxNumber / 2);
            _start = maxNum > _start ? 1 : _start,
            _end = _end > _total ? _total: _end,
            $target.html('<a v="prev" href="' + def.href + '" class="' + (def.current > 1 ? def.enableMark: def.disabledMark) + " " + def.prev.className + '" style="' + def.prev.style + '">' + def.prev.text + "</a>"),
            _start > 1 && ($target.append('<a href="' + def.href + '"><span>1</span></a>'), $target.append('<span class="points"> ... </span>'));
            for (var i = _start; _end >= i; i++) $target.append('<a href="' + def.href + '" ' + (i == def.current ? 'class="' + def.currentMark + '"': "") + "><span>" + i + "</span></a>");
            _total > _end && ($target.append('<span class="points"> ... </span>'), def.showTotalPage && $target.append('<a href="' + def.href + '"><span>' + _total + "</span></a>")),
            $target.append('<a v="next" href="' + def.href + '" class="' + (1 >= _total || def.current >= _total ? def.disabledMark: def.enableMark) + " " + def.next.className + '" style="' + def.next.style + '">' + def.next.text + "</a>"),
            $target.show(),
            $target.find("a[class != " + def.currentMark + "][class != " + def.disabledMark + "]").one("click",
            function() {
                switch ($(this).attr("v")) {
                case "prev":
                    jump($target, def, current - 1);
                    break;
                case "next":
                    jump($target, def, current + 1);
                    break;
                default:
                    jump($target, def, $(this).find("span").html())
                }
            })
        }
    }
    function jump($target, def, index) {
        return 1 > index || index > def.total ? !1 : (def.current = index, draw($target, def), $.isFunction(def.jumpCallBack) ? def.jumpCallBack(def.current) : def.jumpCallBack && eval(def.jumpCallBack + "(options.current)"), void(def.autoBackToTop && $17.backToTop()))
    }
    return this.each(function() {
        var $target = $(this),
        def = {
            total: 0,
            current: 1,
            maxNumber: 5,
            currentMark: "this",
            disabledMark: "disable",
            enableMark: "enable",
            model: "normal",
            showTotalPage: !0,
            autoBackToTop: !0,
            next: {
                text: "<span>下一页</span>",
                className: "",
                style: ""
            },
            prev: {
                text: "<span>上一页</span>",
                className: "",
                style: ""
            },
            href: "javascript:void(0);",
            jumpCallBack: null
        };
        return $.extend(def, option),
        $target.length < 1 ? !1 : def.total < 1 ? ($target.empty().hide(), !1) : (def.maxNumber = def.maxNumber > 5 ? def.maxNumber: 5, void draw($target, def))
    })
},
function($) {
    $.fn.radioClass = function(className) {
        return this.addClass(className).siblings().removeClass(className).end()
    },
    $.fn.radioOption = function() {
        return this.siblings().attr("selected", !1).end().attr("selected", !0)
    }
} (jQuery),


function($17) {
    "use strict";
    function _info(msg) {
        $17.config.debug && console.info(msg)
    }
    function _dir(msg) {
        $17.config.debug && console.dir(msg)
    }
    function _log(msg) {
        $17.config.debug && console.log(msg)
    }
    function _error(msg) {
        $17.config.debug && console.error(msg)
    }
    function setDebugValue(value, debugValue) {
        return $17.config.debug && "true" === $17.getQuery("debug") ? debugValue: value
    }
    window.hasOwnProperty || (window.hasOwnProperty = Object.prototype.hasOwnProperty),
    document.hasOwnProperty || (document.hasOwnProperty = Object.prototype.hasOwnProperty),
    $17.extend($17, {
        config: {
            debug: !1
        }
    }),
    $17.namespace("console.info", "isNotFunction"),
    "isNotFunction" === console.info && (console.info = function() {}),
    $17.namespace("console.log", "isNotFunction"),
    "isNotFunction" === console.log && (console.log = function() {}),
    $17.namespace("console.dir", "isNotFunction"),
    "isNotFunction" === console.dir && (console.dir = function() {}),
    $17.namespace("console.error", "isNotFunction"),
    "isNotFunction" === console.error && (console.error = function() {}),
    $17.namespace("console.time", "isNotFunction"),
    "isNotFunction" === console.time && (console.time = function() {}),
    $17.namespace("console.timeEnd", "isNotFunction"),
    "isNotFunction" === console.timeEnd && (console.timeEnd = function() {}),
    $17.extend($17, {
        info: _info,
        dir: _dir,
        log: _log,
        error: _error,
        sdv: setDebugValue,
        setDebugValue: setDebugValue
    })
} ($17),
function() {
    function tongji() {
        switch ($17.info(arguments), arguments.length) {
        case 1:
            ga("send", "event", arguments[0].toString(), arguments[0].toString(), arguments[0].toString());
            break;
        case 2:
            ga("send", "event", arguments[0].toString(), arguments[1].toString(), arguments[0] + "_" + arguments[1]);
            break;
        case 3:
            ga("send", "event", arguments[0].toString(), arguments[1].toString(), arguments[2].toString())
        }
        return ! 1
    }
    function aTongJi(tjContent, url) {
        return $17.isBlank(tjContent) || $17.tongji(tjContent),
        $17.isBlank(url) || setTimeout(function() {
            location.href = url
        },
        200),
        !1
    }
    $17.extend($17, {
        tongji: tongji,
        atongji: aTongJi
    })
} (),
function() {
    function getQRCodeImgUrl(operation, callback) {
        var url = "teacher" == operation.role ? "/teacher/qrcode.vpage": "/student/qrcode.vpage",
        defaultImgUrl = "teacher" == operation.role ? "//cdn.17zuoye.com/static/project/app/publiccode_teacher.jpg": "//cdn.17zuoye.com/static/project/app/publiccode_student.jpg";
        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: url,
            data: "campaignId=" + operation.campaignId,
            dataType: "json",
            success: function(json) {
                var QRCodeImgUrl = "";
                QRCodeImgUrl = json.success ? $.browser.msie && 6 == parseInt($.browser.version, 10) ? $17.replaceAll(json.qrcode_url, "https://", "http://") : json.qrcode_url: defaultImgUrl,
                "function" == typeof callback && callback.apply(this, [QRCodeImgUrl])
            }
        })
    }
    function getShortUrl(u, callback) {
        return "" != $_shortUrl && $_originalUrl == u && callback ? (callback($_shortUrl), !1) : ($_originalUrl = u, void $.post("/project/crt.vpage", {
            url: u
        },
        function(data) {
            data.success && ($_shortUrl = u = data.url),
            callback && callback(u)
        }))
    }
    var $_shortUrl = "",
    $_originalUrl = "";
    $17.extend($17, {
        getQRCodeImgUrl: getQRCodeImgUrl,
        getShortUrl: getShortUrl
    })
} (),
function() {
    function traceLog() {
        var pathName = window.location.pathname,
        appName = pathName.split("/"),
        l = {
            sys: "web",
            type: "notify",
            app: appName[1] || pathName,
            module: appName[2] || pathName,
            op: "Load",
            target: pathName
        };
        return $17.extend(l, arguments[0]),
        window.voxLogger.log(l),
        $17.info(l),
        !1
    }
    function voxLog() {
        var pathName = window.location.pathname,
        appName = pathName.split("/"),
        roleType = arguments[1] || "teacher",
        tempObj = {
            userId: $.cookie ? $.cookie("uid") : "",
            auth: "undefined" == typeof $uper ? !1 : $uper.userAuth,
            app: appName[1] || pathName,
            module: appName[1] || pathName,
            op: "Load",
            subject: "undefined" == typeof $uper ? !1 : $uper.subject.key,
            userAgent: window.navigator.appVersion,
            target: pathName
        };
        $17.extend(tempObj, arguments[0]),
        $17.info(tempObj);
        var url = "//log.17zuoye.net/log?_c=vox_logs:web_" + roleType + "_logs&_l=3&_log=" + encodeURIComponent($.toJSON(tempObj)) + "&_t=" + (new Date).getTime();
        return $("<img />").attr("src", url).css("display", "none").appendTo($("body")),
        !1
    }
    function voxPageTimeLogs() {
        $17.isBlank(arguments[0].isOpen) ? !0 : arguments[0].isOpen,
        arguments[0],
        arguments[0].rt || 3e4;
        return $17.info("页面停留时长日志分析》》》已关闭"),
        !1
    }
    window.voxLogger = {
        log: function(v) {
            "string" == $.type(v) && "{" == v[0] && (v = $.evalJSON(v)),
            v.collection && "" != v.collection ? voxLog(v, v.collection) : $(function() {
                var url = "//log.17zuoye.net/log?_c=vox_flash:flash&_l=info&" + $.param(v) + "&uid=" + $.cookie("uid") + "&_t=" + (new Date).getTime();
                window._17zuoye && window._17zuoye.realRemoteAddr && (url += "&_ip=" + window._17zuoye.realRemoteAddr),
                $("<img />").attr("src", url).css("display", "none").appendTo($("body"))
            })
        }
    },
    $17.extend($17, {
        traceLog: traceLog,
        voxLog: voxLog,
        voxPageTimeLogs: voxPageTimeLogs
    })
} (),
function($) {
    var ft = {
        name: "ice_cream",
        freezing: "freezing",
        thaw: "thaw"
    };
    $.fn.extend({
        freezing: function() {
            return this.attr(arguments[0] || ft.name, ft.freezing),
            this
        },
        isFreezing: function() {
            return this.attr(arguments[0] || ft.name) == ft.freezing
        },
        thaw: function() {
            return this.attr(arguments[0] || ft.name, ft.thaw),
            this
        },
        isThaw: function() {
            return this.attr(arguments[0] || ft.name) == ft.thaw
        }
    })
} (jQuery),
function($) {
    "use strict";
    var option = {
        target: null,
        position: "absolute",
        border: "5px #f00 solid",
        width: 5,
        height: 5,
        opacity: 1,
        time: 800
    };
    $.fn.fly = function() {
        return "string" === $.type(arguments[0]) ? option.target = $(arguments[0]) : $.extend(option, arguments[0]),
        this.each(function() {
            var $self = $(this),
            left = $self.offset().left,
            top = $self.offset().top;
            if ($self.is(":animated")) return ! 1;
            var $newLife = $self.clone();
            $("body").append($newLife),
            $newLife.css({
                position: option.position,
                border: option.border,
                left: left,
                top: top
            }),
            $newLife.animate({
                width: option.width,
                height: option.height,
                left: $(option.target).offset().left,
                top: $(option.target).offset().top,
                opcacity: option.opacity
            },
            option.time,
            function() {
                $newLife.remove()
            })
        })
    }
} (jQuery),
$.ajaxSetup({
    cache: !1
});
var App = {
    postJSON: function(url, data, callback, error, dataType) {
        return dataType = dataType || "json",
        null != error && $.isFunction(error) || (error = function() {
            console.info(App.config.info._404)
        }),
        $.ajax({
            type: "post",
            url: url,
            data: $.toJSON(data),
            success: callback,
            error: error,
            dataType: dataType,
            contentType: "application/json;charset=UTF-8"
        })
    },
    getJSON: function(url, callback, error, dataType) {
        return dataType = dataType || "json",
        null != error && $.isFunction(error) || (error = function() {
            console.info(App.config.info._404)
        }),
        $.ajax({
            type: "get",
            url: url,
            success: callback,
            error: error,
            dataType: dataType,
            contentType: "application/json;charset=UTF-8"
        })
    },
    post: function(url, data, callback, error) {
        return $.isFunction(data) && (callback = data, data = void 0),
        null != error && $.isFunction(error) || (error = function() {
            console.info(App.config.info._404)
        }),
        $.ajax({
            type: "post",
            url: url,
            data: data,
            success: callback,
            error: error,
            contentType: "text/plain;charset=UTF-8"
        })
    },
    call: function(callback, value) {
        try {
            $.isFunction(callback) ? callback(value) : $17.isBlank(callback) || eval(callback + "(value)")
        } catch(e) {}
    },
    parseInt: function(value, defaultValue) {
        return value = value || defaultValue || 0,
        value = parseInt(value),
        !isNaN(parseFloat(value)) && isFinite(value) ? value: defaultValue || 0
    },
    config: {
        sign: {
            locked: "app_locked",
            lockedDelay: "app_unlock_delay"
        },
        info: {
            _404: "网络请求失败，请稍等重试或者联系客服人员"
        }
    },
    districtSelect: {
        installState: 0,
        clearDistrictNextLevel: function(obj) {
            obj.attr("next_level") && App.districtSelect.clearDistrictNextLevel($("#" + obj.attr("next_level")).html('<option value=""></option>'))
        },
        get: function(_this) {
            var next_level = _this.attr("next_level");
            if (next_level) {
                if (next_level = $("#" + next_level), App.districtSelect.clearDistrictNextLevel(_this), $17.isBlank(_this.val())) return ! 1;
                $.getJSON("/getregion.vpage?regionCode=" + _this.val(),
                function(data) {
                    if (data.success && data.total > 0) {
                        var html = "",
                        defaultOption = next_level.attr("default_option");
                        if (!$17.isBlank(defaultOption)) try {
                            defaultOption = eval("(" + defaultOption + ")"),
                            html = '<option value="' + defaultOption.key + '">' + defaultOption.value + "</option>"
                        } catch(e) {}
                        $.each(data.rows,
                        function() {
                            html += '<option value="' + this.key + '">' + this.value + "</option>"
                        }),
                        next_level.html(html);
                        var defaultValue = next_level.attr("defaultValue");
                        $17.isBlank(defaultValue) && !$17.isBlank(defaultOption) && (defaultValue = defaultOption.key),
                        $17.isBlank(defaultValue) || setTimeout(function() {
                            next_level.val(defaultValue),
                            next_level.attr("defaultValue", "")
                        },
                        1),
                        $17.isBlank(next_level.attr("next_level")) || $17.isBlank(next_level.val()) || "-1" == next_level.val() || setTimeout(function() {
                            next_level.trigger("change")
                        },
                        5),
                        App.call(next_level.attr("success_callback"), next_level)
                    } else($17.isBlank(next_level.attr("show_error")) || "true" == next_level.attr("show_error")) && alert(data.info),
                    App.call(next_level.attr("error_callback"), next_level)
                })
            }
        },
        install: function(obj) {
            return 1 != App.districtSelect.installState ? (obj = obj || $("select.district_select"), obj.live("change",
            function() {
                App.districtSelect.get($(this))
            }), App.districtSelect.installState = 1, App.districtSelect) : void 0
        },
        init: function(obj) {
            0 == App.districtSelect.installState && App.districtSelect.install(),
            obj = obj || $("select.district_select:first"),
            "1" != obj.attr("isLoaded") && (obj.trigger("change"), obj.attr("isLoaded", 1))
        }
    },
    string: {
        transformUrl: function(url) {
            return url.replace(/((https?\:\/\/|ftp\:\/\/)|(www\.))(\S+)(\w{2,4})(:[0-9]+)?(\/|\/([\w#!:.?+=&%@!\-\/]))?/gi,
            function(m) {
                return '<a href="' + m + '" target="_blank">' + m + "</a>"
            })
        }
    },
    lock: {
        init: function(_this, types, fn) {
            return _this ? (_this.locked = function() {
                App.lock.locked(_this)
            },
            _this.unlock = function(delay) {
                App.lock.lockedDelay(_this, delay || 0),
                App.lock.unlock(_this)
            },
            _this.on(types,
            function() {
                App.lock.isLocked(_this) || App.call(fn, _this)
            })) : void 0
        },
        locked: function(_this) {
            _this.attr(App.config.sign.locked, 1)
        },
        unlock: function(_this) {
            setTimeout(function() {
                _this.attr(App.config.sign.locked, 0)
            },
            App.parseInt(_this.attr(App.config.sign.lockedDelay)))
        },
        lockedDelay: function(_this, delay) {
            _this.attr(App.config.sign.lockedDelay, delay)
        },
        isLocked: function(_this) {
            return 1 == _this.attr(App.config.sign.locked)
        }
    },
    focusEnd: function(_this) {
        if (_this) {
            var length = _this.val().length;
            if (0 == _this.val().lengh) return _this;
            var input = _this[0];
            if (input.createTextRange) {
                var range = input.createTextRange();
                range.collapse(!0),
                range.moveEnd("character", length),
                range.moveStart("character", length),
                range.select()
            } else input.setSelectionRange && (input.focus(), input.setSelectionRange(length, length));
            return _this
        }
    }
};
$.fn.extend({
    postJSON: function(url, data, callback, error) {
        var _this = $(this);
        if (!App.lock.isLocked(_this)) {
            var _callback = function(data) {
                App.call(callback, data, _this),
                App.lock.unlock(_this)
            },
            _error = function() {
                console.info(App.config.info._404),
                App.call(error, _this),
                App.lock.unlock(_this)
            };
            return App.lock.locked(_this),
            App.postJSON(url, data, _callback, _error),
            this
        }
    },
    post: function(url, data, callback, error) {
        var _this = $(this);
        if (!App.lock.isLocked(_this)) {
            var _callback = function(data) {
                App.call(callback, data, _this),
                App.lock.unlock(_this)
            },
            _error = function() {
                console.info(App.config.info._404),
                App.call(error, _this),
                App.lock.unlock(_this)
            };
            return App.lock.locked(_this),
            App.post(url, data, _callback, _error),
            this
        }
    },
    lock: function(types, fn, delay) {
        return App.lock.lockedDelay(this, delay || 0),
        App.lock.init(this, types, fn)
    },
    focusEnd: function() {
        return App.focusEnd(this)
    }
}),
$(function() {
    $.isFunction($.fn.jQselectable) && ($("select.selectable").jQselectable({
        set: "fadeIn",
        setDuration: "fast",
        opacity: .9,
        callback: function() {
            $(this).trigger("change")
        }
    }), $("select.simpleselectable").jQselectable({
        style: "simple",
        set: "slideDown",
        out: "fadeOut",
        setDuration: 150,
        outDuration: 150,
        setDuration: "fast",
        opacity: .9,
        callback: function() {
            $(this).trigger("change")
        }
    })),
    $(".app_get_click_event").live("click",
    function() {
        var _this = $(this);
        if ("1" != _this.attr("app_loading")) {
            _this.attr("app_loading", 1);
            var _callbeforeback = _this.attr("app_call_before"),
            _callback = _this.attr("app_call_back"),
            detail = _this.data("detail"),
            _redirectUrl = _this.attr("redirectUrl"),
            _app_delay = _this.attr("app_delay"),
            _nocache = _this.attr("app_nocache") || "false",
            _app_error_prompt = _this.attr("app_error_prompt") || "true";

            _callbeforeback && eval(_callbeforeback + "( _this )"),
            detail ? (_callback && eval(_callback + "( _this, detail )"), _this.attr("app_loading", 0)) : App.getJSON(_this.attr("dataurl"),
            function(data) {
                data.success && "false" == _nocache && _this.data("detail", data),
                $17.isBlank(_redirectUrl) || setTimeout(function() {
                    location.href = _redirectUrl
                },
                App.parseInt(_app_delay, 0)),
                eval(_callback + "( _this, data )"),
                _this.attr("app_loading", 0)
            },
            function(e) {
                var data = {
                    success: !1,
                    info: "网络请求失败"
                };
                eval(_callback + "( _this, data )"),
                _this.attr("app_loading", 0),
                "true" == _app_error_prompt && alert("网络请求失败，请稍等重试或者联系客服人员")
            })
        }
    }),
    $(".app_get_html_click_event").live("click",function(){
	    var _this = $(this),
	    _appName = _this.attr("app_name"),
	    _callbeforeback = _this.attr("app_call_before"),
	    _calllaterback = _this.attr("app_call_later"),
	    _callcompleteback = _this.attr("app_call_complete"),
	    _writeOnce = _this.attr("app_write_once"),
	    _writeTarget = _this.attr("app_write_target"),
	    _htmlPlace = _this.attr("app_html_place");
	    _writeTarget = _writeTarget || "body",
	    _writeTarget = "this" == _writeTarget ? this: _writeTarget,
	    _callbeforeback && App.call(_callbeforeback, _this);
	    var detail = _this.data("detail");
	    detail ? (_calllaterback && App.call(_calllaterback, _this), _writeOnce ? $17.isBlank(_appName) || App.call("app_auto_html_" + _appName + "_init", _this) : "foot" == _htmlPlace ? $(_writeTarget).append(detail) : "body" == _htmlPlace ? $(_writeTarget).html(detail) : $(_writeTarget).prepend(detail), _callcompleteback && App.call(_callcompleteback, _this)) : $.get(_this.attr("dataurl"),
	    function(data) {
	        _this.data("detail", data),
	        _calllaterback && App.call(_calllaterback, _this),
	        "foot" == _htmlPlace ? $(_writeTarget).append(data) : "body" == _htmlPlace ? $(_writeTarget).html(data) : $(_writeTarget).prepend(data),
	        _callcompleteback && App.call(_callcompleteback, _this)
	    })
	}),
	$(".app_init_auto_get_html").each(function() {
	    var _this = $(this),
	    _app_delay = _this.attr("app_delay") || 0;
	    _this.attr("dataurl") && setTimeout(function() {
	        $.get(_this.attr("dataurl"),
	        function(data) {
	            _this.html(data)
	        })
	    },
	    _app_delay)
	})
});