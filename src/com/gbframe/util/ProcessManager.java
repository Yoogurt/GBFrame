package com.gbframe.util;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.Charset;

public class ProcessManager implements Closeable {

	interface onProcessTerminal {
		void onTerminal();
	}

	private class mListener implements onProcessTerminal {

		@Override
		public void onTerminal() {
			close();
			if (mListener != null)
				mListener.onTerminal();
		}

	}

	private class PipeReader extends Thread {

		@Override
		public void run() {
			int mReadCode = 0;
			while (true)
				try {
					mReadCode = br.read();
					if (mReadCode == -1)
						break;
					mOutput.write(mReadCode);
				} catch (Throwable e) {
					break;
				}
			ProcessManager.this.close();
		}
	}

	private Process mProcess;
	private byte[] lock;
	private PrintWriter pw;
	private BufferedReader br;
	private OutputStream mOutput;
	private PipeReader mOutputer;
	protected onProcessTerminal mListener;
	private boolean isDestroy;
	private byte[] mutexLock;

	public ProcessManager(OutputStream out, onProcessTerminal listener) {

		ProcessBuilder pb = new ProcessBuilder();
		pb.redirectErrorStream(true);
		pb.command("cmd");
		try {
			mProcess = pb.start();
		} catch (IOException e) {
			throw new RuntimeException("Unable to Create Process", e);
		}
		mListener = listener;
		isDestroy = false;
		lock = new byte[0];
		mutexLock = new byte[0];
		pw = new PrintWriter(mProcess.getOutputStream());
		br = new BufferedReader(new InputStreamReader(
				mProcess.getInputStream(), Charset.defaultCharset()));
		mOutput = out;
		mOutputer = new PipeReader();
		mOutputer.start();
		new ProcessWatchDog(this, new mListener()).start();
	}

	public void execute(String cmd) {
		pw.println(cmd);
		pw.flush();
	}

	public void close() {

		synchronized (mutexLock) {
			if (isDestroy)
				return;
			isDestroy = true;
		}

		synchronized (lock) {

			if (pw != null) {
				pw.close();
				pw = null;
			}

			if (br != null) {
				try {
					br.close();
				} catch (Exception ignore) {
				}
				br = null;
			}

			if (mProcess != null) {
				mProcess.destroy();
				mProcess = null;
			}

			if (Thread.currentThread() != mOutputer)
				try {
					mOutputer.join();
				} catch (InterruptedException ignore) {
				}
		}
	}

	@Override
	protected void finalize() throws Throwable {
		close();
	}

	public Process getContext() {
		synchronized (lock) {
			if (mProcess != null)
				return mProcess;
			else
				return null;
		}
	}
}
