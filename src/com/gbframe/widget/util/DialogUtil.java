package com.gbframe.widget.util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

public class DialogUtil {

	public Dialog showDoubleClickDialog(Context ctx, String title, String message, boolean cancelable,
			String positiveText, DialogInterface.OnClickListener positiveListener, String negativeText,
			DialogInterface.OnClickListener negativeListener) {

		AlertDialog.Builder builder = new AlertDialog.Builder(ctx);

		if (title != null)
			builder.setTitle(title);

		if (message != null)
			builder.setMessage(message);

		builder.setCancelable(cancelable);

		if (positiveText != null && null != positiveListener)
			builder.setPositiveButton(positiveText, positiveListener);

		if (negativeText != null && null != negativeListener)
			builder.setNegativeButton(negativeText, negativeListener);

		return builder.create();
	}

}
