package com.brighthead.whattodo;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.brighthead.whattodo.R;
import com.brighthead.whattodo.preferences.SharedPreference;
import com.brighthead.whattodo.service.WhatToDoService;

public class MainActivity extends Activity implements View.OnClickListener,
		ILayoutListener {
	private static final String TAG = "MainActivity";
	private TextView mWorkText = null;
	private Button mStartBtn = null;
	private Button mStopBtn = null;
	private Button mEditWorkBtn = null;
	private Button mBgColorBtn = null;

	// layout
	private RelativeLayout mMainView = null;
	private ILayout mCurrentView = null;
	private EditWorkFrameLayout mEditWorkView = null;
	private BgColorFrameLayout mBgColorView = null;

	private static final int ALPHA = 0;
	private static final int FADEOUT = 1;
	private static final int FADEIN = 2;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ResourceUtil.initialize(getApplicationContext());
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	protected void onResume() {

		initializeLayout();
		initializeListener();
		super.onResume();

	}

	private void initializeLayout() {
		if (mStartBtn == null) {
			mStartBtn = (Button) findViewById(R.id.start_btn);
		}

		if (mStopBtn == null) {
			mStopBtn = (Button) findViewById(R.id.stop_btn);
		}

		if (mEditWorkBtn == null) {
			mEditWorkBtn = (Button) findViewById(R.id.work_edit);
		}

		if (mBgColorBtn == null) {
			mBgColorBtn = (Button) findViewById(R.id.color_edit);
		}

		if (mWorkText == null) {
			mWorkText = (TextView) findViewById(R.id.main_text);
			String main = SharedPreference.getSharedPreference(
					getApplicationContext(), getString(R.string.pref_key_work));
			if (main != null) {
				mWorkText.setText(main);
			}
			String bgColor = SharedPreference.getSharedPreference(
					getApplicationContext(),
					getString(R.string.pref_key_color_bg));
			if (ResourceUtil.COLOR_LIST != null) {
				if (ResourceUtil.COLOR_LIST.get(bgColor) != null) {
					mWorkText.setBackgroundColor(ResourceUtil.COLOR_LIST
							.get(bgColor));
				}
			}
		}

		if (mMainView == null) {
			mMainView = (RelativeLayout) findViewById(R.id.main_view);
		}

		if (mEditWorkView == null) {
			mEditWorkView = (EditWorkFrameLayout) findViewById(R.id.edit_work_layout);
			mEditWorkView.init(getApplicationContext());
		} else {
			mEditWorkView.init(getApplicationContext());
		}

		if (mBgColorView == null) {
			mBgColorView = (BgColorFrameLayout) findViewById(R.id.bg_color_layout);
			mBgColorView.init(getApplicationContext());
		} else {
			mBgColorView.init(getApplicationContext());
		}

	}

	private void initializeListener() {
		if (mStartBtn != null) {
			mStartBtn.setOnClickListener(this);
		}
		if (mStopBtn != null) {
			mStopBtn.setOnClickListener(this);
		}
		if (mEditWorkBtn != null) {
			mEditWorkBtn.setOnClickListener(this);
		}
		if (mBgColorBtn != null) {
			mBgColorBtn.setOnClickListener(this);
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		releaseLayout();

	}

	@Override
	protected void onDestroy() {
		ResourceUtil.release();
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		Log.d(TAG, "hwankim onClick");
		if (mStartBtn == null || mStopBtn == null || mEditWorkBtn == null
				|| mBgColorBtn == null || mEditWorkView == null
				|| mBgColorView == null) {
			return;
		}
		Log.d(TAG, "hwankim onClick work");
		if (v.getId() == mStartBtn.getId()) {
			startService(new Intent(this, WhatToDoService.class));
		} else if (v.getId() == mStopBtn.getId()) {
			stopService(new Intent(this, WhatToDoService.class));
		} else if (v.getId() == mEditWorkBtn.getId()) {
			mCurrentView = (ILayout) mEditWorkView;
			changeCurrentView(mCurrentView);
		} else if (v.getId() == mBgColorBtn.getId()) {
			mCurrentView = (ILayout) mBgColorView;
			changeCurrentView(mCurrentView);
		}
	}

	private void changeCurrentView(ILayout view) {
		if (view instanceof EditWorkFrameLayout) {
			mBgColorView.setListener(null);
			mBgColorView.stop();
		} else if (view instanceof BgColorFrameLayout) {
			mEditWorkView.setListener(null);
			mEditWorkView.stop();
		}
		setAnimationToView(mMainView, FADEOUT);
		mMainView.setVisibility(View.GONE);
		view.setListener(this);
		view.start();
		setAnimationToView((View) view, FADEIN);
	}

	private void setAnimationToView(View view, int animID) {

		switch (animID) {
		case FADEIN: {
			ObjectAnimator obj1 = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f);
			obj1.start();
			break;
		}
		case FADEOUT: {
			ObjectAnimator obj1 = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f);
			obj1.start();
			break;
		}
		}
	}

	private void updatePreference(String key, String s) {
		if (key == null || s == null)
			return;
		SharedPreference.putSharedPreference(getApplicationContext(), key, s);
	}

	private void releaseLayout() {
		if (mStartBtn != null) {
			mStartBtn.setOnClickListener(null);
			mStartBtn = null;
		}

		if (mStopBtn != null) {
			mStopBtn.setOnClickListener(null);
			mStopBtn = null;
		}

		if (mEditWorkBtn != null) {
			mEditWorkBtn.setOnClickListener(null);
			mEditWorkBtn = null;
		}

		if (mBgColorBtn != null) {
			mBgColorBtn.setOnClickListener(null);
			mBgColorBtn = null;
		}

		if (mWorkText != null) {
			mWorkText = null;
		}

		if (mEditWorkView != null) {
			mEditWorkView.stop();
			mEditWorkView.setListener(null);
			mEditWorkView.release();
			mEditWorkView = null;
		}

		if (mBgColorView != null) {
			mBgColorView.stop();
			mBgColorView.setListener(null);
			mBgColorView.release();
			mBgColorView = null;
		}

		if (mMainView != null) {
			mMainView = null;
		}
	}

	private void setViewPreference() {
		if (mWorkText == null)
			return;
		String text = SharedPreference.getSharedPreference(
				getApplicationContext(), getString(R.string.pref_key_work));
		String bgColor = SharedPreference.getSharedPreference(
				getApplicationContext(), getString(R.string.pref_key_color_bg));
		if (text == null) {
			text = "EditWork";
		}
		mWorkText.setText(text);
		Log.d(TAG, "setViewPreference text " + text + " bgColor " + bgColor);
		if (ResourceUtil.COLOR_LIST != null && bgColor != null) {
			if (ResourceUtil.COLOR_LIST.get(bgColor) != null) {
				mWorkText.setBackgroundColor(ResourceUtil.COLOR_LIST
						.get(bgColor));
			}
		}
	}

	@Override
	public void commit(String key, String value) {
		updatePreference(key, value);
		setViewPreference();
	}

	@Override
	public void end() {
		mCurrentView.stop();
		mCurrentView.setListener(null);
		setAnimationToView((View)mCurrentView, FADEOUT);
		mMainView.setVisibility(View.VISIBLE);
		setAnimationToView(mMainView, FADEIN);
	}

	@Override
	public void onBackPressed() {
		if ( mMainView == null)
			return;
		if (mCurrentView != null && ((View) mCurrentView).getVisibility() == View.VISIBLE) {
			if (mCurrentView instanceof EditWorkFrameLayout
					|| mCurrentView instanceof BgColorFrameLayout) {
				end();
				return;
			}
		} else {
			finish();
		}
		super.onBackPressed();
	}

}
