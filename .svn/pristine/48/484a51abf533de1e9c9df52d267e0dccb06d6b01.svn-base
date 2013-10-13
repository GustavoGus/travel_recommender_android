/**
 * 
 */
package adm.Media;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Beep sound
 * 
 * @author Usuario
 * 
 */
public class BeepAndVibrate {
	private String logTag = "Beep";
	private Context theContext;

	/**
	 * Constructor
	 * 
	 * @param _theContext
	 */
	public BeepAndVibrate(Context _theContext) {
		theContext = _theContext;
	}

	/**
	 * Make beeps and/or vibrates in the given context and according to the
	 * _iterations you want.
	 * 
	 * @param _theContext
	 * @param _iterations
	 * @param _vibrate
	 * @param _beep
	 */
	public void makeBeepAndVibrate(int _iterations, boolean _vibrate,
			boolean _beep) {
		new AsyncTaskAvisos().execute(theContext, _iterations, _vibrate, _beep);
	}

	private class AsyncTaskAvisos extends AsyncTask<Object, Void, Void> {
		@Override
		protected void onPreExecute() {
			Log.i(logTag, "Start AsyncTaskAvisos");
		};
		@Override
		protected void onPostExecute(Void result) {
			Log.i(logTag, "Finalice AsyncTaskAvisos");
		};

		@Override
		protected Void doInBackground(Object... params) {
			try {
				// Vibrador
				Vibrator v = (Vibrator) ((Context) params[0])
						.getSystemService(Context.VIBRATOR_SERVICE);
				MediaPlayer mMediaPlayer = MediaPlayer.create(
						(Context) params[0], adm.Activities.R.raw.beep);

				// Avisa params[1] veces
				for (int i = 0; i < (Integer) params[1]; i++) {
					
					SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(theContext);
					
					
					boolean pref_vibrar = preferences.getBoolean("beep_pref", true);
					boolean pref_beep = preferences.getBoolean("vibration_pref", true);
					// Si vibrar y sonido
					if ( pref_beep && pref_vibrar) {
						// Start Sound
						mMediaPlayer.start();
						Log.i(logTag, "Sound " + mMediaPlayer.getDuration()
								+ " milliseconds");
						// Vibrate for sound length seconds
						v.vibrate(1000);
						Log.i(logTag, "Vibrate " + 1000 + " milliseconds");
						// Wait sound duration milliseconds
						Thread.sleep(1500);
						
					// Si solo vibrar
					} else	if (pref_vibrar && ! pref_beep) {
						// Vibrate for sound length seconds
						v.vibrate(1000);
						Log.i(logTag, "Vibrate " + 1000 + " milliseconds");
						// Wait sound duration milliseconds
						Thread.sleep(1500);
						// Si solo sonido
					} else 	if (!pref_vibrar && pref_beep) {
								// Start Sound
								mMediaPlayer.start();
								Log.i(logTag, "Sound " + " milliseconds");
								// Wait sound duration milliseconds
								Thread.sleep(1500);
							}
				}
					
				
			} catch (Exception ex) {
				Log.e(logTag, "AsyncTaskAviso failed: " + ex.getMessage());
			}
			return null;
		}
	}
}
