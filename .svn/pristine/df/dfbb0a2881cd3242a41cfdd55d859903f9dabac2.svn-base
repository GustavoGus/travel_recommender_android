/**
 * 
 */
package adm.Activities;
import java.util.ArrayList;
import java.util.List;

import com.google.android.maps.*;

import adm.Repository.GlobalParameters;
import adm.Search.SearchItem;
import adm.Services.TrackingLocalization;
import adm.User.User;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import android.widget.ToggleButton;

/**
 * @author AndroIT
 * 
 */
public class DisfrutarActivity extends MapActivity {

	private final String PREFERENCES_DISFRUTAR = "PREFERENCES_DISFRUTAR";


	private boolean hotel = false;
	private boolean restaurant = false;
	private boolean bar = false;
	private boolean historical = false;
	private boolean transport = false;
	private MapController mapController = null;
	private MapView mapView = null;

	private Intent intentService = null;
	private ArrayList<SearchItem> listLocations = new ArrayList<SearchItem>();
	private ArrayList<SearchItem> addedLocations = new ArrayList<SearchItem>();
	private static boolean mapEnabled = false;


	// Para las coordenadas
	private double coorLat = 0.0;
	private double coorLon = 0.0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		// Quitar barra de arriba.
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// Aplicar layout
		setContentView(R.layout.disfrutar);
		loadOptions();

		//Miramos el botón del servicio:
		SharedPreferences preferences = getSharedPreferences(
				PREFERENCES_DISFRUTAR, 0);
		ToggleButton tglOn = (ToggleButton) findViewById(R.id.tgbtn_on);

		if(preferences.getBoolean("service", false)){
			tglOn.setChecked(true);
		}else {
			tglOn.setChecked(false);
		}
		
		
		// Accedemos al MapView
		mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);

		// Obtenemos el controlador del mapa
		mapController = mapView.getController();
		mapController.setZoom(20);
		
		mapController.setCenter(new GeoPoint((int) (39.47 * 1E6),
				(int) (-0.37 * 1E6)));

		Task t = new Task();
		t.execute(null,null,null);
		User usuarioActual = new User(GlobalParameters.BASE_URL_JSON, getParent());
		
		if(!usuarioActual.hasAuthKey()){
			Toast toastLogin = Toast.makeText(getParent(),getResources().getString(R.string.Login) , Toast.LENGTH_SHORT);
			toastLogin.show();
		}
	}

	/**
	 * OnClick para el botón de Check-in
	 * 
	 * @param v
	 */
	public void checkInOnClick(View v) {
		if (!isMyServiceRunning()) {
			Toast t = Toast.makeText(getParent(),getResources().getString(R.string.start_service) , Toast.LENGTH_LONG);
			t.show();
		}else if(GlobalParameters.my_latitude == 0.0f && GlobalParameters.my_longitude == 0.0f){
			Toast t = Toast.makeText(getParent(),getResources().getString(R.string.location_error) , Toast.LENGTH_LONG);
			t.show();
		}else{
			User usuarioActual = new User(GlobalParameters.BASE_URL_JSON, getParent());
			
			if(!usuarioActual.hasAuthKey()){
				Toast toastLogin = Toast.makeText(getParent(),getResources().getString(R.string.Login) , Toast.LENGTH_SHORT);
				toastLogin.show();
			}else{
				
				Handler h = new Handler();
				loadingTask task = new loadingTask(getParent());
				task.setCancel(false);
				h.post(task);
				User usuario = new User(GlobalParameters.BASE_URL_JSON, this);
				
				if(usuario.hasAuthKey()){
					startActivity(new Intent(DisfrutarActivity.this, CheckinDisfrutarActivity.class));
				}
				task.setCancel(true);
			}
		}
	
	}

	/**
	 * OnClick para el botón de 'add'
	 * 
	 * @param v
	 */
	public void add_OnClick(View v) {
		User usuarioActual = new User(GlobalParameters.BASE_URL_JSON, getParent());
		
		if(!usuarioActual.hasAuthKey()){
			Toast toastLogin = Toast.makeText(getParent(),getResources().getString(R.string.Login) , Toast.LENGTH_SHORT);
			toastLogin.show();
		}else{
			startActivity(new Intent(DisfrutarActivity.this, AddActivity.class));
		}
	}

	/**
	 * OnClick para el botón de 'on'
	 * 
	 * @param v
	 */
	public void on_OnClick(View v) {
		SharedPreferences preferences = getSharedPreferences(
				PREFERENCES_DISFRUTAR, 0);
		Editor preferences_editor = preferences.edit();
		
		
	User usuarioActual = new User(GlobalParameters.BASE_URL_JSON, getParent());
	try{
		if(!usuarioActual.hasAuthKey()){
			Toast toastLogin = Toast.makeText(getParent(),getResources().getString(R.string.Login) , Toast.LENGTH_SHORT);
			toastLogin.show();
		}else{
			ToggleButton tglOn = (ToggleButton) findViewById(R.id.tgbtn_on);
			if (tglOn.isChecked()) {
				if (!isMyServiceRunning()) {
					intentService = new Intent(DisfrutarActivity.this,
							TrackingLocalization.class);
					
					startService(intentService);
					mapEnabled = true;
					preferences_editor.putBoolean("service", true);
					preferences_editor.commit();
				}
	
			} else {
				if (isMyServiceRunning()) {
					intentService = new Intent(DisfrutarActivity.this,
							TrackingLocalization.class);
					stopService(intentService);
					mapEnabled = false;
					preferences_editor.putBoolean("service", false);
					preferences_editor.commit();
				}
	
			}
		}
	}catch(Exception e){
		e.printStackTrace();
	}
}
	/**
	 * OnClick del toggleButton del hotel
	 * 
	 * @param v
	 */
	public void hotel_OnClick(View v) {
		if (!isMyServiceRunning()) {
			Toast t = Toast.makeText(getParent(),getResources().getString(R.string.start_service) , Toast.LENGTH_SHORT);
			t.show();
			
			((ToggleButton) findViewById(R.id.tglbtn_hotel)).setChecked(!((ToggleButton) findViewById(R.id.tglbtn_hotel)).isChecked());
		}else{
			hotel = ((ToggleButton) findViewById(R.id.tglbtn_hotel)).isChecked();
			Log.d("TAG_DISFRUTAR_BON_VOYAGE", "hotel: " + hotel);
		}
	}

	/**
	 * OnClick del toggleButton del hotel
	 * 
	 * @param v
	 */
	public void restaurant_OnClick(View v) {
		if (!isMyServiceRunning()) {
			Toast t = Toast.makeText(getParent(),getResources().getString(R.string.start_service) , Toast.LENGTH_SHORT);
			t.show();
			
			((ToggleButton) findViewById(R.id.tglbtn_restaurant)).setChecked(!((ToggleButton) findViewById(R.id.tglbtn_restaurant)).isChecked());

		}else{
			restaurant = ((ToggleButton) findViewById(R.id.tglbtn_restaurant))
					.isChecked();
			Log.d("TAG_DISFRUTAR_BON_VOYAGE", "restaurant: " + restaurant);
		}

	}
	/**
	 * OnClick del toggleButton del bar
	 * 
	 * @param v
	 */
	public void bar_OnClick(View v) {
		if (!isMyServiceRunning()) {
			Toast t = Toast.makeText(getParent(),getResources().getString(R.string.start_service) , Toast.LENGTH_SHORT);
			t.show();
			
			((ToggleButton) findViewById(R.id.tglbtn_bar)).setChecked(!((ToggleButton) findViewById(R.id.tglbtn_bar)).isChecked());

		}else{
			bar = ((ToggleButton) findViewById(R.id.tglbtn_bar)).isChecked();
			Log.d("TAG_DISFRUTAR_BON_VOYAGE", "bar: " + bar);
		}

	}
	/**
	 * OnClick del toggleButton del museo
	 * 
	 * @param v
	 */
	public void historical_OnClick(View v) {
		if (!isMyServiceRunning()) {
			Toast t = Toast.makeText(getParent(),getResources().getString(R.string.start_service) , Toast.LENGTH_SHORT);
			t.show();
			
			((ToggleButton) findViewById(R.id.tglbtn_museum_historical)).setChecked(!((ToggleButton) findViewById(R.id.tglbtn_museum_historical)).isChecked());

		}else{
			historical = ((ToggleButton) findViewById(R.id.tglbtn_museum_historical))
				.isChecked();
			Log.d("TAG_DISFRUTAR_BON_VOYAGE", "historical: " + historical);
		}

	}
	/**
	 * OnClick del toggleButton del transporte
	 * 
	 * @param v
	 */
	public void transport_OnClick(View v) {
		if (!isMyServiceRunning()) {
			Toast t = Toast.makeText(getParent(),getResources().getString(R.string.start_service) , Toast.LENGTH_SHORT);
			t.show();
			
			((ToggleButton) findViewById(R.id.tglbtn_transport)).setChecked(!((ToggleButton) findViewById(R.id.tglbtn_transport)).isChecked());

		}else{
			transport = ((ToggleButton) findViewById(R.id.tglbtn_transport))
					.isChecked();
			Log.d("TAG_DISFRUTAR_BON_VOYAGE", "transport: " + transport);
		}

	}

	/*
	 * Con los siguientes métodos guardamos de manera persistente las opciones
	 * que el usuario ha seleccionado para filtrar la búsqueda en el mapa
	 */

	/**
	 * Método genérico para guardar las opciones en el shared preferences
	 */
	protected void saveOptions() {
		SharedPreferences preferences = getSharedPreferences(
				PREFERENCES_DISFRUTAR, 0);
		Editor preferences_editor = preferences.edit();

		preferences_editor.putBoolean("Historico", historical);
		preferences_editor.putBoolean("Transporte", transport);
		preferences_editor.putBoolean("Hotel", hotel);
		preferences_editor.putBoolean("Restaurantes", restaurant);
		preferences_editor.putBoolean("Copas", bar);

		preferences_editor.commit();
	}

	/**
	 * Método genérico para cargar las opciones del shared preferences
	 */
	protected void loadOptions() {
		SharedPreferences preferences = getSharedPreferences(
				PREFERENCES_DISFRUTAR, 0);

		coorLat = 0.0f;
		coorLon = 0.0f;
		historical = preferences.getBoolean("Historico", false);
		transport = preferences.getBoolean("Transporte", false);
		hotel = preferences.getBoolean("Hotel", false);
		restaurant= preferences.getBoolean("Restaurantes", false);
		bar = preferences.getBoolean("Copas", false);
		
		
		ToggleButton tglOn = (ToggleButton) findViewById(R.id.tglbtn_museum_historical);

		if(historical){
			tglOn.setChecked(true);
		}else{
			tglOn.setChecked(false);			
		}
		tglOn = (ToggleButton) findViewById(R.id.tglbtn_transport);
		if(transport){
			tglOn.setChecked(true);

		}else{
			tglOn.setChecked(false);			

		}
		tglOn = (ToggleButton) findViewById(R.id.tglbtn_hotel);

		if(hotel){
			tglOn.setChecked(true);

		}else{
			tglOn.setChecked(false);			

		}
		
		tglOn = (ToggleButton) findViewById(R.id.tglbtn_restaurant);

		if(restaurant){
			tglOn.setChecked(true);

		}else{
			tglOn.setChecked(false);			

		}
		tglOn = (ToggleButton) findViewById(R.id.tglbtn_bar);

		if(bar){
			tglOn.setChecked(true);

		}else{
			tglOn.setChecked(false);			

		}
		
	}

	/**
	 * Cuando el usuario pulse back, guardamos las preferences
	 */

	@Override
	public void onBackPressed() {

		saveOptions();
		super.onBackPressed();

	}

	/***
	 * Sobrecarga de métodos del ciclo de vida
	 */
	@Override
	protected void onDestroy() {

		saveOptions();
		super.onDestroy();
	}

	@Override
	protected void onPause() {

		saveOptions();
		super.onPause();
	}

	@Override
	protected void onRestart() {

		saveOptions();
		super.onRestart();
		loadOptions();
	}

	@Override
	protected void onResume() {
		
		super.onResume();
		loadOptions();
		
	}

	@Override
	protected void onStart() {
		
		super.onStart();
		loadOptions();
		
	}

	@Override
	protected void onStop() {
		
		saveOptions();
		super.onStop();
		
	}

	/**
	 * Tarea asíncrona para actualizar el mapa
	 * 
	 */

	private class Task extends AsyncTask<Object, Object, Object> {

		// Para las busquedas

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Runnable#run()
		 */
		public void run() {

			try {
				while(true){	

				// ---------------------------------------//
				// Bucle de tracking

				if (coorLat  != GlobalParameters.my_latitude && GlobalParameters.my_latitude!= null
						&& coorLon  != GlobalParameters.my_longitude && GlobalParameters.my_longitude != null) {

					coorLat = GlobalParameters.my_latitude;
					coorLon = GlobalParameters.my_longitude;

					listLocations = TrackingLocalization.getListLocations();
					mapController.animateTo(new GeoPoint((int) (coorLat * 1E6),
							(int) (coorLon * 1E6)));
				}


				// ---------------------------------------//

				ArrayList<DisfrutarMapPoints> mapOverlays = new ArrayList<DisfrutarActivity.DisfrutarMapPoints>();
				addedLocations.clear();
				/*
				 * Recorremos la lista de lugares, y vamos añadiendo aquellos
				 * que tenemos activados.
				 */
				for (SearchItem item : listLocations) {
					// Añadir overlay de hoteles
					if (hotel) {
						if (item.getPlace_type().equals("HOTEL")) {
							mapOverlays.add(new DisfrutarMapPoints(
									new GeoPoint(
											(int) (item.getLatitude() * 1E6),
											(int) (item.getLongitude() * 1E6)),
									R.drawable.hotel, item.getId()));
							addedLocations.add(item);
						}
					// Añadir overlay de restaurantes
					}
					if (restaurant) {
						if (item.getPlace_type().equals("RESTAURANT")) {

							mapOverlays.add(new DisfrutarMapPoints(
									new GeoPoint(
											(int) (item.getLatitude() * 1E6),
											(int) (item.getLongitude() * 1E6)),
									R.drawable.restaurant, item.getId()));
							addedLocations.add(item);
						}
					// Añadir overlay de bares/copas
					}
					if (bar) {
						if (item.getPlace_type().equals("PUB")
								|| item.getPlace_type().equals("DISCO")) {
							mapOverlays.add(new DisfrutarMapPoints(
									new GeoPoint(
											(int) (item.getLatitude() * 1E6),
											(int) (item.getLongitude() * 1E6)),
									R.drawable.bar, item.getId()));
							addedLocations.add(item);
						}
					// Añadir overlay de museos-poi-naturaleza
					}
					if (historical) {
						if (item.getPlace_type().equals("NATURAL")
								|| item.getPlace_type().equals("CULTURAL")
								|| item.getPlace_type().equals("POI")) {
							mapOverlays.add(new DisfrutarMapPoints(
									new GeoPoint(
											(int) (item.getLatitude() * 1E6),
											(int) (item.getLongitude() * 1E6)),
									R.drawable.museum_historical, item.getId()));
							addedLocations.add(item);
						}
					// Añadir overlay de transportes
					}
					if (transport) {
						if (item.getPlace_type().equals("INFRASTRUCTURE")) {
							mapOverlays.add(new DisfrutarMapPoints(
									new GeoPoint(
											(int) (item.getLatitude() * 1E6),
											(int) (item.getLongitude() * 1E6)),
									R.drawable.transport, item.getId()));
							addedLocations.add(item);
						}
					}

				}// End for
				publishProgress(mapOverlays);				
				try {
					System.gc();
					Thread.sleep(3000);
				} catch (Exception e) {

				}

				
				 }//End While
			} catch (Exception e) {
			}
		}

		@SuppressWarnings("unchecked")
		@Override
		protected void onProgressUpdate(Object... values) {

			List<Overlay> listOverlays = mapView.getOverlays();


			listOverlays.clear();

			for (int i = 0; i < ((ArrayList<DisfrutarMapPoints>)values[0]).size(); i++) {
				listOverlays.add(((ArrayList<DisfrutarMapPoints>)values[0]).get(i));
			}
			
			super.onProgressUpdate(values);
		}

		/* (non-Javadoc)
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 */
		@Override
		protected Object doInBackground(Object... params) {			
			run();
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.android.maps.MapActivity#isRouteDisplayed()
	 */
	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	/*-------------------------------------------------*/
	/*
	 * Class MapPoints
	 */
	/**
	 * @author Julian
	 * 
	 */
	public class DisfrutarMapPoints extends Overlay {

		private GeoPoint p = null;
		private int drawable = 0;
		public DisfrutarMapPoints(GeoPoint p, int drawable, int idPlace) {
			this.p = p;
			this.drawable = drawable;
		}

		public boolean draw(Canvas canvas, MapView mapView, boolean shadow,
				long when) {
			super.draw(canvas, mapView, shadow);

			// Translate the GeoPoint to screen pixels
			Point screenPts = new Point();
			mapView.getProjection().toPixels(p, screenPts);

			// Add the marker
			Bitmap bmp = BitmapFactory.decodeResource(getResources(), drawable);
			canvas.drawBitmap(bmp, screenPts.x, screenPts.y, null);

			return true;
		}

		@Override
		public boolean onTap(GeoPoint p, MapView mapView) {
			// TODO Auto-generated method stub
			boolean exit = false;
			super.onTap(p, mapView);
			for (int i = 0; i < addedLocations.size() && ! exit; i++) {
				
				SearchItem item = addedLocations.get(i);
				if((int)(item.getLatitude()*1E6)/1000 == p.getLatitudeE6()/1000 && (int)(item.getLongitude()*1E6)/1000 == p.getLongitudeE6()/1000){
					Intent intent = new Intent(DisfrutarActivity.this,
							PlaceActivity.class);
					intent.putExtra("idPlace", item.getId());
					startActivity(intent);
					exit = true;
				}
			
			}
			
	
			
			return true;
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
	
	// AsyncTask
	public class loadingTask implements Runnable {

		final ProgressDialog dialog;
		final Context context;
		private boolean cancel = false;

		public loadingTask(Context cont) {
			this.context = cont;
			this.dialog = ProgressDialog.show(context,
					context.getString(R.string.cargando),
					context.getString(R.string.obtInfoServer), true);
		}

		
	
		public boolean isCancel() {
			return cancel;
		}



		public void setCancel(boolean cancel) {
			this.cancel = cancel;
		}



		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		public void run() {

			dialog.setCancelable(true);
			dialog.show();
			int i = 0;
			while(!cancel){
				try {
					dialog.setProgress(i++);
					Thread.sleep(100);
				} catch (InterruptedException e) {

					e.printStackTrace();
				}
			}
			dialog.dismiss();
		}
	}
	
	
	public static boolean isMapEnabled() {
		return mapEnabled;
	}
}
