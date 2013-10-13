package adm.Geolocation;

import adm.Repository.GlobalParameters;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

/**
 * Clase que encapsula el objeto Geolocation.
 * 
 */

public class Geolocation {

	/*
	 * Constructor
	 */
	public Geolocation() {
		super();
	}

	/**
	 * @param Context
	 */

	public LocationManager myCoords(Context context) {

		LocationManager mylocManager = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
		LocationListener locationListener = new LocationListener() {

			public void onLocationChanged(Location location) {
				Log.i("Geolocalizacion",
						"Geolocalizacion. Actualizamos localizacion: "
								+ location.toString());
				GlobalParameters.my_latitude = location.getLatitude();
				GlobalParameters.my_longitude = location.getLongitude();
			}
			public void onProviderEnabled(String provider) {
			}
			public void onProviderDisabled(String provider) {
			}
			public void onStatusChanged(String provider, int status,
					Bundle extras) {
			}
		};

		// Register the listener with the Location Manager to receive location
		// updates
		try {
			mylocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
					0, 0, locationListener);
		} catch (Exception e) {
			Log.e("Geolocation", "Fallo al pedir al GPS");
		}

		try {
			mylocManager.requestLocationUpdates(
					LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
		} catch (Exception e) {
			Log.e("Geolocation", "Fallo al pedir al GPS");
		}
		try {

			locationListener.onLocationChanged(mylocManager
					.getLastKnownLocation(LocationManager.GPS_PROVIDER));
		} catch (Exception e) {
			Log.e("Geolocalizacion",
					"Geolocalizacion. Error al obtener la ultima localizacion GPS_PROVIDER: "
							+ e.getMessage());
		}

		try {

			locationListener.onLocationChanged(mylocManager
					.getLastKnownLocation(LocationManager.NETWORK_PROVIDER));
		} catch (Exception e) {
			Log.e("Geolocalizacion",
					"Geolocalizacion. Error al obtener la ultima localizacion NETWORK_PROVIDER: "
							+ e.getMessage());
		}

		return mylocManager;
	}

}
