package com.gbframe.framework.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.gbframe.util.ReflectUtil;

import android.content.ContextWrapper;

public class ContextImpl {

	public static final String CONTEXTIMPL_CLASS = "android.app.ContextImpl";

	private Object mBase;

	private static ContextImpl mInstance;

	private ContextImpl(Object mContext) {
		mBase = mContext;
	}

	public static ContextImpl getInstance(ContextWrapper ctx) {

		if (mInstance == null)
			synchronized (ContextImpl.class) {
				if (mInstance == null) {
					mInstance = new ContextImpl(
							ReflectUtil.getField(
									ReflectUtil.getClassField(
											ReflectUtil.getSuperClassByName(ctx.getClass(), "ContextWrapper"), "mBase"),
									ctx));
				}
			}
		return mInstance;
	}

	public Object getContext() {
		return mBase;
	}

	public Object[] getAllSystemService() {

		return (Object[]) ReflectUtil.getInstanceFieldObject(mBase, "mServiceCache");

	}

}
