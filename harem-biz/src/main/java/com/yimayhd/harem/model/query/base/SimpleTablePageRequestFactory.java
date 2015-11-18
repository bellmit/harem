package com.yimayhd.harem.model.query.base;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.yimayhd.harem.base.BaseQuery;


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

	public static <T extends BaseQuery> PageRequest<T> newPageRequest(final HttpServletRequest request, T query,
			final String defaultSortColumns) {
		return newPageRequest(request, query, defaultSortColumns, DEFAULT_PAGE_SIZE);
	}

	public static <T extends BaseQuery> PageRequest<T> newPageRequest(final HttpServletRequest request, T query,
			final String defaultSortColumns, final int defaultPageSize) {
		final PageRequest<T> pageRequest = new PageRequest<T>();
		return bindPageRequestParameters(pageRequest, request, query, defaultSortColumns, defaultPageSize);
	}

	public static <T extends BaseQuery> PageRequest<T> bindPageRequestParameters(final PageRequest<T> pageRequest,
			final HttpServletRequest request, T query, final String defaultSortColumns, final int defaultPageSize) {
		pageRequest.setPageNumber(getIntParameter(request, "pageNumber", 1));
		pageRequest.setPageSize(getIntParameter(request, "pageSize", defaultPageSize));
		pageRequest.setSortColumns(getStringParameter(request, "sortColumns", defaultSortColumns));
		pageRequest.setQuery(query);

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
