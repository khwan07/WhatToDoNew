package com.brighthead.whattodo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.brighthead.whattodo.service.WhatToDoService;

public class StaticReceiver extends BroadcastReceiver {
	public static final String TAG = "StaticReceiver";
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
			Log.d(TAG, "hwankim boot completed");
			context.startService(new Intent(context, WhatToDoService.class));
		}
	}
}
