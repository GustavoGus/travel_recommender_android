package adm.Activities;

import adm.Repository.DataBaseManagement;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class PruebasActivity extends TabActivity {
	private TabHost mTabHost;




	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	
		
		Bundle extras = getIntent().getExtras();
		int idTab;
		if(extras !=null) idTab = extras.getInt("idTab");
		else idTab = 1;

	
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.main);

	
		mTabHost = getTabHost(); 
		setupTab(new TextView(this), getResources().getString(R.string.buscarName),
				new Intent().setClass(this, SearchTabActivity.class),
				R.drawable.tab_buscar_selector);
		setupTab(new TextView(this), getResources().getString(R.string.disfrutarName),
				new Intent().setClass(this, DisfrutarTabGroup.class),
				R.drawable.tab_disfrutar_selector);
		setupTab(new TextView(this), getResources().getString(R.string.recordarName),
				new Intent().setClass(this, RememberTabGroup.class),
				R.drawable.tab_recordar_selector);
		mTabHost.setCurrentTab(idTab);

		mTabHost.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(
						mTabHost.getApplicationWindowToken(), 0);
			}
		});
		InputMethodManager inputManager = (InputMethodManager) this
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		inputManager.hideSoftInputFromWindow(this.getCurrentFocus()
				.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		creoBDparaMisCosas();
	}
	
	public void creoBDparaMisCosas() {
		DataBaseManagement database = new DataBaseManagement(this);
		
		database.CreateDB();
		database.createUser("juanito","32");
		// Valencia
		database.createSearch("juanito", "CITY", "78688");
		database.createSearch("juanito", "RESTAURANT", "154095");
	}

	private void setupTab(final View view, final String tag,
			final Intent intent, int drawableId) {
		View tabview = createTabView(mTabHost.getContext(), tag, drawableId);
	
		TabSpec setContent = mTabHost.newTabSpec(tag).setIndicator(tabview)
				.setContent(intent);
		mTabHost.addTab(setContent);
	}

	private static View createTabView(final Context context, final String text,
			final int drawableId) {
		View view = LayoutInflater.from(context)
				.inflate(R.layout.tabs_bg, null);
		TextView tv = (TextView) view.findViewById(R.id.tabsText);
		tv.setText(text);

		ImageView icon = (ImageView) view.findViewById(R.id.tabIcon);
		icon.setImageResource(drawableId);
		return view;
	}
	
	public void onActionBarHomeButtonClick(View v) 
	{
		
		startActivity(new Intent(PruebasActivity.this, DashboardActivity.class));
	}
}