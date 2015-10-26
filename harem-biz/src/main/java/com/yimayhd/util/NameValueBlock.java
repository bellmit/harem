package com.yimayhd.util;

import java.io.Serializable;

/**
 * 自定义类似于key-value的键值对象。key可重复但不能为空
 *
 * Created by wqy on 15-5-3.
 *
 */
public class NameValueBlock implements Serializable{

    private static final long serialVersionUID = 1302435871395356303L;

    private final String name;
    private final String value;


    public NameValueBlock(final String name, final String value) {
        super();
        this.name = ArgsUtil.notNull(name, "Name");
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        if (this.value == null)
            return name;
        final Integer length = this.name.length() + 1 + this.value.length();
        final StringBuffer buffer = new StringBuffer(length);
        buffer.append(this.name);
        buffer.append(":");
        buffer.append(this.value);
        return buffer.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NameValueBlock that = (NameValueBlock) o;

        if (!name.equals(that.name)) return false;
        return !(value != null ? !value.equals(that.value) : that.value != null);

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }

}

