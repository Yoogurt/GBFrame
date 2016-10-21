package com.gbframe.adapter;

import com.gbframe.lambda.PredictView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ListAdapter extends BaseAdapter {

	private int length;
	private int id;
	private Object[] object;
	private PredictView predict;
	private LayoutInflater inflater;

	private ListAdapter(Context ctx) {
		inflater = LayoutInflater.from(ctx);
	}

	public static class Builder {

		private ListAdapter adapter;

		public Builder(Context ctx) {
			adapter = new ListAdapter(ctx);
		}

		public Builder count(int length) {
			adapter.length = length;
			return this;
		}

		public Builder id(int id) {
			adapter.id = id;
			return this;
		}

		public Builder object(Object[] obj) {
			adapter.object = obj;
			return this;
		}

		public Builder predict(PredictView predict) {
			adapter.predict = predict;
			return this;
		}

		public ListAdapter create() {
			if (adapter.predict == null)
				throw new NullPointerException(
						"ListAdapter has not method to paint");
			return adapter;
		}

	}

	@Override
	public int getCount() {
		return length;
	}

	@Override
	public Object getItem(int position) {
		return object[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			View v = inflater.inflate(id, parent, false);
			predict.onPredictView(v, position, parent);
			return v;
		}

		predict.onRepaint(position, convertView, parent);
		return convertView;
	}
}
