var pageUtils = {
	intall : function(id, url, callBack) {
		pageUtils.id = id;
		pageUtils.url = url;
		pageUtils.callBack = callBack || pageUtils.callBack;
		pageUtils.request();
	},
	id : "_id_",
	url : "_url_",
	pageParams : "_page_params_",
	callBack : function(query, pageVo) {
	},
	pageBar : function(pageVo) {
		var content = $(pageUtils.id);
		var root = $("<div class='pagination pagination-large pagination-right whole'/>");
		var pageVoNo = pageVo.thisPageNumber;
		var before = 0;
		var after = 0;
		var pager = $("<ul class='pager pager-pills'/>");
		root.append(pager);
		if (pageVo.hasPreviousPage) {
			var previous = $("<li class='previous'/>");
			var LinkeToPage = $(" <a href='javascript:void(0)' class='LinkeToPage' pageNo='"
					+ pageVo.previousPageNumber + "' id='prePage'>« 上一页</a>");
			LinkeToPage
					.click(function() {
						pageUtils.request($(this).attr("pageNo"),
								$("#pageSize").val());
					});
			pager.append(previous.append(LinkeToPage));
		} else {
			var previous = $("<li class='previous disabled'/>");
			var LinkeToPage = $("<a href='javascript:void(0)' id='prePage'>« 上一页</a>");
			pager.append(previous.append(LinkeToPage));
		}
		for (var i = 1; i <= pageVo.lastPageNumber; i++) {
			if (pageVoNo == i) {
				var li = $("<li class='active'<span><a href='javascript:void(0)'>"
						+ i + "</a></span></li>");
				pager.append(li);
			} else if (1 >= i - 1) {
				var li = $("<li><span><a href='javascript:void(0)' class='LinkeToPage' pageNo='"
						+ i + "'>" + i + "</a></span></li>");
				li.find(".LinkeToPage").click(
						function() {
							pageUtils.request($(this).attr("pageNo"), $(
									"#pageSize").val());
						});
				pager.append(li);
			} else if (pageVo.lastPageNumber <= i + 1) {
				var li = $("<li><span><a href='javascript:void(0)' class='LinkeToPage' pageNo='"
						+ i + "'>" + i + "</a></span></li>");
				li.find(".LinkeToPage").click(
						function() {
							pageUtils.request($(this).attr("pageNo"), $(
									"#pageSize").val());
						});
				pager.append(li);
			} else if (pageVoNo >= i - 2 && pageVoNo <= i + 2) {
				var li = $("<li><span><a href='javascript:void(0)' class='LinkeToPage' pageNo='"
						+ i + "'>" + i + "</a></span></li>");
				li.find(".LinkeToPage").click(
						function() {
							pageUtils.request($(this).attr("pageNo"), $(
									"#pageSize").val());
						});
				pager.append(li);
			} else if (pageVoNo > i && before == 0) {
				var li = $("<li><a href='javascript:void(0)' data-toggle='pager' data-placement='top'>...</a></li>");
				pager.append(li);
				before = 1;
			} else if (pageVoNo < i && after == 0) {
				var li = $("<li><a href='javascript:void(0)' data-toggle='pager' data-placement='top'>...</a></li>");
				pager.append(li);
				after = 1;
			}
		}
		if (pageVo.hasNextPage) {
			var next = $("<li class='next'/>");
			var LinkeToPage = $(" <a href='javascript:void(0)' class='LinkeToPage' pageNo='"
					+ pageVo.nextPageNumber + "' id='nextPage'>下一页»</a>");
			LinkeToPage
					.click(function() {
						pageUtils.request($(this).attr("pageNo"),
								$("#pageSize").val());
					});
			pager.append(next.append(LinkeToPage));
		} else {
			var next = $("<li class='next disabled'/>");
			var LinkeToPage = $("<a href='javascript:void(0)' id='nextPage'>下一页»</a>");
			pager.append(next.append(LinkeToPage));
		}
		var li = $("<li/>");
		var input_group = $("<div class='input-group'/>");
		input_group.append($("<span class='input-group-addon'>每页</span>"));
		var select = $("<select class='form-control pageSize' name='pageSize' id='pageSize'/>");
		select.append($("<option value='10'>10</option>"));
		select.append($("<option value='20'>20</option>"));
		select.append($("<option value='50'>50</option>"));
		select.val(pageVo.pageSize);
		select.change(function() {
			pageUtils.request(1, $(this).val());
		});
		input_group.append(select);
		input_group.append($("<span class='input-group-addon'>条</span>"));
		pager.append(li.append(input_group));
		content.append(root);
	},
	request : function(no, size, p) {
		var url = pageUtils.url;
		var params_str = $("#" + pageUtils.pageParams).val() || "{}";
		var params = JSON.parse(params_str);
		if (p != null) {
			for ( var i in params) {
				params[i] = p[i];
			}
		}
		if (no != null) {
			params["pageNumber"] = no;
		}
		if (size != null) {
			params["pageSize"] = size;
		}
		$.getJSON(url, params, function(data) {
			var content = $(pageUtils.id);
			content.empty();
			var params = $("<input type='hidden'/>");
			params.attr("id", pageUtils.pageParams);
			params.val(JSON.stringify(data.query));
			content.append(params);
			pageUtils.pageBar(data.pageVo);
			pageUtils.callBack(data.query, data.pageVo);
		});
	},
	formUrlKey : "_page_form_url_"
};
