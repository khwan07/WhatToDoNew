package com.brighthead.whattodo.service;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.TextView;

import com.brighthead.whattodo.R;
import com.brighthead.whattodo.ResourceUtil;
import com.brighthead.whattodo.preferences.SharedPreference;

public class WhatToDoService extends Service implements OnTouchListener,
		OnClickListener {
	private static final String TAG = "WhatToDoService";
	private View rootView;
	// private Button mBtn;
	private TextView mTextView;

	private boolean isTouchedDown;
	private boolean isBtnMoved;

	private int STARTX;
	private int STARTY;
	private int VIEWX;
	private int VIEWY;

	WindowManager.LayoutParams mParams = null;
	WindowManager mWindowManager = null;

	@Override
	public IBinder onBind(Intent arg0) {
		Log.d(TAG, "hwankim onBind");
		return null;
	}

	@Override
	public void onCreate() {
		Log.d(TAG, "hwankim onCreate");
		super.onCreate();
		initializeReceiver();
		showAnimation();
	}
	
	private void initializeReceiver() {
		IntentFilter filter = new IntentFilter(Intent.ACTION_USER_PRESENT);
		registerReceiver(mReceiver, filter);
	}
	
	private void showAnimation() {
		initView();
		startAnim();
	}
	
	private void initView() {
		LayoutInflater li = (LayoutInflater) getApplicationContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		rootView = li.inflate(R.layout.service_main, null);
		mTextView = (TextView) rootView.findViewById(R.id.service_text);
		mTextView.setText(SharedPreference.getSharedPreference(
				getApplicationContext(),
				getApplicationContext().getString(R.string.pref_key_work)));
		String bgColor = SharedPreference.getSharedPreference(
				getApplicationContext(),
				getApplicationContext().getString(R.string.pref_key_color_bg));
		if (bgColor != null) {
			if (ResourceUtil.COLOR_LIST != null) {
				
			} else {
				ResourceUtil.initialize(getApplicationContext());
			}
			mTextView.setBackgroundColor(ResourceUtil.COLOR_LIST.get(bgColor));
		}
		mParams = new WindowManager.LayoutParams(
				// WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY, //��긽
				// 理��곸쐞���덇쾶
				// WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, //�곗튂
				// �몄떇
				WindowManager.LayoutParams.MATCH_PARENT,
				WindowManager.LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
				PixelFormat.TRANSLUCENT);
		mParams.gravity = Gravity.TOP;
		mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE); // �덈룄
																			// 留ㅻ땲��
		mWindowManager.addView(rootView, mParams); // 理쒖긽���덈룄�곗뿉 酉��ｊ린.
													// permission�꾩슂.
	}
	
	private void startAnim() {
		Log.d(TAG, "hwankim startAnim");
		ObjectAnimator translateX = ObjectAnimator.ofFloat(mTextView, "translationX", 1000f, -1000f);
		translateX.setDuration(5000);
		ObjectAnimator alpha = ObjectAnimator.ofFloat(mTextView, "alpha", 0f, 1f);
		alpha.setDuration(1000);
		AnimatorSet anim = new AnimatorSet();
		
		anim.addListener(new AnimatorListener() {
			
			@Override
			public void onAnimationStart(Animator animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animator animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animator animation) {
				Log.d(TAG, "hwankim onAnimationEnd");
				destroyView();
			}
			
			@Override
			public void onAnimationCancel(Animator animation) {
				// TODO Auto-generated method stub
				
			}
		});
		
		anim.playTogether(translateX, alpha);
		anim.start();
	}
	
	

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(TAG, "hwankim onStartCommand");
		return super.onStartCommand(intent, flags, startId);
	}
	
	private void destroyView() {
		if (mTextView != null) {
			mTextView = null;
		}

		if (rootView != null) {
			rootView.setVisibility(View.GONE);
			((WindowManager) getSystemService(WINDOW_SERVICE))
					.removeView(rootView);
			rootView = null;
		}
		if (mWindowManager != null) {
			mWindowManager = null;
		}
	}

	@Override
	public boolean onUnbind(Intent intent) {
		Log.d(TAG, "hwankim onUnbind");
		destroyView();

		return super.onUnbind(intent);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.d(TAG, "hwankim onDestroy");
		/*
		 * for button work if (mBtn != null) { ((WindowManager)
		 * getSystemService(WINDOW_SERVICE)).removeView(mBtn);
		 * mBtn.setOnTouchListener(null); mBtn.setOnClickListener(null); mBtn =
		 * null; }
		 */
		destroyView();
		unregisterReceiver(mReceiver);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		Log.d("hwankim", "ontouch");
		/*
		 * for button work int id = v.getId(); int action = event.getAction();
		 * if (id == mBtn.getId()) { if (action == MotionEvent.ACTION_DOWN) {
		 * isTouchedDown = true; STARTX = (int) event.getRawX(); STARTY = (int)
		 * event.getRawY(); VIEWX = mParams.x; VIEWY = mParams.y; } if
		 * (isTouchedDown && action == MotionEvent.ACTION_MOVE) { int rawx =
		 * (int) event.getRawX(); int rawy = (int) event.getRawY(); moveBtn(rawx
		 * - STARTX, rawy - STARTY); } } if (action == MotionEvent.ACTION_UP) {
		 * isTouchedDown = false; }
		 */
		return false;
	}

	private void moveBtn(int movex, int movey) {
		Log.d("hwankim", "moveBtn movex " + movex + " movey " + movey);
		mParams.x = VIEWX + movex;
		mParams.y = VIEWY + movey;

		mWindowManager.updateViewLayout(rootView, mParams);
		isBtnMoved = true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}
	
	private final BroadcastReceiver mReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(Intent.ACTION_USER_PRESENT)) {
				showAnimation();
			}
		}
		
	};

}
