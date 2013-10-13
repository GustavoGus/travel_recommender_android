/**
 * 
 */
package adm.Activities;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TabHost.TabSpec;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
/**
 * @author Androit
 * 
 */
public class SearchTabActivity extends TabActivity {
	private TabHost mTabHost;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.buscar);


		getResources(); 
		mTabHost = getTabHost();
		setupTab(new TextView(this), getResources().getString(R.string.advanced_search),
				new Intent().setClass(this, SearchAdvancedTabGroup.class),
				R.drawable.tab_buscar_avanzada_selector);
		setupTab(new TextView(this),  getResources().getString(R.string.suggestions),
				new Intent().setClass(this, SearchSuggestionActivity.class),
				R.drawable.tab_buscar_sugerencia_selector);
		setupTab(
				new TextView(this),
				 getResources().getString(R.string.feelinglucky),
				new Intent().setClass(this, SearchImFeelingLuckyActivity.class),
				R.drawable.tab_buscar_suerte_selector);
		mTabHost.setCurrentTab(0);
		mTabHost.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(
						mTabHost.getApplicationWindowToken(), 0);
			}
		});
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
		View view = LayoutInflater.from(context).inflate(
				R.layout.tabs_bg_buscar, null);
		TextView tv = (TextView) view.findViewById(R.id.tabsText);
		tv.setText(text);

		ImageView icon = (ImageView) view.findViewById(R.id.tabIcon);
		icon.setImageResource(drawableId);
		return view;
	}
}
