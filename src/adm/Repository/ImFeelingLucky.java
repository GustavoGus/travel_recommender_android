package adm.Repository;

import java.util.Random;
import adm.Search.Search;
import adm.Search.SearchMyLocationResponse;
import android.util.Log;

/**
 * Clase encargada de implementar la funcionalidad: "Voy a tener suerte".
 * Realiza una busqueda Random de latitud y longitud, devuelve informacion sobre
 * esos sitios.
 * 
 * @author Gustavo
 */
public class ImFeelingLucky {
	private String logTag = "ImFeelingLucky";
	private SearchMyLocationResponse smlResponse;

	/**
	 * Constructor "Voy A tener Suerte"
	 * 
	 * @throws Exception
	 */
	public ImFeelingLucky() {
		smlResponse= new SearchMyLocationResponse();
	}

	/**
	 * Execute the request
	 * 
	 * @throws Exception
	 */
	public void execute() throws Exception {
		Log.i(logTag, "Execute");
		/*
		 * Randomized latitude pattern : Min + (int)(Math.random() * ((Max -
		 * Min) + 1))
		 */
		Random rnd = new Random();
		rnd.setSeed(System.currentTimeMillis());
		double latRnd = GlobalParameters.PROVINCIA_VAL_SOUTH
				+ (rnd.nextDouble() * ((GlobalParameters.PROVINCIA_VAL_NORTH - GlobalParameters.PROVINCIA_VAL_SOUTH) + 1));
		double lonRnd = GlobalParameters.PROVINCIA_VAL_WEST
				+ (rnd.nextDouble() * ((GlobalParameters.PROVINCIA_VAL_EAST - GlobalParameters.PROVINCIA_VAL_WEST) + 1));

		// Instance my object Search with a URL
		Search s = new Search(GlobalParameters.BASE_URL_JSON);
		// Call my_location
		smlResponse = s.my_location(latRnd, lonRnd, GlobalParameters.API_KEY);
	}

	/**
	 * @return Objeto SearchMyLocationResponse con la respuesta a la consulta
	 *         creada
	 */
	public SearchMyLocationResponse getResponse() {
		return smlResponse;
	}

	// /**
	// * @author AndroIT AsyncTaskSearchLocation
	// */
	// private class AsyncTaskSearchLocation extends AsyncTask<String, Boolean,
	// SearchResponse> {
	// SearchResponse sResponse = null;
	// @Override
	// protected SearchResponse doInBackground(final String... strings) {
	// //Instance my object Search with a URL
	// Search s= new Search(strings[0]);
	// //Call my_location
	// try
	// {
	// s.my_location(Double.parseDouble(strings[1]),Double.parseDouble(strings[2]),
	// strings[3]);
	// publishProgress(true);
	// }
	// catch (Exception e) {
	// publishProgress(false);
	// }
	//
	// return sResponse;
	// }
	//
	// @Override
	// protected void onProgressUpdate(Boolean... responses) {
	// //TODO:
	// //Tengo si error. ¿Qué hacer?
	// }
	//
	// @Override
	// protected void onPostExecute(final SearchResponse result) {
	// }
	//
	// @Override
	// protected void onPreExecute() {
	// }
	// }
}
