/**
 * 
 */
package adm.Activities;

import java.util.Locale;

import adm.Services.TrackingLocalization;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * @author AndroIT
 * 
 */
public class DashboardActivity extends Activity {
	private Intent intent;
	private static boolean langChanged = false;
	private final String PREFERENCES_DISFRUTAR = "PREFERENCES_DISFRUTAR";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		
		testLanguage();
		
		setContentView(R.layout.dashboard);
		intent = new Intent(this, PruebasActivity.class);
	}

	public void onActionBuscarClick(View v) {
		intent.putExtra("idTab", 0);
		startActivity(intent);
	}

	public void onActionDisfrutarClick(View v) {
		intent.putExtra("idTab", 1);
		startActivity(intent);
	}

	public void onActionRecordarClick(View v) {
		intent.putExtra("idTab", 2);
		startActivity(intent);
	}

	public void onPruebasClick(View v) {
		startActivity(new Intent(this, TakePhoto.class));
	}

	@Override
	public void onBackPressed() {
		SharedPreferences preferences = getSharedPreferences(
				PREFERENCES_DISFRUTAR, 0);
		Editor preferences_editor = preferences.edit();
		
		if (isMyServiceRunning()) {
			Intent intentService = new Intent(DashboardActivity.this,
					TrackingLocalization.class);
			stopService(intentService);
			preferences_editor.putBoolean("service", false);
			preferences_editor.commit();
		}
		android.os.Process.killProcess(android.os.Process.myPid());
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.global_preferences_menu, menu);
		return true;
	}

	// This method is called once the menu is selected
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		// We have only one menu option
		case R.id.preferences:
			// Launch Preference activity
			Intent i = new Intent(DashboardActivity.this, PropertiesActivity.class);
			startActivity(i);
			break;
		case R.id.about:
			// Launch About activity
			Intent im = new Intent(DashboardActivity.this, AboutActivity.class);
			startActivity(im);
			break;

		}
		return true;
	}
	
	private void testLanguage(){
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		SharedPreferences persistentCheck = getSharedPreferences("CheckLangBonVoyage", 0);
		
		String loadedSelected = persistentCheck.getString("lang", "");
		
		
		
		String selection = preferences.getString("lang_pref", "");
		if(!selection.equals("") ){
			
			
			
			if(selection.equals(loadedSelected)){
				langChanged = false;
			}else{
				langChanged = true;
			}
			Editor e = persistentCheck.edit();
			e.putString("lang", selection);
			e.commit();
			
	
			Locale locale = new Locale(selection); 
			Locale.setDefault(locale);
			Configuration config = new Configuration();
			config.locale = locale;
			getBaseContext().getResources().updateConfiguration(config, 
			getBaseContext().getResources().getDisplayMetrics());

			
					
			
		}
		
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		testLanguage();
		
		if(langChanged){
			Intent intent = new Intent(this, DashboardActivity.class);
			startActivity(intent);
			langChanged = false;
			this.finish();
		}
	
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		testLanguage();
		
		if(langChanged){
			Intent intent = new Intent(this, DashboardActivity.class);
			startActivity(intent);
			langChanged = false;
			this.finish();
		}
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		testLanguage();
		
		if(langChanged){
			Intent intent = new Intent(this, DashboardActivity.class);
			startActivity(intent);
			langChanged = false;
			this.finish();
		}
	}
	
	private boolean isMyServiceRunning() {
		ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
		for (RunningServiceInfo service : manager
				.getRunningServices(Integer.MAX_VALUE)) {
			if ("adm.Services.TrackingLocalization".equals(service.service
					.getClassName())) {
				return true;
			}
		}
		return false;
	}
}
