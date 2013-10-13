/**
 * 
 */
package adm.Activities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import adm.Place.Place;
import adm.Place.PlaceItem;
import adm.Place.PlaceResponse;
import adm.Repository.DataBaseManagement;
import adm.Repository.GlobalParameters;
import adm.Search.Search;
import adm.Search.SearchMyLocationItem;
import adm.Search.SearchMyLocationResponse;
import adm.Search.SearchMyLocationResult;
import adm.Submit.Checkin;
import adm.Submit.SimpleResponse;
import adm.Twitter.TwitterJ;
import adm.User.User;
import adm.User.UserLoginResponse;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.Toast;
/**
 * @author AndroIT
 * 
 */
public class CheckinDisfrutarActivity extends Activity {
	private List<SearchMyLocationResult> result = new ArrayList<SearchMyLocationResult>();
	private ArrayAdapter<CharSequence> spinnerPlacesAdapter = null;
	private ArrayList<String> spinnerPlacesId = new ArrayList<String>();
	private ArrayList<SearchMyLocationItem> places = new ArrayList<SearchMyLocationItem>();
	private SearchMyLocationItem city = null;
	
	private boolean noCity = false;
	private boolean noPlace = false;
	private UserLoginResponse login;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
	
		setContentView(R.layout.checkin_layout);
		
		TextView actionBar = (TextView) findViewById(R.id.actionBarCheckin);
		actionBar.setText(R.string.Checkin);
		
		setCheckListener();
		setRadioButtonCheckListener();
		searchMyLocation();
	}

	
	private void setCheckListener(){
		CheckBox repeatChkBx = ( CheckBox ) findViewById( R.id.cb_checkin );
		repeatChkBx.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
		    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
		    {		        	
		    	RatingBar rtb_checkin = (RatingBar)findViewById(R.id.rtb_checkin);

		        if ( isChecked )
		        {
		        	rtb_checkin.setVisibility(View.VISIBLE);
		        }else{
		        	rtb_checkin.setVisibility(View.GONE);
		        }

		    }
		});
	}
	
	private void setRadioButtonCheckListener(){
		RadioButton rdbtn = (RadioButton) findViewById(R.id.rdbtn_Place);
		
		rdbtn.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

				Spinner spnr = (Spinner) findViewById(R.id.spnr_places);

				if(isChecked){
					spnr.setVisibility(View.VISIBLE);
				}else{
					spnr.setVisibility(View.GONE);
				}
			}
		});
	}
	
	private void searchMyLocation(){
		Search s = new Search(GlobalParameters.BASE_URL_JSON);
		SearchMyLocationResponse response = null;
		try {
			response = s.my_location(GlobalParameters.my_latitude, GlobalParameters.my_longitude, GlobalParameters.API_KEY);
			result =  response.getResult();
			Spinner spnr = (Spinner) findViewById(R.id.spnr_places);
			spinnerPlacesAdapter= new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item);
			
			noPlace = true;
			noCity = true;
			for(int i = 0; i < result.size(); i++){
				if(result.get(i).getPlace() != null){
					noPlace = false;
					spinnerPlacesAdapter.add(result.get(i).getPlace().getName());
					spinnerPlacesId.add(result.get(i).getPlace().getId());
					places.add(result.get(i).getPlace());
				}
				if(result.get(i).getCity() != null){
					noCity = false;
					city = result.get(i).getCity();
				}

			}
			if(noPlace){
				spnr = (Spinner) findViewById(R.id.spnr_places);

				spinnerPlacesAdapter= new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item);
				spinnerPlacesAdapter.add("---------");
			}
			
			spinnerPlacesAdapter.notifyDataSetChanged();
			spnr.setAdapter(spinnerPlacesAdapter);

		} catch (Exception e) {

			Log.d("TAG_BON_VOYAGE",e.toString());
		}
	}
	private void doCheckin(){
		RadioButton rdbtnPlace = (RadioButton) findViewById(R.id.rdbtn_Place);
		RadioButton rdbtnCity = (RadioButton) findViewById(R.id.rdbtn_City);
		CheckBox rate = ( CheckBox ) findViewById( R.id.cb_checkin );
		Spinner spnr = (Spinner) findViewById(R.id.spnr_places);

		String placeName = "";
		Checkin doCheckin = new Checkin();
		
		DataBaseManagement database = new DataBaseManagement(this);
		try {
			if(!database.CheckIfDBExist())
				database.CreateDataBase();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		User usuario = new User(GlobalParameters.BASE_URL_JSON, this);
		
		//Si el usuario ha expirado, lo refrescamos.
		if(usuario.hasAuthKey()){
			if(usuario.isAuthExpired(new Date())){
				String sname = usuario.getUserName();
				String spass = usuario.getPassword();

				if (!sname.equals("") && !spass.equals("")) {

					Handler h = new Handler();
					loadingTask task = new loadingTask(this);
					task.setCancel(false);
					h.post(task);
					
					login = usuario.login(sname, spass, GlobalParameters.API_KEY);
					database.createUser(sname, spass);
					usuario.setCredentials(sname, login.getResult(), spass);
					
					task.setCancel(true);
					
				} 
			}
		}
		try{
		//Si queremos añadir puntuación...
			SimpleResponse s = null;
		if(rate.isChecked()){
			RatingBar rtbar = (RatingBar) findViewById(R.id.rtb_checkin);
			//Si queremos hacer checkin sobre la ciudad...
			if(rdbtnCity.isChecked() && !noCity){
				
				s =	doCheckin.make_checkin(getApplicationContext(), city.getId(), new Double(rtbar.getRating()));

				
				if(usuario.hasAuthKey()){
					placeName  = city.getName();
					if(!database.ExistsCoordsInfo(usuario.getUserName(), city.getId())){

						Place p = new Place(GlobalParameters.BASE_URL_JSON);
						ArrayList<Integer> place = new ArrayList<Integer>();
						place.add(new Integer(city.getId()));
						
						PlaceResponse response = p.detail(place, GlobalParameters.API_KEY);
						ArrayList<PlaceItem> placeItems = response.getResult();
			
						if(placeItems.size() > 0){
							database.createCoordsInfo(usuario.getUserName(), city.getName(), city.getId(),placeItems.get(0).getLon(), placeItems.get(0).getLon(), city.getName());
						}
					}
				}
			}
			
			//Si queremos hacer checkin sobre el lugar...
			if(rdbtnPlace.isChecked() && !noPlace){
				
				s= doCheckin.make_checkin(this, spinnerPlacesId.get(spnr.getSelectedItemPosition()), new Double(rtbar.getRating()));
				if(usuario.hasAuthKey()){
					placeName = places.get(spnr.getSelectedItemPosition()).getName();
					if(!database.ExistsCoordsInfo(usuario.getUserName(), 
							spinnerPlacesId.get(spnr.getSelectedItemPosition()).toString())){
						
						Place p = new Place(GlobalParameters.BASE_URL_JSON);
						ArrayList<Integer> place = new ArrayList<Integer>();
						place.add(new Integer(places.get(spnr.getSelectedItemPosition()).getId()));
						
						PlaceResponse response = p.detail(place, GlobalParameters.API_KEY);
						ArrayList<PlaceItem> placeItems = response.getResult();
						
						if(placeItems.size() > 0){
							database.createCoordsInfo(usuario.getUserName(), places.get(spnr.getSelectedItemPosition()).getName(),places.get(spnr.getSelectedItemPosition()).getId(), placeItems.get(0).getLon(), placeItems.get(0).getLat(), places.get(spnr.getSelectedItemPosition()).getName());		
						}
					
					}
				}
	
			}
		}else{
			//Si queremos hacer checkin sobre la ciudad...
			if(rdbtnCity.isChecked() && !noCity){
				
				s= doCheckin.make_checkin(getApplicationContext(), city.getId());
				if(usuario.hasAuthKey()){
					placeName  = city.getName();

					if(!database.ExistsCoordsInfo(usuario.getUserName(), city.getId())){
						Place p = new Place(GlobalParameters.BASE_URL_JSON);
						ArrayList<Integer> place = new ArrayList<Integer>();
						place.add(new Integer(city.getId()));
						
						PlaceResponse response = p.detail(place, GlobalParameters.API_KEY);
						ArrayList<PlaceItem> placeItems = response.getResult();
						
						if(placeItems.size() > 0){
							database.createCoordsInfo(usuario.getUserName(), city.getName(), city.getId(),placeItems.get(0).getLon(), placeItems.get(0).getLat(), city.getName());		
						}
					
					}
				}
	
			}
			
			//Si queremos hacer checkin sobre el lugar...
			if(rdbtnPlace.isChecked() && !noPlace){
				
				s= doCheckin.make_checkin(this, spinnerPlacesId.get(spnr.getSelectedItemPosition()));
				
				if(usuario.hasAuthKey()){
					placeName = places.get(spnr.getSelectedItemPosition()).getName();
					if(!database.ExistsCoordsInfo(usuario.getUserName(), 
					spinnerPlacesId.get(spnr.getSelectedItemPosition()).toString())){
						
						Place p = new Place(GlobalParameters.BASE_URL_JSON);
						ArrayList<Integer> place = new ArrayList<Integer>();
						place.add(new Integer(places.get(spnr.getSelectedItemPosition()).getId()));
						
						PlaceResponse response = p.detail(place, GlobalParameters.API_KEY);
						ArrayList<PlaceItem> placeItems = response.getResult();
						
						if(placeItems.size() > 0){
							database.createCoordsInfo(usuario.getUserName(), spinnerPlacesAdapter.getItem(spnr.getSelectedItemPosition()).toString(), spinnerPlacesId.get(spnr.getSelectedItemPosition()),placeItems.get(0).getLon(), placeItems.get(0).getLat(), city.getName());
	
						}
						
					}
				}
			}
		}
		

			if(s.getStatus() == 3){
				Toast t = Toast.makeText(this, getResources().getString(R.string.checkin_repeat), Toast.LENGTH_SHORT);
				t.show();
			}else if(s.getStatus() == 0){
				Toast t = Toast.makeText(this, getResources().getString(R.string.checkin_ok), Toast.LENGTH_SHORT);
				t.show();
			}else{
				Toast t = Toast.makeText(this, getResources().getString(R.string.checkin_error), Toast.LENGTH_SHORT);
				t.show();
	
			}
			
			//Publicamos un tweet
			TwitterJ twitter = new TwitterJ(getApplicationContext());
			
			//Si el usuario ha expirado, lo refrescamos.
			if(usuario.hasAuthKey()){
				if(usuario.isAuthExpired(new Date())){
					String sname = usuario.getUserName();
					String spass = usuario.getPassword();

					if (!sname.equals("") && !spass.equals("")) {

						Handler h = new Handler();
						loadingTask task = new loadingTask(this);
						task.setCancel(false);
						h.post(task);
						
						login = usuario.login(sname, spass, GlobalParameters.API_KEY);
						database.createUser(sname, spass);
					    usuario.setCredentials(sname, login.getResult(), spass);	
						
						task.setCancel(true);
						
					} 
				}
			}
			if (twitter.hasAccessToken()) {
				// Send Tweet with the info
				twitter.newTweet("Estoy en  "
						+ placeName + " "
						+ GlobalParameters.TWITTER_HASHTAG);
			}
				
		}catch(Exception e){
			Log.d("TAG_BON_VOYAGE", e.toString());
		}

	}
	
	public void checkinOnClick(View v){
		doCheckin();
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
				// TODO Auto-generated method stub
				dialog.setCancelable(true);
				dialog.show();
				int i = 0;
				while(!cancel){
					try {
						dialog.setProgress(i++);
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				dialog.dismiss();
			}
		}
	
}
