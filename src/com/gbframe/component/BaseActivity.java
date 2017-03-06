package com.gbframe.component;

import java.lang.reflect.Field;
import java.util.ArrayList;

import android.app.Activity;
import android.app.Instrumentation;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public abstract class BaseActivity extends Activity {

	@Override
	protected final void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(initView());
		try {
			setUpInterface();
		} catch (IllegalAccessException | IllegalArgumentException e) {
			RuntimeException ex = new RuntimeException("Unable to instantiate Activity , cause by unable to inject setUpInterface()", e);
			throw ex;
		}
		setUpView(savedInstanceState);
	}

	public abstract int initView();

	public abstract void setUpView(Bundle saveInstanceState);

	private void setUpInterface() throws IllegalAccessException, IllegalArgumentException {

		Field[] field = getClass().getDeclaredFields();

		Bind declared_annotation = null;

		for (Field tmp : field) {
			if ((declared_annotation = tmp.getAnnotation(Bind.class)) != null) {
				View v = findViewById(declared_annotation.id());
				if (v == null)
					throw new NullPointerException("Illegal Id");
				if (!tmp.isAccessible()) {
					tmp.setAccessible(true);
					tmp.set(this, v);
					tmp.setAccessible(false);
				} else
					tmp.set(this, v);
			}
		}
	}

	public final void showToast(String msg, int length, View v) {
		if (v != null) {
			Toast toast = new Toast(this);
			toast.setView(v);
			toast.setText(msg);
			toast.setDuration(length);
			toast.show();
		} else
			Toast.makeText(this, msg, length);
	}

	public final void showToast(int res, int length, View v) {

		if (v != null) {
			Toast toast = new Toast(this);
			toast.setView(v);
			toast.setText(res);
			toast.setDuration(length);
			toast.show();
		} else
			Toast.makeText(this, res, length).show();
	}

	public final void showToasts(String msg) {
		showToast(msg, Toast.LENGTH_SHORT, null);
	}

	public final void showToasts(int res) {
		showToast(res, Toast.LENGTH_SHORT, null);
	}

	public final void showToastl(String msg) {
		showToast(msg, Toast.LENGTH_LONG, null);
	}

	public final void showToastl(int res) {
		showToast(res, Toast.LENGTH_LONG, null);
	}
}
