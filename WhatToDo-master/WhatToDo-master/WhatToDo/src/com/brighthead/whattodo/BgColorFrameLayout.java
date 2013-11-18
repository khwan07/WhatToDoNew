package com.brighthead.whattodo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.brighthead.whattodo.R;

public class BgColorFrameLayout extends FrameLayout implements ILayout, OnItemClickListener, OnClickListener {

	private static final String TAG = "BgColorFrameLayout";
	
    private ILayoutListener mListener = null;
    private Context mContext = null;
    
    private TextView mText = null;
    private Button mCommitBtn = null;
    private ListView mBgList = null;
    
    //list adpater
    private ArrayList<String> mBgArrayList;
    private HashMap<String, Integer> mColorMap;
    private ArrayAdapter<String> mBgAdapter;
    
    private int mBgArrayNum = -1;
    
    
    public BgColorFrameLayout(Context context) {
        super(context);
        mContext = context;
    }
    
    public BgColorFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }
    
    @Override
    public void init(Context con) {
    	mContext = con;
        if (mText == null) {
            mText = (TextView) findViewById(R.id.bg_color_text);
        }
        if (mBgList == null) {
            mBgList = (ListView) findViewById(R.id.bg_color_edit);
        }
        if (mCommitBtn == null) {
            mCommitBtn = (Button) findViewById(R.id.bg_color_button);
            mCommitBtn.setOnClickListener(this);
        }
        if (mBgArrayList == null) {
            String[] list = mContext.getResources().getStringArray(R.array.color_name_entries);
            mBgArrayList = new ArrayList<String>(Arrays.asList(list));
            for (String s:mBgArrayList) {
            	Log.d(TAG, "hwankim bgArrayList " + s);
            }
        }
        if (mColorMap == null) {
            mColorMap = new HashMap<String, Integer>();
            mColorMap.put(mBgArrayList.get(0), mContext.getResources().getColor(R.color.black));
            mColorMap.put(mBgArrayList.get(1), mContext.getResources().getColor(R.color.blue));
            mColorMap.put(mBgArrayList.get(2), mContext.getResources().getColor(R.color.yellow));
            mColorMap.put(mBgArrayList.get(3), mContext.getResources().getColor(R.color.red));
        }
        if (mBgAdapter == null) {
            mBgAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, mBgArrayList);
        }
        mBgList.setAdapter(mBgAdapter);
        mBgList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        mBgList.setOnItemClickListener(this);
    }

    @Override
    public void start() {
        setVisibility(View.VISIBLE);
    }

    @Override
    public void setListener(ILayoutListener listener) {
        mListener = listener;
    }

    @Override
    public void stop() {
        setVisibility(View.GONE);
    }

    @Override
    public void release() {
        mContext = null;
        mListener = null;
        if (mText != null) {
            mText = null;
        }
        if (mBgList != null) {
            mBgList.setAdapter(null);
            mBgList.setOnItemClickListener(null);
            mBgList = null;
        }
        if (mCommitBtn != null) {
            mCommitBtn = null;
        }
        if (mBgArrayList != null) {
            mBgArrayList.clear();
            mBgArrayList = null;
        }
        
        if (mBgAdapter != null) {
            mBgAdapter.clear();
            mBgAdapter = null;
        }
        if (mColorMap != null) {
            mColorMap.clear();
            mColorMap = null;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        if (mBgAdapter == null) return;
        Log.d(TAG, "hwankim onItemClick " + arg2);
        if (arg0.getId() == mBgList.getId()) {
        	Log.d(TAG, "hwankim onItemClick id equal " + arg2);
            mBgArrayNum = arg2;
            String color = mBgAdapter.getItem(arg2);
            mText.setBackgroundColor(mColorMap.get(color));
            
        }
    }

    @Override
    public void onClick(View v) {
        if (mCommitBtn == null) return;
        if (v.getId() == mCommitBtn.getId()) {
            String color = mBgAdapter.getItem(mBgArrayNum);
            mListener.commit(mContext.getString(R.string.pref_key_color_bg), color);
            mListener.end();
        }
    }

}
