package com.gbframe.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ContinuityThread extends Thread {

	protected List<Runnable> mQueue;
	protected byte[] mLock = new byte[0];
	protected boolean mStop = false;

	public ContinuityThread(Runnable... runnable) {
		mQueue = new ArrayList<>(runnable.length + 6);
		Collections.addAll(mQueue, runnable);
		setDaemon(true);
		setName(getClass().getSimpleName() + "@" + hashCode());
	}

	public ContinuityThread() {
		mQueue = new ArrayList<>();
		setDaemon(true);
		setName(getClass().getSimpleName() + "@" + hashCode());
	}

	@Override
	public void run() {

		Runnable next = null;

		while (true) {

			synchronized (mLock) { // we're prevent form dead lock carefully

				if (mStop)
					break;

				if (!mQueue.isEmpty())
					next = mQueue.get(0);
				else
					next = null;

				if (null == next)
					try {
						mLock.wait();
						continue;
					} catch (InterruptedException ignore) {
					}
			}

			if (null != next)
				next.run();
		}

		synchronized (mLock) {
			mStop = true;
		}
	}

	public void post(Runnable runnable) {

		if (runnable == null)
			throw new NullPointerException("runnable == null");

		synchronized (mLock) {
			mQueue.add(runnable);
			mLock.notify();
		}
	}

	public void shutdown() {

		synchronized (mLock) {
			if (!mStop) {
				mStop = true;
				mLock.notify();
			}
		}
	}

	public boolean isShutdown() {
		synchronized (mLock) {
			return mStop;
		}
	}

}
