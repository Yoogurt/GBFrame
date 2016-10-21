package com.gbframe.lambda;

import android.view.View;
import android.view.ViewGroup;

public interface PredictView {

	void onPredictView(View v, int position, ViewGroup parent);

	void onRepaint(int position, View convertView, ViewGroup parent);

}
