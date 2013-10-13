/**
 * 
 */
package adm.Activities;

import adm.Repository.DataBaseManagement;
import adm.Repository.GlobalParameters;
import adm.User.User;
import adm.User.UserLoginResponse;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UserLoginActivity extends Activity {
	UserLoginResponse login;
	public TabGroupActivity parentActivity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		final User user = new User(GlobalParameters.BASE_URL_JSON, this);

		final DataBaseManagement database = new DataBaseManagement(this);
		database.CreateDB();

		super.onCreate(savedInstanceState);

		// Quitar barra de arriba.
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		// Aplicar layout
		setContentView(R.layout.login);

		Button login = (Button) findViewById(R.id.buttonLogin);
		final EditText name = (EditText) findViewById(R.id.editTextUser);
		final EditText pass = (EditText) findViewById(R.id.editTextPass);
		final TextView register = (TextView) findViewById(R.id.textViewSingUp);

		// Si ya hay sesión, rellena los campos
		if (user.hasAuthKey()) {
			name.setText(user.getUserName());
			pass.setText(user.getPassword());
		}
		// Recupera el password
		pass.setOnFocusChangeListener(new OnFocusChangeListener() {

			public void onFocusChange(View arg0, boolean arg1) {
				// TODO Auto-generated method stub
				String sname = name.getText().toString();

				if (database.checkIfUserExist(sname))
					pass.setText(database.findPassword(sname));
			}

		});

		// Botón login
		login.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				String sname = name.getText().toString();
				String spass = pass.getText().toString();

				if (!sname.equals("") && !spass.equals("")) {
					AsyncTaskLogin task = new AsyncTaskLogin(sname, spass,
							user, database);
					task.execute();

				} else
					try {
					Toast.makeText(getParent(),
							getResources().getString(R.string.EmptyField),
							Toast.LENGTH_LONG).show();
					} catch (Exception e) {
						
					}
			}
		});

		// Botón registrar
		register.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent(UserLoginActivity.this,
						UserRegisterActivity.class));
			}
		});
	}

	// AsyncTask -----------------

	public class AsyncTaskLogin extends AsyncTask<Void, Void, Integer> {

		/*
		 * final ProgressDialog dialog = ProgressDialog.show(getParent(),
		 * getParent().getString(R.string.cargando), getParent()
		 * .getString(R.string.sending), true);
		 */

		ProgressDialog dialog;
		final String name;
		final String pass;
		final User user;
		final DataBaseManagement database;

		public AsyncTaskLogin(String name, String pass, User user,
				DataBaseManagement db) {
			this.name = name;
			this.pass = pass;
			this.user = user;
			this.database = db;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			try {
			/*dialog = ProgressDialog.show(getApplicationContext(),
					getString(R.string.cargando), getString(R.string.sending),
					true);*/
			dialog = ProgressDialog.show(getParent(),
					 getParent().getString(R.string.cargando), getParent().getString(R.string.sending), true);
			dialog.setCancelable(false);
			} catch (Exception e) {
				
			}
		}

		@Override
		protected Integer doInBackground(Void... params) {
			login = user.login(name, pass, GlobalParameters.API_KEY);

			if (login != null) {
				if (login.getStatus() != 0)
					return 0;
				else
					return 1;
			} else
				return 2;
		}

		protected void onPostExecute(Integer result) {
			try {
				
				if (result == 0)
					Toast.makeText(getParent(), login.getMessage(),
							Toast.LENGTH_LONG).show();
				else if (result == 1) {
					database.createUser(name, pass);
					user.setCredentials(name, login.getResult(), pass);
					UserLoginActivity.this.finish();
					Toast.makeText(getParent(),
							getResources().getString(R.string.loginSuc),
							Toast.LENGTH_LONG).show();
				} else if (result == 2) {
					Toast.makeText(
							getParent(),
							getParent().getResources().getString(
									R.string.failCon), Toast.LENGTH_LONG)
							.show();
				}
				dialog.dismiss();
			} catch (Exception e) {

			}
		}
	}

}