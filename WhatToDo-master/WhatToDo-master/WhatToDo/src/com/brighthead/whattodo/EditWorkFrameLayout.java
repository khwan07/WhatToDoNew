package com.brighthead.whattodo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;


public class EditWorkFrameLayout extends FrameLayout implements ILayout, OnClickListener {
    
    private ILayoutListener mListener = null;
    private Context mContext = null;
    
    private TextView mTextView = null;
    private EditText mEditText = null;
    private Button mCommitBtn = null;

    public EditWorkFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }
    
    public EditWorkFrameLayout(Context context) {
        super(context);
        mContext = context;
    }
    
    public void resume(Context con) {
    	mContext = con;
    }

    @Override
    public void init() {
        if (mTextView == null) {
            mTextView = (TextView) findViewById(R.id.edit_work_text);
        }
        if (mEditText == null) {
            mEditText = (EditText) findViewById(R.id.edit_work_edit);
        }
        if (mCommitBtn == null) {
            mCommitBtn = (Button) findViewById(R.id.edit_work_button);
            mCommitBtn.setOnClickListener(this);
        }
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
        if (mTextView != null) {
            mTextView = null;
        }
        if (mEditText != null) {
            mEditText = null;
        }
        if (mCommitBtn != null) {
            mCommitBtn.setOnClickListener(null);
            mCommitBtn = null;
        }
        mContext = null;
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        if (mCommitBtn == null) return;
        
        if (v.getId() == mCommitBtn.getId()) {
            String s = mEditText.getText().toString();
            mListener.commit(mContext.getString(R.string.pref_key_work), s);
            mListener.end();
        }
        
    }

}
