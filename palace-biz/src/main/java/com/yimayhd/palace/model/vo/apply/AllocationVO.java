package com.yimayhd.palace.model.vo.apply;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by moon_princess on 16/5/25.
 */
public class AllocationVO implements Serializable {
    private static final long serialVersionUID = 6834660850877888460L;

    private long examineId;
    private String categoryIds;

    public long getExamineId() {
        return examineId;
    }

    public void setExamineId(long examineId) {
        this.examineId = examineId;
    }

    public String getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(String categoryIds) {
        this.categoryIds = categoryIds;
    }
}
