/**
 * 
 */
package adm.Activities;

import adm.Repository.DataBaseManagement;
import adm.Repository.ImFeelingLucky;
import adm.Repository.ListViewImFeelingLuckyAdapter;
import adm.Repository.ViewHolder;
import adm.Search.SearchMyLocationResponse;
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
 * @author Androit
 * 
 */
public class SearchImFeelingLuckyActivity extends ListActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.buscar_listview);

		AsyncTaskImFeelingLucky task = new AsyncTaskImFeelingLucky();
		task.execute();
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {

		ViewHolder holder = (ViewHolder) v.getTag();
		Intent i = new Intent(this, PlaceActivity.class);
		i.putExtra("idPlace", holder.getIdPlace());
		startActivity(i);

		if (holder.getIdPlace() != -1) {
			try {
				DataBaseManagement database = new DataBaseManagement(this);
				database.createSearch("juanito", holder.getTypePlace(),
						String.valueOf(holder.getIdPlace()));
				Log.i("Imfeelinglucky", "BD: " + holder.getTypePlace() + " "
						+ String.valueOf(holder.getIdPlace()));
				Log.i("LISTCLICK",
						"Has seleccionado el elemento " + holder.getTypePlace()
								+ " " + String.valueOf(holder.getIdPlace()));
			} catch (Exception e) {
				Log.e("Imfeelinglucky", "Error en BD: " + e.getMessage());
			}
		}
		super.onListItemClick(l, v, position, id);
	}

	/**
	 * AsyncTaskSearchLocation
	 * 
	 * @author AndroIT
	 */
	private class AsyncTaskImFeelingLucky extends
			AsyncTask<Void, SearchMyLocationResponse, Void> {

		final ProgressDialog dialog = ProgressDialog.show(getParent(),
				getParent().getString(R.string.cargando), getParent()
						.getString(R.string.obtInfoServer), true);

		@Override
		protected Void doInBackground(Void... voids) {

			SearchMyLocationResponse smlResponse = new SearchMyLocationResponse();
			dialog.setCancelable(false);
			dialog.show();

			ImFeelingLucky oLuck = new ImFeelingLucky();

			try {
				oLuck.execute();
				smlResponse = oLuck.getResponse();

				publishProgress(smlResponse);
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
		protected void onProgressUpdate(SearchMyLocationResponse... responses) {

			ListViewImFeelingLuckyAdapter lvSuggestionAdapt = new ListViewImFeelingLuckyAdapter(
					getApplicationContext(), R.layout.listview_item_left,
					responses[0].getResult());
			setListAdapter(lvSuggestionAdapt);
		}

		@Override
		protected void onPostExecute(Void result) {
			runOnUiThread(new Runnable() {
				public void run() {
					dialog.dismiss();
				}
			});
		}
	}
}
