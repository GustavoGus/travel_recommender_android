/**
 * 
 */
package adm.Activities;

import java.util.Collections;
import java.util.List;
import java.util.Vector;

import adm.Geolocation.BVOverlayItem;
import adm.Geolocation.MyBalloonItemizedOverlay;
import adm.Repository.CoordInfo;
import adm.Repository.DataBaseManagement;
import adm.Repository.GlobalParameters;
import adm.User.User;
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

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class CheckMapActivity extends MapActivity {
	private float centLat;
	private float centLon;
	private String placeName;
	private int idPlace;
	private MapController mc;
	private MapView mv;

	@Override
	protected void onCreate(Bundle icicle) {
		// TODO Auto-generated method stub
		super.onCreate(icicle);
		// Quitar barra de arriba.
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// Aplicar layout
		setContentView(R.layout.checkmap);

		User user = new User(GlobalParameters.BASE_URL_JSON, this);
		DataBaseManagement db = new DataBaseManagement(this);
		db.CreateDB();
		if (user.hasAuthKey()) {
			// default zoom level
			int zoomLvl = 14;

			// Recupero coordenadas de la base de datos
			List<CoordInfo> ci = db.getCoordsInfo(user.getUserName());

			// Si no se ha hecho checkin aún, pongo algo...
			if (ci.isEmpty()) {
				idPlace = 76797;
				centLat = Float.parseFloat("39.436911");
				centLon = Float.parseFloat("-0.465416");
				placeName = "Torrent";
				ci.add(new CoordInfo(user.getUserName(), placeName, Integer
						.toString(idPlace), centLon, centLat, placeName));
			}

			else {
				Vector<Float> lat = new Vector<Float>();
				Vector<Float> lon = new Vector<Float>();
				for (int i = 0; i < ci.size(); i++) {
					lat.add((ci.get(i).getLatitude()));
					lon.add(ci.get(i).getLongitude());
				}

				// Recupero el máximo y mínimo de cada vector
				float maxLat = Collections.max(lat);
				float maxLon = Collections.max(lon);
				float minLat = Collections.min(lat);
				float minLon = Collections.min(lon);

				// Calculo el centro del mapa
				centLat = minLat + (maxLat - minLat) / 2;
				centLon = minLon + (maxLon - minLon) / 2;

				// what's the distance of our coordinates? (in kilometers)
				float dist = (float) (6371 * Math.acos(Math
						.sin(minLat / 57.2958)
						* Math.sin(maxLat / 57.2958)
						+ (Math.cos(minLat / 57.2958)
								* Math.cos(maxLat / 57.2958) * Math.cos(maxLon
								/ 57.2958 - minLon / 57.2958))));

				// determine the zoom level out of the calculated distance
				if (dist < 24576)
					zoomLvl = 1;
				if (dist < 12288)
					zoomLvl = 2;
				if (dist < 6144)
					zoomLvl = 3;
				if (dist < 3072)
					zoomLvl = 4;
				if (dist < 1536)
					zoomLvl = 5;
				if (dist < 768)
					zoomLvl = 6;
				if (dist < 384)
					zoomLvl = 7;
				if (dist < 192)
					zoomLvl = 8;
				if (dist < 96)
					zoomLvl = 9;
				if (dist < 48)
					zoomLvl = 10;
				if (dist < 24)
					zoomLvl = 11;
				if (dist < 11)
					zoomLvl = 12;
				if (dist < 4.8)
					zoomLvl = 13;
				if (dist < 3.2)
					zoomLvl = 14;
				if (dist < 1.6)
					zoomLvl = 15;
				if (dist < 0.8)
					zoomLvl = 16;
				if (dist < 0.3)
					zoomLvl = 17;
			}

			mv = (MapView) findViewById(R.id.checkMapView);
			mv.setBuiltInZoomControls(true);
			mc = mv.getController();
			GeoPoint center = new GeoPoint((int) (centLat * 1E6),
					(int) (centLon * 1E6));
			mc.setCenter(center);
			mc.setZoom(zoomLvl);

			TextView actionBar = (TextView) findViewById(R.id.actionBarCheckMap);
			actionBar.setText(getResources().getString(R.string.where));

			AsyncTaskCheck task = new AsyncTaskCheck(this, ci);
			task.execute();

		}

	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	// AsyncTask ----------------------------------------------------------
	private class AsyncTaskCheck extends AsyncTask<Integer, Void, Void> {

		// ProgressDialog
		ProgressDialog dialog;
		Context context;
		private List<Overlay> mapOverlay;
		private MyBalloonItemizedOverlay mio;
		private List<CoordInfo> ci;

		public AsyncTaskCheck(Context cont, List<CoordInfo> ci) {
			this.context = cont;
			this.ci = ci;
		}

		@Override
		protected void onPreExecute() {
			try {
				this.dialog = ProgressDialog.show(context,
						context.getString(R.string.cargando),
						context.getString(R.string.loading), true);
			} catch (Exception e) {
				Log.e("PlaceMap", "Error en dialog de checkMapActivity");
			}
		}

		@Override
		protected Void doInBackground(Integer... idPlaces) {
			try {
				dialog.setCancelable(false);
				dialog.show();
			} catch (Exception e) {
				Log.e("", "Error en dialog de checkMapActivity");
			}

			if (!ci.isEmpty()) {
				// Envio a publicar la info
				publishProgress();
			}

			return null;
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			// MUESTRA DATOS DEL LUGAR
			mapOverlay = mv.getOverlays();
			Drawable dr = getResources().getDrawable(R.drawable.marker);
			mio = new MyBalloonItemizedOverlay(dr, mv);

			int lat, lon;
			String description;
			GeoPoint point;
			OverlayItem oi;

			for (CoordInfo c : ci) {
				lat = (int) (c.getLatitude() * 1E6);
				lon = (int) (c.getLongitude() * 1E6);
				point = new GeoPoint(lat, lon);
				description = c.getDescription();
				
				oi = new BVOverlayItem(point, c.getName(), description,
						Integer.parseInt(c.getIdPlace()));
				mio.addOverlay(oi);
			}
		}

		@Override
		protected void onPostExecute(Void result) {
			if(!ci.isEmpty())
				mapOverlay.add(mio);
			try {
				dialog.dismiss();
			} catch (Exception e) {
				Log.e("PlaceMap", "Error al cerrar dialog de placeMapActivity");
			}
		}
	}

	public void onActionBarHomeButtonClick(View v) {
		// Go to the dashboard
		startActivity(new Intent(this, DashboardActivity.class));
	}

}
