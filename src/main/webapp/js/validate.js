//window.alert = elbsAlert;
//function elbs(){};
$(function(){
//全局
	$("input:button, input:submit, .inputBtn").button();
	$("#birthday").datepicker({altFormat: 'yy-mm-dd', dateFormat: 'yy-mm-dd'});
	$(".btnCls").button();
});
var elbs = (function(){
	return {
		alert : function(msg, title) {
			if ($("#alert-dialog").length == 0) {
				$("body").append("<div id='alert-dialog'></div>");
			}
			$("#alert-dialog").html(msg);
			$("#alert-dialog").dialog({
				autoOpen: true,
				title : title == null? '提示' : title,
				modal : true,
				resizable : false,
				buttons : {
					"ok" : function(){
						$(this).dialog("close");
					}
				}
			});
			//$("#alert-dialog").dialog("open");
		}, 
		confirm : function(msg, title, buttons){
			if ($("#confirm-dialog").length == 0) {
				$("body").append("<div id='confirm-dialog'></div>");
			};
			$("#confirm-dialog").html(msg);
			$("#confirm-dialog").dialog({
				autoOpen: true,
				title: title == null? '提示' : title,
				modal: true,
				resizable: false,
				buttons: buttons				
			});
		},
		progressbar : function(options, title, modal){
			options = options == {} ? {value: 100} : options;
			if ($("#pogressbar-dialog").length == 0) {
				$("body").append("<div id='progressbar-dialog'><div id='progressbar'></div></div>");
			};
			//$("#progressbar").html(text);
			$("#progressbar").progressbar(options);
			$(".ui-dialog-titlebar-close").hide();
			$("#progressbar-dialog").dialog({
				autoOpen: true,
				title : title == null? '正在执行操作，请稍候...' : title,
				modal : modal == null? true : modal,
				height: 80,
				resizable : false
			});
		},
		closeProgressbar : function(){
			$("#progressbar-dialog").dialog("close");
		}
	};
})();
var elbs1 = (function() {
	return {
		alert : function(msg){
			alert(msg);
		}
	};
})();


//判断字段是否为空
function isBlank(el, err, errMsg){
	var data = $(el).val().trim();
	var len = data.length;
	if (len < 1) {
		$(err).html(errMsg);
		$(err).show();
		return false;
	} else {
		$(err).hide();
		return true;
	}
}

function isBlank1(el){
	var data = $(el).val().trim();
	var len = data.length;
	if (len < 1) {
		return false;
	} else {
		return true;
	}
}

function validate_userid(el, err, errMsg){
	var data = $(el).val().trim();
	if (data != "") {
		var regStr = /^[a-z0-9_]+$/;
		testRegExp(data, err, errMsg, regStr);
	} else {
		showMsg(data, err, errMsg);
	}
}

function validate_date(el, err, errMsg){
	var data = $(el).val().trim();
	if (data != "") {
		var regStr = /\b\d{4}[\/-]\d{1,2}[\/-]\d{1,2}\b/;
		testRegExp(data, err, errMsg, regStr);
	} else {
		showMsg(data, err, errMsg);
	}
}

function validate_email(el, err, errMsg){
	var data = $(el).val().trim();
	if (data != "") {
		var regStr = /^[\w-]+(\.[\w-]+)*@([\w-]+\.)+[a-zA-Z]+$/;
		testRegExp(data, err, errMsg, regStr);
	} else {
		showMsg(data, err, errMsg);
	}
}

function testRegExp(data, err, errMsg, regStr){
	var pattern = new RegExp(regStr);
	if (pattern.test(data)) {
		$(err).hide();
		return true;
	} else {
		$(err).html(errMsg);
		$(err).show();
		return false;
	}
	return false;
}

function showMsg(data, err, errMsg, regStr){
	$(err).html(errMsg);
	$(err).show();
}

/*$(function(){
//common setting
	
	
	$("#alertDialog").dialog({
		autoOpen: false,
		title : '提示',
		//modal : true,
		resizable : false,
		buttons : {
			"ok" : function(){
				$(this).dialog("close");
			}
		}
	});
});*/



//jquery获取地址栏参数插件
$.request = (function () {
    var apiMap = {};

    function request(queryStr) {
        var api = {};

        if (apiMap[queryStr]) {
            return apiMap[queryStr];
        }

        api.queryString = (function () {
            var urlParams = {};
            var e,
            d = function (s) { return decodeURIComponent(s.replace(/\+/g, " ")); },
            q = queryStr.substring(queryStr.indexOf('?') + 1),
            r = /([^&=]+)=?([^&]*)/g;

            while (e = r.exec(q))
                urlParams[d(e[1])] = d(e[2]);


            return urlParams;
        })();

        api.getUrl = function () {
            var url = queryStr.substring(0, queryStr.indexOf('?') + 1);

            for (var p in api.queryString) {
                url += p + '=' + api.queryString[p] + "&";
            }

            if (url.lastIndexOf('&') == url.length - 1) {
                return url.substring(0, url.lastIndexOf('&'));
            }
            return url;
        };

        apiMap[queryStr] = api;
        return api;
    }

    $.extend(request, request(window.location.href));

    return request;

})();
