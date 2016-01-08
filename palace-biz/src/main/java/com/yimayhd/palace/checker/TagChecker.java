package com.yimayhd.palace.checker;

import org.apache.commons.lang3.StringUtils;

import com.yimayhd.palace.checker.result.CheckResult;
import com.yimayhd.palace.model.LiveTagVO;

public class TagChecker {
	public static CheckResult checkLiveTagVOForSave(LiveTagVO tag) {
		if (StringUtils.isBlank(tag.getName())) {
			return CheckResult.error("标签名称不能为空");
		}
		return CheckResult.success();
	}

	public static CheckResult checkLiveTagVOForUpdate(LiveTagVO tag) {
		if (tag.getId() <= 0) {
			return CheckResult.error("标签ID不能为0");
		}
		if (StringUtils.isBlank(tag.getName())) {
			return CheckResult.error("标签名称不能为空");
		}
		return CheckResult.success();
	}
}
