package com.brighthead.whattodo;

public interface ILayout {
    public void init();
    public void start();
    public void setListener(ILayoutListener listener);
    public void stop();
    public void release();
}
