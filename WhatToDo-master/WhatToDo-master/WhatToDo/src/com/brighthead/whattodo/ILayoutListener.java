package com.brighthead.whattodo;

public interface ILayoutListener {
    public void commit(String key, String value);
    public void end();
}
