/**
 * 
 */
package adm.Activities;

import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceActivity;


/**
 * @author AndroIT
 * 
 */
public class PropertiesActivity extends PreferenceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.global_preferences);
        //getListView().setBackgroundColor(Color.WHITE);
        //setTheme(R.style.BonVoyage);
	}
	
    
}
