/**
 * 
 */
package adm.Activities;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import adm.Comment.CommentItem;
import adm.Comment.CommentResponse;
import adm.Image.ImageItem;
import adm.Image.ImageResponse;
import adm.Place.Place;
import adm.Place.PlaceItem;
import adm.Place.PlaceResponse;
import adm.Repository.GlobalParameters;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author AndroIT
 * 
 */
public class PlaceActivity extends Activity {
	private int idPlace;
	private String placeName;
	private float lat;
	private float lon;
	private static final int SUBMIT_PHOTO = 100;
	private static final int SUBMIT_COMMENT = 200;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.detalle_ciudad);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			idPlace = extras.getInt("idPlace");
		} else {
			this.finish();
		}
		
		TextView actionBar = (TextView) findViewById(R.id.actionBarPlace);
		actionBar.setText(R.string.placeDetail);
		
		
		AsyncTaskPlace task = new AsyncTaskPlace(this);
		task.execute(new Integer(idPlace));
	}
    public void onClick_viewPlaceMap(View v) {
    	Intent intent = new Intent(this, PlaceMapActivity.class);
		intent.putExtra("idPlace", idPlace);
		intent.putExtra("placeName", placeName);
		intent.putExtra("lat", lat);
		intent.putExtra("lon", lon);
		startActivity(intent);
    }
    
    public void onClick_addComment(View v) {
    	Intent intent = new Intent(this, NewComment.class);
		intent.putExtra("idPlace", idPlace);
		intent.putExtra("placeName", placeName);
		
		startActivityForResult(intent, SUBMIT_COMMENT);
    }
    
    public void onClick_addPhoto(View v) {
    	Intent intent = new Intent(this, TakePhoto.class);
		intent.putExtra("idPlace", idPlace);
		intent.putExtra("placeName", placeName);
		
		startActivityForResult(intent, SUBMIT_PHOTO);
    }
    
	/**
	 * AsyncTaskSearchLocation
	 * 
	 * @author AndroIT
	 */
	private class AsyncTaskPlace extends
			AsyncTask<Integer, PlaceResponse, Void> {

		
		ProgressDialog dialog;
		final Context context;
		private Place place;

		public AsyncTaskPlace(Context cont) {
			this.context = cont;
			try{
			this.dialog = ProgressDialog.show(context,
					context.getString(R.string.cargando),
					context.getString(R.string.obtInfoServer), true);
			}catch (Exception e) {
				Log.e("PLACE","Error on show dialog");
			}
		}

		@Override
		protected Void doInBackground(Integer... idPlaces) {
			try{
			dialog.setCancelable(false);
			dialog.show();
			}catch (Exception e) {
				Log.e("PLACE","Error on show dialog");
			}

			place = new Place(GlobalParameters.BASE_URL_JSON);
			ArrayList<Integer> ids = new ArrayList<Integer>();
			ids.add(idPlaces[0]);
			try {
				PlaceResponse resp = place
						.detail(ids, GlobalParameters.API_KEY);
				if (resp.getResult() != null) {
					publishProgress(resp);
				}

			} catch (Exception e) {
				Toast.makeText(getApplicationContext(), e.toString(), 3000);
			}

			return null;
		}

		@Override
		protected void onProgressUpdate(PlaceResponse... responses) {
			PlaceResponse resp = responses[0];

			// MUESTRA DATOS DEL LUGAR
			ArrayList<PlaceItem> items = resp.getResult();
			PlaceItem item = items.get(0);
			String locationDescription;

			lat = item.getLat();
			lon = item.getLon();
			placeName = item.getName();
			TextView tvName = (TextView) findViewById(R.id.tvPlaceName);
			TextView tvLocation = (TextView) findViewById(R.id.tvPlaceLocation);
			TextView tvDesc = (TextView) findViewById(R.id.tvPlaceDescription);
			tvName.setText(item.getName());
			if (item.getPlace_Type().equals("CITY"))
				locationDescription = item.getHierarchy().getRegion().getName()
						+ ", " + item.getHierarchy().getCountry().getName();
			else
				locationDescription = item.getHierarchy().getCity().getName()
						+ ", " + item.getHierarchy().getRegion().getName()
						+ ", " + item.getHierarchy().getCountry().getName();
			
			tvLocation.setText(locationDescription);
			tvDesc.setText(Html.fromHtml(item.getDesc()),
					TextView.BufferType.SPANNABLE);

			TextView tv;
			tv = (TextView) findViewById(R.id.tvRatesDescription);
			tv.setVisibility(View.VISIBLE);
			tv = (TextView) findViewById(R.id.tvCheckinsDescription);
			tv.setVisibility(View.VISIBLE);

			tv = (TextView) findViewById(R.id.tvRatesValue);
			tv.setText(Float.toString(item.getRate()));
			tv = (TextView) findViewById(R.id.tvCheckinsValue);
			tv.setText(Integer.toString(item.getCheck_In()) + " personas");

			// PASA A RECORRER IMAGENES
			ImageResponse imgresp = null;
			try {
				imgresp = place.pictures(item.getId(), "LARGE", 0, 10,
						GlobalParameters.API_KEY);
			} catch (Exception e) {
				Toast.makeText(getApplicationContext(), e.toString(), 3000);
			}
			if (imgresp.getResult() != null) {			
				final ArrayList<ImageItem> images = imgresp.getResult().getPics();
				Gallery gallery = (Gallery) findViewById(R.id.glPlaceImages);
				gallery.setAdapter(new ImageAdapter(this.context, images));
				
				if(images.size() > 0)
				{
					tv = (TextView) findViewById(R.id.tvGalleryDescription);
					tv.setVisibility(View.VISIBLE);
				}
				
				gallery.setOnItemClickListener(new OnItemClickListener() {
					public void onItemClick(@SuppressWarnings("rawtypes") AdapterView parent, View v,
							int position, long id) {
						ImageItem im = images.get(position);
						
						Dialog dialog = new Dialog(PlaceActivity.this);

						dialog.setContentView(R.layout.image_dialog);
						dialog.setTitle(im.getUser_Name() + ", " + im.getDate());

						ImageView dialogImage = (ImageView) dialog.findViewById(R.id.imageBig);
						ImageView imageClicked = (ImageView) v;
						
						BitmapDrawable drawable = (BitmapDrawable) imageClicked.getDrawable();
						Bitmap bitmap = drawable.getBitmap();

						dialogImage.setImageBitmap(bitmap);
						dialog.show();
					}
				});

				int num = imgresp.getResult().size();
				if (num % 2 == 1)
					num = num + 1;
				gallery.setSelection((num / 2) - 1);
			}

			// PASA A RECORRER COMENTARIOS
			CommentResponse comresp = null;
			try {
				comresp = place.comments(item.getId(), 0, 10,
						GlobalParameters.API_KEY);
			} catch (Exception e) {
				Toast.makeText(getApplicationContext(), e.toString(), 3000);
			}
			if (comresp.getResult().getComments() != null) {
				ArrayList<CommentItem> comments = comresp.getResult()
						.getComments();
				LinearLayout parentLayout = (LinearLayout) findViewById(R.id.layoutPlaceComments);

				if(comments.size() > 0)
				{
					tv = (TextView) findViewById(R.id.tvCommentsDescription);
					tv.setVisibility(View.VISIBLE);
				}
				
				CommentItem com;
				for (int i = 0; i < comments.size(); i++) {
					LinearLayout commentLayout = new LinearLayout(this.context);
					commentLayout.setOrientation(LinearLayout.VERTICAL);

					TextView tvAuthor = new TextView(this.context);
					tvAuthor.setTextColor(Color.parseColor("#f96d06"));
					tvAuthor.setTypeface(null, Typeface.BOLD);
					TextView tvDate = new TextView(this.context);
					tvDate.setTextColor(Color.parseColor("#39302A"));
					TextView tvComment = new TextView(this.context);
					tvComment.setTextColor(Color.parseColor("#39302A"));

					if ((i + 1) % 2 == 1)
						commentLayout
								.setBackgroundResource(R.drawable.bocadillo_white_left);
					else
						commentLayout
								.setBackgroundResource(R.drawable.bocadillo_white_right);

					com = comments.get(i);
					tvAuthor.setText(com.getUser_Name());
					tvDate.setText(" " + com.getDate());
					tvComment.setText(com.getComment());
					LinearLayout infoLayout = new LinearLayout(this.context);
					infoLayout.addView(tvAuthor);
					infoLayout.addView(tvDate);
					commentLayout.addView(infoLayout);
					commentLayout.addView(tvComment);
					parentLayout.addView(commentLayout);
				}
			}
		}

		@Override
		protected void onPostExecute(Void result) {
			Button btn;
			btn = (Button) findViewById(R.id.btn_viewPlaceMap);
			btn.setVisibility(View.VISIBLE);
			btn = (Button) findViewById(R.id.btn_addPhoto);
			btn.setVisibility(View.VISIBLE);
			btn = (Button) findViewById(R.id.btn_addComment);
			btn.setVisibility(View.VISIBLE);
			try{
			runOnUiThread(new Runnable() {
				public void run() {
					dialog.dismiss();
				}
			});
			}catch (Exception e) {
				Log.e("PLACE","Error al cerrar dialog");
			}
		}
	}

	public class ImageAdapter extends BaseAdapter {
		private Context mContext;
		private ArrayList<ImageItem> images;

		public ImageAdapter(Context c, ArrayList<ImageItem> imgs) {
			mContext = c;
			images = imgs;
		}

		public int getCount() {
			return images.size();
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView i = new ImageView(mContext);

			try {
				/* Open a new URL and get the InputStream to load data from it. */
				URL aURL = new URL(images.get(position).getUrl());
				URLConnection conn = aURL.openConnection();
				conn.connect();
				InputStream is = conn.getInputStream();
				BufferedInputStream bis = new BufferedInputStream(is);
				/* Decode url-data to a bitmap. */
				Bitmap bm = BitmapFactory.decodeStream(bis);
				bis.close();
				is.close();
				/* Apply the Bitmap to the ImageView that will be returned. */
				i.setImageBitmap(bm);
			} catch (IOException e) {
				i.setImageResource(R.drawable.noimage);
				Log.e("DEBUGTAG", "Remote Image Exception", e);
			}

			i.setScaleType(ImageView.ScaleType.FIT_CENTER);
			i.setLayoutParams(new Gallery.LayoutParams(150, 150));
			return i;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == RESULT_OK)
		{
			switch(requestCode)
			{
			case SUBMIT_COMMENT:
			case SUBMIT_PHOTO:
				Intent reloadPlaceActivity = new Intent(PlaceActivity.this, PlaceActivity.class);
				reloadPlaceActivity.putExtra("idPlace", idPlace);
				startActivity(reloadPlaceActivity);
				this.finish();
			}
		}
	}
	
	public void onActionBarHomeButtonClick(View v) 
	{
		startActivity(new Intent(this, DashboardActivity.class));
	}
}
