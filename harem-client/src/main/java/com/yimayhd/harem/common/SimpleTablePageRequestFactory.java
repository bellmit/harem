package com.yimayhd.harem.common;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.util.WebUtils;

/**
 * PageRequest工厂
 * 
 * @author yebin
 *
 */
public class SimpleTablePageRequestFactory {

	private static final int MAX_PAGE_SIZE = 500;
	private static final int DEFAULT_PAGE_SIZE = 20;

	static {
		System.out.println("SimpleTablePageRequestFactory.DEFAULT_PAGE_SIZE=" + DEFAULT_PAGE_SIZE);
		System.out.println("SimpleTablePageRequestFactory.MAX_PAGE_SIZE=" + MAX_PAGE_SIZE);
	}

	public static PageRequest newPageRequest(final HttpServletRequest request, final String defaultSortColumns) {
		return newPageRequest(request, defaultSortColumns, DEFAULT_PAGE_SIZE);
	}

	public static PageRequest newPageRequest(final HttpServletRequest request, final String defaultSortColumns,
			final int defaultPageSize) {
		final PageRequest pageRequest = new PageRequest();
		return bindPageRequestParameters(pageRequest, request, defaultSortColumns, defaultPageSize);
	}

	public static PageRequest bindPageRequestParameters(final PageRequest pageRequest, final HttpServletRequest request,
			final String defaultSortColumns, final int defaultPageSize) {
		pageRequest.setPageNumber(getIntParameter(request, "pageNumber", 1));
		pageRequest.setPageSize(getIntParameter(request, "pageSize", defaultPageSize));
		pageRequest.setSortColumns(getStringParameter(request, "sortColumns", defaultSortColumns));
		pageRequest.setFilters(WebUtils.getParametersStartingWith(request, "s_"));

		if (pageRequest.getPageSize() > MAX_PAGE_SIZE) {
			pageRequest.setPageSize(MAX_PAGE_SIZE);
		}
		return pageRequest;
	}

	static String getStringParameter(final HttpServletRequest request, final String paramName,
			final String defaultValue) {
		final String value = request.getParameter(paramName);
		return StringUtils.isEmpty(value) ? defaultValue : value;
	}

	static int getIntParameter(final HttpServletRequest request, final String paramName, final int defaultValue) {
		final String value = request.getParameter(paramName);
		return StringUtils.isEmpty(value) ? defaultValue : Integer.parseInt(value);
	}

}
