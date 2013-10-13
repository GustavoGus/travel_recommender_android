package adm.Twitter;

import twitter4j.http.AccessToken;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class TwitterJCredentials {
	private SharedPreferences preferences;
	private Editor editor;
	
	private static final String PREFERENCES_AUTH_KEY = "authKey";
	private static final String PREFERENCES_AUTH_SECRET_KEY = "authSecretKey";
	private static final String PREFERENCES_USER_NAME = "userName";
	private static final String PREFERENCES = "TwitterPreferences";
	
	public TwitterJCredentials(Context context) {
		// TODO Auto-generated constructor stub
		preferences 	  = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
		editor 		  = preferences.edit();
	}
	
	public void storeAccessToken(AccessToken accessToken, String userName) {
		editor.putString(PREFERENCES_AUTH_KEY, accessToken.getToken());
		editor.putString(PREFERENCES_AUTH_SECRET_KEY, accessToken.getTokenSecret());
		editor.putString(PREFERENCES_USER_NAME, userName);
		editor.commit();
	}
	
	public void resetAccessToken() {
		editor.putString(PREFERENCES_AUTH_KEY, null);
		editor.putString(PREFERENCES_AUTH_SECRET_KEY, null);
		editor.putString(PREFERENCES_USER_NAME, null);
		editor.commit();
	}
	
	public AccessToken getAccessToken() {
		String token 		= preferences.getString(PREFERENCES_AUTH_KEY, null);
		String tokenSecret 	= preferences.getString(PREFERENCES_AUTH_SECRET_KEY, null);
		
		if (token != null && tokenSecret != null) 
			return new AccessToken(token, tokenSecret);
		else
			return null;
	}
	
	public String getUserName(){
		String userName = preferences.getString(PREFERENCES_USER_NAME, "");
		return userName;
	}
}
