package com.gbframe.framework.internal;

import java.lang.reflect.Field;

import com.gbframe.util.ReflectUtil;

import android.app.Instrumentation;
import android.content.ContextWrapper;

public class ActivityThread {

	public static final String ACTIVITY_THREAD_CLASS = "android.app.ActivityThread";
	private static final String CURRENT_ACTIVITY_THREAD = "sCurrentActivityThread";

	private final Object mMainThread;

	private static ActivityThread sCurrentActivityThread;

	private ActivityThread(Object mMainThread) {

		if (!mMainThread.getClass().getName().equals(ACTIVITY_THREAD_CLASS))
			throw new IllegalArgumentException("ActivityThread require an instance of android.app.ActivityThread");

		this.mMainThread = mMainThread;
	}

	public static ActivityThread getContextThread() {

		if (sCurrentActivityThread != null)
			return sCurrentActivityThread;

		synchronized (ActivityThread.class) {

			if (sCurrentActivityThread != null)
				return sCurrentActivityThread;

			Field mActivityThreadField = ReflectUtil.getInstanceField(ReflectUtil.findClass(ACTIVITY_THREAD_CLASS),
					CURRENT_ACTIVITY_THREAD);

			sCurrentActivityThread = new ActivityThread(ReflectUtil.getField(mActivityThreadField, null));

		}

		return sCurrentActivityThread;
	}
	
	public Object getContext(){
		return mMainThread;
	} 

	public boolean hookInstrumentation(Instrumentation instrumentation) {
		return ReflectUtil.setField(mMainThread, "mInstrumentation", instrumentation);
	}

}
