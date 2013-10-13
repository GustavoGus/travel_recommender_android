/**
 * 
 */
package adm.Activities;

import adm.Repository.DataBaseManagement;
import adm.Repository.ListViewSuggestionAdapter;
import adm.Repository.Suggestion;
import adm.Repository.ViewHolder;
import adm.Search.SearchResponse;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.Toast;

/**
 * @author Gustavo
 * 
 */
public class SearchSuggestionActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.buscar_listview);

		AsyncTaskSuggestion task = new AsyncTaskSuggestion();
		task.execute();

	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		ViewHolder holder = (ViewHolder) v.getTag();
		Intent i=new Intent(this,PlaceActivity.class);
		i.putExtra("idPlace", holder.getIdPlace());
		startActivity(i);
		
		if (holder.getIdPlace() != -1) {
			try {
				DataBaseManagement database = new DataBaseManagement(this);
				database.createSearch("juanito", holder.getTypePlace(),
						String.valueOf(holder.getIdPlace()));
				Log.i("Suggestion", "BD: " + holder.getTypePlace() + " "
						+ String.valueOf(holder.getIdPlace()));
				Log.i("LISTCLICK", "Has seleccionado el elemento "+ holder.getTypePlace() + " "
						+ String.valueOf(holder.getIdPlace()));
			} catch (Exception e) {
				Log.e("Suggestion", "Error en BD: " + e.getMessage());
			}
		}
		
		super.onListItemClick(l, v, position, id);
	}

	/**
	 * AsyncTaskSearchLocation
	 * 
	 * @author AndroIT
	 */
	private class AsyncTaskSuggestion
			extends
				AsyncTask<Void, SearchResponse, Void> {

	
		final ProgressDialog dialog = ProgressDialog.show(getParent(),
				getParent().getString(R.string.cargando), getParent()
						.getString(R.string.obtInfoServer), true);

		@Override
		protected Void doInBackground(Void... params) {
			SearchResponse sResponse = new SearchResponse();

	
			dialog.setCancelable(false);
			dialog.show();

			Suggestion oSuggest = new Suggestion();

			try {
				oSuggest.execute(getApplicationContext());
	
				sResponse = oSuggest.getResponse();

				publishProgress(sResponse);

			} catch (final Exception e) {
				runOnUiThread(new Runnable() {
					public void run() {
						Toast.makeText(getApplicationContext(), e.getMessage(),
								3000).show();
						Log.e("ERROR", e.getStackTrace().toString());
					}
				});
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			runOnUiThread(new Runnable() {
				public void run() {
					dialog.dismiss();
				}
			});
		}

		@Override
		protected void onProgressUpdate(SearchResponse... responses) {
		
			ListViewSuggestionAdapter lvSuggestionAdapt = new ListViewSuggestionAdapter(
					getApplicationContext(), R.layout.listview_item_left,
					responses[0].getResult());
			setListAdapter(lvSuggestionAdapt);
			Log.i("Info llenar lista",responses[0].getResult().toString());
		}
	}

}
