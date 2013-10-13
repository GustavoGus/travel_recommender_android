/**
 * 
 */
package adm.Activities;

import adm.Repository.DataBaseManagement;
import adm.Repository.GlobalParameters;
import adm.Twitter.TwitterJ;
import adm.User.User;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class RememberTabGroup extends TabGroupActivity {
	final static public int CLOSE_DIALOG = 1;
	final static public int DELETE_DIALOG = 2;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		User user = new User(GlobalParameters.BASE_URL_JSON, this);

		if (user.hasAuthKey()) {
			startChildActivity("RememberTabActivity", new Intent(this,
					RememberTabActivity.class));
		} else
			startChildActivity("UserLoginActivity", new Intent(this,
					UserLoginActivity.class));
	}

	// ------------------------------------------------------------------

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		User user = new User(GlobalParameters.BASE_URL_JSON, getParent());
		menu.add(0, CLOSE_DIALOG, Menu.NONE, getResources().getString(R.string.close)).setIcon(
				android.R.drawable.ic_menu_close_clear_cancel);
		menu.add(0, DELETE_DIALOG, Menu.NONE, getResources().getString(R.string.delete)).setIcon(
				android.R.drawable.ic_menu_delete);

		if (!user.hasAuthKey())
			menu.setGroupEnabled(0, false);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case CLOSE_DIALOG:
			onCreateDialog(CLOSE_DIALOG).show();
			return true;
		case DELETE_DIALOG:
			onCreateDialog(DELETE_DIALOG).show();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected Dialog onCreateDialog(int id) {

		AlertDialog.Builder builder = new AlertDialog.Builder(getParent());
		switch (id) {
		case CLOSE_DIALOG:
			builder.setTitle(getResources().getString(R.string.close));
			builder.setMessage(getResources().getString(R.string.closeS) + "\n"
					+ getResources().getString(R.string.cont));
			builder.setPositiveButton(getResources().getString(R.string.ok),
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							User user = new User(
									GlobalParameters.BASE_URL_JSON, getParent());
							TwitterJ twitter = new TwitterJ(getParent());
							user.resetCredentials();
							twitter.resetAccessToken();
							getLocalActivityManager().getCurrentActivity().finish();
							dialog.cancel();
						}
					});

			builder.setNegativeButton(
					getResources().getString(R.string.cancel),
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
						}
					});

			break;
		case DELETE_DIALOG:
			builder.setTitle(getResources().getString(R.string.delete));
			builder.setMessage(getResources().getString(R.string.sup) + "\n"
					+ getResources().getString(R.string.cont));
			builder.setPositiveButton(getResources().getString(R.string.ok),
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							User user = new User(
									GlobalParameters.BASE_URL_JSON, getParent());
							TwitterJ twitter = new TwitterJ(getParent());
							final DataBaseManagement db = new DataBaseManagement(
									getParent());
							if (db.checkIfUserExist(user.getUserName()))
								db.deleteUser(user.getUserName());
							user.resetCredentials();
							twitter.resetAccessToken();
							getLocalActivityManager().getCurrentActivity().finish();
							dialog.cancel();
						}
					});

			builder.setNegativeButton(
					getResources().getString(R.string.cancel),
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
						}
					});
			break;
		}
		return builder.create();
	}

}
