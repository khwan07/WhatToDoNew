package com.brighthead.whattodo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.brighthead.whattodo.R;


public class BgColorFrameLayout extends FrameLayout implements ILayout, OnItemClickListener, OnClickListener {

	private static final String TAG = "BgColorFrameLayout";
	
    private ILayoutListener mListener = null;
    private Context mContext = null;
    
    private RelativeLayout mBgFrame = null;
    private TextView mText = null;
    private TextView mTextChoice = null;
    private Button mChoiceBtn = null;
    private Button mCommitBtn = null;
    private ListView mBgList = null;
    
    private RelativeLayout mTextFrame = null;
    private TextView mTextColorChoice = null;
    private Button mTextColorChoiceBtn = null;
    private ListView mTextColorBgList = null;
    
    //list adpater
    private ArrayList<String> mBgArrayList;
    private HashMap<String, Integer> mColorMap;
    private ArrayAdapter<String> mBgAdapter;
    
    private int mBgArrayNum = -1;
    private int mTextBgArrayNum = -1;
    
    
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
    	if (mBgFrame == null) {
    	    mBgFrame = (RelativeLayout) findViewById(R.id.bg_color_frame);
    	    mBgFrame.setOnClickListener(this);
    	}
        if (mText == null) {
            mText = (TextView) findViewById(R.id.bg_color_text);
        }
        if (mTextChoice == null) {
            mTextChoice = (TextView) findViewById(R.id.bg_color_choice);
        }
        if (mBgList == null) {
            mBgList = (ListView) findViewById(R.id.bg_color_edit);
        }
        if (mTextFrame == null) {
        	mTextFrame = (RelativeLayout) findViewById(R.id.text_color_frame);
        	mTextFrame.setOnClickListener(this);
        }
        if (mTextColorChoice == null) {
        	mTextColorChoice = (TextView) findViewById(R.id.text_color_choice);
        }
        if (mTextColorChoiceBtn == null) {
        	mTextColorChoiceBtn = (Button) findViewById(R.id.text_color_choice_button);
        	mTextColorChoiceBtn.setOnClickListener(this);
        }
        if (mTextColorBgList == null) {
        	mTextColorBgList = (ListView) findViewById(R.id.text_color_edit);
        }
        if (mCommitBtn == null) {
            mCommitBtn = (Button) findViewById(R.id.bg_color_button);
            mCommitBtn.setOnClickListener(this);
        }
        if (mChoiceBtn == null) {
            mChoiceBtn = (Button) findViewById(R.id.bg_color_choice_button);
            mChoiceBtn.setOnClickListener(this);
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
        
        mTextColorBgList.setAdapter(mBgAdapter);
        mTextColorBgList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        mTextColorBgList.setOnItemClickListener(this);
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
        if (mTextChoice != null) {
            mTextChoice = null;
        }
        if (mBgList != null) {
            mBgList.setAdapter(null);
            mBgList.setOnItemClickListener(null);
            mBgList = null;
        }
        if (mBgFrame != null) {
            mBgFrame.setOnClickListener(null);
            mBgFrame = null;
        }
        if (mChoiceBtn != null) {
            mChoiceBtn.setOnClickListener(null);
            mChoiceBtn = null;
        }
        if (mTextFrame != null) {
        	mTextFrame.setOnClickListener(null);
        	mTextFrame = null;
        }
        if (mTextColorChoice != null) {
        	mTextColorChoice = null;
        }
        if (mTextColorChoiceBtn != null) {
        	mTextColorChoiceBtn.setOnClickListener(null);
        	mTextColorChoiceBtn = null;
        }
        if (mCommitBtn != null) {
            mCommitBtn.setOnClickListener(null);
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
        } else if (arg0.getId() == mTextColorBgList.getId()) {
        	mTextBgArrayNum = arg2;
        	String color = mBgAdapter.getItem(arg2);
        	mText.setTextColor(mColorMap.get(color));
        }
    }

    @Override
    public void onClick(View v) {
        if (mCommitBtn == null || mBgFrame == null || mChoiceBtn == null) return;
        if (v.getId() == mCommitBtn.getId()) {
        	if (mBgArrayNum != -1) {
        		String color = mBgAdapter.getItem(mBgArrayNum);
        		mListener.commit(mContext.getString(R.string.pref_key_color_bg), color);
        	}
            if (mTextBgArrayNum != -1) {
            	String colorText = mBgAdapter.getItem(mTextBgArrayNum);
            	mListener.commit(mContext.getString(R.string.pref_key_color_text), colorText);
            }
            mListener.end();
        } else if (v.getId() == mBgFrame.getId()) {
            flipIt(mBgFrame);
        } else if (v.getId() == mChoiceBtn.getId()) {
            flipIt(mChoiceBtn);
        } else if (v.getId() == mTextFrame.getId()) {
        	flipIt(mTextFrame);
        } else if (v.getId() == mTextColorChoiceBtn.getId()) {
        	flipIt(mTextColorChoiceBtn);
        }
    }
    
    private void flipIt(View view) {
        final View visibleView;
        final View invisibleView;
        final View otherView;
        
        if (view == mBgFrame || view == mChoiceBtn) {
        	if (mTextChoice.getVisibility() == View.GONE) {
        		visibleView = mBgList;
        		invisibleView = mTextChoice;
        		otherView = mChoiceBtn;
        	} else {
        		visibleView = mTextChoice;
        		invisibleView = mBgList;
        		otherView = mChoiceBtn;
        	}
        } else/* if (view == mTextFrame || view == mTextColorChoiceBtn)*/ {
        	if (mTextColorChoice.getVisibility() == View.GONE) {
        		visibleView = mTextColorBgList;
        		invisibleView = mTextColorChoice;
        		otherView = mTextColorChoiceBtn;
        	} else {
        		visibleView = mTextColorChoice;
        		invisibleView = mTextColorBgList;
        		otherView = mTextColorChoiceBtn;
        	}
        }
        
        ObjectAnimator visToInvis = ObjectAnimator.ofFloat(visibleView, "rotationY", 0f, 90f);
        visToInvis.setDuration(500);
//        visToInvis.setInterpolator(accelerator);
        final ObjectAnimator invisToVis = ObjectAnimator.ofFloat(invisibleView, "rotationY",
                -90f, 0f);
        invisToVis.setDuration(500);
//        invisToVis.setInterpolator(decelerator);
        visToInvis.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator anim) {
                visibleView.setVisibility(View.GONE);
                invisToVis.start();
                invisibleView.setVisibility(View.VISIBLE);
                if (invisibleView == mTextChoice || invisibleView == mTextColorChoice) {
                    otherView.setVisibility(View.GONE);
                } else {
                    otherView.setVisibility(View.VISIBLE);
                }
            }
        });
        visToInvis.start();
    }

}
