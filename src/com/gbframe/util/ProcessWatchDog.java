package com.gbframe.util;

import com.gbframe.util.ProcessManager.onProcessTerminal;

public class ProcessWatchDog extends Thread {

	private ProcessManager target;
	private onProcessTerminal mListener;

	ProcessWatchDog(ProcessManager target, onProcessTerminal mListener) {
		this.target = target;
		this.mListener = mListener;
		setDaemon(true);
		setPriority(MIN_PRIORITY);
	}

	@Override
	public void run() {

		while (true)
			try {
				target.getContext().waitFor();
				break;
			} catch (InterruptedException e) {
			}

		if (mListener != null)
			mListener.onTerminal();
		target.close();
	}

}
