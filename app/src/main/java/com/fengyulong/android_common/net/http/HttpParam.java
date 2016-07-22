/**
 *
 */
package com.fengyulong.android_common.net.http;

import com.woke.common.utils.WokeCommonUtil;

import java.io.UnsupportedEncodingException;

/**
 * @author Bean
 */
public class HttpParam {

    private String name;
    private String value;

    public HttpParam(String name, String value) {
        this.name = name;
        if (value != null) {
            try {
                //value = ;
                value = new String(value.getBytes(), WokeCommonUtil.DEFAULT_CHARSET);
            } catch (UnsupportedEncodingException e) {
            }
        }
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        if (value != null) {
            try {
                value = new String(value.getBytes(), WokeCommonUtil.DEFAULT_CHARSET);
            } catch (UnsupportedEncodingException e) {
            }
        }

        this.value = value;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        HttpParam other = (HttpParam) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (value == null) {
            if (other.value != null)
                return false;
        } else if (!value.equals(other.value))
            return false;
        return true;
    }
}
