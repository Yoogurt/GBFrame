package com.gbframe.framework.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import com.gbframe.util.ReflectUtil;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.ContextWrapper;
import android.util.ArrayMap;

public class BroadCastReceiverManager {
	
	@SuppressWarnings("unchecked")
	public static List<BroadcastReceiver> getCurrentRegisterBroadcastReceiver(ContextWrapper ctx) {

		List<BroadcastReceiver> mResult = new ArrayList<>();

		Object contextImpl = ContextImpl.getInstance(ctx).getContext();

		Object loadedApk = ReflectUtil.getInstanceFieldObject(contextImpl, "mPackageInfo");

		ArrayMap<Context, ArrayMap<BroadcastReceiver, ?>> mReceivers = (ArrayMap<Context, ArrayMap<BroadcastReceiver, ?>>) ReflectUtil
				.getInstanceFieldObject(loadedApk, "mReceivers");

		ArrayMap<BroadcastReceiver, ?> mRegisterBroadcastReceivers = mReceivers
				.get(ReflectUtil.invokeMethodSafely(contextImpl, "getOuterContext"));

		if (mRegisterBroadcastReceivers == null)
			return mResult;

		for (Entry<BroadcastReceiver, ?> mEntry : mRegisterBroadcastReceivers.entrySet())
			mResult.add(mEntry.getKey());

		return mResult;
	}

	/*
	 * it didn't work
	 */
	@Deprecated
	@SuppressWarnings("unchecked")
	public static List<BroadcastReceiver> getUnregisterBroadcastReceiver(ContextWrapper ctx) {

		List<BroadcastReceiver> mResult = new ArrayList<>();

		Object contextImpl = ContextImpl.getInstance(ctx).getContext();

		Object loadedApk = ReflectUtil.getInstanceFieldObject(contextImpl, "mPackageInfo");

		ArrayMap<Context, ArrayMap<BroadcastReceiver, ?>> mReceivers = (ArrayMap<Context, ArrayMap<BroadcastReceiver, ?>>) ReflectUtil
				.getInstanceFieldObject(loadedApk, "mUnregisteredReceivers");

		ArrayMap<BroadcastReceiver, ?> mRegisterBroadcastReceivers = mReceivers
				.get(ReflectUtil.invokeMethodSafely(contextImpl, "getOuterContext"));

		if (mRegisterBroadcastReceivers == null)
			return mResult;

		for (Entry<BroadcastReceiver, ?> mEntry : mRegisterBroadcastReceivers.entrySet())
			mResult.add(mEntry.getKey());

		return mResult;
	}

}
