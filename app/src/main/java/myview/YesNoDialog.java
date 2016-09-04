package myview;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.sean.score.R;

/**
 * Created by Administrator on 2016/7/24.
 */
public class YesNoDialog extends Dialog {

	public YesNoDialog(Context context) {
		super(context);
	}

	public YesNoDialog(Context context, int themeResId) {
		super(context, themeResId);
	}

	public static class Builder {
		private Context context;
		private String title;
		private String message;
		private String positiveButtonText;
		private String negativeButtonText;
		private View contentView;
		private DialogInterface.OnClickListener positiveButtonClickListener;
		private DialogInterface.OnClickListener negativeButtonClickListener;

		public Builder(Context context) {
			this.context = context;
		}

		public Builder setMessage(String message) {
			this.message = message;
			return this;
		}

		/**
		 * Set the Dialog title from resource
		 *
		 * @param title
		 * @return
		 */
		public Builder setTitle(int title) {
			this.title = (String) context.getText(title);
			return this;
		}

		public Builder setTitle(String title) {
			this.title = title;
			return this;
		}

		public Builder setContentView(View v) {
			this.contentView = v;
			return this;
		}

		/**
		 * Set the positive button resource and it's listener
		 *
		 * @param positiveButtonText
		 * @return
		 */
		public Builder setPositiveButton(int positiveButtonText,
										 DialogInterface.OnClickListener listener) {
			this.positiveButtonText = (String) context
					.getText(positiveButtonText);
			this.positiveButtonClickListener = listener;
			return this;
		}


		/**
		 * text
		 * @param positiveButtonText
		 * @param listener
		 * @return
		 */
		public Builder setPositiveButton(String positiveButtonText,
										 DialogInterface.OnClickListener listener) {
			this.positiveButtonText = positiveButtonText;
			this.positiveButtonClickListener = listener;
			return this;
		}

		public Builder setPositiveButton(DialogInterface.OnClickListener listener){
			this.positiveButtonClickListener = listener;
			return this;
		}

		/**
		 * text
		 * @param negativeButtonText
		 * @param listener
		 * @return
		 */
		public Builder setNegativeButton(String negativeButtonText,
										 DialogInterface.OnClickListener listener) {
			this.negativeButtonText = negativeButtonText;
			this.negativeButtonClickListener = listener;
			return this;
		}

		public CustomDialog create() {

			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			final CustomDialog dialog = new CustomDialog(context, R.style.Dialog);

			View layout = inflater.inflate(R.layout.dialog_yesno_layout, null);
			dialog.addContentView(layout, new ViewGroup.LayoutParams(
					ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

			((TextView) layout.findViewById(R.id.message)).setText(message);

			if (positiveButtonClickListener != null) {

				((Button) layout.findViewById(R.id.yes))
						.setOnClickListener(new View.OnClickListener() {
							public void onClick(View v) {
								positiveButtonClickListener.onClick(dialog,
										DialogInterface.BUTTON_POSITIVE);
							}
						});
				((Button) layout.findViewById(R.id.yes)).setText(positiveButtonText);

			}else {
				// if no confirm button just set the visibility to GONE
				layout.findViewById(R.id.positiveButton).setVisibility(
						View.GONE);
			}

			//取消
			if(negativeButtonClickListener != null){
				((Button) layout.findViewById(R.id.no))
						.setOnClickListener(new View.OnClickListener() {
							public void onClick(View v) {
								negativeButtonClickListener.onClick(dialog,
										DialogInterface.BUTTON_NEGATIVE);
							}
						});
				((Button) layout.findViewById(R.id.no)).setText(negativeButtonText);

			}else {
				// if no confirm button just set the visibility to GONE
				layout.findViewById(R.id.no).setVisibility(
						View.GONE);
			}


			// set the content message
			if (message != null) {
				((TextView) layout.findViewById(R.id.message)).setText(message);
			}


			dialog.setContentView(layout);
			return dialog;
		}

	}
}
