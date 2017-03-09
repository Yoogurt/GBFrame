package com.gbframe.component;

import com.gbframe.component.BaseApplication;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;

public class HookInstrumentation extends Instrumentation {

	private BaseApplication mApplication;
	public static final String HOOK_MARK = "`?_#hook#_?`";

	@Override
	public Activity newActivity(ClassLoader cl, String className, Intent intent)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {

		String hookActivity;
		if ((hookActivity = intent.getStringExtra(HOOK_MARK)) != null)
			return (Activity) cl.loadClass(hookActivity).newInstance();

		Activity act = super.newActivity(cl, className, intent);
		if (BaseApplication.mLauncherActivity == null)
			BaseApplication.mLauncherActivity = (BaseActivity) act;

		return act;
	}

}
