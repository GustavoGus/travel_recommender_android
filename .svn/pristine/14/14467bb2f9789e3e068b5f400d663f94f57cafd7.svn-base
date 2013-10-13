/**
 * 
 */
package adm.Services;

import java.util.ArrayList;

import adm.Activities.DisfrutarActivity;
import adm.Geolocation.Geolocation;
import adm.Media.BeepAndVibrate;
import adm.Repository.GlobalParameters;
import adm.Search.Search;
import adm.Search.SearchItem;
import adm.Search.SearchResponse;
import adm.widget.BonVoyageWidget;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

/**
 * @author Usuario
 * 
 */
public class TrackingLocalization extends Service {
	private static final String TAG = "Tracking Localization";

	@SuppressWarnings("unused")
	private static LocationManager myLocManager = null;
	private Task tarea;
	private static ArrayList<SearchItem> listLocations = new ArrayList<SearchItem>();

	public static ArrayList<SearchItem> getListLocations() {
		return listLocations;
	}

	public static void setListLocations(ArrayList<SearchItem> listLocations) {
		TrackingLocalization.listLocations = listLocations;
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		Toast.makeText(this, "My Service Created", Toast.LENGTH_LONG).show();
		Log.d(TAG, "onCreate");
		// Creates
		tarea = new Task();
		try {
			Geolocation geo = new Geolocation();
			myLocManager = geo.myCoords(getApplicationContext());
		} catch (Exception e) {
			Log.e(TAG, "Error en captura coordenadas: " + e.getMessage());
		}
	}

	@Override
	public void onDestroy() {
		Toast.makeText(this, "My Service Stopped", Toast.LENGTH_LONG).show();
		Log.d(TAG, "onDestroy");
		
		tarea.cancel(true);
	}

	@Override
	public void onStart(Intent intent, int startid) {
		Toast.makeText(this, "My Service Started", Toast.LENGTH_LONG).show();
		Log.d(TAG, "onStart");
		tarea.execute(10000);
	}

	// **************************************************
	// * ASYNCTASK
	// **************************************************
	private class Task extends AsyncTask<Integer, Integer, Long> {

		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
		}

		@Override
		protected Long doInBackground(Integer... args) {
			Log.i(TAG, "AsyncTask Running");
			int n = 0;
		
			double coorLat = 0.0;
			double coorLon = 0.0;
			Search busqueda;
			SearchResponse busquedaResponse;
			final SharedPreferences preferencias;
			boolean pref_tipoSitios[] = new boolean[12];
			
			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
			
			
			boolean pref_vibrar = preferences.getBoolean("beep_pref", true);
			boolean pref_beep = preferences.getBoolean("vibration_pref", true);
			BeepAndVibrate  notification=new BeepAndVibrate(getApplicationContext());

		
			try {
				preferencias = getSharedPreferences("propertiesAlerts",
						Context.MODE_PRIVATE);

				pref_tipoSitios[0] = preferencias.getBoolean("CITY", false);
				pref_tipoSitios[1] = preferencias.getBoolean("REGION", false);
				pref_tipoSitios[2] = preferencias.getBoolean("COUNTRY", false);
				pref_tipoSitios[3] = preferencias
						.getBoolean("CONTINENT", false);
				pref_tipoSitios[4] = preferencias.getBoolean("NATURAL", false);
				pref_tipoSitios[5] = preferencias.getBoolean("CULTURAL", false);
				pref_tipoSitios[6] = preferencias.getBoolean("POI", false);
				pref_tipoSitios[7] = preferencias.getBoolean("INFRASTRUCTURE",
						false);
				pref_tipoSitios[8] = preferencias.getBoolean("HOTEL", false);
				pref_tipoSitios[9] = preferencias.getBoolean("RESTAURANT",
						false);
				pref_tipoSitios[10] = preferencias.getBoolean("PUB", false);
				pref_tipoSitios[11] = preferencias.getBoolean("DISCO", false);

				pref_vibrar = preferencias.getBoolean("BEEP", true);
				pref_beep = preferencias.getBoolean("VIBRAR", true);
			} catch (Exception e) {
				Log.e(TAG, "Al obtener preferencias: " + e.getMessage());
			}

			// ---------------------------------------//
			// Bucle de tracking
			while (!isCancelled()) {
				try {
					if (BonVoyageWidget.getWidget() != null
							|| DisfrutarActivity.isMapEnabled()) {

						
						if (GlobalParameters.my_latitude != coorLat
								|| GlobalParameters.my_longitude != coorLon) {
							coorLat = GlobalParameters.my_latitude;
							coorLon = GlobalParameters.my_longitude;

						
							busqueda = new Search(
									GlobalParameters.BASE_URL_JSON);
							busquedaResponse = busqueda
									.location(
											coorLat,
											coorLon,
											GlobalParameters.API_KEY,
											"CITY|REGION|COUNTRY|CONTINENT|NATURAL|CULTURAL|POI|INFRASTRUCTURE|HOTEL|RESTAURANT|PUB|DISCO",
											"");

							listLocations = busquedaResponse.getResult();
							if (busquedaResponse != null) {
								updateWidget(busquedaResponse.toString(getApplicationContext()));
								notification.makeBeepAndVibrate(1, pref_vibrar, pref_beep);

							}
						}

						Thread.sleep(args[0]);
					} else {
						Thread.sleep(60000);
					}

					Log.i(TAG, "AsyncTask Running " + n);
					n++;
				} catch (Exception e) {
					Log.e(TAG, e.getMessage());
				}
			}
			return null;
		}
	}

	// **************************************************
	// * Update Widget
	// **************************************************
	public void updateWidget(String texto) {
		try {
			BonVoyageWidget.updateLocalize(texto);

		} catch (NullPointerException e) {
			Log.d("BON_VOYAGE", "No widget");
		} catch (Exception e) {
			Log.d("BON_VOYAGE", "Widget Error");
		}
	}
}
