package com.brighthead.whattodo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import android.content.Context;

public class ResourceUtil {
    private static final int ID_COLOR_ARRAY = R.array.color_entries;
    private static final int ID_COLOR_NAME_ARRAY = R.array.color_name_entries;
    
    public static ArrayList<String> COLOR_NAME_LIST = null;
    public static HashMap<String, Integer> COLOR_LIST = null;
    
//    private static ResourceUtil mResourceUtil = null;
    
    /*public static ResourceUtil getInstance() {
        if (mResourceUtil == null) {
            mResourceUtil = new ResourceUtil();
            return mResourceUtil;
        }
        return mResourceUtil;
    }*/
    
    public ResourceUtil() {
        
    }
    
    public static void initialize(Context con) {
        String[] name = con.getResources().getStringArray(ID_COLOR_NAME_ARRAY);
        int[] id = con.getResources().getIntArray(ID_COLOR_ARRAY);
        
        COLOR_NAME_LIST = new ArrayList<String>(Arrays.asList(name));
        
        COLOR_LIST = new HashMap<String, Integer>();
        for (int i=0; i<name.length; i++) {
            COLOR_LIST.put(name[i], id[i]);
        }
    }
    
    public static void release() {
        if (COLOR_NAME_LIST != null) {
            COLOR_NAME_LIST.clear();
            COLOR_NAME_LIST = null;
        }
        if (COLOR_LIST != null) {
            COLOR_LIST.clear();
            COLOR_LIST = null;
        }
    }
}
