package com.gbframe.component;

import java.lang.ref.WeakReference;
import java.util.List;

import com.gbframe.framework.internal.ActivityThread;
import com.gbframe.framework.internal.BroadCastReceiverManager;
import com.gbframe.framework.internal.ContextImpl;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

public class BaseApplication extends Application {

	private static WeakReference<BaseApplication> mApplication;

	private static final String UNREGISTER_ACTIVITY = "gbframe.unregister_activity";
	private static boolean mUnregisterActivity = false;
	static BaseActivity mLauncherActivity;

	public BaseApplication() {
		mApplication = new WeakReference<BaseApplication>(this);
	}

	public static BaseApplication getInstance() {
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

	public boolean isUnregisterActivity() {
		return mUnregisterActivity;
	}

	@Override
	public void onCreate() {
		super.onCreate();

		try {
			if (getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA).metaData
					.getBoolean(UNREGISTER_ACTIVITY)) {
				mUnregisterActivity = true;
				currentMainThread().hookInstrumentation(new HookInstrumentation());
			}
		} catch (NameNotFoundException e) {
		}
	}

}
