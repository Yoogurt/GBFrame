package com.gbframe.util;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;

/**
 * 
 * 
 * @author Bob。
 *
 */
public final class AsyncUtil {

	private static final Executor executor = Executors
			.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

	private static final int FINISHED = -1;

	private static final Handler handler = new Handler(Looper.getMainLooper()) {

		public void handleMessage(android.os.Message msg) {

			if (msg.what == FINISHED)
				if (msg.obj != null)
					((CallBack) msg.obj).onFinished();

		};

	};

	public static abstract class CallBack {

		/**
		 * run in main Thread
		 */
		public abstract void onFinished();

		/**
		 * run in main Thread
		 */
		public abstract void onPrepare();

		/**
		 * run in new Thread
		 */
		public abstract void onDoInBackground();

		/**
		 * call this method to invoke onFinished method , you'd better not to
		 * call onFinished() directly in your code considering the security of
		 * {@link Thread}
		 */
		public final void callOnFinished() {

			Message msg = Message.obtain(handler);

			msg.what = FINISHED;

			msg.obj = this;

			msg.sendToTarget();

		}

	}

	public final static void execute(@NonNull final CallBack callBack) {

		if (callBack == null)
			throw new NullPointerException("AsyncUtil.callBack == null");

		class mRunnable implements Runnable {

			public void run() {

				callBack.onDoInBackground();

				Message msg = Message.obtain(handler);

				msg.what = FINISHED;

				msg.obj = callBack;

				msg.sendToTarget();

			}

		}

		callBack.onPrepare();

		executor.execute(new mRunnable());

	}

}
