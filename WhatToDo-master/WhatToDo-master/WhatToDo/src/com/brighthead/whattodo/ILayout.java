package com.brighthead.whattodo;

import android.content.Context;

public interface ILayout {
    public void init();
    public void start();
    public void resume(Context con);
    public void setListener(ILayoutListener listener);
    public void stop();
    public void release();
}
