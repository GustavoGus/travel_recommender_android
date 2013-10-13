package adm.Twitter;

import java.util.ArrayList;
import java.util.List;

import oauth.signpost.OAuth;
import oauth.signpost.OAuthProvider;
import oauth.signpost.basic.DefaultOAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import twitter4j.IDs;
import twitter4j.Paging;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.http.AccessToken;
import adm.Activities.R;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

/**
 * Comprobamos si tenemos en shared preferences ya guardado el accessToken. Si
 * no es así, creamos la ventana para dar permisos a la aplicación y recuperamos
 * el token de la url de vuelta. Después llamamos a configureToken. Si ya
 * teníamos accessToken en shared preferences, llamamos directamente a
 * configureToken.
 */

public class TwitterJ {
	private Twitter twitter;
	private TwitterJCredentials session;
	private AccessToken accessToken;
	private String userName;
	private CommonsHttpOAuthConsumer consumer;
	private OAuthProvider provider;

	private ProgressDialog pdialog;
	private Context context;

	private TwitterDialogListener listener;

	private static final String consumerKey = "e9U9c2NF1nJ1vfRg5rrY1g";
	private static final String secretKey = "QxJzrJcKAl1UgqDeZ8SKeyDUpkMnyXcc5oKwU8vuG0";

	private static final String REQUEST_URL = "https://api.twitter.com/oauth/request_token";
	private static final String ACCESS_TOKEN_URL = "https://api.twitter.com/oauth/access_token";
	private static final String AUTH_URL = "https://api.twitter.com/oauth/authorize";
	public static final String CALLBACK_URL = "myapp://twitter";

	public TwitterJ(Context context) {
		super();
		this.context = context;

		pdialog = new ProgressDialog(context);
		twitter = new TwitterFactory().getInstance();

		consumer = new CommonsHttpOAuthConsumer(consumerKey, secretKey);
		provider = new DefaultOAuthProvider(REQUEST_URL, ACCESS_TOKEN_URL,
				AUTH_URL);
		session = new TwitterJCredentials(context);
		accessToken = session.getAccessToken();

		configureToken();
	}

	@SuppressWarnings("deprecation")
	private void configureToken() {
		if (accessToken != null) {
			twitter = new TwitterFactory().getInstance();
			twitter.setOAuthConsumer(consumerKey, secretKey);
			twitter.setOAuthAccessToken(accessToken);
		}
	}

	public void resetAccessToken() {
		if (accessToken != null) {
			session.resetAccessToken();
			accessToken = null;
		}
	}

	public boolean hasAccessToken() {
		if (accessToken != null)
			return true;
		else
			return false;
	}

	public void setListener(TwitterDialogListener listener) {
		this.listener = listener;
	}

	public String getUserName() {
		return session.getUserName();
	}

	public void authorize() {
		pdialog.setMessage(context.getResources().getString(R.string.loading));
		pdialog.show();

		new Thread() {
			@Override
			public void run() {
				String authUrl = "";
				int what = 1;
				try {
					authUrl = provider.retrieveRequestToken(consumer,
							CALLBACK_URL);
					what = 0;
				} catch (Exception e) {
					e.printStackTrace();
				}

				handler.sendMessage(handler.obtainMessage(what, 1, 0, authUrl));
			}
		}.start();
	}

	public void processToken(String callbackUrl) {
		pdialog.setMessage(context.getResources().getString(R.string.loading));
		pdialog.show();

		final String verifier = getVerifier(callbackUrl);

		new Thread() {
			@Override
			public void run() {
				int what = 1;

				try {
					provider.retrieveAccessToken(consumer, verifier);
					accessToken = new AccessToken(consumer.getToken(),
							consumer.getTokenSecret());
					configureToken();
					twitter.verifyCredentials();
					userName = twitter.getScreenName();
					session.storeAccessToken(accessToken, userName);
					what = 0;
				} catch (Exception e) {
					e.printStackTrace();
					Log.v("Twitter", e.toString());
				}

				handler.sendMessage(handler.obtainMessage(what, 2, 0));
			}
		}.start();
	}

	private String getVerifier(String callbackUrl) {
		String verifier = "";
		Uri uri = Uri.parse(callbackUrl);
		if (uri != null && uri.toString().startsWith(CALLBACK_URL)) {
			Log.d("UriToString", uri.toString());
			verifier = uri.getQueryParameter(OAuth.OAUTH_VERIFIER);
			Log.d("Verifier", verifier);
		}
		return verifier;
	}

	private void showLoginDialog(String url) {
		final TwitterDialogListener listener = new TwitterDialogListener() {

			public void onComplete(String value) {
				processToken(value);
			}

			public void onError(String value) {
				Toast.makeText(context,
						context.getResources().getString(R.string.failLoad),
						Toast.LENGTH_LONG).show();

			}
		};

		new TwitterJDialog(context, url, listener).show();
	}

	public interface TwitterDialogListener {
		public void onComplete(String value);

		public void onError(String value);
	}

	public void newTweet(final String tweet) {
		new Thread() {
			@Override
			public void run() {
				int what = 0;
				try {
					twitter.updateStatus(tweet);
				} catch (Exception e) {
					what = 2;
				}
				handler.sendMessage(handler.obtainMessage(what));
			}
		}.start();
	}

	public QueryResult searchTweet(final String squery) {
		try {
			Query query = new Query(squery);
			return twitter.search(query);
		} catch (TwitterException e) {
			e.printStackTrace();
			Toast.makeText(context,
					context.getResources().getString(R.string.failQuery),
					Toast.LENGTH_LONG);
		}
		return null;
	}

	public List<Status> getTimeLine(int i) {
		try {
		    Paging paging = new Paging(i, 100);
			List<Status> timeLine = twitter.getFriendsTimeline(paging);
			return timeLine;
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<User> searchFriends() {
		try {
			List<User> response = new ArrayList<User>();
			IDs ids = twitter.getFriendsIDs();
			int[] vids = ids.getIDs();
			int it = vids.length / 100;
			int rest = vids.length % 100;

			for (int i = 0; i < it; i++) {
				int[] v = new int[100];
				for (int j = 0; j < 100; j++)
					v[j] = vids[j + (i * 100)];
				ResponseList<User> res = twitter.lookupUsers(v);
				response.addAll(res);
			}

			if (rest != 0) {
				int[] v = new int[rest];
				for (int j = 0; j < rest; j++)
					v[j] = vids[j + (it * 100)];
				ResponseList<User> res = twitter.lookupUsers(v);
				response.addAll(res);
			}

			return response;
		} catch (TwitterException e) {
			e.printStackTrace();
			Log.e("ErrorTwitter", e.toString());
		}
		return null;
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			pdialog.dismiss();
			try {
				if (msg.what == 1) {
					if (msg.arg1 == 1)
						Toast.makeText(
								context,
								context.getResources().getString(
										R.string.failRToken), Toast.LENGTH_LONG)
								.show();
					else
						Toast.makeText(
								context,
								context.getResources().getString(
										R.string.failAToken), Toast.LENGTH_LONG)
								.show();

				} else if (msg.what == 2) {
					Toast.makeText(
							context,
							context.getResources()
									.getString(R.string.failTweet),
							Toast.LENGTH_LONG).show();
				} else {
					if (msg.arg1 == 1)
						showLoginDialog((String) msg.obj);
					else {
						listener.onComplete("");
						Toast.makeText(
								context,
								context.getResources().getString(
										R.string.CTwitter), Toast.LENGTH_LONG)
								.show();
					}
				}
			} catch (Exception e) {
				Log.e("TwitterToast", "Error al mostrar el toast");
			}
		}
	};

}
