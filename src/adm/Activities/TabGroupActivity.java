package adm.Activities;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ActivityGroup;
import android.app.LocalActivityManager;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;

/**
 * 
 * @author Androit
 */
public class TabGroupActivity extends ActivityGroup {

	private ArrayList<String> mIdList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (mIdList == null)
			mIdList = new ArrayList<String>();
	}

	@Override
	public void finishFromChild(Activity child) {
		LocalActivityManager manager = getLocalActivityManager();
		int index = mIdList.size() - 1;

		if (index < 1) {
			finish();
			return;
		}

		manager.destroyActivity(mIdList.get(index), true);
		mIdList.remove(index);
		index--;
		String lastId = mIdList.get(index);
		Intent lastIntent = manager.getActivity(lastId).getIntent();
		Window newWindow = manager.startActivity(lastId, lastIntent);
		setContentView(newWindow.getDecorView());
	}

	/**
	 * Starts an Activity as a child Activity to this.
	 * 
	 * @param Id
	 *            Unique identifier of the activity to be started.
	 * @param intent
	 *            The Intent describing the activity to be started.
	 * @throws android.content.ActivityNotFoundException.
	 */
	public void startChildActivity(String Id, Intent intent) {
		Window window = getLocalActivityManager().startActivity(Id,
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
		if (window != null) {
			mIdList.add(Id);
			setContentView(window.getDecorView());
		}
		try {
			InputMethodManager inputManager = (InputMethodManager) this
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			inputManager.hideSoftInputFromWindow(this.getCurrentFocus()
					.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		} catch (Exception e) {
			Log.e("StartChildActivity", "Falla al 'ocultar teclado'");
		}
	}

	/**
	 * The primary purpose is to prevent systems before
	 * android.os.Build.VERSION_CODES.ECLAIR from calling their default
	 * KeyEvent.KEYCODE_BACK during onKeyDown.
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * Overrides the default implementation for KeyEvent.KEYCODE_BACK so that
	 * all systems call onBackPressed().
	 */
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& getResources().getConfiguration().hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_YES) {
			getLocalActivityManager().getCurrentActivity().finish();
			return true;
		}
		return super.onKeyUp(keyCode, event);
	}
	

	/**
	 * If a Child Activity handles KeyEvent.KEYCODE_BACK. Simply override and
	 * add this method.
	 * */
//	@Override
//	public void onBackPressed() {
//		InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//		if (inputManager.hideSoftInputFromWindow(this.getCurrentFocus()
//				.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS)) {
//			// Si teclado no mostrado, cierro la actividad
//			int length = mIdList.size();
//			if (length > 0) {
//				Activity current = getLocalActivityManager().getActivity(
//						mIdList.get(length - 1));
//				current.finish();
//			}
//
//		} else {
//			// Si mostrado, oculto teclado.
//			int length = mIdList.size();
//			if (length > 0) {
//				Activity current = getLocalActivityManager().getActivity(
//						mIdList.get(length - 1));
//				current.getWindow().setSoftInputMode(
//						WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
//
//			}
//
//		}
//	}
}
