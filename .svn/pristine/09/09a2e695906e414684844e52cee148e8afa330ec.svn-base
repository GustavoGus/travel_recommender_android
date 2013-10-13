/**
 * 
 */
package adm.Search;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import android.util.Log;

/**
 * @author AndroIT
 * 
 */
public class Search {
	private String logTag = "";
	private String url = "";

	/**
	 * @param _baseUrl
	 *            : Base Url
	 */
	public Search(String _baseUrl) {
		super();
		this.url = _baseUrl;
	}

	/**
	 * Search.location of Destinia API
	 * 
	 * @param _lat
	 * @param _lon
	 * @param _apiKey
	 * @param _place_type
	 * @param _enclosing_place
	 * @return A SearchResponse with the result. Null if not result.
	 * @throws Exception
	 */
	public SearchResponse location(Double _lat, Double _lon, String _apiKey,
			String _place_type, String _enclosing_place) throws Exception {
		logTag = "location";

		Log.i(logTag, "Inicio");

		/*
		 * Constructing URL
		 */
		String urlAux = "http://" + url;
		final List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("method", "search.location"));
		pairs.add(new BasicNameValuePair("lat", String.valueOf(_lat)));
		pairs.add(new BasicNameValuePair("lon", String.valueOf(_lon)));
		pairs.add(new BasicNameValuePair("api_key", _apiKey));
		if (!_place_type.equals(""))
			pairs.add(new BasicNameValuePair("place_type", _place_type));
		if (!_enclosing_place.equals(""))
			pairs.add(new BasicNameValuePair("enclosing_place",
					_enclosing_place));
		urlAux += "?" + URLEncodedUtils.format(pairs, "utf-8");

		/*
		 * Request call and waiting result.
		 */
		return requestSearch(urlAux);
	}

	/**
	 * search.name of Destinia API
	 * 
	 * @param _name
	 * @param _apiKey
	 * @param _place_type
	 * @param _enclosing_place
	 * @return A SearchResponse with the result. Null if not result.
	 * @throws Exception
	 */
	public SearchResponse name(String _name, String _apiKey,
			String _place_type, String _enclosing_place) throws Exception {
		logTag = "name";

		Log.i(logTag, "Inicio");

		/*
		 * Constructing URL
		 */
		String urlAux = "http://" + url;
		final List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("method", "search.name"));
		pairs.add(new BasicNameValuePair("name", _name));
		if (!_place_type.equals(""))
			pairs.add(new BasicNameValuePair("place_type", _place_type));
		if (!_enclosing_place.equals(""))
			pairs.add(new BasicNameValuePair("enclosing_place",
					_enclosing_place));
		pairs.add(new BasicNameValuePair("api_key", _apiKey));
		urlAux += "?" + URLEncodedUtils.format(pairs, "utf-8");
	
		/*
		 * Request call and waiting result.
		 */
		return requestSearch(urlAux);
	}

	/**
	 * search.related of Destinia API
	 * 
	 * @param _place_id
	 * @param _apiKey
	 * @param _place_type
	 * @param _enclosing_place
	 * @return A SearchResponse with the result. Null if not result.
	 * @throws Exception
	 */
	public SearchResponse related(String _place_id, String _apiKey,
			String _place_type, String _enclosing_place) throws Exception {
		logTag = "related";

		Log.i(logTag, "Inicio");

		/*
		 * Constructing URL
		 */
		String urlAux = "http://" + url;
		final List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("method", "search.related"));
		pairs.add(new BasicNameValuePair("id", _place_id));
		if (!_place_type.equals(""))
			pairs.add(new BasicNameValuePair("place_type", _place_type));
		if (!_enclosing_place.equals(""))
			pairs.add(new BasicNameValuePair("enclosing_place",
					_enclosing_place));
		pairs.add(new BasicNameValuePair("api_key", _apiKey));
		urlAux += "?" + URLEncodedUtils.format(pairs, "utf-8");

		/*
		 * Request call and waiting result.
		 */
		return requestSearch(urlAux);
	}

	/**
	 * search.my_location of Destinia API
	 * 
	 * @param _lat
	 * @param _lon
	 * @param _apiKey
	 * @return A SearchResponse with the result. Null if not result.
	 * @throws Exception
	 */
	public SearchMyLocationResponse my_location(Double _lat, Double _lon,
			String _apiKey) throws Exception {
		logTag = "my_location";

		Log.i(logTag, "Inicio");

		/*
		 * Constructing URL
		 */
		String urlAux = "http://" + url;
		final List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("method", "search.my_location"));
		pairs.add(new BasicNameValuePair("lat", String.valueOf(_lat)));
		pairs.add(new BasicNameValuePair("lon", String.valueOf(_lon)));
		pairs.add(new BasicNameValuePair("api_key", _apiKey));
		urlAux += "?" + URLEncodedUtils.format(pairs, "utf-8");

		/*
		 * Request call and waiting result.
		 */
		return requestSearchMyLocation(urlAux);
	}

	/**
	 * Execute the Request
	 * 
	 * @param _url
	 * @return Return the Search Response
	 * @throws Exception
	 */
	private SearchResponse requestSearch(String _url) throws Exception {
		SearchResponse sResponse = null;
		final HttpClient client = new DefaultHttpClient();
		final HttpGet request = new HttpGet(_url);
		request.setHeader("Accept", "application/json");

		Log.i(logTag, "Executing requestSearch");
		HttpResponse response = client.execute(request);
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			InputStream stream = entity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					stream));
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			stream.close();
			String responseString = sb.toString();

			GsonBuilder builder = new GsonBuilder();
			Gson gson = builder.create();
			JSONObject json = new JSONObject(responseString);
			sResponse = gson.fromJson(json.toString(), SearchResponse.class);
		}

		/* Return */
		Log.i(logTag, "Finish requestSearch");
		return sResponse;
	}

	/**
	 * Execute the Request for myLocation
	 * 
	 * @param _url
	 * @return Return the Search Response
	 * @throws Exception
	 */
	private SearchMyLocationResponse requestSearchMyLocation(String _url)
			throws Exception {
		SearchMyLocationResponse sResponse = null;
		final HttpClient client = new DefaultHttpClient();
		final HttpGet request = new HttpGet(_url);
		request.setHeader("Accept", "application/json");

		Log.i(logTag, "Executing requestSearchMyLocation");
		HttpResponse response = client.execute(request);
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			InputStream stream = entity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					stream));
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			stream.close();
			String responseString = sb.toString();

			GsonBuilder builder = new GsonBuilder();
			Gson gson = builder.create();
			JSONObject json = new JSONObject(responseString);
			sResponse = gson.fromJson(json.toString(),
					SearchMyLocationResponse.class);
		}

		/* Return */
		Log.i(logTag, "Finish request my_location");
		return sResponse;
	}
}
