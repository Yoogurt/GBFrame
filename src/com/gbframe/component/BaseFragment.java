package com.gbframe.component;

import java.lang.reflect.Field;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public abstract class BaseFragment extends Fragment {

	@Override
	public final View onCreateView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {
		
		View v = initView(inflater, container, savedInstanceState);

		try {
			setUpInterface(v);
		} catch (IllegalAccessException | IllegalArgumentException e) {
			RuntimeException ex = new RuntimeException(
					"unexpected exception occured", e);
			throw ex;
		}

		return v;
	}

	public abstract View initView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState);

	private void setUpInterface(View view) throws IllegalAccessException,
			IllegalArgumentException {

		Field[] field = getClass().getDeclaredFields();

		Bind declared_annotation = null;

		for (Field tmp : field) {
			if ((declared_annotation = tmp.getAnnotation(Bind.class)) != null) {
				View v = view.findViewById(declared_annotation.id());
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
			Toast toast = new Toast(getActivity());
			toast.setView(v);
			toast.setText(msg);
			toast.setDuration(length);
			toast.show();
		} else
			Toast.makeText(getActivity(), msg, length);
	}

	public final void showToast(int res, int length, View v) {

		if (v != null) {
			Toast toast = new Toast(getActivity());
			toast.setView(v);
			toast.setText(res);
			toast.setDuration(length);
			toast.show();
		} else
			Toast.makeText(getActivity(), res, length);
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
