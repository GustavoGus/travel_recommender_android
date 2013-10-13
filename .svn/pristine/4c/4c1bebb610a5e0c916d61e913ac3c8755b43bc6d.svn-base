package adm.Submit;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

/**
 * @author AndroIT
 * 
 */
public class Submit {
	private String logTag = "";
	private String url = "";

	/**
	 * @param _baseUrl
	 *            : Base Url
	 */
	public Submit(String _baseUrl) {
		super();
		this.url = _baseUrl;
	}

    /**
     * Submit.Checkin of Destinia API
     * 
     * @param _id
     * @param _rate
     * @param _apiKey
     * @param _auth_token
     * @return SimpleResponse
     * @throws Exception
     */
    public SimpleResponse checkin(String _id, Double _rate, String _apiKey,
                    String _auth_token) throws Exception {
            logTag = "checkin";

            Log.i(logTag, "Inicio");

            /*
             * Constructing URL
             */
            String urlAux = "http://" + url;
            final List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("method", "submit.checkin"));
            pairs.add(new BasicNameValuePair("id", _id));
            pairs.add(new BasicNameValuePair("rate", String.valueOf(_rate)));
            pairs.add(new BasicNameValuePair("api_key", _apiKey));
            pairs.add(new BasicNameValuePair("auth_token", _auth_token));
             urlAux += "?" + URLEncodedUtils.format(pairs, "utf-8");
            return requestCheckin(urlAux, pairs);
    }
    
    
    /**
     * Submit.Checkin of Destinia API, no rate.
     * 
     * @param _id
     * @param _apiKey
     * @param _auth_token
     * @return
     * @throws Exception
     */
    public SimpleResponse checkin(String _id, String _apiKey,
                    String _auth_token) throws Exception {
            logTag = "checkin";

            Log.i(logTag, "Inicio");

            /*
             * Constructing URL
             */
            String urlAux = "http://" + url;
            final List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("method", "submit.checkin"));
            pairs.add(new BasicNameValuePair("id", _id));
            pairs.add(new BasicNameValuePair("api_key", _apiKey));
            pairs.add(new BasicNameValuePair("auth_token", _auth_token));
            urlAux += "?" + URLEncodedUtils.format(pairs, "utf-8");
            return requestCheckin(urlAux, pairs);
    }

    private SimpleResponse requestCheckin(String _url, List<NameValuePair> pairs)
                    throws Exception {
            SimpleResponse sResponse = null;
            final HttpClient client = new DefaultHttpClient();
            final HttpGet request = new HttpGet(_url);
            request.setHeader("Accept", "application/json");

            Log.i(logTag, "Executing requestPlace");
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
                    sResponse = gson.fromJson(json.toString(), SimpleResponse.class);
            }

            /* Return */
            Log.i(logTag, "Finish requestCheckin");
            return sResponse;

    }


	/**
	 * Submit.Place of Destinia API
	 * 
	 * @param _name
	 * @param _place_type
	 * @param _place_parent
	 * @param _lat
	 * @param _lon
	 * @param _desc
	 * @param _apiKey
	 * @param _auth_token
	 * @return sResponse
	 * @throws Exception
	 */
	public SubmitResponse place(String _name, String _place_type,
			String _place_parent, Double _lat, Double _lon, String _desc,
			String _apiKey, String _auth_token) throws Exception {
		logTag = "place";

		Log.i(logTag, "Inicio");

		/*
		 * Constructing URL
		 */
		String urlAux = "http://" + url;
		final List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("method", "submit.place"));
		pairs.add(new BasicNameValuePair("name", _name));
		pairs.add(new BasicNameValuePair("place_parent", _place_parent));
		pairs.add(new BasicNameValuePair("place_type", _place_type));
		pairs.add(new BasicNameValuePair("lat", String.valueOf(_lat)));
		pairs.add(new BasicNameValuePair("lon", String.valueOf(_lon)));
		pairs.add(new BasicNameValuePair("desc", _desc));
		pairs.add(new BasicNameValuePair("api_key", _apiKey));
		pairs.add(new BasicNameValuePair("auth_token", _auth_token));
		urlAux += "?" + URLEncodedUtils.format(pairs, "utf-8");
		return requestPlace(urlAux, pairs);
	}

	private SubmitResponse requestPlace(String _url, List<NameValuePair> pairs)
			throws Exception {
		SubmitResponse sResponse = null;
		final HttpClient client = new DefaultHttpClient();
		final HttpGet request = new HttpGet(_url);
		request.setHeader("Accept", "application/json");

		Log.i(logTag, "Executing requestPlace");
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
			sResponse = gson.fromJson(json.toString(), SubmitResponse.class);
		}

		/* Return */
		Log.i(logTag, "Finish requestPlace");
		return sResponse;

	}

	/**
	 * Submit.Comment of Destinia API
	 * 
	 * @param _id
	 * @param _comment
	 * @param _rate
	 * @param _apiKey
	 * @param _auth_token
	 * @return id
	 * @throws Exception
	 */
	public SimpleResponse comment(String _id, String _comment, Integer _rate,
			String _apiKey, String _auth_token) throws Exception {
		logTag = "comment";

		Log.i(logTag, "Inicio");

		/*
		 * Constructing URL
		 */
		String urlAux = "http://" + url;
		final List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("method", "submit.comment"));
		pairs.add(new BasicNameValuePair("id", _id));
		pairs.add(new BasicNameValuePair("comment", _comment));
		pairs.add(new BasicNameValuePair("rate", String.valueOf(_rate)));
		pairs.add(new BasicNameValuePair("api_key", _apiKey));
		pairs.add(new BasicNameValuePair("auth_token", _auth_token));
		urlAux += "?" + URLEncodedUtils.format(pairs, "utf-8");
		return requestComment(urlAux, pairs);
	}

	private SimpleResponse requestComment(String _url, List<NameValuePair> pairs)
			throws Exception {
		SimpleResponse sResponse = null;
		final HttpClient client = new DefaultHttpClient();
		final HttpGet request = new HttpGet(_url);
		request.setHeader("Accept", "application/json");

		Log.i(logTag, "Executing requestComment");
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
			sResponse = gson.fromJson(json.toString(), SimpleResponse.class);
		}

		/* Return */
		Log.i(logTag, "Finish requestComment");
		return sResponse;

	}

	/**
	 * Submit.Picture of Destinia API
	 * 
	 * @param _id
	 * @param _title
	 * @param _description
	 * @param _image_path
	 *            ruta de la imagen en el dispositivo
	 * @param _apiKey
	 * @param _auth_token
	 * @return id
	 * @throws Exception
	 */
	public SimpleResponse picture(String _id, String _title,
			String _description, String _image_path, String _apiKey,
			String _auth_token) throws Exception {
		logTag = "picture";

		Log.i(logTag, "Inicio");

		/*
		 * Constructing URL
		 */
		String urlAux = "http://" + url;
		final List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("method", "submit.picture"));
		pairs.add(new BasicNameValuePair("id", _id));
		pairs.add(new BasicNameValuePair("title", _title));
		pairs.add(new BasicNameValuePair("description", _description));
		pairs.add(new BasicNameValuePair("api_key", _apiKey));
		pairs.add(new BasicNameValuePair("auth_token", _auth_token));
		urlAux += "?" + URLEncodedUtils.format(pairs, "utf-8");
		return requestPicture(urlAux, _image_path);
	}

	private SimpleResponse requestPicture(String _url, String image_path)
			throws Exception {
		SimpleResponse sResponse = null;
		final HttpClient client = new DefaultHttpClient();
		final HttpPost request = new HttpPost(_url);
		request.setHeader("Accept", "application/json");

		
		Bitmap original_bm, bm;
		original_bm = BitmapFactory.decodeFile(image_path);
		
		int actWidth, actHeight, newWidth, newHeight;
		actWidth = original_bm.getWidth();
		actHeight = original_bm.getHeight();
		
		if(actWidth < 640)
		{
			newWidth = actWidth;
			newHeight = actHeight;
		}
		else
		{
			newWidth = 640;
			
			double percent = (newWidth*1.0)/actWidth;
			newHeight = (int) (percent*actHeight);
		}
		
		
		bm = Bitmap.createScaledBitmap(original_bm, newWidth, newHeight, true);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.JPEG, 50, bos);
		byte[] b = bos.toByteArray();
		
		String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
		
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();  
        nameValuePairs.add(new BasicNameValuePair("picture_stream", encodedImage));               
        request.setEntity(new UrlEncodedFormEntity(nameValuePairs)); 

		Log.i(logTag, "Executing requestPicture");
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
			sResponse = gson.fromJson(json.toString(), SimpleResponse.class);
		}

		/* Return */
		Log.i(logTag, "Finish requestPicture");
		return sResponse;

	}
}
