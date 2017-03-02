package com.gbframe.framework.internal;

import java.lang.reflect.Field;

import com.gbframe.util.ReflectUtil;

import android.app.Activity;
import android.app.Application;
import android.app.Instrumentation;
import android.content.ContextWrapper;
import android.os.Bundle;
import android.view.View;

public class ActivityThread {

	private final Object mMainThread;
	
	private static ActivityThread instance;

	private ActivityThread(Object mMainThread) {
		this.mMainThread = mMainThread;
	}

	public static ActivityThread getContextThread(ContextWrapper ctx) {
		
		if (instance != null)
			return instance;
		
		synchronized (ActivityThread.class) {

			if (instance != null)
				return instance;

			Class<?> mContextWrapper = ReflectUtil.getSuperClassByName(ctx.getClass(), "ContextWrapper");
			Field mContextImplField = ReflectUtil.getClassField(mContextWrapper, "mBase");
			Object mContextImpl = ReflectUtil.getField(mContextImplField, ctx);

			instance = new ActivityThread(
					ReflectUtil.getField(ReflectUtil.getInstanceField(mContextImpl, "mMainThread"), mContextImpl));
		}

		return instance;
	}
	
	public boolean hookInstrumentation(Instrumentation instrumentation){
		return ReflectUtil.setField(mMainThread, "mInstrumentation", instrumentation);
	}

	
	
	
}
