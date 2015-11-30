package com.yimayhd.harem.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/11/30.
 */
//TODO 接口json没有提供，暂时加在这里
public class Picture implements Serializable {

    private static final long serialVersionUID = -8967754226111592225L;
    private String url;
    private String Name;
    private int top;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
