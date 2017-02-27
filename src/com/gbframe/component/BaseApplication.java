package com.gbframe.component;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.content.BroadcastReceiver;

public class BaseApplication extends Application {

	private boolean hasCallSuperOnCreate = false;
	private List<BroadcastReceiver> mBroadCastReceivers = new ArrayList<>();
	
	@Override
	public void onCreate() {
		super.onCreate();
	}
	
	
	


}
