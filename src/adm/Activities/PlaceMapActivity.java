/**
 * 
 */
package adm.Activities;

import java.util.ArrayList;
import java.util.List;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import adm.Geolocation.BVOverlayItem;
import adm.Geolocation.MyBalloonItemizedOverlay;
import adm.Repository.GlobalParameters;
import adm.Search.Search;
import adm.Search.SearchItem;
import adm.Search.SearchResponse;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author AndroIT
 * 
 */
public class PlaceMapActivity extends MapActivity {
	private int idPlace;
	private String placeName;
	private MapController mc;
	private MapView mv;
	private float lat, lon;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.placemap);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			idPlace = extras.getInt("idPlace");
			lat = extras.getFloat("lat");
			lon = extras.getFloat("lon");
			placeName = extras.getString("placeName");
		} else {
			this.finish();
		}

		mv = (MapView) findViewById(R.id.placeMapView);
		mv.setBuiltInZoomControls(true);
		// mv.setStreetView(true);
		mc = mv.getController();
		GeoPoint center = new GeoPoint((int) (lat * 1E6), (int) (lon * 1E6));
		mc.setCenter(center);
		mc.setZoom(14);

		TextView actionBar = (TextView) findViewById(R.id.actionBarPlaceMap);
		actionBar.setText(placeName);

		AsyncTaskRelated task = new AsyncTaskRelated(this);
		task.execute(idPlace);
	}

	@Override
	protected boolean isRouteDisplayed() {
		// Necessary method to override in MapActivity
		return false;
	}

	private class AsyncTaskRelated extends
			AsyncTask<Integer, SearchResponse, Void> {

		// ProgressDialog
		ProgressDialog dialog;
		Context context;
		private Search search;
		private List<Overlay> mapOverlay;
		// private MarkerItemizedOverlay mio;
		private MyBalloonItemizedOverlay mio;

		public AsyncTaskRelated(Context cont) {
			this.context = cont;
		}

		@Override
		protected void onPreExecute() {
			try {
				this.dialog = ProgressDialog.show(context,
						context.getString(R.string.cargando),
						context.getString(R.string.obtInfoServer), true);
			} catch (Exception e) {
				Log.e("PlaceMap", "Error en dialog de placeMapActivity");
			}
		}

		@Override
		protected Void doInBackground(Integer... idPlaces) {
			try {
				dialog.setCancelable(false);
				dialog.show();
			} catch (Exception e) {
				Log.e("", "Error en dialog de placeMapActivity");
			}

			// Solicita los detalles de un lugar
			search = new Search(GlobalParameters.BASE_URL_JSON);
			try {
				String place_type = "CITY|REGION|COUNTRY|CONTINENT|NATURAL|CULTURAL|POI|INFRASTRUCTURE|HOTEL|RESTAURANT|PUB|DISCO";
				SearchResponse sr = search.related(idPlaces[0].toString(),
						GlobalParameters.API_KEY, place_type, "");
				if (sr.getResult() != null) {
					// Envio a publicar la info
					publishProgress(sr);
				}

			} catch (Exception e) {
				Toast.makeText(getApplicationContext(), e.toString(), 3000);
			}

			return null;
		}

		@Override
		protected void onProgressUpdate(SearchResponse... responses) {
			SearchResponse resp = responses[0];

			// MUESTRA DATOS DEL LUGAR
			ArrayList<SearchItem> items = resp.getResult();
			mapOverlay = mv.getOverlays();
			Drawable dr = getResources().getDrawable(R.drawable.marker);
			// mio = new MarkerItemizedOverlay(dr, context);
			mio = new MyBalloonItemizedOverlay(dr, mv);

			// SearchItem item;
			int lat, lon, nameId;
			String description;
			GeoPoint point;
			OverlayItem oi;
			for (SearchItem item : items) {
				lat = (int) (item.getLatitude() * 1E6);
				lon = (int) (item.getLongitude() * 1E6);
				point = new GeoPoint(lat, lon);
				try {
					nameId = context.getResources().getIdentifier(
							item.getPlace_type(), "string", null);
					description = context.getResources().getString(nameId);
				} catch (Exception e) {
					description = item.getPlace_type();
				}
				//oi = new OverlayItem(point, item.getName(), description);
				oi = new BVOverlayItem(point, item.getName(), description, item.getId());

				// Si el item es el del destino del PlaceMap cambiamos el color
				// del marker
				if (item.getId() == idPlace)
					oi.setMarker(mio.boundCenterBottomAux(context
							.getResources()
							.getDrawable(R.drawable.marker_green)));

				mio.addOverlay(oi);
			}

		}

		@Override
		protected void onPostExecute(Void result) {
			mapOverlay.add(mio);
			try {

				dialog.dismiss();
			} catch (Exception e) {
				Log.e("PlaceMap", "Error al cerrar dialog de placeMapActivity");
			}
		}
	}
	
	public void onActionBarHomeButtonClick(View v) 
	{
		//Go to the dashboard
		startActivity(new Intent(this, DashboardActivity.class));
	}
}
