package com.yimayhd.palace.model.vo.apply;

import java.io.Serializable;

/**
 * Created by moon_princess on 16/5/25.
 */
public class AllocationVO implements Serializable {
    private static final long serialVersionUID = 6834660850877888460L;

    private long examineId;
    private long[] categoryIds;

    public long getExamineId() {
        return examineId;
    }

    public void setExamineId(long examineId) {
        this.examineId = examineId;
    }

    public long[] getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(long[] categoryIds) {
        this.categoryIds = categoryIds;
    }
}
