package com.gbframe.component;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.content.BroadcastReceiver;

public class BaseApplication extends Application {

	private static WeakReference<BaseApplication> mApplication;

	public BaseApplication() {
		mApplication = new WeakReference<BaseApplication>(this);
	}
	
	public BaseApplication getInstance(){
		return mApplication.get();
	}

	private List<BroadcastReceiver> mBroadCastReceivers = new ArrayList<>();


}
