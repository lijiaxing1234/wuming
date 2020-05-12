var template = function(id, content) {
    return template["object" == typeof content ? "render": "compile"].apply(template, arguments)
}; 
!function(exports, global) {
    "use strict";
    exports.version = "2.0.0",
    exports.openTag = "<%",
    exports.closeTag = "%>",
    exports.isEscape = !0,
    exports.parser = null,
    exports.render = function(id, data) {
        var cache = _getCache(id);
        return void 0 === cache ? _debug({
            id: id,
            name: "Render Error",
            message: "No Template"
        }) : cache(data)
    },
    exports.compile = function(id, source) {
        function render(data) {
            try {
                return new Render(data) + ""
            } catch(e) {
                return isDebug ? (e.id = id || source, e.name = "Render Error", e.source = source, _debug(e)) : exports.compile(id, source, !0)(data)
            }
        }
        var params = arguments,
        isDebug = params[2],
        anonymous = "anonymous";
        "string" != typeof source && (isDebug = params[1], source = params[0], id = anonymous);
        try {
            var Render = _compile(source, isDebug)
        } catch(e) {
            return e.id = id || source,
            e.name = "Syntax Error",
            _debug(e)
        }
        return render.prototype = Render.prototype,
        render.toString = function() {
            return Render.toString()
        },
        id !== anonymous && (_cache[id] = render),
        render
    },
    exports.helper = function(name, helper) {
        exports.prototype[name] = helper
    },
    exports.onerror = function(e) {
        var content = "[template]:\n" + e.id + "\n\n[name]:\n" + e.name;
        throw e.message && (content += "\n\n[message]:\n" + e.message),
        e.line && (content += "\n\n[line]:\n" + e.line, content += "\n\n[source]:\n" + e.source.split(/\n/)[e.line - 1].replace(/^[\s\t]+/, "")),
        e.temp && (content += "\n\n[temp]:\n" + e.temp),
        global.console && console.error(content),
        new Error(content, "", "")
    };
    var _cache = {},
    _getCache = function(id) {
        var cache = _cache[id];
        if (void 0 === cache && "document" in global) {
            var elem = document.getElementById(id);
            if (elem) {
                var source = elem.value || elem.innerHTML;
                return exports.compile(id, source.replace(/^\s*|\s*$/g, ""))
            }
        } else if (_cache.hasOwnProperty(id)) return cache
    },
    _debug = function(e) {
        function error() {
            return error + ""
        }
        return exports.onerror(e),
        error.toString = function() {
            return "{Template Error}"
        },
        error
    },
    _compile = function() {
        exports.prototype = {
            $render: exports.render,
            $escapeHTML: function(content) {
                return "string" == typeof content ? content.replace(/&(?![\w#]+;)|[<>"']/g,
                function(s) {
                    return {
                        "<": "&#60;",
                        ">": "&#62;",
                        '"': "&#34;",
                        "'": "&#39;",
                        "&": "&#38;"
                    } [s]
                }) : content
            },
            $getValue: function(value) {
                return "string" == typeof value || "number" == typeof value ? value: "function" == typeof value ? value() : ""
            }
        };
        var arrayforEach = Array.prototype.forEach ||
        function(block, thisObject) {
            for (var len = this.length >>> 0,
            i = 0; len > i; i++) i in this && block.call(thisObject, this[i], i, this)
        },
        forEach = function(array, callback) {
            arrayforEach.call(array, callback)
        },
        keyWords = "break,case,catch,continue,debugger,default,delete,do,else,false,finally,for,function,if,in,instanceof,new,null,return,switch,this,throw,true,try,typeof,var,void,while,with,abstract,boolean,byte,char,class,const,double,enum,export,extends,final,float,goto,implements,import,int,interface,long,native,package,private,protected,public,short,static,super,synchronized,throws,transient,volatile,arguments,let,yield,undefined",
        filter = new RegExp(["/\\*(.|\n)*?\\*/|//[^\n]*\n|//[^\n]*$", "'[^']*'|\"[^\"]*\"", "\\.[s	\n]*[\\$\\w]+", "\\b" + keyWords.replace(/,/g, "\\b|\\b") + "\\b"].join("|"), "g"),
        _getVariable = function(code) {
            return code = code.replace(filter, ",").replace(/[^\w\$]+/g, ",").replace(/^,|^\d+|,\d+|,$/g, ""),
            code ? code.split(",") : []
        };
        return function(source, isDebug) {
            function html(code) {
                return line += code.split(/\n/).length - 1,
                code = code.replace(/('|"|\\)/g, "\\$1").replace(/\r/g, "\\r").replace(/\n/g, "\\n"),
                code = replaces[1] + "'" + code + "'" + replaces[2],
                code + "\n"
            }
            function logic(code) {
                var thisLine = line;
                if (parser ? code = parser(code) : isDebug && (code = code.replace(/\n/g,
                function() {
                    return line++,
                    "$line=" + line + ";"
                })), 0 === code.indexOf("=")) {
                    var isEscape = 0 !== code.indexOf("==");
                    if (code = code.replace(/^=*|[\s;]*$/g, ""), isEscape && exports.isEscape) {
                        var name = code.replace(/\s*\([^\)]+\)/, "");
                        helpers.hasOwnProperty(name) || /^(include|print)$/.test(name) || (code = "$escapeHTML($getValue(" + code + "))")
                    } else code = "$getValue(" + code + ")";
                    code = replaces[1] + code + replaces[2]
                }
                return isDebug && (code = "$line=" + thisLine + ";" + code),
                getKey(code),
                code + "\n"
            }
            function getKey(code) {
                code = _getVariable(code),
                forEach(code,
                function(name) {
                    uniq.hasOwnProperty(name) || (setValue(name), uniq[name] = !0)
                })
            }
            function setValue(name) {
                var value;
                "print" === name ? value = print: "include" === name ? (prototype.$render = helpers.$render, value = include) : (value = "$data." + name, helpers.hasOwnProperty(name) && (prototype[name] = helpers[name], value = 0 === name.indexOf("$") ? "$helpers." + name: value + "===undefined?$helpers." + name + ":" + value)),
                variables += name + "=" + value + ","
            }
            var openTag = exports.openTag,
            closeTag = exports.closeTag,
            parser = exports.parser,
            code = source,
            tempCode = "",
            line = 1,
            uniq = {
                $data: !0,
                $helpers: !0,
                $out: !0,
                $line: !0
            },
            helpers = exports.prototype,
            prototype = {},
            variables = "var $helpers=this," + (isDebug ? "$line=0,": ""),
            isNewEngine = "".trim,
            replaces = isNewEngine ? ["$out='';", "$out+=", ";", "$out"] : ["$out=[];", "$out.push(", ");", "$out.join('')"],
            concat = isNewEngine ? "if(content!==undefined){$out+=content;return content}": "$out.push(content);",
            print = "function(content){" + concat + "}",
            include = "function(id,data){if(data===undefined){data=$data}var content=$helpers.$render(id,data);" + concat + "}";
            forEach(code.split(openTag),
            function(code, i) {
                code = code.split(closeTag);
                var $0 = code[0],
                $1 = code[1];
                1 === code.length ? tempCode += html($0) : (tempCode += logic($0), $1 && (tempCode += html($1)))
            }),
            code = tempCode,
            isDebug && (code = "try{" + code + "}catch(e){e.line=$line;throw e}"),
            code = "'use strict';" + variables + replaces[0] + code + "return new String(" + replaces[3] + ")";
            try {
                var Render = new Function("$data", code);
                return Render.prototype = prototype,
                Render
            } catch(e) {
                throw e.temp = "function anonymous($data){" + code + "}",
                e
            }
        }
    } ()
} (template, this),
"undefined" != typeof module && module.exports ? module.exports = template: "function" == typeof define && define.amd && define("template", [],
function() {
    return template
});
//# sourceMappingURL=template.min.js.map