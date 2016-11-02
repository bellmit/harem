package com.yimayhd.gf.model.query;

import com.yimayhd.palace.base.BaseQuery;

/**
 * Created by p on 10/21/16.
 */
public class GfAgentPageQuery extends BaseQuery {
    private static final long serialVersionUID = 7279675881936788459L;
    private String agentName;
    private String agentLevel;
    private String startAt;
    private String endAt;
    private Integer agentStatus;
    private String parentName;

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getAgentLevel() {
        return agentLevel;
    }

    public void setAgentLevel(String agentLevel) {
        this.agentLevel = agentLevel;
    }

    public String getStartAt() {
        return startAt;
    }

    public void setStartAt(String startAt) {
        this.startAt = startAt;
    }

    public String getEndAt() {
        return endAt;
    }

    public void setEndAt(String endAt) {
        this.endAt = endAt;
    }

    public Integer getAgentStatus() {
        return agentStatus;
    }

    public void setAgentStatus(Integer agentStatus) {
        this.agentStatus = agentStatus;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }
}
