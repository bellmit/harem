package com.yimayhd.palace.result;

import java.util.List;

/**
 * 分页
 * 
 * @author yebin
 *
 * @param <T>
 */
public class BizPageResult<T> extends BizResultSupport {
	private static final long serialVersionUID = 7378807577314788084L;
	protected int pageNo = 1;
	protected int pageSize;
	protected int totalCount;
	protected List<T> list;

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		if (pageNo <= 0) {
			pageNo = 1;
		}
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		if (pageSize < 0) {
			pageSize = 0;
		}
		this.pageSize = pageSize;
	}

	public int getStartRow() {
		return (pageNo - 1) * pageSize;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public static <U> BizPageResult<U> fail(int code, String msg, List<U> value) {
		BizPageResult<U> baseResult = new BizPageResult<U>();
		baseResult.setSuccess(false);
		baseResult.setCode(code);
		baseResult.setMsg(msg);
		baseResult.setList(value);
		return baseResult;
	}

	public static <U> BizPageResult<U> success(List<U> value) {
		BizPageResult<U> baseResult = new BizPageResult<U>();
		baseResult.setSuccess(true);
		baseResult.setCode(SUCCESS_CODE);
		baseResult.setMsg("操作成功");
		baseResult.setList(value);
		return baseResult;
	}
}
