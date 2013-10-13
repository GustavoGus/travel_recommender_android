package adm.widget;

import java.util.ArrayList;

import adm.Activities.R;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.widget.RemoteViews;
/*Widget*/
public class BonVoyageWidget extends AppWidgetProvider{

	private static final String WIDGET_PREFERENCES = "WidgetPrefs";
	@SuppressWarnings("unused")
	private static final String WIDGET_SOCIAL_STRING = "WidgetSocialString";
	private static final String WIDGET_LOCALIZE_STRING = "WidgetLocalizeString";
	
	public static String ACTION_LOCALIZE = "ActionLocalize";
	public static String ACTION_SOCIAL = "ActionSocial";


	private static ArrayList<String> localizeMessages = new ArrayList<String>();
	
	private static boolean localizeTab = true;
	

	private static Context contextT = null;
	private static AppWidgetManager appWidgetManagerT = null;
	private static BonVoyageWidget widget = null;
	private static int[] appWidgetIdsT = null;
	
	@Override
	public void onEnabled(Context context) {
		super.onEnabled(context);	
		
	}

	/**
	 * Update the widget with the string s
	 * @param s
	 */
	public static void updateLocalize(String s){
		SharedPreferences preferences = contextT.getSharedPreferences(WIDGET_PREFERENCES,
				  Context.MODE_PRIVATE);

		Editor edit = preferences.edit(); 
		edit.putString(WIDGET_LOCALIZE_STRING, s);
		edit.commit(); 
		widget.onUpdate(contextT, appWidgetManagerT, appWidgetIdsT);
	}
	public static BonVoyageWidget getWidget() {
		return widget;
	}

	public static void setWidget(BonVoyageWidget widget) {
		BonVoyageWidget.widget = widget;
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		
			widget = this;
			appWidgetManagerT = appWidgetManager;
			contextT = context;
			appWidgetIdsT = appWidgetIds;
	 
	        //Actualizamos el widget actual 
		    updateLocalize(context);
		
	}
	
	
	/**
	 * Updates the widget
	 * @param context
	 */
	public static void updateLocalize(Context context){
			SharedPreferences widgetPrefs = context.getSharedPreferences(WIDGET_PREFERENCES, Context.MODE_PRIVATE);

			 Log.d("TAG_SOCIAL","UPDATE_LOCALIZE");
			 
			 localizeMessages.clear();
			 RemoteViews controles =
			            new RemoteViews(context.getPackageName(), R.layout.bon_voyage_widget);
	            new RemoteViews(context.getPackageName(), R.layout.bon_voyage_widget);
			 localizeMessages.add(widgetPrefs.getString(WIDGET_LOCALIZE_STRING, "ERROR"));

			 
			 String msg = "";
			 if(localizeTab){
				 for(int i = localizeMessages.size()-1; i >= 0 ; i--){
						msg+=localizeMessages.get(i).toString()+"\n"; 
					 }
					 
			     //Actualizamos el mensaje en el control del widget
				 controles.setTextViewText(R.id.WidgetTv_Messages, msg);
			     appWidgetManagerT.updateAppWidget(appWidgetIdsT, controles);			 }	 
		}
	
	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		super.onDeleted(context, appWidgetIds);
	}

	@Override
	public void onDisabled(Context context) {
		super.onDisabled(context);
	}

}
