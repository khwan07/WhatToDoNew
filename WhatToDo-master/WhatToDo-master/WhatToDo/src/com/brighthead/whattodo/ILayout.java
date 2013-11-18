package com.brighthead.whattodo;

import android.content.Context;

public interface ILayout {
    public void init(Context con);
    public void start();
    public void setListener(ILayoutListener listener);
    public void stop();
    public void release();
}
