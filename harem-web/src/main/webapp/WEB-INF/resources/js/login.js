var opChk = function(obj) {
	var curState = $(obj).attr("class");
	if (curState == "ico_checkUn") {
		curState = "ico_checkOk";
	} else {
		curState = "ico_checkUn";
	}
	$(obj).attr("class", curState);
}

var getInfo_fromCookie = function() {
	var user = $.cookie('lvdao_user');
	var company = $.cookie('lvdao_company');
	if (user)
		$("#username").val(user);
	if (company) {
		$("#company").val(company);
		$("#isRemember").attr("class", "ico_checkOk");
	}
}

var checkSubmitFlg = false;

function is_CheckSubmit() {
	if (checkSubmitFlg == true) {
		return false;
	}
	checkSubmitFlg = true;
	return true;
}

var loginVerify = function() {
	var form = document.getElementById('loginForm');
	if (!chkLen(form.company, 20, '企业编码', false)) {
		return;
	}
	if (!chkLen(form.username, 20, '用户名', false)) {
		return;
	}
	if (!chkLen(form.password, 20, '密码', false)) {
		return;
	}
	if (!chkLen(form.verify, 4, '验证码', false)) {
		return;
	}

	//记住cookie状态
	/*var curState = $("#isRemember").attr("class");
	if (curState == "ico_checkUn") {
		$.cookie('lvdao_user', '');
		$.cookie('lvdao_company', '');
	} else {
		$.cookie('lvdao_user', $("#username").val());
		$.cookie('lvdao_company', $("#company").val());
	}

	if (is_CheckSubmit()) {
		form.submit();
	} else {
		alert('在上一个动作未执行完成之前，请不要重复提交。');
		checkSubmitFlg = false;
	}*/
}

jQuery(function($) {
	$.supersized({
		slide_interval: 5000,
		transition: 1,
		transition_speed: 3000,
		performance: 1,
		min_width: 0,
		min_height: 0,
		vertical_center: 1,
		horizontal_center: 1,
		fit_always: 0,
		fit_portrait: 1,
		fit_landscape: 0,
		slide_links: 'blank',
		slides: [{
			image: '/resources/img/bj01.jpg'
		}, {
			image: '/resources/img/bj02.jpg'
		}, {
			image: '/resources/img/bj03.jpg'
		}, {
			image: '/resources/img/bj04.jpg'
		}]
	});


	//document.getElementById("verify").src = "image.jsp?" + Math.random();

	$("#btnSubmit").bind("click", function() {
		loginVerify();
	});
	$("#isRemember").bind("click", function() {
		opChk(this);
	});
	getInfo_fromCookie();
});