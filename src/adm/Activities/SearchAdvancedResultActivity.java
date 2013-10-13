/**
 * 
 */
package adm.Activities;

import adm.Repository.DataBaseManagement;
import adm.Repository.GlobalParameters;
import adm.Repository.ListViewSuggestionAdapter;
import adm.Repository.ViewHolder;
import adm.Search.Search;
import adm.Search.SearchResponse;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * @author
 */
public class SearchAdvancedResultActivity extends ListActivity {

	private String txtBusqueda;
	private String localizacion;
	private String tipo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.buscarname_result);

		Bundle b = getIntent().getExtras();

		if (b.get("txtBusqueda") != null)
			txtBusqueda = (String) b.get("txtBusqueda");
		else
			txtBusqueda = "";

		if (b.get("localizacion") != null)
			localizacion = (String) b.get("localizacion");
		else
			localizacion = "";

		if (b.get("tipo") != null)
			tipo = (String) b.get("tipo");
		else
			tipo = "";

		TextView actionBar = (TextView) findViewById(R.id.actionBarSearchAdvancedResult);
		actionBar.setText(R.string.searchAdvancedTitle);

	
		new AsyncTaskSearchName(this).execute(txtBusqueda, tipo, localizacion);
	}
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		
		ViewHolder holder = (ViewHolder) v.getTag();
		Intent i = new Intent(SearchAdvancedResultActivity.this,
				PlaceActivity.class);
		i.putExtra("idPlace", holder.getIdPlace());

		startActivity(i);

		if (holder.getIdPlace() != -1) {
			try {
				DataBaseManagement database = new DataBaseManagement(this);
				database.createSearch("juanito", holder.getTypePlace(),
						String.valueOf(holder.getIdPlace()));
				Log.i("AdvancedResult", "BD: " + holder.getTypePlace() + " "
						+ String.valueOf(holder.getIdPlace()));
				Log.i("LISTCLICK", "Has seleccionado el elemento "+ holder.getTypePlace() + " "
						+ String.valueOf(holder.getIdPlace()));
			} catch (Exception e) {
				Log.e("AdvancedResult", "Error en BD: " + e.getMessage());
			}
		}

		super.onListItemClick(l, v, position, id);
	}
	/**
	 * AsyncTaskSearchLocation
	 * 
	 * @author AndroIT
	 */
	private class AsyncTaskSearchName
			extends
				AsyncTask<String, SearchResponse, SearchResponse> {

		ProgressDialog dialog;
		private Context context;

		/**
		 * @param searchAdvancedResultActivity
		 */
		public AsyncTaskSearchName(Context cont) {
			this.context = cont;
		}

		@Override
		protected void onPreExecute() {
			try {
				dialog = new ProgressDialog(context);
				dialog.setTitle(R.string.cargando);
				dialog.setMessage(getString(R.string.obtInfoServer));
				dialog.setCancelable(false);
				dialog.show();
			} catch (final Exception e) {
				runOnUiThread(new Runnable() {
					public void run() {
						Toast.makeText(getApplicationContext(), e.getMessage(),
								3000).show();
						Log.e("ERROR", e.getStackTrace().toString());
					}
				});
			}
		}

		@Override
		protected SearchResponse doInBackground(String... strings) {

			Search sh = new Search(GlobalParameters.BASE_URL_JSON);
			try {
				SearchResponse sResponse = sh.name(strings[0],
						GlobalParameters.API_KEY, strings[1], strings[2]);

				publishProgress(sResponse);
			} catch (final Exception e) {
				runOnUiThread(new Runnable() {
					public void run() {
						Toast.makeText(getApplicationContext(),
								getResources().getString(R.string.connectionError), 3000).show();
						Log.e("ERROR", e.getStackTrace().toString());
					}
				});
			}

			return null;
		}

		@Override
		protected void onProgressUpdate(SearchResponse... responses) {
			
			ListViewSuggestionAdapter lvSuggestionAdapt = new ListViewSuggestionAdapter(
					getApplicationContext(), R.layout.listview_item_left,
					responses[0].getResult());
			setListAdapter(lvSuggestionAdapt);
		}

		@Override
		protected void onPostExecute(final SearchResponse result) {
			runOnUiThread(new Runnable() {
				public void run() {
					try {
						dialog.dismiss();
					} catch (Exception e) {
						Log.i("MI_ERROR", e.getMessage());
					}

				}
			});
		}
	}

	public void onActionBarHomeButtonClick(View v) {
		startActivity(new Intent(this, DashboardActivity.class));
	}
}
