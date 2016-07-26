package com.yimayhd.palace.util;

import com.yimayhd.commentcenter.client.enums.TagType;

/**
 * 标签工具类
 * 
 * @author xiemingna
 *
 */
public class TagUtil {
	public static String getComTagName(int tagType) {
		if (tagType <= 0) {
			return null;
		}
		TagType it = TagType.getByType(tagType);
		if (it != null) {
			return it.getDesc();
		}
		return null;
	}
}
