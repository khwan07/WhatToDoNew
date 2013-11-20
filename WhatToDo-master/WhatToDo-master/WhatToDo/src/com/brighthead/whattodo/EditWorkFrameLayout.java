package com.brighthead.whattodo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class EditWorkFrameLayout extends FrameLayout implements ILayout, OnClickListener {
    
    private ILayoutListener mListener = null;
    private Context mContext = null;
    
    private TextView mTextView = null;
    private RelativeLayout mEditLayout = null;
    private TextView mEditTextView = null;
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
    
    @Override
    public void init(Context con) {
    	mContext = con;
        if (mTextView == null) {
            mTextView = (TextView) findViewById(R.id.edit_work_text);
        }
        if (mEditLayout == null) {
        	mEditLayout = (RelativeLayout) findViewById(R.id.edit_layout);
        	mEditLayout.setOnClickListener(this);
        }
        if (mEditTextView == null) {
            mEditTextView = (TextView) findViewById(R.id.edit_work_textview);
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
        if (mEditTextView != null) {
        	mEditTextView = null;
        }
        if (mEditLayout != null) {
        	mEditLayout.setOnClickListener(null);
        	mEditLayout = null;
        }
        mContext = null;
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        if (mCommitBtn == null || mEditLayout == null) return;
        
        if (v.getId() == mCommitBtn.getId()) {
            String s = mEditText.getText().toString();
            mListener.commit(mContext.getString(R.string.pref_key_work), s);
            mListener.end();
        } else if (v.getId() == mEditLayout.getId()) {
        	flipIt();
        }
        
    }
    
    private void flipIt() {
        final View visibleView;
        final View invisibleView;
        
        	if (mEditTextView.getVisibility() == View.GONE) {
        		visibleView = mEditText;
        		invisibleView = mEditTextView;
        	} else {
        		visibleView = mEditTextView;
        		invisibleView = mEditText;
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
            }
        });
        visToInvis.start();
    }

}
