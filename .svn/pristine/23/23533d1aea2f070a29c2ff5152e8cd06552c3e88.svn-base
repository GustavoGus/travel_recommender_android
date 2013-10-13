/**
 * 
 */
package adm.Activities;

import java.util.ArrayList;
import java.util.List;
import adm.Twitter.TwitterJ;
import adm.Twitter.TwitterJ.TwitterDialogListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

/**
 * @author AndroIT
 * 
 */
public class RememberTabActivity extends Activity {
	final static public int CLOSE_DIALOG = 1;
	final static public int DELETE_DIALOG = 2;
	ArrayList<String> tweets = new ArrayList<String>();
	ToggleButton auth;
	TwitterJ twitter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		twitter = new TwitterJ(getParent());
		twitter.setListener(twDialogListener);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.recordar);

		if (!twitter.hasAccessToken()) {
			viewNotConnected(getParent());
		} else {
			viewConnected(getParent(), twitter);
		}

		final ToggleButton auth = (ToggleButton) findViewById(R.id.toggleButtonTwitter);
		auth.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (!twitter.hasAccessToken()) {
					auth.setChecked(false);
					twitter.authorize();
				}

				else {
					auth.setChecked(true);
					AlertDialog.Builder builder = new AlertDialog.Builder(
							getParent());
					builder.setTitle(getResources().getString(R.string.close));
					builder.setMessage(getResources().getString(
							R.string.dtwitter));
					builder.setPositiveButton(
							getResources().getString(R.string.ok),
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									twitter.resetAccessToken();
									viewNotConnected(getParent());
									dialog.cancel();
								}
							});

					builder.setNegativeButton(
							getResources().getString(R.string.cancel),
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.cancel();
								}
							});
					builder.create().show();
				}
			}
		});

		ImageView iv = (ImageView) findViewById(R.id.imageViewRefresh);
		iv.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (twitter.hasAccessToken()) {
					viewConnected(getParent(), twitter);
				}
			}
		});

		Button map = (Button) findViewById(R.id.buttonMap);
		map.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent(RememberTabActivity.this,
						CheckMapActivity.class));

			}
		});
	}

	private LinearLayout showComment(Context context, int i, String author,
			String comment) {
		LinearLayout commentLayout = new LinearLayout(context);
		commentLayout.setOrientation(LinearLayout.VERTICAL);

		TextView tvAuthor = new TextView(context);
		tvAuthor.setTextColor(Color.parseColor("#f96d06"));
		tvAuthor.setTypeface(null, Typeface.BOLD);
		TextView tvComment = new TextView(context);
		tvComment.setTextColor(Color.parseColor("#39302A"));

		if ((i + 1) % 2 == 1)
			commentLayout
					.setBackgroundResource(R.drawable.bocadillo_white_left);
		else
			commentLayout
					.setBackgroundResource(R.drawable.bocadillo_white_right);

		tvAuthor.setText("@" + author);
		tvComment.setText(comment);

		commentLayout.addView(tvAuthor);
		commentLayout.addView(tvComment);

		return commentLayout;

	}

	private void viewNotConnected(Context context) {
		TextView status = (TextView) findViewById(R.id.textViewStatus);
		status.setText(getResources().getString(R.string.offline));

		ToggleButton auth = (ToggleButton) findViewById(R.id.toggleButtonTwitter);
		auth.setChecked(false);

		LinearLayout parentLayout = (LinearLayout) findViewById(R.id.linearLayoutTweets);
		parentLayout.removeAllViews();

		String comment = getResources().getString(R.string.welcome) + "\n\n"
				+ getResources().getString(R.string.logTwitter) + "\n\n"
				+ getResources().getString(R.string.map);

		LinearLayout commentLayout = showComment(getParent(), 0, getResources()
				.getString(R.string.hello), comment);

		parentLayout.addView(commentLayout);
	}

	private void viewConnected(Context context, TwitterJ twitter) {
		TextView status = (TextView) findViewById(R.id.textViewStatus);
		status.setText(getResources().getString(R.string.online));

		ToggleButton auth = (ToggleButton) findViewById(R.id.toggleButtonTwitter);
		auth.setChecked(true);

		LinearLayout parentLayout = (LinearLayout) findViewById(R.id.linearLayoutTweets);
		parentLayout.removeAllViews();
		AsyncTaskTweet task = new AsyncTaskTweet(getParent(), twitter);
		task.execute();
	}

	private final TwitterDialogListener twDialogListener = new TwitterDialogListener() {
		public void onComplete(String value) {
			viewConnected(getParent(), twitter);
		}

		public void onError(String value) {
			// auth.setChecked(false);
		}
	};

	// AsyncTask ------------------------------------------------
	public class AsyncTaskTweet extends
			AsyncTask<Void, List<twitter4j.Status>, Integer> {

		final ProgressDialog dialog;
		final Context context;
		final TwitterJ twitter;
		final int friends;

		public AsyncTaskTweet(Context cont, TwitterJ twitter) {
			this.context = cont;
			this.dialog = ProgressDialog.show(context,
					context.getString(R.string.cargando),
					context.getString(R.string.obtInfoServer), true);
			this.twitter = twitter;
			this.friends = 1;
		}

		@SuppressWarnings("unchecked")
		@Override
		protected Integer doInBackground(Void... params) {
			dialog.setCancelable(false);
			dialog.show();

			List<twitter4j.Status> statuses = new ArrayList<twitter4j.Status>();

			int pages = 3;

			for (int i = 1; i <= pages; i++) {
				try {
					statuses.addAll(twitter.getTimeLine(i));
				} catch (Exception e) {
					statuses = null;
				}
			}

			if (statuses == null)
				return 0;
			else
				publishProgress(statuses);

			return 1;
		}

		protected void onProgressUpdate(List<twitter4j.Status>... result) {
			List<twitter4j.Status> res = result[0];
			LinearLayout parentLayout = (LinearLayout) findViewById(R.id.linearLayoutTweets);

			int i = 0;

			for (twitter4j.Status status : res) {
				if (status.getText().contains("BV_AndroIT")) {
					LinearLayout commentLayout = showComment(getParent(), i,
							status.getUser().getScreenName(), status.getText());
					parentLayout.addView(commentLayout);
					i++;
				}
			}

			if (i == 0) {
				LinearLayout commentLayout = showComment(getParent(), i,
						getParent().getResources().getString(R.string.hello),
						getParent().getResources().getString(R.string.noComm));
				parentLayout.addView(commentLayout);
			}

		}

		protected void onPostExecute(Integer result) {
			dialog.dismiss();
			if (result == 0)
				Toast.makeText(context,
						context.getResources().getString(R.string.failCon),
						Toast.LENGTH_LONG).show();
		}
	}

}
