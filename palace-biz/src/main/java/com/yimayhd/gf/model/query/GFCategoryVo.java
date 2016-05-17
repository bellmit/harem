package com.yimayhd.gf.model.query;

import java.util.Date;
import java.util.List;

import com.yimayhd.commentcenter.client.domain.CategoryDO;
import com.yimayhd.palace.base.BaseQuery;

public class GFCategoryVo extends BaseQuery {

	private static final long serialVersionUID = 1L;
	
	private long id;

    private int domain;

    private String name;

    private int level;

    private int leaf;

    private Integer parentId;

    private int priority;

    private int status;

    private int version;

    private int sellerId;

    private Date gmtCreated;

    private Date gmtModified;

    //子节点
    private List<CategoryDO> nodeCategoryList;
    
    private List<Long> itemIdList; //商品id集合

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getDomain() {
		return domain;
	}

	public void setDomain(int domain) {
		this.domain = domain;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getLeaf() {
		return leaf;
	}

	public void setLeaf(int leaf) {
		this.leaf = leaf;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public int getSellerId() {
		return sellerId;
	}

	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}

	public Date getGmtCreated() {
		return gmtCreated;
	}

	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public List<CategoryDO> getNodeCategoryList() {
		return nodeCategoryList;
	}

	public void setNodeCategoryList(List<CategoryDO> nodeCategoryList) {
		this.nodeCategoryList = nodeCategoryList;
	}

	public List<Long> getItemIdList() {
		return itemIdList;
	}

	public void setItemIdList(List<Long> itemIdList) {
		this.itemIdList = itemIdList;
	}
	
	
	 
	
	
	
}
