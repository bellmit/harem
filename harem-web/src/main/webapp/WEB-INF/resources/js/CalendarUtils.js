/**
 * 日期工具
 */
var CalendarUtils = {
	// 日期+天
	AddDays : function(d, n) {
		var t = new Date(d);// 复制并操作新对象，避免改动原对象
		t.setDate(t.getDate() + n);
		return t;
	},
	// 日期+月。日对日，若目标月份不存在该日期，则置为最后一日
	AddMonths : function(d, n) {
		var t = new Date(d);
		t.setMonth(t.getMonth() + n);
		if (t.getDate() != d.getDate()) {
			t.setDate(0);
		}
		return t;
	},
	// 日期+年。月对月日对日，若目标年月不存在该日期，则置为最后一日
	AddYears : function(d, n) {
		var t = new Date(d);
		t.setFullYear(t.getFullYear() + n);
		if (t.getDate() != d.getDate()) {
			t.setDate(0);
		}
		return t;
	},
	// 月初
	getMonthStartDate : function(date) {
		return new Date(date.getFullYear(), date.getMonth(), 1);
	},
	/*
	 * // 获得本季度的开始月份 getQuarterStartMonth : function() { if (nowMonth <= 2) {
	 * return 0; } else if (nowMonth <= 5) { return 3; } else if (nowMonth <= 8) {
	 * return 6; } else { return 9; } }, // 周一 getWeekStartDate : function() {
	 * return AddDays(now, -nowDayOfWeek); }, // 周日。本周一+6天 getWeekEndDate :
	 * function() { return AddDays(getWeekStartDate(), 6); }, // 月末。下月初-1天
	 * getMonthEndDate : function() { return
	 * AddDays(AddMonths(getMonthStartDate(), 1), -1); }, // 季度初
	 * getQuarterStartDate : function() { return new Date(nowYear,
	 * this.getQuarterStartMonth(), 1); }, // 季度末。下季初-1天 getQuarterEndDate :
	 * function() { return
	 * this.AddDays(this.AddMonths(this.getQuarterStartDate(), 3), -1); },
	 */
	// 格式化
	getFormat : function(date, fmt) {
		fmt = fmt + '';
		var o = {
			"M+" : date.getMonth() + 1, // 月份
			"d+" : date.getDate(), // 日
			"h+" : date.getHours(), // 小时
			"m+" : date.getMinutes(), // 分
			"s+" : date.getSeconds(), // 秒
			"q+" : Math.floor((date.getMonth() + 3) / 3), // 季度
			"S" : date.getMilliseconds()
		// 毫秒
		};
		if (/(y+)/.test(fmt))
			fmt = fmt.replace(RegExp.$1, (date.getFullYear() + "")
					.substr(4 - RegExp.$1.length));
		for ( var k in o)
			if (new RegExp("(" + k + ")").test(fmt))
				fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
						: (("00" + o[k]).substr(("" + o[k]).length)));
		return fmt;
	},
	// 得到按月分类的树
	getMonthTree : function(startDate, endDate) {
		var dateJson = {};
		for (i = startDate; Date.parse(i) <= Date.parse(endDate); i = this
				.AddDays(i, 1)) {
			var key = this.getMonthStartDate(i).getTime();
			if (dateJson[key] != null) {
				dateJson[key].push(i.getTime());
			} else {
				dateJson[key] = [ i.getTime() ];
			}
		}
		return dateJson;
	}
};