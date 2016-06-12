package com.yimayhd.palace.model.vo;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.List;

/**
 * @author create by yushengwei on 2016/6/3
 * @Description
 * @return $returns
 */
public class OperactionsVO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String code;
    private String name;
    private String jsonOperationDetails;
    List<OperationDetails> listOperationDetails;

    class OperationDetails{
        private Long id;
        private Long operationId;
        private int showType;
        private int jumpType;
        private String showName;
        private String showValue;

        @Override
        public String toString() {
            return "OperationDetails{" +
                    "id=" + id +
                    ", operationId=" + operationId +
                    ", showType=" + showType +
                    ", jumpType=" + jumpType +
                    ", showName='" + showName + '\'' +
                    ", showValue='" + showValue + '\'' +
                    '}';
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Long getOperationId() {
            return operationId;
        }

        public void setOperationId(Long operationId) {
            this.operationId = operationId;
        }

        public int getShowType() {
            return showType;
        }

        public void setShowType(int showType) {
            this.showType = showType;
        }

        public int getJumpType() {
            return jumpType;
        }

        public void setJumpType(int jumpType) {
            this.jumpType = jumpType;
        }

        public String getShowName() {
            return showName;
        }

        public void setShowName(String showName) {
            this.showName = showName;
        }

        public String getShowValue() {
            return showValue;
        }

        public void setShowValue(String showValue) {
            this.showValue = showValue;
        }
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<OperationDetails> getListOperationDetails() {
        return listOperationDetails;
    }

    public void setListOperationDetails(List<OperationDetails> listOperationDetails) {
        this.listOperationDetails = listOperationDetails;
    }

    public  String getJsonOperationDetails() {
        synchronized(this) {
            List<OperationDetails> listOperationDetail = this.getListOperationDetails();
            return JSON.toJSONString(listOperationDetail);
        }
    }

    public void setJsonOperationDetails(String jsonOperationDetails) {
        this.jsonOperationDetails = jsonOperationDetails;
    }

    @Override
    public String toString() {
        return "OperactionsVO{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", listOperationDetails=" + listOperationDetails +
                '}';
    }
}
