package com.gbframe.component;

import java.lang.ref.WeakReference;
import java.util.List;

import com.gbframe.framework.internal.ActivityThread;
import com.gbframe.framework.internal.BroadCastReceiverManager;
import com.gbframe.framework.internal.ContextImpl;

import android.app.Application;
import android.content.BroadcastReceiver;

public class BaseApplication extends Application {

	private static WeakReference<BaseApplication> mApplication;

	public BaseApplication() {
		mApplication = new WeakReference<BaseApplication>(this);
	}

	public BaseApplication getInstance() {
		return mApplication.get();
	}

	public ActivityThread currentMainThread() {
		return ActivityThread.getInstance();
	}

	public ContextImpl getBaseOuterContext() {
		return ContextImpl.getInstance(this);
	}

	public List<BroadcastReceiver> getAllRegisterBroadcast() {
		return BroadCastReceiverManager.getCurrentRegisterBroadcastReceiver(this);
	}

	/*
	 * it didn't work
	 */
	@Deprecated
	public List<BroadcastReceiver> getAllUnregisterBroadcast() {
		return BroadCastReceiverManager.getUnregisterBroadcastReceiver(this);
	}

}
